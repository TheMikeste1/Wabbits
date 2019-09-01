package com.themikeste1.wabbits.core.tileentities;

import com.themikeste1.wabbits.Config;
import com.themikeste1.wabbits.atlas.Items;
import com.themikeste1.wabbits.atlas.TileEntityTypes;
import com.themikeste1.wabbits.core.gui.container.MaceratorContainer;
import com.themikeste1.wabbits.core.tools.EnergyStorageWabbits;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;


//TODO: Add macerate recipe, ticking, container,
// and make it accept the correct items.


@SuppressWarnings("DuplicatedCode")
public class MaceratorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(this::createEnergyHandler);
    private int counter = 0;

    public MaceratorTileEntity() {
        super(TileEntityTypes.macerator);
    }

    @Override
    public void tick() {
        if (world.isRemote)
            return;
    }

    private IItemHandler createItemHandler() {
        return new ItemStackHandler(2) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return ItemTags.getCollection()
                        .get(ResourceLocation.tryCreate("forge:ores"))
                        .contains(stack.getItem())
                        && slot == 0;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot != 0
                        || !ItemTags.getCollection()
                        .get(ResourceLocation.tryCreate("forge:ores"))
                        .contains(stack.getItem())
                ) {
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
        return new EnergyStorageWabbits(Config.MACERATOR_MAXCAP.get());
    }

    @Override
    public ITextComponent getDisplayName() { return new StringTextComponent(getType().getRegistryName().getPath()); }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new MaceratorContainer(id, playerInventory, world, pos);
    }

    public ItemStack getStackInSlot(int index) {
        AtomicReference<ItemStack> stack = new AtomicReference<ItemStack>();
        itemHandler.ifPresent( (h -> stack.set(h.getStackInSlot(index))) );
        return stack.get();
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemHandler.cast();

        if (cap == CapabilityEnergy.ENERGY)
            return energyHandler.cast();

        return super.getCapability(cap, side);
    }
}
