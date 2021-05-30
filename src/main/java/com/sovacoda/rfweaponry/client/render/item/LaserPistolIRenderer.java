package com.sovacoda.rfweaponry.client.render.item;

import com.sovacoda.rfweaponry.client.render.model.LaserPistolIModel;

import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class LaserPistolIRenderer extends GeoItemRenderer{

	public LaserPistolIRenderer() {
		super(new LaserPistolIModel());
	}

}
