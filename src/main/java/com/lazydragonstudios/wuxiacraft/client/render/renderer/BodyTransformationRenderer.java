package com.lazydragonstudios.wuxiacraft.client.render.renderer;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.client.render.models.AbstractBodyTransformationModel;
import com.lazydragonstudios.wuxiacraft.cultivation.BodyCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.init.WuxiaEntities;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BodyTransformationRenderer extends LivingEntityRenderer<AbstractClientPlayer, AbstractBodyTransformationModel> {

	public BodyTransformationRenderer(EntityRendererProvider.Context pContext, AbstractBodyTransformationModel model) {
		super(pContext, model, 0.5f);
	}

	@Override
	public void render(AbstractClientPlayer pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
		pPoseStack.pushPose();
		this.model.attackTime = this.getAttackAnim(pEntity, pPartialTicks);

		boolean shouldSit = pEntity.isPassenger() && (pEntity.getVehicle() != null && pEntity.getVehicle().shouldRiderSit());
		this.model.riding = shouldSit;
		this.model.young = pEntity.isBaby();
		float f = Mth.rotLerp(pPartialTicks, pEntity.yBodyRotO, pEntity.yBodyRot);
		float f1 = Mth.rotLerp(pPartialTicks, pEntity.yHeadRotO, pEntity.yHeadRot);
		float f2 = f1 - f;
		if (shouldSit && pEntity.getVehicle() instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity) pEntity.getVehicle();
			f = Mth.rotLerp(pPartialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
			f2 = f1 - f;
			float f3 = Mth.wrapDegrees(f2);
			if (f3 < -85.0F) {
				f3 = -85.0F;
			}

			if (f3 >= 85.0F) {
				f3 = 85.0F;
			}

			f = f1 - f3;
			if (f3 * f3 > 2500.0F) {
				f += f3 * 0.2F;
			}

			f2 = f1 - f;
		}

		float f6 = Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot());
		if (isEntityUpsideDown(pEntity)) {
			f6 *= -1.0F;
			f2 *= -1.0F;
		}

		if (pEntity.hasPose(Pose.SLEEPING)) {
			Direction direction = pEntity.getBedOrientation();
			if (direction != null) {
				float f4 = pEntity.getEyeHeight(Pose.STANDING) - 0.1F;
				pPoseStack.translate((float) (-direction.getStepX()) * f4, 0.0F, (float) (-direction.getStepZ()) * f4);
			}
		}

		float f7 = this.getBob(pEntity, pPartialTicks);
		this.setupRotations(pEntity, pPoseStack, f7, f, pPartialTicks);
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		this.scale(pEntity, pPoseStack, pPartialTicks);
		pPoseStack.translate(0.0F, -1.501F, 0.0F);
		float f8 = 0.0F;
		float f5 = 0.0F;
		if (!shouldSit && pEntity.isAlive()) {
			f8 = pEntity.walkAnimation.speed(pPartialTicks);
			f5 = pEntity.walkAnimation.position(pPartialTicks);
			if (pEntity.isBaby()) {
				f5 *= 3.0F;
			}

			if (f8 > 1.0F) {
				f8 = 1.0F;
			}
		}

		this.model.prepareMobModel(pEntity, f5, f8, pPartialTicks);
		this.model.setupAnim(pEntity, f5, f8, f7, f2, f6);
		Minecraft minecraft = Minecraft.getInstance();
		boolean flag = this.isBodyVisible(pEntity);
		boolean flag1 = !flag && !pEntity.isInvisibleTo(minecraft.player);
		boolean flag2 = minecraft.shouldEntityAppearGlowing(pEntity);
		RenderType rendertype = this.getRenderType(pEntity, flag, flag1, flag2);
		if (rendertype != null) {
			VertexConsumer vertexconsumer = pBuffer.getBuffer(rendertype);
			int i = getOverlayCoords(pEntity, this.getWhiteOverlayProgress(pEntity, pPartialTicks));
			this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
		}

		if (!pEntity.isSpectator()) {
			for (RenderLayer<AbstractClientPlayer, AbstractBodyTransformationModel> renderlayer : this.layers) {
				renderlayer.render(pPoseStack, pBuffer, pPackedLight, pEntity, f5, f8, pPartialTicks, f7, f2, f6);
			}
		}

		pPoseStack.popPose();
	}

	@Override
	@Nonnull
	public ResourceLocation getTextureLocation(AbstractClientPlayer pEntity) {
		var cultivation = Cultivation.get(pEntity);
		var transformation = ((BodyCultivationContainer) cultivation.getSystemData(System.BODY)).getDisplayBodyTransformation();
		if (transformation == null) return new ResourceLocation(WuxiaCraft.MOD_ID, "none");
		if (transformation.equals(WuxiaEntities.KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY.getId())
		)
			return new ResourceLocation(WuxiaCraft.MOD_ID, "textures/entity/transformation/kitsune_tail_texture.png");
		if (transformation.equals(WuxiaEntities.SPATIAL_KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.SPATIAL_KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.SPATIAL_KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.SPATIAL_KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.SPATIAL_KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.SPATIAL_KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.SPATIAL_KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.SPATIAL_KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.SPATIAL_KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY.getId())
		)
			return new ResourceLocation(WuxiaCraft.MOD_ID, "textures/entity/transformation/spatial_kitsune_tail_texture.png");
		if (transformation.equals(WuxiaEntities.HALF_DRAGON_BODY_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.HALF_DRAGON_BODY_ARMED_TRANSFORMATION_ENTITY.getId()) ||
				transformation.equals(WuxiaEntities.HALF_DRAGON_BODY_HORNED_TRANSFORMATION_ENTITY.getId()))
			return new ResourceLocation(WuxiaCraft.MOD_ID, "textures/entity/transformation/dragon_transformation.png");
		return new ResourceLocation(WuxiaCraft.MOD_ID, "none");
	}


	@Override
	protected void setupRotations(AbstractClientPlayer pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
		float f = pEntityLiving.getSwimAmount(pPartialTicks);
		if (pEntityLiving.isFallFlying()) {
			super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
			float f1 = (float)pEntityLiving.getFallFlyingTicks() + pPartialTicks;
			float f2 = Mth.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
			if (!pEntityLiving.isAutoSpinAttack()) {
				pPoseStack.mulPose(Axis.XP.rotationDegrees(f2 * (-90.0F - pEntityLiving.getXRot())));
			}

			Vec3 vec3 = pEntityLiving.getViewVector(pPartialTicks);
			Vec3 vec31 = pEntityLiving.getDeltaMovementLerped(pPartialTicks);
			double d0 = vec31.horizontalDistanceSqr();
			double d1 = vec3.horizontalDistanceSqr();
			if (d0 > 0.0D && d1 > 0.0D) {
				double d2 = (vec31.x * vec3.x + vec31.z * vec3.z) / Math.sqrt(d0 * d1);
				double d3 = vec31.x * vec3.z - vec31.z * vec3.x;
				pPoseStack.mulPose(Axis.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
			}
		} else if (f > 0.0F) {
			super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
			float f3 = pEntityLiving.isInWater() || pEntityLiving.isInFluidType((fluidType, height) -> pEntityLiving.canSwimInFluidType(fluidType)) ? -90.0F - pEntityLiving.getXRot() : -90.0F;
			float f4 = Mth.lerp(f, 0.0F, f3);
			pPoseStack.mulPose(Axis.XP.rotationDegrees(f4));
			if (pEntityLiving.isVisuallySwimming()) {
				pPoseStack.translate(0.0F, -1.0F, 0.3F);
			}
		} else {
			super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
		}

	}
}
