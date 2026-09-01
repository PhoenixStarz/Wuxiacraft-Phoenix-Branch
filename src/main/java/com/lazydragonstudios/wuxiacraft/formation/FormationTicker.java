package com.lazydragonstudios.wuxiacraft.formation;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.blocks.entity.FormationCore;
import com.lazydragonstudios.wuxiacraft.client.WorldRenderHandler;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaButton;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.init.WuxiaParticleTypes;
import com.lazydragonstudios.wuxiacraft.item.FormationBarrierBadge;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;
import org.joml.Vector4f;

import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

@ParametersAreNonnullByDefault
public class FormationTicker implements BlockEntityTicker<FormationCore> {

	public static ResourceLocation BARRIER_BREAKING_STAGES = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/barrier_breaking_stages.png");

	public static final TagKey<EntityType<?>> ALLOWED_INSIDE_BARRIER = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(WuxiaCraft.MOD_ID, "allowed_inside_barrier"));

	public static HashMap<System, ParticleType<SimpleParticleType>> PARTICLE_BY_SYSTEM = new HashMap<>();

	static {
		PARTICLE_BY_SYSTEM.put(System.BODY, WuxiaParticleTypes.BODY_QI_FOG.get());
		PARTICLE_BY_SYSTEM.put(System.DIVINE, WuxiaParticleTypes.DIVINE_QI_FOG.get());
		PARTICLE_BY_SYSTEM.put(System.ESSENCE, WuxiaParticleTypes.ESSENCE_QI_FOG.get());
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState blockState, FormationCore core) {
		if (core.scheduleActivation) {
			core.activate(core.owner);
		}
		if (!core.isActive()) return;
		var owner = core.getOwner();
		if (owner != null && !level.isClientSide()) {
			var cultivation = Cultivation.get(owner);
			if (cultivation.getFormation() != null) {
				if (cultivation.getFormation().compareTo(pos) != 0) {
					core.deactivate();
					return;
				}
			} else {
				core.deactivate();
				return;
			}
		}
		var barrierAmount = core.getStat(FormationStat.BARRIER_AMOUNT);
		var barrierMaxAmount = core.getStat(FormationStat.BARRIER_MAX_AMOUNT);
		var barrierRegen = core.getStat(FormationStat.BARRIER_REGEN);
		var barrierCooldown = core.getStat(FormationStat.BARRIER_COOLDOWN);
		if (barrierAmount.compareTo(barrierMaxAmount) < 0 && barrierCooldown.compareTo(BigDecimal.ZERO) <= 0) {
			core.setStat(FormationStat.BARRIER_AMOUNT, barrierAmount.add(barrierRegen).min(barrierMaxAmount));
		}
		if (barrierCooldown.compareTo(BigDecimal.ZERO) > 0) {
			core.setStat(FormationStat.BARRIER_COOLDOWN, barrierCooldown.subtract(BigDecimal.ONE).max(BigDecimal.ZERO));
		}
		if (barrierAmount.compareTo(BigDecimal.ZERO) > 0) {
			var barrierRange = core.getStat(FormationStat.BARRIER_RANGE).intValue();
			var aabb = new AABB(core.getBlockPos()).inflate(barrierRange);
			var entities = level.getEntities(core.getOwner(),
					aabb, entity ->
							pos.distToCenterSqr(entity.getX(), entity.getY(), entity.getZ()) < barrierRange * barrierRange &&
									!entity.getType().is(ALLOWED_INSIDE_BARRIER) &&
									((entity instanceof LivingEntity && !(entity instanceof Animal)) ||
											(entity instanceof Projectile proj && proj.getOwner() != core.getOwner()) ||
											(entity instanceof Boat boat && boat.hasPassenger(passenger -> passenger != core.getOwner()))
									));
			var allowedPlayers = new HashSet<Player>();
			for (var entity : entities) {
				if (entity instanceof Player targetPlayer) {
					var inv = targetPlayer.getInventory();
					for (var itemStack : inv.items) {
						if (!(itemStack.getItem() instanceof FormationBarrierBadge)) continue;
						var tag = itemStack.getTag();
						if (tag == null) continue;
						if (!tag.contains("formation")) continue;
						var formationTag = tag.getCompound("formation");
						String ownerTag = formationTag.getString("ownerName");
						String formationOwner = core.getOwnerName();
						if (!ownerTag.equals(formationOwner)) continue;
						int x = formationTag.getInt("x");
						int y = formationTag.getInt("y");
						int z = formationTag.getInt("z");
						var blockPos = new BlockPos(x, y, z);
						if (blockPos.compareTo(pos) == 0) {
							allowedPlayers.add(targetPlayer);
						}
					}
				}
			}
			for (var entity : entities) {
				if (entity instanceof Boat boat) {
					Boolean cont = true;
					for (var ridingEntities : boat.getPassengers()) {
						if (ridingEntities instanceof Player player && !allowedPlayers.contains(player)) {
							cont = false;
							break;
						}
					}
					if (cont) continue;
				}
				if (entity instanceof AbstractMinecart cart) {
					Boolean cont = true;
					for (var ridingEntities : cart.getPassengers()) {
						if (ridingEntities instanceof Player player && !allowedPlayers.contains(player)) {
							cont = false;
							break;
						}
					}
					if (cont) continue;
				}
				if (entity instanceof PlayerRideable && entity.hasPassenger(allowedPlayers::contains)) continue;
				if (entity instanceof ArmorStand) continue;
				if (entity instanceof Npc) continue;
				if (entity instanceof Projectile proj) {
					if (allowedPlayers.contains(proj.getOwner())) continue;
					core.attackBarrierMelee((LivingEntity)proj.getOwner(), 1.0f);
				}
				if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) continue;
				if (allowedPlayers.contains(entity)) continue;
				if (entity instanceof Monster monster) {
					if (!monster.swinging && monster.getTarget() != null) {
						core.attackBarrierMelee(monster, 1.0f);
						monster.swing(InteractionHand.MAIN_HAND);
					}
				}
				Vector3f movement = new Vector3f((float) (entity.getX() - pos.getX()), (float) (entity.getY() - pos.getY()), (float) (entity.getZ() - pos.getZ()));
				movement.normalize();
				movement.mul(0.6f);
				entity.setDeltaMovement(movement.x(), movement.y() + 0.007f, movement.z());
			}
		}
		if (!level.isClientSide) return;
		for (var system : System.values()) {
			var systemEnergyRegen = core.getStat(system, FormationSystemStat.ENERGY_REGEN);
			if (systemEnergyRegen.compareTo(BigDecimal.ZERO) <= 0) continue;
			var range = core.getStat(system, FormationSystemStat.ENERGY_REGEN_RANGE).doubleValue();
			int particles = Math.max(1, (int) (systemEnergyRegen.doubleValue() / 0.02f));
			particles = Math.min(particles, 40);
			for (int i = 0; i < particles; i++) {
				level.addParticle((ParticleOptions) PARTICLE_BY_SYSTEM.get(system),
						pos.getX() + 0.5d + Math.random() * 2 * range - range,
						pos.getY() + 0.5d + Math.random() * 2 * range - range,
						pos.getZ() + 0.5d + Math.random() * 2 * range - range,
						0d, 0.1d, 0d);
			}
		}
		renderClientSide(core);
	}

	@OnlyIn(Dist.CLIENT)
	public void renderClientSide(FormationCore core) {
		var barrierAmount = core.getStat(FormationStat.BARRIER_AMOUNT);
		var maxBarrierAmount = core.getStat(FormationStat.BARRIER_MAX_AMOUNT);
		if (barrierAmount.compareTo(BigDecimal.ZERO) <= 0) return;
		if (maxBarrierAmount.compareTo(BigDecimal.ZERO) <= 0) return;
		WorldRenderHandler.LEVEL_RENDER_QUEUE.add(1, (poseStack, partialTick) -> {
			poseStack.pushPose();
			//poseStack.setIdentity();
			var pos = core.getBlockPos();
			//poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
			Tesselator tesselator = Tesselator.getInstance();
			BufferBuilder builder = tesselator.getBuilder();
			var player = Minecraft.getInstance().player;
			if (player == null) return;
			Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
			var camPos = camera.getPosition();
			poseStack.translate(pos.getX() - camPos.x, pos.getY() - camPos.y, pos.getZ() - camPos.z);
			RenderSystem.enableBlend();
			//RenderSystem.disableCull();
			var barrierRange = core.getStat(FormationStat.BARRIER_RANGE).intValue();
			var distSqr = pos.distToCenterSqr(player.getX(), player.getY(), player.getZ());
			if (distSqr <= barrierRange * barrierRange) {
				RenderSystem.disableCull();
			}
			var formationFilled = barrierAmount.divide(maxBarrierAmount, RoundingMode.HALF_EVEN).floatValue();
			renderUVSphere(poseStack, tesselator, builder, barrierRange, formationFilled);
			poseStack.popPose();
		});
	}

	private static void renderUVSphere(PoseStack poseStack, Tesselator tesselator, BufferBuilder bufferBuilder, double radius, float barrierFill) {
		int verticalSections = 32;
		int horizontalSections = 64;
		float breakingSteps = 1 / 7f;
		int breakingStep = 6 - (int) (barrierFill * 6f);
		RenderSystem.setShaderTexture(0, BARRIER_BREAKING_STAGES);
		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
		int colorAlpha = (int) ((0x12) + (0x7C - 0x12) * barrierFill);
		colorAlpha *= 0x01000000;
		int barrierColor = colorAlpha + 0x00FFFFFF;
		for (int i = 1; i < verticalSections - 1; i++) {
			double y0 = Math.cos(Math.PI * (double) i / (double) verticalSections);
			double y0Radius = radius * Math.sin(Math.PI * (double) i / (double) verticalSections);
			double y1 = Math.cos(Math.PI * (double) (i + 1) / (double) verticalSections);
			double y1Radius = radius * Math.sin(Math.PI * (double) (i + 1) / (double) verticalSections);
			for (int j = 0; j < horizontalSections; j++) {
				double x0Pos = Math.cos(2 * Math.PI * (double) (j) / (double) horizontalSections);
				double x1Pos = Math.cos(2 * Math.PI * (double) (j + 1) / (double) horizontalSections);
				double z0Pos = Math.sin(2 * Math.PI * (double) (j) / (double) horizontalSections);
				double z1Pos = Math.sin(2 * Math.PI * (double) (j + 1) / (double) horizontalSections);
				float[][] uvs = new float[][]{
						new float[]{(breakingStep) * breakingSteps, 0},
						new float[]{(breakingStep + 1) * breakingSteps, 0},
						new float[]{(breakingStep + 1) * breakingSteps, 1},
						new float[]{(breakingStep) * breakingSteps, 1}};
				Vector3f[] vertices = new Vector3f[]{
						new Vector3f((float) (x0Pos * y0Radius), (float) (y0 * radius), (float) (z0Pos * y0Radius)),
						new Vector3f((float) (x1Pos * y0Radius), (float) (y0 * radius), (float) (z1Pos * y0Radius)),
						new Vector3f((float) (x1Pos * y1Radius), (float) (y1 * radius), (float) (z1Pos * y1Radius)),
						new Vector3f((float) (x0Pos * y1Radius), (float) (y1 * radius), (float) (z0Pos * y1Radius)),
				};
				for (int v = 0; v < 4; v++) {
					var vertex = vertices[v];
					var uv = uvs[v];
					var vertexPosInWorld = new Vector4f(vertex.x(), vertex.y(), vertex.z(), 1f);
					vertexPosInWorld.mul(poseStack.last().pose());
					bufferBuilder.vertex(vertexPosInWorld.x(), vertexPosInWorld.y(), vertexPosInWorld.z()).color(barrierColor).uv(uv[0], uv[1]).endVertex();
				}
			}
		}
		tesselator.end();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
		var vertexPosInWorldTop = new Vector4f(0, (float) radius, 0, 1f);
		vertexPosInWorldTop.mul(poseStack.last().pose());
		bufferBuilder.vertex(vertexPosInWorldTop.x(), vertexPosInWorldTop.y(), vertexPosInWorldTop.z()).color(barrierColor).endVertex();
		double y0 = Math.cos(Math.PI / (double) verticalSections);
		double y0Radius = radius * Math.sin(Math.PI / (double) verticalSections);
		for (int j = horizontalSections; j >= 0; j--) {
			double x0Pos = Math.cos(2 * Math.PI * (double) (j) / (double) horizontalSections);
			double z0Pos = Math.sin(2 * Math.PI * (double) (j) / (double) horizontalSections);
			var vertexPosInWorld = new Vector4f((float) (x0Pos * y0Radius), (float) (y0 * radius), (float) (z0Pos * y0Radius), 1f);
			vertexPosInWorld.mul(poseStack.last().pose());
			bufferBuilder.vertex(vertexPosInWorld.x(), vertexPosInWorld.y(), vertexPosInWorld.z()).color(barrierColor).endVertex();
		}
		tesselator.end();
		RenderSystem.disableBlend();
	}

}
