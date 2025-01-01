package com.airesnor.wuxiacraft.world.dimensions.biomes;

import com.airesnor.wuxiacraft.WuxiaCraft;
import com.airesnor.wuxiacraft.config.WuxiaCraftConfig;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class WuxiaBiomes {

    public static final Biome MINING = new BiomeMining();
    public static final Biome METAL = new BiomeMetal();
    public static final Biome FIRE = new BiomeFire();
    public static final Biome WOOD = new BiomeWood();
    public static final Biome WATER = new BiomeWater();
    public static final Biome EARTH = new BiomeEarth();
    public static final Biome EXTREMEQI = new BiomeExtremeQi();

    private static Biome registerBiome(Biome biome, String biomeName, BiomeType biomeType, boolean spawnInOverworld, int weight, Type... types) {
        biome.setRegistryName(biomeName);
        ForgeRegistries.BIOMES.register(biome);
        WuxiaCraft.logger.info("WuxiaCraft biome " + biomeName + " has been registered!");
        BiomeDictionary.addTypes(biome, types);

        //This makes it so that the overworld gets the biome added then it spawns it
        if(spawnInOverworld) {
            BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));
        }
        BiomeManager.addSpawnBiome(biome);
        WuxiaCraft.logger.info("WuxiaCraft biome " + biomeName + " has been added!");
        return biome;
    }

    public static void registerBiomes() {
        registerBiome(MINING, "Mining", BiomeType.COOL, false, 5, Type.MAGICAL, Type.DRY, Type.MOUNTAIN);
        registerBiome(METAL, "Metal", BiomeType.ICY, false, 5, Type.MAGICAL, Type.DRY, Type.MOUNTAIN, Type.COLD);
        registerBiome(WOOD, "Wood", BiomeType.WARM, false, 5, Type.MAGICAL, Type.DENSE, Type.FOREST, Type.PLAINS, Type.WATER);
        registerBiome(WATER, "Water", BiomeType.COOL, false, 5, Type.MAGICAL, Type.OCEAN, Type.BEACH);
        registerBiome(FIRE, "Fire", BiomeType.DESERT, false, 5, Type.MAGICAL, Type.HOT);
        registerBiome(EARTH, "Earth", BiomeType.WARM, false, 5, Type.MAGICAL, Type.MOUNTAIN, Type.HILLS, Type.LUSH, Type.PLAINS, Type.DENSE);
        registerBiome(EXTREMEQI, "ExtremeQi", BiomeType.WARM, WuxiaCraftConfig.EXTREME_QI_BIOME_SPAWN, 5, Type.MAGICAL, Type.MOUNTAIN, Type.LUSH, Type.DENSE, Type.RARE);
    }
}
