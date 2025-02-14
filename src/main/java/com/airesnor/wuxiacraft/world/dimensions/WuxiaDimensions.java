package com.airesnor.wuxiacraft.world.dimensions;

import com.airesnor.wuxiacraft.config.WuxiaCraftConfig;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class WuxiaDimensions {

    public static final DimensionType MINING = DimensionType.register("Mining", "_mining", WuxiaCraftConfig.DIMENSION_MINING, DimensionMining.class, false);
    public static final DimensionType ELEMENTAL = DimensionType.register("ELEMENTAL", "_elemental", WuxiaCraftConfig.DIMENSION_ELEMENTAL, DimensionElemental.class, false);
    public static final DimensionType SKY = DimensionType.register("SKY", "_sky", WuxiaCraftConfig.DIMENSION_SKY, DimensionSky.class, false);

    public static void registerDimensions() {
        DimensionManager.registerDimension(WuxiaDimensions.MINING.getId(), MINING);
        DimensionManager.registerDimension(WuxiaDimensions.ELEMENTAL.getId(), ELEMENTAL);
        DimensionManager.registerDimension(WuxiaDimensions.SKY.getId(), SKY);
    }
}
