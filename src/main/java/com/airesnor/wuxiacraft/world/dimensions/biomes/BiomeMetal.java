package com.airesnor.wuxiacraft.world.dimensions.biomes;

import com.airesnor.wuxiacraft.blocks.WuxiaBlocks;
import com.airesnor.wuxiacraft.entities.mobs.WanderingCultivator;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class BiomeMetal extends Biome {


    public BiomeMetal() {
        super(new BiomeProperties("Metal").setBaseHeight(1.5f).setHeightVariation(2.0f).setTemperature(0.1f).setRainDisabled().setWaterColor(9140823));

        topBlock = Blocks.GRASS.getDefaultState();
        fillerBlock = WuxiaBlocks.METALLIC_STONE.getDefaultState();
        this.decorator.treesPerChunk = 1;

        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();

        this.spawnableCreatureList.add(new SpawnListEntry(WanderingCultivator.class, 8, 1, 5));
    }
}
