package themikeste1.wabbits.core.tileentities;

import themikeste1.wabbits.Config;
import themikeste1.wabbits.atlas.TileEntityTypes;
import themikeste1.wabbits.core.tools.EnergyStorageWabbits;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class ConduitTileEntity extends TileEntity implements ITickableTileEntity {
    private LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(this::createEnergyHandler);

    public ConduitTileEntity() { super(TileEntityTypes.conduit); }

    @Override
    public void tick() {
        if (world.isRemote)
            return;

        energyHandler.ifPresent(
                e -> ((EnergyStorageWabbits)e).pushPower(this));
    }

    private IEnergyStorage createEnergyHandler() {
        return new EnergyStorageWabbits(Config.CONDUIT_MAXCAP.get());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY)
            return energyHandler.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT energyTag = tag.getCompound("energy");
        energyHandler.ifPresent( e -> ((INBTSerializable<CompoundNBT>) e).deserializeNBT(energyTag) );
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        energyHandler.ifPresent(
                e -> {
                    CompoundNBT compound = ((INBTSerializable<CompoundNBT>) e).serializeNBT();
                    tag.put("energy", compound);
                }
        );
        return super.write(tag);
    }
}
