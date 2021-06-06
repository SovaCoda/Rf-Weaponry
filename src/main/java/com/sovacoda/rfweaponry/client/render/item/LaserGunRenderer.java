package com.sovacoda.rfweaponry.client.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sovacoda.rfweaponry.client.render.model.LaserGunModel;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class LaserGunRenderer extends GeoItemRenderer{
	
	public LaserGunRenderer(){
		super(new LaserGunModel());
	}

	
}
