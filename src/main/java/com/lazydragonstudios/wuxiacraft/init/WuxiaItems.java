package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.blocks.FormationCoreBaseBlock;
import com.lazydragonstudios.wuxiacraft.blocks.FormationCoreBlock;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.formation.FormationMaterialTier;
import com.lazydragonstudios.wuxiacraft.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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

	public static RegistryObject<Item> BODY_MANUAL = ITEMS.register("body_manual",
			() -> new TechniqueManual(new Item.Properties().stacksTo(1), System.BODY));

	public static RegistryObject<Item> DIVINE_MANUAL = ITEMS.register("divine_manual",
			() -> new TechniqueManual(new Item.Properties().stacksTo(1), System.DIVINE));

	public static RegistryObject<Item> ESSENCE_MANUAL = ITEMS.register("essence_manual",
			() -> new TechniqueManual(new Item.Properties().stacksTo(1), System.ESSENCE));

	public static RegistryObject<Item> WOOD_RUNE_STENCIL = ITEMS.register("wood_rune_stencil",
			() -> new RuneStencil(1f, 0.8f, Tiers.WOOD, new Item.Properties()));

	public static RegistryObject<Item> STONE_RUNE_STENCIL = ITEMS.register("stone_rune_stencil",
			() -> new RuneStencil(2f, 0.8f, Tiers.STONE, new Item.Properties()));

	public static RegistryObject<Item> COPPER_RUNE_STENCIL = ITEMS.register("copper_rune_stencil",
			() -> new RuneStencil(2.5f, 0.8f, Tiers.IRON, new Item.Properties()));

	public static RegistryObject<Item> IRON_RUNE_STENCIL = ITEMS.register("iron_rune_stencil",
			() -> new RuneStencil(3f, 0.8f, Tiers.IRON, new Item.Properties()));

	public static RegistryObject<Item> GOLD_RUNE_STENCIL = ITEMS.register("gold_rune_stencil",
			() -> new RuneStencil(2f, 0.8f, Tiers.GOLD, new Item.Properties()));

	public static RegistryObject<Item> DIAMOND_RUNE_STENCIL = ITEMS.register("diamond_rune_stencil",
			() -> new RuneStencil(3.5f, 0.8f, Tiers.DIAMOND, new Item.Properties()));

	public static RegistryObject<Item> NETHERITE_RUNE_STENCIL = ITEMS.register("netherite_rune_stencil",
			() -> new RuneStencil(4.5f, 0.8f, Tiers.NETHERITE, new Item.Properties()));

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

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_1 = ITEMS.register("spirit_stone_vein_1",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_1.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_2 = ITEMS.register("spirit_stone_vein_2",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_2.get(), new Item.Properties()));

	public static RegistryObject<Item> DEEPSLATE_SPIRIT_STONE_VEIN_2 = ITEMS.register("deepslate_spirit_stone_vein_2",
			() -> new BlockItem(WuxiaBlocks.DEEPSLATE_SPIRIT_STONE_VEIN_2.get(), new Item.Properties()));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_3 = ITEMS.register("spirit_stone_vein_3",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_3.get(), new Item.Properties()));

	public static RegistryObject<Item> RUNEMAKING_TABLE = ITEMS.register("runemaking_table",
			() -> new BlockItem(WuxiaBlocks.RUNEMAKING_TABLE.get(), new Item.Properties()));

	public static RegistryObject<Item> FORMATION_CORE_BASE = ITEMS.register("formation_core_base",
			() -> new BlockItem(WuxiaBlocks.FORMATION_CORE_BASE.get(), new Item.Properties()));

	public static RegistryObject<Item> OAK_FORMATION_CORE = ITEMS.register("oak_formation_core",
			() -> new BlockItem(WuxiaBlocks.OAK_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> BIRCH_FORMATION_CORE = ITEMS.register("birch_formation_core",
			() -> new BlockItem(WuxiaBlocks.BIRCH_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> SPRUCE_FORMATION_CORE = ITEMS.register("spruce_formation_core",
			() -> new BlockItem(WuxiaBlocks.SPRUCE_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> JUNGLE_FORMATION_CORE = ITEMS.register("jungle_formation_core",
			() -> new BlockItem(WuxiaBlocks.JUNGLE_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> ACACIA_FORMATION_CORE = ITEMS.register("acacia_formation_core",
			() -> new BlockItem(WuxiaBlocks.ACACIA_FORMATION_CORE.get(), new Item.Properties()));

	public static RegistryObject<Item> DARK_OAK_FORMATION_CORE = ITEMS.register("dark_oak_formation_core",
			() -> new BlockItem(WuxiaBlocks.DARK_OAK_FORMATION_CORE.get(), new Item.Properties()));

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

	public static HashMap<String, RegistryObject<Item>> WOODEN_FORMATION_CORES_BASES = new HashMap<>();

	public static HashMap<String, RegistryObject<Item>> WOODEN_FORMATION_CORES = new HashMap<>();

	static {
		WuxiaBlocks.WOOD_TYPES.put("acacia", Blocks.ACACIA_LOG);
		WuxiaBlocks.WOOD_TYPES.put("birch", Blocks.BIRCH_LOG);
		WuxiaBlocks.WOOD_TYPES.put("cherry", Blocks.CHERRY_LOG);
		WuxiaBlocks.WOOD_TYPES.put("dark_oak", Blocks.DARK_OAK_LOG);
		WuxiaBlocks.WOOD_TYPES.put("jungle", Blocks.JUNGLE_LOG);
		WuxiaBlocks.WOOD_TYPES.put("mangrove", Blocks.MANGROVE_LOG);
		WuxiaBlocks.WOOD_TYPES.put("oak", Blocks.OAK_LOG);
		WuxiaBlocks.WOOD_TYPES.put("spruce", Blocks.SPRUCE_LOG);
		for (var wood_type : WuxiaBlocks.WOOD_TYPES.keySet()) {
			WOODEN_FORMATION_CORES_BASES.put(wood_type, ITEMS.register(wood_type + "_formation_core_base",
					() -> new BlockItem(WuxiaBlocks.WOODEN_FORMATION_CORES_BASES.get(wood_type).get(), new Item.Properties())));
			WOODEN_FORMATION_CORES.put(wood_type, ITEMS.register(wood_type + "_wooden_formation_core",
					() -> new BlockItem(WuxiaBlocks.WOODEN_FORMATION_CORES.get(wood_type).get(), new Item.Properties())));
		}
	}

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

}
