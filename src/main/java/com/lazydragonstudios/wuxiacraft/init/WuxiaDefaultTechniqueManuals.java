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
		TechniqueGrid chronicles_of_fire_forging = new TechniqueGrid();
		chronicles_of_fire_forging.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		chronicles_of_fire_forging.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.CINDER.getId(), BigDecimal.TEN);
		chronicles_of_fire_forging.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.SCORCH.getId(), BigDecimal.TEN);
		chronicles_of_fire_forging.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "chronicles_of_fire_forging"), 1, chronicles_of_fire_forging);
		TechniqueGrid mountain_bone_body = new TechniqueGrid();
		mountain_bone_body.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		mountain_bone_body.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.DUST.getId(), BigDecimal.TEN);
		mountain_bone_body.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.HARDENING.getId(), BigDecimal.TEN);
		mountain_bone_body.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "mountain_bone_body"), 1, mountain_bone_body);
		TechniqueGrid steel_forged_body = new TechniqueGrid();
		steel_forged_body.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		steel_forged_body.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.ORE.getId(), BigDecimal.TEN);
		steel_forged_body.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.METAL_SKIN.getId(), BigDecimal.TEN);
		steel_forged_body.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "steel_forged_body"), 1, steel_forged_body);
		TechniqueGrid ocean_tide_body = new TechniqueGrid();
		ocean_tide_body.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		ocean_tide_body.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.DROP.getId(), BigDecimal.TEN);
		ocean_tide_body.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.SPLASH.getId(), BigDecimal.TEN);
		ocean_tide_body.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "ocean_tide_body"), 1, ocean_tide_body);
		TechniqueGrid druidic_body = new TechniqueGrid();
		druidic_body.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		druidic_body.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SEED.getId(), BigDecimal.TEN);
		druidic_body.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.BARK.getId(), BigDecimal.TEN);
		druidic_body.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "druidic_body"), 1, druidic_body);

		TechniqueGrid surging_thunder = new TechniqueGrid();
		surging_thunder.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		surging_thunder.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SPARK.getId(), BigDecimal.TEN);
		surging_thunder.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.CONDUIT.getId(), BigDecimal.TEN);
		surging_thunder.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "surging_thunder"), 2, surging_thunder);
		TechniqueGrid storm_forged_body = new TechniqueGrid();
		storm_forged_body.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		storm_forged_body.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.BREEZE.getId(), BigDecimal.TEN);
		storm_forged_body.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.AIRFLOW.getId(), BigDecimal.TEN);
		storm_forged_body.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "storm_forged_body"), 2, storm_forged_body);
		TechniqueGrid venom_etched_body = new TechniqueGrid();
		venom_etched_body.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		venom_etched_body.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.VENOM.getId(), BigDecimal.TEN);
		venom_etched_body.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.CORRUPTION.getId(), BigDecimal.TEN);
		venom_etched_body.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "venom_etched_body"), 2, venom_etched_body);
		TechniqueGrid radiant_tempering = new TechniqueGrid();
		radiant_tempering.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		radiant_tempering.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.STARRY_BATH.getId(), BigDecimal.TEN);
		radiant_tempering.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.LUMEN.getId(), BigDecimal.TEN);
		radiant_tempering.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "radiant_tempering"), 2, radiant_tempering);
		TechniqueGrid umbral_imbuement = new TechniqueGrid();
		umbral_imbuement.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		umbral_imbuement.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SHADOW_BATH.getId(), BigDecimal.TEN);
		umbral_imbuement.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.DIM.getId(), BigDecimal.TEN);
		umbral_imbuement.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "umbral_imbuement"), 2, umbral_imbuement);

		
		TechniqueGrid fire_manipulation = new TechniqueGrid();
		fire_manipulation.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		fire_manipulation.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.CINDER.getId(), BigDecimal.TEN);
		fire_manipulation.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.MAGIC_BURNING.getId(), BigDecimal.TEN);
		fire_manipulation.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "fire_manipulation"), 1, fire_manipulation);
		TechniqueGrid legends_of_water = new TechniqueGrid();
		legends_of_water.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		legends_of_water.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.DROP.getId(), BigDecimal.TEN);
		legends_of_water.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.STREAM.getId(), BigDecimal.TEN);
		legends_of_water.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "legends_of_water"), 1, legends_of_water);
		TechniqueGrid nature_observation = new TechniqueGrid();
		nature_observation.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		nature_observation.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SEED.getId(), BigDecimal.TEN);
		nature_observation.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.BRANCHING.getId(), BigDecimal.TEN);
		nature_observation.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "nature_observation"), 1, nature_observation);
		TechniqueGrid metallic_reinforcement = new TechniqueGrid();
		metallic_reinforcement.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		metallic_reinforcement.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.ORE.getId(), BigDecimal.TEN);
		metallic_reinforcement.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.SHARPNESS.getId(), BigDecimal.TEN);
		metallic_reinforcement.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "metallic_reinforcement"), 1, metallic_reinforcement);
		TechniqueGrid earth_motion = new TechniqueGrid();
		earth_motion.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		earth_motion.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.DUST.getId(), BigDecimal.TEN);
		earth_motion.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.TREMOR.getId(), BigDecimal.TEN);
		earth_motion.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "earth_motion"), 1, earth_motion);

		TechniqueGrid lightning_replication = new TechniqueGrid();
		lightning_replication.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		lightning_replication.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SPARK.getId(), BigDecimal.TEN);
		lightning_replication.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.ARC.getId(), BigDecimal.TEN);
		lightning_replication.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "lightning_replication"), 2, lightning_replication);
		TechniqueGrid gale_circulation = new TechniqueGrid();
		gale_circulation.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		gale_circulation.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.BREEZE.getId(), BigDecimal.TEN);
		gale_circulation.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.GALE.getId(), BigDecimal.TEN);
		gale_circulation.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "gale_circulation"), 2, gale_circulation);
		TechniqueGrid malignant_infusion = new TechniqueGrid();
		malignant_infusion.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		malignant_infusion.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.VENOM.getId(), BigDecimal.TEN);
		malignant_infusion.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.MALIGNANCE.getId(), BigDecimal.TEN);
		malignant_infusion.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "malignant_infusion"), 2, malignant_infusion);
		TechniqueGrid stellarthread_binding = new TechniqueGrid();
		stellarthread_binding.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		stellarthread_binding.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.STARRY_BATH.getId(), BigDecimal.TEN);
		stellarthread_binding.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.SHINE.getId(), BigDecimal.TEN);
		stellarthread_binding.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "stellarthread_binding"), 2, stellarthread_binding);
		TechniqueGrid midnight_tethering = new TechniqueGrid();
		midnight_tethering.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		midnight_tethering.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SHADOW_BATH.getId(), BigDecimal.TEN);
		midnight_tethering.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.GLOOM.getId(), BigDecimal.TEN);
		midnight_tethering.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "midnight_tethering"), 2, midnight_tethering);


		TechniqueGrid burning_clarity = new TechniqueGrid();
		burning_clarity.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		burning_clarity.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.CINDER.getId(), BigDecimal.TEN);
		burning_clarity.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.MIND_FLARE.getId(), BigDecimal.TEN);
		burning_clarity.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "burning_clarity"), 1, burning_clarity);
		TechniqueGrid grounding_oath = new TechniqueGrid();
		grounding_oath.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		grounding_oath.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.DUST.getId(), BigDecimal.TEN);
		grounding_oath.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.STILLNESS.getId(), BigDecimal.TEN);
		grounding_oath.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "grounding_oath"), 1, grounding_oath);
		TechniqueGrid steel_bound_discipline = new TechniqueGrid();
		steel_bound_discipline.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		steel_bound_discipline.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.ORE.getId(), BigDecimal.TEN);
		steel_bound_discipline.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.MAGNETIZATION.getId(), BigDecimal.TEN);
		steel_bound_discipline.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "steel_bound_discipline"), 1, steel_bound_discipline);
		TechniqueGrid drifting_oceans_current = new TechniqueGrid();
		drifting_oceans_current.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		drifting_oceans_current.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.DROP.getId(), BigDecimal.TEN);
		drifting_oceans_current.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.WAVING.getId(), BigDecimal.TEN);
		drifting_oceans_current.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "drifting_oceans_current"), 1, drifting_oceans_current);
		TechniqueGrid verdant_spirit = new TechniqueGrid();
		verdant_spirit.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		verdant_spirit.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SEED.getId(), BigDecimal.TEN);
		verdant_spirit.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.SWAYING.getId(), BigDecimal.TEN);
		verdant_spirit.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "verdant_spirit"), 1, verdant_spirit);

		TechniqueGrid stormwake_thunder = new TechniqueGrid();
		stormwake_thunder.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		stormwake_thunder.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SPARK.getId(), BigDecimal.TEN);
		stormwake_thunder.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.FLASH.getId(), BigDecimal.TEN);
		stormwake_thunder.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "stormwake_thunder"), 2, stormwake_thunder);
		TechniqueGrid seven_drifting_winds = new TechniqueGrid();
		seven_drifting_winds.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		seven_drifting_winds.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.BREEZE.getId(), BigDecimal.TEN);
		seven_drifting_winds.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.DRAFT.getId(), BigDecimal.TEN);
		seven_drifting_winds.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "seven_drifting_winds"), 2, seven_drifting_winds);
		TechniqueGrid corrosive_insight = new TechniqueGrid();
		corrosive_insight.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		corrosive_insight.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.VENOM.getId(), BigDecimal.TEN);
		corrosive_insight.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.CORROSION.getId(), BigDecimal.TEN);
		corrosive_insight.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "corrosive_insight"), 2, corrosive_insight);
		TechniqueGrid dawn_spirit_illumination = new TechniqueGrid();
		dawn_spirit_illumination.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		dawn_spirit_illumination.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.STARRY_BATH.getId(), BigDecimal.TEN);
		dawn_spirit_illumination.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.FLASH.getId(), BigDecimal.TEN);
		dawn_spirit_illumination.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "dawn_spirit_illumination"), 2, dawn_spirit_illumination);
		TechniqueGrid void_crown_eclipse = new TechniqueGrid();
		void_crown_eclipse.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		void_crown_eclipse.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SHADOW_BATH.getId(), BigDecimal.TEN);
		void_crown_eclipse.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.ECLIPSE.getId(), BigDecimal.TEN);
		void_crown_eclipse.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "void_crown_eclipse"), 2, void_crown_eclipse);

		TechniqueGrid qi_tempering = new TechniqueGrid();
		qi_tempering.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		qi_tempering.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.QI_STRAND.getId(), BigDecimal.TEN);
		qi_tempering.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.QI_TEMPERING.getId(), BigDecimal.TEN);
		qi_tempering.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "qi_tempering"), 1, qi_tempering);
		TechniqueGrid qi_flow = new TechniqueGrid();
		qi_flow.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		qi_flow.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.QI_STRAND.getId(), BigDecimal.TEN);
		qi_flow.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.QI_FLOW.getId(), BigDecimal.TEN);
		qi_flow.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "qi_flow"), 1, qi_flow);
		TechniqueGrid qi_enlightenment = new TechniqueGrid();
		qi_enlightenment.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		qi_enlightenment.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.QI_STRAND.getId(), BigDecimal.TEN);
		qi_enlightenment.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.QI_ENLIGHTENMENT.getId(), BigDecimal.TEN);
		qi_enlightenment.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "qi_enlightenment"), 1, qi_enlightenment);

		TechniqueGrid spacing_out = new TechniqueGrid();
		spacing_out.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		spacing_out.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.SPACE_DETECTION.getId(), BigDecimal.TEN);
		spacing_out.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.SPACE_TEAR.getId(), BigDecimal.TEN);
		spacing_out.addGridNode(new Point(0, -1), WuxiaTechniqueAspects.SPATIAL_TEMPERING.getId(), BigDecimal.TEN);
		spacing_out.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		spacing_out.addGridNode(new Point(1, -1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		spacing_out.addGridNode(new Point(-1, 0), WuxiaTechniqueAspects.SPATIAL_AMPLIFICATION.getId(), BigDecimal.TEN);
		registerDivineManual(new ResourceLocation(WuxiaCraft.MOD_ID, "spacing_out"), 1, spacing_out);
		TechniqueGrid timing_out = new TechniqueGrid();
		timing_out.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		timing_out.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.TIME_DETECTION.getId(), BigDecimal.TEN);
		timing_out.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.TIME_TEAR.getId(), BigDecimal.TEN);
		timing_out.addGridNode(new Point(0, -1), WuxiaTechniqueAspects.TEMPORAL_TEMPERING.getId(), BigDecimal.TEN);
		timing_out.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		timing_out.addGridNode(new Point(1, -1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		timing_out.addGridNode(new Point(-1, 0), WuxiaTechniqueAspects.TEMPORAL_AMPLIFICATION.getId(), BigDecimal.TEN);
		registerEssenceManual(new ResourceLocation(WuxiaCraft.MOD_ID, "timing_out"), 1, timing_out);
		TechniqueGrid reincarnated = new TechniqueGrid();
		reincarnated.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		reincarnated.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.ASHES_OF_REBIRTH.getId(), BigDecimal.TEN);
		reincarnated.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.CINDER_OF_RENEWAL.getId(), BigDecimal.TEN);
		reincarnated.addGridNode(new Point(0, -1), WuxiaTechniqueAspects.EMBER_OF_REKINDLING.getId(), BigDecimal.TEN);
		reincarnated.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		reincarnated.addGridNode(new Point(1, -1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		reincarnated.addGridNode(new Point(-1, 0), WuxiaTechniqueAspects.SPIRITUAL_RECONSTRUCTION.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "reincarnated"), 1, reincarnated);
		TechniqueGrid demonic_arts = new TechniqueGrid();
		demonic_arts.addGridNode(new Point(0, 0), WuxiaTechniqueAspects.DEVOURING.getId(), BigDecimal.TEN);
		demonic_arts.addGridNode(new Point(1, 0), WuxiaTechniqueAspects.CONSUMPTION.getId(), BigDecimal.TEN);
		demonic_arts.addGridNode(new Point(0, 1), WuxiaTechniqueAspects.GLUTTONY.getId(), BigDecimal.TEN);
		demonic_arts.addGridNode(new Point(-1, 1), WuxiaTechniqueAspects.BEELZEBUB.getId(), BigDecimal.TEN);
		demonic_arts.addGridNode(new Point(0, -1), WuxiaTechniqueAspects.ESSENCE_GATHERING.getId(), BigDecimal.TEN);
		demonic_arts.addGridNode(new Point(1, -1), WuxiaTechniqueAspects.BODY_GATHERING.getId(), BigDecimal.TEN);
		demonic_arts.addGridNode(new Point(-1, 0), WuxiaTechniqueAspects.DIVINE_GATHERING.getId(), BigDecimal.TEN);
		registerBodyManual(new ResourceLocation(WuxiaCraft.MOD_ID, "demonic_arts"), 1, demonic_arts);
	}

	public static void registerEssenceManual(ResourceLocation resourceLocation, int radius, TechniqueGrid grid) {
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
		public static void registerDivineManual(ResourceLocation resourceLocation, int radius, TechniqueGrid grid) {
		DEFAULT_MANUALS.put(resourceLocation, () -> {
			var itemStack = new ItemStack(WuxiaItems.DIVINE_MANUAL.get());
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
