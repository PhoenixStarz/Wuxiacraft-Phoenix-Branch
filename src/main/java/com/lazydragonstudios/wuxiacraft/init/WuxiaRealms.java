package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.*;
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

	public static RegistryObject<CultivationRealm> BODY_FORGING_REALM = REALM_REGISTER
			.register("body_forging_realm",
					() -> new CultivationRealm("body_forging_realm",
							System.BODY
					));

	public static RegistryObject<CultivationRealm> BODY_TORSO_TEMPERING = REALM_REGISTER
			.register("body_torso_tempering",
					() -> new CultivationRealm("body_torso_tempering",
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

	public static RegistryObject<CultivationRealm> ESSENCE_VOID_NEBULA_REALM = REALM_REGISTER
			.register("essence_void_nebula_realm",
					() -> new CultivationRealm("essence_void_nebula_realm",
							System.ESSENCE
					));

	//*********************************
	// body stages
	//*********************************

	public static RegistryObject<CultivationStage> BODY_MORTAL_STAGE = STAGE_REGISTER
			.register("body_mortal_stage",
					() -> new BodyCultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_mortal_realm"),
							null,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_tendon_forging")
					)
							.unlockPart(WuxiaBodyParts.LEFT_ARM_TENDON.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOREARM_TENDON.getId())
							.unlockPart(WuxiaBodyParts.LEFT_HAND_TENDON.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_ARM_TENDON.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOREARM_TENDON.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_HAND_TENDON.getId())
							.unlockPart(WuxiaBodyParts.LEFT_THIGH_TENDON.getId())
							.unlockPart(WuxiaBodyParts.LEFT_CALF_TENDON.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOOT_TENDON.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_THIGH_TENDON.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_CALF_TENDON.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOOT_TENDON.getId())
							.setStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("500"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.01"))
			);

	public static RegistryObject<CultivationStage> BODY_TENDON_FORGING = STAGE_REGISTER
			.register("body_tendon_forging",
					() -> new BodyCultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_forging_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_mortal_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_marrow_forging")
					)
							.setStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("4500"))
							.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.01"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.01"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.02"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("5"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.01"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("2"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.01"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.01"))
			);

	public static RegistryObject<CultivationStage> BODY_MARROW_FORGING = STAGE_REGISTER
			.register("body_marrow_forging",
					() -> new BodyCultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_forging_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_tendon_forging"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_veins_forging")
					)
							.unlockPart(WuxiaBodyParts.LEFT_ARM_MARROW.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOREARM_MARROW.getId())
							.unlockPart(WuxiaBodyParts.LEFT_HAND_MARROW.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_ARM_MARROW.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOREARM_MARROW.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_HAND_MARROW.getId())
							.unlockPart(WuxiaBodyParts.LEFT_THIGH_MARROW.getId())
							.unlockPart(WuxiaBodyParts.LEFT_CALF_MARROW.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOOT_MARROW.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_THIGH_MARROW.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_CALF_MARROW.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOOT_MARROW.getId())
							.setStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("7000"))
							.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.015"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("1"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.01"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.01"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.02"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("7"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.015"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.025"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.015"))
			);

	public static RegistryObject<CultivationStage> BODY_VEINS_FORGING = STAGE_REGISTER
			.register("body_veins_forging",
					() -> new BodyCultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_forging_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_marrow_forging"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_muscle_forging")
					)
							.unlockPart(WuxiaBodyParts.LEFT_ARM_VEIN.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOREARM_VEIN.getId())
							.unlockPart(WuxiaBodyParts.LEFT_HAND_VEIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_ARM_VEIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOREARM_VEIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_HAND_VEIN.getId())
							.unlockPart(WuxiaBodyParts.LEFT_THIGH_VEIN.getId())
							.unlockPart(WuxiaBodyParts.LEFT_CALF_VEIN.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOOT_VEIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_THIGH_VEIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_CALF_VEIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOOT_VEIN.getId())
							.setStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("10000"))
							.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.015"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("3"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("1"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.01"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.015"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.02"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("9"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.015"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("2"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.035"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.02"))
							.setStat(System.BODY, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);

	public static RegistryObject<CultivationStage> BODY_MUSCLE_FORGING = STAGE_REGISTER
			.register("body_muscle_forging",
					() -> new BodyCultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_forging_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_veins_forging"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_skin_forging")
					)
							.unlockPart(WuxiaBodyParts.LEFT_ARM_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOREARM_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.LEFT_HAND_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_ARM_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOREARM_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_HAND_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.LEFT_THIGH_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.LEFT_CALF_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOOT_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_THIGH_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_CALF_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOOT_MUSCLE.getId())
							.setStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("15000"))
							.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.02"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("6"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("2"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.015"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.02"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.025"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("12"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.025"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("2"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("2"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.04"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.025"))
			);

	public static RegistryObject<CultivationStage> BODY_SKIN_FORGING = STAGE_REGISTER
			.register("body_skin_forging",
					() -> new BodyCultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_forging_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_muscle_forging"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_bone_forging")
					)
							.unlockPart(WuxiaBodyParts.LEFT_ARM_SKIN.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOREARM_SKIN.getId())
							.unlockPart(WuxiaBodyParts.LEFT_HAND_SKIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_ARM_SKIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOREARM_SKIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_HAND_SKIN.getId())
							.unlockPart(WuxiaBodyParts.LEFT_THIGH_SKIN.getId())
							.unlockPart(WuxiaBodyParts.LEFT_CALF_SKIN.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOOT_SKIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_THIGH_SKIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_CALF_SKIN.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOOT_SKIN.getId())
							.setStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("33000"))
							.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.02"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("8"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("3"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.02"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.025"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.03"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("15"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.03"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("2"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.05"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.035"))
			);

	public static RegistryObject<CultivationStage> BODY_BONE_FORGING = STAGE_REGISTER
			.register("body_bone_forging",
					() -> new BodyCultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_forging_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_skin_forging"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_torso_forging")
					)
							.unlockPart(WuxiaBodyParts.LEFT_ARM_BONE.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOREARM_BONE.getId())
							.unlockPart(WuxiaBodyParts.LEFT_HAND_BONE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_ARM_BONE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOREARM_BONE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_HAND_BONE.getId())
							.unlockPart(WuxiaBodyParts.LEFT_THIGH_BONE.getId())
							.unlockPart(WuxiaBodyParts.LEFT_CALF_BONE.getId())
							.unlockPart(WuxiaBodyParts.LEFT_FOOT_BONE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_THIGH_BONE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_CALF_BONE.getId())
							.unlockPart(WuxiaBodyParts.RIGHT_FOOT_BONE.getId())
							.setStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("60000"))
							.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.03"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("12"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("4"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.025"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.03"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.03"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("18"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.04"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("3"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("3"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.06"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.045"))
							.setStat(System.BODY, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);

	public static RegistryObject<CultivationStage> BODY_TORSO_FORGING = STAGE_REGISTER
			.register("body_torso_forging",
					() -> new BodyCultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_forging_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_bone_forging"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_viscera_tempering")
					)
							.unlockPart(WuxiaBodyParts.ABDOMEN_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.PECTORALS_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.LOWER_BACK_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.UPPER_BACK_MUSCLE.getId())
							.unlockPart(WuxiaBodyParts.ABDOMEN_SKIN.getId())
							.unlockPart(WuxiaBodyParts.PECTORALS_SKIN.getId())
							.unlockPart(WuxiaBodyParts.LOWER_BACK_SKIN.getId())
							.unlockPart(WuxiaBodyParts.UPPER_BACK_SKIN.getId())
							.unlockPart(WuxiaBodyParts.RIB_CAGE_BONE.getId())
							.unlockPart(WuxiaBodyParts.LOWER_SPINE_BONE.getId())
							.unlockPart(WuxiaBodyParts.UPPER_SPINE_BONE.getId())
							.setStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("100000"))
							.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.05"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("22"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("6"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.04"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.04"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.035"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("28"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.08"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("4"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("5"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.1"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.06"))
			);

	public static RegistryObject<CultivationStage> BODY_VISCERA_TEMPERING = STAGE_REGISTER
			.register("body_viscera_tempering",
					() -> new BodyCultivationStage(
							System.BODY,
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_torso_tempering"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_skin_forging"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "body_viscera_tempering") //TODO remove this loop
					)
							.unlockPart(WuxiaBodyParts.HEART.getId())
							.unlockPart(WuxiaBodyParts.LIVER.getId())
							.unlockPart(WuxiaBodyParts.SPLEEN.getId())
							.unlockPart(WuxiaBodyParts.LUNGS.getId())
							.unlockPart(WuxiaBodyParts.BLADDER.getId())
							.unlockPart(WuxiaBodyParts.STOMACH.getId())
							.unlockPart(WuxiaBodyParts.GALLBLADDER.getId())
							.unlockPart(WuxiaBodyParts.LARGE_INTESTINE.getId())
							.unlockPart(WuxiaBodyParts.SMALL_INTESTINE.getId())
							.unlockPart(WuxiaBodyParts.KIDNEYS.getId())
							.setStat(System.BODY, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("160000"))
							.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.04"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("16"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("6"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.03"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.03"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.035"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("16"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.05"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("3"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("2"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.07"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.05"))
							.setStat(System.BODY, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);

	//*********************************
	// divine stages
	//*********************************

	public static RegistryObject<CultivationStage> DIVINE_MORTAL_STAGE = STAGE_REGISTER
			.register("divine_mortal_stage",
					() -> new CultivationStage(
							System.DIVINE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "divine_mortal_realm"),
							null, null
					)
							.setStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("100"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.0001"))
			);

	//*********************************
	// essence stages
	//*********************************

	public static RegistryObject<CultivationStage> ESSENCE_MORTAL_STAGE = STAGE_REGISTER
			.register("essence_mortal_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_mortal_realm"),
							null,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_gathering_stage")
					)
							.cannotHaveBarrier()
							.cannotRegenEnergy()
							.setOnCultivate(cultivateFlatAmounts(BigDecimal.ONE, BigDecimal.ONE))
							.setOnCultivationFailure(cultivateFailureEnergy(BigDecimal.ONE))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("1000")) //1k
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_GATHERING_STAGE = STAGE_REGISTER
			.register("essence_qi_gathering_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_mortal_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_pathways_stage")
					)
							.cannotConvertToFood()
							.cannotHaveBarrier()
							.cannotRegenEnergy()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("2.5"), BigDecimal.ONE))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("2.5")))
							.addSkill(WuxiaSkillAspects.PUNCH.getId())
							.addSkill(WuxiaSkillAspects.SELF.getId())
							.addSkill(WuxiaSkillAspects.ATTACK.getId())
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("9000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.01"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("10"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("8"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.04"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.001"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.001"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("5"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("1"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("1"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.001"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("1"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.1"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.5"))
							.setStat(System.ESSENCE, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_PATHWAYS_STAGE = STAGE_REGISTER
			.register("essence_qi_pathways_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_gathering_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_condensation_stage")
					)
							.cannotConvertToFood()
							.cannotHaveBarrier()
							.cannotRegenEnergy()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("4"), BigDecimal.ONE))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("4")))
							.addSkill(WuxiaSkillAspects.BREAK.getId())
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("16000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("3"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("2"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.02"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.001"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.002"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("6"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("1"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("1"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.001"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("2"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.2"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.6"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.01"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.005"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_CONDENSATION_STAGE = STAGE_REGISTER
			.register("essence_qi_condensation_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_pathways_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_phenomenon_stage")
					)
							.cannotConvertToFood()
							.cannotHaveBarrier()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("4"), new BigDecimal("2.5")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("4")))
							.addSkill(WuxiaSkillAspects.CHOP.getId())
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("35000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.01"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("3"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("3"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.02"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.001"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.003"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("7"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.001"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("1.5"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("1.5"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.002"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("3"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.3"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.7"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.02"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.01"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_PHENOMENON_STAGE = STAGE_REGISTER
			.register("essence_qi_phenomenon_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_gathering_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_condensation_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_shaping_stage")
					)
							.cannotConvertToFood()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("8"), new BigDecimal("4")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("8")))
							.addSkill(WuxiaSkillAspects.SHOOT.getId())
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("76000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.015"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("5"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("4"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.03"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.002"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.004"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("10"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.001"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("2.5"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.01"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("2.5"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.005"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("5"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.4"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.8"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("5"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.01"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.03"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.03"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.015"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.05"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.04"))
			);

	//Change realms -> big leap in stats
	public static RegistryObject<CultivationStage> ESSENCE_QI_SHAPING_STAGE = STAGE_REGISTER
			.register("essence_qi_shaping_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "foundation_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_phenomenon_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_molding_stage")
					)
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("10"), new BigDecimal("4")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("2.5")))
							.addSkill(WuxiaSkillAspects.SWORD_FLIGHT.getId())
							.addSkill(WuxiaSkillAspects.BEAM.getId())
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("133000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.02"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("43"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("14"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.12"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.008"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.015"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("25"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("4"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.01"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("4"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.01"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("4"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.6"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("1"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("3"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("2"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("6"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.03"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.07"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.04"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.025"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.12"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.12"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.03"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.05"))
							.setStat(System.ESSENCE, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_MOLDING_STAGE = STAGE_REGISTER
			.register("essence_qi_molding_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "foundation_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_shaping_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_solidification_stage")
					)
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("12"), new BigDecimal("5")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("8")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("200000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.02"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("12"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("2"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.03"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.003"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.007"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("18"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.005"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("6"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.01"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("6"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.02"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("5"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.8"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("1.5"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("8"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.04"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.09"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.04"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.03"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.05"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.04"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.005"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.008"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_QI_SOLIDIFICATION_STAGE = STAGE_REGISTER
			.register("essence_qi_solidification_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "foundation_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_molding_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_core_shaping_stage")
					)
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("16"), new BigDecimal("7")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("12")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("430000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.04"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("12"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("3"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.04"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.004"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.009"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("25"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.007"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("10"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.02"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("10"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.03"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("6"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("1"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("2"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("2"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("1"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("11"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.05"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.11"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.05"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.035"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.07"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.06"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.005"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.008"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_CORE_SHAPING_STAGE = STAGE_REGISTER
			.register("essence_core_shaping_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "foundation_establishment_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_qi_solidification_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_golden_core_stage")
					)
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("25"), new BigDecimal("12")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("18")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("1300000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.008"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("22"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("4"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.04"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.004"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.009"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("42"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.023"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("15"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.02"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("15"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.05"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("7"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("1.2"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("2.5"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("2"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("2"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("15"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.07"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.18"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.06"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.04"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.2"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.16"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.01"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.014"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_GOLDEN_CORE_STAGE = STAGE_REGISTER
			.register("essence_golden_core_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_revolving_core_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_core_shaping_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_core_expansion_stage")
					)
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("35"), new BigDecimal("15")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("25")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("1800000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.13"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("86"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("20"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.14"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.014"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.022"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("84"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.012"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("12"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.03"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("12"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.06"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("9"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("1.5"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("3"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("10"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("6"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("20"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.1"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.2"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.08"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.05"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.26"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.22"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.06"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.08"))
							.setStat(System.ESSENCE, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_CORE_EXPANSION_STAGE = STAGE_REGISTER
			.register("essence_core_expansion_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_revolving_core_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_golden_core_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_revolving_core_stage")
					)
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("48"), new BigDecimal("18")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("35")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("3000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.2"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("14"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("6"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.06"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.006"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.009"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("56"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.02"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("10"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.016"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("10"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.06"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("7"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("1.9"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("2"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("4"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("3"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("14"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.07"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.11"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.06"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.04"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.1"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.08"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.01"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.014"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_REVOLVING_CORE_STAGE = STAGE_REGISTER
			.register("essence_revolving_core_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_revolving_core_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_core_expansion_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_immortal_transformation_stage")
					)
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("64"), new BigDecimal("23")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("48")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("5000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.3"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("16"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("7"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.06"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.006"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.009"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("72"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.025"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("12"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.02"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("12"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.06"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("8"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("2.6"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("3"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("5"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("3"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("16"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.09"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.15"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.06"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.04"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.12"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.1"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.01"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.014"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_IMMORTAL_TRANSFORMATION_STAGE = STAGE_REGISTER
			.register("essence_immortal_transformation_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_revolving_core_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_revolving_core_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_immortal_pond_stage")
					)
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("86"), new BigDecimal("28")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("64")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("8000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.5"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("34"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("9"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.08"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.009"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.014"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("96"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.04"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("16"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.03"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("16"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.08"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("8"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("4.8"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("4"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("8"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("4"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("22"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.12"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.2"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.08"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.06"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.16"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.12"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.02"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.025"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_IMMORTAL_POND_STAGE = STAGE_REGISTER
			.register("essence_immortal_pond_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_immortal_sea_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_immortal_transformation_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_river_expansion_stage")
					)
							.setFlight()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("104"), new BigDecimal("32")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("78")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("120000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.8"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("134"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("30"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.18"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.022"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.036"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("144"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.1"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("14"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.06"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("14"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.1"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("12"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("5.8"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("8"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("15"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("12"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("36"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.18"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.3"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.1"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.07"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.56"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.42"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.1"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.12"))
							.setStat(System.ESSENCE, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_RIVER_EXPANSION_STAGE = STAGE_REGISTER
			.register("essence_river_expansion_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_immortal_sea_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_immortal_pond_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_boundless_sea_stage")
					)
							.setFlight()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("128"), new BigDecimal("36")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("78")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("70000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("1.4"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("24"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("12"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.8"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.009"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.014"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("92"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.06"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("12"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.02"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("12"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.08"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("9"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("7.8"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("9"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("8"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("9"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("22"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.12"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.22"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.07"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.05"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.16"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.16"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.03"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.03"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_BOUNDLESS_SEA_STAGE = STAGE_REGISTER
			.register("essence_boundless_sea_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_immortal_sea_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_river_expansion_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_immortal_ocean_stage")
					)
							.setFlight()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("164"), new BigDecimal("40")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("120")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("120000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("2.4"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("28"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("14"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.8"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.009"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.013"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("124"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.08"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("16"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.02"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("16"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.1"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("10"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("8.8"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("11"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("10"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("11"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("24"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.16"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.28"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.08"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.06"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.2"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.18"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.04"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.05"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_IMMORTAL_OCEAN_STAGE = STAGE_REGISTER
			.register("essence_immortal_ocean_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_immortal_sea_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_boundless_sea_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_ocean_obliteration_stage")
					)
							.setFlight()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("228"), new BigDecimal("45")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("178")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("270000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("3.8"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("44"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("16"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.8"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.016"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.026"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("172"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.1"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("20"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.06"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("20"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.1"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("10"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("9.5"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("14"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("13"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("14"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("30"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.22"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.38"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.09"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.07"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.28"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.32"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.05"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.06"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_OCEAN_OBLITERATION_STAGE = STAGE_REGISTER
			.register("essence_ocean_obliteration_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_void_nebula_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_immortal_ocean_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_starry_field_stage")
					)
							.setFlight()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("228"), new BigDecimal("45")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("178")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("540000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("5.4"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("237"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("54"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.24"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.036"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.036"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("232"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.3"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("32"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.08"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("32"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.016"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("16"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("13.5"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("17"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("21"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("17"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("62"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.36"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.48"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.14"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.12"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.76"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.64"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.2"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.2"))
							.setStat(System.ESSENCE, PlayerSystemStat.ADDITIONAL_GRID_RADIUS, new BigDecimal("1"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_STARRY_FIELD_STAGE = STAGE_REGISTER
			.register("essence_starry_field_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_void_nebula_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_ocean_obliteration_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_galaxy_stage")
					)
							.setFlight()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("228"), new BigDecimal("45")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("178")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("1860000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("6.8"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("36"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("18"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.12"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.012"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.014"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("112"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.08"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("14"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.03"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("14"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.004"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("10"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("14.8"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("19"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("12"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("9"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("28"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.18"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.3"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.09"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.07"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.22"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.22"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.04"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.045"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_GALAXY_STAGE = STAGE_REGISTER
			.register("essence_galaxy_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_void_nebula_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_starry_field_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_void_nebula_stage")
					)
							.setFlight()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("228"), new BigDecimal("45")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("178")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("37200000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("9.1"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("40"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("22"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.12"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.012"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.014"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("148"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.1"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("18"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.03"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("18"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.007"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("12"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("19.5"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("24"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("14"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("13"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("32"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.22"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.36"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.11"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.09"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.26"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.24"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.06"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.08"))
			);

	public static RegistryObject<CultivationStage> ESSENCE_VOID_NEBULA_STAGE = STAGE_REGISTER
			.register("essence_void_nebula_stage",
					() -> new EssenceCultivationStage(
							System.ESSENCE,
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_void_nebula_realm"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_galaxy_stage"),
							new ResourceLocation(WuxiaCraft.MOD_ID, "essence_void_nebula_stage")
					)
							.setFlight()
							.setOnCultivate(cultivateFlatAmounts(new BigDecimal("228"), new BigDecimal("45")))
							.setOnCultivationFailure(cultivateFailureEnergy(new BigDecimal("178")))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE, new BigDecimal("62800000000"))
							.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("12.07"))
							.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("68"))
							.setStat(PlayerStat.STRENGTH, new BigDecimal("32"))
							.setStat(PlayerStat.AGILITY, new BigDecimal("0.12"))
							.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.02"))
							.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.022"))
							.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("212"))
							.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.16"))
							.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("23"))
							.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.08"))
							.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("23"))
							.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.009"))
							.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("12"))
							.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("22.3"))
							.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("28"))
							.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("20"))
							.setStat(PlayerElementalStat.PIERCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("16"))
							.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("38"))
							.setStat(PlayerStat.BARRIER_REGEN, new BigDecimal("0.26"))
							.setStat(PlayerStat.BARRIER_REGEN_COST, new BigDecimal("0.38"))
							.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.13"))
							.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.11"))
							.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.40"))
							.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.36"))
							.setStat(PlayerStat.HUNGER_REGEN, new BigDecimal("0.1"))
							.setStat(PlayerStat.HUNGER_REGEN_COST, new BigDecimal("0.12"))
			);

	public static Consumer<Player> cultivateFlatAmounts(BigDecimal energy, BigDecimal cultivationBase) {
		return player -> {
			var cultivation = Cultivation.get(player);
			var essenceData = cultivation.getSystemData(System.ESSENCE);
			if (essenceData.consumeEnergy(energy)) {
				cultivation.addCultivationBase(player, System.ESSENCE, cultivationBase);
			}
		};
	}

	public static Consumer<Player> cultivateFailureEnergy(BigDecimal energy) {
		return player -> {
			var cultivation = Cultivation.get(player);
			var essenceData = cultivation.getSystemData(System.ESSENCE);
			essenceData.consumeEnergy(energy);
		};
	}

}
