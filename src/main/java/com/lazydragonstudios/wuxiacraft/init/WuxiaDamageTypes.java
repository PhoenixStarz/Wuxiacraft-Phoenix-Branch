package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class WuxiaDamageTypes {
	public static ResourceKey<DamageType> ENERGY_EXCESS_BODY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WuxiaCraft.MOD_ID, "energy_excess_body"));

	public static ResourceKey<DamageType> ENERGY_EXCESS_DIVINE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WuxiaCraft.MOD_ID, "energy_excess_divine"));

	public static ResourceKey<DamageType> ENERGY_EXCESS_ESSENCE =ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WuxiaCraft.MOD_ID, "energy_excess_essence"));

	public static ResourceKey<DamageType> FORGOT = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WuxiaCraft.MOD_ID, "forgot"));

	public static ResourceKey<DamageType> SKILL_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WuxiaCraft.MOD_ID, "skill_attack"));

	public static ResourceKey<DamageType> SKILL_BEAM_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WuxiaCraft.MOD_ID, "skill_beam_attack"));

	public static ResourceKey<DamageType> SKILL_BREAK =ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WuxiaCraft.MOD_ID, "skill_break"));

	public static ResourceKey<DamageType> SKILL_CHOP = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WuxiaCraft.MOD_ID, "skill_chop"));

	public static ResourceKey<DamageType> SKILL_EXPLOSION = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WuxiaCraft.MOD_ID, "skill_explosion"));

}
