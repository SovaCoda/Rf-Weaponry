package com.sovacoda.rfweaponry.common.entities;

import java.util.List;
import java.util.function.Predicate;

import com.sovacoda.rfweaponry.core.init.EntityTypeinit;
import com.sovacoda.rfweaponry.core.util.MyProjectileHelper;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.command.EntitySelectorManager;
import net.minecraftforge.common.command.IEntitySelectorType;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LaserPistolIShotEntity extends AbstractArrowEntity implements IAnimatable{
	
	private AnimationFactory factory = new AnimationFactory(this);
	private float timeinair = 0;
	

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
    	return PlayState.CONTINUE;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }
	
	public LaserPistolIShotEntity(EntityType<? extends LaserPistolIShotEntity> type, World worldIn) {
		super(type, worldIn);

		this.setNoGravity(true);
	}
	
	public LaserPistolIShotEntity(World world, LivingEntity owner) {
		super(EntityTypeinit.Laser_Pistol_I_Shot_Entity.get(), owner, world);
		this.setNoGravity(true);
	}
	
	public LaserPistolIShotEntity(LivingEntity owner, double x, double y, double z, World worldIn) {
		super(EntityTypeinit.Laser_Pistol_I_Shot_Entity.get(), owner, worldIn);

		this.setNoGravity(true);
	}
   
	protected float getGravity() {
      return 0.03F;
	}
	
	
	
	
	
	
	public void findTarget() {
		
		World world = this.level;
		
		
		double scanScale = 10;
		
	    double x = (double)this.getX();
	    double y = (double)this.getY();
	    double z = (double)this.getZ();
	    
	    //Create a new bounding box to scan for projectiles of the same class around the shot
	    AxisAlignedBB scanArea = new AxisAlignedBB(x - scanScale, y - scanScale, z - scanScale, x + scanScale, y + scanScale, z + scanScale);
		//Scan the area in the bounding box for shots
		List<LaserPistolIShotEntity> entities = world.getEntitiesOfClass(LaserPistolIShotEntity.class, scanArea);
		
		if(!entities.isEmpty()) {
			for(int i = 0; i < entities.size(); i++) {
				if(!entities.get(i).equals(this)) {
					
				   //Loop through the shot's targets and get the shots position as well as the targets
	               Vector3d origin = this.getPosition(0.0f);
	               Vector3d target = entities.get(i).getPosition(0.0f);
	               
	               
	               //Find the distance between the original shot and its target
	               Vector3d distance = origin.vectorTo(target);
	               
//	               RayTraceResult ray = MyProjectileHelper.getHitResult(this.getEntity(), this::canHitEntity, target);
	               // Uses ProjectileHelper function to get entities hit
	               EntityRayTraceResult ray = ProjectileHelper.getEntityHitResult(world, this.getEntity(), origin, target, scanArea,this::canHitEntity);
	               
	               //check if the entity ray trace result is not null (will cause crash if this is not checked)
	               if (ray != null) {
	            	   Entity Mob = ray.getEntity();
	            	   //does not do damage to owner
	            	   if (Mob != this.getOwner()) {
	            		   Mob.hurt(DamageSource.MAGIC, 4);   
	            	   }
	               }
	          

	               
	               
	               //Create a new bounding box with one corner being the original shot's position and the other being the target's position
	               //AxisAlignedBB between = new AxisAlignedBB(origin, target);
	               
	               //Create a list of all entities within the bounding box
	               //List<Entity> targets = world.getEntities(null, between);
	               
	               //Loop through all entites within the bounding box
	               //for(int i2 = 0; i2 < targets.size(); i2++) {
	            	   
	            	   //Raytrace to check if the line between the original shot and  its target hits the entity found
	            	   //if(targets.get(i2).getBoundingBox().clip(origin, target) != null) {
	            		  // if (!targets.get(i2).getEntity().equals(this.getEntity()))
	            		   //{
	            			   //Do damage to the entity found if it is not a projectile
            				   //targets.get(i2).hurt(DamageSource.MAGIC, 3);

	            		   //}
	            	   //}
	               //}
	               
	               //Divide the distance up into 16 divisions
	               double divX = distance.x/16;
	               double divY = distance.y/16;
	               double divZ = distance.z/16;
	               
	               //Make a particle at each division to give the illusion of a line
	               for(int i1 = 1; i1 < 16; i1++) {
	            	   this.level.addParticle(ParticleTypes.CRIT,this.getX() + (divX * i1), this.getY() + (divY * i1), this.getZ() + (divZ * i1), 0, 0, 0);
	               }
	               
				}
				
			}
		}
	}

	
   @Override
   public void tick() {
      super.tick();
      RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
      boolean flag = false;
      if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
         BlockPos blockpos = ((BlockRayTraceResult)raytraceresult).getBlockPos();
         BlockState blockstate = this.level.getBlockState(blockpos);
         if (blockstate.is(Blocks.NETHER_PORTAL)) {
            this.handleInsidePortal(blockpos);
            flag = true;
         } else if (blockstate.is(Blocks.END_GATEWAY)) {
            TileEntity tileentity = this.level.getBlockEntity(blockpos);
            if (tileentity instanceof EndGatewayTileEntity && EndGatewayTileEntity.canEntityTeleport(this)) {
               ((EndGatewayTileEntity)tileentity).teleportEntity(this);
            }

            flag = true;
         }
         
      }

      if (raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
         this.onHit(raytraceresult);
      }

      this.checkInsideBlocks();
      Vector3d vector3d = this.getDeltaMovement();
      double d2 = this.getX() + vector3d.x;
      double d0 = this.getY() + vector3d.y;
      double d1 = this.getZ() + vector3d.z;
      this.updateRotation();
      
      
      if(this.isInWater()) {
    	  this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, Explosion.Mode.BREAK);
    	  this.remove();
      }


      if (!this.isNoGravity()) {
         Vector3d vector3d1 = this.getDeltaMovement();
         this.setDeltaMovement(vector3d1.x, vector3d1.y - (double)this.getGravity(), vector3d1.z);
      }

      this.setPos(d2, d0, d1);
      this.timeinair++;
      if(this.timeinair > 200) {
    	  this.remove();
      }
      
      this.findTarget();
      
      this.level.addParticle(new RedstoneParticleData(1F,1F,1F,1F),this.getX(), this.getY(), this.getZ(), 0, 0, 0);
   }

	@Override 
	protected void onHit(RayTraceResult p_70227_1_) {


		super.onHit(p_70227_1_);
		this.remove();
		for(int i = 0; i < 10; i++) {
			this.level.addParticle(new RedstoneParticleData(1F,1F,1F,1F),this.getX(), this.getY(), this.getZ(), 0, 0, 0);
		}
	}
  
	//Seems to disable the arrow hit ground sound effect 
	@Override
	protected void onHitBlock(BlockRayTraceResult result) {}

   @Override
	protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
		super.onHitEntity(p_213868_1_);
		p_213868_1_.getEntity().invulnerableTime = 0;
		Entity entity = this.getOwner();
		if (p_213868_1_.getType() != RayTraceResult.Type.ENTITY
		|| !((EntityRayTraceResult) p_213868_1_).getEntity().is(entity)) {
			if (!this.level.isClientSide) {
				this.remove();
			}
		}
		
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, inaccuracy);
	}
	
	@Override
    public void shootFromRotation(Entity p_234612_1_, float p_234612_2_, float p_234612_3_, float p_234612_4_, float p_234612_5_, float p_234612_6_) {
        float f = -MathHelper.sin(p_234612_3_ * ((float)Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float)Math.PI / 180F));
        float f1 = -MathHelper.sin((p_234612_2_ + p_234612_4_) * ((float)Math.PI / 180F));
        float f2 = MathHelper.cos(p_234612_3_ * ((float)Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float)Math.PI / 180F));
        this.shoot((double)f, (double)f1, (double)f2, p_234612_5_, p_234612_6_);
        this.setDeltaMovement(this.getDeltaMovement());
     }
	
   public IPacket<?> getAddEntityPacket() {
	   return NetworkHooks.getEntitySpawningPacket(this);
   }
	
	@Override
	protected ItemStack getPickupItem() {
		return null;
	}
}
