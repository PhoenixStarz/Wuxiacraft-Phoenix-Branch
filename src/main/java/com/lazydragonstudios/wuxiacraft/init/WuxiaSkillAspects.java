package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.*;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator.*;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.*;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.modifier.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class WuxiaSkillAspects {

	public static DeferredRegister<SkillAspectType> ASPECTS = DeferredRegister.create(new ResourceLocation(WuxiaCraft.MOD_ID, "skill_aspects"), WuxiaCraft.MOD_ID);

	/**
	 * Directly hit ahead the caster, where the caster is looking at
	 */
	public static RegistryObject<SkillAspectType> PUNCH = ASPECTS.register("punch", () -> SkillAspectType.build(
			SkillTouchAspect::new
	));

	/**
	 * Throw in the direction caster is looking at
	 */
	public static RegistryObject<SkillAspectType> SHOOT = ASPECTS.register("shoot", () -> SkillAspectType.build(
			SkillShootAspect::new
	));

	/**
	 * Hits the caster
	 */
	public static RegistryObject<SkillAspectType> SELF = ASPECTS.register("self", () -> SkillAspectType.build(
			SkillSelfAspect::new
	));

	/**
	 * Sword flight, when hit something, activates hit
	 */
	public static RegistryObject<SkillAspectType> SWORD_FLIGHT = ASPECTS.register("sword_flight", () -> SkillAspectType.build(
			SkillSwordFlightActivator::new
	));

	/**
	 * Cast a wave ahead of the caster, hitting everything ahead of it
	 */
	public static RegistryObject<SkillAspectType> WAVE = ASPECTS.register("wave", () -> SkillAspectType.build(
			SkillWaveAspect::new
	));

	/**
	 * Kame-Hame-Ha, but with lots of particles, and it is actually rayTraced instead of using entities.
	 */
	public static RegistryObject<SkillAspectType> BEAM = ASPECTS.register("beam", () -> SkillAspectType.build(
			SkillBeamAspect::new
	));

	/**
	 * Hits in a spherical shape around the caster
	 */
	public static RegistryObject<SkillAspectType> BARRIER = ASPECTS.register("barrier", () -> SkillAspectType.build(SkillActivatorAspect::new));

	/**
	 * Similar to above, but only ahead of the caster
	 */
	public static RegistryObject<SkillAspectType> SHIELD = ASPECTS.register("shield", () -> SkillAspectType.build(SkillActivatorAspect::new));

	/**
	 * Hits everything around the caster
	 */
	public static RegistryObject<SkillAspectType> AREA = ASPECTS.register("area", () -> SkillAspectType.build(
			SkillAreaAspect::new
	));

	//TODO key of kings'law activator from botania

	//TODO add professions based activator and modifiers

	/**
	 * Keeps releasing multiple of the hit aspect
	 */
	public static RegistryObject<SkillAspectType> CHANNELING = ASPECTS.register("channeling", () -> SkillAspectType.build(SkillActivationModifierAspect::new));

	/**
	 * Charges the skill before casting increasing values
	 */
	public static RegistryObject<SkillAspectType> CHARGE = ASPECTS.register("charge", () -> SkillAspectType.build(SkillActivationModifierAspect::new));

	/**
	 * Similar to above, but only ahead of the caster
	 */
	public static RegistryObject<SkillAspectType> GRAVITY_MODIFIER = ASPECTS.register("gravity_modifier", () -> SkillAspectType.build(SkillActivationModifierAspect::new));

	/**
	 * Increases the range of activation for certain aspects
	 */
	public static RegistryObject<SkillAspectType> RANGE_MODIFIER = ASPECTS.register("range_modifier", () -> SkillAspectType.build(SkillActivationModifierAspect::new));

	/**
	 * Area of effect modifier (Activates around)
	 */
	public static RegistryObject<SkillAspectType> AREA_MODIFIER = ASPECTS.register("area_modifier", () -> SkillAspectType.build(SkillActivationModifierAspect::new));

	/**
	 * Similar to above, but only ahead of the caster
	 */
	public static RegistryObject<SkillAspectType> RADIUS_MODIFIER = ASPECTS.register("radius_modifier", () -> SkillAspectType.build(SkillActivationModifierAspect::new));


	// *********************************************
	//  On hit things, this is where the fun begins
	// ********************************************

	/**
	 * Creates an explosion on hit
	 */
	public static RegistryObject<SkillAspectType> EXPLOSION = ASPECTS.register("explosion", () -> SkillAspectType.build(
			SkillExplosionAspect::new
	));

	/**
	 * Directly dealing damage
	 */
	public static RegistryObject<SkillAspectType> ATTACK = ASPECTS.register("attack", () -> SkillAspectType.build(
			SkillAttackAspect::new
	));

	/**
	 * Fertilize plants
	 */
	public static RegistryObject<SkillAspectType> FERTILIZE = ASPECTS.register("fertilize", () -> SkillAspectType.build(
			SkillFertilizeAspect::new
	));

	/**
	 * Breaks blocks on hit
	 */
	public static RegistryObject<SkillAspectType> BREAK = ASPECTS.register("break", () -> SkillAspectType.build(
			SkillBreakAspect::new
	));

	/**
	 * Chops a tree instantly
	 */
	public static RegistryObject<SkillAspectType> CHOP = ASPECTS.register("chop", () -> SkillAspectType.build(
			SkillChopAspect::new
	));

	/**
	 * Generates a coffin of defined block modifier around hit place
	 */
	public static RegistryObject<SkillAspectType> COFFIN = ASPECTS.register("coffin", () -> SkillAspectType.build(
			SkillCoffinAspect::new
	));

	/**
	 * Ore mine blocks on hit
	 * ^ just made it a larger break skill
	 */
	public static RegistryObject<SkillAspectType> ORE_MINE = ASPECTS.register("ore_mine", () -> SkillAspectType.build(
			SkillOreMineAspect::new
	));

	/**
	 * Heals the target on hit
	 */
	public static RegistryObject<SkillAspectType> HEAL = ASPECTS.register("heal", () -> SkillAspectType.build(
			SkillHealAspect::new
	));

	/**
	 * Drains life from target on hit
	 */
	public static RegistryObject<SkillAspectType> LIFE_STEAL = ASPECTS.register("life_steal", () -> SkillAspectType.build(
			SkillLifeStealAspect::new
	));
	
	/**
	 * Summons minions
	 */
	public static RegistryObject<SkillAspectType> SUMMON_MINIONS = ASPECTS.register("summon_minions", () -> SkillAspectType.build(SkillHitAspect::new));


	/**
	 * Summons lightnings on the activation position
	 */
	public static RegistryObject<SkillAspectType> SUMMON_LIGHTNING = ASPECTS.register("summon_lightning", () -> SkillAspectType.build(
			SkillLightningAspect::new
	));
	

	// *********************************************
	//  On hit modifiers
	// ********************************************

	/**
	 * Teleports on hit, continuing on the direction of the hit
	 */
	public static RegistryObject<SkillAspectType> SPATIAL_TEARING = ASPECTS.register("spatial_tearing", () -> SkillAspectType.build(
			SkillSpatialTearingAspect::new
	));

	/**
	 * slowes down out target on hit, or speeds up self.
	 */
	public static RegistryObject<SkillAspectType> TEMPORAL_TEARING = ASPECTS.register("temporal_tearing", () -> SkillAspectType.build(
			SkillTemporalTearingAspect::new
	));

	/**
	 * Applies potion effects
	 */
	public static RegistryObject<SkillAspectType> POISON_EFFECT = ASPECTS.register("poison_effect", () -> SkillAspectType.build(
			SkillPoisonEffectAspect::new
	));
	
	/**
	 * Gives cultivation from the target to the caster
	 */
	public static RegistryObject<SkillAspectType> STEAL_CULTIVATION = ASPECTS.register("steal_cultivation", () -> SkillAspectType.build(
			SkillStealCultivationAspect::new
	));

	/**
	 * Gives cultivation from the caster to the target
	 */
	public static RegistryObject<SkillAspectType> SHARE_CULTIVATION = ASPECTS.register("share_cultivation", () -> SkillAspectType.build(
			SkillShareCultivationAspect::new
	));

}
