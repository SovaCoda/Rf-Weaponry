package com.sovacoda.rfweaponry.client.render.entity;

import com.sovacoda.rfweaponry.client.render.model.LaserPistolIModel;
import com.sovacoda.rfweaponry.client.render.model.LaserPistolIShotModel;
import com.sovacoda.rfweaponry.common.entities.LaserEntity;
import com.sovacoda.rfweaponry.common.entities.LaserPistolIShotEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class LaserPistolIShotRenderer extends GeoProjectilesRenderer<LaserPistolIShotEntity>{

	public LaserPistolIShotRenderer(EntityRendererManager renderManager) {
		super(renderManager, new LaserPistolIShotModel());
		this.shadowRadius = 0.7f;
	}



}
