package com.sovacoda.rfweaponry.core.network;

import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.core.network.message.AnimMessage;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NewNetwork {
	
	public static final String NETWORK_VERSION = "0.1.0";
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(Rfweaponry.MOD_ID, "network"), 
			() -> NETWORK_VERSION, version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION));
	
	public static void init() {
		CHANNEL.registerMessage(0, AnimMessage.class, AnimMessage::encode, AnimMessage::decode, AnimMessage::handle);
	}
}
