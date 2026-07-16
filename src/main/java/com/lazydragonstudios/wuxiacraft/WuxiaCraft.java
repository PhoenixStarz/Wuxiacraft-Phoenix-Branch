package com.lazydragonstudios.wuxiacraft;

import com.lazydragonstudios.wuxiacraft.capabilities.CapabilityAttachingHandler;
import com.lazydragonstudios.wuxiacraft.capabilities.CapabilityRegistryHandler;
import com.lazydragonstudios.wuxiacraft.command.*;
import com.lazydragonstudios.wuxiacraft.init.*;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.util.TechniqueUtil;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(WuxiaCraft.MOD_ID)
public class WuxiaCraft {

	public static final String MOD_ID = "wuxiacraft";

	public static final Logger LOGGER = LogUtils.getLogger();

	public WuxiaCraft() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);

		modEventBus.register(CapabilityRegistryHandler.class);

		MinecraftForge.EVENT_BUS.register(CapabilityAttachingHandler.class);

		MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);

		WuxiaElements.ELEMENTS.register(modEventBus);
		WuxiaBlocks.BLOCKS.register(modEventBus);
		WuxiaItems.ITEMS.register(modEventBus);
		WuxiaCreativeTabs.CREATIVE_TABS.register(modEventBus);
		WuxiaMenuTypes.MENU_TYPES.register(modEventBus);
		WuxiaBlockEntities.BLOCK_ENTITIES.register(modEventBus);
		WuxiaMobEffects.EFFECTS.register(modEventBus);
		WuxiaPoiTypes.POI_TYPES.register(modEventBus);
		WuxiaProfessions.PROFESSIONS.register(modEventBus);
		WuxiaParticleTypes.PARTICLE_TYPES.register(modEventBus);
		WuxiaRecipeTypes.RECIPE_TYPES.register(modEventBus);
		WuxiaRecipeTypes.RECIPE_TYPE_SERIALIZERS.register(modEventBus);
		WuxiaRealms.REALM_REGISTER.register(modEventBus);
		WuxiaRealms.STAGE_REGISTER.register(modEventBus);
		WuxiaEntities.ENTITY_TYPE_REGISTER.register(modEventBus);
		WuxiaTechniqueAspects.ASPECTS.register(modEventBus);
		WuxiaSkillAspects.ASPECTS.register(modEventBus);
		WuxiaBodyParts.BODY_PART_TYPES.register(modEventBus);
		WuxiaBodyParts.BODY_PARTS.register(modEventBus);
		WuxiacraftFeatures.FEATURES.register(modEventBus);

		WuxiaConfigs.registerConfigs();
	}

	private void setup(final FMLCommonSetupEvent event) {
		LOGGER.info("Registering messages. Check your transmission talisman!");
		WuxiaPacketHandler.registerMessages();

		WuxiaDefaultTechniqueManuals.init();
		WuxiaGameRules.registerRules();
		TechniqueUtil.initDevouringData();
		TechniqueUtil.initChancedAspectsBlocks();
		TechniqueUtil.initEntityChancedAspects();
		TechniqueUtil.initWeaponTechniques();
		TechniqueUtil.initDevouringAspects();
		TechniqueUtil.initTransformationAspects();
		registerArgumentTypes();
	}

	public void onRegisterCommands(final RegisterCommandsEvent event) {
		var dispatcher = event.getDispatcher();
		CultivationCommand.register(dispatcher);
		StatCommand.register(dispatcher);
		CreateDefaultManualCommand.register(dispatcher);
	}

	public void registerArgumentTypes() {
		ArgumentTypeInfos.registerByClass(AspectArgument.class, SingletonArgumentInfo.contextFree(AspectArgument::id));
		ArgumentTypeInfos.registerByClass(StageArgument.class, SingletonArgumentInfo.contextFree(StageArgument::id));
		ArgumentTypeInfos.registerByClass(ElementArgument.class, SingletonArgumentInfo.contextFree(ElementArgument::id));
	}
}
