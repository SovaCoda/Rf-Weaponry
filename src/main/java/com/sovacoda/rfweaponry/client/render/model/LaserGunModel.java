package com.sovacoda.rfweaponry.client.render.model;

import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.common.items.SpecialItem;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LaserGunModel extends AnimatedGeoModel<SpecialItem> {
    @Override
    public ResourceLocation getModelLocation(SpecialItem object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "geo/lasergunmodel.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SpecialItem object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "textures/items/lasergunmodeltexture.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SpecialItem object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "animations/lasergunmodel.animation.json");
    }
}
