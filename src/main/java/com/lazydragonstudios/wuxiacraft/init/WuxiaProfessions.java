package com.lazydragonstudios.wuxiacraft.init;

import com.google.common.collect.ImmutableSet;
import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.ResourceLocation;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

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
			trades.get(1).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.EMERALD, 8),
					getManualSupplierQi(),
					3,
					10,
					0.2F
			));			
			trades.get(1).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.EMERALD, 8),
					getManualSupplierQi(),
					3,
					10,
					0.2F
			));	
			trades.get(1).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.BOOK, 4),
					new ItemStack(Items.EMERALD, 1),
					16,
					5,
					0.05F
			));
			trades.get(2).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.EMERALD, 8),
					getManualSupplierQi(),
					3,
					10,
					0.2F
			));
			trades.get(2).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.EMERALD, 16),
					getManualSupplierElemental(),
					2,
					20,
					0.2F
			));
			trades.get(2).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.INK_SAC, 6),
					new ItemStack(Items.EMERALD, 1),
					16,
					10,
					0.05F
			));
			trades.get(3).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.EMERALD, 16),
					getManualSupplierElemental(),
					2,
					20,
					0.2F
			));
			trades.get(3).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.EMERALD, 16),
					getManualSupplierElemental(),
					2,
					20,
					0.2F
			));
			trades.get(3).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(WuxiaItems.SPIRIT_STONE_1.get(), 8),
					new ItemStack(Items.EMERALD, 1),
					16,
					15,
					0.05F
			));
			trades.get(4).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.EMERALD, 16),
					getManualSupplierElemental(),
					2,
					20,
					0.2F
				));
			trades.get(4).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.EMERALD, 32),
					getManualSupplierElementalT2(),
					1,
					30,
					0.2F
				));
			trades.get(4).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(WuxiaItems.SPIRIT_STONE_2.get(), 2),
					new ItemStack(Items.EMERALD, 1),
					16,
					20,
					0.05F
			));
			trades.get(5).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.EMERALD, 32),
					getManualSupplierElementalT2(),
					1,
					30,
					0.2F
				));
			trades.get(5).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(Items.EMERALD, 32),
					getManualSupplierElementalT2(),
					1,
					30,
					0.2F
				));
			trades.get(5).add((entity, random) ->
				new MerchantOffer(
					new ItemStack(WuxiaItems.SPIRIT_STONE_3.get(), 1),
					new ItemStack(Items.EMERALD, 2),
					16,
					25,
					0.05F
				));
		}
	}

	private static ItemStack getManualSupplierQi() {
		List<ItemStack> manuals = new ArrayList<ItemStack>();
		for (ResourceLocation manualLocation : WuxiaDefaultTechniqueManuals.getAllKeys()) {
			if (manualLocation.getPath().equalsIgnoreCase("qi_tempering") ||
				manualLocation.getPath().equalsIgnoreCase("qi_flow") ||
				manualLocation.getPath().equalsIgnoreCase("qi_enlightenment")) { 
			Supplier<ItemStack> manualSupplier = WuxiaDefaultTechniqueManuals.getDefaultManual(manualLocation);
			if (manualSupplier != null) manuals.add(manualSupplier.get());
			}
		}
		if (manuals.isEmpty()) return null;
		return manuals.get(new Random().nextInt(manuals.size()));
	}

	private static ItemStack getManualSupplierElemental() {
		List<ItemStack> manuals = new ArrayList<ItemStack>();
		for (ResourceLocation manualLocation : WuxiaDefaultTechniqueManuals.getAllKeys()) {
			if (!manualLocation.getPath().equalsIgnoreCase("qi_tempering") &&
				!manualLocation.getPath().equalsIgnoreCase("qi_flow") &&
				!manualLocation.getPath().equalsIgnoreCase("qi_enlightenment") &&
				!manualLocation.getPath().equalsIgnoreCase("spacing_out") &&
				!manualLocation.getPath().equalsIgnoreCase("timing_out") &&
				!manualLocation.getPath().equalsIgnoreCase("reincarnated") &&
				!manualLocation.getPath().equalsIgnoreCase("demonic_arts")) { 
				Supplier<ItemStack> manualSupplier = WuxiaDefaultTechniqueManuals.getDefaultManual(manualLocation);
				if (manualSupplier != null) {
					ItemStack stack = manualSupplier.get();
					if (stack.getTag() == null) break;
					if (stack.getTag().contains("radius") && stack.getTag().getInt("radius") == 1) {
						manuals.add(manualSupplier.get());
					}
				}
			}
		}
		if (manuals.isEmpty()) return null;
		return manuals.get(new Random().nextInt(manuals.size()));
	}
	private static ItemStack getManualSupplierElementalT2() {
		List<ItemStack> manuals = new ArrayList<ItemStack>();
		for (ResourceLocation manualLocation : WuxiaDefaultTechniqueManuals.getAllKeys()) {
			Supplier<ItemStack> manualSupplier = WuxiaDefaultTechniqueManuals.getDefaultManual(manualLocation);
			if (manualSupplier != null) {
				ItemStack stack = manualSupplier.get();
				if (stack.getTag() == null) break;
				if (stack.getTag().contains("radius") && stack.getTag().getInt("radius") > 1) {
					manuals.add(manualSupplier.get());
				}
			}
		}
		if (manuals.isEmpty()) return null;
		return manuals.get(new Random().nextInt(manuals.size()));
	}


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