package com.lazydragonstudios.wuxiacraft.init;

import com.google.common.collect.ImmutableSet;
import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WuxiaProfessions {

	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, WuxiaCraft.MOD_ID);

	public static RegistryObject<VillagerProfession> CULTIVATOR = PROFESSIONS
			.register("cultivator",
					() -> new VillagerProfession("cultivator",
							poiTypeHolder -> poiTypeHolder.is(WuxiaPoiTypes.TECHNIQUE_INSCRIBER_POI.getId()),
							poiTypeHolder -> poiTypeHolder.is(WuxiaPoiTypes.TECHNIQUE_INSCRIBER_POI.getId()),
							ImmutableSet.of(),
							ImmutableSet.of(),
							SoundEvents.VILLAGER_WORK_CARTOGRAPHER
					)
			);

@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (event.getType() == WuxiaProfessions.CULTIVATOR.get()) {
			var trades = event.getTrades();
			for (var manualLocation : WuxiaDefaultTechniqueManuals.getAllKeys()) {
				if (manualLocation.getPath().equalsIgnoreCase("qi_tempering") ||
					manualLocation.getPath().equalsIgnoreCase("qi_flow") ||
					manualLocation.getPath().equalsIgnoreCase("qi_enlightenment")) 
					{ 
				var manualSupplier = WuxiaDefaultTechniqueManuals.getDefaultManual(manualLocation);
				if(manualLocation.getPath().equalsIgnoreCase("spacing_out")) continue;
				if (manualSupplier == null) continue;
			for (var manualLocation2 : WuxiaDefaultTechniqueManuals.getAllKeys()) {
				if (!manualLocation2.getPath().equalsIgnoreCase("qi_tempering") &&
					!manualLocation2.getPath().equalsIgnoreCase("qi_flow") &&
					!manualLocation2.getPath().equalsIgnoreCase("qi_enlightenment")) 
					{ 
				var manualSupplier2 = WuxiaDefaultTechniqueManuals.getDefaultManual(manualLocation2);
				if(manualLocation2.getPath().equalsIgnoreCase("spacing_out")) continue;
				if (manualSupplier2 == null) continue;
				trades.get(1).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 10),
							manualSupplier.get(),
							5,
							25,
							0.05F
					)
				);
				trades.get(2).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 10),
							manualSupplier.get(),
							5,
							50,
							0.05F
					)
				);
				trades.get(3).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 10),
							manualSupplier.get(),
							5,
							75,
							0.05F
					)
				);
				trades.get(3).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 10),
							manualSupplier.get(),
							5,
							75,
							0.05F
					)
				);
				trades.get(3).add((entity, random) ->
				new MerchantOffer(
						new ItemStack(Items.EMERALD, 16),
						manualSupplier2.get(),
						3,
						75,
						0.2F
				)
				);
				trades.get(4).add((entity, random) ->
				new MerchantOffer(
						new ItemStack(Items.EMERALD, 16),
						manualSupplier2.get(),
						3,
						100,
						0.2F
				)
				);
				trades.get(4).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 10),
							manualSupplier.get(),
							5,
							100,
							0.05F
					)
				);
				trades.get(4).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 16),
							manualSupplier2.get(),
							3,
							100,
							0.2F
					)
				);
				trades.get(5).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 16),
							manualSupplier2.get(),
							3,
							125,
							0.2F
					)
				);
				trades.get(5).add((entity, random) ->
					new MerchantOffer(
							new ItemStack(Items.EMERALD, 16),
							manualSupplier2.get(),
							3,
							125,
							0.2F
					)
				);
			}}
	  	}}
	}}
	

	static class ItemTrade implements ItemListing {

		private ItemStack cost;

		private ItemStack product;

		private int xpGained;

		private int stock;

		public ItemTrade(ItemStack cost, ItemStack product, int stock, int xpGained) {
			this.cost = cost;
			this.product = product;
			this.stock = stock;
			this.xpGained = xpGained;
		}

		@Override
		public MerchantOffer getOffer(Entity trader, RandomSource rand) {
			return new MerchantOffer(cost, product, stock, xpGained, 0F);
		}
	}
}