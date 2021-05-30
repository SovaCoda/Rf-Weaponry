package com.sovacoda.rfweaponry.client.render.model;

import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.common.entities.LaserEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LaserEntityModel extends AnimatedGeoModel<LaserEntity>
{
    @Override
    public ResourceLocation getModelLocation(LaserEntity object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "geo/laserentitymodel.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(LaserEntity object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "textures/entities/laserentitymodeltexture.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LaserEntity object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "");
    }
}