package com.airesnor.wuxiacraft;

import com.airesnor.wuxiacraft.commands.*;
import com.airesnor.wuxiacraft.proxy.CommonProxy;
import net.minecraft.world.GameRules;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = WuxiaCraft.MOD_ID, name = WuxiaCraft.NAME, version = WuxiaCraft.VERSION, guiFactory = WuxiaCraft.CONFIG_GUI_FACTORY)
public class WuxiaCraft {
	public static final String MOD_ID = "wuxiacraft";
	public static final String NAME = "Wuxia Craft";
	public static final String VERSION = "@VERSION@";
	public static final String CONFIG_GUI_FACTORY = "com.airesnor.wuxiacraft.config.WuxiaCraftConfigFactory";

	public static Logger logger;

	@Mod.Instance(WuxiaCraft.MOD_ID)
	public static WuxiaCraft instance;

	@SidedProxy(clientSide = "com.airesnor.wuxiacraft.proxy.ClientProxy", serverSide = "com.airesnor.wuxiacraft.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// some example code
		//logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
		proxy.init();
	}

	@EventHandler
	public void onServerStart(FMLServerStartingEvent event) {
		registerCommands(event);
		registerGameRules(event);
	}

	public void registerCommands(FMLServerStartingEvent event) {
		event.registerServerCommand(new CultivationCommand());
		event.registerServerCommand(new CultTechsCommand());
		event.registerServerCommand(new AdvCultLevel());
		event.registerServerCommand(new ResetCultCommand());
		event.registerServerCommand(new SkillsCommand());
		event.registerServerCommand(new CreateRecipeCommand());
		event.registerServerCommand(new CultInfoCommand());
		event.registerServerCommand(new CultHelpCommand());
		event.registerServerCommand(new TPtoDimCommand());
		event.registerServerCommand(new EnergyCommand());
		event.registerServerCommand(new ProgressCommand());
		event.registerServerCommand(new HealthCommand());
		event.registerServerCommand(new FoundationCommand());
		event.registerServerCommand(new TribulationCommand());
		event.registerServerCommand(new SealCommand());
		event.registerServerCommand(new BarrierCommand());
		event.registerServerCommand(new FoodCommand());
		event.registerServerCommand(new SectCommand());
		event.registerServerCommand(new WorldVarCommand());
		event.registerServerCommand(new ProficiencyCommand());
		event.registerServerCommand(new SectAdminCommand());
	}

	public void registerGameRules(FMLServerStartingEvent event) {
		if(!event.getServer().getWorld(0).getGameRules().hasRule("doPlayerSkillSetFire")) {
			event.getServer().getWorld(0).getGameRules().addGameRule("doPlayerSkillSetFire", "true", GameRules.ValueType.BOOLEAN_VALUE);
		}
		if(!event.getServer().getWorld(0).getGameRules().hasRule("doMobSkillSetFire")) {
			event.getServer().getWorld(0).getGameRules().addGameRule("doMobSkillSetFire", "true", GameRules.ValueType.BOOLEAN_VALUE);
		}
		if(!event.getServer().getWorld(0).getGameRules().hasRule("tribulationMultiplier")) {
			event.getServer().getWorld(0).getGameRules().addGameRule("tribulationMultiplier", "1", GameRules.ValueType.NUMERICAL_VALUE);
		}
	}
}
