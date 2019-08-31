package com.themikeste1.wabbits.core.tileentities;

import com.themikeste1.wabbits.Config;
import com.themikeste1.wabbits.atlas.Items;
import com.themikeste1.wabbits.atlas.TileEntityTypes;
import com.themikeste1.wabbits.core.tools.EnergyStorageWabbits;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;


//TODO: Add #read/#write, macerate capability, ticking,
// and make it accept the correct items.


public class MaceratorTileEntity extends TileEntity {
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(this::createEnergyHandler);
    private int counter = 0;

    public MaceratorTileEntity() {
        super(TileEntityTypes.macerator);
    }


    private IItemHandler createItemHandler() {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return ItemTags.getCollection().get(ResourceLocation.tryCreate("forge:ores")).contains(stack.getItem());
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (ItemTags.getCollection().get(ResourceLocation.tryCreate("forge:ores")).contains(stack.getItem())) {
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
        return new EnergyStorageWabbits(10000);
    }
}
