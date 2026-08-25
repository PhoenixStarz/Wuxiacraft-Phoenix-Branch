package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.blocks.*;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.formation.FormationMaterialTier;
import com.lazydragonstudios.wuxiacraft.formation.FormationStat;
import com.lazydragonstudios.wuxiacraft.formation.FormationSystemStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
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
			() -> new FormationCoreBaseBlock(BlockBehaviour.Properties.of().strength(2f)));

	public static RegistryObject<Block> RUNEMAKING_TABLE = BLOCKS.register("runemaking_table",
			() -> new RunemakingTableBlock(BlockBehaviour.Properties.of().strength(2f)));

	public static RegistryObject<Block> STONE_FORMATION_CORE = BLOCKS.register("stone_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 2, Blocks.STONE, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> COPPER_FORMATION_CORE = BLOCKS.register("copper_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 3, Blocks.COPPER_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> IRON_FORMATION_CORE = BLOCKS.register("iron_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 5, Blocks.IRON_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> LAPIS_FORMATION_CORE = BLOCKS.register("lapis_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 3, Blocks.LAPIS_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> GOLD_FORMATION_CORE = BLOCKS.register("gold_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 7, Blocks.GOLD_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> DIAMOND_FORMATION_CORE = BLOCKS.register("diamond_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 8, Blocks.DIAMOND_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> EMERALD_FORMATION_CORE = BLOCKS.register("emerald_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 6, Blocks.EMERALD_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> NETHERITE_FORMATION_CORE = BLOCKS.register("netherite_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 9, Blocks.NETHERITE_BLOCK, FORMATION_CORE_BASE.get()));

	public static RegistryObject<Block> SPIRIT_STONE_VEIN_1 = BLOCKS.register("spirit_stone_vein_1",
			() -> new VeinBlock(BlockBehaviour.Properties.of().strength(8f).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> SPIRIT_STONE_VEIN_2 = BLOCKS.register("spirit_stone_vein_2",
			() -> new VeinBlock(BlockBehaviour.Properties.of().strength(12f).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> DEEPSLATE_SPIRIT_STONE_VEIN_2 = BLOCKS.register("deepslate_spirit_stone_vein_2",
			() -> new VeinBlock(BlockBehaviour.Properties.of().strength(16f).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> SPIRIT_STONE_VEIN_3 = BLOCKS.register("spirit_stone_vein_3",
			() -> new VeinBlock(BlockBehaviour.Properties.of().strength(20f).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> SPIRIT_STONE_VEIN_4 = BLOCKS.register("spirit_stone_vein_4",
			() -> new VeinBlock(BlockBehaviour.Properties.of().strength(24f).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> SPIRIT_STONE_VEIN_5 = BLOCKS.register("spirit_stone_vein_5",
			() -> new VeinBlock(BlockBehaviour.Properties.of().strength(32f).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> SPIRIT_STONE_VEIN_6 = BLOCKS.register("spirit_stone_vein_6",
			() -> new VeinBlock(BlockBehaviour.Properties.of().strength(40f).requiresCorrectToolForDrops()));	

	public static RegistryObject<Block> SPIRIT_STONE_VEIN_7 = BLOCKS.register("spirit_stone_vein_7",
			() -> new VeinBlock(BlockBehaviour.Properties.of().strength(44f).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> CELESTIAL_IRON_ORE = BLOCKS.register("celestial_iron_ore",
			() -> new Block(BlockBehaviour.Properties.of().strength(16f).lightLevel(s -> 3).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> DEEPSLATE_CELESTIAL_IRON_ORE = BLOCKS.register("deepslate_celestial_iron_ore",
			() -> new Block(BlockBehaviour.Properties.of().strength(16f).lightLevel(s -> 3).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> CELESTIAL_IRON_BLOCK = BLOCKS.register("celestial_iron_block",
			() -> new Block(BlockBehaviour.Properties.of().strength(16f).lightLevel(s -> 3).requiresCorrectToolForDrops()));

	public static RegistryObject<Block> RAW_CELESTIAL_IRON_BLOCK = BLOCKS.register("raw_celestial_iron_block",
			() -> new Block(BlockBehaviour.Properties.of().strength(16f).lightLevel(s -> 3).requiresCorrectToolForDrops()));
	
	public static RegistryObject<Block> CELESTIAL_IRON_FORMATION_CORE = BLOCKS.register("celestial_iron_formation_core",
			() -> new FormationCoreBlock(BlockBehaviour.Properties.of().strength(2f), 10, CELESTIAL_IRON_BLOCK.get(), FORMATION_CORE_BASE.get()));
			
    public static RegistryObject<Block> BONSAI_1 = BLOCKS.register("bonsai_1",
			() -> new BonsaiBlock(() -> WuxiaItems.SPIRIT_FRUIT_1.get()));

    public static RegistryObject<Block> BONSAI_2 = BLOCKS.register("bonsai_2",
			() -> new BonsaiBlock(() -> WuxiaItems.SPIRIT_FRUIT_2.get()));
			
    public static RegistryObject<Block> BONSAI_3 = BLOCKS.register("bonsai_3",
			() -> new BonsaiBlock(() -> WuxiaItems.SPIRIT_FRUIT_3.get()));
			
    public static RegistryObject<Block> BONSAI_4 = BLOCKS.register("bonsai_4",
			() -> new BonsaiBlock(() -> WuxiaItems.SPIRIT_FRUIT_4.get()));
			
    public static RegistryObject<Block> BONSAI_5 = BLOCKS.register("bonsai_5",
			() -> new BonsaiBlock(() -> WuxiaItems.SPIRIT_FRUIT_5.get()));
			
    public static RegistryObject<Block> BONSAI_6 = BLOCKS.register("bonsai_6",
			() -> new BonsaiBlock(() -> WuxiaItems.SPIRIT_FRUIT_6.get()));
			
    public static RegistryObject<Block> BONSAI_7 = BLOCKS.register("bonsai_7",
			() -> new BonsaiBlock(() -> WuxiaItems.SPIRIT_FRUIT_7.get()));
			
    public static RegistryObject<Block> BONSAI_8 = BLOCKS.register("bonsai_8",
			() -> new BonsaiBlock(() -> WuxiaItems.SPIRIT_FRUIT_8.get()));
			
    public static RegistryObject<Block> BONSAI_9 = BLOCKS.register("bonsai_9",
			() -> new BonsaiBlock(() -> WuxiaItems.SPIRIT_FRUIT_9.get()));

						
    public static RegistryObject<Block> SPIRIT_CRYSTAL_BLOCK = BLOCKS.register("spirit_crystal_block",
			() -> new VeinBlock(BlockBehaviour.Properties.of()
			.strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> BUDDING_SPIRIT_CRYSTAL_BLOCK = BLOCKS.register("budding_spirit_crystal_block",
			() -> new BuddingSpiritCrystalBlock(BlockBehaviour.Properties.of()
			.pushReaction(PushReaction.DESTROY).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
									
    public static RegistryObject<Block> SPIRIT_CRYSTAL_CLUSTER_1 = BLOCKS.register("spirit_crystal_cluster_1",
			() -> new SpiritCrystalCluster(BlockBehaviour.Properties.of().forceSolidOn()
                    .pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel(state -> 3)));

    public static RegistryObject<Block> SPIRIT_CRYSTAL_CLUSTER_2 = BLOCKS.register("spirit_crystal_cluster_2",
			() -> new SpiritCrystalCluster(BlockBehaviour.Properties.of().forceSolidOn()
                    .pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel(state -> 4)));
			
    public static RegistryObject<Block> SPIRIT_CRYSTAL_CLUSTER_3 = BLOCKS.register("spirit_crystal_cluster_3",
			() -> new SpiritCrystalCluster(BlockBehaviour.Properties.of().forceSolidOn()
                    .pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel(state -> 5)));
			
    public static RegistryObject<Block> SPIRIT_CRYSTAL_CLUSTER_4 = BLOCKS.register("spirit_crystal_cluster_4",
			() -> new SpiritCrystalCluster(BlockBehaviour.Properties.of().forceSolidOn()
                    .pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel(state -> 6)));
			
    public static RegistryObject<Block> SPIRIT_CRYSTAL_CLUSTER_5 = BLOCKS.register("spirit_crystal_cluster_5",
			() -> new SpiritCrystalCluster(BlockBehaviour.Properties.of().forceSolidOn()
                    .pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel(state -> 7)));
			
    public static RegistryObject<Block> SPIRIT_CRYSTAL_CLUSTER_6 = BLOCKS.register("spirit_crystal_cluster_6",
			() -> new SpiritCrystalCluster(BlockBehaviour.Properties.of().forceSolidOn()
                    .pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel(state -> 8)));
			
    public static RegistryObject<Block> SPIRIT_CRYSTAL_CLUSTER_7 = BLOCKS.register("spirit_crystal_cluster_7",
			() -> new SpiritCrystalCluster(BlockBehaviour.Properties.of().forceSolidOn()
                    .pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel(state -> 9)));
			
    public static RegistryObject<Block> SPIRIT_CRYSTAL_CLUSTER_8 = BLOCKS.register("spirit_crystal_cluster_8",
			() -> new SpiritCrystalCluster(BlockBehaviour.Properties.of().forceSolidOn()
                    .pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel(state -> 10)));
			
    public static RegistryObject<Block> SPIRIT_CRYSTAL_CLUSTER_9 = BLOCKS.register("spirit_crystal_cluster_9",
			() -> new SpiritCrystalCluster(BlockBehaviour.Properties.of().forceSolidOn()
                    .pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel(state -> 11)));

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
							.addStat(FormationStat.BARRIER_RANGE, new BigDecimal("4").add(material.materialModifier))
							.addStat(FormationStat.BARRIER_MAX_AMOUNT, material.materialModifier.multiply(new BigDecimal("10")))
							.addStat(FormationStat.BARRIER_STRENGTH, material.materialModifier)
							.addStat(FormationStat.BARRIER_REGEN, material.materialModifier.multiply(new BigDecimal("0.02")))
			));
			for (var system : System.values()) {
				ENERGY_RUNES.putIfAbsent(system, new HashMap<>());
				CULTIVATION_RUNES.putIfAbsent(system, new HashMap<>());
				ENERGY_RUNES.get(system).put(material, BLOCKS.register(system.name().toLowerCase() + "_" + material.name().toLowerCase() + "_energy_rune",
						() -> new StatRuneBlock(BlockBehaviour.Properties.of().strength(material.blockStrength))
								.addStat(FormationStat.ENERGY_COST, material.materialModifier.multiply(new BigDecimal("4")))
								.addStat(system, FormationSystemStat.ENERGY_REGEN, material.materialModifier.multiply(new BigDecimal("0.005")))
								.addStat(system, FormationSystemStat.ENERGY_REGEN_RUNE_COUNT, BigDecimal.ONE)
								.addStat(system, FormationSystemStat.ENERGY_REGEN_RANGE, new BigDecimal("8").add(material.materialModifier))
				));
				CULTIVATION_RUNES.get(system).put(material, BLOCKS.register(system.name().toLowerCase() + "_" + material.name().toLowerCase() + "_cultivation_rune",
						() -> new StatRuneBlock(BlockBehaviour.Properties.of().strength(material.blockStrength))
								.addStat(FormationStat.ENERGY_COST, material.materialModifier.multiply(new BigDecimal("3")))
								.addStat(system, FormationSystemStat.CULTIVATION_SPEED, material.materialModifier.multiply(new BigDecimal("0.05")))
								.addStat(system, FormationSystemStat.CULTIVATION_RUNE_COUNT, BigDecimal.ONE)
				));
			}
		}
	}

}
