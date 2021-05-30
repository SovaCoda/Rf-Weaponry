package com.sovacoda.rfweaponry.client.render.item;

import com.sovacoda.rfweaponry.client.render.model.LaserGunModel;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class LaserGunRenderer extends GeoItemRenderer{
	
	public LaserGunRenderer(){
		super(new LaserGunModel());
	}
}
