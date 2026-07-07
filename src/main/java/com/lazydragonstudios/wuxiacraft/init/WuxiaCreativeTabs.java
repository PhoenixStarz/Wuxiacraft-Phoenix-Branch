package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.formation.FormationMaterialTier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WuxiaCreativeTabs {

	public static DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WuxiaCraft.MOD_ID);

	public static RegistryObject<CreativeModeTab> RUNES = CREATIVE_TABS.register("runes", () ->
			CreativeModeTab.builder()
					.icon(WuxiaItems.GENERATION_RUNES.get(FormationMaterialTier.DIAMOND).get()::getDefaultInstance)
					.title(Component.translatable("wuxiacraft.runes_tab"))
					.displayItems((p1, output) -> {
						WuxiaItems.GENERATION_RUNES.forEach((tier, runeRegistry) -> output.accept(runeRegistry.get()));
						WuxiaItems.BARRIER_RUNES.forEach((tier, runeRegistry) -> output.accept(runeRegistry.get()));
						WuxiaItems.CULTIVATION_RUNES.forEach((system, systemRunes) -> systemRunes.forEach((material, runeRegistry) -> output.accept(runeRegistry.get())));
						WuxiaItems.ENERGY_RUNES.forEach((system, systemRunes) -> systemRunes.forEach((material, runeRegistry) -> output.accept(runeRegistry.get())));
					})
					.build()
	);

	public static RegistryObject<CreativeModeTab> MANUALS = CREATIVE_TABS.register("manuals", () ->
			CreativeModeTab.builder()
					.icon(WuxiaItems.ESSENCE_MANUAL.get()::getDefaultInstance)
					.title(Component.translatable("wuxiacraft.manuals_tab"))
					.displayItems((whatever, output) -> {
						WuxiaDefaultTechniqueManuals.DEFAULT_MANUALS.forEach((name, manualItemSupplier) -> output.accept(manualItemSupplier.get()));
					})
					.build()
	);

	public static RegistryObject<CreativeModeTab> WUXIA_RANDOM_ITEMS = CREATIVE_TABS.register("wuxia_random_items", () ->
			CreativeModeTab.builder()
					.icon(WuxiaItems.SPIRIT_STONE_1.get()::getDefaultInstance)
					.title(Component.translatable("wuxiacraft.random_items"))
					.displayItems((whatever, output) -> {
						output.accept(WuxiaItems.SPIRIT_CRYSTAL_BLOCK.get());
						output.accept(WuxiaItems.BUDDING_SPIRIT_CRYSTAL_BLOCK.get());
						output.accept(WuxiaItems.SPIRIT_CRYSTAL_CLUSTER_1.get());
						output.accept(WuxiaItems.SPIRIT_CRYSTAL_CLUSTER_2.get());
						output.accept(WuxiaItems.SPIRIT_CRYSTAL_CLUSTER_3.get());
						output.accept(WuxiaItems.SPIRIT_CRYSTAL_CLUSTER_4.get());
						output.accept(WuxiaItems.SPIRIT_CRYSTAL_CLUSTER_5.get());
						output.accept(WuxiaItems.SPIRIT_CRYSTAL_CLUSTER_6.get());
						output.accept(WuxiaItems.SPIRIT_CRYSTAL_CLUSTER_7.get());
						output.accept(WuxiaItems.SPIRIT_CRYSTAL_CLUSTER_8.get());
						output.accept(WuxiaItems.SPIRIT_CRYSTAL_CLUSTER_9.get());
						output.accept(WuxiaItems.SPIRIT_STONE_1.get());
						output.accept(WuxiaItems.SPIRIT_STONE_2.get());
						output.accept(WuxiaItems.SPIRIT_STONE_3.get());
						output.accept(WuxiaItems.SPIRIT_STONE_4.get());
						output.accept(WuxiaItems.SPIRIT_STONE_5.get());
						output.accept(WuxiaItems.SPIRIT_STONE_6.get());
						output.accept(WuxiaItems.SPIRIT_STONE_7.get());
						output.accept(WuxiaItems.SPIRIT_STONE_8.get());
						output.accept(WuxiaItems.SPIRIT_STONE_9.get());
						output.accept(WuxiaItems.SPIRIT_FRUIT_1.get());
						output.accept(WuxiaItems.SPIRIT_FRUIT_2.get());
						output.accept(WuxiaItems.SPIRIT_FRUIT_3.get());
						output.accept(WuxiaItems.SPIRIT_FRUIT_4.get());
						output.accept(WuxiaItems.SPIRIT_FRUIT_5.get());
						output.accept(WuxiaItems.SPIRIT_FRUIT_6.get());
						output.accept(WuxiaItems.SPIRIT_FRUIT_7.get());
						output.accept(WuxiaItems.SPIRIT_FRUIT_8.get());
						output.accept(WuxiaItems.SPIRIT_FRUIT_9.get());
						output.accept(WuxiaItems.PILL_1.get());
						output.accept(WuxiaItems.PILL_2.get());
						output.accept(WuxiaItems.PILL_3.get());
						output.accept(WuxiaItems.PILL_4.get());
						output.accept(WuxiaItems.PILL_5.get());
						output.accept(WuxiaItems.PILL_6.get());
						output.accept(WuxiaItems.PILL_7.get());
						output.accept(WuxiaItems.PILL_8.get());
						output.accept(WuxiaItems.PILL_9.get());		
						output.accept(WuxiaItems.SPIRIT_STONE_VEIN_1.get());
						output.accept(WuxiaItems.DEEPSLATE_SPIRIT_STONE_VEIN_2.get());
						output.accept(WuxiaItems.SPIRIT_STONE_VEIN_2.get());
						output.accept(WuxiaItems.SPIRIT_STONE_VEIN_3.get());
						output.accept(WuxiaItems.SPIRIT_STONE_VEIN_4.get());
						output.accept(WuxiaItems.SPIRIT_STONE_VEIN_5.get());
						output.accept(WuxiaItems.CELESTIAL_IRON_ORE.get());
						output.accept(WuxiaItems.DEEPSLATE_CELESTIAL_IRON_ORE.get());
						output.accept(WuxiaItems.CELESTIAL_IRON_BLOCK.get());
						output.accept(WuxiaItems.RAW_CELESTIAL_IRON_BLOCK.get());
						output.accept(WuxiaItems.CELESTIAL_IRON_INGOT.get());
						output.accept(WuxiaItems.RAW_CELESTIAL_IRON.get());
						output.accept(WuxiaItems.TECHNIQUE_INSCRIBER.get());
						output.accept(WuxiaItems.RUNEMAKING_TABLE.get());
						output.accept(WuxiaItems.CELESTIAL_HELMET.get());
						output.accept(WuxiaItems.CELESTIAL_CHESTPLATE.get());
						output.accept(WuxiaItems.CELESTIAL_LEGGINGS.get());
						output.accept(WuxiaItems.CELESTIAL_BOOTS.get());
						output.accept(WuxiaItems.CELESTIAL_SWORD.get());
						output.accept(WuxiaItems.CELESTIAL_AXE.get());
						output.accept(WuxiaItems.CELESTIAL_PICKAXE.get());
						output.accept(WuxiaItems.CELESTIAL_SHOVEL.get());
						output.accept(WuxiaItems.CELESTIAL_HOE.get());
						output.accept(WuxiaItems.SNAKE_SPAWN_EGG.get());
						output.accept(WuxiaItems.DESERT_SNAKE_SPAWN_EGG.get());
						output.accept(WuxiaItems.RED_SNAKE_SPAWN_EGG.get());
						output.accept(WuxiaItems.WHITE_SNAKE_SPAWN_EGG.get());
					})
					.build()
	);

	public static RegistryObject<CreativeModeTab> FORMATION_ITEMS = CREATIVE_TABS.register("formation_items", () ->
			CreativeModeTab.builder()
					.icon(WuxiaItems.DIAMOND_FORMATION_CORE.get()::getDefaultInstance)
					.title(Component.translatable("wuxiacraft.formation_items"))
					.displayItems((whatever, output) -> {
						output.accept(WuxiaItems.FORMATION_CORE_BASE.get());
						output.accept(WuxiaItems.COPPER_FORMATION_CORE.get());
						output.accept(WuxiaItems.DIAMOND_FORMATION_CORE.get());
						output.accept(WuxiaItems.EMERALD_FORMATION_CORE.get());
						output.accept(WuxiaItems.GOLD_FORMATION_CORE.get());
						output.accept(WuxiaItems.IRON_FORMATION_CORE.get());
						output.accept(WuxiaItems.LAPIS_FORMATION_CORE.get());
						output.accept(WuxiaItems.NETHERITE_FORMATION_CORE.get());
						output.accept(WuxiaItems.CELESTIAL_IRON_FORMATION_CORE.get());
						output.accept(WuxiaItems.STONE_FORMATION_CORE.get());
						output.accept(WuxiaItems.FORMATION_CORE_BASE.get());
						output.accept(WuxiaItems.STONE_FORMATION_BADGE.get());
						output.accept(WuxiaItems.LAPIS_FORMATION_BADGE.get());
						output.accept(WuxiaItems.COPPER_FORMATION_BADGE.get());
						output.accept(WuxiaItems.IRON_FORMATION_BADGE.get());
						output.accept(WuxiaItems.GOLD_FORMATION_BADGE.get());
						output.accept(WuxiaItems.DIAMOND_FORMATION_BADGE.get());
						output.accept(WuxiaItems.EMERALD_FORMATION_BADGE.get());
						output.accept(WuxiaItems.NETHERITE_FORMATION_BADGE.get());
						output.accept(WuxiaItems.CELESTIAL_IRON_FORMATION_BADGE.get());
						output.accept(WuxiaItems.STONE_RUNE_STENCIL.get());
						output.accept(WuxiaItems.COPPER_RUNE_STENCIL.get());
						output.accept(WuxiaItems.IRON_RUNE_STENCIL.get());
						output.accept(WuxiaItems.GOLD_RUNE_STENCIL.get());
						output.accept(WuxiaItems.DIAMOND_RUNE_STENCIL.get());
						output.accept(WuxiaItems.NETHERITE_RUNE_STENCIL.get());
						output.accept(WuxiaItems.CELESTIAL_IRON_RUNE_STENCIL.get());
					})
					.build()
	);
}
