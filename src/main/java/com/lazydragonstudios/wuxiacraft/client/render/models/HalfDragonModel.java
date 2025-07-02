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

public class HalfDragonModel extends AbstractBodyTransformationModel {
	public static final ModelLayerLocation LOCATION = new ModelLayerLocation(new ResourceLocation(WuxiaCraft.MOD_ID, "dragon_transformation"), "main");

	private final ModelPart head;

	private final ModelPart dragon_tail_1;
	private final ModelPart dragon_tail_2;
	private final ModelPart dragon_tail_3;

	public HalfDragonModel(ModelPart root) {
		this.head = root.getChild("Head");
		this.dragon_tail_1 = root.getChild("DragonTail");
		this.dragon_tail_2 = dragon_tail_1.getChild("DragonTail2");
		this.dragon_tail_3 = dragon_tail_2.getChild("DragonTail3");
	}

	public static LayerDefinition createMesh() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create()
				.texOffs(0, 16).addBox(0.0F, -6.0F, 4.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(14, 22).addBox(0.0F, -8.0F, -2.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hair_r1 = Head.addOrReplaceChild("hair_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition hair_r2 = Head.addOrReplaceChild("hair_r2", CubeListBuilder.create().texOffs(0, 20).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 3.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition hair_r3 = Head.addOrReplaceChild("hair_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 1.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition hair_r4 = Head.addOrReplaceChild("hair_r4", CubeListBuilder.create().texOffs(28, 0).addBox(-7.0F, 0.0F, -1.0F, 14.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 4.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spikes_r1 = Head.addOrReplaceChild("spikes_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -8.0F, 4.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.0F, 0.0F, 0.0F, -0.3054F, 0.0F));

		PartDefinition spikes_r2 = Head.addOrReplaceChild("spikes_r2", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -8.0F, 4.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, 0.0F, 0.0F, 0.3054F, 0.0F));

		PartDefinition DragonTail = partdefinition.addOrReplaceChild("DragonTail", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(14, 26).addBox(0.0F, -4.0F, -1.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 1.0F, -0.6981F, 0.0F, 0.0F));

		PartDefinition DragonTail2 = DragonTail.addOrReplaceChild("DragonTail2", CubeListBuilder.create().texOffs(25, 30).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(14, 29).addBox(0.0F, -3.0F, -1.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition DragonTail3 = DragonTail2.addOrReplaceChild("DragonTail3", CubeListBuilder.create().texOffs(37, 35).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(26, 33).addBox(0.0F, -2.0F, 2.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(31, 30).addBox(-2.0F, 0.0F, 2.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

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

		if (player.isCrouching()) {
			this.head.y = 4.2F;
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
		}

		if(player.isVisuallySwimming()) {
			this.dragon_tail_1.xRot += -0.5f;
			this.dragon_tail_2.xRot += -0.5f;
		}
		this.dragon_tail_1.yRot =Mth.cos(ageInTicks * 0.06f) * 0.1f;
		this.dragon_tail_1.xRot +=  -0.6981F + Mth.abs(Mth.cos(limbSwing * 0.39F)) * 0.6F * limbSwingAmount;
		this.dragon_tail_2.xRot += Mth.cos(1.0f+limbSwing * 0.39F) * 0.6F * limbSwingAmount;
		this.dragon_tail_3.xRot = Mth.cos(2.0f+limbSwing * 0.39F) * 0.6F * limbSwingAmount;

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		dragon_tail_1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}