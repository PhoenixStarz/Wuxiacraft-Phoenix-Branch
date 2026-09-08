package com.lazydragonstudios.wuxiacraft.item;

import com.lazydragonstudios.wuxiacraft.effects.WuxiaEffect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaMobEffects;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SpiritFruit extends Item {

	private final int strength;
	private final Block block;

	public SpiritFruit(Block block, Properties properties, int strength) {
		super(properties);
		this.block = block;
		this.strength = strength;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		BlockHitResult hitResult = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos clickedPos = hitResult.getBlockPos();
			BlockPos bushPos = clickedPos.relative(hitResult.getDirection());

			BlockState bushState = this.block.defaultBlockState();

			// Check whether the bush can survive there
			if (level.getBlockState(bushPos).isAir()
					&& bushState.canSurvive(level, bushPos)) {
				if (!level.isClientSide) {
					level.setBlock(bushPos, bushState, Block.UPDATE_ALL);
					itemstack.shrink(1);
				}
				level.playSound(player, bushPos, SoundEvents.SWEET_BERRY_BUSH_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
				return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
			}
		}
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.EAT;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			if (!player.isCreative())stack.shrink(1);
			int duration = 120 * 20;
			if (player.hasEffect(WuxiaMobEffects.ENLIGHTENMENT.get())) {
				var effectInstance = player.getEffect(WuxiaMobEffects.ENLIGHTENMENT.get());
				if (effectInstance != null) {
					if (effectInstance.getAmplifier() == this.strength) {
						duration = effectInstance.getDuration();
						if (duration >= 12000) {
							duration += 600;
						} else if (duration >= 2400) {
							duration += 1200;
						} else {
							duration += 2400;
						}
					}
				}
			}
			player.addEffect(new MobEffectInstance(WuxiaMobEffects.ENLIGHTENMENT.get(), duration, this.strength, true, true, false));
		}
		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 10;
	}
}
