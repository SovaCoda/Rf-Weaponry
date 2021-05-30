package com.sovacoda.rfweaponry.common.items.bases;

import javax.annotation.Nullable;

import com.sovacoda.rfweaponry.common.items.SpecialItem;
import com.sovacoda.rfweaponry.common.items.SpecialItem.EnergyCapabilityProvider;
import com.sovacoda.rfweaponry.core.capabilites.EnergyItemCapability;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyCapableItem extends Item{
	
	public EnergyCapableItem(Properties properties) {
		super(properties);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new EnergyCapabilityProvider(stack, this);
	}
	
	private static class EnergyCapabilityProvider implements ICapabilityProvider{
		public final EnergyItemCapability storage;
		private final LazyOptional<EnergyItemCapability> lazyStorage;

		
		public EnergyCapabilityProvider(final ItemStack stack, Item item) {
			//Once SpecialItem is converted into a base for other items, change args to constructed values
			this.storage = new EnergyItemCapability(10000, 100, 100) {
			    @Override
                public int getEnergyStored() {
                    if (stack.hasTag()) {
                        return stack.getOrCreateTag().getInt("Energy");
                    } else {
                        return 0;
                    }
                }

                @Override
                public void setEnergyStored(int energy) {
                    stack.getOrCreateTag().putInt("Energy", energy);
                }
            };

			
			lazyStorage = LazyOptional.of(() -> this.storage);
		}
		
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap,@Nullable Direction side) {
			if(cap == CapabilityEnergy.ENERGY) {
				return this.lazyStorage.cast();
			}
			return LazyOptional.empty();
		};

	}
	

    public int getMaxEnergyStored(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }
    
    public int extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> cap.extractEnergy(maxExtract, simulate));
        return 0;
    }
    
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(cap -> cap.extractEnergy(maxExtract, simulate)).orElse(0);
    }
    
    public int getEnergyStored(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }
    
    public void setEnergy(ItemStack stack, int energy) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((EnergyItemCapability) cap).setEnergyStored(energy));
    }
    
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(cap -> cap.receiveEnergy(maxReceive, simulate)).orElse(0);
    }

    public int receiveEnergyInternal(ItemStack stack, int maxReceive, boolean simulate) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((EnergyItemCapability) cap).receiveEnergyInternal(maxReceive, simulate));
        return 0;
    }
}
