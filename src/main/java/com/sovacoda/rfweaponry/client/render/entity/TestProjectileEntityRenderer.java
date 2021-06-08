package com.sovacoda.rfweaponry.client.render.entity;

import com.sovacoda.rfweaponry.client.render.model.TestProjectileEntityModel;
import com.sovacoda.rfweaponry.common.entities.TestProjectileEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class TestProjectileEntityRenderer extends GeoProjectilesRenderer<TestProjectileEntity>{

    public TestProjectileEntityRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new TestProjectileEntityModel());
        this.shadowRadius = 0.1F;

    }
	
}
