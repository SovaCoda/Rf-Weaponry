package com.sovacoda.rfweaponry.core.init;

import com.sovacoda.rfweaponry.Rfweaponry;
import com.sovacoda.rfweaponry.common.entities.LaserEntity;
import com.sovacoda.rfweaponry.common.entities.LaserPistolIShotEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeinit {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Rfweaponry.MOD_ID);
	
	
	public static final RegistryObject<EntityType<LaserEntity>> 
	Laser_Entity = ENTITIES.register(
		    "laser_entity", () -> 
	EntityType.Builder.<LaserEntity>of(LaserEntity::new, 
	EntityClassification.MISC).build("rfweaponry:laser_entity"));
	
	public static final RegistryObject<EntityType<LaserPistolIShotEntity>> 
	Laser_Pistol_I_Shot_Entity = ENTITIES.register(
		    "laser_pistol_i_shot_entity", () -> 
	EntityType.Builder.<LaserPistolIShotEntity>of(LaserPistolIShotEntity::new, 
	EntityClassification.MISC).setUpdateInterval(1).build("rfweaponry:laser_pistol_i_shot_entity"));

	public static final RegistryObject<EntityType<LaserEntity>> LASER_ENTITY_REF = RegistryObject.of(new ResourceLocation("rfweaponry:laser_entity"), ForgeRegistries.ENTITIES);

}
