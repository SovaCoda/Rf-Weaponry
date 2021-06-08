package com.sovacoda.rfweaponry.client.event;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.common.items.AssaultRife;
import com.sovacoda.rfweaponry.core.init.Iteminit;
import com.sovacoda.rfweaponry.core.network.NewNetwork;
import com.sovacoda.rfweaponry.core.network.message.AnimMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber(modid = Rfweaponry.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {
	@SubscribeEvent
	public static void renderHand(RenderHandEvent event) {

	}
}
