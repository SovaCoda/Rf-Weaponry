package com.sovacoda.rfweaponry.core.init;

import com.sovacoda.rfweaponry.Rfweaponry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundEventinit {
	public static final DeferredRegister<SoundEvent> MOD_SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			Rfweaponry.MOD_ID);
	
	public static final RegistryObject<SoundEvent> LASER_FIRING = MOD_SOUNDS.register("rfweaponry.lasersoundfx", 
			() -> new SoundEvent(new ResourceLocation(Rfweaponry.MOD_ID, "rfweaponry.lasersoundfx")));
}
