package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.world.features.ores.DeepslateCelestialIronOreFeature;
import com.lazydragonstudios.wuxiacraft.world.features.ores.CelestialIronOreFeature;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Holder;

import java.util.function.Supplier;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

@Mod.EventBusSubscriber
public class WuxiaFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, WuxiaCraft.MOD_ID);
	
	private static final List<FeatureRegistration> FEATURE_REGISTRATIONS = new ArrayList<>();
	
	public static final RegistryObject<Feature<?>> DEEPSLATE_CELESTIAL_IRON_ORE = register("deepslate_celestial_iron_ore", DeepslateCelestialIronOreFeature::feature,
			new FeatureRegistration(GenerationStep.Decoration.UNDERGROUND_ORES, DeepslateCelestialIronOreFeature.GENERATE_BIOMES, DeepslateCelestialIronOreFeature::placedFeature));

	public static final RegistryObject<Feature<?>> CELESTIAL_IRON_ORE = register("celestial_iron_ore", CelestialIronOreFeature::feature,
			new FeatureRegistration(GenerationStep.Decoration.UNDERGROUND_ORES, CelestialIronOreFeature.GENERATE_BIOMES, CelestialIronOreFeature::placedFeature));

	private static RegistryObject<Feature<?>> register(String registryname, Supplier<Feature<?>> feature, FeatureRegistration featureRegistration) {
		FEATURE_REGISTRATIONS.add(featureRegistration);
		return REGISTRY.register(registryname, feature);
	}

	@SubscribeEvent
	public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
		for (FeatureRegistration registration : FEATURE_REGISTRATIONS) {
			if (registration.biomes() == null || registration.biomes().contains(event.getName()))
				event.getGeneration().getFeatures(registration.stage()).add(registration.placedFeature().get());
		}
	}

	private static record FeatureRegistration(GenerationStep.Decoration stage, Set<ResourceLocation> biomes, Supplier<Holder<PlacedFeature>> placedFeature) {
	}
}
