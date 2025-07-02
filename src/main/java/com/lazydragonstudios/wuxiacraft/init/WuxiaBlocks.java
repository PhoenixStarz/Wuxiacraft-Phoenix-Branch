package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.blocks.*;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.formation.FormationMaterialTier;
import com.lazydragonstudios.wuxiacraft.formation.FormationStat;
import com.lazydragonstudios.wuxiacraft.formation.FormationSystemStat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.math.BigDecimal;
import java.util.HashMap;

public class WuxiaBlocks {

	public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WuxiaCraft.MOD_ID);

	public static RegistryObject<Block> TECHNIQUE_INSCRIBER = BLOCKS.register("technique_inscriber",
			() -> new TechniqueInscriber(BlockBehaviour.Properties.of().strength(3f)));

	public static RegistryObject<Block> FORMATION_CORE_BASE = BLOCKS.register("formation_core_base",
			() -> new FormationCoreBaseBlock(BlockBehaviour.Properties.of().strength(2f), false));

	public static final HashMap<String, Block> WOOD_TYPES = new HashMap<>();

	public static HashMap<String, RegistryObject<Block>> WOODEN_FORMATION_CORES_BASES = new HashMap<>();

	public static HashMap<String, RegistryObject<Block>> WOODEN_FORMATION_CORES = new HashMap<>();

	static {
		WOOD_TYPES.put("acacia", Blocks.ACACIA_LOG);
		WOOD_TYPES.put("birch", Blocks.BIRCH_LOG);
		WOOD_TYPES.put("cherry", Blocks.CHERRY_LOG);
		WOOD_TYPES.put("dark_oak", Blocks.DARK_OAK_LOG);
		WOOD_TYPES.put("jungle", Blocks.JUNGLE_LOG);
		WOOD_TYPES.put("mangrove", Blocks.MANGROVE_LOG);
		WOOD_TYPES.put("oak", Blocks.OAK_LOG);
		WOOD_TYPES.put("spruce", Blocks.SPRUCE_LOG);
		for (var wood_type : WOOD_TYPES.keySet()) {
			WOODEN_FORMATION_CORES_BASES.put(wood_type, BLOCKS.register(wood_type + "_formation_core_base",
					() -> new FormationCoreBaseBlock(BlockBehaviour.Properties.of().strength(1f), true)));
			WOODEN_FORMATION_CORES.put(wood_type, BLOCKS.register(wood_type + "_wooden_formation_core",
					() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(1f),
							3,
							WOOD_TYPES.get(wood_type),
							WOODEN_FORMATION_CORES_BASES.get(wood_type).get(),
							true))
			);
		}
	}

	public static RegistryObject<Block> RUNEMAKING_TABLE = BLOCKS.register("runemaking_table",
			() -> new RunemakingTableBlock(BlockBehaviour.Properties.of().strength(2f)));

	public static RegistryObject<Block> OAK_FORMATION_CORE = BLOCKS.register("oak_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 2, Blocks.OAK_LOG, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> BIRCH_FORMATION_CORE = BLOCKS.register("birch_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 2, Blocks.BIRCH_LOG, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> SPRUCE_FORMATION_CORE = BLOCKS.register("spruce_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 2, Blocks.SPRUCE_LOG, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> JUNGLE_FORMATION_CORE = BLOCKS.register("jungle_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 2, Blocks.JUNGLE_LOG, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> ACACIA_FORMATION_CORE = BLOCKS.register("acacia_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 2, Blocks.ACACIA_LOG, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> DARK_OAK_FORMATION_CORE = BLOCKS.register("dark_oak_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 2, Blocks.DARK_OAK_LOG, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> STONE_FORMATION_CORE = BLOCKS.register("stone_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 4, Blocks.STONE, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> COPPER_FORMATION_CORE = BLOCKS.register("copper_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 5, Blocks.COPPER_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> IRON_FORMATION_CORE = BLOCKS.register("iron_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 5, Blocks.IRON_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> LAPIS_FORMATION_CORE = BLOCKS.register("lapis_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 4, Blocks.LAPIS_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> GOLD_FORMATION_CORE = BLOCKS.register("gold_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 6, Blocks.GOLD_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> DIAMOND_FORMATION_CORE = BLOCKS.register("diamond_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 9, Blocks.DIAMOND_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> EMERALD_FORMATION_CORE = BLOCKS.register("emerald_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 5, Blocks.EMERALD_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> SPIRIT_STONE_VEIN_1 = BLOCKS.register("spirit_stone_vein_1",
			() -> new Block(BlockBehaviour.Properties.of().strength(20f).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> SPIRIT_STONE_VEIN_2 = BLOCKS.register("spirit_stone_vein_2",
			() -> new Block(BlockBehaviour.Properties.of().strength(30f).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> DEEPSLATE_SPIRIT_STONE_VEIN_2 = BLOCKS.register("deepslate_spirit_stone_vein_2",
			() -> new Block(BlockBehaviour.Properties.of().strength(40f).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> SPIRIT_STONE_VEIN_3 = BLOCKS.register("spirit_stone_vein_3",
			() -> new Block(BlockBehaviour.Properties.of().strength(50f).requiresCorrectToolForDrops()));

	public static HashMap<FormationMaterialTier, RegistryObject<Block>> GENERATION_RUNES = new HashMap<>();

	public static HashMap<FormationMaterialTier, RegistryObject<Block>> BARRIER_RUNES = new HashMap<>();

	public static HashMap<System, HashMap<FormationMaterialTier, RegistryObject<Block>>> ENERGY_RUNES = new HashMap<>();

	public static HashMap<System, HashMap<FormationMaterialTier, RegistryObject<Block>>> CULTIVATION_RUNES = new HashMap<>();

	static {
		for (var material : FormationMaterialTier.values()) {
			GENERATION_RUNES.put(material, BLOCKS.register(material.name().toLowerCase() + "_generation_rune",
					() -> new StatRuneBlock(BlockBehaviour.Properties.of().strength(material.blockStrength))
							.addStat(FormationStat.ENERGY_GENERATION, material.materialModifier)
			));
			BARRIER_RUNES.put(material, BLOCKS.register(material.name().toLowerCase() + "_barrier_rune",
					() -> new StatRuneBlock(BlockBehaviour.Properties.of().strength(material.blockStrength))
							.addStat(FormationStat.ENERGY_COST, material.materialModifier.multiply(new BigDecimal("8")))
							.addStat(FormationStat.BARRIER_RANGE, BigDecimal.TEN.add(material.materialModifier.multiply(new BigDecimal("2"))))
							.addStat(FormationStat.BARRIER_MAX_AMOUNT, new BigDecimal("25").add(material.materialModifier.multiply(new BigDecimal("2.5"))))
							.addStat(FormationStat.BARRIER_STRENGTH, material.materialModifier)
							.addStat(FormationStat.BARRIER_REGEN, material.materialModifier.multiply(new BigDecimal("0.02")))
			));
			for (var system : System.values()) {
				ENERGY_RUNES.putIfAbsent(system, new HashMap<>());
				CULTIVATION_RUNES.putIfAbsent(system, new HashMap<>());
				ENERGY_RUNES.get(system).put(material, BLOCKS.register(system.name().toLowerCase() + "_" + material.name().toLowerCase() + "_energy_rune",
						() -> new StatRuneBlock(BlockBehaviour.Properties.of().strength(material.blockStrength))
								.addStat(FormationStat.ENERGY_COST, material.materialModifier.multiply(new BigDecimal("4")))
								.addStat(system, FormationSystemStat.ENERGY_REGEN, material.materialModifier.multiply(new BigDecimal("0.001")))
								.addStat(system, FormationSystemStat.ENERGY_REGEN_RUNE_COUNT, BigDecimal.ONE)
								.addStat(system, FormationSystemStat.ENERGY_REGEN_RANGE, new BigDecimal("8").add(material.materialModifier))
				));
				CULTIVATION_RUNES.get(system).put(material, BLOCKS.register(system.name().toLowerCase() + "_" + material.name().toLowerCase() + "_cultivation_rune",
						() -> new StatRuneBlock(BlockBehaviour.Properties.of().strength(material.blockStrength))
								.addStat(FormationStat.ENERGY_COST, material.materialModifier.multiply(new BigDecimal("3")))
								.addStat(system, FormationSystemStat.CULTIVATION_SPEED, new BigDecimal("0.1").add( material.materialModifier.multiply(new BigDecimal("0.006"))))
								.addStat(system, FormationSystemStat.CULTIVATION_RUNE_COUNT, BigDecimal.ONE)
				));
			}
		}
	}

}
