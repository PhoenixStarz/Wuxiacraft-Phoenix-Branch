package com.lazydragonstudios.wuxiacraft.client.render;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.blocks.*;
import com.lazydragonstudios.wuxiacraft.capabilities.ClientAnimationState;
import com.lazydragonstudios.wuxiacraft.client.render.renderer.AnimatedPlayerRenderer;
import com.lazydragonstudios.wuxiacraft.client.render.renderer.AuraRenderer;
import com.lazydragonstudios.wuxiacraft.client.render.renderer.BodyTransformationRenderer;
import com.lazydragonstudios.wuxiacraft.client.render.renderer.DemonicAuraRenderer;
import com.lazydragonstudios.wuxiacraft.client.render.renderer.GhostRenderer;
import com.lazydragonstudios.wuxiacraft.cultivation.BodyCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerEntityRenderEventHandler {

	/**
	 * This event will cancel the basic render when the remote player is meditating
	 * And replace it with an animation
	 *
	 * @param event a description what is happening
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onSinglePlayerRender(RenderLivingEvent.Pre<AbstractClientPlayer, ? extends Model> event) {
		if (!(event.getEntity() instanceof Player player)) return; //we don't want local players so far
		var animationState = ClientAnimationState.get(player);
		if (animationState.isMeditating() || animationState.isExercising() || animationState.isSwordFlight()) {
			event.setCanceled(true);
		}
		if (!event.isCanceled()) return;
		//this is when we canceled the rendering to render it ourselves to add animations
		var renderer = (AnimatedPlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(WuxiaEntities.ANIMATED_PLAYER_ENTITY.get());
		if (((AbstractClientPlayer) player).getModelName() == "slim") {
			renderer = (AnimatedPlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(WuxiaEntities.ANIMATED_PLAYER_ENTITY_SLIM.get());
		}
		if (renderer == null) return;
		renderer.render((AbstractClientPlayer) player, player.yBodyRot, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRenderGhost(RenderLivingEvent.Post<AbstractClientPlayer, ? extends Model> event) {
		if (!(event.getEntity() instanceof AbstractClientPlayer target)) return;
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		if (!cultivation.isDivineSense()) return;
		var renderer = (GhostRenderer) Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(WuxiaEntities.GHOST_ENTITY.get());
		if (renderer == null) return;
		var targetCultivation = Cultivation.get(target);
		var range = cultivation.getStat(PlayerStat.DETECTION_RANGE).doubleValue();
		if (player.distanceTo(target) > range) return;
		var detectionStrength = cultivation.getStat(PlayerStat.DETECTION_STRENGTH);
		var detectionResistance = targetCultivation.getStat(PlayerStat.DETECTION_RESISTANCE);
		if (detectionStrength.compareTo(detectionResistance) <= 0) return;
		renderer.render(target, player.yBodyRot, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRenderHighlight(RenderLivingEvent.Post<?, ?> event) {
		if (event.getEntity() instanceof AbstractClientPlayer) return;
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		if (!cultivation.isDivineSense()) return;
		if (!player.isShiftKeyDown()) return;
		var target = event.getEntity();
		var range = cultivation.getStat(PlayerStat.DETECTION_RANGE).doubleValue();
		if (player.distanceTo(target) > range) return;
		var detectionStrength = cultivation.getStat(PlayerStat.DETECTION_STRENGTH);
		var detectionResistance = BigDecimal.valueOf(event.getEntity().getMaxHealth()).divide(new BigDecimal(10));
		if (detectionStrength.compareTo(detectionResistance) <= 0) return;

		PoseStack poseStack = event.getPoseStack();
		MultiBufferSource buffer = event.getMultiBufferSource();

		float partialTick = event.getPartialTick();

		EntityRenderer<?> entityRenderer = event.getRenderer();
		if (!(entityRenderer instanceof LivingEntityRenderer<?, ?> renderer0))
			return;

		@SuppressWarnings("unchecked")
		LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> renderer =
				(LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>>) renderer0;

		EntityModel<LivingEntity> model = renderer.getModel();

		float bodyYaw = Mth.rotLerp(partialTick, target.yBodyRotO, target.yBodyRot);
		float headYaw = Mth.rotLerp(partialTick, target.yHeadRotO, target.yHeadRot);
		float netHeadYaw = headYaw - bodyYaw;
		float pitch = Mth.lerp(partialTick, target.xRotO, target.getXRot());

		poseStack.pushPose();

		renderer.setupRotations(target, poseStack, target.tickCount + partialTick, bodyYaw, partialTick);

		renderer.scale(target, poseStack, partialTick);

		model.prepareMobModel(target, 0.0F, 0.0F, partialTick);

		VertexConsumer consumer = buffer.getBuffer(
				RenderType.outline(renderer.getTextureLocation(target))
		);
		
		poseStack.translate(0f, 1.5f, 0f);
		poseStack.scale(-1f, -1f, 1f);

		if(event.getEntity() instanceof Monster)
		model.renderToBuffer(poseStack, consumer, 0,
				OverlayTexture.NO_OVERLAY, 0.9F, 0F, 0F, 1F);
		else if(event.getEntity() instanceof NeutralMob)
		model.renderToBuffer(poseStack, consumer, 0,
				OverlayTexture.NO_OVERLAY, 0.8F, 0.8F, 0F, 1F);
		else if(event.getEntity() instanceof Animal)
		model.renderToBuffer(poseStack, consumer, 0,
				OverlayTexture.NO_OVERLAY, 0F, 0.9F, 0F, 1F);
		else 
		model.renderToBuffer(poseStack, consumer, 0,
				OverlayTexture.NO_OVERLAY, 0.9F, 0.9F, 0.9F, 1F);

		poseStack.popPose();
	}

    private static final Set<BlockPos> CACHED_BLOCKS = new HashSet<>();
    private static int scanTick = 0;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderWorldLast(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) return;

        var cultivation = Cultivation.get(player);
        double range = cultivation.getStat(PlayerStat.DETECTION_RANGE).doubleValue();

        if (!(range > 0.0D && cultivation.isDivineSense() && player.isCrouching())) {
            CACHED_BLOCKS.clear();
            return;
        }

        Level level = player.level();
        BlockPos center = player.blockPosition();

        int radius = (int) Math.ceil(range);
        int scanWidth = radius * 2 + 1;
        int x = (scanTick % scanWidth) - radius;
        scanTick++;

        if (scanTick >= scanWidth) {
            scanTick = 0;
        }

        for (int y = -radius; y <= radius; y++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos pos = center.offset(x, y, z);

                if (isDetectableBlock(level, pos)) {
                    CACHED_BLOCKS.add(pos.immutable());
                }
            }
        }

        CACHED_BLOCKS.removeIf(pos -> {
            Block block = level.getBlockState(pos).getBlock();
            return center.distSqr(pos) > range * range || !isDetectableBlock(block);
        });

        PoseStack poseStack = event.getPoseStack();
        Vec3 cameraPosition = mc.gameRenderer.getMainCamera().getPosition();

        var bufferSource = mc.renderBuffers().bufferSource();
        var vertexConsumer = bufferSource.getBuffer(WuxiaRenderTypes.outlineThroughBlocks.get());

        for (BlockPos pos : CACHED_BLOCKS) {
            AABB box = new AABB(pos).move(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
            LevelRenderer.renderLineBox(
                    poseStack,
                    vertexConsumer,
                    box,
                    0.2F, 0.8F, 1.0F, 1.0F
            );
        }
    }
    
	private static boolean isDetectableBlock(Level level, BlockPos pos) {
        return isDetectableBlock(level.getBlockState(pos).getBlock());
    }

    private static boolean isDetectableBlock(Block block) {
        return block instanceof BonsaiBlock
                || block instanceof VeinBlock
                || block instanceof BuddingSpiritCrystalBlock
                || block instanceof SpiritCrystalCluster;
    }

	@SubscribeEvent
	public static void onRenderAura(RenderLivingEvent.Post<AbstractClientPlayer, ? extends Model> event) {
		if (!(event.getEntity() instanceof AbstractClientPlayer target)) return;
		var renderer = (AuraRenderer) Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(WuxiaEntities.AURA_ENTITY.get());
		if (renderer == null) return;
		var cultivation = Cultivation.get(target);
		if (!cultivation.isCombat()) return;
		if (cultivation.getStat(PlayerStat.BARRIER).compareTo(BigDecimal.ZERO) <= 0) return;
		if (cultivation.getStat(PlayerStat.MAX_BARRIER).compareTo(BigDecimal.ZERO) <= 0) return;
		//rel = max(barrier/maxBarrier, 0.3);
		var barrierRelative = cultivation.getStat(PlayerStat.BARRIER)
				.divide(cultivation.getStat(PlayerStat.MAX_BARRIER), RoundingMode.HALF_UP).max(new BigDecimal("0.3")).floatValue();
		event.getPoseStack().pushPose();
		event.getPoseStack().scale(barrierRelative, 1f, barrierRelative);
		renderer.render(target, target.yBodyRot, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
		event.getPoseStack().popPose();
	}

	@SubscribeEvent
	public static void onRenderDemonicAura(RenderLivingEvent.Post<AbstractClientPlayer, ? extends Model> event) {
		if (!(event.getEntity() instanceof AbstractClientPlayer target)) return;
		var renderer = (DemonicAuraRenderer) Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(WuxiaEntities.DEMONIC_AURA_ENTITY.get());
		if (renderer == null) return;
		var cultivation = Cultivation.get(target);
		if (cultivation.getDemonicStage() < 10) return;
		var renderRelative = Math.min(1f, cultivation.getDemonicStage()/20f);
		event.getPoseStack().pushPose();
		event.getPoseStack().scale(renderRelative, 1f, renderRelative);
		renderer.render(target, target.yBodyRot, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
		event.getPoseStack().popPose();
	}

	@SubscribeEvent
	public static void onRenderBodyTransformations(RenderLivingEvent.Post<AbstractClientPlayer, ? extends Model> event) {
		if (!(event.getEntity() instanceof AbstractClientPlayer target)) return;
		var cultivation = Cultivation.get(target);
		var bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
		var transformation = bodyData.getDisplayBodyTransformation();
		if (transformation == null) return;
		var entityType = ForgeRegistries.ENTITY_TYPES.getValue(transformation);
		if (entityType == null) return;
		var renderer = (BodyTransformationRenderer) Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(entityType);
		if (renderer == null) return;
		renderer.render(target, target.yBodyRot, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
	}

	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var animationState = ClientAnimationState.get(player);
		if (animationState.isSwordFlight()) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onPlayerTickAddAnimation(TickEvent.PlayerTickEvent event) {
		if (event.side != LogicalSide.CLIENT) return;
		if (event.phase != TickEvent.Phase.END) return;
		var player = event.player;
		if (player == null) return;
		var animationState = ClientAnimationState.get(player);
		animationState.advanceAnimationFrame();
	}

}
