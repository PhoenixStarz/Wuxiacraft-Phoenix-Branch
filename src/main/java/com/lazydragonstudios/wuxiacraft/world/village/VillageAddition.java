package com.lazydragonstudios.wuxiacraft.world.village;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = WuxiaCraft.MOD_ID)
public class VillageAddition {

	private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(
			Registries.PROCESSOR_LIST, new ResourceLocation("minecraft", "empty"));

	private static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolRegistry,
																				Registry<StructureProcessorList> processorListRegistry,
																				ResourceLocation poolRL,
																				String nbtPieceRL,
																				int weight) {

		Holder<StructureProcessorList> emptyProcessorList = processorListRegistry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);

		StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
		if (pool == null) return;

		SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL,
				emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);

		for (int i = 0; i < weight; i++) {
			pool.templates.add(piece);
		}

		List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
		listOfPieceEntries.add(new Pair<>(piece, weight));
		pool.rawTemplates = listOfPieceEntries;
	}

	@SubscribeEvent
	public static void addNewVillageBuilding(final ServerAboutToStartEvent event) {
		Registry<StructureTemplatePool> templatePoolRegistry = event.getServer().registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow();
		Registry<StructureProcessorList> processorListRegistry = event.getServer().registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();

		addBuildingToPool(templatePoolRegistry, processorListRegistry,
				new ResourceLocation("minecraft:village/plains/houses"),
				"wuxiacraft:village/plains/houses/plains_technique_store", 16);
		addBuildingToPool(templatePoolRegistry, processorListRegistry,
				new ResourceLocation("minecraft:village/savanna/houses"),
				"wuxiacraft:village/savanna/houses/savanna_technique_store", 16);
		addBuildingToPool(templatePoolRegistry, processorListRegistry,
				new ResourceLocation("minecraft:village/desert/houses"),
				"wuxiacraft:village/plains/desert/desert_technique_store", 16);
		addBuildingToPool(templatePoolRegistry, processorListRegistry,
				new ResourceLocation("minecraft:village/snowy/houses"),
				"wuxiacraft:village/plains/houses/plains_technique_store", 16);
		addBuildingToPool(templatePoolRegistry, processorListRegistry,
				new ResourceLocation("minecraft:village/taiga/houses"),
				"wuxiacraft:village/plains/houses/plains_technique_store", 16);
	}

}
