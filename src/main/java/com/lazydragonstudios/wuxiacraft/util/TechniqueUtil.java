package com.lazydragonstudios.wuxiacraft.util;

import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaTechniqueAspects;
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

	private static final HashSet<ResourceLocation> TRANSFORMATION_ASPECTS = new HashSet<>();

	public static void initDevouringData() {
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
		addDevouringData(Items.ACACIA_WOOD, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.BIRCH_WOOD, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.DARK_OAK_WOOD, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.JUNGLE_WOOD, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.OAK_WOOD, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.SPRUCE_WOOD, WuxiaElements.WOOD.getId(), new BigDecimal("3"));
		addDevouringData(Items.SPRUCE_WOOD, WuxiaElements.WOOD.getId(), new BigDecimal("0.5"));
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
		addDevouringData(Items.DIAMOND, WuxiaElements.EARTH.getId(), new BigDecimal("32"));
		addDevouringData(Items.EMERALD, WuxiaElements.EARTH.getId(), new BigDecimal("38"));
	}

	public static void initChancedAspectsBlocks() {
		addBlockToAspectChanced(Blocks.IRON_ORE, WuxiaTechniqueAspects.ORE.getId(), 10000d);
		addBlockToAspectChanced(Blocks.IRON_ORE, WuxiaTechniqueAspects.MAGNETIZATION.getId(), 100000d);
		addBlockToAspectChanced(Blocks.IRON_ORE, WuxiaTechniqueAspects.SHARPNESS.getId(), 100000d);
		addBlockToAspectChanced(Blocks.IRON_ORE, WuxiaTechniqueAspects.METAL_SKIN.getId(), 100000d);
		addBlockToAspectChanced(Blocks.COPPER_ORE, WuxiaTechniqueAspects.ORE.getId(), 50000d);
		addBlockToAspectChanced(Blocks.COPPER_ORE, WuxiaTechniqueAspects.MAGNETIZATION.getId(), 100000d);
		addBlockToAspectChanced(Blocks.COPPER_ORE, WuxiaTechniqueAspects.SHARPNESS.getId(), 100000d);
		addBlockToAspectChanced(Blocks.COPPER_ORE, WuxiaTechniqueAspects.METAL_SKIN.getId(), 100000d);
		addBlockToAspectChanced(Blocks.MOSS_BLOCK, WuxiaTechniqueAspects.MOSS.getId(), 10000d);
		addBlockToAspectChanced(Blocks.MOSS_BLOCK, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.MOSS_BLOCK, WuxiaTechniqueAspects.BARK.getId(), 100000d);
		addBlockToAspectChanced(Blocks.MOSS_BLOCK, WuxiaTechniqueAspects.SWAYING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.MOSSY_COBBLESTONE, WuxiaTechniqueAspects.MOSS.getId(), 5000000d);
		addBlockToAspectChanced(Blocks.MOSSY_COBBLESTONE, WuxiaTechniqueAspects.BRANCHING.getId(), 5000000d);
		addBlockToAspectChanced(Blocks.MOSSY_COBBLESTONE, WuxiaTechniqueAspects.BARK.getId(), 5000000d);
		addBlockToAspectChanced(Blocks.MOSSY_COBBLESTONE, WuxiaTechniqueAspects.SWAYING.getId(), 5000000d);
		addBlockToAspectChanced(Blocks.MOSSY_COBBLESTONE, WuxiaTechniqueAspects.MOSS.getId(), 10000d);
		addBlockToAspectChanced(Blocks.INFESTED_MOSSY_STONE_BRICKS, WuxiaTechniqueAspects.MOSS.getId(), 20000d);
		addBlockToAspectChanced(Blocks.OXEYE_DAISY, WuxiaTechniqueAspects.FLOWER.getId(), 30000d);
		addBlockToAspectChanced(Blocks.OXEYE_DAISY, WuxiaTechniqueAspects.STEM.getId(), 5000000d);
		addBlockToAspectChanced(Blocks.DANDELION, WuxiaTechniqueAspects.FLOWER.getId(), 30000d);
		addBlockToAspectChanced(Blocks.DANDELION, WuxiaTechniqueAspects.STEM.getId(), 5000000d);
		addBlockToAspectChanced(Blocks.POPPY, WuxiaTechniqueAspects.FLOWER.getId(), 30000d);
		addBlockToAspectChanced(Blocks.POPPY, WuxiaTechniqueAspects.STEM.getId(), 5000000d);
		addBlockToAspectChanced(Blocks.ORANGE_TULIP, WuxiaTechniqueAspects.FLOWER.getId(), 30000d);
		addBlockToAspectChanced(Blocks.ORANGE_TULIP, WuxiaTechniqueAspects.STEM.getId(), 5000000d);
		addBlockToAspectChanced(Blocks.PINK_TULIP, WuxiaTechniqueAspects.FLOWER.getId(), 30000d);
		addBlockToAspectChanced(Blocks.PINK_TULIP, WuxiaTechniqueAspects.STEM.getId(), 5000000d);
		addBlockToAspectChanced(Blocks.SUNFLOWER, WuxiaTechniqueAspects.FLOWER.getId(), 30000d);
		addBlockToAspectChanced(Blocks.SUNFLOWER, WuxiaTechniqueAspects.STEM.getId(), 5000000d);
		addBlockToAspectChanced(Blocks.NETHERRACK, WuxiaTechniqueAspects.CINDER.getId(), 10000d);
		addBlockToAspectChanced(Blocks.NETHERRACK, WuxiaTechniqueAspects.SCORCH.getId(), 10000d);
		addBlockToAspectChanced(Blocks.NETHERRACK, WuxiaTechniqueAspects.MAGIC_BURNING.getId(), 10000d);
		addBlockToAspectChanced(Blocks.NETHERRACK, WuxiaTechniqueAspects.MIND_FLARE.getId(), 10000d);
		addBlockToAspectChanced(Blocks.SOUL_SAND, WuxiaTechniqueAspects.CINDER.getId(), 10000d);
		addBlockToAspectChanced(Blocks.SOUL_SAND, WuxiaTechniqueAspects.EMBER.getId(), 50000d);
		addBlockToAspectChanced(Blocks.SOUL_SAND, WuxiaTechniqueAspects.SCORCH.getId(), 100000d);
		addBlockToAspectChanced(Blocks.SOUL_SAND, WuxiaTechniqueAspects.MAGIC_BURNING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.SOUL_SAND, WuxiaTechniqueAspects.MIND_FLARE.getId(), 100000d);
		addBlockToAspectChanced(Blocks.MAGMA_BLOCK, WuxiaTechniqueAspects.CINDER.getId(), 10000d);
		addBlockToAspectChanced(Blocks.MAGMA_BLOCK, WuxiaTechniqueAspects.EMBER.getId(), 50000d);
		addBlockToAspectChanced(Blocks.MAGMA_BLOCK, WuxiaTechniqueAspects.SCORCH.getId(), 1200000d);
		addBlockToAspectChanced(Blocks.MAGMA_BLOCK, WuxiaTechniqueAspects.MAGIC_BURNING.getId(), 1200000d);
		addBlockToAspectChanced(Blocks.MAGMA_BLOCK, WuxiaTechniqueAspects.MIND_FLARE.getId(), 400000d);
		addBlockToAspectChanced(Blocks.REDSTONE_ORE, WuxiaTechniqueAspects.SPARK.getId(), 25000d);
		addBlockToAspectChanced(Blocks.REDSTONE_ORE, WuxiaTechniqueAspects.CONDUIT.getId(), 1000000d);
		addBlockToAspectChanced(Blocks.REDSTONE_ORE, WuxiaTechniqueAspects.FLASH.getId(), 1000000d);
		addBlockToAspectChanced(Blocks.REDSTONE_ORE, WuxiaTechniqueAspects.ARC.getId(), 1000000d);
		addBlockToAspectChanced(Blocks.REDSTONE_BLOCK, WuxiaTechniqueAspects.SPARK.getId(), 250000d);
		addBlockToAspectChanced(Blocks.REDSTONE_BLOCK, WuxiaTechniqueAspects.CONDUIT.getId(), 10000000d);
		addBlockToAspectChanced(Blocks.REDSTONE_BLOCK, WuxiaTechniqueAspects.FLASH.getId(), 10000000d);
		addBlockToAspectChanced(Blocks.REDSTONE_BLOCK, WuxiaTechniqueAspects.ARC.getId(), 10000000d);
		addBlockToAspectChanced(Blocks.DIRT, WuxiaTechniqueAspects.DUST.getId(), 10000d);
		addBlockToAspectChanced(Blocks.DIRT, WuxiaTechniqueAspects.DIRT.getId(), 10000d);
		addBlockToAspectChanced(Blocks.DIRT, WuxiaTechniqueAspects.HARDENING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.DIRT, WuxiaTechniqueAspects.TREMOR.getId(), 100000d);
		addBlockToAspectChanced(Blocks.COBBLESTONE, WuxiaTechniqueAspects.DUST.getId(), 10000d);
		addBlockToAspectChanced(Blocks.COBBLESTONE, WuxiaTechniqueAspects.DIRT.getId(), 10000d);
		addBlockToAspectChanced(Blocks.COBBLESTONE, WuxiaTechniqueAspects.PEBBLES.getId(), 100000d);
		addBlockToAspectChanced(Blocks.COBBLESTONE, WuxiaTechniqueAspects.HARDENING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.COBBLESTONE, WuxiaTechniqueAspects.TREMOR.getId(), 100000d);
		addBlockToAspectChanced(Blocks.STONE, WuxiaTechniqueAspects.PEBBLES.getId(), 100000d);
		addBlockToAspectChanced(Blocks.STONE, WuxiaTechniqueAspects.STONE.getId(), 100000d);
		addBlockToAspectChanced(Blocks.STONE, WuxiaTechniqueAspects.HARDENING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.STONE, WuxiaTechniqueAspects.TREMOR.getId(), 100000d);
		addBlockToAspectChanced(Blocks.COAL_BLOCK, WuxiaTechniqueAspects.CHARCOAL.getId(), 1000000d);
		addBlockToAspectChanced(Blocks.DIAMOND_ORE, WuxiaTechniqueAspects.DIAMOND_CONSTRUCT.getId(), 10000000d);
		addBlockToAspectChanced(Blocks.DIAMOND_ORE, WuxiaTechniqueAspects.CRYSTAL.getId(), 1000000d);
		addBlockToAspectChanced(Blocks.DIAMOND_BLOCK, WuxiaTechniqueAspects.DIAMOND_CONSTRUCT.getId(), 100000000d);
		addBlockToAspectChanced(Blocks.DIAMOND_BLOCK, WuxiaTechniqueAspects.CRYSTAL.getId(), 10000000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SEED.getId(), 10000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SPROUT.getId(), 10000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.SAPLING.getId(), 10000d);
		addBlockToAspectChanced(Blocks.ACACIA_LOG, WuxiaTechniqueAspects.BARK.getId(), 100000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.CHERRY_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.SEED.getId(), 10000d);
		addBlockToAspectChanced(Blocks.CHERRY_LOG, WuxiaTechniqueAspects.SEED.getId(), 10000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.SEED.getId(), 10000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.SEED.getId(), 10000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LOG, WuxiaTechniqueAspects.SEED.getId(), 10000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.SEED.getId(), 10000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.SEED.getId(), 10000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.SPROUT.getId(), 10000d);
		addBlockToAspectChanced(Blocks.CHERRY_LOG, WuxiaTechniqueAspects.SPROUT.getId(), 10000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.SPROUT.getId(), 10000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.SPROUT.getId(), 10000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LOG, WuxiaTechniqueAspects.SPROUT.getId(), 10000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.SPROUT.getId(), 10000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.SPROUT.getId(), 10000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.SAPLING.getId(), 10000d);
		addBlockToAspectChanced(Blocks.CHERRY_LOG, WuxiaTechniqueAspects.SAPLING.getId(), 10000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.SAPLING.getId(), 10000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.SAPLING.getId(), 10000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LOG, WuxiaTechniqueAspects.SAPLING.getId(), 10000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.SAPLING.getId(), 10000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.SAPLING.getId(), 10000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.BARK.getId(), 100000d);
		addBlockToAspectChanced(Blocks.CHERRY_LOG, WuxiaTechniqueAspects.BARK.getId(), 100000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.BARK.getId(), 100000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.BARK.getId(), 100000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LOG, WuxiaTechniqueAspects.BARK.getId(), 100000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.BARK.getId(), 100000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.BARK.getId(), 100000d);
		addBlockToAspectChanced(Blocks.BIRCH_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.CHERRY_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.DARK_OAK_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.JUNGLE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.MANGROVE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.OAK_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.SPRUCE_LOG, WuxiaTechniqueAspects.BRANCHING.getId(), 100000d);
		addBlockToAspectChanced(Blocks.SNOW, WuxiaTechniqueAspects.SNOW.getId(), 10000d);
		addBlockToAspectChanced(Blocks.SNOW_BLOCK, WuxiaTechniqueAspects.SNOW.getId(), 10000d);
		addBlockToAspectChanced(Blocks.ICE, WuxiaTechniqueAspects.SNOW.getId(), 10000d);
		//TODO glowstone for light
		//TODO any crystal to earth aspects

	}

	public static void initEntityChancedAspects() {
		addEntityToAspectChanced(EntityType.FOX, WuxiaTechniqueAspects.KITSUNE_TRANSFORMATION.getId(), 100d);
		addEntityToAspectChanced(EntityType.MAGMA_CUBE, WuxiaTechniqueAspects.CINDER.getId(), 1000d);
		addEntityToAspectChanced(EntityType.MAGMA_CUBE, WuxiaTechniqueAspects.EMBER.getId(), 1000d);
		addEntityToAspectChanced(EntityType.MAGMA_CUBE, WuxiaTechniqueAspects.SCORCH.getId(), 10000d);
		addEntityToAspectChanced(EntityType.MAGMA_CUBE, WuxiaTechniqueAspects.MAGIC_BURNING.getId(), 10000d);
		addEntityToAspectChanced(EntityType.ENDER_DRAGON, WuxiaTechniqueAspects.DRAGON_TRANSFORMATION.getId(), 100d);
		addEntityToAspectChanced(EntityType.ENDER_DRAGON, WuxiaTechniqueAspects.SPACE_DETECTION.getId(), 1000d);
		addEntityToAspectChanced(EntityType.ENDER_DRAGON, WuxiaTechniqueAspects.SPACE_TEAR.getId(), 1000d);
		addEntityToAspectChanced(EntityType.ENDER_DRAGON, WuxiaTechniqueAspects.SPATIAL_TEMPERING.getId(), 1000d);
		addEntityToAspectChanced(EntityType.ENDERMAN, WuxiaTechniqueAspects.SPACE_DETECTION.getId(), 100000d);
		addEntityToAspectChanced(EntityType.ENDERMAN, WuxiaTechniqueAspects.SPACE_TEAR.getId(), 1000000d);
		addEntityToAspectChanced(EntityType.ENDERMAN, WuxiaTechniqueAspects.SPATIAL_TEMPERING.getId(), 1000000d);
		addEntityToAspectChanced(EntityType.ZOMBIE, WuxiaTechniqueAspects.BARK.getId(), 1000d);
		addEntityToAspectChanced(EntityType.ZOMBIE_VILLAGER, WuxiaTechniqueAspects.BARK.getId(), 1000d);
		addEntityToAspectChanced(EntityType.SKELETON, WuxiaTechniqueAspects.HARDENING.getId(), 1000d);
		addEntityToAspectChanced(EntityType.SNOW_GOLEM, WuxiaTechniqueAspects.SNOW.getId(), 1000d);

		// TODO add (1 + cave) * spiders to give some poison aspects
		// TODO add undead give darkness or necromancy whatever
		// TODO make villagers give demonic shit
	}

	public static void initTransformationAspects() {
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.KITSUNE_TRANSFORMATION.getId());
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.SPATIAL_KITSUNE_TRANSFORMATION.getId());
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.DRAGON_TRANSFORMATION.getId());
	}

	public static void initWeaponTechniques() {
		WEAPON_ASPECTS.add(WuxiaTechniqueAspects.BASIC_SWORD_SET.getId());
		WEAPON_ASPECTS.add(WuxiaTechniqueAspects.SWORD_QI_GATHERING.getId());
	}

	public static HashSet<ResourceLocation> getWeaponAspects() {
		return WEAPON_ASPECTS;
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
