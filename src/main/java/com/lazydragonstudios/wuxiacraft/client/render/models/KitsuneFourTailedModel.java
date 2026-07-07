package com.lazydragonstudios.wuxiacraft.client.render.models;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class KitsuneFourTailedModel extends AbstractBodyTransformationModel {

	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LOCATION = new ModelLayerLocation(new ResourceLocation(WuxiaCraft.MOD_ID, "kitsune_4_tail"), "main");

	private final ModelPart tail_root;

	private final ModelPart head;

	private final ModelPart tail_start_1;

	private final ModelPart tail_middle_1;

	private final ModelPart tail_end_1;

	private final ModelPart tail_start_2;

	private final ModelPart tail_middle_2;

	private final ModelPart tail_end_2;

	private final ModelPart tail_start_3;

	private final ModelPart tail_middle_3;

	private final ModelPart tail_end_3;

	private final ModelPart tail_start_4;

	private final ModelPart tail_middle_4;

	private final ModelPart tail_end_4;

	public KitsuneFourTailedModel(ModelPart root) {
		this.head = root.getChild("head");
		this.tail_root = root.getChild("tail_root");
		this.tail_start_1 = this.tail_root.getChild("tail_start_1");
		this.tail_middle_1 = this.tail_start_1.getChild("tail_middle_1");
		this.tail_end_1 = this.tail_middle_1.getChild("tail_end_1");
		this.tail_start_2 = this.tail_root.getChild("tail_start_2");
		this.tail_middle_2 = this.tail_start_2.getChild("tail_middle_2");
		this.tail_end_2 = this.tail_middle_2.getChild("tail_end_2");
		this.tail_start_3 = this.tail_root.getChild("tail_start_3");
		this.tail_middle_3 = this.tail_start_3.getChild("tail_middle_3");
		this.tail_end_3 = this.tail_middle_3.getChild("tail_end_3");
		this.tail_start_4 = this.tail_root.getChild("tail_start_4");
		this.tail_middle_4 = this.tail_start_4.getChild("tail_middle_4");
		this.tail_end_4 = this.tail_middle_4.getChild("tail_end_4");
	}

	public static LayerDefinition createMesh() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail_root = partdefinition.addOrReplaceChild("tail_root", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 2.0F));

		PartDefinition tail_start_1 = tail_root.addOrReplaceChild("tail_start_1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.2618F, 0.5236F, 0.0F));

		PartDefinition tail_middle_1 = tail_start_1.addOrReplaceChild("tail_middle_1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition tail_end_1 = tail_middle_1.addOrReplaceChild("tail_end_1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition cube_r1 = tail_end_1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 10).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition tail_start_2 = tail_root.addOrReplaceChild("tail_start_2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.2618F, -0.5236F, 0.0F));

		PartDefinition tail_middle_2 = tail_start_2.addOrReplaceChild("tail_middle_2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition tail_end_2 = tail_middle_2.addOrReplaceChild("tail_end_2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition cube_r2 = tail_end_2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(4, 10).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition tail_start_3 = tail_root.addOrReplaceChild("tail_start_3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.5236F, -0.5236F, 0.0F));

		PartDefinition tail_middle_3 = tail_start_3.addOrReplaceChild("tail_middle_3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition tail_end_3 = tail_middle_3.addOrReplaceChild("tail_end_3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition cube_r3 = tail_end_3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(4, 10).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition tail_start_4 = tail_root.addOrReplaceChild("tail_start_4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.5236F, 0.5236F, 0.0F));

		PartDefinition tail_middle_4 = tail_start_4.addOrReplaceChild("tail_middle_4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition tail_end_4 = tail_middle_4.addOrReplaceChild("tail_end_4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition cube_r4 = tail_end_4.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(4, 10).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(10, 1).addBox(-4.0F, -8.0F, 1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(8, 8).addBox(-3.5F, -9.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(8, 8).addBox(2.5F, -9.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(10, 1).addBox(2.0F, -8.0F, 1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		tail_root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(AbstractClientPlayer pEntity, float limbSwing, float limbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		//previously the render function, render code was moved to a method below
		boolean isFalling = pEntity.getFallFlyingTicks() > 4;
		boolean isSwimming = pEntity.isVisuallySwimming();
		this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
		if (isFalling) {
			this.head.xRot = (-(float) Math.PI / 4F);
		} else if (this.swimAmount > 0.0F) {
			if (isSwimming) {
				this.head.xRot = this.rotLerpRad(this.swimAmount, this.head.xRot, (-(float) Math.PI / 4F));
			} else {
				this.head.xRot = this.rotLerpRad(this.swimAmount, this.head.xRot, pHeadPitch * ((float) Math.PI / 180F));
			}
		} else {
			this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
		}
		if (pEntity.isCrouching()) {
			this.head.y = 4.2F;
		} else {
			this.head.y = 0.0F;
		}
		
		tail_root.xRot = -Mth.abs(Mth.cos(limbSwing * 0.33F)) * 0.3F * limbSwingAmount;
		tail_start_1.xRot = -0.2618F + 0.48f - Mth.abs(Mth.cos(limbSwing * 0.44F)) * 0.7F * limbSwingAmount;
		tail_start_1.yRot = 0.5236F + Mth.cos(pAgeInTicks * 0.03F) * 0.2F + Mth.cos(limbSwing * 0.44F) * 0.3f * limbSwingAmount;
		tail_middle_1.xRot = 0.15f - Mth.abs(Mth.cos(limbSwing * 0.26F)) * 0.4F * limbSwingAmount;
		tail_middle_1.yRot = Mth.cos(pAgeInTicks * 0.03F) * 0.1F;
		tail_end_1.xRot = -0.15f + Mth.cos(0.75f + limbSwing * 0.66F) * 0.7F * limbSwingAmount;
		tail_end_1.yRot = Mth.cos(1.5f + pAgeInTicks * 0.03F) * 0.1F;
		float phaseIncrement = 24.0f;
		pAgeInTicks += phaseIncrement;
		tail_start_2.xRot = -0.2618F + 0.48f - Mth.abs(Mth.cos(limbSwing * 0.44F)) * 0.7F * limbSwingAmount;
		tail_start_2.yRot = -0.5236F + Mth.cos(pAgeInTicks * 0.03F) * 0.2F + Mth.cos(limbSwing * 0.44F) * 0.3f * limbSwingAmount;
		tail_middle_2.xRot = 0.15f - Mth.abs(Mth.cos(limbSwing * 0.26F)) * 0.4F * limbSwingAmount;
		tail_middle_2.yRot = Mth.cos(pAgeInTicks * 0.03F) * 0.1F;
		tail_end_2.xRot = -0.15f + Mth.cos(0.75f + limbSwing * 0.66F) * 0.7F * limbSwingAmount;
		tail_end_2.yRot = Mth.cos(1.5f + pAgeInTicks * 0.03F) * 0.1F;
		pAgeInTicks += phaseIncrement;
		tail_start_3.xRot = 0.5236F + 0.48f - Mth.abs(Mth.cos(limbSwing * 0.44F)) * 0.7F * limbSwingAmount;
		tail_start_3.yRot = -0.5236F + Mth.cos(pAgeInTicks * 0.03F) * 0.2F + Mth.cos(limbSwing * 0.44F) * 0.3f * limbSwingAmount;
		tail_middle_3.xRot = 0.15f - Mth.abs(Mth.cos(limbSwing * 0.26F)) * 0.4F * limbSwingAmount;
		tail_middle_3.yRot = Mth.cos(pAgeInTicks * 0.03F) * 0.1F;
		tail_end_3.xRot = -0.15f + Mth.cos(0.75f + limbSwing * 0.66F) * 0.7F * limbSwingAmount;
		tail_end_3.yRot = Mth.cos(1.5f + pAgeInTicks * 0.03F) * 0.1F;
		pAgeInTicks += phaseIncrement;
		tail_start_4.xRot = 0.5236F + 0.48f - Mth.abs(Mth.cos(limbSwing * 0.44F)) * 0.7F * limbSwingAmount;
		tail_start_4.yRot = 0.5236F + Mth.cos(pAgeInTicks * 0.03F) * 0.2F + Mth.cos(limbSwing * 0.44F) * 0.3f * limbSwingAmount;
		tail_middle_4.xRot = 0.15f - Mth.abs(Mth.cos(limbSwing * 0.26F)) * 0.4F * limbSwingAmount;
		tail_middle_4.yRot = Mth.cos(pAgeInTicks * 0.03F) * 0.1F;
		tail_end_4.xRot = -0.15f + Mth.cos(0.75f + limbSwing * 0.66F) * 0.7F * limbSwingAmount;
		tail_end_4.yRot = Mth.cos(1.5f + pAgeInTicks * 0.03F) * 0.1F;
	}
}