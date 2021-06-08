package com.sovacoda.rfweaponry.common.items;

import java.text.NumberFormat;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.glfw.GLFW;

import com.sovacoda.rfweaponry.client.render.item.LaserGunRenderer;
import com.sovacoda.rfweaponry.common.entities.LaserEntity;
import com.sovacoda.rfweaponry.core.capabilites.EnergyItemCapability;
import com.sovacoda.rfweaponry.core.init.SoundEventinit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
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


public class AssaultRife extends Item implements IAnimatable, ISyncable, ITickable{
	
    public AnimationFactory factory = new AnimationFactory(this);
    public String controllerName = "controller";
    public static final int ANIM_OPEN = 0;
    
    public int useTime = 7200;
	
	
    public AssaultRife(Properties properties)
    {
        super(properties.stacksTo(1));
        GeckoLibNetwork.registerSyncable(this);
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World p_77624_2_, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> {
            NumberFormat format = NumberFormat.getInstance();
            // todo: migrate to i18n
            tooltip.add(new StringTextComponent(String.format("%s/%s Redstone Flux", format.format(cap.getEnergyStored()), format.format(cap.getMaxEnergyStored()))));
            if (InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            	tooltip.add(new StringTextComponent("test"));
            }
            else {
            	tooltip.add(new StringTextComponent("Hold "+ "\u00A7e"+"Shift"+"\u00A7f"+" for More Information"));
            }
        });
    }
	
    
    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) 
    {
        return PlayState.CONTINUE;
    }


	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "controller", 0.05F, this::predicate));
    }
    
    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }
    
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
    	// TODO Auto-generated method stub
    	return false;
    }
    
    
	@Override
	public void onUseTick(World worldIn, LivingEntity entityUsed, ItemStack stack, int ticksUsed) {
		if (entityUsed instanceof PlayerEntity) {

			PlayerEntity playerIn = (PlayerEntity) entityUsed;
			
			if(!playerIn.getCooldowns().isOnCooldown(this) & this.getEnergyStored(stack) != 0) {
				playerIn.getCooldowns().addCooldown(this, 4);
				this.receiveEnergyInternal(stack, 30, false);
				this.extractEnergyInternal(stack, 20, false);
				
				if(!worldIn.isClientSide) {
					final int id = GeckoLibUtil.getIDFromStack(stack);
					final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> playerIn);
					GeckoLibNetwork.syncAnimation(target, this, id, ANIM_OPEN);
					
					LaserEntity laser = new LaserEntity(worldIn, playerIn );
					laser.shootFromRotation(playerIn ,playerIn.xRot, playerIn.yRot, 0, 3F, 0);
					worldIn.addFreshEntity(laser);
					worldIn.playSound((PlayerEntity) null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEventinit.LASER_FIRING.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);

					
					this.useTime = ticksUsed;
					
				}
				
			}
			

		}

	}
	@Override
	public boolean useOnRelease(ItemStack p_219970_1_) {
		this.useTime = 7200;
		return super.useOnRelease(p_219970_1_);
		
	}
	
	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.NONE;
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
				controller.setAnimation(new AnimationBuilder().addAnimation("animation.lasergunmodel.shoot", false));
			}
		}
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

		playerIn.startUsingItem(handIn);
		

		return ActionResult.fail(playerIn.getItemInHand(handIn));
	}
	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}
	
	public int getUseDuration(ItemStack stack) {
		return 7200;
	}
	
	public int ticksUsed() {
		return this.useTime;
	}
	

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new EnergyCapabilityProvider(stack, this);
	}
	
	public static class EnergyCapabilityProvider implements ICapabilityProvider{
		public final EnergyItemCapability storage;
		private final LazyOptional<EnergyItemCapability> lazyStorage;

		
		public EnergyCapabilityProvider(final ItemStack stack, AssaultRife item) {
			//Once SpecialItem is converted into a base for other items, change args to constructed values
			this.storage = new EnergyItemCapability(10000, 100, 100) {
			    @Override
                public int getEnergyStored() {
                    if (stack.hasTag()) {
                        return stack.getOrCreateTag().getInt("Energy");
                    } else {
                        return 0;
                    }
                }

                @Override
                public void setEnergyStored(int energy) {
                    stack.getOrCreateTag().putInt("Energy", energy);
                }
            };

			
			lazyStorage = LazyOptional.of(() -> this.storage);
		}
		
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap,@Nullable Direction side) {
			if(cap == CapabilityEnergy.ENERGY) {
				return this.lazyStorage.cast();
			}
			return LazyOptional.empty();
		};

	}
	

    public int getMaxEnergyStored(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }
    
    public int extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> cap.extractEnergy(maxExtract, simulate));
        return 0;
    }
    
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(cap -> cap.extractEnergy(maxExtract, simulate)).orElse(0);
    }
    
    public int getEnergyStored(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }
    
    public void setEnergy(ItemStack stack, int energy) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((EnergyItemCapability) cap).setEnergyStored(energy));
    }
    
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(cap -> cap.receiveEnergy(maxReceive, simulate)).orElse(0);
    }

    public int receiveEnergyInternal(ItemStack stack, int maxReceive, boolean simulate) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((EnergyItemCapability) cap).receiveEnergyInternal(maxReceive, simulate));
        return 0;
    }
    
	@Override
	public void tick() {
		
	}


}
