package com.themikeste1.wabbits.core.gui.container;

import com.themikeste1.wabbits.atlas.Blocks;
import com.themikeste1.wabbits.atlas.ContainerTypes;

import com.themikeste1.wabbits.atlas.Items;
import com.themikeste1.wabbits.core.tools.EnergyStorageWabbits;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.concurrent.atomic.AtomicBoolean;


public class MaceratorContainer extends Container {
    private TileEntity tileEntity;

    public MaceratorContainer(int id, PlayerInventory playerInventory, World world, BlockPos pos) {
        super(ContainerTypes.macerator, id);
        tileEntity = world.getTileEntity(pos);
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .ifPresent( (
                        h -> {
                            addSlot(new SlotItemHandler(h, 0,  50, 35));
                            addSlot(new SlotItemHandler(h, 1, 116, 35));
                        })
                );

        layoutPlayerInventorySlots(8, 84, new InvWrapper(playerInventory));

        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getEnergy();
            }

            @Override
            public void set(int energy) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> ((EnergyStorageWabbits) e).setEnergy(energy));
            }
        });
    }

    public int getEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                AtomicBoolean accepted = new AtomicBoolean(false);
                tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(
                        h -> {
                            accepted.set(h.isItemValid(0, stack));
                        }
                );

                if (accepted.get()) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.mergeItemStack(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(
                IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()),
                playerIn,
                Blocks.macerator
        );
    }

    private int addSlotRow(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int i = 0 ; i < verAmount ; i++) {
            index = addSlotRow(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int col, int row, IItemHandler playerInventory) {
        //Player
        addSlotBox(playerInventory, 9, col, row, 9, 18, 3, 18);

        //Hotbar
        row += 58;
        addSlotRow(playerInventory, 0, col, row, 9, 18);
    }
}
