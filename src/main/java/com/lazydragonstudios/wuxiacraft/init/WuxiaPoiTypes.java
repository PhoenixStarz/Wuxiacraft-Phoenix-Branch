package com.lazydragonstudios.wuxiacraft.init;

import com.google.common.collect.ImmutableSet;
import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class WuxiaPoiTypes {

	public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, WuxiaCraft.MOD_ID);

	public static RegistryObject<PoiType> TECHNIQUE_INSCRIBER_POI = POI_TYPES
			.register("technique_inscriber_poi",
					() -> new PoiType(
							ImmutableSet.copyOf(WuxiaBlocks.TECHNIQUE_INSCRIBER.get().getStateDefinition().getPossibleStates()),
							1, 1)
			);
}