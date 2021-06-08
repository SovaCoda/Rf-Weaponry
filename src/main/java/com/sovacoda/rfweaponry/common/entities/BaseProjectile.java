package com.sovacoda.rfweaponry.common.entities;

import javax.annotation.Nullable;

import com.sovacoda.rfweaponry.core.init.EntityTypeinit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BaseProjectile extends ProjectileEntity{
	private boolean noGravity = true;

	public BaseProjectile(EntityType<? extends BaseProjectile> type, World worldIn) {
		super(type, worldIn);
		this.setNoGravity(noGravity);
	}
	
	public BaseProjectile(EntityType<? extends BaseProjectile> type, World worldIn, double x, double y, double z) {
		this(type, worldIn);
		this.setPos(x, y, z);
	}
	
	public BaseProjectile(EntityType<? extends BaseProjectile> type, LivingEntity owner, World worldIn) {
		this(type, worldIn, owner.getX(), owner.getEyeY() - (double)0.1F, owner.getZ());
		this.setOwner(owner);
	}
	
	
	
    @Nullable
    protected EntityRayTraceResult findHitEntity(Vector3d p_213866_1_, Vector3d p_213866_2_) {
       return ProjectileHelper.getEntityHitResult(this.level, this, p_213866_1_, p_213866_2_, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }
	
	@Override
	public void tick() {
      super.tick();
      
		
	}
	


	@Override
	protected void defineSynchedData() {
	}

	@Override
    public IPacket<?> getAddEntityPacket() {
      return NetworkHooks.getEntitySpawningPacket(this);
    }
	
	
}
