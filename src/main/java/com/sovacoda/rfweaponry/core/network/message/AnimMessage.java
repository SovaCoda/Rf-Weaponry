package com.sovacoda.rfweaponry.core.network.message;

import java.util.function.Supplier;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkEvent;

public class AnimMessage {
	
	public int key;
	
	public AnimMessage(){
	}
	
	public AnimMessage(int key){
		this.key = key;
	}
	
	public static void encode(AnimMessage message, PacketBuffer buffer){
		buffer.writeInt(message.key);

	}   
	
	public static AnimMessage decode(PacketBuffer buffer){
		return new AnimMessage(buffer.readInt());
	}
	
	public static void handle(AnimMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() ->{
			ServerPlayerEntity player = context.getSender();
			World world = player.getCommandSenderWorld();
			world.setBlock(new BlockPos(player.position()), Blocks.DIAMOND_BLOCK.defaultBlockState(), 0);
			
			System.out.println("yo");
		
		});
		context.setPacketHandled(true);
	}
}
