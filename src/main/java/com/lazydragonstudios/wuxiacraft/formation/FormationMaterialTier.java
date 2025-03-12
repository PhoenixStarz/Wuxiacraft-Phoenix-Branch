package com.lazydragonstudios.wuxiacraft.formation;

import com.lazydragonstudios.wuxiacraft.init.WuxiaItems;
import com.lazydragonstudios.wuxiacraft.init.WuxiaBlocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Material;

import java.math.BigDecimal;

public enum FormationMaterialTier {

	CELESTIAL_IRON(new BigDecimal("60"), 8f, Material.METAL, Items.IRON_BLOCK),
	STONE(new BigDecimal("3"), 3f, Material.STONE, Items.STONE),
	COPPER(new BigDecimal("5"), 4f, Material.METAL, Items.COPPER_BLOCK),
	IRON(new BigDecimal("6"), 4f, Material.METAL, Items.IRON_BLOCK),
	LAPIS(new BigDecimal("8"), 3f, Material.STONE, Items.LAPIS_BLOCK),
	GOLD(new BigDecimal("10"), 4f, Material.METAL, Items.GOLD_BLOCK),
	DIAMOND(new BigDecimal("14"), 6f, Material.METAL, Items.DIAMOND_BLOCK),
	EMERALD(new BigDecimal("16"), 6f, Material.STONE, Items.EMERALD_BLOCK),
	NETHERITE(new BigDecimal("30"), 8f, Material.STONE, Items.NETHERITE_BLOCK);

	public final BigDecimal materialModifier;

	public final float blockStrength;

	public final Material blockMaterial;

	public final Item materialBlockItem;

	FormationMaterialTier(BigDecimal materialModifier, float blockStrength, Material blockMaterial, Item materialBlockItem) {
		this.materialModifier = materialModifier;
		this.blockStrength = blockStrength;
		this.blockMaterial = blockMaterial;
		this.materialBlockItem = materialBlockItem;
	}

	public static FormationMaterialTier getTierFromItem(Item item) {
		for (var tier : FormationMaterialTier.values()) {
			if (tier.materialBlockItem == item) return tier;
		}
		return null;
	}
}
