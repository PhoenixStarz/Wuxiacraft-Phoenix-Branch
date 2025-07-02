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
public class KitsuneOneTailedModel extends AbstractBodyTransformationModel {

	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LOCATION = new ModelLayerLocation(new ResourceLocation(WuxiaCraft.MOD_ID, "kitsune_1_tail"), "main");

	private final ModelPart tail_root;

	private final ModelPart tail_2;

	private final ModelPart tail_3;

	private final ModelPart tail_4;

	public KitsuneOneTailedModel(ModelPart root) {
		this.tail_root = root.getChild("tail_root");
		this.tail_2 = this.tail_root.getChild("tail_2");
		this.tail_3 = this.tail_2.getChild("tail_3");
		this.tail_4 = this.tail_3.getChild("tail_4");
	}

	public static LayerDefinition createMesh() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail_root = partdefinition.addOrReplaceChild("tail_root", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 2.0F));

		PartDefinition tail_2 = tail_root.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition tail_4 = tail_3.addOrReplaceChild("tail_4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition cube_r1 = tail_4.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 10).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		tail_root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(AbstractClientPlayer pEntity, float limbSwing, float limbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		tail_root.xRot = 0.48f - Mth.abs(Mth.cos(limbSwing * 0.33F)) * 0.3F * limbSwingAmount;
		tail_2.xRot = 0.48f - Mth.abs(Mth.cos(limbSwing * 0.44F)) * 0.7F * limbSwingAmount;
		tail_2.yRot = Mth.cos(pAgeInTicks * 0.03F) * 0.3F + Mth.cos(limbSwing * 0.44F)*0.3f*limbSwingAmount;
		tail_3.xRot = 0.15f - Mth.abs(Mth.cos(limbSwing * 0.26F)) * 0.4F * limbSwingAmount;
		tail_3.yRot = Mth.cos(pAgeInTicks * 0.03F) * 0.1F;
		tail_4.xRot = -0.15f + Mth.cos(0.75f+limbSwing * 0.66F) * 0.7F * limbSwingAmount;
		tail_4.yRot = Mth.cos(1.5f+pAgeInTicks * 0.03F) * 0.1F;
	}
}