package com.lazydragonstudios.wuxiacraft.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.Set;

public class SpiritStoneFeatureE extends OreFeature {

	private final Set<ResourceKey<Level>> generate_dimensions = Set.of(Level.END);


	public SpiritStoneFeatureE() {
		super(OreConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<OreConfiguration> context) {
		WorldGenLevel world = context.level();
		if (!generate_dimensions.contains(world.getLevel().dimension()))
			return false;
		return super.place(context);
	}

}
