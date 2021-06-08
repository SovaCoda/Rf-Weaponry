package com.sovacoda.rfweaponry.common.entities;

import com.sovacoda.rfweaponry.core.init.EntityTypeinit;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TestProjectileEntity extends BaseProjectile implements IAnimatable{
	private AnimationFactory factory = new AnimationFactory(this);
	
	public TestProjectileEntity(EntityType<? extends BaseProjectile> type, World worldIn) {
		super(type, worldIn);
	}

	public TestProjectileEntity(EntityType<? extends BaseProjectile> type, LivingEntity owner, World worldIn) {
		super(type, owner, worldIn);
	}

	
	public TestProjectileEntity(World worldIn, LivingEntity owner) {
		this(EntityTypeinit.Test_Projectile_Entity.get(), owner, worldIn);
	}

	
	

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

}
