package com.sovacoda.rfweaponry.client.render.entity;

import com.sovacoda.rfweaponry.core.init.EntityTypeinit;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class RegisterEntityRennderers {
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerRenderers(final FMLClientSetupEvent event)
	{
	    RenderingRegistry.registerEntityRenderingHandler(EntityTypeinit.Laser_Entity.get(),
	    manager -> new LaserEntityRenderer(manager));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityTypeinit.Laser_Pistol_I_Shot_Entity.get(),
	    manager -> new LaserPistolIShotRenderer(manager));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityTypeinit.Test_Projectile_Entity.get(),
	    	    manager -> new TestProjectileEntityRenderer(manager));
	}
}
