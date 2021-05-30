package com.sovacoda.rfweaponry;

import com.sovacoda.rfweaponry.core.init.Blockinit;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

//This is a class that handles all the block item creations and registrations automatically.

@Mod.EventBusSubscriber(modid = Rfweaponry.MOD_ID, bus = Bus.MOD)
public class ModEventBusSubscriber {
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		Blockinit.BLOCKS.getEntries().stream().map(RegistryObject::get)
				.forEach(block -> {
					final Item.Properties properties = new Item.Properties().tab(ItemGroup.TAB_MISC);
					final BlockItem blockItem = new BlockItem(block, properties);
					blockItem.setRegistryName(block.getRegistryName());
					registry.register(blockItem);
                });
	}	
}
