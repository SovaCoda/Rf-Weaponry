package com.sovacoda.rfweaponry.client.render.model;

import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.common.items.AssaultRife;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LaserGunModel extends AnimatedGeoModel<AssaultRife> {
    @Override
    public ResourceLocation getModelLocation(AssaultRife object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "geo/lasergunmodel.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AssaultRife object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "textures/items/lasergunmodeltexture.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AssaultRife object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "animations/lasergunmodel.animation.json");
    }
}
