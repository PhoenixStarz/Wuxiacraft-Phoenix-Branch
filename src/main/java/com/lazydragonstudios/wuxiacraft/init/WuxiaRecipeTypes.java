package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.crafting.RuneMakingRecipe;
import com.lazydragonstudios.wuxiacraft.crafting.RuneMakingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WuxiaRecipeTypes {

	public static DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, WuxiaCraft.MOD_ID);

	public static RegistryObject<RecipeType<RuneMakingRecipe>> RUNEMAKING_RECIPE_TYPE = RECIPE_TYPES.register("runemaking_recipe_type",
			() -> RecipeType.simple(new ResourceLocation(WuxiaCraft.MOD_ID, "runemaking_recipe_type")));

	public static DeferredRegister<RecipeSerializer<?>> RECIPE_TYPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WuxiaCraft.MOD_ID);

	public static RegistryObject<RecipeSerializer<RuneMakingRecipe>> RUNEMAKING_SERIALIZER = RECIPE_TYPE_SERIALIZERS
			.register("runemaking", RuneMakingRecipeSerializer::new);

}
