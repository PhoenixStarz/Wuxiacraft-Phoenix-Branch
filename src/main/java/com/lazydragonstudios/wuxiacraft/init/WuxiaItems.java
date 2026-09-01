package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.blocks.FormationCoreBaseBlock;
import com.lazydragonstudios.wuxiacraft.blocks.FormationCoreBlock;
import com.lazydragonstudios.wuxiacraft.item.ToolMaterialTiers;
import com.lazydragonstudios.wuxiacraft.item.CelestialArmorMaterial;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.formation.FormationMaterialTier;
import com.lazydragonstudios.wuxiacraft.item.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.lang.reflect.Type;

@SuppressWarnings("unused")
public class WuxiaItems {

	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WuxiaCraft.MOD_ID);

	public static RegistryObject<Item> TECHNIQUE_INSCRIBER = ITEMS.register("technique_inscriber",
			() -> new BlockItem(WuxiaBlocks.TECHNIQUE_INSCRIBER.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_STONE_1 = ITEMS.register("spirit_stone_1",
			() -> new SpiritStone(new Item.Properties(), 0));

	public static RegistryObject<Item> SPIRIT_STONE_2 = ITEMS.register("spirit_stone_2",
			() -> new SpiritStone(new Item.Properties(), 1));

	public static RegistryObject<Item> SPIRIT_STONE_3 = ITEMS.register("spirit_stone_3",
			() -> new SpiritStone(new Item.Properties(), 2));

	public static RegistryObject<Item> SPIRIT_STONE_4 = ITEMS.register("spirit_stone_4",
			() -> new SpiritStone(new Item.Properties(), 3));

	public static RegistryObject<Item> SPIRIT_STONE_5 = ITEMS.register("spirit_stone_5",
			() -> new SpiritStone(new Item.Properties(), 4));

	public static RegistryObject<Item> SPIRIT_STONE_6 = ITEMS.register("spirit_stone_6",
			() -> new SpiritStone(new Item.Properties(), 5));

	public static RegistryObject<Item> SPIRIT_STONE_7 = ITEMS.register("spirit_stone_7",
			() -> new SpiritStone(new Item.Properties(), 6));

	public static RegistryObject<Item> SPIRIT_STONE_8 = ITEMS.register("spirit_stone_8",
			() -> new SpiritStone(new Item.Properties(), 7));

	public static RegistryObject<Item> SPIRIT_STONE_9 = ITEMS.register("spirit_stone_9",
			() -> new SpiritStone(new Item.Properties(), 8));

	public static RegistryObject<Item> PILL_1 = ITEMS.register("pill_1",
			() -> new Pill(new Item.Properties(), 0));

	public static RegistryObject<Item> PILL_2 = ITEMS.register("pill_2",
			() -> new Pill(new Item.Properties(), 1));

	public static RegistryObject<Item> PILL_3 = ITEMS.register("pill_3",
			() -> new Pill(new Item.Properties(), 2));

	public static RegistryObject<Item> PILL_4 = ITEMS.register("pill_4",
			() -> new Pill(new Item.Properties(), 3));

	public static RegistryObject<Item> PILL_5 = ITEMS.register("pill_5",
			() -> new Pill(new Item.Properties(), 4));

	public static RegistryObject<Item> PILL_6 = ITEMS.register("pill_6",
			() -> new Pill(new Item.Properties(), 5));

	public static RegistryObject<Item> PILL_7 = ITEMS.register("pill_7",
			() -> new Pill(new Item.Properties(), 6));

	public static RegistryObject<Item> PILL_8 = ITEMS.register("pill_8",
			() -> new Pill(new Item.Properties(), 7));

	public static RegistryObject<Item> PILL_9 = ITEMS.register("pill_9",
			() -> new Pill(new Item.Properties(), 8));

	public static RegistryObject<Item> SPIRIT_FRUIT_1 = ITEMS.register("spirit_fruit_1",
			() -> new SpiritFruit(WuxiaBlocks.BONSAI_1.get(), new Item.Properties(), 0));

	public static RegistryObject<Item> SPIRIT_FRUIT_2 = ITEMS.register("spirit_fruit_2",
			() -> new SpiritFruit(WuxiaBlocks.BONSAI_2.get(), new Item.Properties(), 1));

	public static RegistryObject<Item> SPIRIT_FRUIT_3 = ITEMS.register("spirit_fruit_3",
			() -> new SpiritFruit(WuxiaBlocks.BONSAI_3.get(), new Item.Properties(), 2));

	public static RegistryObject<Item> SPIRIT_FRUIT_4 = ITEMS.register("spirit_fruit_4",
			() -> new SpiritFruit(WuxiaBlocks.BONSAI_4.get(), new Item.Properties(), 3));

	public static RegistryObject<Item> SPIRIT_FRUIT_5 = ITEMS.register("spirit_fruit_5",
			() -> new SpiritFruit(WuxiaBlocks.BONSAI_5.get(), new Item.Properties(), 4));

	public static RegistryObject<Item> SPIRIT_FRUIT_6 = ITEMS.register("spirit_fruit_6",
			() -> new SpiritFruit(WuxiaBlocks.BONSAI_6.get(), new Item.Properties(), 5));

	public static RegistryObject<Item> SPIRIT_FRUIT_7 = ITEMS.register("spirit_fruit_7",
			() -> new SpiritFruit(WuxiaBlocks.BONSAI_7.get(), new Item.Properties(), 6));

	public static RegistryObject<Item> SPIRIT_FRUIT_8 = ITEMS.register("spirit_fruit_8",
			() -> new SpiritFruit(WuxiaBlocks.BONSAI_8.get(), new Item.Properties(), 7));

	public static RegistryObject<Item> SPIRIT_FRUIT_9 = ITEMS.register("spirit_fruit_9",
			() -> new SpiritFruit(WuxiaBlocks.BONSAI_9.get(), new Item.Properties(), 8));
			
	public static RegistryObject<Item> BODY_MANUAL = ITEMS.register("body_manual",
			() -> new TechniqueManual(new Item.Properties().stacksTo(1), System.BODY));

	public static RegistryObject<Item> DIVINE_MANUAL = ITEMS.register("divine_manual",
			() -> new TechniqueManual(new Item.Properties().stacksTo(1), System.DIVINE));

	public static RegistryObject<Item> ESSENCE_MANUAL = ITEMS.register("essence_manual",
			() -> new TechniqueManual(new Item.Properties().stacksTo(1), System.ESSENCE));

	public static RegistryObject<Item> STONE_RUNE_STENCIL = ITEMS.register("stone_rune_stencil",
			() -> new RuneStencil(1.5f, 0.8f, Tiers.STONE, new Item.Properties()));

	public static RegistryObject<Item> COPPER_RUNE_STENCIL = ITEMS.register("copper_rune_stencil",
			() -> new RuneStencil(1.5f, 0.8f, Tiers.IRON, new Item.Properties()));

	public static RegistryObject<Item> IRON_RUNE_STENCIL = ITEMS.register("iron_rune_stencil",
			() -> new RuneStencil(1.5f, 0.8f, Tiers.IRON, new Item.Properties()));

	public static RegistryObject<Item> GOLD_RUNE_STENCIL = ITEMS.register("gold_rune_stencil",
			() -> new RuneStencil(1.5f, 0.8f, Tiers.GOLD, new Item.Properties()));

	public static RegistryObject<Item> DIAMOND_RUNE_STENCIL = ITEMS.register("diamond_rune_stencil",
			() -> new RuneStencil(-0.5f, 0.8f, Tiers.DIAMOND, new Item.Properties()));

	public static RegistryObject<Item> NETHERITE_RUNE_STENCIL = ITEMS.register("netherite_rune_stencil",
			() -> new RuneStencil(1.5f, 0.8f, Tiers.NETHERITE, new Item.Properties()));
	
	public static RegistryObject<Item> CELESTIAL_IRON_RUNE_STENCIL = ITEMS.register("celestial_iron_rune_stencil",
			() -> new RuneStencil(0f, 0.8f, ToolMaterialTiers.CELESTIAL_IRON, new Item.Properties()));

	public static RegistryObject<Item> STONE_FORMATION_BADGE = ITEMS.register("stone_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties()));

	public static RegistryObject<Item> COPPER_FORMATION_BADGE = ITEMS.register("copper_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties()));

	public static RegistryObject<Item> IRON_FORMATION_BADGE = ITEMS.register("iron_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties()));

	public static RegistryObject<Item> GOLD_FORMATION_BADGE = ITEMS.register("gold_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties()));

	public static RegistryObject<Item> LAPIS_FORMATION_BADGE = ITEMS.register("lapis_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties()));

	public static RegistryObject<Item> DIAMOND_FORMATION_BADGE = ITEMS.register("diamond_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties()));

	public static RegistryObject<Item> EMERALD_FORMATION_BADGE = ITEMS.register("emerald_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties()));

	public static RegistryObject<Item> NETHERITE_FORMATION_BADGE = ITEMS.register("netherite_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties()));
			
	public static RegistryObject<Item> CELESTIAL_IRON_FORMATION_BADGE = ITEMS.register("celestial_iron_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_1 = ITEMS.register("spirit_stone_vein_1",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_1.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_2 = ITEMS.register("spirit_stone_vein_2",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_2.get(), new Item.Properties()));

	public static RegistryObject<Item> DEEPSLATE_SPIRIT_STONE_VEIN_2 = ITEMS.register("deepslate_spirit_stone_vein_2",
			() -> new BlockItem(WuxiaBlocks.DEEPSLATE_SPIRIT_STONE_VEIN_2.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_3 = ITEMS.register("spirit_stone_vein_3",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_3.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_4 = ITEMS.register("spirit_stone_vein_4",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_4.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_5 = ITEMS.register("spirit_stone_vein_5",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_5.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_6 = ITEMS.register("spirit_stone_vein_6",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_6.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_7 = ITEMS.register("spirit_stone_vein_7",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_7.get(), new Item.Properties()));

	public static RegistryObject<Item> CELESTIAL_IRON_ORE = ITEMS.register("celestial_iron_ore",
			() -> new BlockItem(WuxiaBlocks.CELESTIAL_IRON_ORE.get(), new Item.Properties()));
			
	public static RegistryObject<Item> DEEPSLATE_CELESTIAL_IRON_ORE = ITEMS.register("deepslate_celestial_iron_ore",
			() -> new BlockItem(WuxiaBlocks.DEEPSLATE_CELESTIAL_IRON_ORE.get(), new Item.Properties()));

	public static RegistryObject<Item> CELESTIAL_IRON_BLOCK = ITEMS.register("celestial_iron_block",
			() -> new BlockItem(WuxiaBlocks.CELESTIAL_IRON_BLOCK.get(), new Item.Properties()));

	public static RegistryObject<Item> RAW_CELESTIAL_IRON_BLOCK = ITEMS.register("raw_celestial_iron_block",
			() -> new BlockItem(WuxiaBlocks.RAW_CELESTIAL_IRON_BLOCK.get(), new Item.Properties()));

	public static RegistryObject<Item> CELESTIAL_IRON_INGOT = ITEMS.register("celestial_iron_ingot",
			() -> new Item(new Item.Properties()));

	public static RegistryObject<Item> RAW_CELESTIAL_IRON = ITEMS.register("raw_celestial_iron",
			() -> new Item(new Item.Properties()));

	public static RegistryObject<Item> SOUL_CORE = ITEMS.register("soul_core",
			() -> new SoulCore(new Item.Properties()));

	public static RegistryObject<Item> RUNEMAKING_TABLE = ITEMS.register("runemaking_table",
			() -> new BlockItem(WuxiaBlocks.RUNEMAKING_TABLE.get(), new Item.Properties()));

	public static RegistryObject<Item> FORMATION_CORE_BASE = ITEMS.register("formation_core_base",
			() -> new BlockItem(WuxiaBlocks.FORMATION_CORE_BASE.get(), new Item.Properties()));

	public static RegistryObject<Item> STONE_FORMATION_CORE = ITEMS.register("stone_formation_core",
			() -> new BlockItem(WuxiaBlocks.STONE_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> COPPER_FORMATION_CORE = ITEMS.register("copper_formation_core",
			() -> new BlockItem(WuxiaBlocks.COPPER_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> IRON_FORMATION_CORE = ITEMS.register("iron_formation_core",
			() -> new BlockItem(WuxiaBlocks.IRON_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> LAPIS_FORMATION_CORE = ITEMS.register("lapis_formation_core",
			() -> new BlockItem(WuxiaBlocks.LAPIS_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> GOLD_FORMATION_CORE = ITEMS.register("gold_formation_core",
			() -> new BlockItem(WuxiaBlocks.GOLD_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> DIAMOND_FORMATION_CORE = ITEMS.register("diamond_formation_core",
			() -> new BlockItem(WuxiaBlocks.DIAMOND_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> EMERALD_FORMATION_CORE = ITEMS.register("emerald_formation_core",
			() -> new BlockItem(WuxiaBlocks.EMERALD_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> NETHERITE_FORMATION_CORE = ITEMS.register("netherite_formation_core",
			() -> new BlockItem(WuxiaBlocks.NETHERITE_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> CELESTIAL_IRON_FORMATION_CORE = ITEMS.register("celestial_iron_formation_core",
			() -> new BlockItem(WuxiaBlocks.CELESTIAL_IRON_FORMATION_CORE.get(), new Item.Properties()));

	public static final RegistryObject<Item> CELESTIAL_SWORD = ITEMS.register("celestial_sword", 
			() -> new SwordItem(ToolMaterialTiers.CELESTIAL_IRON, 2, -2.4f, new Item.Properties()));

	public static final RegistryObject<Item> CELESTIAL_PICKAXE = ITEMS.register("celestial_pickaxe",  
			() -> new PickaxeItem(ToolMaterialTiers.CELESTIAL_IRON, 0, -2.8f, new Item.Properties()));

	public static final RegistryObject<Item> CELESTIAL_AXE = ITEMS.register("celestial_axe",  
			() -> new AxeItem(ToolMaterialTiers.CELESTIAL_IRON, 4, -3.0f, new Item.Properties()));

	public static final RegistryObject<Item> CELESTIAL_SHOVEL = ITEMS.register("celestial_shovel",  
			() -> new ShovelItem(ToolMaterialTiers.CELESTIAL_IRON, 0, -2.8f, new Item.Properties()));

	public static final RegistryObject<Item> CELESTIAL_HOE = ITEMS.register("celestial_hoe",  
			() -> new HoeItem(ToolMaterialTiers.CELESTIAL_IRON, 0, -2.0f, new Item.Properties()));
			
	public static final RegistryObject<Item> CELESTIAL_HELMET = ITEMS.register("celestial_helmet",  
            () -> new ArmorItem(CelestialArmorMaterial.CELESTIAL_IRON, ArmorItem.Type.HELMET, new Item.Properties()));

	public static final RegistryObject<Item> CELESTIAL_CHESTPLATE = ITEMS.register("celestial_chestplate",  
            () -> new ArmorItem(CelestialArmorMaterial.CELESTIAL_IRON, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

	public static final RegistryObject<Item> CELESTIAL_LEGGINGS = ITEMS.register("celestial_leggings",  
            () -> new ArmorItem(CelestialArmorMaterial.CELESTIAL_IRON, ArmorItem.Type.LEGGINGS, new Item.Properties()));

	public static final RegistryObject<Item> CELESTIAL_BOOTS = ITEMS.register("celestial_boots",  
            () -> new ArmorItem(CelestialArmorMaterial.CELESTIAL_IRON, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_CRYSTAL_BLOCK = ITEMS.register("spirit_crystal_block",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_CRYSTAL_BLOCK.get(), new Item.Properties()));

	public static RegistryObject<Item> BUDDING_SPIRIT_CRYSTAL_BLOCK = ITEMS.register("budding_spirit_crystal_block",
			() -> new BlockItem(WuxiaBlocks.BUDDING_SPIRIT_CRYSTAL_BLOCK.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_CRYSTAL_CLUSTER_1 = ITEMS.register("spirit_crystal_cluster_1",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_CRYSTAL_CLUSTER_1.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_CRYSTAL_CLUSTER_2 = ITEMS.register("spirit_crystal_cluster_2",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_CRYSTAL_CLUSTER_2.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_CRYSTAL_CLUSTER_3 = ITEMS.register("spirit_crystal_cluster_3",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_CRYSTAL_CLUSTER_3.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_CRYSTAL_CLUSTER_4 = ITEMS.register("spirit_crystal_cluster_4",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_CRYSTAL_CLUSTER_4.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_CRYSTAL_CLUSTER_5 = ITEMS.register("spirit_crystal_cluster_5",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_CRYSTAL_CLUSTER_5.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_CRYSTAL_CLUSTER_6 = ITEMS.register("spirit_crystal_cluster_6",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_CRYSTAL_CLUSTER_6.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_CRYSTAL_CLUSTER_7 = ITEMS.register("spirit_crystal_cluster_7",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_CRYSTAL_CLUSTER_7.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_CRYSTAL_CLUSTER_8 = ITEMS.register("spirit_crystal_cluster_8",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_CRYSTAL_CLUSTER_8.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_CRYSTAL_CLUSTER_9 = ITEMS.register("spirit_crystal_cluster_9",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_CRYSTAL_CLUSTER_9.get(), new Item.Properties()));

	public static HashMap<FormationMaterialTier, RegistryObject<Item>> GENERATION_RUNES = new HashMap<>();

	public static HashMap<FormationMaterialTier, RegistryObject<Item>> BARRIER_RUNES = new HashMap<>();

	public static HashMap<System, HashMap<FormationMaterialTier, RegistryObject<Item>>> ENERGY_RUNES = new HashMap<>();

	public static HashMap<System, HashMap<FormationMaterialTier, RegistryObject<Item>>> CULTIVATION_RUNES = new HashMap<>();

	static {
		for (var material : FormationMaterialTier.values()) {
			GENERATION_RUNES.put(material, ITEMS.register(material.name().toLowerCase() + "_generation_rune",
					() -> new BlockItem(WuxiaBlocks.GENERATION_RUNES.get(material).get(), new Item.Properties())
			));
			BARRIER_RUNES.put(material, ITEMS.register(material.name().toLowerCase() + "_barrier_rune",
					() -> new BlockItem(WuxiaBlocks.BARRIER_RUNES.get(material).get(), new Item.Properties())
			));
			for (var system : System.values()) {
				ENERGY_RUNES.putIfAbsent(system, new HashMap<>());
				CULTIVATION_RUNES.putIfAbsent(system, new HashMap<>());
				ENERGY_RUNES.get(system).put(material, ITEMS.register(system.name().toLowerCase() + "_" + material.name().toLowerCase() + "_energy_rune",
						() -> new BlockItem(WuxiaBlocks.ENERGY_RUNES.get(system).get(material).get(), new Item.Properties())
				));
				CULTIVATION_RUNES.get(system).put(material, ITEMS.register(system.name().toLowerCase() + "_" + material.name().toLowerCase() + "_cultivation_rune",
						() -> new BlockItem(WuxiaBlocks.CULTIVATION_RUNES.get(system).get(material).get(), new Item.Properties())
				));
			}
		}
	}

	// Test Item
	public static RegistryObject<Item> SPATIAL_RING = ITEMS.register("spatial_ring",
			() -> new SpatialItem(new Item.Properties(), 3, 9));

	public static RegistryObject<Item> SNAKE_SPAWN_EGG = ITEMS.register("snake_spawn_egg",
			() -> new ForgeSpawnEggItem(WuxiaEntities.SNAKE_ENTITY_TYPE::get, 2014323, 16514950, new Item.Properties())
	);

	public static RegistryObject<Item> DESERT_SNAKE_SPAWN_EGG = ITEMS.register("desert_snake_spawn_egg",
			() -> new ForgeSpawnEggItem(WuxiaEntities.DESERT_SNAKE_ENTITY_TYPE::get, 11718965, 16514950, new Item.Properties())
	);
	
	public static RegistryObject<Item> RED_SNAKE_SPAWN_EGG = ITEMS.register("red_snake_spawn_egg",
			() -> new ForgeSpawnEggItem(WuxiaEntities.RED_SNAKE_ENTITY_TYPE::get, 16722496, 16514950, new Item.Properties())
	);
	
	public static RegistryObject<Item> BLUE_SNAKE_SPAWN_EGG = ITEMS.register("blue_snake_spawn_egg",
			() -> new ForgeSpawnEggItem(WuxiaEntities.BLUE_SNAKE_ENTITY_TYPE::get, 38091, 16514950, new Item.Properties())
	);
	
	public static RegistryObject<Item> WHITE_SNAKE_SPAWN_EGG = ITEMS.register("white_snake_spawn_egg",
			() -> new ForgeSpawnEggItem(WuxiaEntities.WHITE_SNAKE_ENTITY_TYPE::get, 14803425, 16514950, new Item.Properties())
	);
	
	public static RegistryObject<Item> BLACK_SNAKE_SPAWN_EGG = ITEMS.register("black_snake_spawn_egg",
			() -> new ForgeSpawnEggItem(WuxiaEntities.BLACK_SNAKE_ENTITY_TYPE::get, 0, 16514950, new Item.Properties())
	);

}
