package com.lazydragonstudios.wuxiacraft.client.render.models;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractBodyTransformationModel extends EntityModel<AbstractClientPlayer> {

	protected float swimAmount;

	@Override
	public void prepareMobModel(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick) {
		this.swimAmount = player.getSwimAmount(partialTick);
		super.prepareMobModel(player, limbSwing, limbSwingAmount, partialTick);
	}

	protected float rotLerpRad(float angleIn, float maxAngleIn, float mulIn) {
		float f = (mulIn - maxAngleIn) % ((float) Math.PI * 2F);
		if (f < -(float) Math.PI) {
			f += ((float) Math.PI * 2F);
		}

		if (f >= (float) Math.PI) {
			f -= ((float) Math.PI * 2F);
		}

		return maxAngleIn + angleIn * f;
	}

	protected float quadraticArmUpdate(float limbSwing) {
		return -65.0F * limbSwing + limbSwing * limbSwing;
	}
}
