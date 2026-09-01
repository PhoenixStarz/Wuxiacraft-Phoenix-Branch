package com.lazydragonstudios.wuxiacraft.item;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.init.WuxiaItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ToolMaterialTiers
{

    public static final Tier CELESTIAL_IRON = new ForgeTier(3,// mining level
            1872,                // durability
            (float) 13,          // mining speed 
            (float) 4,           // attackDamageBonus 
            16,                  // enchantmentValue 
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(WuxiaItems.CELESTIAL_IRON_INGOT.get()));
    
    public static void register()
    {
        TierSortingRegistry.registerTier(CELESTIAL_IRON, new ResourceLocation(WuxiaCraft.MOD_ID, "celestial_iron"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));
    }
}