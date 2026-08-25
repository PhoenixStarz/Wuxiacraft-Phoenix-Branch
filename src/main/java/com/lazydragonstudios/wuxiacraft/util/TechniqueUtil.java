package com.lazydragonstudios.wuxiacraft.util;


import com.google.gson.*;
import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaTechniqueAspects;
import com.lazydragonstudios.wuxiacraft.init.WuxiaBlocks;
import com.lazydragonstudios.wuxiacraft.init.WuxiaEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

public class TechniqueUtil {

	private static final HashMap<Item, HashMap<ResourceLocation, BigDecimal>> DEVOURING_ITEM_TO_REWARD = new HashMap<>();

	private static final HashMap<Block, HashMap<ResourceLocation, Double>> BLOCK_TO_CHANCED_ASPECT = new HashMap<>();

	private static final HashMap<EntityType<?>, HashMap<ResourceLocation, Double>> ENTITY_TO_CHANCED_ASPECT = new HashMap<>();

	private static final HashSet<ResourceLocation> WEAPON_ASPECTS = new HashSet<>();

	private static final HashSet<ResourceLocation> TRANSFORMATION_ASPECTS = new HashSet<>();

	public static void initTransformationAspects() {
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.KITSUNE_TRANSFORMATION.getId());
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.LIGHT_KITSUNE_TRANSFORMATION.getId());
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.SPATIAL_KITSUNE_TRANSFORMATION.getId());
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.DRAGON_TRANSFORMATION.getId());
		TRANSFORMATION_ASPECTS.add(WuxiaTechniqueAspects.AZURE_DRAGON_TRANSFORMATION.getId());
	}

	public static void initWeaponTechniques() {
		WEAPON_ASPECTS.add(WuxiaTechniqueAspects.BASIC_SWORD_SET.getId());
		WEAPON_ASPECTS.add(WuxiaTechniqueAspects.SWORD_QI_GATHERING.getId());
		WEAPON_ASPECTS.add(WuxiaTechniqueAspects.MAGICAL_SWORD_GLINT.getId());
	}

	public static HashSet<ResourceLocation> getWeaponAspects() {
		return WEAPON_ASPECTS;
	}

	public static HashSet<ResourceLocation> getTransformationAspects() {
		return TRANSFORMATION_ASPECTS;
	}

	public static void addBlockToAspectChanced(Block block, ResourceLocation aspect, double chance) {
		BLOCK_TO_CHANCED_ASPECT.putIfAbsent(block, new HashMap<>());
		BLOCK_TO_CHANCED_ASPECT.get(block).put(aspect, chance);
	}

	public static void addEntityToAspectChanced(EntityType<?> entityType, ResourceLocation aspect, double chance) {
		ENTITY_TO_CHANCED_ASPECT.putIfAbsent(entityType, new HashMap<>());
		ENTITY_TO_CHANCED_ASPECT.get(entityType).put(aspect, chance);
	}

	public static HashMap<ResourceLocation, Double> getAspectChancePerBlock(Block block) {
		return BLOCK_TO_CHANCED_ASPECT.getOrDefault(block, new HashMap<>());
	}

	public static HashMap<ResourceLocation, Double> getAspectChancePerEntity(EntityType<?> entityType) {
		return ENTITY_TO_CHANCED_ASPECT.getOrDefault(entityType, new HashMap<>());
	}

	public static void addDevouringData(Item item, ResourceLocation element, BigDecimal value) {
		DEVOURING_ITEM_TO_REWARD.putIfAbsent(item, new HashMap<>());
		DEVOURING_ITEM_TO_REWARD.get(item).put(element, value);
	}

	@Nonnull
	public static HashMap<ResourceLocation, BigDecimal> getDevouringDataPerItem(Item item) {
		return DEVOURING_ITEM_TO_REWARD.getOrDefault(item, new HashMap<>());
	}

    public static void loadDevouringData() {
		String path = "/data/wuxiacraft/aspects/devouring_data.json";

		try (InputStream in = TechniqueUtil.class.getResourceAsStream(path)) {
			if (in == null) WuxiaCraft.LOGGER.error("Missing resource: " + path);

			JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
			JsonArray arr = root.getAsJsonArray("devouring_data");
			for (JsonElement e : arr) {
				JsonObject obj = e.getAsJsonObject();

				ResourceLocation itemId = new ResourceLocation(obj.get("item").getAsString());
				ResourceLocation elementId = new ResourceLocation(obj.get("element").getAsString());
				BigDecimal value = obj.get("value").getAsBigDecimal();

				Item item = ForgeRegistries.ITEMS.getValue(itemId);
				if (item == null) WuxiaCraft.LOGGER.error("Unknown item: " + itemId);

				addDevouringData(item, elementId, value);
			}
		} catch (Exception ex) {
			WuxiaCraft.LOGGER.error("Failed to load devouring data", ex);
		}
    }

	public static void loadChancedAspectsBlocks() {
		String path = "/data/wuxiacraft/aspects/chanced_aspects_blocks.json";
		try (InputStream in = TechniqueUtil.class.getResourceAsStream(path)) {
			if (in == null) WuxiaCraft.LOGGER.error("Missing resource: " + path);

			JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
			JsonArray arr = root.getAsJsonArray("chanced_aspects_blocks");
			for (JsonElement e : arr) {
				JsonObject obj = e.getAsJsonObject();

				ResourceLocation blockId = new ResourceLocation(obj.get("block").getAsString());
				ResourceLocation aspectId = new ResourceLocation(obj.get("aspect").getAsString());
				double chance = obj.get("chance").getAsDouble();

				Block block = ForgeRegistries.BLOCKS.getValue(blockId);
				if (block == null) WuxiaCraft.LOGGER.error("Unknown block: " + blockId);

				addBlockToAspectChanced(block, aspectId, chance);
			}
		} catch (Exception ex) {
			WuxiaCraft.LOGGER.error("Failed to load chanced aspects blocks", ex);
		}
	}

	public static void loadChancedAspectsEntities() {
		String path = "/data/wuxiacraft/aspects/chanced_aspects_entities.json";
		try (InputStream in = TechniqueUtil.class.getResourceAsStream(path)) {
			if (in == null) WuxiaCraft.LOGGER.error("Missing resource: " + path);

			JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
			JsonArray arr = root.getAsJsonArray("chanced_aspects_entities");
			for (JsonElement e : arr) {
				JsonObject obj = e.getAsJsonObject();

				ResourceLocation entityID = new ResourceLocation(obj.get("entity").getAsString());
				ResourceLocation aspectId = new ResourceLocation(obj.get("aspect").getAsString());
				double chance = obj.get("chance").getAsDouble();

				EntityType<?> entity = ForgeRegistries.ENTITY_TYPES.getValue(entityID);
				if (entity == null) WuxiaCraft.LOGGER.error("Unknown entity: " + entityID);

				addEntityToAspectChanced(entity, aspectId, chance);
			}
		} catch (Exception ex) {
			WuxiaCraft.LOGGER.error("Failed to load chanced aspects entities", ex);
		}
	}

}
