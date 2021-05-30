package com.sovacoda.rfweaponry.core.init;

import com.sovacoda.rfweaponry.Rfweaponry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Blockinit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, 
			Rfweaponry.MOD_ID);
	
	public static final RegistryObject<Block> POWER_BLOCK = BLOCKS.register("power_block", 
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN)
					.strength(15f, 10f)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
					.sound(SoundType.ANVIL)));
}
