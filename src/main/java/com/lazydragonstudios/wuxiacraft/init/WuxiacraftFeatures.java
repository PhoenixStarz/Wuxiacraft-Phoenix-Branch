package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.world.feature.SpiritStoneFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WuxiacraftFeatures {

	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, WuxiaCraft.MOD_ID);

	public static final RegistryObject<Feature<?>> ORE_SPIRIT_VEIN_1 = FEATURES.register("ore_spirit_vein_1", SpiritStoneFeature::new);
	public static final RegistryObject<Feature<?>> ORE_SPIRIT_VEIN_2 = FEATURES.register("ore_spirit_vein_2", SpiritStoneFeature::new);
	public static final RegistryObject<Feature<?>> ORE_SPIRIT_VEIN_3 = FEATURES.register("ore_spirit_vein_3", SpiritStoneFeature::new);

}
