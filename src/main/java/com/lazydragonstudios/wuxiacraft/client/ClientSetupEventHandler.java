package com.lazydragonstudios.wuxiacraft.client;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.client.gui.InscriberScreen;
import com.lazydragonstudios.wuxiacraft.client.gui.IntrospectionScreen;
import com.lazydragonstudios.wuxiacraft.client.gui.RunemakingScreen;
import com.lazydragonstudios.wuxiacraft.client.overlays.*;
import com.lazydragonstudios.wuxiacraft.client.particle.BeamParticle;
import com.lazydragonstudios.wuxiacraft.client.particle.FlightParticle;
import com.lazydragonstudios.wuxiacraft.client.particle.QiFogParticle;
import com.lazydragonstudios.wuxiacraft.client.render.models.*;
import com.lazydragonstudios.wuxiacraft.client.render.renderer.*;
import com.lazydragonstudios.wuxiacraft.init.WuxiaEntities;
import com.lazydragonstudios.wuxiacraft.init.WuxiaMenuTypes;
import com.lazydragonstudios.wuxiacraft.init.WuxiaParticleTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEventHandler {

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(RenderHUDEventHandler.class);
		MinecraftForge.EVENT_BUS.register(InputHandler.class);
		event.enqueueWork(() -> {
			MenuScreens.register(WuxiaMenuTypes.INTROSPECTION_MENU.get(), IntrospectionScreen::new);
			MenuScreens.register(WuxiaMenuTypes.INSCRIBER_MENU.get(), InscriberScreen::new);
			MenuScreens.register(WuxiaMenuTypes.RUNEMAKING_MENU.get(), RunemakingScreen::new);
		});
	}

	@SubscribeEvent
	public static void onInputSetup(RegisterKeyMappingsEvent event) {
		event.register(InputHandler.OPEN_INTROSPECTION);
		event.register(InputHandler.KEY_DIVINE_SENSE);
		event.register(InputHandler.KEY_COMBAT_MODE);
		event.register(InputHandler.KEY_CAST_SKILL);
		event.register(InputHandler.KEY_SKILL_WHEEL);
		event.register(InputHandler.KEY_EXERCISE);
		event.register(InputHandler.KEY_MEDITATE);
	}
