package com.sovacoda.rfweaponry.core.init;

import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.common.items.PistolItemI;
import com.sovacoda.rfweaponry.common.items.AssaultRife;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.sovacoda.rfweaponry.client.render.item.LaserPistolIRenderer;
import com.sovacoda.rfweaponry.client.render.item.LaserGunRenderer;

//Handles the preparation of the initialization of our item by putting the item into a place holder registry class and passing it to the main class for final registry
public class Iteminit {
	
	//Create a deferred register that can handle items called "ITEMS"
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Rfweaponry.MOD_ID);
	
	//Create a new Item called "GUN_ITEM" and put its registry and properties into the deferred register
	public static final RegistryObject<AssaultRife> GUN_ITEM = ITEMS.register("gun_item", 
			() -> new AssaultRife(new Item.Properties().tab(ItemGroup.TAB_MISC).setISTER(() -> LaserGunRenderer::new)));
	
	public static final RegistryObject<PistolItemI> PISTOL_ITEM_I = ITEMS.register("pistol_item_i", 
			() -> new PistolItemI(new Item.Properties().tab(ItemGroup.TAB_MISC).setISTER(() -> LaserPistolIRenderer::new)));
	
	//Hellllllloooo
	

}