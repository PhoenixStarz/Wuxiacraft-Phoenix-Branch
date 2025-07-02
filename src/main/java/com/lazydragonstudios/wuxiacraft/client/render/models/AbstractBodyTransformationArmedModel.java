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
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public abstract class AbstractBodyTransformationArmedModel extends AbstractBodyTransformationModel {

	protected float swimAmount;

	protected ModelPart rightArm;
	protected ModelPart leftArm;

	protected ModelPart head;

	public boolean crouching;

	public HumanoidModel.ArmPose leftArmPose = HumanoidModel.ArmPose.EMPTY;
	public HumanoidModel.ArmPose rightArmPose = HumanoidModel.ArmPose.EMPTY;

	@Override
	public void prepareMobModel(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick) {
		this.swimAmount = player.getSwimAmount(partialTick);
		this.crouching = player.isCrouching();
		if(player.getMainArm() == HumanoidArm.LEFT) {
			this.leftArmPose = getArmPose(player, InteractionHand.MAIN_HAND);
			this.rightArmPose = getArmPose(player, InteractionHand.OFF_HAND);
		} else {
			this.rightArmPose = getArmPose(player, InteractionHand.MAIN_HAND);
			this.leftArmPose = getArmPose(player, InteractionHand.OFF_HAND);
		}
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

	protected ModelPart getArm(HumanoidArm arm) {
		return arm == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
	}

	public ModelPart getHead() {
		return this.head;
	}

	protected HumanoidArm getAttackArm(Player player) {
		HumanoidArm humanoidarm = player.getMainArm();
		return player.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
	}

	public static HumanoidModel.ArmPose getArmPose(AbstractClientPlayer pPlayer, InteractionHand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if (itemstack.isEmpty()) {
			return HumanoidModel.ArmPose.EMPTY;
		} else {
			if (pPlayer.getUsedItemHand() == pHand && pPlayer.getUseItemRemainingTicks() > 0) {
				UseAnim useanim = itemstack.getUseAnimation();
				if (useanim == UseAnim.BLOCK) {
					return HumanoidModel.ArmPose.BLOCK;
				}

				if (useanim == UseAnim.BOW) {
					return HumanoidModel.ArmPose.BOW_AND_ARROW;
				}

				if (useanim == UseAnim.SPEAR) {
					return HumanoidModel.ArmPose.THROW_SPEAR;
				}

				if (useanim == UseAnim.CROSSBOW && pHand == pPlayer.getUsedItemHand()) {
					return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
				}

				if (useanim == UseAnim.SPYGLASS) {
					return HumanoidModel.ArmPose.SPYGLASS;
				}

				if (useanim == UseAnim.TOOT_HORN) {
					return HumanoidModel.ArmPose.TOOT_HORN;
				}

				if (useanim == UseAnim.BRUSH) {
					return HumanoidModel.ArmPose.BRUSH;
				}
			} else if (!pPlayer.swinging && itemstack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack)) {
				return HumanoidModel.ArmPose.CROSSBOW_HOLD;
			}

			HumanoidModel.ArmPose forgeArmPose = net.minecraftforge.client.extensions.common.IClientItemExtensions.of(itemstack).getArmPose(pPlayer, pHand, itemstack);
			if (forgeArmPose != null) return forgeArmPose;

			return HumanoidModel.ArmPose.ITEM;
		}
	}

	protected void poseRightArm(Player player) {
		switch (this.rightArmPose) {
			case EMPTY -> this.rightArm.yRot = 0.0F;
			case BLOCK -> {
				this.rightArm.xRot = this.rightArm.xRot * 0.5F - 0.9424779F;
				this.rightArm.yRot = (-(float) Math.PI / 6F);
			}
			case ITEM -> {
				this.rightArm.xRot = this.rightArm.xRot * 0.5F - ((float) Math.PI / 10F);
				this.rightArm.yRot = 0.0F;
			}
			case THROW_SPEAR -> {
				this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) Math.PI;
				this.rightArm.yRot = 0.0F;
			}
			case BOW_AND_ARROW -> {
				this.rightArm.yRot = -0.1F + this.head.yRot;
				this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
				this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
				this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
			}
			case CROSSBOW_CHARGE -> AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, player, true);
			case CROSSBOW_HOLD -> AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
			case SPYGLASS -> {
				this.rightArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - (player.isCrouching() ? 0.2617994F : 0.0F), -2.4F, 3.3F);
				this.rightArm.yRot = this.head.yRot - 0.2617994F;
			}
		}

	}

	protected void poseLeftArm(Player player) {
		switch (this.leftArmPose) {
			case EMPTY -> this.leftArm.yRot = 0.0F;
			case BLOCK -> {
				this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.9424779F;
				this.leftArm.yRot = ((float) Math.PI / 6F);
			}
			case ITEM -> {
				this.leftArm.xRot = this.leftArm.xRot * 0.5F - ((float) Math.PI / 10F);
				this.leftArm.yRot = 0.0F;
			}
			case THROW_SPEAR -> {
				this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) Math.PI;
				this.leftArm.yRot = 0.0F;
			}
			case BOW_AND_ARROW -> {
				this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
				this.leftArm.yRot = 0.1F + this.head.yRot;
				this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
				this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
			}
			case CROSSBOW_CHARGE -> AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, player, false);
			case CROSSBOW_HOLD -> AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, false);
			case SPYGLASS -> {
				this.leftArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - (player.isCrouching() ? 0.2617994F : 0.0F), -2.4F, 3.3F);
				this.leftArm.yRot = this.head.yRot + 0.2617994F;
			}
		}
	}


	protected void setupAttackAnimation(Player player) {
		if (!(this.attackTime <= 0.0F)) {
			HumanoidArm humanoidarm = this.getAttackArm(player);
			ModelPart modelpart = this.getArm(humanoidarm);
			float f = this.attackTime;
			var yRot = Mth.sin(Mth.sqrt(f) * ((float) Math.PI * 2F)) * 0.2F;
			if (humanoidarm == HumanoidArm.LEFT) {
				yRot *= -1.0F;
			}

			this.rightArm.z = Mth.sin(yRot) * 5.0F;
			this.rightArm.x = -Mth.cos(yRot) * 5.0F;
			this.leftArm.z = -Mth.sin(yRot) * 5.0F;
			this.leftArm.x = Mth.cos(yRot) * 5.0F;
			this.rightArm.yRot += yRot;
			this.leftArm.yRot += yRot;
			//noinspection SuspiciousNameCombination
			this.leftArm.xRot += yRot;
			f = 1.0F - this.attackTime;
			f *= f;
			f *= f;
			f = 1.0F - f;
			float f1 = Mth.sin(f * (float) Math.PI);
			float f2 = Mth.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
			modelpart.xRot = (float) ((double) modelpart.xRot - ((double) f1 * 1.2D + (double) f2));
			modelpart.yRot += yRot * 2.0F;
			modelpart.zRot += Mth.sin(this.attackTime * (float) Math.PI) * -0.4F;
		}
	}


	protected float quadraticArmUpdate(float limbSwing) {
		return -65.0F * limbSwing + limbSwing * limbSwing;
	}
}
