package com.sovacoda.rfweaponry.common.items.bases;

import net.minecraft.item.ItemStack;

public class RFGunItem extends EnergyCapableItem {
	
	public RFGunItem(Properties properties) {
		super(properties);
	}
	
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
    	return false;
    }

}
