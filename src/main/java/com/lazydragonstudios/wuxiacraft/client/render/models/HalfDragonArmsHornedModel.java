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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;

public class HalfDragonArmsHornedModel extends HalfDragonArmsModel {
	public static final ModelLayerLocation LOCATION = new ModelLayerLocation(new ResourceLocation(WuxiaCraft.MOD_ID, "dragon_transformation_horned"), "main");

	public HalfDragonArmsHornedModel(ModelPart root) {
		super(root);
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

		PartDefinition RightHorn = Head.addOrReplaceChild("RightHorn", CubeListBuilder.create().texOffs(40, 41).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -6.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		PartDefinition horn_r1 = RightHorn.addOrReplaceChild("horn_r1", CubeListBuilder.create().texOffs(40, 41).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition LeftHorn = Head.addOrReplaceChild("LeftHorn", CubeListBuilder.create().texOffs(40, 41).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -6.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		PartDefinition horn_r2 = LeftHorn.addOrReplaceChild("horn_r2", CubeListBuilder.create().texOffs(40, 41).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

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

}