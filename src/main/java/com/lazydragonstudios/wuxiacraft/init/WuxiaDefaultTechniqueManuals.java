package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.TechniqueGrid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Supplier;

public class WuxiaDefaultTechniqueManuals {

	static final HashMap<ResourceLocation, Supplier<ItemStack>> DEFAULT_MANUALS = new HashMap<>();

	public static void init() {
		TechniqueGrid fire_manipulation = new TechniqueGrid();
		fire_manipulation.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		fire_manipulation.addGridNode(new Point(-1, 0), WuxiaTechniqueAspects.CINDER.getId(), BigDecimal.TEN);
		fire_manipulation.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.CINDER.getId(), BigDecimal.TEN);
		fire_manipulation.addGridNode(new Point(1, -1), WuxiaTechniqueAspects.MAGIC_BURNING.getId(), BigDecimal.TEN);
		fire_manipulation.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.MAGIC_BURNING.getId(), BigDecimal.TEN);
		fire_manipulation.addGridNode(new Point(0, -1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		fire_manipulation.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerNewManual(new ResourceLocation(WuxiaCraft.MOD_ID, "fire_manipulation"), 1, fire_manipulation);

		TechniqueGrid legends_of_water = new TechniqueGrid();
		legends_of_water.addGridNode(new Point(0, -1), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		legends_of_water.addGridNode(new Point(1, -1), WuxiaTechniqueAspects.DROP.getId(), BigDecimal.TEN);
		legends_of_water.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.DROP.getId(), BigDecimal.TEN);
		legends_of_water.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.STREAM.getId(), BigDecimal.TEN);
		legends_of_water.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerNewManual(new ResourceLocation(WuxiaCraft.MOD_ID, "legends_of_water"), 1, legends_of_water);

		TechniqueGrid nature_observation = new TechniqueGrid();
		nature_observation.addGridNode(new Point(-1, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		nature_observation.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.SEED.getId(), BigDecimal.TEN);
		nature_observation.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.SEED.getId(), BigDecimal.TEN);
		nature_observation.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.BRANCHING.getId(), BigDecimal.TEN);
		nature_observation.addGridNode(new Point(1, -1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerNewManual(new ResourceLocation(WuxiaCraft.MOD_ID, "nature_observation"), 1, nature_observation);

		TechniqueGrid lightning_replication = new TechniqueGrid();
		lightning_replication.addGridNode(new Point(1, -1), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		lightning_replication.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SPARK.getId(), BigDecimal.TEN);
		lightning_replication.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.ARC.getId(), BigDecimal.TEN);
		lightning_replication.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerNewManual(new ResourceLocation(WuxiaCraft.MOD_ID, "lightning_replication"), 1, lightning_replication);

		TechniqueGrid metallic_reinforcement = new TechniqueGrid();
		metallic_reinforcement.addGridNode(new Point(0, -1), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		metallic_reinforcement.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.ORE.getId(), BigDecimal.TEN);
		metallic_reinforcement.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.SHARPNESS.getId(), BigDecimal.TEN);
		metallic_reinforcement.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerNewManual(new ResourceLocation(WuxiaCraft.MOD_ID, "metallic_reinforcement"), 1, metallic_reinforcement);

		TechniqueGrid earth_motion = new TechniqueGrid();
		earth_motion.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		earth_motion.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.DUST.getId(), BigDecimal.TEN);
		earth_motion.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.TREMOR.getId(), BigDecimal.TEN);
		earth_motion.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerNewManual(new ResourceLocation(WuxiaCraft.MOD_ID, "earth_motion"), 1, earth_motion);

		TechniqueGrid sword_basic_set = new TechniqueGrid();
		sword_basic_set.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		sword_basic_set.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.BASIC_SWORD_SET.getId(), BigDecimal.TEN);
		sword_basic_set.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.PURE_QI_FLOW.getId(), BigDecimal.TEN);
		sword_basic_set.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerNewManual(new ResourceLocation(WuxiaCraft.MOD_ID, "sword_basic_set"), 1, sword_basic_set);

		TechniqueGrid body_forging = new TechniqueGrid();
		body_forging.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		body_forging.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.QI_STRAND.getId(), BigDecimal.TEN);
		body_forging.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.QI_TEMPERING.getId(), BigDecimal.TEN);
		body_forging.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "body_forging"), 1, body_forging);

		TechniqueGrid chronicles_of_fire_forging = new TechniqueGrid();
		chronicles_of_fire_forging.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		chronicles_of_fire_forging.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.CINDER.getId(), BigDecimal.TEN);
		chronicles_of_fire_forging.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.SCORCH.getId(), BigDecimal.TEN);
		chronicles_of_fire_forging.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "chronicles_of_fire_forging"), 1, chronicles_of_fire_forging);

		TechniqueGrid mountain_body = new TechniqueGrid();
		mountain_body.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		mountain_body.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.DUST.getId(), BigDecimal.TEN);
		mountain_body.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.HARDENING.getId(), BigDecimal.TEN);
		mountain_body.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "mountain_body"), 1, mountain_body);

		TechniqueGrid iron_palms = new TechniqueGrid();
		iron_palms.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		iron_palms.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.ORE.getId(), BigDecimal.TEN);
		iron_palms.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.METAL_SKIN.getId(), BigDecimal.TEN);
		iron_palms.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "iron_palms"), 1, iron_palms);

		TechniqueGrid ocean_body = new TechniqueGrid();
		ocean_body.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		ocean_body.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.DROP.getId(), BigDecimal.TEN);
		ocean_body.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.SPLASH.getId(), BigDecimal.TEN);
		ocean_body.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "ocean_body"), 1, ocean_body);

		TechniqueGrid druidic_body = new TechniqueGrid();
		druidic_body.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		druidic_body.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SEED.getId(), BigDecimal.TEN);
		druidic_body.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.BARK.getId(), BigDecimal.TEN);
		druidic_body.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "druidic_body"), 1, druidic_body);

		TechniqueGrid surging_thunder = new TechniqueGrid();
		surging_thunder.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		surging_thunder.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.ARC.getId(), BigDecimal.TEN);
		surging_thunder.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.CONDUIT.getId(), BigDecimal.TEN);
		surging_thunder.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "surging_thunder"), 1, surging_thunder);

		TechniqueGrid spacing_out = new TechniqueGrid();
		spacing_out.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		spacing_out.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SPACE_DETECTION.getId(), BigDecimal.TEN);
		spacing_out.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.SPACE_TEAR.getId(), BigDecimal.TEN);
		spacing_out.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerNewManual(new ResourceLocation(WuxiaCraft.MOD_ID, "spacing_out"), 1, spacing_out);
	}

	public static void registerNewManual(ResourceLocation resourceLocation, int radius, TechniqueGrid grid) {
		DEFAULT_MANUALS.put(resourceLocation, () -> {
			var itemStack = new ItemStack(WuxiaItems.ESSENCE_MANUAL.get());
			var itemTag = itemStack.getTag();
			if (itemTag == null) {
				itemTag = new CompoundTag();
				itemStack.setTag(itemTag);
			}
			itemTag.putString("name", resourceLocation.getNamespace() + ".technique." + resourceLocation.getPath());
			itemTag.putInt("radius", radius);
			itemTag.put("technique-grid", grid.serialize());
			return itemStack;
		});
	}

	public static void registerBodyManual(ResourceLocation resourceLocation, int radius, TechniqueGrid grid) {
		DEFAULT_MANUALS.put(resourceLocation, () -> {
			var itemStack = new ItemStack(WuxiaItems.BODY_MANUAL.get());
			var itemTag = itemStack.getTag();
			if (itemTag == null) {
				itemTag = new CompoundTag();
				itemStack.setTag(itemTag);
			}
			itemTag.putString("name", resourceLocation.getNamespace() + ".technique." + resourceLocation.getPath());
			itemTag.putInt("radius", radius);
			itemTag.put("technique-grid", grid.serialize());
			return itemStack;
		});
	}

	@Nullable
	public static Supplier<ItemStack> getDefaultManual(ResourceLocation resourceLocation) {
		return DEFAULT_MANUALS.get(resourceLocation);
	}

	public static Set<ResourceLocation> getAllKeys() {
		return DEFAULT_MANUALS.keySet();
	}

}
