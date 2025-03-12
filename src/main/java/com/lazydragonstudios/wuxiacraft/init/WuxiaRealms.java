package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationRealm;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationStage;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.math.BigDecimal;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class WuxiaRealms {

	public static DeferredRegister<CultivationRealm> REALM_REGISTER = DeferredRegister.create(new ResourceLocation(WuxiaCraft.MOD_ID, "cultivation_realms"), WuxiaCraft.MOD_ID);
	public static DeferredRegister<CultivationStage> STAGE_REGISTER = DeferredRegister.create(new ResourceLocation(WuxiaCraft.MOD_ID, "cultivation_stages"), WuxiaCraft.MOD_ID);

	//************************************
	// body realms
	//************************************

	public static RegistryObject<CultivationRealm> BODY_MORTAL_REALM = REALM_REGISTER
			.register("body_mortal_realm",
					() -> new CultivationRealm("body_mortal_realm",
							System.BODY
					));

	public static RegistryObject<CultivationRealm> BODY_GATHERING_REALM = REALM_REGISTER
			.register("body_gathering_realm",
					() -> new CultivationRealm("body_gathering_realm",
							System.BODY
					));
	public static RegistryObject<CultivationRealm> BODY_ESTABLISHMENT_REALM = REALM_REGISTER
					.register("body_establishment",
							() -> new CultivationRealm("body_establishment_realm",
									System.BODY
							));
		
	public static RegistryObject<CultivationRealm> BODY_REVOLVING_CORE_REALM = REALM_REGISTER
					.register("body_revolving_core_realm",
							() -> new CultivationRealm("body_revolving_core_realm",
									System.BODY
							));

	//************************************
	// divine realms
	//************************************

	public static RegistryObject<CultivationRealm> DIVINE_MORTAL_REALM = REALM_REGISTER
			.register("divine_mortal_realm",
					() -> new CultivationRealm("divine_mortal_realm",
							System.DIVINE
					));

	public static RegistryObject<CultivationRealm> DIVINE_GATHERING_REALM = REALM_REGISTER
			.register("divine_gathering_realm",
					() -> new CultivationRealm("divine_gathering_realm",
							System.DIVINE
					));
	public static RegistryObject<CultivationRealm> DIVINE_ESTABLISHMENT_REALM = REALM_REGISTER
			.register("divine_establishment",
					() -> new CultivationRealm("divine_establishment_realm",
							System.DIVINE
					));

	public static RegistryObject<CultivationRealm> DIVINE_REVOLVING_CORE_REALM = REALM_REGISTER
			.register("divine_revolving_core_realm",
					() -> new CultivationRealm("divine_revolving_core_realm",
							System.DIVINE
					));

	//************************************
	// essence realms
	//************************************

	public static RegistryObject<CultivationRealm> ESSENCE_MORTAL_REALM = REALM_REGISTER
			.register("essence_mortal_realm",
					() -> new CultivationRealm("essence_mortal_realm",
							System.ESSENCE
					));

	public static RegistryObject<CultivationRealm> ESSENCE_GATHERING_REALM = REALM_REGISTER
			.register("essence_gathering_realm",
					() -> new CultivationRealm("essence_gathering_realm",
							System.ESSENCE
					));

	public static RegistryObject<CultivationRealm> FOUNDATION_ESTABLISHMENT_REALM = REALM_REGISTER
			.register("foundation_establishment",
					() -> new CultivationRealm("foundation_establishment_realm",
							System.ESSENCE
					));

	public static RegistryObject<CultivationRealm> ESSENCE_REVOLVING_CORE_REALM = REALM_REGISTER
			.register("essence_revolving_core_realm",
					() -> new CultivationRealm("essence_revolving_core_realm",
							System.ESSENCE
					));

	public static RegistryObject<CultivationRealm> ESSENCE_IMMORTAL_SEA_REALM = REALM_REGISTER
			.register("essence_immortal_sea_realm",
					() -> new CultivationRealm("essence_immortal_sea_realm",
							System.ESSENCE
					));

	public static RegistryObject<CultivationRealm> ESSENCE_IMMORTAL_TRANSFORMATION_REALM = REALM_REGISTER
			.register("essence_immortal_transformation_realm",
					() -> new CultivationRealm("essence_immortal_transformation_realm",
							System.ESSENCE
					));

	//*********************************
	// body stages
	//*********************************

	public static RegistryObject<CultivationStage> BODY_MORTAL_STAGE = STAGE_REGISTER
			.register("body_mortal_stage",
					() -> new CultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_mortal_realm"),
							null,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_gathering_stage")
					)
					.setOnCultivate(cultivateFlatAmountsBody(BigDecimal.ONE, BigDecimal.ONE))
					.setOnCultivationFailure(cultivateFailureEnergyBody(BigDecimal.ONE))
					.addSystemStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("1000"))
			);

	public static RegistryObject<CultivationStage> BODY_GATHERING_STAGE = STAGE_REGISTER
			.register("body_gathering_stage",
					() -> new CultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_mortal_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_pathways_stage")
					)
						.setOnCultivate(cultivateFlatAmountsBody(new BigDecimal("2.5"), BigDecimal.ONE))
						.setOnCultivationFailure(cultivateFailureEnergyBody(new BigDecimal("2.5")))
						.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("4"))
						.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1"))
						.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("1"))
						.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.3"))
						.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.1"))
						.addSystemStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("4000"))
						.addSystemStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.01"))
						.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("3"))
			);

	public static RegistryObject<CultivationStage> BODY_PATHWAYS_STAGE = STAGE_REGISTER
			.register("body_pathways_stage",
					() -> new CultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_gathering_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_condensation_stage")
					)
							.setOnCultivate(cultivateFlatAmountsBody(new BigDecimal("4"), BigDecimal.ONE))
							.setOnCultivationFailure(cultivateFailureEnergyBody(new BigDecimal("4")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.3"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.2"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("6000")) //20k
							.addSystemStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("4"))
			);

	public static RegistryObject<CultivationStage> BODY_CONDENSATION_STAGE = STAGE_REGISTER
			.register("body_condensation_stage",
					() -> new CultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_pathways_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_phenomenon_stage")
					)
							.setOnCultivate(cultivateFlatAmountsBody(new BigDecimal("4"), new BigDecimal("2.5")))
							.setOnCultivationFailure(cultivateFailureEnergyBody(new BigDecimal("4")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("4"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.3"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.3"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.005"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.005"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("9000"))
							.addSystemStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.01"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("5"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.01"))
			);

	public static RegistryObject<CultivationStage> BODY_PHENOMENON_STAGE = STAGE_REGISTER
			.register("body_phenomenon_stage",
					() -> new CultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_condensation_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_shaping_stage")
					)
							.setOnCultivate(cultivateFlatAmountsBody(new BigDecimal("8"), new BigDecimal("4")))
							.setOnCultivationFailure(cultivateFailureEnergyBody(new BigDecimal("8")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("6"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.6"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.4"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.015"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.015"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("15000"))
							.addSystemStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.015"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("8"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.002"))
							.addSystemStat(System.BODY, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);

	//Change realms -> big leap in stats
	public static RegistryObject<CultivationStage> BODY_SHAPING_STAGE = STAGE_REGISTER
			.register("body_shaping_stage",
					() -> new CultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_phenomenon_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_molding_stage")
					)
							.setOnCultivate(cultivateFlatAmountsBody(new BigDecimal("10"), new BigDecimal("4")))
							.setOnCultivationFailure(cultivateFailureEnergyBody(new BigDecimal("2.5")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("12"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("2.4"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.02"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.025"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("25000"))
							.addSystemStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.02"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("15"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.005"))
			);

	public static RegistryObject<CultivationStage> BODY_MOLDING_STAGE = STAGE_REGISTER
			.register("body_molding_stage",
					() -> new CultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_shaping_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_solidification_stage")
					)
							.setOnCultivate(cultivateFlatAmountsBody(new BigDecimal("12"), new BigDecimal("5")))
							.setOnCultivationFailure(cultivateFailureEnergyBody(new BigDecimal("8")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("14"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.9"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.7"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.02"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.03"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("40000"))
							.addSystemStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.2"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("18"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.07"))
			);

	public static RegistryObject<CultivationStage> BODY_SOLIDIFICATION_STAGE = STAGE_REGISTER
			.register("body_solidification_stage",
					() -> new CultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_molding_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_core_shaping_stage")
					)
							.setOnCultivate(cultivateFlatAmountsBody(new BigDecimal("16"), new BigDecimal("7")))
							.setOnCultivationFailure(cultivateFailureEnergyBody(new BigDecimal("12")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("14"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("3"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("1.2"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.9"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.025"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.035"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("70000"))
							.addSystemStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.04"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("25"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.03"))
			);

	public static RegistryObject<CultivationStage> BODY_CORE_SHAPING_STAGE = STAGE_REGISTER
			.register("body_core_shaping_stage",
					() -> new CultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "foundation_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_solidification_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_golden_core_stage")
					)
							.setOnCultivate(cultivateFlatAmountsBody(new BigDecimal("25"), new BigDecimal("12")))
							.setOnCultivationFailure(cultivateFailureEnergyBody(new BigDecimal("18")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("16"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("3"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("3"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("1.2"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.9"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.03"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.04"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("130000"))
							.addSystemStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.08"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("42"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.05"))
							.addSystemStat(System.BODY, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);
	//TODO finish golden core stage
	public static RegistryObject<CultivationStage> BODY_GOLDEN_CORE_STAGE = STAGE_REGISTER
			.register("body_golden_core_stage",
					() -> new CultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_revolving_core_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_core_shaping_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_golden_core_stage")
					)
							.setOnCultivate(cultivateFlatAmountsBody(new BigDecimal("35"), new BigDecimal("15")))
							.setOnCultivationFailure(cultivateFailureEnergyBody(new BigDecimal("25")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("18"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("4"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("3"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("1.8"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.8"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.04"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.05"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("200000"))
							.addSystemStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.13"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("84"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.06"))
			);			

	//*********************************
	// divine stages
	//*********************************

	public static RegistryObject<CultivationStage> DIVINE_MORTAL_STAGE = STAGE_REGISTER
			.register("divine_mortal_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_mortal_realm"),
							null, 
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_gathering_stage")
					)
					.setOnCultivate(cultivateFlatAmountsDivine(BigDecimal.ONE, BigDecimal.ONE))
					.setOnCultivationFailure(cultivateFailureEnergyDivine(BigDecimal.ONE))
					.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("1000"))
			);

	public static RegistryObject<CultivationStage> DIVINE_GATHERING_STAGE = STAGE_REGISTER
			.register("divine_gathering_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_mortal_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_pathways_stage")
					)
					.setOnCultivate(cultivateFlatAmountsDivine(new BigDecimal("2.5"), BigDecimal.ONE))
					.setOnCultivationFailure(cultivateFailureEnergyDivine(new BigDecimal("2.5")))
					.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("2"))
					.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("0.5"))
					.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("2"))
					.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("1"))
					.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.4"))
					.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("4000"))
					.addSystemStat(System.DIVINE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.01"))
					.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("3"))
					.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.001"))
					.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.002"))
			);
	public static RegistryObject<CultivationStage> DIVINE_PATHWAYS_STAGE = STAGE_REGISTER
			.register("divine_pathways_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_gathering_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_condensation_stage")
					)
							.setOnCultivate(cultivateFlatAmountsDivine(new BigDecimal("4"), BigDecimal.ONE))
							.setOnCultivationFailure(cultivateFailureEnergyDivine(new BigDecimal("4")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("0.5"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("4"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("1.2"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.8"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("6000")) //20k
							.addSystemStat(System.DIVINE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("4"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.002"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.003"))
			);

	public static RegistryObject<CultivationStage> DIVINE_CONDENSATION_STAGE = STAGE_REGISTER
			.register("divine_condensation_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_pathways_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_phenomenon_stage")
					)
							.setOnCultivate(cultivateFlatAmountsDivine(new BigDecimal("4"), new BigDecimal("2.5")))
							.setOnCultivationFailure(cultivateFailureEnergyDivine(new BigDecimal("4")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("0.5"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("6"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("1.4"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("1.2"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("9000"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.01"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("5"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.01"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.003"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.004"))
			);

	public static RegistryObject<CultivationStage> DIVINE_PHENOMENON_STAGE = STAGE_REGISTER
			.register("divine_phenomenon_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_condensation_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_shaping_stage")
					)
							.setOnCultivate(cultivateFlatAmountsDivine(new BigDecimal("8"), new BigDecimal("4")))
							.setOnCultivationFailure(cultivateFailureEnergyDivine(new BigDecimal("8")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("3"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("10"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("1.6"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("1.6"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("15000"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.015"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("8"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.002"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.01"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.01"))
			);

	//Change realms -> big leap in stats
	public static RegistryObject<CultivationStage> DIVINE_SHAPING_STAGE = STAGE_REGISTER
			.register("divine_shaping_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_phenomenon_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_molding_stage")
					)
							.setOnCultivate(cultivateFlatAmountsDivine(new BigDecimal("10"), new BigDecimal("4")))
							.setOnCultivationFailure(cultivateFailureEnergyDivine(new BigDecimal("2.5")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("5"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("8"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("2.4"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("25000"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.02"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("6"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.005"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.003"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.005"))
			);

	public static RegistryObject<CultivationStage> DIVINE_MOLDING_STAGE = STAGE_REGISTER
			.register("divine_molding_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_shaping_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_solidification_stage")
					)
							.setOnCultivate(cultivateFlatAmountsDivine(new BigDecimal("12"), new BigDecimal("5")))
							.setOnCultivationFailure(cultivateFailureEnergyDivine(new BigDecimal("8")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("7"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("10"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("3"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("3.2"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("40000"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.2"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("18"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.007"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.001"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.002"))
			);

	public static RegistryObject<CultivationStage> DIVINE_SOLIDIFICATION_STAGE = STAGE_REGISTER
			.register("divine_solidification_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_molding_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_core_shaping_stage")
					)
							.setOnCultivate(cultivateFlatAmountsDivine(new BigDecimal("16"), new BigDecimal("7")))
							.setOnCultivationFailure(cultivateFailureEnergyDivine(new BigDecimal("12")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("7"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("12"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("4"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("4"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("70000"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.04"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("25"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.012"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.01"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.03"))
			);

	public static RegistryObject<CultivationStage> DIVINE_CORE_SHAPING_STAGE = STAGE_REGISTER
			.register("divine_core_shaping_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_solidification_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_golden_core_stage")
					)
							.setOnCultivate(cultivateFlatAmountsDivine(new BigDecimal("25"), new BigDecimal("12")))
							.setOnCultivationFailure(cultivateFailureEnergyDivine(new BigDecimal("18")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("8"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("10"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("5"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("4.8"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("130000"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.08"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("42"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.023"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.003"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.004"))
			);
	//TODO finish golden core stage
	public static RegistryObject<CultivationStage> DIVINE_GOLDEN_CORE_STAGE = STAGE_REGISTER
			.register("divine_golden_core_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_revolving_core_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_core_shaping_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_golden_core_stage")
					)
							.setOnCultivate(cultivateFlatAmountsDivine(new BigDecimal("35"), new BigDecimal("15")))
							.setOnCultivationFailure(cultivateFailureEnergyDivine(new BigDecimal("25")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("10"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("4"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("14"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("6"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("6"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("200000"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.13"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("84"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.06"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.006"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.008"))
			);
	//*********************************
	// essence stages
	//*********************************

	public static RegistryObject<CultivationStage> ESSENCE_MORTAL_STAGE = STAGE_REGISTER
			.register("essence_mortal_stage",
					() -> new CultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_mortal_realm"),
							null,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_gathering_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(BigDecimal.ONE, BigDecimal.ONE))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(BigDecimal.ONE))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("1000")) //1k
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_GATHERING_STAGE = STAGE_REGISTER
			.register("essence_qi_gathering_stage",
					() -> new CultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_mortal_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_pathways_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(new BigDecimal("2.5"), BigDecimal.ONE))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(new BigDecimal("2.5")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("0.5"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.5"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.1"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.1"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.1"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("4000"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.01"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("1"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("1"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("3"))
							.addSkill(WuxiaSkillAspects.PUNCH.getId())
							.addSkill(WuxiaSkillAspects.SELF.getId())
							.addSkill(WuxiaSkillAspects.ATTACK.getId())
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_PATHWAYS_STAGE = STAGE_REGISTER
			.register("essence_qi_pathways_stage",
					() -> new CultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_gathering_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_condensation_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(new BigDecimal("4"), BigDecimal.ONE))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(new BigDecimal("4")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("0.5"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.6"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.2"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.1"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.2"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("6000")) //20k
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("1.5"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("1.5"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("4"))
							.addSkill(WuxiaSkillAspects.BREAK.getId())
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_CONDENSATION_STAGE = STAGE_REGISTER
			.register("essence_qi_condensation_stage",
					() -> new CultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_pathways_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_phenomenon_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(new BigDecimal("4"), new BigDecimal("2.5")))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(new BigDecimal("4")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("0.5"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("3"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.7"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.3"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.1"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.3"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.01"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.005"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("9000"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.01"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("2"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("2"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("5"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.01"))
							.addSkill(WuxiaSkillAspects.CHOP.getId())
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_PHENOMENON_STAGE = STAGE_REGISTER
			.register("essence_qi_phenomenon_stage",
					() -> new CultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_condensation_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_shaping_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(new BigDecimal("8"), new BigDecimal("4")))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(new BigDecimal("8")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("3"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("5"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.8"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.4"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.2"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.4"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.03"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.015"))
							.addPlayerStat(PlayerStat.MAX_BARRIER, new BigDecimal("5"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.01"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.03"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("15000"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.015"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("2.5"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.001"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("2.5"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.001"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("8"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.002"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.01"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.01"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
							.addSkill(WuxiaSkillAspects.SHOOT.getId())
			);

	//Change realms -> big leap in stats
	public static RegistryObject<CultivationStage> ESSENCE_QI_SHAPING_STAGE = STAGE_REGISTER
			.register("essence_qi_shaping_stage",
					() -> new CultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "foundation_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_phenomenon_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_molding_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(new BigDecimal("10"), new BigDecimal("4")))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(new BigDecimal("2.5")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("8"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("4"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.6"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.8"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.04"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.025"))
							.addPlayerStat(PlayerStat.MAX_BARRIER, new BigDecimal("6"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.03"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.07"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("25000"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.02"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("6"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.005"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("6"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.005"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("15"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.005"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.003"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.005"))
							.addSkill(WuxiaSkillAspects.SWORD_FLIGHT.getId())
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_MOLDING_STAGE = STAGE_REGISTER
			.register("essence_qi_molding_stage",
					() -> new CultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "foundation_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_shaping_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_solidification_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(new BigDecimal("12"), new BigDecimal("5")))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(new BigDecimal("8")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("7"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("5"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.8"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.3"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.7"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.04"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.03"))
							.addPlayerStat(PlayerStat.MAX_BARRIER, new BigDecimal("8"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.04"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.09"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("40000"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.2"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("6"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.02"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("6"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.02"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("18"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.007"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.001"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.002"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_SOLIDIFICATION_STAGE = STAGE_REGISTER
			.register("essence_qi_solidification_stage",
					() -> new CultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "foundation_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_molding_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_core_shaping_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(new BigDecimal("16"), new BigDecimal("7")))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(new BigDecimal("12")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("7"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("6"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("1"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.4"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.9"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.05"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.035"))
							.addPlayerStat(PlayerStat.MAX_BARRIER, new BigDecimal("11"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.05"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.11"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("70000"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.04"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("10"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.03"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("10"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.03"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("25"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.012"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.01"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.03"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_CORE_SHAPING_STAGE = STAGE_REGISTER
			.register("essence_core_shaping_stage",
					() -> new CultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "foundation_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_solidification_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_golden_core_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(new BigDecimal("25"), new BigDecimal("12")))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(new BigDecimal("18")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("8"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("5"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("2.5"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("1.2"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.4"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.9"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.06"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.04"))
							.addPlayerStat(PlayerStat.MAX_BARRIER, new BigDecimal("15"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.07"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.18"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("130000"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.08"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("15"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.05"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("15"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.05"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("42"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.023"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.003"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.004"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);
	//TODO finish golden core stage
	public static RegistryObject<CultivationStage> ESSENCE_GOLDEN_CORE_STAGE = STAGE_REGISTER 
			.register("essence_golden_core_stage",
					() -> new CultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_revolving_core_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_core_shaping_stage"), 
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_golden_core_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(new BigDecimal("35"), new BigDecimal("15")))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(new BigDecimal("25")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("12"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("7"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("3"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.6"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.8"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.08"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.05"))
							.addPlayerStat(PlayerStat.MAX_BARRIER, new BigDecimal("20"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.1"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.2"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("200000"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.13"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("15"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.05"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("15"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.05"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("84"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.06"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.006"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.008"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_CORE_EXPANSION_STAGE = STAGE_REGISTER
			.register("essence_core_expansion_stage", 
					() -> new CultivationStage(
							System.ESSENCE, 
							new ResourceLocation("wuxiacraft", "essence_revolving_core_realm"), 
							new ResourceLocation("wuxiacraft", "essence_golden_core_stage"), 
							new ResourceLocation("wuxiacraft", "essence_core_expansion_stage")
					)
							.setOnCultivate(cultivateFlatAmountsEssence(new BigDecimal("48"), new BigDecimal("18")))
							.setOnCultivationFailure(cultivateFailureEnergyEssence(new BigDecimal("35")))
							.addPlayerStat(PlayerStat.MAX_HEALTH, new BigDecimal("14"))
							.addPlayerStat(PlayerStat.STRENGTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.AGILITY, new BigDecimal("1.5"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.6"))
							.addPlayerStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.9"))
							.addPlayerStat(PlayerStat.DETECTION_RANGE, new BigDecimal("7"))
							.addPlayerStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("1.9"))
							.addPlayerStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("2"))
							.addPlayerStat(PlayerStat.MAX_BARRIER, new BigDecimal("14"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.07"))
							.addPlayerStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.11"))
							.addPlayerStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.06"))
							.addPlayerStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.04"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("3000000"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.2"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("56"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.02"))
							.addSystemStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("10"))
							.addSystemStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.016"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("10"))
							.addSystemStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.06"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.1"))
							.addSystemStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.08"))
				);
    
// ESSENCE

	public static Consumer<Player> cultivateFlatAmountsEssence(BigDecimal energy, BigDecimal cultivationBase) {
		return player -> {
			var cultivation = Cultivation.get(player);
			var essenceData = cultivation.getSystemData(System.ESSENCE);
			if (essenceData.consumeEnergy(energy)) {
				cultivation.addCultivationBase(player, System.ESSENCE, cultivationBase);
			}
		};
	}
	

	public static Consumer<Player> cultivateFailureEnergyEssence(BigDecimal energy) {
		return player -> {
			var cultivation = Cultivation.get(player);
			var essenceData = cultivation.getSystemData(System.ESSENCE);
			essenceData.consumeEnergy(energy);
		};
	}
// DIVINE
	public static Consumer<Player> cultivateFlatAmountsDivine(BigDecimal energy, BigDecimal cultivationBase) {
		return player -> {
			var cultivation = Cultivation.get(player);
			var divineData = cultivation.getSystemData(System.DIVINE);
			if (divineData.consumeEnergy(energy)) {
				cultivation.addCultivationBase(player, System.DIVINE, cultivationBase);
			}
		};
	}
	

	public static Consumer<Player> cultivateFailureEnergyDivine(BigDecimal energy) {
		return player -> {
			var cultivation = Cultivation.get(player);
			var divineData = cultivation.getSystemData(System.DIVINE);
			divineData.consumeEnergy(energy);
		};
	}
// BODY
	public static Consumer<Player> cultivateFlatAmountsBody(BigDecimal energy, BigDecimal cultivationBase) {
		return player -> {
			var cultivation = Cultivation.get(player);
			var bodyData = cultivation.getSystemData(System.BODY);
			if (bodyData.consumeEnergy(energy)) {
				cultivation.addCultivationBase(player, System.BODY, cultivationBase);
			}
		};
	}
	
	public static Consumer<Player> cultivateFailureEnergyBody(BigDecimal energy) {
		return player -> {
			var cultivation = Cultivation.get(player);
			var bodyData = cultivation.getSystemData(System.BODY);
			bodyData.consumeEnergy(energy);
		};
	}

}
