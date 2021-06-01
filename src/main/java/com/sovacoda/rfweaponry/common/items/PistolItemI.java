package com.sovacoda.rfweaponry.common.items;

import com.sovacoda.rfweaponry.common.entities.LaserEntity;
import com.sovacoda.rfweaponry.common.entities.LaserPistolIShotEntity;
import com.sovacoda.rfweaponry.common.items.bases.RFGunItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class PistolItemI extends RFGunItem implements IAnimatable, ISyncable{
	
    public AnimationFactory factory = new AnimationFactory(this);
    public String controllerName = "controller";
    public static final int ANIM_OPEN = 0;

	public PistolItemI(Properties properties) {
		super(properties);
	}

	@Override
	public void onAnimationSync(int id, int state) {
		if (state == ANIM_OPEN) {
			// Always use GeckoLibUtil to get AnimationControllers when you don't have
			// access to an AnimationEvent
			@SuppressWarnings("rawtypes")
			final AnimationController controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);

			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(new AnimationBuilder().addAnimation("animation.pistolitemimodel.shoot", false));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, "controller", 0.05F, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
        return this.factory;
	}
	
    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) 
    {
        return PlayState.CONTINUE;
    }
    
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		if(!worldIn.isClientSide) {
			
			LaserPistolIShotEntity shot = new LaserPistolIShotEntity(worldIn, playerIn );
			shot.shootFromRotation(playerIn ,playerIn.xRot, playerIn.yRot, 0, 0.3F, 0);
			worldIn.addFreshEntity(shot);
		}
		return ActionResult.fail(playerIn.getItemInHand(handIn));
	}
	
}
