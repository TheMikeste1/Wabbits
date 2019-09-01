package com.themikeste1.wabbits.core.tools;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.concurrent.atomic.AtomicInteger;

public class EnergyStorageWabbits extends net.minecraftforge.energy.EnergyStorage implements INBTSerializable<CompoundNBT> {
    public EnergyStorageWabbits(int capacity) { super(capacity); }
    public EnergyStorageWabbits(int capacity, int maxTransfer) { super(capacity, maxTransfer); }
    public EnergyStorageWabbits(int capacity, int maxReceive, int maxExtract) { super(capacity, maxReceive, maxExtract); }
    public EnergyStorageWabbits(int capacity, int maxReceive, int maxExtract, int energy) { super(capacity, maxReceive, maxExtract, energy); }

    public void setEnergy(int energy) { this.energy = energy; }
    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored())
            this.energy = getMaxEnergyStored();
    }
    public int getEnergy() { return energy; }
    public boolean atMaxCapacity() { return energy >= getMaxEnergyStored(); }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("energy", getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag) { setEnergy(tag.getInt("energy")); }

    public void pushPower(TileEntity sendingEntity) {
        //Return if we have no energy.
        AtomicInteger stored = new AtomicInteger(getEnergyStored());
        if (stored.get() <= 0)
            return;

        for (Direction direction : Direction.values()) {
            TileEntity receivingEntity = sendingEntity.getWorld().getTileEntity(sendingEntity.getPos().offset(direction));
            //If there's no TE, just continue to the next.
            if (receivingEntity == null)
                continue;

            receivingEntity.getCapability(CapabilityEnergy.ENERGY, direction)
                    .ifPresent(
                            receiver -> {
                                if (receiver.canReceive()) {
                                    int accepted = receiver.receiveEnergy(Math.min(Math.min(stored.get(), maxExtract), 100), false);
                                    stored.addAndGet(-accepted);
                                }
                            }
                    );

            //If we're out of energy, leave.
            if (stored.get() <= 0) {
                break;
            }
        }

        //Update our stored energy
        int energyUsed = getEnergyStored() - stored.get();
        if (energyUsed != 0) {
            //The default EnergyStorage#extractEnergy would almost work
            //here, but it has a max amount you can extract at once.
            addEnergy(-energyUsed);
            sendingEntity.markDirty(); //We've transferred power
        }
    }
}
