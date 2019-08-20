package com.themikeste1.wabbits.core.tileentities;

//META
import com.themikeste1.wabbits.atlas.Items;
import com.themikeste1.wabbits.atlas.TileEntitiesTypes;
import com.themikeste1.wabbits.core.gui.container.GeneratorRainbowShardContainer;
import com.themikeste1.wabbits.core.tools.EnergyStorageWabbits;

//Minecraft
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

//Forge
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

//Java
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


@SuppressWarnings("unchecked")
public class GeneratorRainbowShardTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(this::createEnergyHandler);
    private int counter;

    public GeneratorRainbowShardTileEntity() { super(TileEntitiesTypes.generator_rainbow_shard); }

    @Override
    public void tick() {
        pushPower();
        markDirty();

        AtomicBoolean maxCap = new AtomicBoolean(false);
        energyHandler.ifPresent(e -> maxCap.set(((EnergyStorageWabbits) e).atMaxCapacity()));

        if (maxCap.get())
            return;

        if (counter > 0) {
            counter--;
            energyHandler.ifPresent(e -> ((EnergyStorageWabbits) e).addEnergy(50));
        }
        else {
            itemHandler.ifPresent(
                    h -> {
                        ItemStack stack = h.getStackInSlot(0);
                        if (stack.getItem() == Items.rainbow_shard) {
                            h.extractItem(0, 1, false);
                            counter = 20;
                        }
                    }
            );
        }
    }

    private void pushPower() {
        energyHandler.ifPresent(
                sender -> {
                    AtomicInteger stored = new AtomicInteger(sender.getEnergyStored());
                    if (stored.get() <= 0)
                        return;

                    for (Direction direction : Direction.values()) {
                        TileEntity tileEntity = world.getTileEntity(pos.offset(direction));
                        if (tileEntity == null)
                            return;

                        tileEntity.getCapability(CapabilityEnergy.ENERGY, direction)
                                .ifPresent(
                                        receiver -> {
                                            if (receiver.canReceive()) {
                                                int accepted = receiver.receiveEnergy(Math.min(stored.get(), 100), false);
                                                stored.addAndGet(-accepted);
                                            }
                                        }
                        );

                        if (stored.get() <= 0)
                            break;
                    }

                    //The default EnergyStorage#extractEnergy would almost work
                    //here, but it has a max amount you can extract at once.
                    ((EnergyStorageWabbits) sender).addEnergy(-(sender.getEnergyStored() - stored.get()));
                }
        );
    }



    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        itemHandler.ifPresent( h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag) );
        CompoundNBT energyTag = tag.getCompound("energy");
        energyHandler.ifPresent( e -> ((INBTSerializable<CompoundNBT>) e).deserializeNBT(energyTag) );
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        itemHandler.ifPresent(
                h -> {
                    CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
                    tag.put("inv", compound);
                }
        );

        energyHandler.ifPresent(
                e -> {
                    CompoundNBT compound = ((INBTSerializable<CompoundNBT>) e).serializeNBT();
                    tag.put("energy", compound);
                }
        );

        return super.write(tag);
    }

    private IItemHandler createItemHandler() {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == Items.rainbow_shard;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.getItem() != Items.rainbow_shard) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }
        };
    }

    private IEnergyStorage createEnergyHandler() {
        return new EnergyStorageWabbits(100000);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemHandler.cast();

        if (cap == CapabilityEnergy.ENERGY)
            return energyHandler.cast();

        return super.getCapability(cap, side);
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new GeneratorRainbowShardContainer(id, playerInventory, world, pos);
    }

    public ItemStack getStackInSlot(int index) {
        AtomicReference<ItemStack> stack = new AtomicReference<ItemStack>();
        itemHandler.ifPresent( (h -> stack.set(h.getStackInSlot(index))) );
        return stack.get();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }
}
