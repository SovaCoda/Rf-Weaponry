package com.sovacoda.rfweaponry.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sovacoda.rfweaponry.client.render.model.LaserEntityModel;
import com.sovacoda.rfweaponry.common.entities.LaserEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;


public class LaserEntityRenderer extends GeoProjectilesRenderer<LaserEntity>
{
    public LaserEntityRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new LaserEntityModel());
        this.shadowRadius = 0.1F;

    }
    
}
