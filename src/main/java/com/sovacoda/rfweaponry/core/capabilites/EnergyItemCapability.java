package com.sovacoda.rfweaponry.core.capabilites;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyItemCapability extends EnergyStorage{
	
    public EnergyItemCapability(int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract);
    }
    
    

    
    public int receiveEnergyInternal(int maxReceive, boolean simulate) {
        int before = this.maxReceive;
        this.maxReceive = Integer.MAX_VALUE;

        int toReturn = this.receiveEnergy(maxReceive, simulate);

        this.maxReceive = before;
        return toReturn;
    }

    public void addEnergyRaw(int energy) {
        this.energy = Math.min(this.energy + energy, this.capacity);
    }
    
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!this.canReceive()) { return 0; }
        int energy = this.getEnergyStored();

        int energyReceived = Math.min(this.capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            this.setEnergyStored(energy + energyReceived);
        }

        return energyReceived;
    }
    
    public int extractEnergyInternal(int maxExtract, boolean simulate) {
    	 int before = this.maxExtract;
         this.maxExtract = Integer.MAX_VALUE;

         int toReturn = this.extractEnergy(maxExtract, simulate);

         this.maxExtract = before;
         return toReturn;
    }
    
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!this.canExtract()) { return 0; }
        int energy = this.getEnergyStored();

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            this.setEnergyStored(energy - energyExtracted);
        }
        return energyExtracted;
    }
    
    @Override
    public int getEnergyStored() {
    	return super.getEnergyStored();
    }
    
    @Override
    public int getMaxEnergyStored() {
    	return super.getMaxEnergyStored();
    }
    
    @Override
    public boolean canExtract() {
    	return super.canExtract();
    }
    
    @Override
    public boolean canReceive() {
    	return super.canReceive();
    }
    
    public void setEnergyStored(int energy) {
    	this.energy = energy;
    }
    
    public void readFromNBT(CompoundNBT compound) {
    	this.energy = compound.getInt("Energy");
    	this.capacity = compound.getInt("Capacity");
    	this.maxReceive = compound.getInt("MaxReceive");
    	this.maxExtract = compound.getInt("MaxExtract");
    }
    
    public void writeToNBT(CompoundNBT compound) {
    	compound.putInt("Energy", this.energy);
    	compound.putInt("Capacity", this.capacity);
    	compound.putInt("MaxReceive", this.maxReceive);
    	compound.putInt("MaxExtract", this.maxExtract);
    }
}
