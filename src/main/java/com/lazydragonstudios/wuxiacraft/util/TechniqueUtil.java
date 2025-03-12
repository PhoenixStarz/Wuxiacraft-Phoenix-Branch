package com.lazydragonstudios.wuxiacraft.util;

import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaTechniqueAspects;
import com.lazydragonstudios.wuxiacraft.init.WuxiaBlocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;

public class TechniqueUtil {

	private static final HashMap<Item, HashMap<ResourceLocation, BigDecimal>> DEVOURING_ITEM_TO_REWARD = new HashMap<>();

	private static final HashMap<Block, HashMap<ResourceLocation, Double>> BLOCK_TO_CHANCED_ASPECT = new HashMap<>();

	private static final HashMap<EntityType<?>, HashMap<ResourceLocation, Double>> ENTITY_TO_CHANCED_ASPECT = new HashMap();

	public static void initDevouringData() {
		//TODO rework all this shit 
		addDevouringData(Items.DIRT, WuxiaElements.EARTH.getId(), new BigDecimal("0.9"));
		addDevouringData(Items.STONE, WuxiaElements.EARTH.getId(), new BigDecimal("1.4"));
		addDevouringData(Items.COBBLESTONE, WuxiaElements.EARTH.getId(), new BigDecimal("1.3"));
		addDevouringData(Items.GRASS_BLOCK, WuxiaElements.EARTH.getId(), new BigDecimal("0.7"));
		addDevouringData(Items.GRASS_BLOCK, WuxiaElements.WOOD.getId(), new BigDecimal("0.4"));
		addDevouringData(Items.COAL, WuxiaElements.EARTH.getId(), new BigDecimal("6"));
		addDevouringData(Items.COAL, WuxiaElements.FIRE.getId(), new BigDecimal("1"));
		addDevouringData(Items.CHARCOAL, WuxiaElements.WOOD.getId(), new BigDecimal("4"));
		addDevouringData(Items.CHARCOAL, WuxiaElements.FIRE.getId(), new BigDecimal("1"));
		addDevouringData(Items.STONE_BUTTON, WuxiaElements.EARTH.getId(), new BigDecimal("1"));
		addDevouringData(Items.BRICK, WuxiaElements.EARTH.getId(), new BigDecimal("1"));
		addDevouringData(Items.BRICK, WuxiaElements.FIRE.getId(), new BigDecimal("1"));
		addDevouringData(Items.CLAY, WuxiaElements.EARTH.getId(), new BigDecimal("1"));
		addDevouringData(Items.CLAY, WuxiaElements.WATER.getId(), new BigDecimal("1"));
		addDevouringData(Items.ANVIL, WuxiaElements.METAL.getId(), new BigDecimal("20"));
		addDevouringData(Items.GLOWSTONE_DUST, WuxiaElements.LIGHT.getId(), new BigDecimal("1"));
		addDevouringData(Items.GLOWSTONE, WuxiaElements.LIGHT.getId(), new BigDecimal("2"));
		addDevouringData(Items.ACACIA_LOG, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.BIRCH_LOG, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.DARK_OAK_LOG, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.JUNGLE_LOG, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.OAK_LOG, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.SPRUCE_LOG, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.SPRUCE_LOG, WuxiaElements.WOOD.getId(), new BigDecimal("0.5"));
		addDevouringData(Items.ACACIA_PLANKS, WuxiaElements.WOOD.getId(), new BigDecimal("1"));
		addDevouringData(Items.BIRCH_PLANKS, WuxiaElements.WOOD.getId(), new BigDecimal("1"));
		addDevouringData(Items.DARK_OAK_PLANKS, WuxiaElements.WOOD.getId(), new BigDecimal("1"));
		addDevouringData(Items.JUNGLE_PLANKS, WuxiaElements.WOOD.getId(), new BigDecimal("1"));
		addDevouringData(Items.OAK_PLANKS, WuxiaElements.WOOD.getId(), new BigDecimal("1"));
		addDevouringData(Items.SPRUCE_PLANKS, WuxiaElements.WOOD.getId(), new BigDecimal("1"));
		addDevouringData(Items.IRON_INGOT, WuxiaElements.METAL.getId(), new BigDecimal("1"));
		addDevouringData(Items.IRON_BLOCK, WuxiaElements.METAL.getId(), new BigDecimal("7")); //loss intended, in many cases
		addDevouringData(Items.IRON_ORE, WuxiaElements.METAL.getId(), new BigDecimal("0.8"));
		addDevouringData(Items.IRON_ORE, WuxiaElements.EARTH.getId(), new BigDecimal("0.6"));
		addDevouringData(Items.COPPER_ORE, WuxiaElements.METAL.getId(), new BigDecimal("0.6"));
		addDevouringData(Items.COPPER_ORE, WuxiaElements.EARTH.getId(), new BigDecimal("0.6"));
		addDevouringData(Items.COAL_ORE, WuxiaElements.EARTH.getId(), new BigDecimal("6"));
		addDevouringData(Items.COAL_ORE, WuxiaElements.FIRE.getId(), new BigDecimal("0.6"));
		addDevouringData(Items.DIAMOND, WuxiaElements.EARTH.getId(), new BigDecimal("16"));
		addDevouringData(Items.EMERALD, WuxiaElements.EARTH.getId(), new BigDecimal("12"));
	}
	
