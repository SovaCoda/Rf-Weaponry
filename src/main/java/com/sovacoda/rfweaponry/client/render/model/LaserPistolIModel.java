package com.sovacoda.rfweaponry.client.render.model;

import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.common.items.PistolItemI;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LaserPistolIModel extends AnimatedGeoModel<PistolItemI>{
	
    @Override
    public ResourceLocation getModelLocation(PistolItemI object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "geo/pistolitemimodel.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PistolItemI object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "textures/items/pistolitemimodeltexture.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PistolItemI object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "animations/pistolitemimodel.animation.json");
    }
    
}
