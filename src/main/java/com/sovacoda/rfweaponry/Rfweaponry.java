package com.sovacoda.rfweaponry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sovacoda.rfweaponry.client.render.entity.RegisterEntityRennderers;
import com.sovacoda.rfweaponry.core.init.Blockinit;
import com.sovacoda.rfweaponry.core.init.EntityTypeinit;
import com.sovacoda.rfweaponry.core.init.Iteminit;
import com.sovacoda.rfweaponry.core.init.SoundEventinit;
import com.sovacoda.rfweaponry.core.network.NewNetwork;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Rfweaponry.MOD_ID)

public class Rfweaponry
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "rfweaponry";
    
    public Rfweaponry() {
    	GeckoLib.initialize();
    	//Put the mod's event bus into a class for simpler reference
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	//Tell the bus to listen for events
    	bus.addListener(this::setup);
    	bus.addListener(this::commonSetup);
    	
    	
    	//Register the items from the item init package
    	Iteminit.ITEMS.register(bus);
    	
    	//Register the soundevents from the sounds init package
    	SoundEventinit.MOD_SOUNDS.register(bus);
    	
    	//Register the entities from the init package
    	EntityTypeinit.ENTITIES.register(bus);
    	
    	//Register the blocks from the init package
    	Blockinit.BLOCKS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
        

    }
    
    public void commonSetup(final FMLCommonSetupEvent event) 
    {
    	NewNetwork.init();
    }

    private void setup(final FMLClientSetupEvent event)
    {
    	RegisterEntityRennderers.registerRenderers(event);
    }
    

} 
