package com.lazydragonstudios.wuxiacraft.init;

import java.util.List;
import java.util.Random;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import com.google.common.collect.ImmutableSet;
import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.init.WuxiaItems;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WuxiaProfessions {

	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, WuxiaCraft.MOD_ID);

	public static RegistryObject<VillagerProfession> CULTIVATOR = PROFESSIONS
			.register("cultivator",
					() -> new VillagerProfession("cultivator",
							WuxiaPoiTypes.TECHNIQUE_INSCRIBER.get(),
							ImmutableSet.of(),
							ImmutableSet.of(),
							SoundEvents.VILLAGER_WORK_CARTOGRAPHER
					)
			);

	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (event.getType() == WuxiaProfessions.CULTIVATOR.get()) {
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
			for (var manualLocation : WuxiaDefaultTechniqueManuals.getAllKeys()) {
				if (manualLocation.getPath().equalsIgnoreCase("qi_tempering") ||
					manualLocation.getPath().equalsIgnoreCase("qi_flow") ||
					manualLocation.getPath().equalsIgnoreCase("qi_enlightenment")) 
					{ 
				var manualSupplier = WuxiaDefaultTechniqueManuals.getDefaultManual(manualLocation);
				if (manualSupplier == null) continue;
			for (var manualLocation2 : WuxiaDefaultTechniqueManuals.getAllKeys()) {
				if (!manualLocation2.getPath().equalsIgnoreCase("qi_tempering") ||
					!manualLocation2.getPath().equalsIgnoreCase("qi_flow") ||
					!manualLocation2.getPath().equalsIgnoreCase("qi_enlightenment")) 
					{ 
				var manualSupplier2 = WuxiaDefaultTechniqueManuals.getDefaultManual(manualLocation2);
				if (manualSupplier2 == null) continue;
				trades.get(1).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 10),
							manualSupplier.get(),
							5,
							2,
							0.05F
					)
				);
				trades.get(2).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 10),
							manualSupplier.get(),
							5,
							5,
							0.05F
					)
				);
				trades.get(3).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 10),
							manualSupplier.get(),
							5,
							10,
							0.05F
					)
				);
				trades.get(3).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 10),
							manualSupplier.get(),
							5,
							10,
							0.05F
					)
				);
				trades.get(4).add((entity, random) ->
				new MerchantOffer(
						new ItemStack(Items.EMERALD, 16),
						manualSupplier2.get(),
						3,
						15,
						0.2F
				)
				);
				trades.get(4).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 10),
							manualSupplier.get(),
							5,
							15,
							0.05F
					)
				);
				trades.get(4).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 16),
							manualSupplier2.get(),
							3,
							15,
							0.2F
					)
				);
				trades.get(5).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 16),
							manualSupplier2.get(),
							3,
							15,
							0.2F
					)
				);
				trades.get(5).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 16),
							manualSupplier2.get(),
							3,
							15,
							0.2F
					)
				);
			}}
	  	}}
	}}
}