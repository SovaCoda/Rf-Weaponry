package com.sovacoda.rfweaponry.common.entities;

import java.util.Arrays;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.sovacoda.rfweaponry.core.init.EntityTypeinit;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;


public class BaseProjectile extends ProjectileEntity{
	private boolean noGravity = true;
	protected boolean inGround;
	private BlockState lastState;
	private int life;
	private double baseDamage = 2.0D;
	private int knockback;
	
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
	
	
	@Override
	public void shootFromRotation(Entity p_234612_1_, float p_234612_2_, float p_234612_3_, float p_234612_4_, float p_234612_5_, float p_234612_6_) {	      float f = -MathHelper.sin(p_234612_3_ * ((float)Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float)Math.PI / 180F));
      float f1 = -MathHelper.sin((p_234612_2_ + p_234612_4_) * ((float)Math.PI / 180F));
      float f2 = MathHelper.cos(p_234612_3_ * ((float)Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float)Math.PI / 180F));
      this.shoot((double)f, (double)f1, (double)f2, p_234612_5_, p_234612_6_);
	}
	
	
	
    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double p_70112_1_) {
       double d0 = this.getBoundingBox().getSize() * 10.0D;
       if (Double.isNaN(d0)) {
          d0 = 1.0D;
       }

       d0 = d0 * 64.0D * getViewScale();
       return p_70112_1_ < d0 * d0;
    }


   @OnlyIn(Dist.CLIENT)
   public void lerpTo(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
      this.setPos(p_180426_1_, p_180426_3_, p_180426_5_);
      this.setRot(p_180426_7_, p_180426_8_);
   }

   @OnlyIn(Dist.CLIENT)
   public void lerpMotion(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
      super.lerpMotion(p_70016_1_, p_70016_3_, p_70016_5_);
   }

   public void tick() {
      super.tick();
      boolean flag = this.isNoPhysics();
      Vector3d vector3d = this.getDeltaMovement();
      if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
         float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
         this.yRot = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
         this.xRot = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
         this.yRotO = this.yRot;
         this.xRotO = this.xRot;
      }

      BlockPos blockpos = this.blockPosition();
      BlockState blockstate = this.level.getBlockState(blockpos);
      if (!blockstate.isAir(this.level, blockpos) && !flag) {
         VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
         if (!voxelshape.isEmpty()) {
            Vector3d vector3d1 = this.position();

            for(AxisAlignedBB axisalignedbb : voxelshape.toAabbs()) {
               if (axisalignedbb.move(blockpos).contains(vector3d1)) {
                  this.inGround = true;
                  break;
               }
            }
         }
      }


      if (this.isInWaterOrRain()) {
         this.clearFire();
      }

      if (this.inGround && !flag) {
         if (this.lastState != blockstate && this.shouldFall()) {
            this.startFalling();
         } else if (!this.level.isClientSide) {
            this.tickDespawn();
         }

      } else {
         Vector3d vector3d2 = this.position();
         Vector3d vector3d3 = vector3d2.add(vector3d);
         RayTraceResult raytraceresult = this.level.clip(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
         if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
            vector3d3 = raytraceresult.getLocation();
         }

         while(!this.removed) {
            EntityRayTraceResult entityraytraceresult = this.findHitEntity(vector3d2, vector3d3);
            if (entityraytraceresult != null) {
               raytraceresult = entityraytraceresult;
            }

            if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
               Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
               Entity entity1 = this.getOwner();
               if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canHarmPlayer((PlayerEntity)entity)) {
                  raytraceresult = null;
                  entityraytraceresult = null;
               }
            }

            if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
               this.onHit(raytraceresult);
               this.hasImpulse = true;
            }

            if (entityraytraceresult == null) {
               break;
            }

            raytraceresult = null;
         }

         vector3d = this.getDeltaMovement();
         double d3 = vector3d.x;
         double d4 = vector3d.y;
         double d0 = vector3d.z;


         double d5 = this.getX() + d3;
         double d1 = this.getY() + d4;
         double d2 = this.getZ() + d0;
         float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
         if (flag) {
            this.yRot = (float)(MathHelper.atan2(-d3, -d0) * (double)(180F / (float)Math.PI));
         } else {
            this.yRot = (float)(MathHelper.atan2(d3, d0) * (double)(180F / (float)Math.PI));
         }

         this.xRot = (float)(MathHelper.atan2(d4, (double)f1) * (double)(180F / (float)Math.PI));
         this.xRot = lerpRotation(this.xRotO, this.xRot);
         this.yRot = lerpRotation(this.yRotO, this.yRot);
         float f2 = 1F;
         float f3 = 0.05F;

         this.setDeltaMovement(vector3d.scale((double)f2));
         if (!this.isNoGravity() && !flag) {
            Vector3d vector3d4 = this.getDeltaMovement();
            this.setDeltaMovement(vector3d4.x, vector3d4.y - (double)0.05F, vector3d4.z);
         }

         this.setPos(d5, d1, d2);
         this.checkInsideBlocks();
      }
   }

   private boolean shouldFall() {
      return this.inGround && this.level.noCollision((new AxisAlignedBB(this.position(), this.position())).inflate(0.06D));
   }

   private void startFalling() {
      this.inGround = false;
      Vector3d vector3d = this.getDeltaMovement();
      this.setDeltaMovement(vector3d.multiply((double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F)));
   }

   public void move(MoverType p_213315_1_, Vector3d p_213315_2_) {
      super.move(p_213315_1_, p_213315_2_);
      if (p_213315_1_ != MoverType.SELF && this.shouldFall()) {
         this.startFalling();
      }

   }

   protected void tickDespawn() {
      ++this.life;
      if (this.life >= 1200) {
         this.remove();
      }

   }


   protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
      super.onHitEntity(p_213868_1_);
      Entity entity = p_213868_1_.getEntity();
      int i = MathHelper.ceil(MathHelper.clamp(this.baseDamage, 0.0D, 2.147483647E9D));


      Entity entity1 = this.getOwner();
      DamageSource damagesource;
      if (entity1 == null) {
         damagesource = DamageSource.MAGIC;
      } else {
         damagesource = DamageSource.MAGIC;
         if (entity1 instanceof LivingEntity) {
            ((LivingEntity)entity1).setLastHurtMob(entity);
         }
      }

      boolean flag = entity.getType() == EntityType.ENDERMAN;
      int k = entity.getRemainingFireTicks();
      if (this.isOnFire() && !flag) {
         entity.setSecondsOnFire(5);
      }

      if (entity.hurt(damagesource, (float)i)) {
         if (flag) {
            return;
         }

         if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            
            if (this.knockback > 0) {
               Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D);
               if (vector3d.lengthSqr() > 0.0D) {
                  livingentity.push(vector3d.x, this.knockback, vector3d.z);
               }
            }


            if (!this.level.isClientSide && entity1 instanceof ServerPlayerEntity) {
               ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entity1;
            }
         }
         
         this.remove();
         
      } else {
         entity.setRemainingFireTicks(k);
         this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
         this.yRot += 180.0F;
         this.yRotO += 180.0F;
         if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
            this.remove();
         }
      }
      this.remove();

   }

   protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
      this.lastState = this.level.getBlockState(p_230299_1_.getBlockPos());
      super.onHitBlock(p_230299_1_);
      Vector3d vector3d = p_230299_1_.getLocation().subtract(this.getX(), this.getY(), this.getZ());
      this.setDeltaMovement(vector3d);
      Vector3d vector3d1 = vector3d.normalize().scale((double)0.05F);
      this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
      this.inGround = true;
      this.remove();
   }



   protected void doPostHurtEffects(LivingEntity p_184548_1_) {
   }

   @Nullable
   protected EntityRayTraceResult findHitEntity(Vector3d p_213866_1_, Vector3d p_213866_2_) {
      return ProjectileHelper.getEntityHitResult(this.level, this, p_213866_1_, p_213866_2_, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
   }

   protected boolean canHitEntity(Entity p_230298_1_) {
      return super.canHitEntity(p_230298_1_);
   }

   public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
      super.addAdditionalSaveData(p_213281_1_);
      p_213281_1_.putShort("life", (short)this.life);
      if (this.lastState != null) {
         p_213281_1_.put("inBlockState", NBTUtil.writeBlockState(this.lastState));
      }
      p_213281_1_.putBoolean("inGround", this.inGround);
      p_213281_1_.putDouble("damage", this.baseDamage);
   }

   public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
      super.readAdditionalSaveData(p_70037_1_);
      this.life = p_70037_1_.getShort("life");
      if (p_70037_1_.contains("inBlockState", 10)) {
         this.lastState = NBTUtil.readBlockState(p_70037_1_.getCompound("inBlockState"));
      }

      this.inGround = p_70037_1_.getBoolean("inGround");
      if (p_70037_1_.contains("damage", 99)) {
         this.baseDamage = p_70037_1_.getDouble("damage");
      }

  
     
   }

   public void setOwner(@Nullable Entity p_212361_1_) {
      super.setOwner(p_212361_1_);
   }


   protected boolean isMovementNoisy() {
      return false;
   }

   public void setBaseDamage(double p_70239_1_) {
      this.baseDamage = p_70239_1_;
   }

   public double getBaseDamage() {
      return this.baseDamage;
   }

   public void setKnockback(int p_70240_1_) {
      this.knockback = p_70240_1_;
   }

   public boolean isAttackable() {
      return false;
   }

   protected float getEyeHeight(Pose p_213316_1_, EntitySize p_213316_2_) {
      return 0.13F;
   }






   protected float getWaterInertia() {
      return 0.6F;
   }

   public void setNoPhysics(boolean p_203045_1_) {
      this.noPhysics = p_203045_1_;
   }

   public boolean isNoPhysics() {
      return this.noPhysics;
   }

	@Override
	protected void defineSynchedData() {
	}

	@Override
    public IPacket<?> getAddEntityPacket() {
      return NetworkHooks.getEntitySpawningPacket(this);
    }
	
	
}
