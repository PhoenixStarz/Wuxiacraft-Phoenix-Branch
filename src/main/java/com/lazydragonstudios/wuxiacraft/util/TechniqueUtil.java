package com.lazydragonstudios.wuxiacraft.util;

import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaTechniqueAspects;
import com.lazydragonstudios.wuxiacraft.init.WuxiaBlocks;
import com.lazydragonstudios.wuxiacraft.init.WuxiaEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

public class TechniqueUtil {

	private static final HashMap<Item, HashMap<ResourceLocation, BigDecimal>> DEVOURING_ITEM_TO_REWARD = new HashMap<>();

	private static final HashMap<Block, HashMap<ResourceLocation, Double>> BLOCK_TO_CHANCED_ASPECT = new HashMap<>();

	private static final HashMap<EntityType<?>, HashMap<ResourceLocation, Double>> ENTITY_TO_CHANCED_ASPECT = new HashMap<>();

	private static final HashSet<ResourceLocation> WEAPON_ASPECTS = new HashSet<>();

	private static final HashSet<ResourceLocation> DEVOURING_ASPECTS = new HashSet<>();

	private static final HashSet<ResourceLocation> TRANSFORMATION_ASPECTS = new HashSet<>();

	public static void initDevouringData() {
		//TODO rework all this 
		addDevouringData(Items.DIRT, WuxiaElements.EARTH.getId(), new BigDecimal("0.9"));
		addDevouringData(Items.STONE, WuxiaElements.EARTH.getId(), new BigDecimal("1.4"));
		addDevouringData(Items.COBBLESTONE, WuxiaElements.EARTH.getId(), new BigDecimal("1.3"));
		addDevouringData(Items.GRASS_BLOCK, WuxiaElements.EARTH.getId(), new BigDecimal("0.7"));
		addDevouringData(Items.GRASS_BLOCK, WuxiaElements.WOOD.getId(), new BigDecimal("0.4"));
		addDevouringData(Items.COAL, WuxiaElements.EARTH.getId(), new BigDecimal("0.6"));
		addDevouringData(Items.COAL, WuxiaElements.FIRE.getId(), new BigDecimal("0.1"));
		addDevouringData(Items.CHARCOAL, WuxiaElements.WOOD.getId(), new BigDecimal("0.4"));
		addDevouringData(Items.CHARCOAL, WuxiaElements.FIRE.getId(), new BigDecimal("0.1"));
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
		addDevouringData(Items.DIAMOND, WuxiaElements.EARTH.getId(), new BigDecimal("8"));
		addDevouringData(Items.EMERALD, WuxiaElements.EARTH.getId(), new BigDecimal("6"));
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
		addBlockToAspectChanced(Blocks.SEA_PICKLE, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addBlockToAspectChanced(Blocks.CLAY, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.DROP.getId(), 1000d);
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.FLOW.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SEA_PICKLE, WuxiaTechniqueAspects.FLOW.getId(), 3000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.FLOW.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.WATERFALL.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SEA_PICKLE, WuxiaTechniqueAspects.WATERFALL.getId(), 9000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.WATERFALL.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.SPLASH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SEA_PICKLE, WuxiaTechniqueAspects.SPLASH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.SPLASH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.STREAM.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SEA_PICKLE, WuxiaTechniqueAspects.STREAM.getId(), 3000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.STREAM.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SEAGRASS, WuxiaTechniqueAspects.WAVING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SEA_PICKLE, WuxiaTechniqueAspects.WAVING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.KELP, WuxiaTechniqueAspects.WAVING.getId(), 3000d);
	
		addBlockToAspectChanced(Blocks.GRASS_BLOCK, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.GRASS, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.MOSS_BLOCK, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.OAK_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.CHERRY_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.OAK_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.CHERRY_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LEAVES, WuxiaTechniqueAspects.SEED.getId(), 1000d);
		addBlockToAspectChanced(Blocks.GRASS_BLOCK, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.GRASS, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.MOSS_BLOCK, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.OAK_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.CHERRY_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LEAVES, WuxiaTechniqueAspects.MOSS.getId(), 3000d);
		addBlockToAspectChanced(Blocks.OAK_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.CHERRY_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LEAVES, WuxiaTechniqueAspects.SPROUT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.CHERRY_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LOG, WuxiaTechniqueAspects.BARK.getId(), 3000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.CHERRY_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.OAK_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.CHERRY_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LEAVES, WuxiaTechniqueAspects.SWAYING.getId(), 3000d);

		addBlockToAspectChanced(Blocks.MANGROVE_ROOTS, WuxiaTechniqueAspects.ROOT.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LEAVES, WuxiaTechniqueAspects.LEAF.getId(), 9000d);
		addBlockToAspectChanced(Blocks.BIRCH_LEAVES, WuxiaTechniqueAspects.LEAF.getId(), 9000d);
		addBlockToAspectChanced(Blocks.CHERRY_LEAVES, WuxiaTechniqueAspects.LEAF.getId(), 9000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LEAVES, WuxiaTechniqueAspects.LEAF.getId(), 9000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.LEAF.getId(), 9000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LEAVES, WuxiaTechniqueAspects.LEAF.getId(), 9000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LEAVES, WuxiaTechniqueAspects.LEAF.getId(), 9000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.LICHEN.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.LICHEN.getId(), 9000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.LICHEN.getId(), 9000d);
		addBlockToAspectChanced(Blocks.CHERRY_LOG, WuxiaTechniqueAspects.LICHEN.getId(), 9000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.LICHEN.getId(), 9000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.LICHEN.getId(), 9000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.LICHEN.getId(), 9000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LOG, WuxiaTechniqueAspects.LICHEN.getId(), 9000d);

		addBlockToAspectChanced(Blocks.ALLIUM, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.AZURE_BLUET, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.BLUE_ORCHID, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.CORNFLOWER, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.DANDELION, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.LILY_OF_THE_VALLEY, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.ORANGE_TULIP, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.OXEYE_DAISY, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.PINK_TULIP, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.POPPY, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.RED_TULIP, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.TORCHFLOWER, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.WHITE_TULIP, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.WITHER_ROSE, WuxiaTechniqueAspects.FLOWER.getId(), 9000d);
		addBlockToAspectChanced(Blocks.ALLIUM, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.AZURE_BLUET, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.BLUE_ORCHID, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.CORNFLOWER, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.DANDELION, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.LILY_OF_THE_VALLEY, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.ORANGE_TULIP, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.OXEYE_DAISY, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.PINK_TULIP, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.POPPY, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.RED_TULIP, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.TORCHFLOWER, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.WHITE_TULIP, WuxiaTechniqueAspects.STEM.getId(), 27000d);
		addBlockToAspectChanced(Blocks.WITHER_ROSE, WuxiaTechniqueAspects.STEM.getId(), 27000d);

		addBlockToAspectChanced(Blocks.SNOW, WuxiaTechniqueAspects.SNOW.getId(), 90000d);
		addBlockToAspectChanced(Blocks.SNOW_BLOCK, WuxiaTechniqueAspects.SNOW.getId(), 90000d);
		addBlockToAspectChanced(Blocks.BLUE_ICE, WuxiaTechniqueAspects.SNOW.getId(), 90000d);
		addBlockToAspectChanced(Blocks.PACKED_ICE, WuxiaTechniqueAspects.SNOW.getId(), 90000d);
		addBlockToAspectChanced(Blocks.ICE, WuxiaTechniqueAspects.SNOW.getId(), 90000d);

		addBlockToAspectChanced(Blocks.COAL_ORE, WuxiaTechniqueAspects.CHARCOAL.getId(), 9000d);
		addBlockToAspectChanced(Blocks.COAL_BLOCK, WuxiaTechniqueAspects.CHARCOAL.getId(), 9000d);

		addBlockToAspectChanced(Blocks.DIAMOND_ORE, WuxiaTechniqueAspects.DIAMOND_CONSTRUCT.getId(), 10000000d);
		addBlockToAspectChanced(Blocks.DIAMOND_BLOCK, WuxiaTechniqueAspects.DIAMOND_CONSTRUCT.getId(), 100000000d);

		addBlockToAspectChanced(Blocks.GLOWSTONE, WuxiaTechniqueAspects.STARRY_BATH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.GLOWSTONE, WuxiaTechniqueAspects.STARLIGHT_BATH.getId(), 9000d);
		addBlockToAspectChanced(Blocks.GLOWSTONE, WuxiaTechniqueAspects.LUMEN.getId(), 9000d);
		addBlockToAspectChanced(Blocks.GLOWSTONE, WuxiaTechniqueAspects.SHINE.getId(), 9000d);
		addBlockToAspectChanced(Blocks.GLOWSTONE, WuxiaTechniqueAspects.FLARE.getId(), 9000d);
		addBlockToAspectChanced(Blocks.GLOW_LICHEN, WuxiaTechniqueAspects.STARRY_BATH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.GLOW_LICHEN, WuxiaTechniqueAspects.STARLIGHT_BATH.getId(), 9000d);
		addBlockToAspectChanced(Blocks.GLOW_LICHEN, WuxiaTechniqueAspects.LUMEN.getId(), 9000d);
		addBlockToAspectChanced(Blocks.GLOW_LICHEN, WuxiaTechniqueAspects.SHINE.getId(), 9000d);
		addBlockToAspectChanced(Blocks.GLOW_LICHEN, WuxiaTechniqueAspects.FLARE.getId(), 9000d);

		addBlockToAspectChanced(Blocks.SCULK, WuxiaTechniqueAspects.SHADOW_BATH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SCULK, WuxiaTechniqueAspects.DUSK_BATH.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SCULK, WuxiaTechniqueAspects.DIM.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SCULK, WuxiaTechniqueAspects.GLOOM.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SCULK, WuxiaTechniqueAspects.ECLIPSE.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SCULK_VEIN, WuxiaTechniqueAspects.SHADOW_BATH.getId(), 3000d);
		addBlockToAspectChanced(Blocks.SCULK_VEIN, WuxiaTechniqueAspects.DUSK_BATH.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SCULK_VEIN, WuxiaTechniqueAspects.DIM.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SCULK_VEIN, WuxiaTechniqueAspects.GLOOM.getId(), 9000d);
		addBlockToAspectChanced(Blocks.SCULK_VEIN, WuxiaTechniqueAspects.ECLIPSE.getId(), 9000d);
				
		addBlockToAspectChanced(Blocks.EMERALD_ORE, WuxiaTechniqueAspects.BREEZE.getId(), 3000d);
		addBlockToAspectChanced(Blocks.EMERALD_ORE, WuxiaTechniqueAspects.GUST.getId(), 9000d);
		addBlockToAspectChanced(Blocks.EMERALD_ORE, WuxiaTechniqueAspects.AIRFLOW.getId(), 9000d);
		addBlockToAspectChanced(Blocks.EMERALD_ORE, WuxiaTechniqueAspects.GALE.getId(), 9000d);
		addBlockToAspectChanced(Blocks.EMERALD_ORE, WuxiaTechniqueAspects.DRAFT.getId(), 9000d);		

		addBlockToAspectChanced(Blocks.LILY_OF_THE_VALLEY, WuxiaTechniqueAspects.VENOM.getId(), 3000d);
		addBlockToAspectChanced(Blocks.LILY_OF_THE_VALLEY, WuxiaTechniqueAspects.MIASMA.getId(), 9000d);
		addBlockToAspectChanced(Blocks.LILY_OF_THE_VALLEY, WuxiaTechniqueAspects.CORRUPTION.getId(), 9000d);
		addBlockToAspectChanced(Blocks.LILY_OF_THE_VALLEY, WuxiaTechniqueAspects.MALIGNANCE.getId(), 9000d);
		addBlockToAspectChanced(Blocks.LILY_OF_THE_VALLEY, WuxiaTechniqueAspects.CORROSION.getId(), 9000d);
		addBlockToAspectChanced(Blocks.WITHER_ROSE, WuxiaTechniqueAspects.VENOM.getId(), 3000d);
		addBlockToAspectChanced(Blocks.WITHER_ROSE, WuxiaTechniqueAspects.MIASMA.getId(), 9000d);
		addBlockToAspectChanced(Blocks.WITHER_ROSE, WuxiaTechniqueAspects.CORRUPTION.getId(), 9000d);
		addBlockToAspectChanced(Blocks.WITHER_ROSE, WuxiaTechniqueAspects.MALIGNANCE.getId(), 9000d);
		addBlockToAspectChanced(Blocks.WITHER_ROSE, WuxiaTechniqueAspects.CORROSION.getId(), 9000d);

		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_1.get(), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), 100d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_2.get(), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), 80d);
		addBlockToAspectChanced(WuxiaBlocks.DEEPSLATE_SPIRIT_STONE_VEIN_2.get(), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), 80d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_3.get(), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), 60d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_4.get(), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), 40d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_5.get(), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), 20d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_1.get(), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), 100d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_2.get(), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), 80d);
		addBlockToAspectChanced(WuxiaBlocks.DEEPSLATE_SPIRIT_STONE_VEIN_2.get(), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), 80d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_3.get(), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), 60d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_4.get(), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), 40d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_5.get(), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), 20d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_1.get(), WuxiaTechniqueAspects.BODY_GATHERING.getId(), 100d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_2.get(), WuxiaTechniqueAspects.BODY_GATHERING.getId(), 80d);
		addBlockToAspectChanced(WuxiaBlocks.DEEPSLATE_SPIRIT_STONE_VEIN_2.get(), WuxiaTechniqueAspects.BODY_GATHERING.getId(), 80d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_3.get(), WuxiaTechniqueAspects.BODY_GATHERING.getId(), 60d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_4.get(), WuxiaTechniqueAspects.BODY_GATHERING.getId(), 40d);
		addBlockToAspectChanced(WuxiaBlocks.SPIRIT_STONE_VEIN_5.get(), WuxiaTechniqueAspects.BODY_GATHERING.getId(), 20d);

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
		addEntityToAspectChanced(EntityType.BAT, WuxiaTechniqueAspects.DUST.getId(), 1000d);
		addEntityToAspectChanced(EntityType.BAT, WuxiaTechniqueAspects.DIRT.getId(), 3000d);
		addEntityToAspectChanced(EntityType.BAT, WuxiaTechniqueAspects.PEBBLES.getId(), 9000d);
		addEntityToAspectChanced(EntityType.BAT, WuxiaTechniqueAspects.HARDENING.getId(), 3000d);
		addEntityToAspectChanced(EntityType.BAT, WuxiaTechniqueAspects.TREMOR.getId(), 3000d);
		addEntityToAspectChanced(EntityType.BAT, WuxiaTechniqueAspects.STILLNESS.getId(), 3000d);
	
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
	
		addEntityToAspectChanced(EntityType.ZOMBIE, WuxiaTechniqueAspects.DEVOURING.getId(), 9000d);
		addEntityToAspectChanced(EntityType.HUSK, WuxiaTechniqueAspects.DEVOURING.getId(), 9000d);
		addEntityToAspectChanced(EntityType.ZOGLIN, WuxiaTechniqueAspects.DEVOURING.getId(), 9000d);
		
		addEntityToAspectChanced(EntityType.FOX, WuxiaTechniqueAspects.KITSUNE_TRANSFORMATION.getId(), 1000d);
		addEntityToAspectChanced(EntityType.ENDER_DRAGON, WuxiaTechniqueAspects.DRAGON_TRANSFORMATION.getId(), 10d);
		
		addEntityToAspectChanced(EntityType.ENDER_DRAGON, WuxiaTechniqueAspects.SPACE_DETECTION.getId(), 10d);
		addEntityToAspectChanced(EntityType.ENDER_DRAGON, WuxiaTechniqueAspects.SPACE_TEAR.getId(), 30d);
		addEntityToAspectChanced(EntityType.ENDER_DRAGON, WuxiaTechniqueAspects.SPATIAL_TEMPERING.getId(), 30d);
		addEntityToAspectChanced(EntityType.ENDER_DRAGON, WuxiaTechniqueAspects.SPATIAL_AMPLIFICATION.getId(), 30d);
		addEntityToAspectChanced(EntityType.ENDERMAN, WuxiaTechniqueAspects.SPACE_DETECTION.getId(), 100000d);
		addEntityToAspectChanced(EntityType.ENDERMAN, WuxiaTechniqueAspects.SPACE_TEAR.getId(), 300000d);
		addEntityToAspectChanced(EntityType.ENDERMAN, WuxiaTechniqueAspects.SPATIAL_TEMPERING.getId(), 300000d);
		addEntityToAspectChanced(EntityType.ENDERMAN, WuxiaTechniqueAspects.SPATIAL_AMPLIFICATION.getId(), 300000d);

		addEntityToAspectChanced(EntityType.ZOMBIE_VILLAGER, WuxiaTechniqueAspects.BARK.getId(), 1000d);
		addEntityToAspectChanced(EntityType.SKELETON, WuxiaTechniqueAspects.HARDENING.getId(), 1000d);

		addEntityToAspectChanced(EntityType.SNOW_GOLEM, WuxiaTechniqueAspects.SNOW.getId(), 1000d);
		
		addEntityToAspectChanced(EntityType.PHANTOM, WuxiaTechniqueAspects.BREEZE.getId(), 3000d);
		addEntityToAspectChanced(EntityType.PHANTOM, WuxiaTechniqueAspects.GUST.getId(), 9000d);
		addEntityToAspectChanced(EntityType.PHANTOM, WuxiaTechniqueAspects.AIRFLOW.getId(), 9000d);
		addEntityToAspectChanced(EntityType.PHANTOM, WuxiaTechniqueAspects.GALE.getId(), 9000d);
		addEntityToAspectChanced(EntityType.PHANTOM, WuxiaTechniqueAspects.DRAFT.getId(), 9000d);		
		addEntityToAspectChanced(EntityType.GHAST, WuxiaTechniqueAspects.BREEZE.getId(), 3000d);
		addEntityToAspectChanced(EntityType.GHAST, WuxiaTechniqueAspects.GUST.getId(), 9000d);
		addEntityToAspectChanced(EntityType.GHAST, WuxiaTechniqueAspects.AIRFLOW.getId(), 9000d);
		addEntityToAspectChanced(EntityType.GHAST, WuxiaTechniqueAspects.GALE.getId(), 9000d);
		addEntityToAspectChanced(EntityType.GHAST, WuxiaTechniqueAspects.DRAFT.getId(), 9000d);
		
		addEntityToAspectChanced(EntityType.CAVE_SPIDER, WuxiaTechniqueAspects.VENOM.getId(), 3000d);
		addEntityToAspectChanced(EntityType.CAVE_SPIDER, WuxiaTechniqueAspects.MIASMA.getId(), 9000d);
		addEntityToAspectChanced(EntityType.CAVE_SPIDER, WuxiaTechniqueAspects.CORRUPTION.getId(), 9000d);
		addEntityToAspectChanced(EntityType.CAVE_SPIDER, WuxiaTechniqueAspects.MALIGNANCE.getId(), 9000d);
		addEntityToAspectChanced(EntityType.CAVE_SPIDER, WuxiaTechniqueAspects.CORROSION.getId(), 9000d);

		addEntityToAspectChanced(EntityType.ALLAY, WuxiaTechniqueAspects.STARRY_BATH.getId(), 3000d);
		addEntityToAspectChanced(EntityType.VEX, WuxiaTechniqueAspects.STARLIGHT_BATH.getId(), 9000d);
		addEntityToAspectChanced(EntityType.ALLAY, WuxiaTechniqueAspects.LUMEN.getId(), 9000d);
		addEntityToAspectChanced(EntityType.ALLAY, WuxiaTechniqueAspects.SHINE.getId(), 9000d);
		addEntityToAspectChanced(EntityType.ALLAY, WuxiaTechniqueAspects.FLARE.getId(), 9000d);

		addEntityToAspectChanced(EntityType.WARDEN, WuxiaTechniqueAspects.SHADOW_BATH.getId(), 300d);
		addEntityToAspectChanced(EntityType.WARDEN, WuxiaTechniqueAspects.DUSK_BATH.getId(), 900d);
		addEntityToAspectChanced(EntityType.WARDEN, WuxiaTechniqueAspects.DIM.getId(), 900d);
		addEntityToAspectChanced(EntityType.WARDEN, WuxiaTechniqueAspects.GLOOM.getId(), 900d);
		addEntityToAspectChanced(EntityType.WARDEN, WuxiaTechniqueAspects.ECLIPSE.getId(), 900d);

		addEntityToAspectChanced(EntityType.SNIFFER, WuxiaTechniqueAspects.TIME_DETECTION.getId(), 10000d);
		addEntityToAspectChanced(EntityType.SNIFFER, WuxiaTechniqueAspects.TEMPORAL_TEMPERING.getId(), 30000d);
		addEntityToAspectChanced(EntityType.SNIFFER, WuxiaTechniqueAspects.TIME_TEAR.getId(), 30000d);
		addEntityToAspectChanced(EntityType.SNIFFER, WuxiaTechniqueAspects.TEMPORAL_AMPLIFICATION.getId(), 30000d);

		addEntityToAspectChanced(WuxiaEntities.SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), 80d);
		addEntityToAspectChanced(WuxiaEntities.DESERT_SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), 60d);
		addEntityToAspectChanced(WuxiaEntities.RED_SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), 40d);
		addEntityToAspectChanced(WuxiaEntities.WHITE_SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), 20d);
		addEntityToAspectChanced(WuxiaEntities.SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), 80d);
		addEntityToAspectChanced(WuxiaEntities.DESERT_SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), 60d);
		addEntityToAspectChanced(WuxiaEntities.RED_SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), 40d);
		addEntityToAspectChanced(WuxiaEntities.WHITE_SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), 20d);
		addEntityToAspectChanced(WuxiaEntities.SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.BODY_GATHERING.getId(), 80d);
		addEntityToAspectChanced(WuxiaEntities.DESERT_SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.BODY_GATHERING.getId(), 60d);
		addEntityToAspectChanced(WuxiaEntities.RED_SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.BODY_GATHERING.getId(), 40d);
		addEntityToAspectChanced(WuxiaEntities.WHITE_SNAKE_ENTITY_TYPE.get(), WuxiaTechniqueAspects.BODY_GATHERING.getId(), 20d);

	}

	public static void initTransformationAspects() {
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.KITSUNE_TRANSFORMATION.getId());
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.LIGHT_KITSUNE_TRANSFORMATION.getId());
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.SPATIAL_KITSUNE_TRANSFORMATION.getId());
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.DRAGON_TRANSFORMATION.getId());
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.AZURE_DRAGON_TRANSFORMATION.getId());
	}

	public static void initWeaponTechniques() {
		WEAPON_ASPECTS.add(WuxiaTechniqueAspects.BASIC_SWORD_SET.getId());
		WEAPON_ASPECTS.add(WuxiaTechniqueAspects.SWORD_QI_GATHERING.getId());
		WEAPON_ASPECTS.add(WuxiaTechniqueAspects.MAGICAL_SWORD_GLINT.getId());
	}
	
	public static void initDevouringAspects() {
		DEVOURING_ASPECTS.add(WuxiaTechniqueAspects.DEVOURING.getId());
		DEVOURING_ASPECTS.add(WuxiaTechniqueAspects.CONSUMPTION.getId());
		DEVOURING_ASPECTS.add(WuxiaTechniqueAspects.GLUTTONY.getId());
	}

	public static HashSet<ResourceLocation> getWeaponAspects() {
		return WEAPON_ASPECTS;
	}

	public static HashSet<ResourceLocation> getDevouringAspects() {
		return DEVOURING_ASPECTS;
	}

	public static HashSet<ResourceLocation> getTransformationAspects() {
		return TRANSFORMATION_ASPECTS;
	}

	public static void addBlockToAspectChanced(Block block, ResourceLocation aspect, double chance) {
		BLOCK_TO_CHANCED_ASPECT.putIfAbsent(block, new HashMap<>());
		BLOCK_TO_CHANCED_ASPECT.get(block).put(aspect, chance);
	}

	public static void addEntityToAspectChanced(EntityType<?> entityType, ResourceLocation aspect, double chance) {
		ENTITY_TO_CHANCED_ASPECT.putIfAbsent(entityType, new HashMap<>());
		ENTITY_TO_CHANCED_ASPECT.get(entityType).put(aspect, chance);
	}

	public static HashMap<ResourceLocation, Double> getAspectChancePerBlock(Block block) {
		return BLOCK_TO_CHANCED_ASPECT.getOrDefault(block, new HashMap<>());
	}

	public static HashMap<ResourceLocation, Double> getAspectChancePerEntity(EntityType<?> entityType) {
		return ENTITY_TO_CHANCED_ASPECT.getOrDefault(entityType, new HashMap<>());
	}

	public static void addDevouringData(Item item, ResourceLocation element, BigDecimal value) {
		DEVOURING_ITEM_TO_REWARD.putIfAbsent(item, new HashMap<>());
		DEVOURING_ITEM_TO_REWARD.get(item).put(element, value);
	}

	@Nonnull
	public static HashMap<ResourceLocation, BigDecimal> getDevouringDataPerItem(Item item) {
		return DEVOURING_ITEM_TO_REWARD.getOrDefault(item, new HashMap<>());
	}

}
