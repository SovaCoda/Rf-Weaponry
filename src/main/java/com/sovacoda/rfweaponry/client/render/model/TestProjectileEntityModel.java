package com.sovacoda.rfweaponry.client.render.model;

import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.common.entities.LaserEntity;
import com.sovacoda.rfweaponry.common.entities.LaserPistolIShotEntity;
import com.sovacoda.rfweaponry.common.entities.TestProjectileEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TestProjectileEntityModel extends AnimatedGeoModel<TestProjectileEntity>{
    @Override
    public ResourceLocation getModelLocation(TestProjectileEntity object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "geo/pistolshotentitymodel.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TestProjectileEntity object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "textures/entities/pistolshotentitymodeltexture.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TestProjectileEntity object)
    {
        return new ResourceLocation(Rfweaponry.MOD_ID, "");
    }
}
