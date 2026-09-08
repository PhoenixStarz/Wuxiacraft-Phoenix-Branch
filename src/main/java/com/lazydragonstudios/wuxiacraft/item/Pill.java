package com.lazydragonstudios.wuxiacraft.item;

import com.lazydragonstudios.wuxiacraft.effects.WuxiaEffect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaMobEffects;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Pill extends Item {

	private final int strength;

	public Pill(Properties properties, int strength) {
		super(properties);
		this.strength = strength;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
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
			if (player.hasEffect(WuxiaMobEffects.PILL_RESONANCE.get())) {
				var effectInstance = player.getEffect(WuxiaMobEffects.PILL_RESONANCE.get());
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
			player.addEffect(new MobEffectInstance(WuxiaMobEffects.PILL_RESONANCE.get(), duration, this.strength, true, true, false));
		}
		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 10;
	}
}
