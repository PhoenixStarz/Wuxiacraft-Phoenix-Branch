package com.airesnor.wuxiacraft.world.dimensions.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class GenLayerFixSky {

    public static GenLayer[] createWorld(long seed) {
        GenLayer biomes = new GenLayerSky(1l);
        biomes = new GenLayerZoom(1000l, biomes);
        biomes = new GenLayerZoom(1001l, biomes);
        biomes = new GenLayerZoom(1002l, biomes);
        biomes = new GenLayerZoom(1003l, biomes);
        biomes = new GenLayerZoom(1004l, biomes);
        biomes = new GenLayerZoom(1005l, biomes);
        GenLayer genLayerVoronoiZoom = new GenLayerVoronoiZoom(10l, biomes);
        biomes.initWorldGenSeed(seed);
        return new GenLayer[] {biomes, genLayerVoronoiZoom};
    }
}
