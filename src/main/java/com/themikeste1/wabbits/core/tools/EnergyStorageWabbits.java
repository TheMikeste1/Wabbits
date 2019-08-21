package com.themikeste1.wabbits.core.tools;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

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
}
