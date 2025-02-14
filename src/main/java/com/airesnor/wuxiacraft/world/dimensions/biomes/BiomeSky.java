package com.airesnor.wuxiacraft.world.dimensions.biomes;

import com.airesnor.wuxiacraft.entities.mobs.WanderingCultivator;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.*;

public class BiomeSky extends Biome {

    public BiomeSky() {
        super(new BiomeProperties("Sky").setBaseHeight(1.0f).setHeightVariation(0.9f).setTemperature(0.0f).setRainfall(0.0f).setWaterColor(444));

        topBlock = Blocks.DIRT.getDefaultState();
        fillerBlock = Blocks.STONE.getDefaultState();

        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();

        this.spawnableCreatureList.add(new SpawnListEntry(WanderingCultivator.class, 8, 1, 5));
    }
}
