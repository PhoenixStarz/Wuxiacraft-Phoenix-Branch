package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.Element;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class WuxiaElements {

	public static DeferredRegister<Element> ELEMENTS = DeferredRegister.create(new ResourceLocation(WuxiaCraft.MOD_ID, "elements"), WuxiaCraft.MOD_ID);

	public static RegistryObject<Element> PHYSICAL = ELEMENTS.register("physical", () -> new Element()
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "physical"), new BigDecimal("0.02"))
	);

	public static RegistryObject<Element> FIRE = ELEMENTS.register("fire", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "space"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "earth"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "light"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "metal"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "posion"))
			.addSkill(WuxiaSkillAspects.EXPLOSION.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.STRENGTH, new BigDecimal("0.014"))
			.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.00008"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "fire"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "metal"), new BigDecimal("0.008"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "poison"), new BigDecimal("0.008"))
	);

	public static RegistryObject<Element> EARTH = ELEMENTS.register("earth", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "space"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "metal"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "poison"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "water"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "wind"))
			.addSkill(WuxiaSkillAspects.COFFIN.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("0.04"))
			.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.018"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "earth"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "water"), new BigDecimal("0.008"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "wind"), new BigDecimal("0.008"))
	);

	public static RegistryObject<Element> METAL = ELEMENTS.register("metal", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "space"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "water"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "lightning"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "wood"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "wind"))
			.addSkill(WuxiaSkillAspects.ORE_MINE.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.STRENGTH, new BigDecimal("0.009"))
			.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.00013"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "metal"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "wood"), new BigDecimal("0.008"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "wind"), new BigDecimal("0.008"))
	);

	public static RegistryObject<Element> WATER = ELEMENTS.register("water", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "space"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "wood"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "dark"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "fire"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "lightning"))
			.addSkill(WuxiaSkillAspects.WAVE.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("0.04"))
			.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.022"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "water"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "fire"), new BigDecimal("0.008"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "lightning"), new BigDecimal("0.008"))
	);

	public static RegistryObject<Element> WOOD = ELEMENTS.register("wood", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "space"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "fire"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "wind"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "earth"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "poison"))
			.addSkill(WuxiaSkillAspects.FERTILIZE.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.00003"))
			.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("0.05"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "wood"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "earth"), new BigDecimal("0.008"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "poison"), new BigDecimal("0.008"))
	);

	public static RegistryObject<Element> LIGHTNING = ELEMENTS.register("lightning", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "time"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "fire"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "water"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "wood"))
			.addSkill(WuxiaSkillAspects.SUMMON_LIGHTNING.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.STRENGTH, new BigDecimal("0.016"))
			.setStat(PlayerStat.AGILITY, new BigDecimal("0.0006"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "lightning"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "water"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "wood"), new BigDecimal("0.005"))
	);

	public static RegistryObject<Element> WIND = ELEMENTS.register("wind", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "time"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "fire"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "earth"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "metal"))
			.addSkill(WuxiaSkillAspects.AREA.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.AGILITY, new BigDecimal("0.0008"))
			.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("0.03"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "wind"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "earth"), new BigDecimal("0.008"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "metal"), new BigDecimal("0.008"))
	);

	public static RegistryObject<Element> POISON = ELEMENTS.register("poison", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "time"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "physical"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "water"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "wood"))
			.addSkill(WuxiaSkillAspects.POISON_EFFECT.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.000005"))
			.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.0023"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "poison"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "water"), new BigDecimal("0.008"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "wood"), new BigDecimal("0.008"))
	);

	public static RegistryObject<Element> LIGHT = ELEMENTS.register("light", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "time"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "wood"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "dark"))
			.addSkill(WuxiaSkillAspects.HEAL.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.AGILITY, new BigDecimal("0.0004"))
			.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.0028"))
			.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.000004"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "light"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "dark"), new BigDecimal("0.008"))
	);

	public static RegistryObject<Element> DARK = ELEMENTS.register("dark", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "time"))
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "earth"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "light"))
			.addSkill(WuxiaSkillAspects.LIFE_STEAL.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.STRENGTH, new BigDecimal("0.06"))
			.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.06"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "dark"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "light"), new BigDecimal("0.008"))
	);

	public static RegistryObject<Element> SPACE = ELEMENTS.register("space", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "rebirth"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "time"))
			.addSkill(WuxiaSkillAspects.SPATIAL_TEARING.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("0.05"))
			.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.00002"))
			.setStat(PlayerStat.STRENGTH, new BigDecimal("0.012"))
			.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("0.02"))
			.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.00007"))
			.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.00004"))
			.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.011"))
			.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.000007"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "space"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "time"), new BigDecimal("0.008"))
	);

	public static RegistryObject<Element> TIME = ELEMENTS.register("time", () -> new Element()
			.begets(new ResourceLocation(WuxiaCraft.MOD_ID, "rebirth"))
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "space"))
			.addSkill(WuxiaSkillAspects.TEMPORAL_TEARING.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.STRENGTH, new BigDecimal("0.038"))
			.setStat(PlayerStat.AGILITY, new BigDecimal("0.0009"))
			.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.000005"))
			.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.0015"))
			.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.03"))
			.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("0.02"))
			.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.000003"))
			.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.000004"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "time"), new BigDecimal("0.005"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "space"), new BigDecimal("0.008"))
	);


	public static RegistryObject<Element> REBIRTH = ELEMENTS.register("rebirth", () -> new Element()
			.suppresses(new ResourceLocation(WuxiaCraft.MOD_ID, "demonic"))
			.addSkill(WuxiaSkillAspects.SHARE_CULTIVATION.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("0.05"))
			.setStat(PlayerStat.STRENGTH, new BigDecimal("0.05"))
			.setStat(PlayerStat.AGILITY, new BigDecimal("0.0009"))
			.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.000025"))
			.setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.0015"))
			.setStat(PlayerStat.DETECTION_RESISTANCE, new BigDecimal("0.03"))
			.setStat(PlayerStat.DETECTION_RANGE, new BigDecimal("0.02"))
			.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("0.02"))
			.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.000003"))
			.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.000011"))
			.setStat(System.ESSENCE, PlayerSystemStat.COOLDOWN_SPEED, new BigDecimal("0.00007"))
			.setStat(System.ESSENCE, PlayerSystemStat.CAST_SPEED, new BigDecimal("0.00004"))
			.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.011"))
			.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "demonic"), new BigDecimal("0.0001"))
	);
	
	
	public static RegistryObject<Element> DEMONIC = ELEMENTS.register("demonic", () -> new Element()
			.addSkill(WuxiaSkillAspects.STEAL_CULTIVATION.getId(), new BigDecimal("50000"))
			.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("-0.03"))
			.setStat(PlayerStat.STRENGTH, new BigDecimal("-0.019"))
			.setStat(PlayerStat.AGILITY, new BigDecimal("-0.0002"))
			.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("-0.00001"))
			.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("-0.0000125"))
			.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("-0.00005"))
			.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("-0.00004"))
			.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("-0.03"))
			.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("-0.012"))
			.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("-0.000014"))
			.setStat(System.BODY, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("-0.000007"))
			.setStat(System.DIVINE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("-0.000007"))
	);

}
