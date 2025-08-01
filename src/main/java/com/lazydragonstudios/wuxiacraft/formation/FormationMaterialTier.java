package com.lazydragonstudios.wuxiacraft.formation;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.math.BigDecimal;

public enum FormationMaterialTier {

	STONE(new BigDecimal("3"), 3f, Items.STONE),
	COPPER(new BigDecimal("5"), 3f, Items.COPPER_BLOCK),
	IRON(new BigDecimal("6"), 4f, Items.IRON_BLOCK),
	LAPIS(new BigDecimal("8"), 4f, Items.LAPIS_BLOCK),
	GOLD(new BigDecimal("10"), 4f, Items.GOLD_BLOCK),
	EMERALD(new BigDecimal("14"), 6f, Items.EMERALD_BLOCK),
	DIAMOND(new BigDecimal("16"), 6f, Items.DIAMOND_BLOCK),
	NETHERITE(new BigDecimal("30"), 8f, Items.NETHERITE_BLOCK),
	CELESTIAL_IRON(new BigDecimal("60"), 8f, Items.IRON_BLOCK);

	
	public final BigDecimal materialModifier;

	public final float blockStrength;

	public final Item materialBlockItem;

	FormationMaterialTier(BigDecimal materialModifier, float blockStrength, Item materialBlockItem) {
		this.materialModifier = materialModifier;
		this.blockStrength = blockStrength;
		this.materialBlockItem = materialBlockItem;
	}

	public static FormationMaterialTier getTierFromItem(Item item) {
		for (var tier : FormationMaterialTier.values()) {
			if (tier.materialBlockItem == item) return tier;
		}
		return null;
	}
}
