package com.lazydragonstudios.wuxiacraft.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public class MixinMinecraft {

	@Shadow
	protected int missTime;

	@Final
	@Shadow
	private static Logger LOGGER;

	@Shadow
	public HitResult hitResult;

	@Shadow
	public LocalPlayer player;

	@Shadow
	public MultiPlayerGameMode gameMode;

	@Shadow
	public ClientLevel level;

	@Final
	@Shadow
	public Options options;

	private boolean startAttack() {
		if (this.missTime > 0) {
			return false;
		} else if (this.hitResult == null) {
			LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
			if (this.gameMode.hasMissTime()) {
				this.missTime = 10;
			}

			return false;
		} else if (this.player.isHandsBusy()) {
			return false;
		} else {
			ItemStack itemstack = this.player.getItemInHand(InteractionHand.MAIN_HAND);
			if (!itemstack.isItemEnabled(this.level.enabledFeatures())) {
				return false;
			} else {
				boolean flag = false;
				var inputEvent = net.minecraftforge.client.ForgeHooksClient.onClickInput(0, this.options.keyAttack, InteractionHand.MAIN_HAND);
				if (!inputEvent.isCanceled())
					switch (this.hitResult.getType()) {
						case ENTITY:
							this.gameMode.attack(this.player, ((EntityHitResult) this.hitResult).getEntity());
							break;
						case BLOCK:
							BlockHitResult blockhitresult = (BlockHitResult) this.hitResult;
							BlockPos blockpos = blockhitresult.getBlockPos();
							if (!this.level.isEmptyBlock(blockpos)) {
								this.gameMode.startDestroyBlock(blockpos, blockhitresult.getDirection());
								if (this.level.getBlockState(blockpos).isAir()) {
									flag = true;
								}
								break;
							}
						case MISS:
							if (this.gameMode.hasMissTime()) {
								this.missTime = 10;
							}

							net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(this.player);
							this.player.resetAttackStrengthTicker();
					}

				if (inputEvent.shouldSwingHand())
					this.player.swing(InteractionHand.MAIN_HAND);
				return flag;
			}
		}
	}

}