	public static void initChancedAspectsBlocks() {
		addBlockToAspectChanced(Blocks.MAGMA_BLOCK, WuxiaTechniqueAspects.CINDER.getId(), 1000d);
		addBlockToAspectChanced(Blocks.NETHERRACK, WuxiaTechniqueAspects.CINDER.getId(), 1000d);
		addBlockToAspectChanced(Blocks.BASALT, WuxiaTechniqueAspects.CINDER.getId(), 1000d);
		addBlockToAspectChanced(Blocks.FIRE, WuxiaTechniqueAspects.CINDER.getId(), 1000d);
		addBlockToAspectChanced(Blocks.SOUL_FIRE, WuxiaTechniqueAspects.CINDER.getId(), 1000d);
		addBlockToAspectChanced(Blocks.MAGMA_BLOCK, WuxiaTechniqueAspects.EMBER.getId(), 3000d);
		addBlockToAspectChanced(Blocks.FIRE, WuxiaTechniqueAspects.EMBER.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SOUL_FIRE, WuxiaTechniqueAspects.EMBER.getId(), 3000d);
		addBlockToAspectChanced(Blocks.FIRE, WuxiaTechniqueAspects.BLAZE.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SOUL_FIRE, WuxiaTechniqueAspects.BLAZE.getId(), 9000d);
		addBlockToAspectChanced(Blocks.MAGMA_BLOCK, WuxiaTechniqueAspects.SCORCH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.NETHER_BRICKS, WuxiaTechniqueAspects.SCORCH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.NETHER_WART, WuxiaTechniqueAspects.SCORCH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.MAGMA_BLOCK, WuxiaTechniqueAspects.MAGIC_BURNING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.FIRE, WuxiaTechniqueAspects.MAGIC_BURNING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SOUL_FIRE, WuxiaTechniqueAspects.MAGIC_BURNING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SOUL_SAND, WuxiaTechniqueAspects.MIND_FLARE.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SOUL_SOIL, WuxiaTechniqueAspects.MIND_FLARE.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SOUL_FIRE, WuxiaTechniqueAspects.MIND_FLARE.getId(), 3000d);
			
		addBlockToAspectChanced(Blocks.DIRT, WuxiaTechniqueAspects.DUST.getId(), 1000d);
		addBlockToAspectChanced(Blocks.GRAVEL, WuxiaTechniqueAspects.DUST.getId(), 1000d);
		addBlockToAspectChanced(Blocks.SAND, WuxiaTechniqueAspects.DUST.getId(), 1000d);
		addBlockToAspectChanced(Blocks.RED_SAND, WuxiaTechniqueAspects.DUST.getId(), 1000d);
		addBlockToAspectChanced(Blocks.STONE, WuxiaTechniqueAspects.DUST.getId(), 1000d);
		addBlockToAspectChanced(Blocks.DIRT, WuxiaTechniqueAspects.DIRT.getId(), 3000d);
		addBlockToAspectChanced(Blocks.STONE, WuxiaTechniqueAspects.DIRT.getId(), 3000d);
		addBlockToAspectChanced(Blocks.GRAVEL, WuxiaTechniqueAspects.DIRT.getId(), 3000d);
		addBlockToAspectChanced(Blocks.STONE, WuxiaTechniqueAspects.PEBBLES.getId(), 9000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE, WuxiaTechniqueAspects.PEBBLES.getId(), 9000d);
		addBlockToAspectChanced(Blocks.STONE, WuxiaTechniqueAspects.HARDENING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE, WuxiaTechniqueAspects.HARDENING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.GRAVEL, WuxiaTechniqueAspects.HARDENING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.GRAVEL, WuxiaTechniqueAspects.TREMOR.getId(), 3000d);
		addBlockToAspectChanced(Blocks.STONE, WuxiaTechniqueAspects.TREMOR.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE, WuxiaTechniqueAspects.TREMOR.getId(), 3000d);
		addBlockToAspectChanced(Blocks.GRAVEL, WuxiaTechniqueAspects.STILLNESS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.STONE, WuxiaTechniqueAspects.STILLNESS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE, WuxiaTechniqueAspects.STILLNESS.getId(), 3000d);
	
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addBlockToAspectChanced(Blocks.CLAY, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.FLOW.getId(), 3000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.FLOW.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.WATERFALL.getId(), 9000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.WATERFALL.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.SPLASH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.SPLASH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.STREAM.getId(), 3000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.STREAM.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.WAVING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.WAVING.getId(), 3000d);
	
		addBlockToAspectChanced(Blocks.GRASS_BLOCK, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.GRASS, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.MOSS_BLOCK, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.OAK_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.OAK_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.GRASS_BLOCK, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.GRASS, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.MOSS_BLOCK, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.OAK_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.OAK_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.OAK_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
	
		addBlockToAspectChanced(Blocks.IRON_ORE, WuxiaTechniqueAspects.ORE.getId(), 1000d);
		addBlockToAspectChanced(Blocks.COPPER_ORE, WuxiaTechniqueAspects.ORE.getId(), 1000d);
		addBlockToAspectChanced(Blocks.GOLD_ORE, WuxiaTechniqueAspects.ORE.getId(), 1000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_IRON_ORE, WuxiaTechniqueAspects.ORE.getId(), 1000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_COPPER_ORE, WuxiaTechniqueAspects.ORE.getId(), 1000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_GOLD_ORE, WuxiaTechniqueAspects.ORE.getId(), 1000d);
		addBlockToAspectChanced(Blocks.IRON_ORE, WuxiaTechniqueAspects.METAL_NUGGET.getId(), 3000d);
		addBlockToAspectChanced(Blocks.COPPER_ORE, WuxiaTechniqueAspects.METAL_NUGGET.getId(), 3000d);
		addBlockToAspectChanced(Blocks.GOLD_ORE, WuxiaTechniqueAspects.METAL_NUGGET.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_IRON_ORE, WuxiaTechniqueAspects.METAL_NUGGET.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_COPPER_ORE, WuxiaTechniqueAspects.METAL_NUGGET.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_GOLD_ORE, WuxiaTechniqueAspects.METAL_NUGGET.getId(), 3000d);
		addBlockToAspectChanced(Blocks.IRON_ORE, WuxiaTechniqueAspects.METAL_INGOT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.COPPER_ORE, WuxiaTechniqueAspects.METAL_INGOT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.GOLD_ORE, WuxiaTechniqueAspects.METAL_INGOT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_IRON_ORE, WuxiaTechniqueAspects.METAL_INGOT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_COPPER_ORE, WuxiaTechniqueAspects.METAL_INGOT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_GOLD_ORE, WuxiaTechniqueAspects.METAL_INGOT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.IRON_ORE, WuxiaTechniqueAspects.METAL_SKIN.getId(), 3000d);
		addBlockToAspectChanced(Blocks.COPPER_ORE, WuxiaTechniqueAspects.METAL_SKIN.getId(), 3000d);
		addBlockToAspectChanced(Blocks.GOLD_ORE, WuxiaTechniqueAspects.METAL_SKIN.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_IRON_ORE, WuxiaTechniqueAspects.METAL_SKIN.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_COPPER_ORE, WuxiaTechniqueAspects.METAL_SKIN.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_GOLD_ORE, WuxiaTechniqueAspects.METAL_SKIN.getId(), 3000d);
		addBlockToAspectChanced(Blocks.IRON_ORE, WuxiaTechniqueAspects.SHARPNESS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.IRON_BLOCK, WuxiaTechniqueAspects.SHARPNESS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_IRON_ORE, WuxiaTechniqueAspects.SHARPNESS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.IRON_ORE, WuxiaTechniqueAspects.MAGNETIZATION.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_IRON_ORE, WuxiaTechniqueAspects.MAGNETIZATION.getId(), 3000d);
		addBlockToAspectChanced(Blocks.COPPER_ORE, WuxiaTechniqueAspects.MAGNETIZATION.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DEEPSLATE_COPPER_ORE, WuxiaTechniqueAspects.MAGNETIZATION.getId(), 3000d);
		}
	
	public static void initEntityChancedAspects() {
		addEntityToAspectChanced(EntityType.BLAZE, WuxiaTechniqueAspects.CINDER.getId(), 1000d);
		addEntityToAspectChanced(EntityType.MAGMA_CUBE, WuxiaTechniqueAspects.CINDER.getId(), 1000d);
		addEntityToAspectChanced(EntityType.BLAZE, WuxiaTechniqueAspects.EMBER.getId(), 3000d);
		addEntityToAspectChanced(EntityType.MAGMA_CUBE, WuxiaTechniqueAspects.EMBER.getId(), 3000d);
		addEntityToAspectChanced(EntityType.BLAZE, WuxiaTechniqueAspects.BLAZE.getId(), 9000d);
		addEntityToAspectChanced(EntityType.MAGMA_CUBE, WuxiaTechniqueAspects.BLAZE.getId(), 9000d);
		addEntityToAspectChanced(EntityType.BLAZE, WuxiaTechniqueAspects.SCORCH.getId(), 3000d);
		addEntityToAspectChanced(EntityType.MAGMA_CUBE, WuxiaTechniqueAspects.SCORCH.getId(), 3000d);
		addEntityToAspectChanced(EntityType.BLAZE, WuxiaTechniqueAspects.MAGIC_BURNING.getId(), 3000d);
		addEntityToAspectChanced(EntityType.MAGMA_CUBE, WuxiaTechniqueAspects.MAGIC_BURNING.getId(), 3000d);
		addEntityToAspectChanced(EntityType.SKELETON, WuxiaTechniqueAspects.MIND_FLARE.getId(), 3000d);
		addEntityToAspectChanced(EntityType.WITHER_SKELETON, WuxiaTechniqueAspects.MIND_FLARE.getId(), 3000d);
			
		addEntityToAspectChanced(EntityType.SILVERFISH, WuxiaTechniqueAspects.DUST.getId(), 1000d);
		addEntityToAspectChanced(EntityType.SILVERFISH, WuxiaTechniqueAspects.DIRT.getId(), 3000d);
		addEntityToAspectChanced(EntityType.SILVERFISH, WuxiaTechniqueAspects.PEBBLES.getId(), 9000d);
		addEntityToAspectChanced(EntityType.SILVERFISH, WuxiaTechniqueAspects.HARDENING.getId(), 3000d);
		addEntityToAspectChanced(EntityType.SILVERFISH, WuxiaTechniqueAspects.TREMOR.getId(), 3000d);
		addEntityToAspectChanced(EntityType.SILVERFISH, WuxiaTechniqueAspects.STILLNESS.getId(), 3000d);
	
		addEntityToAspectChanced(EntityType.COD, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addEntityToAspectChanced(EntityType.SALMON, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addEntityToAspectChanced(EntityType.SQUID, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addEntityToAspectChanced(EntityType.TROPICAL_FISH, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addEntityToAspectChanced(EntityType.GUARDIAN, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addEntityToAspectChanced(EntityType.DROWNED, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addEntityToAspectChanced(EntityType.SQUID, WuxiaTechniqueAspects.FLOW.getId(), 3000d);
		addEntityToAspectChanced(EntityType.GUARDIAN, WuxiaTechniqueAspects.FLOW.getId(), 3000d);
		addEntityToAspectChanced(EntityType.DROWNED, WuxiaTechniqueAspects.FLOW.getId(), 3000d);
		addEntityToAspectChanced(EntityType.GUARDIAN, WuxiaTechniqueAspects.WATERFALL.getId(), 9000d);
		addEntityToAspectChanced(EntityType.DROWNED, WuxiaTechniqueAspects.WATERFALL.getId(), 9000d);
		addEntityToAspectChanced(EntityType.COD, WuxiaTechniqueAspects.SPLASH.getId(), 3000d);
		addEntityToAspectChanced(EntityType.SALMON, WuxiaTechniqueAspects.SPLASH.getId(), 3000d);
		addEntityToAspectChanced(EntityType.TROPICAL_FISH, WuxiaTechniqueAspects.SPLASH.getId(), 3000d);
		addEntityToAspectChanced(EntityType.SQUID, WuxiaTechniqueAspects.STREAM.getId(), 3000d);
		addEntityToAspectChanced(EntityType.SALMON, WuxiaTechniqueAspects.STREAM.getId(), 3000d);
		addEntityToAspectChanced(EntityType.DROWNED, WuxiaTechniqueAspects.STREAM.getId(), 3000d);
		addEntityToAspectChanced(EntityType.GUARDIAN, WuxiaTechniqueAspects.WAVING.getId(), 3000d);
		addEntityToAspectChanced(EntityType.DROWNED, WuxiaTechniqueAspects.WAVING.getId(), 3000d);
		addEntityToAspectChanced(EntityType.COD, WuxiaTechniqueAspects.WAVING.getId(), 3000d);
	
		addEntityToAspectChanced(EntityType.IRON_GOLEM, WuxiaTechniqueAspects.ORE.getId(), 200d);
		addEntityToAspectChanced(EntityType.IRON_GOLEM, WuxiaTechniqueAspects.METAL_NUGGET.getId(), 600d);
		addEntityToAspectChanced(EntityType.IRON_GOLEM, WuxiaTechniqueAspects.METAL_INGOT.getId(), 1800d);
		addEntityToAspectChanced(EntityType.IRON_GOLEM, WuxiaTechniqueAspects.METAL_SKIN.getId(), 600d);
		addEntityToAspectChanced(EntityType.IRON_GOLEM, WuxiaTechniqueAspects.SHARPNESS.getId(), 600d);
		addEntityToAspectChanced(EntityType.IRON_GOLEM, WuxiaTechniqueAspects.MAGNETIZATION.getId(), 600d);
	
		addEntityToAspectChanced(EntityType.ZOMBIE, WuxiaTechniqueAspects.DEVOURING.getId(), 3000d);
		addEntityToAspectChanced(EntityType.HUSK, WuxiaTechniqueAspects.DEVOURING.getId(), 3000d);
		}
	

	public static void addBlockToAspectChanced(Block block, ResourceLocation aspect, double chance) {
		BLOCK_TO_CHANCED_ASPECT.putIfAbsent(block, new HashMap<>());
		BLOCK_TO_CHANCED_ASPECT.get(block).put(aspect, chance);
	}

	public static HashMap<ResourceLocation, Double> getAspectChancePerBlock(Block block) {
		return BLOCK_TO_CHANCED_ASPECT.getOrDefault(block, new HashMap<>());
	}

	public static void addDevouringData(Item item, ResourceLocation element, BigDecimal value) {
		DEVOURING_ITEM_TO_REWARD.putIfAbsent(item, new HashMap<>());
		DEVOURING_ITEM_TO_REWARD.get(item).put(element, value);
	}

	@Nonnull
	public static HashMap<ResourceLocation, BigDecimal> getDevouringDataPerItem(Item item) {
		return DEVOURING_ITEM_TO_REWARD.getOrDefault(item, new HashMap<>());
	}

	public static void addEntityToAspectChanced(EntityType<?> entityType, ResourceLocation aspect, double chance) {
    	ENTITY_TO_CHANCED_ASPECT.putIfAbsent(entityType, new HashMap<>());
    	ENTITY_TO_CHANCED_ASPECT.get(entityType).put(aspect, chance);
	}

	public static HashMap<ResourceLocation, Double> getAspectChancePerEntity(EntityType<?> entityType) {
    	return ENTITY_TO_CHANCED_ASPECT.getOrDefault(entityType, new HashMap<>());
	}

}
