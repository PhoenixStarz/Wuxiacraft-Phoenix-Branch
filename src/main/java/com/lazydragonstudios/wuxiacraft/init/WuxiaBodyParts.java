package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.body.BodyPart;
import com.lazydragonstudios.wuxiacraft.cultivation.body.BodyPartGroup;
import com.lazydragonstudios.wuxiacraft.cultivation.body.BodyPartType;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.BodyStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class WuxiaBodyParts {

	public static DeferredRegister<BodyPartType> BODY_PART_TYPES = DeferredRegister.create(new ResourceLocation(WuxiaCraft.MOD_ID, "body_part_types"), WuxiaCraft.MOD_ID);

	public static DeferredRegister<BodyPart> BODY_PARTS = DeferredRegister.create(new ResourceLocation(WuxiaCraft.MOD_ID, "body_parts"), WuxiaCraft.MOD_ID);

	public static RegistryObject<BodyPartType> MUSCLE = BODY_PART_TYPES.register("muscle",
			() -> new BodyPartType()
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.003"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPartType> TENDON = BODY_PART_TYPES.register("tendon",
			() -> new BodyPartType()
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.001"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPartType> BONE = BODY_PART_TYPES.register("bone",
			() -> new BodyPartType()
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.0005"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.0005"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.002"))
	);

	public static RegistryObject<BodyPartType> MARROW = BODY_PART_TYPES.register("marrow",
			() -> new BodyPartType()
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.002"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.002"))
	);

	public static RegistryObject<BodyPartType> VEINS = BODY_PART_TYPES.register("veins",
			() -> new BodyPartType()
					.setStat(System.BODY, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.008"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.00002"))
	);

	public static RegistryObject<BodyPartType> MERIDIANS = BODY_PART_TYPES.register("meridians",
			() -> new BodyPartType()
					.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.008"))
					.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.00002"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.0000002"))
	);

	public static RegistryObject<BodyPartType> SKIN = BODY_PART_TYPES.register("skin",
			() -> new BodyPartType()
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.004"))
	);

	public static RegistryObject<BodyPartType> ORGAN = BODY_PART_TYPES.register("organ",
			() -> new BodyPartType()
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.00001"))
	);

	public static RegistryObject<BodyPart> LEFT_ARM_TENDON = BODY_PARTS.register("left_arm_tendon",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_FOREARM_TENDON = BODY_PARTS.register("left_forearm_tendon",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_HAND_TENDON = BODY_PARTS.register("left_hand_tendon",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_ARM_TENDON = BODY_PARTS.register("right_arm_tendon",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOREARM_TENDON = BODY_PARTS.register("right_forearm_tendon",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_HAND_TENDON = BODY_PARTS.register("right_hand_tendon",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_THIGH_TENDON = BODY_PARTS.register("left_thigh_tendon",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_CALF_TENDON = BODY_PARTS.register("left_calf_tendon",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_FOOT_TENDON = BODY_PARTS.register("left_foot_tendon",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_THIGH_TENDON = BODY_PARTS.register("right_thigh_tendon",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_CALF_TENDON = BODY_PARTS.register("right_calf_tendon",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOOT_TENDON = BODY_PARTS.register("right_foot_tendon",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, TENDON.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("10"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_ARM_MUSCLE = BODY_PARTS.register("left_arm_muscle",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, MUSCLE.get())
					.setIsRelatedToPart(LEFT_ARM_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.01"))
	);

	public static RegistryObject<BodyPart> LEFT_FOREARM_MUSCLE = BODY_PARTS.register("left_forearm_muscle",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, MUSCLE.get())
					.setIsRelatedToPart(LEFT_FOREARM_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> LEFT_HAND_MUSCLE = BODY_PARTS.register("left_hand_muscle",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, MUSCLE.get())
					.setIsRelatedToPart(LEFT_HAND_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> RIGHT_ARM_MUSCLE = BODY_PARTS.register("right_arm_muscle",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, MUSCLE.get())
					.setIsRelatedToPart(RIGHT_ARM_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOREARM_MUSCLE = BODY_PARTS.register("right_forearm_muscle",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, MUSCLE.get())
					.setIsRelatedToPart(RIGHT_FOREARM_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> RIGHT_HAND_MUSCLE = BODY_PARTS.register("right_hand_muscle",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, MUSCLE.get())
					.setIsRelatedToPart(RIGHT_HAND_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> LEFT_THIGH_MUSCLE = BODY_PARTS.register("left_thigh_muscle",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, MUSCLE.get())
					.setIsRelatedToPart(LEFT_THIGH_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> LEFT_CALF_MUSCLE = BODY_PARTS.register("left_calf_muscle",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, MUSCLE.get())
					.setIsRelatedToPart(LEFT_CALF_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> LEFT_FOOT_MUSCLE = BODY_PARTS.register("left_foot_muscle",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, MUSCLE.get())
					.setIsRelatedToPart(LEFT_FOOT_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> RIGHT_THIGH_MUSCLE = BODY_PARTS.register("right_thigh_muscle",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, MUSCLE.get())
					.setIsRelatedToPart(RIGHT_THIGH_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> RIGHT_CALF_MUSCLE = BODY_PARTS.register("right_calf_muscle",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, MUSCLE.get())
					.setIsRelatedToPart(RIGHT_CALF_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOOT_MUSCLE = BODY_PARTS.register("right_foot_muscle",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, MUSCLE.get())
					.setIsRelatedToPart(RIGHT_FOOT_TENDON.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.001"))
	);

	public static RegistryObject<BodyPart> PECTORALS_MUSCLE = BODY_PARTS.register("pectorals_muscle",
			() -> new BodyPart(BodyPartGroup.TORSO, MUSCLE.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.STRENGTH, new BigDecimal("0.007"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> ABDOMEN_MUSCLE = BODY_PARTS.register("abdomen_muscle",
			() -> new BodyPart(BodyPartGroup.TORSO, MUSCLE.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.005"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> UPPER_BACK_MUSCLE = BODY_PARTS.register("upper_back_muscle",
			() -> new BodyPart(BodyPartGroup.TORSO, MUSCLE.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> LOWER_BACK_MUSCLE = BODY_PARTS.register("lower_back_muscle",
			() -> new BodyPart(BodyPartGroup.TORSO, MUSCLE.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> LEFT_ARM_MARROW = BODY_PARTS.register("left_arm_marrow",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_FOREARM_MARROW = BODY_PARTS.register("left_forearm_marrow",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_HAND_MARROW = BODY_PARTS.register("left_hand_marrow",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_ARM_MARROW = BODY_PARTS.register("right_arm_marrow",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOREARM_MARROW = BODY_PARTS.register("right_forearm_marrow",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_HAND_MARROW = BODY_PARTS.register("right_hand_marrow",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_THIGH_MARROW = BODY_PARTS.register("left_thigh_marrow",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_CALF_MARROW = BODY_PARTS.register("left_calf_marrow",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_FOOT_MARROW = BODY_PARTS.register("left_foot_marrow",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_THIGH_MARROW = BODY_PARTS.register("right_thigh_marrow",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_CALF_MARROW = BODY_PARTS.register("right_calf_marrow",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOOT_MARROW = BODY_PARTS.register("right_foot_marrow",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, MARROW.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("20"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_ARM_BONE = BODY_PARTS.register("left_arm_bone",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, BONE.get())
					.setIsRelatedToPart(LEFT_ARM_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.AGILITY, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LEFT_FOREARM_BONE = BODY_PARTS.register("left_forearm_bone",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, BONE.get())
					.setIsRelatedToPart(LEFT_FOREARM_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> LEFT_HAND_BONE = BODY_PARTS.register("left_hand_bone",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, BONE.get())
					.setIsRelatedToPart(LEFT_HAND_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> RIGHT_ARM_BONE = BODY_PARTS.register("right_arm_bone",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, BONE.get())
					.setIsRelatedToPart(RIGHT_ARM_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOREARM_BONE = BODY_PARTS.register("right_forearm_bone",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, BONE.get())
					.setIsRelatedToPart(RIGHT_FOREARM_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> RIGHT_HAND_BONE = BODY_PARTS.register("right_hand_bone",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, BONE.get())
					.setIsRelatedToPart(RIGHT_HAND_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> LEFT_THIGH_BONE = BODY_PARTS.register("left_thigh_bone",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, BONE.get())
					.setIsRelatedToPart(LEFT_THIGH_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> LEFT_CALF_BONE = BODY_PARTS.register("left_calf_bone",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, BONE.get())
					.setIsRelatedToPart(LEFT_CALF_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> LEFT_FOOT_BONE = BODY_PARTS.register("left_foot_bone",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, BONE.get())
					.setIsRelatedToPart(LEFT_FOOT_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> RIGHT_THIGH_BONE = BODY_PARTS.register("right_thigh_bone",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, BONE.get())
					.setIsRelatedToPart(RIGHT_THIGH_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> RIGHT_CALF_BONE = BODY_PARTS.register("right_calf_bone",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, BONE.get())
					.setIsRelatedToPart(RIGHT_CALF_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOOT_BONE = BODY_PARTS.register("right_foot_bone",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, BONE.get())
					.setIsRelatedToPart(RIGHT_FOOT_MARROW.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> RIB_CAGE_BONE = BODY_PARTS.register("rib_cage_bone",
			() -> new BodyPart(BodyPartGroup.TORSO, BONE.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> UPPER_SPINE_BONE = BODY_PARTS.register("upper_spine_bone",
			() -> new BodyPart(BodyPartGroup.TORSO, BONE.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> LOWER_SPINE_BONE = BODY_PARTS.register("lower_spine_bone",
			() -> new BodyPart(BodyPartGroup.TORSO, BONE.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> SKULL_BONE = BODY_PARTS.register("skull_bone",
			() -> new BodyPart(BodyPartGroup.HEAD, BONE.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("150"))
					.setStat(PlayerStat.HEALTH, new BigDecimal("0.003"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.0003"))
	);

	public static RegistryObject<BodyPart> LEFT_ARM_SKIN = BODY_PARTS.register("left_arm_skin",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> LEFT_FOREARM_SKIN = BODY_PARTS.register("left_forearm_skin",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> LEFT_HAND_SKIN = BODY_PARTS.register("left_hand_skin",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> RIGHT_ARM_SKIN = BODY_PARTS.register("right_arm_skin",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOREARM_SKIN = BODY_PARTS.register("right_forearm_skin",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> RIGHT_HAND_SKIN = BODY_PARTS.register("right_hand_skin",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> LEFT_THIGH_SKIN = BODY_PARTS.register("left_thigh_skin",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> LEFT_CALF_SKIN = BODY_PARTS.register("left_calf_skin",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> LEFT_FOOT_SKIN = BODY_PARTS.register("left_foot_skin",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> RIGHT_THIGH_SKIN = BODY_PARTS.register("right_thigh_skin",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> RIGHT_CALF_SKIN = BODY_PARTS.register("right_calf_skin",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOOT_SKIN = BODY_PARTS.register("right_foot_skin",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.02"))
	);

	public static RegistryObject<BodyPart> PECTORALS_SKIN = BODY_PARTS.register("pectorals_skin",
			() -> new BodyPart(BodyPartGroup.TORSO, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.4"))
	);

	public static RegistryObject<BodyPart> ABDOMEN_SKIN = BODY_PARTS.register("abdomen_skin",
			() -> new BodyPart(BodyPartGroup.TORSO, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.4"))
	);

	public static RegistryObject<BodyPart> UPPER_BACK_SKIN = BODY_PARTS.register("upper_back_skin",
			() -> new BodyPart(BodyPartGroup.TORSO, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.4"))
	);

	public static RegistryObject<BodyPart> LOWER_BACK_SKIN = BODY_PARTS.register("lower_back_skin",
			() -> new BodyPart(BodyPartGroup.TORSO, SKIN.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("50"))
					.setStat(PlayerElementalStat.RESISTANCE, WuxiaElements.PHYSICAL.getId(), new BigDecimal("0.4"))
	);

	public static RegistryObject<BodyPart> LEFT_ARM_VEIN = BODY_PARTS.register("left_arm_vein",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> LEFT_FOREARM_VEIN = BODY_PARTS.register("left_forearm_vein",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> LEFT_HAND_VEIN = BODY_PARTS.register("left_hand_vein",
			() -> new BodyPart(BodyPartGroup.LEFT_ARM, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> RIGHT_ARM_VEIN = BODY_PARTS.register("right_arm_vein",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOREARM_VEIN = BODY_PARTS.register("right_forearm_vein",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> RIGHT_HAND_VEIN = BODY_PARTS.register("right_hand_vein",
			() -> new BodyPart(BodyPartGroup.RIGHT_ARM, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> LEFT_THIGH_VEIN = BODY_PARTS.register("left_thigh_vein",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> LEFT_CALF_VEIN = BODY_PARTS.register("left_calf_vein",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> LEFT_FOOT_VEIN = BODY_PARTS.register("left_foot_vein",
			() -> new BodyPart(BodyPartGroup.LEFT_LEG, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.005"))
	);

	public static RegistryObject<BodyPart> RIGHT_THIGH_VEIN = BODY_PARTS.register("right_thigh_vein",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_CALF_VEIN = BODY_PARTS.register("right_calf_vein",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> RIGHT_FOOT_VEIN = BODY_PARTS.register("right_foot_vein",
			() -> new BodyPart(BodyPartGroup.RIGHT_LEG, VEINS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("60"))
					.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.003"))
	);

	public static RegistryObject<BodyPart> LIVER = BODY_PARTS.register("liver",
			() -> new BodyPart(BodyPartGroup.TORSO, ORGAN.get())
					.setElementalAffinity(WuxiaElements.WOOD.getId())
					.setIsRelatedToPart(new ResourceLocation(WuxiaCraft.MOD_ID, "bladder"))
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("500"))
	);

	public static RegistryObject<BodyPart> HEART = BODY_PARTS.register("heart",
			() -> new BodyPart(BodyPartGroup.TORSO, ORGAN.get())
					.setElementalAffinity(WuxiaElements.FIRE.getId())
					.setIsRelatedToPart(new ResourceLocation(WuxiaCraft.MOD_ID, "gallbladder"))
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("500"))
	);

	public static RegistryObject<BodyPart> SPLEEN = BODY_PARTS.register("spleen",
			() -> new BodyPart(BodyPartGroup.TORSO, ORGAN.get())
					.setElementalAffinity(WuxiaElements.EARTH.getId())
					.setIsRelatedToPart(new ResourceLocation(WuxiaCraft.MOD_ID, "small_intestine"))
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("500"))
	);

	public static RegistryObject<BodyPart> LUNGS = BODY_PARTS.register("lungs",
			() -> new BodyPart(BodyPartGroup.TORSO, ORGAN.get())
					.setElementalAffinity(WuxiaElements.METAL.getId())
					.setIsRelatedToPart(new ResourceLocation(WuxiaCraft.MOD_ID, "stomach"))
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("500"))
	);

	public static RegistryObject<BodyPart> KIDNEYS = BODY_PARTS.register("kidneys",
			() -> new BodyPart(BodyPartGroup.TORSO, ORGAN.get())
					.setElementalAffinity(WuxiaElements.WATER.getId())
					.setIsRelatedToPart(new ResourceLocation(WuxiaCraft.MOD_ID, "large_intestine"))
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("500"))
	);

	public static RegistryObject<BodyPart> GALLBLADDER = BODY_PARTS.register("gallbladder",
			() -> new BodyPart(BodyPartGroup.TORSO, ORGAN.get())
					.setElementalAffinity(WuxiaElements.WOOD.getId())
					.setIsRelatedToPart(new ResourceLocation(WuxiaCraft.MOD_ID, "kidneys"))
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("500"))
	);

	public static RegistryObject<BodyPart> SMALL_INTESTINE = BODY_PARTS.register("small_intestine",
			() -> new BodyPart(BodyPartGroup.TORSO, ORGAN.get())
					.setElementalAffinity(WuxiaElements.FIRE.getId())
					.setIsRelatedToPart(new ResourceLocation(WuxiaCraft.MOD_ID, "liver"))
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("500"))
	);

	public static RegistryObject<BodyPart> STOMACH = BODY_PARTS.register("stomach",
			() -> new BodyPart(BodyPartGroup.TORSO, ORGAN.get())
					.setElementalAffinity(WuxiaElements.EARTH.getId())
					.setIsRelatedToPart(new ResourceLocation(WuxiaCraft.MOD_ID, "heart"))
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("500"))
	);

	public static RegistryObject<BodyPart> LARGE_INTESTINE = BODY_PARTS.register("large_intestine",
			() -> new BodyPart(BodyPartGroup.TORSO, ORGAN.get())
					.setElementalAffinity(WuxiaElements.METAL.getId())
					.setIsRelatedToPart(new ResourceLocation(WuxiaCraft.MOD_ID, "spleen"))
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("500"))
	);

	public static RegistryObject<BodyPart> BLADDER = BODY_PARTS.register("bladder",
			() -> new BodyPart(BodyPartGroup.TORSO, ORGAN.get())
					.setElementalAffinity(WuxiaElements.WATER.getId())
					.setIsRelatedToPart(new ResourceLocation(WuxiaCraft.MOD_ID, "lungs"))
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("500"))
	);

	public static RegistryObject<BodyPart> LEFT_EYE = BODY_PARTS.register("left_eye",
			() -> new BodyPart(BodyPartGroup.HEAD, ORGAN.get())
					.setElementalAffinity(WuxiaElements.WOOD.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("600"))
	);

	public static RegistryObject<BodyPart> RIGHT_EYE = BODY_PARTS.register("right_eye",
			() -> new BodyPart(BodyPartGroup.HEAD, ORGAN.get())
					.setElementalAffinity(WuxiaElements.WOOD.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("600"))
	);

	public static RegistryObject<BodyPart> LEFT_EAR = BODY_PARTS.register("left_ear",
			() -> new BodyPart(BodyPartGroup.HEAD, ORGAN.get())
					.setElementalAffinity(WuxiaElements.WATER.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("600"))
	);

	public static RegistryObject<BodyPart> RIGHT_EAR = BODY_PARTS.register("right_ear",
			() -> new BodyPart(BodyPartGroup.HEAD, ORGAN.get())
					.setElementalAffinity(WuxiaElements.WATER.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("600"))
	);

	public static RegistryObject<BodyPart> NOSE = BODY_PARTS.register("nose",
			() -> new BodyPart(BodyPartGroup.HEAD, ORGAN.get())
					.setElementalAffinity(WuxiaElements.METAL.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("600"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.1"))
	);

	public static RegistryObject<BodyPart> MOUTH = BODY_PARTS.register("mouth",
			() -> new BodyPart(BodyPartGroup.HEAD, ORGAN.get())
					.setElementalAffinity(WuxiaElements.EARTH.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("600"))
	);

	public static RegistryObject<BodyPart> TONGUE = BODY_PARTS.register("tongue",
			() -> new BodyPart(BodyPartGroup.HEAD, ORGAN.get())
					.setElementalAffinity(WuxiaElements.FIRE.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("600"))
	);

	public static RegistryObject<BodyPart> BRAIN = BODY_PARTS.register("brain",
			() -> new BodyPart(BodyPartGroup.HEAD, ORGAN.get())
					.setElementalAffinity(WuxiaElements.LIGHTNING.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("600"))
					.setStat(System.BODY, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.75"))
					.setStat(System.DIVINE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.75"))
					.setStat(System.ESSENCE, PlayerSystemStat.CULTIVATION_SPEED, new BigDecimal("0.75"))
	);

	public static RegistryObject<BodyPart> ARM_TAI_YIN = BODY_PARTS.register("arm_tai_yin",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.METAL.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);
		
	public static RegistryObject<BodyPart> ARM_YANG_MING = BODY_PARTS.register("arm_yang_ming",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.METAL.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);	
	
	public static RegistryObject<BodyPart> LEG_YANG_MING = BODY_PARTS.register("leg_yang_ming",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.EARTH.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);	
	
	public static RegistryObject<BodyPart> LEG_TAI_YIN = BODY_PARTS.register("leg_tai_yin",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.EARTH.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);	
	
	public static RegistryObject<BodyPart> ARM_SHAO_YIN = BODY_PARTS.register("arm_shao_yin",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.FIRE.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);	
	
	public static RegistryObject<BodyPart> ARM_TAI_YANG = BODY_PARTS.register("arm_tai_yang",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.FIRE.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);	
	
	public static RegistryObject<BodyPart> LEG_TAI_YANG = BODY_PARTS.register("leg_tai_yang",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.WATER.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);	
	
	public static RegistryObject<BodyPart> LEG_SHAO_YIN = BODY_PARTS.register("leg_shao_yin",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.WATER.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);	
	
	public static RegistryObject<BodyPart> ARM_JUE_YIN = BODY_PARTS.register("arm_jue_yin",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.FIRE.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);

	public static RegistryObject<BodyPart> ARM_SHAO_YANG = BODY_PARTS.register("arm_shao_yang",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.FIRE.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);

	public static RegistryObject<BodyPart> LEG_SHAO_YANG = BODY_PARTS.register("leg_shao_yang",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.WOOD.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);

	public static RegistryObject<BodyPart> LEG_JUE_YIN = BODY_PARTS.register("leg_jue_yin",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.setElementalAffinity(WuxiaElements.WOOD.getId())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("800"))
	);

	public static RegistryObject<BodyPart> REN_MAI = BODY_PARTS.register("ren_mai",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("1200"))
					.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.003"))
					.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.002"))
	);
	public static RegistryObject<BodyPart> DU_MAI = BODY_PARTS.register("du_mai",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("1200"))
					.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.003"))
					.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.002"))
	);
	public static RegistryObject<BodyPart> CHONG_MAI = BODY_PARTS.register("chong_mai",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("1200"))
					.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.003"))
					.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.002"))
	);
	public static RegistryObject<BodyPart> DAI_MAI = BODY_PARTS.register("dai_mai",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("1200"))
					.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.003"))
					.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.002"))
	);
	public static RegistryObject<BodyPart> YIN_WEI_MAI = BODY_PARTS.register("yin_wei_mai",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("1200"))
					.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.003"))
					.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.002"))
	);
	public static RegistryObject<BodyPart> YANG_WEI_MAI = BODY_PARTS.register("yang_wei_mai",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("1200"))
					.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.003"))
					.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.002"))
	);
	public static RegistryObject<BodyPart> YIN_QIAO_MAI = BODY_PARTS.register("yin_qiao_mai",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("1200"))
					.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.003"))
					.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.002"))
	);
	public static RegistryObject<BodyPart> YANG_QIAO_MAI = BODY_PARTS.register("yang_qiao_mai",
			() -> new BodyPart(BodyPartGroup.MERIDIAN, MERIDIANS.get())
					.<BodyPart>setStat(BodyStat.FORGING_LIMIT, new BigDecimal("1200"))
					.setStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.003"))
					.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.002"))
	);
}
