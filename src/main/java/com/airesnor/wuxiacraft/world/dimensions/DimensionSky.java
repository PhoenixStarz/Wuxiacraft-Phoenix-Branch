package com.airesnor.wuxiacraft.world.dimensions;

import com.airesnor.wuxiacraft.world.dimensions.ChunkGen.SkyChunkGen;
import com.airesnor.wuxiacraft.world.dimensions.biomes.BiomeProviderSky;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionSky extends WorldProvider {

    @Override
    protected void init() {
        this.biomeProvider = new BiomeProviderSky(this.world.getSeed());
    }

    @Override
    public DimensionType getDimensionType() {
        return WuxiaDimensions.SKY;
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new SkyChunkGen(this.world, true, this.world.getSeed());
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public WorldBorder createWorldBorder() {
        return new WuxiaWorldBorder(0.0D, 0.0D, 150.0D, 5.0D, 15, 5);
    }

    @Override
    protected void generateLightBrightnessTable() {
        float f = 0.5f;
        for(int i = 0; i <= 15; i++) {
            float f1 = 1.0f - (float)i/15.0f;
            this.lightBrightnessTable[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * (1.0f - f) + f;
        }
    }
}
