package com.airesnor.wuxiacraft.config;

import com.airesnor.wuxiacraft.WuxiaCraft;
import com.airesnor.wuxiacraft.cultivation.ICultivation;
import com.airesnor.wuxiacraft.networking.NetworkWrapper;
import com.airesnor.wuxiacraft.networking.SpeedHandicapMessage;
import com.airesnor.wuxiacraft.utils.CultivationUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WuxiaCraftConfig {

	public static Configuration config = null;

	public static final String CATEGORY_GAMEPLAY = "gameplay";
	public static int speedHandicap;
	public static boolean disableStepAssist;
	public static float maxSpeed;
	public static float blockBreakLimit;
	public static float jumpLimit;
	public static float stepAssistLimit;

	public static final String CATEGORY_MISCELLANEOUS = "miscellaneous";
	public static boolean EXTREME_QI_BIOME_SPAWN;

	public static final String CATEGORY_DIMENSION = "dimensions";
	public static int DIMENSION_MINING;
	public static int DIMENSION_ELEMENTAL;
	public static int DIMENSION_SKY;

	public static final String CATEGORY_SERVER= "server";
	public static float maxServerSpeed;
	public static float serverCultivationSpeed;
	public static boolean enabledEvents;
	public static float worldEvent1Chance;
	public static float worldEvent2Chance;
	public static float worldEvent3Chance;

	public static void preInit() {
		File configFile = new File(Loader.instance().getConfigDir(), "WuxiaCraft.cfg");
		config = new Configuration(configFile);
		syncFromFiles();
	}

	public static Configuration getConfig() {
		return config;
	}

	public static void clientPreInit() {
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
	}

	public static void syncFromFiles() {
		syncConfig(true, true);

	}

	public static void syncFromGui() {
		syncConfig(false, true);
	}

	public static void syncFromFields() {
		syncConfig(false, false);
	}

	private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
		if (loadFromConfigFile)
			config.load();

		config.setCategoryComment(CATEGORY_GAMEPLAY, "Set Gameplay aspects");
		config.setCategoryComment(CATEGORY_DIMENSION, "Set Dimension IDs");
		config.setCategoryComment(CATEGORY_MISCELLANEOUS, "Set Miscellaneous Items");
		config.setCategoryComment(CATEGORY_SERVER, "Set Server limiters to help not going crazy");

		//Gameplay
		Property propHandicap = config.get(CATEGORY_GAMEPLAY, "speed_handicap", 100);
		propHandicap.setLanguageKey("gui.config.gameplay.speed_handicap.name");
		propHandicap.setComment("The relative top speed, after all, we're only humans. 0% = vanilla move speed - 100% = maximum available");
		propHandicap.setMaxValue(100);
		propHandicap.setMinValue(0);

		Property propMaxSpeed = config.get(CATEGORY_GAMEPLAY, "max_speed", 5.0f);
		propMaxSpeed.setLanguageKey("gui.config.gameplay.max_speed.name");
		propMaxSpeed.setComment("Max speed for playing, this will allow you to never go beyond this speed");
		propMaxSpeed.setDefaultValue(5.0f);

		Property propStepAssist = config.get(CATEGORY_GAMEPLAY, "step_assist", true);
		propStepAssist.setLanguageKey("gui.config.gameplay.step_assist.name");
		propStepAssist.setComment("If you want to enable step assist gained from cultivation levels");
		propStepAssist.setDefaultValue(true);

		Property propBreakSpeed = config.get(CATEGORY_GAMEPLAY, "haste_limit", 5f);
		propBreakSpeed.setLanguageKey("gui.config.gameplay.haste_limit.name");
		propBreakSpeed.setComment("Set a multiplier to base breaking speed that will be the it's limit gained from cultivation level");
		propBreakSpeed.setDefaultValue(5.0f);

		Property propJumpLimit = config.get(CATEGORY_GAMEPLAY, "jump_limit", 5f);
		propJumpLimit.setLanguageKey("gui.config.gameplay.jump_limit.name");
		propJumpLimit.setComment("Set a multiplier to base jump height that will be the it's limit gained from cultivation level");
		propJumpLimit.setDefaultValue(5.0f);

		Property propStepAssistLimit = config.get(CATEGORY_GAMEPLAY, "step_assist_limit", 3f);
		propJumpLimit.setLanguageKey("gui.config.gameplay.step_assist_limit.name");
		propJumpLimit.setComment("Set many blocks step assist permitted");
		propJumpLimit.setDefaultValue(3.0f);

		//Dimensions
		Property propDimensionMining = config.get(CATEGORY_DIMENSION, "dimension_mining", 200);
		propDimensionMining.setComment("Set the ID for the Mining Dimension");
		propDimensionMining.setDefaultValue(200);

		Property propDimensionElemental = config.get(CATEGORY_DIMENSION, "dimension_elemental", 201);
		propDimensionElemental.setComment("Set the ID for the Elemental Dimension");
		propDimensionElemental.setDefaultValue(201);

		Property propDimensionSky = config.get(CATEGORY_DIMENSION, "dimension_sky", 202);
		propDimensionSky.setComment("Set the ID for the Sky Dimension");
		propDimensionSky.setDefaultValue(202);

		//Miscellaneous
		Property propExtremeQiBiomeSpawn = config.get(CATEGORY_MISCELLANEOUS, "extreme_qi_biome_spawn", true);
		propExtremeQiBiomeSpawn.setComment("Set whether the Extreme Qi biome will spawn in overworld");
		propExtremeQiBiomeSpawn.set(true);

		//Server
		Property propMaxServerSpeed = config.get(CATEGORY_SERVER, "max_server_speed", 10.0);
		propMaxServerSpeed.setComment("Sets the maximum speed a player can have in the server");
		propMaxServerSpeed.setDefaultValue(10.0);

		Property propCultivationSpeed = config.get(CATEGORY_SERVER, "server_cultivation_speed", 1.0);
		propCultivationSpeed.setComment("Sets the speed a player can cultivate");
		propCultivationSpeed.setDefaultValue(1.0);

		Property enableEvents = config.get(CATEGORY_SERVER, "enable_events", true);
		enableEvents.setComment("World event1 chance");
		enableEvents.set(true);

		Property event1Chance = config.get(CATEGORY_SERVER, "event1_chance", 0.01);
		event1Chance.setComment("World event1 chance");
		event1Chance.setDefaultValue(0.01);
		Property event2Chance = config.get(CATEGORY_SERVER, "event2_chance", 0.01);
		event2Chance.setComment("World event2 chance");
		event2Chance.setDefaultValue(0.01);
		Property event3Chance = config.get(CATEGORY_SERVER, "event3_chance", 0.005);
		event3Chance.setComment("World event3 chance");
		event3Chance.setDefaultValue(0.005);

		List<String> propOrder = new ArrayList<>();
		propOrder.add(propHandicap.getName());
		propOrder.add(propMaxSpeed.getName());
		propOrder.add(propStepAssist.getName());
		propOrder.add(propBreakSpeed.getName());
		propOrder.add(propJumpLimit.getName());
		propOrder.add(propStepAssistLimit.getName());
		config.setCategoryPropertyOrder(CATEGORY_GAMEPLAY, propOrder);

		propOrder.add(propDimensionMining.getName());
		propOrder.add(propDimensionElemental.getName());
		propOrder.add(propDimensionSky.getName());
		config.setCategoryPropertyOrder(CATEGORY_DIMENSION, propOrder);

		propOrder.add(propExtremeQiBiomeSpawn.getName());
		config.setCategoryPropertyOrder(CATEGORY_MISCELLANEOUS, propOrder);

		if (readFieldsFromConfig) {
			speedHandicap = propHandicap.getInt();
			maxSpeed = (float) propMaxSpeed.getDouble();
			disableStepAssist = propStepAssist.getBoolean();
			blockBreakLimit = (float) propBreakSpeed.getDouble();
			jumpLimit = (float) propJumpLimit.getDouble();
			stepAssistLimit = (float) propStepAssistLimit.getDouble();
			DIMENSION_MINING = propDimensionMining.getInt();
			DIMENSION_ELEMENTAL = propDimensionElemental.getInt();
			DIMENSION_SKY = propDimensionSky.getInt();
			EXTREME_QI_BIOME_SPAWN = propExtremeQiBiomeSpawn.getBoolean();
			maxServerSpeed = (float) propMaxServerSpeed.getDouble();
			serverCultivationSpeed = (float) propCultivationSpeed.getDouble();
			enabledEvents = enableEvents.getBoolean();
			worldEvent1Chance = (float) event1Chance.getDouble();
			worldEvent2Chance = (float) event2Chance.getDouble();
			worldEvent3Chance = (float) event3Chance.getDouble();
		}

		propHandicap.set(speedHandicap);
		propMaxSpeed.set(maxSpeed);
		propStepAssist.set(disableStepAssist);
		propBreakSpeed.set(blockBreakLimit);
		propJumpLimit.set(jumpLimit);
		propStepAssistLimit.set(stepAssistLimit);
		propDimensionMining.set(DIMENSION_MINING);
		propDimensionSky.set(DIMENSION_SKY);
		propDimensionElemental.set(DIMENSION_ELEMENTAL);
		propExtremeQiBiomeSpawn.set(EXTREME_QI_BIOME_SPAWN);
		propMaxServerSpeed.set(maxServerSpeed);
		propCultivationSpeed.set(serverCultivationSpeed);
		enableEvents.set(enabledEvents);
		event1Chance.set(worldEvent1Chance);
		event2Chance.set(worldEvent2Chance);
		event3Chance.set(worldEvent3Chance);

		if (config.hasChanged())
			config.save();
	}

	public static class ConfigEventHandler {
		@SideOnly(Side.CLIENT)
		@SubscribeEvent
		public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(WuxiaCraft.MOD_ID)) {
				syncFromGui();
				WuxiaCraft.logger.info("Sending a config update to server");
				syncCultivationFromConfigToClient();
				NetworkWrapper.INSTANCE.sendToServer(new SpeedHandicapMessage(speedHandicap, maxSpeed, blockBreakLimit, jumpLimit, stepAssistLimit, Minecraft.getMinecraft().player.getUniqueID()));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static void syncCultivationFromConfigToClient() {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			ICultivation cultivation = CultivationUtils.getCultivationFromEntity(Minecraft.getMinecraft().player);
			cultivation.setSpeedHandicap(WuxiaCraftConfig.speedHandicap);
			cultivation.setMaxSpeed(WuxiaCraftConfig.maxSpeed);
			cultivation.setHasteLimit(WuxiaCraftConfig.blockBreakLimit);
			cultivation.setJumpLimit(WuxiaCraftConfig.jumpLimit);
			cultivation.setStepAssistLimit(WuxiaCraftConfig.stepAssistLimit);
		});
	}
}
