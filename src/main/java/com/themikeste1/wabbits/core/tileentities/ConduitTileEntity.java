package com.themikeste1.wabbits.core.tileentities;

import com.themikeste1.wabbits.Config;
import com.themikeste1.wabbits.atlas.TileEntityTypes;
import com.themikeste1.wabbits.core.tools.EnergyStorageWabbits;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
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
}