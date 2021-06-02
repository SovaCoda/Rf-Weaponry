package com.sovacoda.rfweaponry.client.render.entity;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sovacoda.rfweaponry.client.render.model.LaserPistolIModel;
import com.sovacoda.rfweaponry.client.render.model.LaserPistolIShotModel;
import com.sovacoda.rfweaponry.common.entities.LaserEntity;
import com.sovacoda.rfweaponry.common.entities.LaserPistolIShotEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class LaserPistolIShotRenderer extends GeoProjectilesRenderer<LaserPistolIShotEntity>{

	public LaserPistolIShotRenderer(EntityRendererManager renderManager) {
		super(renderManager, new LaserPistolIShotModel());
		this.shadowRadius = 0.7f;
	}
	
	@Override
	public void render(LaserPistolIShotEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		Tessellator tessellator = Tessellator.getInstance();
	}



}
