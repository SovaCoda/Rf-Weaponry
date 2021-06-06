package com.sovacoda.rfweaponry.core.event;

import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.common.items.SpecialItem;
import com.sovacoda.rfweaponry.core.init.Iteminit;
import com.sovacoda.rfweaponry.core.network.NewNetwork;
import com.sovacoda.rfweaponry.core.network.message.AnimMessage;

import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.BipedModel.ArmPose;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

@EventBusSubscriber(modid = Rfweaponry.MOD_ID, bus = Bus.FORGE)
public class EventHandler {

	@SubscribeEvent
	public static void onBlockBreak(final BlockEvent.BreakEvent event) {
		//if(event.getState().getBlock().equals(Blocks.OAK_LOG))
		//event.getPlayer().addItem(ForgeRegistries.ITEMS.getValue(Iteminit.GUN_ITEM_REF.getId()).getDefaultInstance());
	}
	
	@SubscribeEvent
	public static void onRenderThird(final RenderPlayerEvent event)
	{
		
	}
}
