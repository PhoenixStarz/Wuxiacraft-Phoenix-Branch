package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.item.*;
import com.lazydragonstudios.wuxiacraft.formation.FormationMaterialTier;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.HashMap;

@SuppressWarnings("unused")
public class WuxiaItems {

	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WuxiaCraft.MOD_ID);

	public static CreativeModeTab WUXIACRAFT_TAB = new CreativeModeTab("wuxiacraft") {
		@Nonnull
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(SPIRIT_STONE_1.get());
		}
	};

	public static CreativeModeTab FORMATIONS_TAB = new CreativeModeTab("wuxiacraft") {
		@Nonnull
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DIAMOND_FORMATION_CORE.get());
		}
	};

	public static RegistryObject<Item> TECHNIQUE_INSCRIBER = ITEMS.register("technique_inscriber",
			() -> new BlockItem(WuxiaBlocks.TECHNIQUE_INSCRIBER.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> SPIRIT_STONE_1 = ITEMS.register("spirit_stone_1",
			() -> new SpiritStone(new Item.Properties().tab(WUXIACRAFT_TAB), 0));

	public static RegistryObject<Item> SPIRIT_STONE_2 = ITEMS.register("spirit_stone_2",
			() -> new SpiritStone(new Item.Properties().tab(WUXIACRAFT_TAB), 1));

	public static RegistryObject<Item> SPIRIT_STONE_3 = ITEMS.register("spirit_stone_3",
			() -> new SpiritStone(new Item.Properties().tab(WUXIACRAFT_TAB), 2));

	public static RegistryObject<Item> SPIRIT_STONE_4 = ITEMS.register("spirit_stone_4",
			() -> new SpiritStone(new Item.Properties().tab(WUXIACRAFT_TAB), 3));

	public static RegistryObject<Item> SPIRIT_STONE_5 = ITEMS.register("spirit_stone_5",
			() -> new SpiritStone(new Item.Properties().tab(WUXIACRAFT_TAB), 4));

	public static RegistryObject<Item> SPIRIT_STONE_6 = ITEMS.register("spirit_stone_6",
			() -> new SpiritStone(new Item.Properties().tab(WUXIACRAFT_TAB), 5));

	public static RegistryObject<Item> SPIRIT_STONE_7 = ITEMS.register("spirit_stone_7",
			() -> new SpiritStone(new Item.Properties().tab(WUXIACRAFT_TAB), 6));

	public static RegistryObject<Item> SPIRIT_STONE_8 = ITEMS.register("spirit_stone_8",
			() -> new SpiritStone(new Item.Properties().tab(WUXIACRAFT_TAB), 7));

	public static RegistryObject<Item> SPIRIT_STONE_9 = ITEMS.register("spirit_stone_9",
			() -> new SpiritStone(new Item.Properties().tab(WUXIACRAFT_TAB), 8));

	public static RegistryObject<Item> PILL_1 = ITEMS.register("pill_1",
			() -> new Pill(new Item.Properties().tab(WUXIACRAFT_TAB), 0));

	public static RegistryObject<Item> PILL_2 = ITEMS.register("pill_2",
			() -> new Pill(new Item.Properties().tab(WUXIACRAFT_TAB), 1));

	public static RegistryObject<Item> PILL_3 = ITEMS.register("pill_3",
			() -> new Pill(new Item.Properties().tab(WUXIACRAFT_TAB), 2));

	public static RegistryObject<Item> PILL_4 = ITEMS.register("pill_4",
			() -> new Pill(new Item.Properties().tab(WUXIACRAFT_TAB), 3));

	public static RegistryObject<Item> PILL_5 = ITEMS.register("pill_5",
			() -> new Pill(new Item.Properties().tab(WUXIACRAFT_TAB), 4));

	public static RegistryObject<Item> PILL_6 = ITEMS.register("pill_6",
			() -> new Pill(new Item.Properties().tab(WUXIACRAFT_TAB), 5));

	public static RegistryObject<Item> PILL_7 = ITEMS.register("pill_7",
			() -> new Pill(new Item.Properties().tab(WUXIACRAFT_TAB), 6));

	public static RegistryObject<Item> PILL_8 = ITEMS.register("pill_8",
			() -> new Pill(new Item.Properties().tab(WUXIACRAFT_TAB), 7));

	public static RegistryObject<Item> PILL_9 = ITEMS.register("pill_9",
			() -> new Pill(new Item.Properties().tab(WUXIACRAFT_TAB), 8));

	public static RegistryObject<Item> SPRIIT_FRUIT_1 = ITEMS.register("spirit_fruit_1",
			() -> new SpiritFruit(new Item.Properties().tab(WUXIACRAFT_TAB), 0));

	public static RegistryObject<Item> SPRIIT_FRUIT_2 = ITEMS.register("spirit_fruit_2",
			() -> new SpiritFruit(new Item.Properties().tab(WUXIACRAFT_TAB), 1));

	public static RegistryObject<Item> SPRIIT_FRUIT_3 = ITEMS.register("spirit_fruit_3",
			() -> new SpiritFruit(new Item.Properties().tab(WUXIACRAFT_TAB), 2));

	public static RegistryObject<Item> BODY_MANUAL = ITEMS.register("body_manual",
			() -> new TechniqueManual(new Item.Properties().stacksTo(1), System.BODY));

	public static RegistryObject<Item> DIVINE_MANUAL = ITEMS.register("divine_manual",
			() -> new TechniqueManual(new Item.Properties().stacksTo(1), System.DIVINE));

	public static RegistryObject<Item> ESSENCE_MANUAL = ITEMS.register("essence_manual",
			() -> new TechniqueManual(new Item.Properties().stacksTo(1), System.ESSENCE));

	public static RegistryObject<Item> WOOD_RUNE_STENCIL = ITEMS.register("wood_rune_stencil",
			() -> new RuneStencil(Tiers.WOOD, new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> STONE_RUNE_STENCIL = ITEMS.register("stone_rune_stencil",
			() -> new RuneStencil(Tiers.STONE, new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> COPPER_RUNE_STENCIL = ITEMS.register("copper_rune_stencil",
			() -> new RuneStencil(Tiers.IRON, new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> IRON_RUNE_STENCIL = ITEMS.register("iron_rune_stencil",
			() -> new RuneStencil(Tiers.IRON, new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> GOLD_RUNE_STENCIL = ITEMS.register("gold_rune_stencil",
			() -> new RuneStencil(Tiers.GOLD, new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> DIAMOND_RUNE_STENCIL = ITEMS.register("diamond_rune_stencil",
			() -> new RuneStencil(Tiers.DIAMOND, new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> NETHERITE_RUNE_STENCIL = ITEMS.register("netherite_rune_stencil",
			() -> new RuneStencil(Tiers.NETHERITE, new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> COPPER_FORMATION_BADGE = ITEMS.register("copper_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> IRON_FORMATION_BADGE = ITEMS.register("iron_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> GOLD_FORMATION_BADGE = ITEMS.register("gold_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> LAPIS_FORMATION_BADGE = ITEMS.register("lapis_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> DIAMOND_FORMATION_BADGE = ITEMS.register("diamond_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> EMERALD_FORMATION_BADGE = ITEMS.register("emerald_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> NETHERITE_FORMATION_BADGE = ITEMS.register("netherite_formation_badge",
			() -> new FormationBarrierBadge(new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_1 = ITEMS.register("spirit_stone_vein_1",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_1.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> SPIRIT_STONE_VEIN_2 = ITEMS.register("spirit_stone_vein_2",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_2.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> DEEPSLATE_SPIRIT_STONE_VEIN_2 = ITEMS.register("deepslate_spirit_stone_vein_2",
			() -> new BlockItem(WuxiaBlocks.DEEPSLATE_SPIRIT_STONE_VEIN_2.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));
	
	public static RegistryObject<Item> SPIRIT_STONE_VEIN_3 = ITEMS.register("spirit_stone_vein_3",
			() -> new BlockItem(WuxiaBlocks.SPIRIT_STONE_VEIN_3.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));

		public static RegistryObject<Item> CELESTIAL_IRON_ORE = ITEMS.register("celestial_iron_ore",
			() -> new BlockItem(WuxiaBlocks.CELESTIAL_IRON_ORE.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));
			
	public static RegistryObject<Item> DEEPSLATE_CELESTIAL_IRON_ORE = ITEMS.register("deepslate_celestial_iron_ore",
			() -> new BlockItem(WuxiaBlocks.DEEPSLATE_CELESTIAL_IRON_ORE.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> CELESTIAL_IRON_BLOCK = ITEMS.register("celestial_iron_block",
			() -> new BlockItem(WuxiaBlocks.CELESTIAL_IRON_BLOCK.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> RAW_CELESTIAL_IRON_BLOCK = ITEMS.register("raw_celestial_iron_block",
			() -> new BlockItem(WuxiaBlocks.RAW_CELESTIAL_IRON_BLOCK.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> CELESTIAL_IRON_INGOT = ITEMS.register("celestial_iron_ingot",
			() -> new Item(new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> RAW_CELESTIAL_IRON = ITEMS.register("raw_celestial_iron",
			() -> new Item(new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> RUNEMAKING_TABLE = ITEMS.register("runemaking_table",
			() -> new BlockItem(WuxiaBlocks.RUNEMAKING_TABLE.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static RegistryObject<Item> FORMATION_CORE_BASE = ITEMS.register("formation_core_base",
			() -> new BlockItem(WuxiaBlocks.FORMATION_CORE_BASE.get(), new Item.Properties().tab(FORMATIONS_TAB)));

	public static RegistryObject<Item> STONE_FORMATION_CORE = ITEMS.register("stone_formation_core",
			() -> new BlockItem(WuxiaBlocks.STONE_FORMATION_CORE.get(), new Item.Properties().tab(FORMATIONS_TAB)));

	public static RegistryObject<Item> COPPER_FORMATION_CORE = ITEMS.register("copper_formation_core",
			() -> new BlockItem(WuxiaBlocks.COPPER_FORMATION_CORE.get(), new Item.Properties().tab(FORMATIONS_TAB)));

	public static RegistryObject<Item> IRON_FORMATION_CORE = ITEMS.register("iron_formation_core",
			() -> new BlockItem(WuxiaBlocks.IRON_FORMATION_CORE.get(), new Item.Properties().tab(FORMATIONS_TAB)));

	public static RegistryObject<Item> LAPIS_FORMATION_CORE = ITEMS.register("lapis_formation_core",
			() -> new BlockItem(WuxiaBlocks.LAPIS_FORMATION_CORE.get(), new Item.Properties().tab(FORMATIONS_TAB)));

	public static RegistryObject<Item> GOLD_FORMATION_CORE = ITEMS.register("gold_formation_core",
			() -> new BlockItem(WuxiaBlocks.GOLD_FORMATION_CORE.get(), new Item.Properties().tab(FORMATIONS_TAB)));

	public static RegistryObject<Item> DIAMOND_FORMATION_CORE = ITEMS.register("diamond_formation_core",
			() -> new BlockItem(WuxiaBlocks.DIAMOND_FORMATION_CORE.get(), new Item.Properties().tab(FORMATIONS_TAB)));

	public static RegistryObject<Item> EMERALD_FORMATION_CORE = ITEMS.register("emerald_formation_core",
			() -> new BlockItem(WuxiaBlocks.EMERALD_FORMATION_CORE.get(), new Item.Properties().tab(FORMATIONS_TAB)));

	public static RegistryObject<Item> DIVINE_FLAME = ITEMS.register("divine_flame",
			() -> new BlockItem(WuxiaBlocks.DIVINE_FLAME.get(), new Item.Properties().tab(WUXIACRAFT_TAB)));

	public static HashMap<FormationMaterialTier, RegistryObject<Item>> GENERATION_RUNES = new HashMap<>();
	public static HashMap<FormationMaterialTier, RegistryObject<Item>> BARRIER_RUNES = new HashMap<>();
	public static HashMap<System, HashMap<FormationMaterialTier, RegistryObject<Item>>> ENERGY_RUNES = new HashMap<>();
	public static HashMap<System, HashMap<FormationMaterialTier, RegistryObject<Item>>> CULTIVATION_RUNES = new HashMap<>();

	static {
		for (var material : FormationMaterialTier.values()) {
			GENERATION_RUNES.put(material, ITEMS.register(material.name().toLowerCase() + "_generation_rune",
					() -> new BlockItem(WuxiaBlocks.GENERATION_RUNES.get(material).get(), new Item.Properties().tab(FORMATIONS_TAB))
			));
			BARRIER_RUNES.put(material, ITEMS.register(material.name().toLowerCase() + "_barrier_rune",
					() -> new BlockItem(WuxiaBlocks.BARRIER_RUNES.get(material).get(), new Item.Properties().tab(FORMATIONS_TAB))
			));
			for (var system : System.values()) {
				ENERGY_RUNES.putIfAbsent(system, new HashMap<>());
				CULTIVATION_RUNES.putIfAbsent(system, new HashMap<>());
				ENERGY_RUNES.get(system).put(material, ITEMS.register(system.name().toLowerCase() + "_" + material.name().toLowerCase() + "_energy_rune",
						() -> new BlockItem(WuxiaBlocks.ENERGY_RUNES.get(system).get(material).get(), new Item.Properties().tab(FORMATIONS_TAB))
				));
				CULTIVATION_RUNES.get(system).put(material, ITEMS.register(system.name().toLowerCase() + "_" + material.name().toLowerCase() + "_cultivation_rune",
						() -> new BlockItem(WuxiaBlocks.CULTIVATION_RUNES.get(system).get(material).get(), new Item.Properties().tab(FORMATIONS_TAB))
				));
			}
		}
	}

	// Test Item
	public static RegistryObject<Item> SPATIAL_RING = ITEMS.register("spatial_ring",
			() -> new SpatialItem(new Item.Properties().tab(WUXIACRAFT_TAB), 3, 9));

}