//	WuxiaConfigs.HEALTH_BAR_ENABLED.get()
	@SubscribeEvent
	public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) { 
		event.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "wuxiacraft_health_bar", new HealthOverlay());
		event.registerAbove(new ResourceLocation(WuxiaCraft.MOD_ID, "wuxiacraft_health_bar"), "wuxiacraft_barrier_bar", new BarrierOverlay());
		event.registerBelow(VanillaGuiOverlay.CHAT_PANEL.id(), "wuxiacraft_energies", new EnergiesOverlay());
		event.registerAboveAll("wuxiacraft_skill_wheel", new SkillWheel());
		event.registerAboveAll("wuxiacraft_debug", new DebugOverlay());
		event.registerAboveAll("wuxiacraft_combat_mode", new CombatModeOverlay());
		event.registerAboveAll("wuxiacraft_skill_bars", new SkillBarsOverlay());
	}

	@SubscribeEvent
	public static void onRenderingRegistry(EntityRenderersEvent.RegisterRenderers event) {
		//player overrides
		event.registerEntityRenderer(WuxiaEntities.ANIMATED_PLAYER_ENTITY.get(), ctx -> new AnimatedPlayerRenderer(ctx, false));
		event.registerEntityRenderer(WuxiaEntities.ANIMATED_PLAYER_ENTITY_SLIM.get(), ctx -> new AnimatedPlayerRenderer(ctx, true));
		event.registerEntityRenderer(WuxiaEntities.GHOST_ENTITY.get(), GhostRenderer::new);
		event.registerEntityRenderer(WuxiaEntities.AURA_ENTITY.get(), ctx -> new AuraRenderer(ctx));
		event.registerEntityRenderer(WuxiaEntities.DEMONIC_AURA_ENTITY.get(), ctx -> new DemonicAuraRenderer(ctx));

		//body transformations
		event.registerEntityRenderer(WuxiaEntities.KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneOneTailedModel(ctx.bakeLayer(KitsuneOneTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneTwoTailedModel(ctx.bakeLayer(KitsuneTwoTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneThreeTailedModel(ctx.bakeLayer(KitsuneThreeTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneFourTailedModel(ctx.bakeLayer(KitsuneFourTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneFiveTailedModel(ctx.bakeLayer(KitsuneFiveTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneSixTailedModel(ctx.bakeLayer(KitsuneSixTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneSevenTailedModel(ctx.bakeLayer(KitsuneSevenTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneEightTailedModel(ctx.bakeLayer(KitsuneEightTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneNineTailedModel(ctx.bakeLayer(KitsuneNineTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.LIGHT_KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneOneTailedModel(ctx.bakeLayer(KitsuneOneTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.LIGHT_KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneTwoTailedModel(ctx.bakeLayer(KitsuneTwoTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.LIGHT_KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneThreeTailedModel(ctx.bakeLayer(KitsuneThreeTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.LIGHT_KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneFourTailedModel(ctx.bakeLayer(KitsuneFourTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.LIGHT_KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneFiveTailedModel(ctx.bakeLayer(KitsuneFiveTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.LIGHT_KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneSixTailedModel(ctx.bakeLayer(KitsuneSixTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.LIGHT_KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneSevenTailedModel(ctx.bakeLayer(KitsuneSevenTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.LIGHT_KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneEightTailedModel(ctx.bakeLayer(KitsuneEightTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.LIGHT_KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneNineTailedModel(ctx.bakeLayer(KitsuneNineTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.SPATIAL_KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneOneTailedModel(ctx.bakeLayer(KitsuneOneTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.SPATIAL_KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneTwoTailedModel(ctx.bakeLayer(KitsuneTwoTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.SPATIAL_KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneThreeTailedModel(ctx.bakeLayer(KitsuneThreeTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.SPATIAL_KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneFourTailedModel(ctx.bakeLayer(KitsuneFourTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.SPATIAL_KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneFiveTailedModel(ctx.bakeLayer(KitsuneFiveTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.SPATIAL_KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneSixTailedModel(ctx.bakeLayer(KitsuneSixTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.SPATIAL_KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneSevenTailedModel(ctx.bakeLayer(KitsuneSevenTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.SPATIAL_KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneEightTailedModel(ctx.bakeLayer(KitsuneEightTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.SPATIAL_KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new KitsuneNineTailedModel(ctx.bakeLayer(KitsuneNineTailedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.HALF_DRAGON_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new HalfDragonModel(ctx.bakeLayer(HalfDragonModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.HALF_DRAGON_BODY_ARMED_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new HalfDragonArmsModel(ctx.bakeLayer(HalfDragonArmsModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.HALF_DRAGON_BODY_HORNED_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new HalfDragonArmsHornedModel(ctx.bakeLayer(HalfDragonArmsHornedModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.AZURE_HALF_DRAGON_BODY_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new HalfDragonModel(ctx.bakeLayer(HalfDragonModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.AZURE_HALF_DRAGON_BODY_ARMED_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new HalfDragonArmsModel(ctx.bakeLayer(HalfDragonArmsModel.LOCATION))));
		event.registerEntityRenderer(WuxiaEntities.AZURE_HALF_DRAGON_BODY_HORNED_TRANSFORMATION_ENTITY.get(),
				ctx -> new BodyTransformationRenderer(ctx, new HalfDragonArmsHornedModel(ctx.bakeLayer(HalfDragonArmsHornedModel.LOCATION))));

		//actual entities
		event.registerEntityRenderer(WuxiaEntities.THROW_SKILL_TYPE.get(), ThrowSkillRenderer::new);
		event.registerEntityRenderer(WuxiaEntities.SNAKE_ENTITY_TYPE.get(), SnakeRenderer::new);
		event.registerEntityRenderer(WuxiaEntities.DESERT_SNAKE_ENTITY_TYPE.get(), DesertSnakeRenderer::new);
		event.registerEntityRenderer(WuxiaEntities.RED_SNAKE_ENTITY_TYPE.get(), RedSnakeRenderer::new);
		event.registerEntityRenderer(WuxiaEntities.BLUE_SNAKE_ENTITY_TYPE.get(), BlueSnakeRenderer::new);
		event.registerEntityRenderer(WuxiaEntities.WHITE_SNAKE_ENTITY_TYPE.get(), WhiteSnakeRenderer::new);
		event.registerEntityRenderer(WuxiaEntities.BLACK_SNAKE_ENTITY_TYPE.get(), BlackSnakeRenderer::new);
	}

	@SubscribeEvent
	public static void onRegisterModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(GhostModel.LOCATION, () -> LayerDefinition.create(GhostModel.createMesh(CubeDeformation.NONE, 0f), 64, 64));
		event.registerLayerDefinition(HalfDragonModel.LOCATION, HalfDragonModel::createMesh);
		event.registerLayerDefinition(HalfDragonArmsModel.LOCATION, HalfDragonArmsModel::createMesh);
		event.registerLayerDefinition(HalfDragonArmsHornedModel.LOCATION, HalfDragonArmsHornedModel::createMesh);
		event.registerLayerDefinition(KitsuneOneTailedModel.LOCATION, KitsuneOneTailedModel::createMesh);
		event.registerLayerDefinition(KitsuneTwoTailedModel.LOCATION, KitsuneTwoTailedModel::createMesh);
		event.registerLayerDefinition(KitsuneThreeTailedModel.LOCATION, KitsuneThreeTailedModel::createMesh);
		event.registerLayerDefinition(KitsuneFourTailedModel.LOCATION, KitsuneFourTailedModel::createMesh);
		event.registerLayerDefinition(KitsuneFiveTailedModel.LOCATION, KitsuneFiveTailedModel::createMesh);
		event.registerLayerDefinition(KitsuneSixTailedModel.LOCATION, KitsuneSixTailedModel::createMesh);
		event.registerLayerDefinition(KitsuneSevenTailedModel.LOCATION, KitsuneSevenTailedModel::createMesh);
		event.registerLayerDefinition(KitsuneEightTailedModel.LOCATION, KitsuneEightTailedModel::createMesh);
		event.registerLayerDefinition(KitsuneNineTailedModel.LOCATION, KitsuneNineTailedModel::createMesh);
		event.registerLayerDefinition(SnakeModel.LOCATION, () -> LayerDefinition.create(SnakeModel.createBodyLayer(), 16, 16));
	}

	@SubscribeEvent
	public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(WuxiaParticleTypes.BODY_QI_FOG.get(),
				QiFogParticle.Provider::new
		);
		event.registerSpriteSet(WuxiaParticleTypes.DIVINE_QI_FOG.get(),
				QiFogParticle.Provider::new
		);
		event.registerSpriteSet(WuxiaParticleTypes.ESSENCE_QI_FOG.get(),
				QiFogParticle.Provider::new
		);
		event.registerSpriteSet(WuxiaParticleTypes.BEAM_SKILL_PHYSICAL.get(),
				spriteSet -> new BeamParticle.Provider(spriteSet, 1.0f, 0.9f, 0.6f)
		);
		event.registerSpriteSet(WuxiaParticleTypes.BEAM_SKILL_FIRE.get(),
				spriteSet -> new BeamParticle.Provider(spriteSet, 1.0f, 0.3f, 0.0f)
		);
		event.registerSpriteSet(WuxiaParticleTypes.BEAM_SKILL_EARTH.get(),
				spriteSet -> new BeamParticle.Provider(spriteSet, 0.5f, 0.4f, 0.05f)
		);
		event.registerSpriteSet(WuxiaParticleTypes.BEAM_SKILL_METAL.get(),
				spriteSet -> new BeamParticle.Provider(spriteSet, 1.0f, 1.0f, 0.9f)
		);
		event.registerSpriteSet(WuxiaParticleTypes.BEAM_SKILL_WATER.get(),
				spriteSet -> new BeamParticle.Provider(spriteSet, 0.2f, 0.4f, 1.0f)
		);
		event.registerSpriteSet(WuxiaParticleTypes.BEAM_SKILL_WOOD.get(),
				spriteSet -> new BeamParticle.Provider(spriteSet, 0.3f, 0.75f, 0.2f)
		);
		event.registerSpriteSet(WuxiaParticleTypes.BEAM_SKILL_LIGHTNING.get(),
				spriteSet -> new BeamParticle.Provider(spriteSet, 0.6f, 0.1f, 0.85f)
		);
		event.registerSpriteSet(WuxiaParticleTypes.BEAM_SKILL_POISON.get(),
				spriteSet -> new BeamParticle.Provider(spriteSet, 0.35f, 0.6f, 0f)
		);
		event.registerSpriteSet(WuxiaParticleTypes.BEAM_SKILL_LIGHT.get(),
				spriteSet -> new BeamParticle.Provider(spriteSet, 1.0f, 1.0f, 1.0f)
		);
		event.registerSpriteSet(WuxiaParticleTypes.BEAM_SKILL_SPACE.get(),
				spriteSet -> new BeamParticle.Provider(spriteSet, 0.06f, 0f, 0.4f)
		);

		event.registerSpriteSet(WuxiaParticleTypes.FLIGHT_PARTICLE.get(),
				spriteSet -> new FlightParticle.Provider(spriteSet, 0.6f, 0.5f, 0.1f)
		);
	}
}
