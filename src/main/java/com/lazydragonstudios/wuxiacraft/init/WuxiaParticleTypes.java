package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WuxiaParticleTypes {

	public static DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, WuxiaCraft.MOD_ID);

	public static RegistryObject<ParticleType<SimpleParticleType>> BODY_QI_FOG = PARTICLE_TYPES.register("body_qi_fog", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> DIVINE_QI_FOG = PARTICLE_TYPES.register("divine_qi_fog", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> ESSENCE_QI_FOG = PARTICLE_TYPES.register("essence_qi_fog", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> BEAM_SKILL_PHYSICAL = PARTICLE_TYPES.register("beam_skill_physical", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> BEAM_SKILL_FIRE = PARTICLE_TYPES.register("beam_skill_fire", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> BEAM_SKILL_EARTH = PARTICLE_TYPES.register("beam_skill_earth", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> BEAM_SKILL_METAL = PARTICLE_TYPES.register("beam_skill_metal", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> BEAM_SKILL_WATER = PARTICLE_TYPES.register("beam_skill_water", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> BEAM_SKILL_WOOD = PARTICLE_TYPES.register("beam_skill_wood", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> BEAM_SKILL_LIGHTNING = PARTICLE_TYPES.register("beam_skill_lightning", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> BEAM_SKILL_POISON = PARTICLE_TYPES.register("beam_skill_poison", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> BEAM_SKILL_LIGHT = PARTICLE_TYPES.register("beam_skill_light", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> BEAM_SKILL_SPACE = PARTICLE_TYPES.register("beam_skill_space", () -> new SimpleParticleType(false));
	public static RegistryObject<ParticleType<SimpleParticleType>> FLIGHT_PARTICLE = PARTICLE_TYPES.register("flight_particle", () -> new SimpleParticleType(false));
}
