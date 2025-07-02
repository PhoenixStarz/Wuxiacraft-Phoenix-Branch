package com.lazydragonstudios.wuxiacraft.client.render.models;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.entity.Snake;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SnakeModel extends EntityModel<Snake> {

	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LOCATION = new ModelLayerLocation(new ResourceLocation(WuxiaCraft.MOD_ID, "snake"), "main");

	private final ModelPart head;

	private final ModelPart neck_1;
	private final ModelPart neck_2;
	private final ModelPart neck_3;
	private final ModelPart neck_4;
	private final ModelPart body_1;
	private final ModelPart body_2;
	private final ModelPart body_3;
	private final ModelPart body_4;
	private final ModelPart body_5;
	private final ModelPart body_6;
	private final ModelPart body_7;

	public SnakeModel(ModelPart root) {
		this.head = root.getChild("head");
		this.neck_1 = this.head.getChild("neck_1");
		this.neck_2 = this.neck_1.getChild("neck_2");
		this.neck_3 = this.neck_2.getChild("neck_3");
		this.neck_4 = this.neck_3.getChild("neck_4");
		this.body_1 = this.neck_4.getChild("body_1");
		this.body_2 = this.body_1.getChild("body_2");
		this.body_3 = this.body_2.getChild("body_3");
		this.body_4 = this.body_3.getChild("body_4");
		this.body_5 = this.body_4.getChild("body_5");
		this.body_6 = this.body_5.getChild("body_6");
		this.body_7 = this.body_6.getChild("body_7");
	}

	public static MeshDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 16.7F, -0.15F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, -1.0958F, -1.9495F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1047F, 0.0F, 0.0F));

		PartDefinition mouth_up = head.addOrReplaceChild("mouth_up", CubeListBuilder.create(), PartPose.offset(0.0F, 0.1F, 1.15F));

		PartDefinition mouth_up_r1 = mouth_up.addOrReplaceChild("mouth_up_r1", CubeListBuilder.create().texOffs(7, 8).addBox(-0.8F, -0.7155F, -2.4163F, 1.7F, 1.0F, 2.6F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.3F, -0.1047F, 0.0F, 0.0F));

		PartDefinition mouth_bottom = head.addOrReplaceChild("mouth_bottom", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 1.15F));

		PartDefinition mouth_bottom_r1 = mouth_bottom.addOrReplaceChild("mouth_bottom_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-0.8F, -0.5133F, -2.4581F, 1.7F, 1.0F, 2.6F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.3F, -0.1047F, 0.0F, 0.0F));

		PartDefinition neck_1 = head.addOrReplaceChild("neck_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.35F));

		PartDefinition neck_1_r1 = neck_1.addOrReplaceChild("neck_1_r1", CubeListBuilder.create().texOffs(8, 3).addBox(-1.0F, -1.1057F, -0.2997F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.7F, -0.8465F, 0.0F, 0.0F));

		PartDefinition neck_2 = neck_1.addOrReplaceChild("neck_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -1.0F));

		PartDefinition neck_2_r1 = neck_2.addOrReplaceChild("neck_2_r1", CubeListBuilder.create().texOffs(8, 3).addBox(-1.0F, -0.9235F, 0.072F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.3F, -1.4923F, 0.0F, 0.0F));

		PartDefinition neck_3 = neck_2.addOrReplaceChild("neck_3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.4F, 0.0F));

		PartDefinition neck_3_r1 = neck_3.addOrReplaceChild("neck_3_r1", CubeListBuilder.create().texOffs(8, 3).addBox(-1.0F, -1.1836F, -0.1007F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.3F, -2.042F, 0.0F, 0.0F));

		PartDefinition neck_4 = neck_3.addOrReplaceChild("neck_4", CubeListBuilder.create(), PartPose.offset(0.0F, 1.4F, 0.6F));

		PartDefinition neck_4_r1 = neck_4.addOrReplaceChild("neck_4_r1", CubeListBuilder.create().texOffs(8, 3).addBox(-1.0F, -1.05F, -0.15F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.1F, -1.5708F, 0.0F, 0.0F));

		PartDefinition body_1 = neck_4.addOrReplaceChild("body_1", CubeListBuilder.create(), PartPose.offset(0.0F, 1.3F, 0.1F));

		PartDefinition body_1_r1 = body_1.addOrReplaceChild("body_1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0257F, -0.1559F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.1F, -0.7854F, 0.0F, 0.0F));

		PartDefinition body_2 = body_1.addOrReplaceChild("body_2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.1F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.7F, 1.1F));

		PartDefinition body_3 = body_2.addOrReplaceChild("body_3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.1F, 0.1F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1F, 2.8F));

		PartDefinition body_4 = body_3.addOrReplaceChild("body_4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -0.2F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -0.1F, 3.1F));

		PartDefinition body_5 = body_4.addOrReplaceChild("body_5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 2.7F));

		PartDefinition body_6 = body_5.addOrReplaceChild("body_6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -0.4F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 0.0F, 2.2F));

		PartDefinition body_7 = body_6.addOrReplaceChild("body_7", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -0.6F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.0F, 2.2F));

		return meshdefinition;
	}

	@Override
	public void setupAnim(Snake entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = 1.0F;
		this.body_1.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		this.body_2.yRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
		this.body_3.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		this.body_4.yRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
		this.body_5.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		this.body_6.yRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
		this.body_7.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}
