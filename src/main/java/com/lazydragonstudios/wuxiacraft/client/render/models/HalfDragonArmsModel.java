package com.lazydragonstudios.wuxiacraft.client.render.models;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;

public class HalfDragonArmsModel extends AbstractBodyTransformationArmedModel {
	public static final ModelLayerLocation LOCATION = new ModelLayerLocation(new ResourceLocation(WuxiaCraft.MOD_ID, "dragon_transformation_armed"), "main");

	private final ModelPart dragon_tail_1;
	private final ModelPart dragon_tail_2;
	private final ModelPart dragon_tail_3;

	public HalfDragonArmsModel(ModelPart root) {
		this.head = root.getChild("Head");
		this.dragon_tail_1 = root.getChild("DragonTail");
		this.dragon_tail_2 = dragon_tail_1.getChild("DragonTail2");
		this.dragon_tail_3 = dragon_tail_2.getChild("DragonTail3");
		this.rightArm = root.getChild("RightArm");
		this.leftArm = root.getChild("LeftArm");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createMesh() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -6.0F, 4.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(14, 22).addBox(0.0F, -8.0F, -2.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hair_r1 = Head.addOrReplaceChild("hair_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition hair_r2 = Head.addOrReplaceChild("hair_r2", CubeListBuilder.create().texOffs(0, 20).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 3.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition hair_r3 = Head.addOrReplaceChild("hair_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 1.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition hair_r4 = Head.addOrReplaceChild("hair_r4", CubeListBuilder.create().texOffs(28, 0).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 4.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spikes_r1 = Head.addOrReplaceChild("spikes_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -8.0F, 4.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.0F, 0.0F, 0.0F, -0.3054F, 0.0F));

		PartDefinition spikes_r2 = Head.addOrReplaceChild("spikes_r2", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -8.0F, 4.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, 0.0F, 0.0F, 0.3054F, 0.0F));

		PartDefinition DragonTail = partdefinition.addOrReplaceChild("DragonTail", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(14, 26).addBox(0.0F, -4.0F, -1.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 2.0F, -0.6981F, 0.0F, 0.0F));

		PartDefinition DragonTail2 = DragonTail.addOrReplaceChild("DragonTail2", CubeListBuilder.create().texOffs(25, 30).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(14, 29).addBox(0.0F, -3.0F, -1.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition DragonTail3 = DragonTail2.addOrReplaceChild("DragonTail3", CubeListBuilder.create().texOffs(37, 35).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(26, 33).addBox(0.0F, -2.0F, 2.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(31, 30).addBox(-2.0F, 0.0F, 2.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create()
						.texOffs(28, 42).addBox(-3.0F, 3.0F, -2.0F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.25F))
						.texOffs(14, 40).addBox(-6.0F, 3.0F, 0.5F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create()
						.texOffs(28, 42).addBox(1.0F, 3.0F, -2.0F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.25F))
						.texOffs(14, 40).mirror().addBox(3.0F, 3.0F, 0.5F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(5.0F, 2.0F, 0.0F));


		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//previously the render function, render code was moved to a method below
		boolean isFalling = player.getFallFlyingTicks() > 4;
		boolean isSwimming = player.isVisuallySwimming();
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		if (isFalling) {
			this.head.xRot = (-(float) Math.PI / 4F);
		} else if (this.swimAmount > 0.0F) {
			if (isSwimming) {
				this.head.xRot = this.rotLerpRad(this.swimAmount, this.head.xRot, (-(float) Math.PI / 4F));
			} else {
				this.head.xRot = this.rotLerpRad(this.swimAmount, this.head.xRot, headPitch * ((float) Math.PI / 180F));
			}
		} else {
			this.head.xRot = headPitch * ((float) Math.PI / 180F);
		}

		this.rightArm.z = 0.0F;
		this.rightArm.x = -5.0F;
		this.leftArm.z = 0.0F;
		this.leftArm.x = 5.0F;
		float f = 1.0F;
		if (isFalling) {
			f = (float) player.getDeltaMovement().lengthSqr();
			f /= 0.2F;
			f *= f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;
		if (this.riding) {
			this.rightArm.xRot += (-(float) Math.PI / 5F);
			this.leftArm.xRot += (-(float) Math.PI / 5F);
		}

		this.rightArm.yRot = 0.0F;
		this.leftArm.yRot = 0.0F;
		boolean flag2 = player.getMainArm() == HumanoidArm.RIGHT;
		if (player.isUsingItem()) {
			boolean flag3 = player.getUsedItemHand() == InteractionHand.MAIN_HAND;
			if (flag3 == flag2) {
				this.poseRightArm(player);
			} else {
				this.poseLeftArm(player);
			}
		} else {
			boolean flag4 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
			if (flag2 != flag4) {
				this.poseLeftArm(player);
				this.poseRightArm(player);
			} else {
				this.poseRightArm(player);
				this.poseLeftArm(player);
			}
		}

		this.setupAttackAnimation(player);
		if (this.crouching) {
			this.rightArm.xRot += 0.4F;
			this.leftArm.xRot += 0.4F;
			this.head.y = 4.2F;
			this.leftArm.y = 5.2F;
			this.rightArm.y = 5.2F;
			this.dragon_tail_1.y=10.2f;
			this.dragon_tail_1.z=5.5f;
			this.dragon_tail_1.xRot = 0.9f;
			this.dragon_tail_2.xRot = -0.6f;
		} else {
			this.dragon_tail_1.y=9.0f;
			this.dragon_tail_1.z=2f;
			this.dragon_tail_1.xRot = 0f;
			this.dragon_tail_2.xRot = 0f;
			this.head.y = 0.0F;
			this.leftArm.y = 2.0F;
			this.rightArm.y = 2.0F;
		}

		if(player.isVisuallySwimming()) {
			this.dragon_tail_1.xRot += -0.5f;
			this.dragon_tail_2.xRot += -0.5f;
		}

		if (this.rightArmPose != HumanoidModel.ArmPose.SPYGLASS) {
			AnimationUtils.bobModelPart(this.rightArm, ageInTicks, 1.0F);
		}

		if (this.leftArmPose != HumanoidModel.ArmPose.SPYGLASS) {
			AnimationUtils.bobModelPart(this.leftArm, ageInTicks, -1.0F);
		}

		if (this.swimAmount > 0.0F) {
			float f5 = limbSwing % 26.0F;
			HumanoidArm humanoidarm = this.getAttackArm(player);
			float f1 = humanoidarm == HumanoidArm.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
			float f2 = humanoidarm == HumanoidArm.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
			if (!player.isUsingItem()) {
				if (f5 < 14.0F) {
					this.leftArm.xRot = this.rotLerpRad(f2, this.leftArm.xRot, 0.0F);
					this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, 0.0F);
					this.leftArm.yRot = this.rotLerpRad(f2, this.leftArm.yRot, (float) Math.PI);
					this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float) Math.PI);
					this.leftArm.zRot = this.rotLerpRad(f2, this.leftArm.zRot, (float) Math.PI + 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F));
					this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, (float) Math.PI - 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F));
				} else if (f5 >= 14.0F && f5 < 22.0F) {
					float f6 = (f5 - 14.0F) / 8.0F;
					this.leftArm.xRot = this.rotLerpRad(f2, this.leftArm.xRot, ((float) Math.PI / 2F) * f6);
					this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, ((float) Math.PI / 2F) * f6);
					this.leftArm.yRot = this.rotLerpRad(f2, this.leftArm.yRot, (float) Math.PI);
					this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float) Math.PI);
					this.leftArm.zRot = this.rotLerpRad(f2, this.leftArm.zRot, 5.012389F - 1.8707964F * f6);
					this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, 1.2707963F + 1.8707964F * f6);
				} else if (f5 >= 22.0F && f5 < 26.0F) {
					float f3 = (f5 - 22.0F) / 4.0F;
					this.leftArm.xRot = this.rotLerpRad(f2, this.leftArm.xRot, ((float) Math.PI / 2F) - ((float) Math.PI / 2F) * f3);
					this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, ((float) Math.PI / 2F) - ((float) Math.PI / 2F) * f3);
					this.leftArm.yRot = this.rotLerpRad(f2, this.leftArm.yRot, (float) Math.PI);
					this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float) Math.PI);
					this.leftArm.zRot = this.rotLerpRad(f2, this.leftArm.zRot, (float) Math.PI);
					this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, (float) Math.PI);
				}
			}
		}

		this.dragon_tail_1.yRot = Mth.cos(ageInTicks * 0.06f) * 0.1f;
		this.dragon_tail_1.xRot +=  -0.6981F + Mth.abs(Mth.cos(limbSwing * 0.39F)) * 0.6F * limbSwingAmount;
		this.dragon_tail_2.xRot += Mth.cos(1.0f+limbSwing * 0.39F) * 0.6F * limbSwingAmount;
		this.dragon_tail_3.xRot = Mth.cos(2.0f+limbSwing * 0.39F) * 0.6F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		dragon_tail_1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}