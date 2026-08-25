package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.combat.WuxiaDamageSource;
import com.lazydragonstudios.wuxiacraft.cultivation.BodyCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.*;
import com.lazydragonstudios.wuxiacraft.event.CultivatingEvent;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.item.SoulCore;
import com.lazydragonstudios.wuxiacraft.util.TechniqueUtil;
import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class WuxiaTechniqueAspects {

	public static DeferredRegister<TechniqueAspect> ASPECTS = DeferredRegister.create(new ResourceLocation(WuxiaCraft.MOD_ID, "technique_aspects"), WuxiaCraft.MOD_ID);

	public static RegistryObject<TechniqueAspect> START = ASPECTS.register("start", StartAspect::new);

	/**
	 * this is an empty aspect should, should not even be mentioned
	 * but is there to serve as default for when the grid is empty and avoid null pointers
	 * plus it says it won't connect to anyone
	 */
	public static RegistryObject<TechniqueAspect> EMPTY = ASPECTS.register("empty",
			() -> new TechniqueAspect() {

				@Override
				public boolean canConnect(TechniqueAspect aspect) {
					return false;
				}
			}
	);

	public static RegistryObject<TechniqueAspect> UNKNOWN = ASPECTS.register("unknown",
			() -> new TechniqueAspect() {

				@Override
				public boolean canConnect(TechniqueAspect aspect) {
					return false;
				}
			}
	);

	//////////////////////////////////////////
	//           Gathering Nodes            //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> BODY_GATHERING = ASPECTS.register("body_gathering",
			() -> new SystemGather(System.BODY)
	);

	public static RegistryObject<TechniqueAspect> DIVINE_GATHERING = ASPECTS.register("divine_gathering",
			() -> new SystemGather(System.DIVINE)
	);

	public static RegistryObject<TechniqueAspect> ESSENCE_GATHERING = ASPECTS.register("essence_gathering",
			() -> new SystemGather(System.ESSENCE)
	);

	//////////////////////////////////////////
	//           Fire Generation ones       //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> CINDER = ASPECTS.register("cinder",
			() -> new ElementalGenerator(1d, WuxiaElements.FIRE.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ember"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> EMBER = ASPECTS.register("ember",
			() -> new ElementalGenerator(3d, WuxiaElements.FIRE.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "blazing"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> BLAZE = ASPECTS.register("blazing",
			() -> new ElementalGenerator(9d, WuxiaElements.FIRE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "cinder")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earth_scorching"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> EARTH_SCORCHING = ASPECTS.register("earth_scorching",
			() -> new ElementalGenerator(27d, WuxiaElements.FIRE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ember")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heaven_burning_fire"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> HEAVEN_BURNING_FIRE = ASPECTS.register("heaven_burning_fire",
			() -> new ElementalGenerator(81d, WuxiaElements.FIRE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "blazing")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "everlasting_flame"), cultivation);
								tryToLearnSpaceGeneration(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> EVERLASTING_FLAME = ASPECTS.register("everlasting_flame",
			() -> new ElementalGenerator(243d, WuxiaElements.FIRE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earth_scorching")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "solar_flame"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SOLAR_FLAME = ASPECTS.register("solar_flame",
			() -> new ElementalGenerator(729d, WuxiaElements.FIRE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heaven_burning_fire")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "immortal_flame"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> IMMORTAL_FLAME = ASPECTS.register("immortal_flame",
			() -> new ElementalGenerator(2187d, WuxiaElements.FIRE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "everlasting_flame")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "flame_emperor"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> FLAME_EMPEROR = ASPECTS.register("flame_emperor",
			() -> new ElementalGenerator(6561d, WuxiaElements.FIRE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "immortal_flame")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//       Fire Transformation ones       //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> SCORCH = ASPECTS.register("scorch",
			() -> new ElementSystemConverter(3d, WuxiaElements.FIRE.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "cinder"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ember")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "fiery_skin"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> FIERY_SKIN = ASPECTS.register("fiery_skin",
			() -> new ElementSystemConverter(12d, WuxiaElements.FIRE.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "scorch"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "blazing")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnSpaceConversionBody(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> MAGIC_BURNING = ASPECTS.register("magic_burning",
			() -> new ElementSystemConverter(3d, WuxiaElements.FIRE.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "cinder"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ember")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_incineration"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MAGICAL_INCINERATION = ASPECTS.register("magical_incineration",
			() -> new ElementSystemConverter(12d, WuxiaElements.FIRE.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magic_burning"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "blazing")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "essence_combustion"), cultivation);
								tryToLearnSpaceConversionEssence(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> ESSENCE_COMBUSTION = ASPECTS.register("essence_combustion",
			() -> new ElementSystemConverter(60d, WuxiaElements.FIRE.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_incineration"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earth_scorching")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> MIND_FLARE = ASPECTS.register("mind_flare",
			() -> new ElementSystemConverter(3d, WuxiaElements.FIRE.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "cinder"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ember")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "soul_flame"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SOUL_FLAME = ASPECTS.register("soul_flame",
			() -> new ElementSystemConverter(12d, WuxiaElements.FIRE.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "mind_flare"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "blazing")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnSpaceConversionDivine(cultivation);
							}))
	);

	//////////////////////////////////////////
	//           Earth Generation ones      //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> DUST = ASPECTS.register("dust",
			() -> new ElementalGenerator(1d, WuxiaElements.EARTH.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dirt"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> DIRT = ASPECTS.register("dirt",
			() -> new ElementalGenerator(3d, WuxiaElements.EARTH.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "pebbles"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> PEBBLES = ASPECTS.register("pebbles",
			() -> new ElementalGenerator(9d, WuxiaElements.EARTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dust")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "stone"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> STONE = ASPECTS.register("stone",
			() -> new ElementalGenerator(27d, WuxiaElements.EARTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dirt")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "crystal"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> CRYSTAL = ASPECTS.register("crystal",
			() -> new ElementalGenerator(81d, WuxiaElements.EARTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "pebbles")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earth_crystal"), cultivation);
								tryToLearnSpaceGeneration(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> EARTH_CRYSTAL = ASPECTS.register("earth_crystal",
			() -> new ElementalGenerator(243d, WuxiaElements.EARTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "stone")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heavenly_crystal"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> HEAVENLY_CRYSTAL = ASPECTS.register("heavenly_crystal",
			() -> new ElementalGenerator(729d, WuxiaElements.EARTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "crystal")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "immortal_crystal"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> IMMORTAL_CRYSTAL = ASPECTS.register("immortal_crystal",
			() -> new ElementalGenerator(2187d, WuxiaElements.EARTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earth_crystal")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "diamond_god"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> DIAMOND_GOD = ASPECTS.register("diamond_god",
			() -> new ElementalGenerator(6561d, WuxiaElements.EARTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heavenly_crystal")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//       Earth Transformation ones      //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> HARDENING = ASPECTS.register("hardening",
			() -> new ElementSystemConverter(3d, WuxiaElements.EARTH.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dust"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dirt")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earthen_construct"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> EARTHEN_CONSTRUCT = ASPECTS.register("earthen_construct",
			() -> new ElementSystemConverter(12d, WuxiaElements.EARTH.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "hardening"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "pebbles")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnSpaceConversionBody(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> DIAMOND_CONSTRUCT = ASPECTS.register("diamond_construct",
			() -> new ElementSystemConverter(300d, WuxiaElements.EARTH.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earthen_construct"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "crystal")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> TREMOR = ASPECTS.register("tremor",
			() -> new ElementSystemConverter(3d, WuxiaElements.EARTH.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dust"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dirt")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_tremor"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MAGICAL_TREMOR = ASPECTS.register("magical_tremor",
			() -> new ElementSystemConverter(12d, WuxiaElements.EARTH.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "tremor"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "pebbles")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "rock_slide"), cultivation);
								tryToLearnSpaceConversionEssence(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> ROCK_SLIDE = ASPECTS.register("rock_slide",
			() -> new ElementSystemConverter(60d, WuxiaElements.EARTH.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_tremor"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "stone")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> STILLNESS = ASPECTS.register("stillness",
			() -> new ElementSystemConverter(3d, WuxiaElements.EARTH.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dust"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dirt")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "serenity"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SERENITY = ASPECTS.register("serenity",
			() -> new ElementSystemConverter(12d, WuxiaElements.EARTH.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "stillness"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "pebbles")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnSpaceConversionDivine(cultivation);
							}))
	);

	//////////////////////////////////////////
	//       Earth Special ones             //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> ROOT = ASPECTS.register("root",
			() -> new ElementToElementConverter(3d, 1.1d, WuxiaElements.EARTH.getId(), WuxiaElements.EARTH.getId())
	);

	//////////////////////////////////////////
	//           Water Generation ones      //
	//////////////////////////////////////////
	public static RegistryObject<TechniqueAspect> DROP = ASPECTS.register("drop",
			() -> new ElementalGenerator(1d, WuxiaElements.WATER.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "flow"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> FLOW = ASPECTS.register("flow",
			() -> new ElementalGenerator(3d, WuxiaElements.WATER.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "waterfall"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> WATERFALL = ASPECTS.register("waterfall",
			() -> new ElementalGenerator(9d, WuxiaElements.WATER.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "drop")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ocean_heart"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> OCEAN_HEART = ASPECTS.register("ocean_heart",
			() -> new ElementalGenerator(27d, WuxiaElements.WATER.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "flow")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heavenly_water"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> HEAVENLY_WATER = ASPECTS.register("heavenly_water",
			() -> new ElementalGenerator(81d, WuxiaElements.WATER.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "waterfall")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ocean_tide"), cultivation);
								tryToLearnSpaceGeneration(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> OCEAN_TIDE = ASPECTS.register("ocean_tide",
			() -> new ElementalGenerator(243d, WuxiaElements.WATER.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ocean_heart")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "lunar_water"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> LUNAR_WATER = ASPECTS.register("lunar_water",
			() -> new ElementalGenerator(729d, WuxiaElements.WATER.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heavenly_water")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "immortal_water"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> IMMORTAL_WATER = ASPECTS.register("immortal_water",
			() -> new ElementalGenerator(2187d, WuxiaElements.WATER.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ocean_tide")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "god_of_water"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> GOD_OF_WATER = ASPECTS.register("god_of_water",
			() -> new ElementalGenerator(6561d, WuxiaElements.WATER.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "lunar_water")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//       Water Transformation ones      //
	//////////////////////////////////////////
	public static RegistryObject<TechniqueAspect> SPLASH = ASPECTS.register("splash",
			() -> new ElementSystemConverter(3d, WuxiaElements.WATER.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "drop"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "flow")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "inner_stream"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> INNER_STREAM = ASPECTS.register("inner_stream",
			() -> new ElementSystemConverter(12d, WuxiaElements.WATER.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "splash"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "waterfall")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnSpaceConversionBody(cultivation);
								cultivation.getAspects().learnAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "azure_dragon_transformation"), cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> STREAM = ASPECTS.register("stream",
			() -> new ElementSystemConverter(3d, WuxiaElements.WATER.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "drop"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "flow")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_flow"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MAGICAL_FLOW = ASPECTS.register("magical_flow",
			() -> new ElementSystemConverter(12d, WuxiaElements.WATER.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "stream"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "waterfall")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_tide"), cultivation);
								tryToLearnSpaceConversionEssence(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> MAGICAL_TIDE = ASPECTS.register("magical_tide",
			() -> new ElementSystemConverter(60d, WuxiaElements.WATER.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_flow"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ocean_heart")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> WAVING = ASPECTS.register("waving",
			() -> new ElementSystemConverter(3d, WuxiaElements.WATER.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "drop"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "flow")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "crashing_waves"), cultivation)))
	);
	
	public static RegistryObject<TechniqueAspect> CRASHING_WAVES = ASPECTS.register("crashing_waves",
			() -> new ElementSystemConverter(12d, WuxiaElements.WATER.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "waving"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "waterfall")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnSpaceConversionDivine(cultivation);
							}))
	);

	//////////////////////////////////////////
	//       Water Special ones             //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> SNOW = ASPECTS.register("snow",
			() -> new ConditionalElementalGenerator(3d, WuxiaElements.WATER.getId()) {

				@Override
				public void onCultivate(CultivatingEvent event) {
					if (event.getPlayer().getTicksFrozen() > 0) {
						event.addElement(WuxiaElements.WATER.getId(), new BigDecimal(1.2));
					}
				}
			}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "waterfall"), cultivation)))
	);

	//////////////////////////////////////////
	//       Wood Generation ones           //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> SEED = ASPECTS.register("seed",
			() -> new ElementalGenerator(1d, WuxiaElements.WOOD.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "moss"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MOSS = ASPECTS.register("moss",
			() -> new ElementalGenerator(3d, WuxiaElements.WOOD.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "sprout"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPROUT = ASPECTS.register("sprout",
			() -> new ElementalGenerator(9d, WuxiaElements.WOOD.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "seed")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "herb"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> HERB = ASPECTS.register("herb",
			() -> new ElementalGenerator(27d, WuxiaElements.WOOD.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "moss")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "bush"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> BUSH = ASPECTS.register("bush",
			() -> new ElementalGenerator(81d, WuxiaElements.WOOD.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "sprout")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "sapling"), cultivation);
								tryToLearnSpaceGeneration(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> SAPLING = ASPECTS.register("sapling",
			() -> new ElementalGenerator(243d, WuxiaElements.WOOD.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "herb")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "tree"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TREE = ASPECTS.register("tree",
			() -> new ElementalGenerator(729d, WuxiaElements.WOOD.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "bush")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ancient_tree"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ANCIENT_TREE = ASPECTS.register("ancient_tree",
			() -> new ElementalGenerator(2187d, WuxiaElements.WOOD.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "sapling")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "world_tree"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> WORLD_TREE = ASPECTS.register("world_tree",
			() -> new ElementalGenerator(6561d, WuxiaElements.WOOD.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "tree")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//       Wood Transformation ones       //
	//////////////////////////////////////////
	public static RegistryObject<TechniqueAspect> BARK = ASPECTS.register("bark",
			() -> new ElementSystemConverter(3d, WuxiaElements.WOOD.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "seed"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "moss")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "treant"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TREANT = ASPECTS.register("treant",
			() -> new ElementSystemConverter(12d, WuxiaElements.WOOD.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "bark"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "sprout")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnSpaceConversionBody(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> BRANCHING = ASPECTS.register("branching",
			() -> new ElementSystemConverter(3d, WuxiaElements.WOOD.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "seed"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "moss")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_growth"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MAGICAL_GROWTH = ASPECTS.register("magical_growth",
			() -> new ElementSystemConverter(12d, WuxiaElements.WOOD.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "branching"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "sprout")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "nature_dominance"), cultivation);
								tryToLearnSpaceConversionEssence(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> NATURE_DOMINANCE = ASPECTS.register("nature_dominance",
			() -> new ElementSystemConverter(60d, WuxiaElements.WOOD.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_growth"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "herb")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> SWAYING = ASPECTS.register("swaying",
			() -> new ElementSystemConverter(3d, WuxiaElements.WOOD.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "seed"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "moss")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "flowing_foliage"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> FLOWING_FOLIAGE = ASPECTS.register("flowing_foliage",
			() -> new ElementSystemConverter(12d, WuxiaElements.WOOD.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "swaying"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "sprout")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnSpaceConversionDivine(cultivation);
							}))
	);

	//////////////////////////////////////////
	//       Wooden Special ones            //
	//////////////////////////////////////////
	//TODO make this get stronger by adding a flower factor to it and convert this to special
	public static RegistryObject<TechniqueAspect> FLOWER = ASPECTS.register("flower",
			() -> new ElementalGenerator(1d, WuxiaElements.WOOD.getId())
	);

	public static RegistryObject<TechniqueAspect> LICHEN = ASPECTS.register("lichen",
			() -> new ElementToElementConverter(3d, 1.5d, WuxiaElements.WOOD.getId(), WuxiaElements.WOOD.getId())
	);

	public static RegistryObject<TechniqueAspect> STEM = ASPECTS.register("stem",
			() -> new ElementToElementConverter(15d, 1.8d, WuxiaElements.WOOD.getId(), WuxiaElements.WOOD.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6")))
	);

	public static RegistryObject<TechniqueAspect> CHARCOAL = ASPECTS.register("charcoal",
			() -> new ElementToElementConverter(3d, 1.1d, WuxiaElements.WOOD.getId(), WuxiaElements.FIRE.getId())
					.setCanLearn(cultivation -> (cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "seed"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "moss")))
							&& (cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "cinder"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ember"))))
	);

	//////////////////////////////////////////
	//       Metal Transformation ones      //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> ORE = ASPECTS.register("ore",
			() -> new ElementalGenerator(1d, WuxiaElements.METAL.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_nugget"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> METAL_NUGGET = ASPECTS.register("metal_nugget",
			() -> new ElementalGenerator(3d, WuxiaElements.METAL.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_ingot"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> METAL_INGOT = ASPECTS.register("metal_ingot",
			() -> new ElementalGenerator(9d, WuxiaElements.METAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ore")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_block"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> METAL_BLOCK = ASPECTS.register("metal_block",
			() -> new ElementalGenerator(27d, WuxiaElements.METAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_nugget")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earthly_metal"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> EARTHLY_METAL = ASPECTS.register("earthly_metal",
			() -> new ElementalGenerator(81d, WuxiaElements.METAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_ingot")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heavenly_metal"), cultivation);
								tryToLearnSpaceGeneration(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> HEAVENLY_METAL = ASPECTS.register("heavenly_metal",
			() -> new ElementalGenerator(243d, WuxiaElements.METAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_block")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "godly_metal"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> GODLY_METAL = ASPECTS.register("godly_metal",
			() -> new ElementalGenerator(729d, WuxiaElements.METAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earthly_metal")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ancient_metal"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ANCIENT_METAL = ASPECTS.register("ancient_metal",
			() -> new ElementalGenerator(2187d, WuxiaElements.METAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heavenly_metal")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "world_metal"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> WORLD_METAL = ASPECTS.register("world_metal",
			() -> new ElementalGenerator(6561d, WuxiaElements.METAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "godly_metal")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//       Metal Transformation ones      //
	//////////////////////////////////////////
	public static RegistryObject<TechniqueAspect> METAL_SKIN = ASPECTS.register("metal_skin",
			() -> new ElementSystemConverter(3d, WuxiaElements.METAL.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ore"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_nugget")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_construct"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> METAL_CONSTRUCT = ASPECTS.register("metal_construct",
			() -> new ElementSystemConverter(12d, WuxiaElements.METAL.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_skin"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_ingot")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnSpaceConversionBody(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> SHARPNESS = ASPECTS.register("sharpness",
			() -> new ElementSystemConverter(3d, WuxiaElements.METAL.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ore"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_nugget")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_sharpness"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MAGICAL_SHARPNESS = ASPECTS.register("magical_sharpness",
			() -> new ElementSystemConverter(12d, WuxiaElements.METAL.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "sharpness"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_ingot")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magnetic_splitting"), cultivation);
								tryToLearnSpaceConversionEssence(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> MAGNETIC_SPLITTING = ASPECTS.register("magnetic_splitting",
			() -> new ElementSystemConverter(60d, WuxiaElements.METAL.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_sharpness"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_block")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> MAGNETIZATION = ASPECTS.register("magnetization",
			() -> new ElementSystemConverter(3d, WuxiaElements.METAL.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ore"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_nugget")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "galvanization"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> GALVANIZATION = ASPECTS.register("galvanization",
			() -> new ElementSystemConverter(12d, WuxiaElements.METAL.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magnetization"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "metal_ingot")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnSpaceConversionDivine(cultivation);
							}))
	);

	//////////////////////////////////////////
	//       Lightning Generation ones      //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> SPARK = ASPECTS.register("spark",
			() -> new ConditionalElementalGenerator(1d, WuxiaElements.LIGHTNING.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightningCultivate(event);}}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "circuit"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> CIRCUIT = ASPECTS.register("circuit",
			() -> new ConditionalElementalGenerator(3d, WuxiaElements.LIGHTNING.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightningCultivate(event);}}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "thundering"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> THUNDERING = ASPECTS.register("thundering",
			() -> new ConditionalElementalGenerator(9d, WuxiaElements.LIGHTNING.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightningCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spark")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earthen_thunder"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> EARTHEN_THUNDER = ASPECTS.register("earthen_thunder",
			() -> new ConditionalElementalGenerator(27d, WuxiaElements.LIGHTNING.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightningCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "circuit")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heavenly_lightning"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> HEAVENLY_LIGHTNING = ASPECTS.register("heavenly_lightning",
			() -> new ConditionalElementalGenerator(81d, WuxiaElements.LIGHTNING.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightningCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "thundering")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "immortal_storm"), cultivation);
								tryToLearnTimeGeneration(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> IMMORTAL_STORM = ASPECTS.register("immortal_storm",
			() -> new ConditionalElementalGenerator(243d, WuxiaElements.LIGHTNING.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightningCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earthen_thunder")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "endless_lightning"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ENDLESS_LIGHTNING = ASPECTS.register("endless_lightning",
			() -> new ConditionalElementalGenerator(729d, WuxiaElements.LIGHTNING.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightningCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heavenly_lightning")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "embodiment_of_lightning"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> EMBODIMENT_OF_LIGHTNING = ASPECTS.register("embodiment_of_lightning",
			() -> new ConditionalElementalGenerator(2187d, WuxiaElements.LIGHTNING.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightningCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "immortal_storm")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "god_of_lightning"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> GOD_OF_LIGHTNING = ASPECTS.register("god_of_lightning",
			() -> new ConditionalElementalGenerator(6561d, WuxiaElements.LIGHTNING.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightningCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "endless_lightning")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//       Lightning Conversion ones      //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> CONDUIT = ASPECTS.register("conduit",
			() -> new ElementSystemConverter(3d, WuxiaElements.LIGHTNING.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spark"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "circuit")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "vein_conductor"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> VEIN_CONDUCTOR = ASPECTS.register("vein_conductor",
			() -> new ElementSystemConverter(12d, WuxiaElements.LIGHTNING.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "conduit"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "thundering")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnTimeConversionBody(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> ARC = ASPECTS.register("arc",
			() -> new ElementSystemConverter(3d, WuxiaElements.LIGHTNING.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spark"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "circuit")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_conduction"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MAGICAL_CONDUCTION = ASPECTS.register("magical_conduction",
			() -> new ElementSystemConverter(12d, WuxiaElements.LIGHTNING.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "arc"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "thundering")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "thundering_conduction"), cultivation);
								tryToLearnTimeConversionEssence(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> THUNDERING_CONDUCTION = ASPECTS.register("thundering_conduction",
			() -> new ElementSystemConverter(60d, WuxiaElements.LIGHTNING.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_conduction"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earthen_thunder")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> FLASH = ASPECTS.register("flash",
			() -> new ElementSystemConverter(3d, WuxiaElements.LIGHTNING.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spark"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "circuit")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "jolt"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> JOLT = ASPECTS.register("jolt",
			() -> new ElementSystemConverter(12d, WuxiaElements.LIGHTNING.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "flash"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "thundering")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnTimeConversionDivine(cultivation);
							}))
	);
	

	//////////////////////////////////////////
	//         Wind Generation ones         //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> BREEZE = ASPECTS.register("breeze",
			() -> new ConditionalElementalGenerator(1d, WuxiaElements.WIND.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {windCultivate(event);}}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "gust"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> GUST = ASPECTS.register("gust",
			() -> new ConditionalElementalGenerator(3d, WuxiaElements.WIND.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {windCultivate(event);}}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "winding"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> WINDING = ASPECTS.register("winding",
			() -> new ConditionalElementalGenerator(9d, WuxiaElements.WIND.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {windCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "breeze")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earthen_wind"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> EARTHEN_WIND = ASPECTS.register("earthen_wind",
			() -> new ConditionalElementalGenerator(27d, WuxiaElements.WIND.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {windCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "gust")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heavenly_wind"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> HEAVENLY_WIND = ASPECTS.register("heavenly_wind",
			() -> new ConditionalElementalGenerator(81d, WuxiaElements.WIND.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {windCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "winding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "immortal_gale"), cultivation);
								tryToLearnTimeGeneration(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> IMMORTAL_GALE = ASPECTS.register("immortal_gale",
			() -> new ConditionalElementalGenerator(243d, WuxiaElements.WIND.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {windCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earthen_wind")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "endless_wind"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ENDLESS_WIND = ASPECTS.register("endless_wind",
			() -> new ConditionalElementalGenerator(729d, WuxiaElements.WIND.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {windCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "heavenly_wind")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "embodiment_of_wind"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> EMBODIMENT_OF_WIND = ASPECTS.register("embodiment_of_wind",
			() -> new ConditionalElementalGenerator(2187d, WuxiaElements.WIND.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {windCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "immortal_gale")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "god_of_wind"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> GOD_OF_WIND = ASPECTS.register("god_of_wind",
			() -> new ConditionalElementalGenerator(6561d, WuxiaElements.WIND.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {windCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "endless_wind")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//          Wind Conversion ones        //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> AIRFLOW = ASPECTS.register("airflow",
			() -> new ElementSystemConverter(3d, WuxiaElements.WIND.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "breeze"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "gust")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "wind_channel"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> WIND_CHANNEL = ASPECTS.register("wind_channel",
			() -> new ElementSystemConverter(12d, WuxiaElements.WIND.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "airflow"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "winding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnTimeConversionBody(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> GALE = ASPECTS.register("gale",
			() -> new ElementSystemConverter(3d, WuxiaElements.WIND.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "breeze"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "gust")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_wind"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MAGICAL_WIND = ASPECTS.register("magical_wind",
			() -> new ElementSystemConverter(12d, WuxiaElements.WIND.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "gale"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "winding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "howling_wind"), cultivation);
								tryToLearnTimeConversionEssence(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> HOWLING_WIND = ASPECTS.register("howling_wind",
			() -> new ElementSystemConverter(60d, WuxiaElements.WIND.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_wind"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "earthen_wind")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> DRAFT = ASPECTS.register("draft",
			() -> new ElementSystemConverter(3d, WuxiaElements.WIND.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "breeze"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "gust")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_vortex"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MAGICAL_VORTEX = ASPECTS.register("magical_vortex",
			() -> new ElementSystemConverter(12d, WuxiaElements.WIND.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "draft"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "winding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnTimeConversionDivine(cultivation);
							}))
	);

	//////////////////////////////////////////
	//        Poison Generation ones        //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> VENOM = ASPECTS.register("venom",
			() -> new ConditionalElementalGenerator(1d, WuxiaElements.POISON.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {poisonCultivate(event);}}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "miasma"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MIASMA = ASPECTS.register("miasma",
			() -> new ConditionalElementalGenerator(3d, WuxiaElements.POISON.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {poisonCultivate(event);}}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "toxic_bloom"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TOXIC_BLOOM = ASPECTS.register("toxic_bloom",
			() -> new ConditionalElementalGenerator(9d, WuxiaElements.POISON.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {poisonCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "venom")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "blight"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> BLIGHT = ASPECTS.register("blight",
			() -> new ConditionalElementalGenerator(27d, WuxiaElements.POISON.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {poisonCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "miasma")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "nightshade"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> NIGHTSHADE = ASPECTS.register("nightshade",
			() -> new ConditionalElementalGenerator(81d, WuxiaElements.POISON.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {poisonCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "toxic_bloom")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "immortal_plague"), cultivation);
								tryToLearnTimeGeneration(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> IMMORTAL_PLAGUE = ASPECTS.register("immortal_plague",
			() -> new ConditionalElementalGenerator(243d, WuxiaElements.POISON.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {poisonCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "blight")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "endless_decay"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ENDLESS_DECAY = ASPECTS.register("endless_decay",
			() -> new ConditionalElementalGenerator(729d, WuxiaElements.POISON.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {poisonCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "nightshade")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "embodiment_of_disease"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> EMBODIMENT_OF_DISEASE = ASPECTS.register("embodiment_of_disease",
			() -> new ConditionalElementalGenerator(2187d, WuxiaElements.POISON.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {poisonCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "immortal_plague")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "lord_of_rot"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> LORD_OF_ROT = ASPECTS.register("lord_of_rot",
			() -> new ConditionalElementalGenerator(6561d, WuxiaElements.POISON.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {poisonCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "endless_decay")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//        Poison Conversion ones        //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> CORRUPTION = ASPECTS.register("corruption",
			() -> new ElementSystemConverter(3d, WuxiaElements.POISON.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "venom"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "miasma")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "vein_toxin"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> VEIN_TOXIN = ASPECTS.register("vein_toxin",
			() -> new ElementSystemConverter(12d, WuxiaElements.POISON.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "corruption"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "blight")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnTimeConversionBody(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> MALIGNANCE = ASPECTS.register("malignance",
			() -> new ElementSystemConverter(3d, WuxiaElements.POISON.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "venom"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "miasma")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "arcane_toxin"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ARCANE_TOXIN = ASPECTS.register("arcane_toxin",
			() -> new ElementSystemConverter(12d, WuxiaElements.POISON.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "malignance"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "blight")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "withering_touch"), cultivation);
								tryToLearnTimeConversionEssence(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> WITHERING_TOUCH = ASPECTS.register("withering_touch",
			() -> new ElementSystemConverter(60d, WuxiaElements.POISON.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "arcane_toxin"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "nightshade")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> CORROSION = ASPECTS.register("corrosion",
			() -> new ElementSystemConverter(3d, WuxiaElements.POISON.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "venom"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "miasma")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spiritual_blight"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPIRITUAL_BLIGHT = ASPECTS.register("spiritual_blight",
			() -> new ElementSystemConverter(12d, WuxiaElements.POISON.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "corrosion"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "blight")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnTimeConversionDivine(cultivation);
							}))
	);


	//////////////////////////////////////////
	//       Light Generation ones          //
	//////////////////////////////////////////	
	
	public static RegistryObject<TechniqueAspect> STARRY_BATH = ASPECTS.register("starry_bath",
			() -> new ConditionalElementalGenerator(1d, WuxiaElements.LIGHT.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightCultivate(event);}}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "starlight_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> STARLIGHT_BATH = ASPECTS.register("starlight_bath",
			() -> new ConditionalElementalGenerator(3d, WuxiaElements.LIGHT.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightCultivate(event);}}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "lunar_bath"), cultivation))) 
	);

	public static RegistryObject<TechniqueAspect> LUNAR_BATH = ASPECTS.register("lunar_bath",
			() -> new ConditionalElementalGenerator(9d, WuxiaElements.LIGHT.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "starry_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "astral_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ASTRAL_BATH = ASPECTS.register("astral_bath",
			() -> new ConditionalElementalGenerator(27d, WuxiaElements.LIGHT.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "starlight_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "celestial_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> CELESTIAL_BATH = ASPECTS.register("celestial_bath",
			() -> new ConditionalElementalGenerator(81d, WuxiaElements.LIGHT.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "lunar_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "cosmic_bath"), cultivation);
								tryToLearnTimeGeneration(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> COSMIC_BATH = ASPECTS.register("cosmic_bath",
			() -> new ConditionalElementalGenerator(243d, WuxiaElements.LIGHT.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "astral_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "nebula_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> NEBULA_BATH = ASPECTS.register("nebula_bath",
			() -> new ConditionalElementalGenerator(729d, WuxiaElements.LIGHT.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "celestial_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "galactic_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> GALACTIC_BATH = ASPECTS.register("galactic_bath",
			() -> new ConditionalElementalGenerator(2187d, WuxiaElements.LIGHT.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "cosmic_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "cosmic_ascendant_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> COSMIC_ASCENDANT_BATH = ASPECTS.register("cosmic_ascendant_bath",
			() -> new ConditionalElementalGenerator(6561d, WuxiaElements.LIGHT.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {lightCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "nebula_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//         Light Conversion ones        //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> LUMEN = ASPECTS.register("lumen",
			() -> new ElementSystemConverter(3d, WuxiaElements.LIGHT.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "starry_bath"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "starlight_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "stellar_conducation"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> STELLAR_CONDUCTION = ASPECTS.register("stellar_conducation",
			() -> new ElementSystemConverter(12d, WuxiaElements.LIGHT.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "lumen"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "lunar_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnTimeConversionBody(cultivation);
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "light_kitsune_transformation"), cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> SHINE = ASPECTS.register("shine",
			() -> new ElementSystemConverter(3d, WuxiaElements.LIGHT.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "starry_bath"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "starlight_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "prismatic_radiance"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> PRISMATIC_RADIANCE = ASPECTS.register("prismatic_radiance",
			() -> new ElementSystemConverter(12d, WuxiaElements.LIGHT.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "shine"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "lunar_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "supernova"), cultivation);
								tryToLearnTimeConversionEssence(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> SUPERNOVA = ASPECTS.register("supernova",
			() -> new ElementSystemConverter(60d, WuxiaElements.LIGHT.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "prismatic_radiance"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "astral_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> FLARE = ASPECTS.register("flare",
			() -> new ElementSystemConverter(3d, WuxiaElements.LIGHT.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "starry_bath"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "starlight_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "kaleidoscopic_ray"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> KALEIDOSCOPIC_RAY = ASPECTS.register("kaleidoscopic_ray",
			() -> new ElementSystemConverter(12d, WuxiaElements.LIGHT.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "flare"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "lunar_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnTimeConversionDivine(cultivation);
							}))
	);

	//////////////////////////////////////////
	//  	   Dark Generation ones         //
	//////////////////////////////////////////
	
	public static RegistryObject<TechniqueAspect> SHADOW_BATH = ASPECTS.register("shadow_bath",
			() -> new ConditionalElementalGenerator(1d, WuxiaElements.DARK.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {darkCultivate(event);}}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dusk_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> DUSK_BATH = ASPECTS.register("dusk_bath",
			() -> new ConditionalElementalGenerator(3d, WuxiaElements.DARK.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {darkCultivate(event);}}.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "midnight_bath"), cultivation))) 
	);

	public static RegistryObject<TechniqueAspect> MIDNIGHT_BATH = ASPECTS.register("midnight_bath",
			() -> new ConditionalElementalGenerator(9d, WuxiaElements.DARK.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {darkCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "shadow_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "moonshadow_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MOONSHADOW_BATH = ASPECTS.register("moonshadow_bath",
			() -> new ConditionalElementalGenerator(27d, WuxiaElements.DARK.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {darkCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dusk_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "eclipse_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ECLIPSE_BATH = ASPECTS.register("eclipse_bath",
			() -> new ConditionalElementalGenerator(81d, WuxiaElements.DARK.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {darkCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "midnight_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "void_bath"), cultivation);
								tryToLearnTimeGeneration(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> VOID_BATH = ASPECTS.register("void_bath",
			() -> new ConditionalElementalGenerator(243d, WuxiaElements.DARK.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {darkCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "moonshadow_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "abyssal_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ABYSSAL_BATH = ASPECTS.register("abyssal_bath",
			() -> new ConditionalElementalGenerator(729d, WuxiaElements.DARK.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {darkCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "eclipse_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "stygian_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> STYGIAN_BATH = ASPECTS.register("stygian_bath",
			() -> new ConditionalElementalGenerator(2187d, WuxiaElements.DARK.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {darkCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "void_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "primordial_shadow_bath"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> PRIMORDIAL_SHADOW_BATH = ASPECTS.register("primordial_shadow_bath",
			() -> new ConditionalElementalGenerator(6561d, WuxiaElements.DARK.getId()) {
					@Override public void onCultivate(CultivatingEvent event) {darkCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "abyssal_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//         Dark Conversion ones         //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> DIM = ASPECTS.register("dim",
			() -> new ElementSystemConverter(3d, WuxiaElements.DARK.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "shadow_bath"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dusk_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "twilight_cloak"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TWILIGHT_CLOAK = ASPECTS.register("twilight_cloak",
			() -> new ElementSystemConverter(12d, WuxiaElements.DARK.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dim"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "midnight_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnTimeConversionBody(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> GLOOM = ASPECTS.register("gloom",
			() -> new ElementSystemConverter(3d, WuxiaElements.DARK.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "shadow_bath"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dusk_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "shade"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SHADE = ASPECTS.register("shade",
			() -> new ElementSystemConverter(12d, WuxiaElements.DARK.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "gloom"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "midnight_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> {
								cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "umbral_visage"), cultivation);
								tryToLearnTimeConversionEssence(cultivation);
							}))
	);

	public static RegistryObject<TechniqueAspect> UMBRAL_VISAGE = ASPECTS.register("umbral_visage",
			() -> new ElementSystemConverter(60d, WuxiaElements.DARK.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "shade"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "moonshadow_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> ECLIPSE = ASPECTS.register("eclipse",
			() -> new ElementSystemConverter(3d, WuxiaElements.DARK.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "shadow_bath"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "dusk_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "penumbra"), cultivation)))
	);
	
	public static RegistryObject<TechniqueAspect> PENUMBRA = ASPECTS.register("penumbra",
			() -> new ElementSystemConverter(12d, WuxiaElements.DARK.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "eclipse"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "midnight_bath")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> { 
								tryToLearnTimeConversionDivine(cultivation);
							}))
	);
 
	//////////////////////////////////////////
	//       Light Special ones             //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> LEAF = ASPECTS.register("leaf",
			() -> new ElementToElementConverter(3d, 0.7d, WuxiaElements.LIGHT.getId(), WuxiaElements.WOOD.getId())
	);

	//////////////////////////////////////////
	//       Space Generation ones          //
	//////////////////////////////////////////
	public static RegistryObject<TechniqueAspect> SPACE_DETECTION = ASPECTS.register("space_detection",
			() -> new ElementalGenerator(1.7d, WuxiaElements.SPACE.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_absorption"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPACE_ABSORPTION = ASPECTS.register("space_absorption",
			() -> new ElementalGenerator(5.1d, WuxiaElements.SPACE.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_folding"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPACE_FOLDING = ASPECTS.register("space_folding",
			() -> new ElementalGenerator(15.3d, WuxiaElements.SPACE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_detection")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_generation"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPACE_GENERATION = ASPECTS.register("space_generation",
			() -> new ElementalGenerator(45.9d, WuxiaElements.SPACE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_absorption")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_expansion"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPACE_EXPANSION = ASPECTS.register("space_expansion",
			() -> new ElementalGenerator(137.7d, WuxiaElements.SPACE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_folding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_creation"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPACE_CREATION = ASPECTS.register("space_creation",
			() -> new ElementalGenerator(413.1d, WuxiaElements.SPACE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_generation")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_domination"), cultivation)))
	);

		public static RegistryObject<TechniqueAspect> SPACE_DOMINATION = ASPECTS.register("space_domination",
			() -> new ElementalGenerator(729d, WuxiaElements.SPACE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_expansion")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_embodiment"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPACE_EMBODIMENT = ASPECTS.register("space_embodiment",
			() -> new ElementalGenerator(2187d, WuxiaElements.SPACE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_creation")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_apotheosis"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPACE_APOTHEOSIS = ASPECTS.register("space_apotheosis",
			() -> new ElementalGenerator(6561d, WuxiaElements.SPACE.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_domination")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);


	//////////////////////////////////////////
	//       Space Conversion ones          //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> SPATIAL_TEMPERING = ASPECTS.register("spatial_tempering",
			() -> new ElementSystemConverter(5d, WuxiaElements.SPACE.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_detection"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_absorption")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_body"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPATIAL_BODY = ASPECTS.register("spatial_body",
			() -> new ElementSystemConverter(20d, WuxiaElements.SPACE.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_tempering"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_folding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_kitsune_transformation"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPACE_TEAR = ASPECTS.register("space_tear",
			() -> new ElementSystemConverter(5d, WuxiaElements.SPACE.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_detection"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_absorption")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_vortex"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPATIAL_VORTEX = ASPECTS.register("spatial_vortex",
			() -> new ElementSystemConverter(20d, WuxiaElements.SPACE.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_tear"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_folding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_tribulation"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPATIAL_TRIBULATION = ASPECTS.register("spatial_tribulation",
			() -> new ElementSystemConverter(100d, WuxiaElements.SPACE.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_vortex"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_generation")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> SPATIAL_AMPLIFICATION = ASPECTS.register("spatial_amplification",
			() -> new ElementSystemConverter(5d, WuxiaElements.SPACE.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_detection"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_absorption")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_compression"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPATIAL_COMPRESSION = ASPECTS.register("spatial_compression",
			() -> new ElementSystemConverter(20d, WuxiaElements.SPACE.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_amplification"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "space_folding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1")))
	);

	//////////////////////////////////////////
	//  	   Time Generation ones         //
	//////////////////////////////////////////
	public static RegistryObject<TechniqueAspect> TIME_DETECTION = ASPECTS.register("time_detection",
			() -> new ElementalGenerator(1.7d, WuxiaElements.TIME.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_absorption"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TIME_ABSORPTION = ASPECTS.register("time_absorption",
			() -> new ElementalGenerator(5.1d, WuxiaElements.TIME.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_folding"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TIME_FOLDING = ASPECTS.register("time_folding",
			() -> new ElementalGenerator(15.3d, WuxiaElements.TIME.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_detection")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_generation"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TIME_GENERATION = ASPECTS.register("time_generation",
			() -> new ElementalGenerator(45.9d, WuxiaElements.TIME.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_absorption")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_expansion"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TIME_EXPANSION = ASPECTS.register("time_expansion",
			() -> new ElementalGenerator(137.7d, WuxiaElements.TIME.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_folding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_creation"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TIME_CREATION = ASPECTS.register("time_creation",
			() -> new ElementalGenerator(413.1d, WuxiaElements.TIME.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_generation")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_domination"), cultivation)))
	);

		public static RegistryObject<TechniqueAspect> TIME_DOMINATION = ASPECTS.register("time_domination",
			() -> new ElementalGenerator(729d, WuxiaElements.TIME.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_expansion")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_embodiment"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TIME_EMBODIMENT = ASPECTS.register("time_embodiment",
			() -> new ElementalGenerator(2187d, WuxiaElements.TIME.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_creation")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_apotheosis"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TIME_APOTHEOSIS = ASPECTS.register("time_apotheosis",
			() -> new ElementalGenerator(6561d, WuxiaElements.TIME.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_domination")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);


	//////////////////////////////////////////
	//        Time Conversion ones          //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> TEMPORAL_TEMPERING = ASPECTS.register("temporal_tempering",
			() -> new ElementSystemConverter(5d, WuxiaElements.TIME.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_detection"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_absorption")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "temporal_body"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TEMPORAL_BODY = ASPECTS.register("temporal_body",
			() -> new ElementSystemConverter(20d, WuxiaElements.TIME.getId(), System.BODY)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "temporal_tempering"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_folding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> TIME_TEAR = ASPECTS.register("time_tear",
			() -> new ElementSystemConverter(5d, WuxiaElements.TIME.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_detection"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_absorption")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "temporal_vortex"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TEMPORAL_VORTEX = ASPECTS.register("temporal_vortex",
			() -> new ElementSystemConverter(20d, WuxiaElements.TIME.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_tear"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_folding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "temporal_tribulation"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TEMPORAL_TRIBULATION = ASPECTS.register("temporal_tribulation",
			() -> new ElementSystemConverter(100d, WuxiaElements.TIME.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "temporal_vortex"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_generation")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> TEMPORAL_AMPLIFICATION = ASPECTS.register("temporal_amplification",
			() -> new ElementSystemConverter(5d, WuxiaElements.TIME.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_detection"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_absorption")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "temporal_compression"), cultivation)))
	);

	
	public static RegistryObject<TechniqueAspect> TEMPORAL_COMPRESSION = ASPECTS.register("temporal_compression",
			() -> new ElementSystemConverter(20d, WuxiaElements.TIME.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "temporal_amplification"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "time_folding")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1")))
	);


	//////////////////////////////////////////
	//        Rebirth Generation ones       //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> ASHES_OF_REBIRTH = ASPECTS.register("ashes_of_rebirth",
			() -> new ElementalGenerator(2d, WuxiaElements.REBIRTH.getId())
					.setCanLearn(cultivation -> cultivation.getRebirths() > 0)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "rekindled_spark"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> REKINDLED_SPARK = ASPECTS.register("rekindled_spark",
			() -> new ElementalGenerator(6d, WuxiaElements.REBIRTH.getId())
					.setCanLearn(cultivation -> cultivation.getRebirths() > 0)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ignition"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> IGNITION = ASPECTS.register("ignition",
			() -> new ElementalGenerator(18d, WuxiaElements.REBIRTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ashes_of_rebirth"))
							&& cultivation.getRebirths() > 0)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "inferno"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> INFERNO = ASPECTS.register("inferno",
			() -> new ElementalGenerator(54, WuxiaElements.REBIRTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "rekindled_spark"))
							&& cultivation.getRebirths() > 1)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "renewal"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> RENEWAL = ASPECTS.register("renewal",
			() -> new ElementalGenerator(162d, WuxiaElements.REBIRTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ignition"))
							&& cultivation.getRebirths() > 1)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "resurrection"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> RESURRECTION = ASPECTS.register("resurrection",
			() -> new ElementalGenerator(483d, WuxiaElements.REBIRTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "inferno"))
							&& cultivation.getRebirths() > 1)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ascent"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ASCENT = ASPECTS.register("ascent",
			() -> new ElementalGenerator(1458d, WuxiaElements.REBIRTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "renewal"))
							&& cultivation.getRebirths() > 2)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("729000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("1822500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("3645000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("7290000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "transcendence"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> TRANSCENDENCE = ASPECTS.register("transcendence",
			() -> new ElementalGenerator(2187d, WuxiaElements.REBIRTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "resurrection"))
							&& cultivation.getRebirths() > 2)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "the_eternal_cycle"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> THE_ETERNAL_CYCLE = ASPECTS.register("the_eternal_cycle",
			() -> new ElementalGenerator(4374d, WuxiaElements.REBIRTH.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ascent"))
							&& cultivation.getRebirths() > 2)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("6561000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("16402500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("32805000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("65610000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//      Rebirth Transformation ones     //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> EMBER_OF_REKINDLING = ASPECTS.register("ember_of_rekindling",
			() -> new ElementSystemConverter(6d, WuxiaElements.REBIRTH.getId(), System.BODY)
					.setCanLearn(cultivation -> (cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ashes_of_rebirth"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "rekindled_spark")))
							&& cultivation.getRebirths() > 0)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "flame_of_purification"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> FLAME_OF_PURIFICATION = ASPECTS.register("flame_of_purification",
			() -> new ElementSystemConverter(24d, WuxiaElements.REBIRTH.getId(), System.BODY)
					.setCanLearn(cultivation -> (cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ember_of_rekindling"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ignition")))
							&& cultivation.getRebirths() > 1)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> CINDER_OF_RENEWAL = ASPECTS.register("cinder_of_renewal",
			() -> new ElementSystemConverter(6d, WuxiaElements.REBIRTH.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> (cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ashes_of_rebirth"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "rekindled_spark")))
							&& cultivation.getRebirths() > 0)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spark_of_awakening"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPARK_OF_AWAKENING = ASPECTS.register("spark_of_awakening",
			() -> new ElementSystemConverter(24d, WuxiaElements.REBIRTH.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "cinder_of_renewal"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ignition"))
							&& cultivation.getRebirths() > 1)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "essence_combustion"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> ESSENCE_OF_REBIRTH = ASPECTS.register("essence_of_rebirth",
			() -> new ElementSystemConverter(120d, WuxiaElements.REBIRTH.getId(), System.ESSENCE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spark_of_awakening"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "inferno"))
							&& cultivation.getRebirths() > 2)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5000000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("35000000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> SPIRITUAL_RECONSTRUCTION = ASPECTS.register("spiritual_reconstruction",
			() -> new ElementSystemConverter(6d, WuxiaElements.REBIRTH.getId(), System.DIVINE)
					.setCanLearn(cultivation -> (cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ashes_of_rebirth"))
							|| cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "rekindled_spark")))
							&& cultivation.getRebirths() > 0)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spirit_of_restoration"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SPIRIT_OF_RESTORATION = ASPECTS.register("spirit_of_restoration",
			() -> new ElementSystemConverter(24d, WuxiaElements.REBIRTH.getId(), System.DIVINE)
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spiritual_reconstruction"))
							&& cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "ignition"))
							&& cultivation.getRebirths() > 1)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("100000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("500000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1500000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("3500000"), new BigDecimal("1.1")))
	);

	//////////////////////////////////////////
	//       Neutral Generation ones        //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> QI_STRAND = ASPECTS.register("qi_strand",
			() -> new ElementalGenerator(1d, WuxiaElements.PHYSICAL.getId())
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "qi_manifestation"), cultivation)))
	);
	
	public static RegistryObject<TechniqueAspect> QI_MANIFESTATION = ASPECTS.register("qi_manifestation",
			() -> new ElementalGenerator(3d, WuxiaElements.PHYSICAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "qi_strand")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "qi_whirlpool"), cultivation)))
	);
	
	public static RegistryObject<TechniqueAspect> QI_WHIRLPOOL = ASPECTS.register("qi_whirlpool",
			() -> new ElementalGenerator(9d, WuxiaElements.PHYSICAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
						.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "qi_manifestation")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "qi_pond"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> QI_POND = ASPECTS.register("qi_pond",
			() -> new ElementalGenerator(27d, WuxiaElements.PHYSICAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "qi_whirlpool")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "physical_essence"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> PHYSICAL_ESSENCE = ASPECTS.register("physical_essence",
			() -> new ElementalGenerator(81D, WuxiaElements.PHYSICAL.getId())
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "qi_pond")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("81000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("202500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("405000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("810000"), new BigDecimal("0.6")))
	);
	
	public static RegistryObject<TechniqueAspect> QI_TEMPERING = ASPECTS.register("qi_tempering",
			() -> new ElementSystemConverter(3d, WuxiaElements.PHYSICAL.getId(), System.BODY)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1")))
	);
	
	public static RegistryObject<TechniqueAspect> QI_ENLIGHTENMENT = ASPECTS.register("qi_enlightenment",
			() -> new ElementSystemConverter(3d, WuxiaElements.PHYSICAL.getId(), System.DIVINE)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1")))
	);
	
	public static RegistryObject<TechniqueAspect> QI_FLOW = ASPECTS.register("qi_flow",
			() -> new ElementSystemConverter(3d, WuxiaElements.PHYSICAL.getId(), System.ESSENCE)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("10000"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("50000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("150000"), new BigDecimal("0.6")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("350000"), new BigDecimal("1.1")))
	);

	public static RegistryObject<TechniqueAspect> BLOOD_BURNING = ASPECTS.register("blood_burning",
			() -> new ConditionalElementalGenerator(2d, WuxiaElements.PHYSICAL.getId()) {
				@Override
				public void onCultivate(CultivatingEvent event) {
				var player = event.getPlayer();
				ICultivation cultivation =Cultivation.get(event.getPlayer());
				var systemData = cultivation.getSystemData(event.getSystem());
				var techniqueData = systemData.techniqueData;
				var grid = techniqueData.grid;
				var modifier = BigDecimal.ONE;
				for (var aspect : grid.getGrid().values()) {
					if (aspect.equals(WuxiaTechniqueAspects.BLOOD_BURNING.getId())) {
						modifier = modifier.multiply(new BigDecimal(1.5));
					}
				}
				if (modifier.compareTo(new BigDecimal(250000)) > 0) modifier = new BigDecimal(250000);
				var damageType = player.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(WuxiaDamageTypes.BLOOD_BURNING_SKILL);
				player.hurt(new WuxiaDamageSource(damageType, WuxiaElements.PHYSICAL.get(), player, modifier).setInstantDeath(), modifier.floatValue());
				if (player.isAlive()) { 
					event.setAmount(event.getAmount().add(modifier));
					event.addElement(WuxiaElements.PHYSICAL.getId(), BigDecimal.ONE);
					}
				}
			}
	);

	public static RegistryObject<TechniqueAspect> DEVOURING = ASPECTS.register("devouring",
			() -> new ConditionalElementalGenerator(1d, WuxiaElements.DEMONIC.getId()) {
				@Override public void onCultivate(CultivatingEvent event) { devourCultivate(event);}}
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.05")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.15")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.25"),
			cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "consumption"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> CONSUMPTION = ASPECTS.register("consumption",
			() -> new ConditionalElementalGenerator(2d, WuxiaElements.DEMONIC.getId()) {
				@Override public void onCultivate(CultivatingEvent event) { devourCultivate(event);}}
					.setCanLearn(cultivation -> cultivation.getAspects()
						.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "devouring")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("27000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("67500"), new BigDecimal("0.05")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("135000"), new BigDecimal("0.15")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("270000"), new BigDecimal("0.25"),
			cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "gluttony"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> GLUTTONY = ASPECTS.register("gluttony",
			() -> new ConditionalElementalGenerator(3d, WuxiaElements.DEMONIC.getId()) {
				@Override public void onCultivate(CultivatingEvent event) { devourCultivate(event);}}
				.setCanLearn(cultivation -> cultivation.getAspects()
						.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "consumption")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("243000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("607500"), new BigDecimal("0.05")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("1215000"), new BigDecimal("0.15")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("2430000"), new BigDecimal("0.25"),
			cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "beelzebub"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> BEELZEBUB = ASPECTS.register("beelzebub",
			() -> new ConditionalElementalGenerator(4d, WuxiaElements.DEMONIC.getId()) {
				@Override public void onCultivate(CultivatingEvent event) { devourCultivate(event);}}
				.setCanLearn(cultivation -> cultivation.getAspects()
						.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "gluttony")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("2187000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("5647500"), new BigDecimal("0.05")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("10935000"), new BigDecimal("0.15")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("21870000"), new BigDecimal("0.25")))
	);

	//////////////////////////////////////////
	//       Weapons Generation ones        //
	//////////////////////////////////////////

	public static RegistryObject<TechniqueAspect> BASIC_SWORD_SET = ASPECTS.register("basic_sword_set",
			() -> new WeaponElementalGenerator(0.75d, WuxiaElements.PHYSICAL.getId(), WeaponElementalGenerator.WeaponType.SWORD)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("1000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("2500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("5000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("10000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "sword_qi_gathering"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> SWORD_QI_GATHERING = ASPECTS.register("sword_qi_gathering",
			() -> new WeaponElementalGenerator(2.25d, WuxiaElements.PHYSICAL.getId(), WeaponElementalGenerator.WeaponType.SWORD)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("3000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("7500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("15000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("30000"), new BigDecimal("0.6"),
							cultivation -> cultivation.getAspects().learnAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "magical_sword_glint"), cultivation)))
	);

	public static RegistryObject<TechniqueAspect> MAGICAL_SWORD_GLINT = ASPECTS.register("magical_sword_glint",
			() -> new WeaponElementalGenerator(6.75d, WuxiaElements.PHYSICAL.getId(), WeaponElementalGenerator.WeaponType.SWORD)
					.addCheckpoint(new TechniqueAspect.Checkpoint("basic", new BigDecimal("9000")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("intermediate", new BigDecimal("22500"), new BigDecimal("0.2")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("advanced", new BigDecimal("45000"), new BigDecimal("0.4")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("expert", new BigDecimal("90000"), new BigDecimal("0.6")))
	);

	//////////////////////////////////////////
	//       Body Transformation Ones       //
	//////////////////////////////////////////
	public static RegistryObject<TechniqueAspect> KITSUNE_TRANSFORMATION = ASPECTS.register("kitsune_transformation",
			() -> new BodyTransformationAspect(WuxiaElements.PHYSICAL.getId(), 3)
					.<ElementToStatsConsumer>setStat(PlayerStat.STRENGTH, new BigDecimal("0.1"))
					.<ElementToStatsConsumer>setStat(PlayerStat.AGILITY, new BigDecimal("0.4"))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_one_tail", new BigDecimal("3000"), WuxiaEntities.KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.1")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_two_tails", new BigDecimal("9000"), WuxiaEntities.KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.3")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_three_tails", new BigDecimal("15000"), WuxiaEntities.KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.6")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_four_tails", new BigDecimal("21000"), WuxiaEntities.KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.9")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_five_tails", new BigDecimal("30000"), WuxiaEntities.KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.2")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_six_tails", new BigDecimal("42000"), WuxiaEntities.KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.5")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_seven_tails", new BigDecimal("60000"), WuxiaEntities.KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.8")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_eight_tails", new BigDecimal("90000"), WuxiaEntities.KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("2.1")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_nine_tails", new BigDecimal("150000"), WuxiaEntities.KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("2.7"),
							cultivation -> cultivation.getAspects().learnAspectWithProficiency(new ResourceLocation(WuxiaCraft.MOD_ID, "light_kitsune_transformation"), cultivation, new BigDecimal("150000"))))
	);

	public static RegistryObject<TechniqueAspect> LIGHT_KITSUNE_TRANSFORMATION = ASPECTS.register("light_kitsune_transformation",
			() -> new BodyTransformationAspect(WuxiaElements.LIGHT.getId(), 9)
					.<ElementToStatsConsumer>setStat(PlayerStat.STRENGTH, new BigDecimal("0.6"))
					.<ElementToStatsConsumer>setStat(PlayerStat.AGILITY, new BigDecimal("1.8"))
					.<ElementToStatsConsumer>setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.2"))
					.setCanLearn(cultivation -> cultivation.getAspects()
						.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "stellar_conducation")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_one_tail", new BigDecimal("30000"), WuxiaEntities.LIGHT_KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.1")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_two_tails", new BigDecimal("90000"), WuxiaEntities.LIGHT_KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.3")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_three_tails", new BigDecimal("150000"), WuxiaEntities.LIGHT_KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.6")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_four_tails", new BigDecimal("210000"), WuxiaEntities.LIGHT_KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.9")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_five_tails", new BigDecimal("300000"), WuxiaEntities.LIGHT_KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.2")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_six_tails", new BigDecimal("420000"), WuxiaEntities.LIGHT_KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.5")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_seven_tails", new BigDecimal("600000"), WuxiaEntities.LIGHT_KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.8")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_eight_tails", new BigDecimal("900000"), WuxiaEntities.LIGHT_KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("2.1")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_nine_tails", new BigDecimal("1500000"), WuxiaEntities.LIGHT_KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("2.7"),
							cultivation -> cultivation.getAspects().learnAspectWithProficiency(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_kitsune_transformation"), cultivation, new BigDecimal("1500000"))))
	);

	public static RegistryObject<TechniqueAspect> SPATIAL_KITSUNE_TRANSFORMATION = ASPECTS.register("spatial_kitsune_transformation",
			() -> new BodyTransformationAspect(WuxiaElements.SPACE.getId(), 27)
					.<ElementToStatsConsumer>setStat(PlayerStat.STRENGTH, new BigDecimal("1"))
					.<ElementToStatsConsumer>setStat(PlayerStat.AGILITY, new BigDecimal("3"))
					.<ElementToStatsConsumer>setStat(PlayerStat.DETECTION_STRENGTH, new BigDecimal("0.35"))
					.<ElementToStatsConsumer>setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.05"))
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_body")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_one_tail", new BigDecimal("300000"), WuxiaEntities.SPATIAL_KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.1")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_two_tails", new BigDecimal("900000"), WuxiaEntities.SPATIAL_KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.3")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_three_tails", new BigDecimal("1500000"), WuxiaEntities.SPATIAL_KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.6")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_four_tails", new BigDecimal("2100000"), WuxiaEntities.SPATIAL_KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.9")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_five_tails", new BigDecimal("3000000"), WuxiaEntities.SPATIAL_KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.2")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_six_tails", new BigDecimal("4200000"), WuxiaEntities.SPATIAL_KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.5")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_seven_tails", new BigDecimal("6000000"), WuxiaEntities.SPATIAL_KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.8")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_eight_tails", new BigDecimal("9000000"), WuxiaEntities.SPATIAL_KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("2.1")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("kitsune_nine_tails", new BigDecimal("15000000"), WuxiaEntities.SPATIAL_KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("2.7")))
	);

	public static RegistryObject<TechniqueAspect> DRAGON_TRANSFORMATION = ASPECTS.register("dragon_transformation",
			() -> new BodyTransformationAspect(WuxiaElements.PHYSICAL.getId(), 5)
					.<ElementToStatsConsumer>setStat(PlayerStat.STRENGTH, new BigDecimal("0.75"))
					.<ElementToStatsConsumer>setStat(PlayerStat.MAX_HEALTH, new BigDecimal("0.35"))
					.<ElementToStatsConsumer>setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.1"))
					.addCheckpoint(new TechniqueAspect.Checkpoint("internal_dragon_transformation", new BigDecimal("3000"), new BigDecimal("0.1")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("dragon_transformation", new BigDecimal("15000"), WuxiaEntities.HALF_DRAGON_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.2")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("dragon_resemblance", new BigDecimal("60000"), WuxiaEntities.HALF_DRAGON_BODY_ARMED_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.8")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("draconian_presence", new BigDecimal("240000"), WuxiaEntities.HALF_DRAGON_BODY_HORNED_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.6")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("full_dragon_transformation", new BigDecimal("2000000"), WuxiaEntities.HALF_DRAGON_BODY_HORNED_TRANSFORMATION_ENTITY.getId(), new BigDecimal("3.2"),
							cultivation -> cultivation.getAspects().learnAspectWithProficiency(new ResourceLocation(WuxiaCraft.MOD_ID, "azure_dragon_transformation"), cultivation, new BigDecimal("2000000"))))
	);

	public static RegistryObject<TechniqueAspect> AZURE_DRAGON_TRANSFORMATION = ASPECTS.register("azure_dragon_transformation",
			() -> new BodyTransformationAspect(WuxiaElements.WATER.getId(), 15)
					.<ElementToStatsConsumer>setStat(PlayerStat.STRENGTH, new BigDecimal("1.5"))
					.<ElementToStatsConsumer>setStat(PlayerStat.MAX_HEALTH, new BigDecimal("0.75"))
					.<ElementToStatsConsumer>setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.2"))
					.<ElementToStatsConsumer>setStat(PlayerStat.MAX_BARRIER, new BigDecimal("0.4"))
					.setCanLearn(cultivation -> cultivation.getAspects()
							.knowsAspect(new ResourceLocation(WuxiaCraft.MOD_ID, "inner_stream")))
					.addCheckpoint(new TechniqueAspect.Checkpoint("internal_dragon_transformation", new BigDecimal("300000"), new BigDecimal("0.1")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("dragon_transformation", new BigDecimal("1500000"), WuxiaEntities.AZURE_HALF_DRAGON_BODY_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.2")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("dragon_resemblance", new BigDecimal("6000000"), WuxiaEntities.AZURE_HALF_DRAGON_BODY_ARMED_TRANSFORMATION_ENTITY.getId(), new BigDecimal("0.8")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("draconian_presence", new BigDecimal("24000000"), WuxiaEntities.AZURE_HALF_DRAGON_BODY_HORNED_TRANSFORMATION_ENTITY.getId(), new BigDecimal("1.6")))
					.addCheckpoint(new BodyTransformationAspect.TransformationCheckpoint("full_dragon_transformation", new BigDecimal("200000000"), WuxiaEntities.AZURE_HALF_DRAGON_BODY_HORNED_TRANSFORMATION_ENTITY.getId(), new BigDecimal("3.2")))
	);

	//////////////////////////////////
	// HELPER METHODS               //
	//////////////////////////////////

	private static void tryToLearnSpaceGeneration(ICultivation cultivation) {
		boolean knowsFire = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "everlasting_flame"));
		boolean knowsEarth = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "earth_crystal"));
		boolean knowsMetal = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "heavenly_metal"));
		boolean knowsWater = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "ocean_tide"));
		boolean knowsWood = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "sapling"));
		if (knowsFire && knowsEarth && knowsMetal && knowsWater && knowsWood) {
			cultivation.getAspects().learnAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "space_detection"), cultivation);
		}
	}

	private static void tryToLearnSpaceConversionEssence(ICultivation cultivation) {
		boolean knowsFire = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "magical_incineration"));
		boolean knowsEarth = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "magical_tremor"));
		boolean knowsMetal = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "magical_sharpness"));
		boolean knowsWater = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "magical_flow"));
		boolean knowsWood = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "magical_growth"));
		if (knowsFire && knowsEarth && knowsMetal && knowsWater && knowsWood) {
			cultivation.getAspects().learnAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "space_tear"), cultivation);
		}
	}

	private static void tryToLearnSpaceConversionBody(ICultivation cultivation) {
		boolean knowsFire = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "fiery_skin"));
		boolean knowsEarth = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "earthen_construct"));
		boolean knowsMetal = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "metal_construct"));
		boolean knowsWater = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "inner_stream"));
		boolean knowsWood = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "treant"));
		if (knowsFire && knowsEarth && knowsMetal && knowsWater && knowsWood) {
			cultivation.getAspects().learnAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "spatial_tempering"), cultivation);
		}
	}

	private static void tryToLearnSpaceConversionDivine(ICultivation cultivation) {
		boolean knowsFire = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "soul_flame"));
		boolean knowsEarth = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "serenity"));
		boolean knowsMetal = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "galvanization"));
		boolean knowsWater = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "crashing_waves"));
		boolean knowsWood = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "flowing_foliage"));
		if (knowsFire && knowsEarth && knowsMetal && knowsWater && knowsWood) {
			cultivation.getAspects().learnAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "spatial_amplification"), cultivation);
		}
	}
	
	private static void tryToLearnTimeGeneration(ICultivation cultivation) {
		boolean knowsLightning = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "immortal_storm"));
		boolean knowsWind = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "immortal_gale"));
		boolean knowsPoison = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "immortal_plague"));
		boolean knowsLight = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "cosmic_bath"));
		boolean knowsDark = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "void_bath"));
		if (knowsLightning && knowsWind && knowsPoison && knowsLight && knowsDark) {
			cultivation.getAspects().learnAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "time_detection"), cultivation);
		}
	}
	private static void tryToLearnTimeConversionEssence(ICultivation cultivation) {
		boolean knowsLightning = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "magical_conduction"));
		boolean knowsWind = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "magical_wind"));
		boolean knowsPoison = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "arcane_toxin"));
		boolean knowsLight = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "prismatic_radiance"));
		boolean knowsDark = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "shade"));
		if (knowsLightning && knowsWind && knowsPoison && knowsLight && knowsDark) {
			cultivation.getAspects().learnAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "time_tear"), cultivation);
		}
	}

	private static void tryToLearnTimeConversionBody(ICultivation cultivation) {
		boolean knowsLightning = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "vein_conductor"));
		boolean knowsWind = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "wind_channel"));
		boolean knowsPoison = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "vein_toxin"));
		boolean knowsLight = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "stellar_conducation"));
		boolean knowsDark = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "twilight_cloak"));
		if (knowsLightning && knowsWind && knowsPoison && knowsLight && knowsDark) {
			cultivation.getAspects().learnAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "temporal_tempering"), cultivation);
		}
	}

	private static void tryToLearnTimeConversionDivine(ICultivation cultivation) {
		boolean knowsLightning = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "jolt"));
		boolean knowsWind = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "magical_vortex"));
		boolean knowsPoison = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "spiritual_blight"));
		boolean knowsLight = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "kaleidoscopic_ray"));
		boolean knowsDark = cultivation.getAspects().knowsAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "penumbra"));
		if (knowsLightning && knowsWind && knowsPoison && knowsLight && knowsDark) {
			cultivation.getAspects().learnAspect(ResourceLocation.tryBuild(WuxiaCraft.MOD_ID, "temporal_amplification"), cultivation);
		}
	}

	private static void lightningCultivate(CultivatingEvent event) {
		var level = event.getPlayer().level();
		var techniqueData = Cultivation.get(event.getPlayer()).getSystemData(event.getSystem()).techniqueData;
		var grid = techniqueData.grid;
		int lightingCount = 0;
		for (var aspect : grid.getGrid().values()) {
			var aspect2 = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspect);
			if (aspect2 instanceof ConditionalElementalGenerator gen && gen.element == WuxiaElements.LIGHTNING.getId()) {
				lightingCount++;
			}
		}
		var amount = event.getAmount();
		var modifier = BigDecimal.ONE;
		if (level.isRaining() || level.isThundering()) {
			if (level.isRainingAt(event.getPlayer().blockPosition())) {
				// modifier = modifier +  lightingCount * 0.02
				modifier = modifier.add(new BigDecimal(lightingCount).multiply(new BigDecimal(0.01)));
			}
			// modifier = modifier +  lightingCount * 0.01
			modifier = modifier.add(new BigDecimal(lightingCount).multiply(new BigDecimal(0.01)));
		} else {
			// modifier = modifier +  lightingCount * -0.01
			modifier = modifier.add(new BigDecimal(lightingCount).multiply(new BigDecimal(-0.01)));
		}
		if (modifier.compareTo(BigDecimal.ZERO) < 0) modifier = BigDecimal.ZERO;
		if (modifier.compareTo(new BigDecimal(2)) > 0) modifier = new BigDecimal(2);
		event.addElement(WuxiaElements.LIGHTNING.getId(), modifier);
	}

	private static void windCultivate(CultivatingEvent event) {
		var y = event.getPlayer().blockPosition().getY();
		var techniqueData = Cultivation.get(event.getPlayer()).getSystemData(event.getSystem()).techniqueData;
		var grid = techniqueData.grid;
		int windCount = 0;
		for (var aspect : grid.getGrid().values()) {
			var aspect2 = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspect);
			if (aspect2 instanceof ConditionalElementalGenerator gen && gen.element == WuxiaElements.WIND.getId()) {
				windCount++;
			}
		}
		var amount = event.getAmount();
		var modifier = BigDecimal.ONE;
		if (y > 255) {
			// modifier = modifier +  windCount * 0.02
			modifier = modifier.add(new BigDecimal(windCount).multiply(new BigDecimal(0.02)));
		} else 
		if (y > 127) {
			// modifier = modifier +  windCount * 0.02
			modifier = modifier.add(new BigDecimal(windCount).multiply(new BigDecimal(0.01)));
		} else 
		if (y > 63) {
			// modifier = modifier + windCount * 0.01
			modifier = modifier.add(new BigDecimal(windCount).multiply(new BigDecimal(0.005)));
		} else {
			// modifier = modifier + windCount * -0.01
			modifier = modifier.add(new BigDecimal(windCount).multiply(new BigDecimal(-0.01)));
		}
		if (modifier.compareTo(BigDecimal.ZERO) < 0) modifier = BigDecimal.ZERO;
		if (modifier.compareTo(new BigDecimal(2)) > 0) modifier = new BigDecimal(2);
		event.addElement(WuxiaElements.WIND.getId(), modifier);
	}

	private static void poisonCultivate(CultivatingEvent event) {
		var techniqueData = Cultivation.get(event.getPlayer()).getSystemData(event.getSystem()).techniqueData;
		var grid = techniqueData.grid;
		int posionCount = 0;
		for (var aspect : grid.getGrid().values()) {
			var aspect2 = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspect);
			if (aspect2 instanceof ConditionalElementalGenerator gen && gen.element == WuxiaElements.POISON.getId()) {
				posionCount++;
			}
		}
		var amount = event.getAmount();
		var modifier = BigDecimal.ONE;
		if (event.getPlayer().hasEffect(MobEffects.POISON)) {
			// modifier = modifier +  posionCount * 0.03
			modifier = modifier.add(new BigDecimal(posionCount).multiply(new BigDecimal(0.02)));
		} else if (event.getPlayer().hasEffect(MobEffects.WITHER)) {
			// modifier = modifier +  posionCount * 0.01
			modifier = modifier.add(new BigDecimal(posionCount).multiply(new BigDecimal(0.01)));
		} else {
			// modifier = modifier + posionCount * -0.01
			modifier = modifier.add(new BigDecimal(posionCount).multiply(new BigDecimal(-0.01)));
		}
		if (modifier.compareTo(BigDecimal.ZERO) < 0) modifier = BigDecimal.ZERO;
		if (modifier.compareTo(new BigDecimal(2)) > 0) modifier = new BigDecimal(2);
		event.addElement(WuxiaElements.POISON.getId(), modifier);
	}
	
	private static void lightCultivate(CultivatingEvent event) {
		var timeOfDay = event.getPlayer().level().getDayTime();
		var techniqueData = Cultivation.get(event.getPlayer()).getSystemData(event.getSystem()).techniqueData;
		var grid = techniqueData.grid;
		int starryBathCount = 0;
		for (var aspect : grid.getGrid().values()) {
			var aspect2 = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspect);
			if (aspect2 instanceof ConditionalElementalGenerator gen && gen.element == WuxiaElements.LIGHT.getId()) {
				starryBathCount++;
			}
		}
		var amount = event.getAmount();
		var modifier = BigDecimal.ONE;
		if (timeOfDay < 12000) {
			// modifier = modifier +  starryBathCount * 0.02
			modifier = modifier.add(new BigDecimal(starryBathCount).multiply(new BigDecimal(0.02)));
		} else {
			// modifier = modifier + starryBathCount * -0.01
			modifier = modifier.add(new BigDecimal(starryBathCount).multiply(new BigDecimal(-0.01)));
		}
		if (modifier.compareTo(BigDecimal.ZERO) < 0) modifier = BigDecimal.ZERO;
		if (modifier.compareTo(new BigDecimal(2)) > 0) modifier = new BigDecimal(2);
		event.addElement(WuxiaElements.LIGHT.getId(), modifier);
	}

	private static void darkCultivate(CultivatingEvent event) {
		var timeOfDay = event.getPlayer().level().getDayTime();
		var techniqueData = Cultivation.get(event.getPlayer()).getSystemData(event.getSystem()).techniqueData;
		var grid = techniqueData.grid;
		int shadowBathCount = 0;

		for (var aspect : grid.getGrid().values()) {
			var aspect2 = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspect);
			if (aspect2 instanceof ConditionalElementalGenerator gen && gen.element == WuxiaElements.DARK.getId()) {
				shadowBathCount++;
			}
		}
		var amount = event.getAmount();
		var modifier = BigDecimal.ONE;
		if (timeOfDay > 12000) {
			// modifier = modifier +  shadowBathCount * 0.02
			modifier = modifier.add(new BigDecimal(shadowBathCount).multiply(new BigDecimal(0.02)));
		} else {
			// modifier = modifier + shadowBathCount * -0.01
			modifier = modifier.add(new BigDecimal(shadowBathCount).multiply(new BigDecimal(-0.01)));
		}
		if (modifier.compareTo(BigDecimal.ZERO) < 0) modifier = BigDecimal.ZERO;
		if (modifier.compareTo(new BigDecimal(2)) > 0) modifier = new BigDecimal(2);
		event.addElement(WuxiaElements.DARK.getId(), modifier);
	}

	private static void devourCultivate(CultivatingEvent event) {
		var player = event.getPlayer();
		ICultivation cultivation = Cultivation.get(player);
		System system = event.getSystem();
		var techniqueData = cultivation.getSystemData(system).techniqueData;
		int devourCount = 0;
		double DevourMulti = 0d;
		var grid = techniqueData.grid;
		for (var aspect : grid.getGrid().values()) {
			var aspect2 = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspect);
			if (aspect2 instanceof ConditionalElementalGenerator gen && gen.element == WuxiaElements.DEMONIC.getId()) {
				DevourMulti += gen.generated;
				devourCount++;
			}
		}
		DevourMulti /= (double)devourCount;
		var itemStack = player.getMainHandItem();
		if(itemStack.getItem() instanceof SoulCore) {	
			CompoundTag itemTag = itemStack.getTag();
			if (itemTag == null) return;
			if (itemTag.contains("durability")) {
				int durability = itemTag.getInt("durability");
				itemTag.putInt("durability", durability-devourCount*(int)DevourMulti);
				if (itemTag.getInt("durability") <= 0) 
				itemStack.shrink(1); 
				cultivation.setStat(PlayerStat.LIVES, cultivation.getStat(PlayerStat.LIVES).add(BigDecimal.ONE).min(cultivation.getStat(PlayerStat.MAX_LIVES)));

				double durabilityMulti = Math.min(durability, devourCount*(int)DevourMulti)/100d;
				for (System systems : System.values()) {
					String strSystem = systems.toString().toLowerCase();
					if (itemTag.contains(strSystem)) {
						CompoundTag cTag = itemTag.getCompound(strSystem);
						BigDecimal amount = new BigDecimal(cTag.getDouble("amount")*durabilityMulti);
						BodyCultivationContainer bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
						if (systems == System.BODY) {
							bodyData.forgeAllParts(amount);
						} else
						cultivation.addStat(systems, PlayerSystemStat.CULTIVATION_BASE, amount);
						cultivation.addStat(System.ESSENCE, WuxiaElements.DEMONIC.getId(), PlayerSystemElementalStat.FOUNDATION, amount);
					}
				}
			}
		} else {
			var devourData = TechniqueUtil.getDevouringDataPerItem(itemStack.getItem());
			if (devourData.size() <= 0) return;
			if (devourCount > itemStack.getCount()) devourCount = itemStack.getCount();
			var baseAmount = event.getAmount();
			MobEffectInstance instance = null;
			BigDecimal demonicScale = new BigDecimal(devourCount*DevourMulti*2d);
			if (system == System.ESSENCE && player.hasEffect(WuxiaMobEffects.SPIRITUAL_RESONANCE.get())) {
				instance = player.getEffect(WuxiaMobEffects.SPIRITUAL_RESONANCE.get());
			} else
			if (system == System.BODY && player.hasEffect(WuxiaMobEffects.PILL_RESONANCE.get())) {
				instance = player.getEffect(WuxiaMobEffects.PILL_RESONANCE.get());
			} else
			if (system == System.DIVINE && player.hasEffect(WuxiaMobEffects.ENLIGHTENMENT.get())) {
				instance = player.getEffect(WuxiaMobEffects.ENLIGHTENMENT.get());
			}
			if (instance != null) {
				var amplifier = instance.getAmplifier();
				//amount = amount * (1 + (2 ^ amplifier))
				demonicScale = demonicScale.multiply(BigDecimal.ONE.add(new BigDecimal("2").pow(amplifier)));
			}
			for (var element : devourData.keySet()) {
				baseAmount = baseAmount.add(devourData.get(element).multiply(new BigDecimal(devourCount*DevourMulti)));
				if (system != System.BODY)
				cultivation.addStat(system, element, PlayerSystemElementalStat.FOUNDATION, baseAmount.multiply(new BigDecimal(devourCount*DevourMulti/10d)));
				cultivation.addStat(element, PlayerElementalStat.COMPREHENSION, baseAmount.multiply(new BigDecimal(devourCount*DevourMulti*3d/10d)));
				cultivation.addStat(System.ESSENCE, WuxiaElements.DEMONIC.getId(), PlayerSystemElementalStat.FOUNDATION, demonicScale);
			}
			itemStack.shrink(devourCount);
			event.setAmount(baseAmount);
		}
		event.addElement(WuxiaElements.DEMONIC.getId(), BigDecimal.ONE);
	}


	/*


metallic heart (metal) -> gather metal cultivation base when near metal blocks
  image = image.empty
Author = @[Dao of luck breeding] syn

beast comprehension -> tamed animals might give a raw soul cultivation base
  image = image.empty

Author = @[Dao of luck breeding] syn
Aires — 01/24/2022
enlightenment -> gets a small chance of getting enlightened that increases cult speed for a while
  image = image.empty
Aires — 01/26/2022
slaugther -> gets soul cultivation base from killing
  image = image.empty

Author = @Seteron
hungry for earth -> gets cultivation base by breaking blocks (chanced) and increases proficiency in breaking earth element blocks
  image = image.empty

Author = @[Ruler of Dragons] Wu Long
Aires — 01/28/2022
chronological return -> gets time cultivation base from continuously going back to the start of the session and repeatedly increasing the cultivation based gained from it. From outside (and to the player) you might just be cultivating at the same place. Cultivation base increases exponentially as long as you don't move and keeps cultivating
  image = image.empty

Author = @Everyone's Junior Sister
Aires — 02/27/2022
Aspect of Luck = Transforms raw cultivation base (the refined part) into luck.
  image = image.empty

Author = @[Dao of Forests] Mt Febian
Sound manipulation = Unlocks search abilities using sound
  image = image.empty

Author = @[Dao of Forests] Mt Febian
Heavenly Dao Bot
BOT
 — 02/28/2022
Rain Aspect
Converts a little bit of water cultivation base into water elemental pierce for attacks
Author
@[Dao of Forests] Mt Febian
Sigh Aspect
Uses body cultivation base to enhance sight
Author
@[Dao of Forests] Mt Febian
Poisoned Qi
Converts a little bit of cultivation base to make attacks poison on contact
Author
@[Dao of Forests] Mt Febian
Heavenly Dao Bot
BOT
 — 02/28/2022
Aspect of Stars
Uses stars energy to generate a little bit of raw body cultivation base, works better at night
Author
@Asura
Yin Yang Exchange
Generates raw cultivation base if there is a partner on the bed same bed while cultivating (Only works up to 1 partner)
Author
@[Dao of Retardism]Gavatron80
Yin Aspect Dual Cultivation
Yin counterpart of the Yin Yang Exchange, gives bonus to yin elements connected
Author
@Vermilion Bird
Yang Aspect Dual Cultivaiton
Yang counterpart of the Yin Yang Exchange, gives bonus to yang elements connected
Author
@Vermilion Bird
Aspect of chaos
Generate good amounts of cultivation base at the cost of foundation
Author
<@!338712089650790401>
Aspect of order
Generate good amounts of cultivation base if foundation is Strong
Author
<@!338712089650790401>
Primordial Chaos
Uses spatial energy from the outer chaos to generate cultivation base
Author
@Vermilion Bird
Demon Aspect
Uses raw cultivation base and turns it into demonic cultivation base
Author
@Vermilion Bird
Dragon Aspect
Uses body cultivation base and starts transforming body into dragon
Author
@Vermilion Bird
Phoenix Aspect
Uses body cultivation base and starts transforming body into Phoenix
Author
@Vermilion Bird
Tiger Aspect
Uses body cultivation base and starts transforming body into Tiger
Author
@Vermilion Bird
Tortoise Aspect
Uses body cultivation base and starts transforming body into Tortoise
Author
@Vermilion Bird
Qilin Aspect
Uses body cultivation base and starts transforming body into Qilin
Author
@Vermilion Bird
Fox Aspect
Uses body cultivation base and starts transforming body into Fox
Author
@Everyone's Junior Sister
Five elements conversion
Generates cultivation base from the most efficient way of the cycle of the five base elements
Author
<@!590116241319133204>
Alchemy Cultivation
Gain cultivation base from performing alchemy
Author
@Zigresho
Defiance
Gains demonic cultivation base from killing stronger foes
Author
@Zigresho
Constellation aspects
A plethora of aspects that can have a constellation linked that can even perhaps contain some law knowledge behind
Author
@Harvey
Enhancement
When paired with another generation aspect will enhance a that aspect
Author
@[Dao of Retardism]Gavatron80
Aspect of Steam
By using both fire and water cultivation base to practice in a form that they benefit each other in the form of steam
Author
<@!491663363676307466>
Aspect of Ice
By freezing water cultivation base, can be used to practice in a form that water will be frozen
Author
<@!491663363676307466>
Aspect of Tribulation
By comprehending tribulation energy, can use it to attack foes
Author
@[Dao of Retardism]Gavatron80
Vitality Aspect
Uses wooden cultivation base to add quick healing properties to the body, causing the a phenomenon that will seem like the practitioner has a great health
Author
@Asura
Healing
Uses wooden cultivation base to transform some of it into a powerful healing skill to aid allies
Author
@Asura
Vermilion Flames
By comprehending a super rare form of fire, the Vermilion Flames from the Vermilion Bird, allows you to generate that fire to cultivate with it
Author
@Vermilion Bird
Dryad Life Link
By comprehending a rare wooden law, you learn to link your life essence to a plant. As long as that plant exists, you'll exist, and the damage will be shared with that plant
Author
@Vermilion Bird
Blending Aspect
Allows to enhance stats gained by the elements in the body system by further blending with the elements in your body
Author
@[DSS Leader] MrEizy
Gravity Manipulation
By comprehending rare wave forms released by the earth element spatial form, the practitioner can alter the gravity in a certain region
Author
@Asura
Poising Coexistence
By absorbing poison, generates raw cultivation base
Author
@Ancient Devouring Beast
Existence Paradox Aspect
By comprehending a very rare part of some law, gives the practitioner the ability of temporarily fade into non existence
Author
<@!881552223971192844>
Life and Death Exchange
By comprehending a rare part of the wood law, allows to exchange the vitality of nearby living things to bring to life someone who almost died
Samsara Experience
By comprehending the reincarnation cycle of life and death, allows to pull someone from this cycle to continue living in this life
Author
@Vermilion Bird
Metallic Body Aspect
Consume random metals and increase body metallic nature
Author
@Zigresho
Crystallic Body Aspect
Consume random metals and increase body crystallic nature
Author
@Zigresho
Lightning Aspect
When struck by lightning gains a little of cultivation base
Author
@Zigresho
Lightning Circuit
By further understanding the lightning strikes, enables the practitioner to gain more cultivation base from Lightning Aspect (use both together)
Author
@Zigresho
Scholarly Aspect
Gains cultivation base from reading or writing books
Author
@Seteron
Bleeding edge
Enhance body cultivation base gain from being with low health
Author
@Seteron
Heavenly Dao Bot
BOT
 — 02/28/2022
Oneironautics
Gains cultivation base from cultivating inside of the dreams
Author
@Zigresho
Lay Down Roots
Passively lay down energy roots that allows for better energy recovery in the essence system, only works for essence system
Author
@Seteron
Boundless Sea
By using water comprehension on how water is in the nature, allows for the practitioner to use the same logic to store it's own energy and further increase max energy
Author
@Seteron
Heavenly Dao Bot
BOT
 — 02/28/2022
Aspect of training
A peculiar aspect that allows you to gain body cultivation base from physical activities the cultivator do
Author
@[Dao of Dimension] Blue Phoenix
Aspect of running
A peculiar aspect that allows the practitioner to have extra speed based on how much the practitioner have traversed on ground
Author
@[Dao of Dimension] Blue Phoenix
Undead Slayer
Gain stats for when nearby undead are detected and have increased cultivation base when undead detected undead are killed in the detection range
Author
@[Dao of Dimension] Blue Phoenix
Aspect of Clear Minded
Gains divine cultivation base from doing activities that make your mind clear in the mundane world, like fishing
Author
@Heavenly Fruit Deity IV
Heavenly Dao Bot
BOT
 — 02/28/2022
Aspect of Moss
Collects wooden energy from nature and transforms it into wooden cultivation base
Author
@[Dao of Forests] Mt Febian
Aspect of Lichen
When combined with other wood element generation aspect symbionts will further enhance the wooden energy, enhancing that aspect generated resources
Author
@[Dao of Forests] Mt Febian
Aspect of Stem
When combined with other wood element generation aspect will make the wood energy grow into stems, enhancing that aspect generated resources
Author
@[Dao of Forests] Mt Febian
Herbalist
Growing herbs will generate wooden cultivation base
Author
@papryk
Concept of Buddhism
Grants an increased soul cultivation base, but in return hurting living things will cause a loss of cultivation base and foundation
Author
@Seteron
Heavenly Dao Bot
BOT
 — 02/28/2022
Sword Affinity
Grants cultivation base from the comprehension of the sword
Author
@Zigresho
Heavenly Dao Bot
BOT
 — 03/02/2022
Cinder
Tier 1 Fire generation aspect = Gets fire energy from the environment and with it's cinders, transform that into cultivaiton base
Author
@Sapphire Dragon Emperor
Blaze
Tier 3 Fire generation aspect = Gets fire energy from the environment and make them blaze to transform that into cultivaiton base
Author
@Sapphire Dragon Emperor
	 */

}
