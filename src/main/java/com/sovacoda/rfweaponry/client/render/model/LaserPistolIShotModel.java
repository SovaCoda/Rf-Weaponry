package com.sovacoda.rfweaponry.client.render.model;

import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.common.entities.LaserEntity;
import com.sovacoda.rfweaponry.common.entities.LaserPistolIShotEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LaserPistolIShotModel extends AnimatedGeoModel<LaserPistolIShotEntity>{
    @Override
    public ResourceLocation getModelLocation(LaserPistolIShotEntity object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "geo/pistolshotentitymodel.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(LaserPistolIShotEntity object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "textures/entities/pistolshotentitymodeltexture.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LaserPistolIShotEntity object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "");
    }
}
