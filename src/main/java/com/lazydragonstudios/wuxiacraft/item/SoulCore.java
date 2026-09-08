package com.lazydragonstudios.wuxiacraft.item;

import com.lazydragonstudios.wuxiacraft.cultivation.BodyCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SoulCore extends Item {

	public SoulCore(Properties properties) {
		super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant().setNoRepair());
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			ICultivation cultivation = Cultivation.get(player);
			CompoundTag itemTag = stack.getTag();
			if (itemTag == null) return stack;
			if (!player.isCreative()) {
				if (itemTag.contains("durability")) {
					itemTag.putInt("durability", itemTag.getInt("durability")-1);
					if (itemTag.getInt("durability") <= 0)
					stack.shrink(1);
					cultivation.setStat(PlayerStat.LIVES, cultivation.getStat(PlayerStat.LIVES).add(BigDecimal.ONE).min(cultivation.getStat(PlayerStat.MAX_LIVES)));
				}
			}
			BodyCultivationContainer bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
			for (System systems : System.values()) {
				String system = systems.toString().toLowerCase();
				if (itemTag.contains(system)) {
					CompoundTag cTag = itemTag.getCompound(system);
					BigDecimal amount = new BigDecimal(cTag.getDouble("amount")/100d);
					if (systems == System.BODY) {
						bodyData.forgeAllParts(amount);
					} else
					cultivation.addStat(systems, PlayerSystemStat.CULTIVATION_BASE, amount);
					cultivation.addStat(System.ESSENCE, WuxiaElements.DEMONIC.getId(), PlayerSystemElementalStat.FOUNDATION, amount.multiply(new BigDecimal(5)));
				}
			}
		}
		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 100;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag ) {
		CompoundTag itemTag = stack.getTag();
		if (itemTag != null) {
			if (itemTag.contains("name")) {
				MutableComponent disc = Component.translatable(itemTag.getString("name"));
				for (System systems : System.values()) {
					String system = systems.toString().toLowerCase();
					if (itemTag.contains(system)) {
						CompoundTag cTag = itemTag.getCompound(system);
						disc.append("\n")
						.append(Component.translatable(cTag.getString("stage"))).append(" - ")
						.append(Component.translatable("wuxiacraft.gui.cultpoint", cTag.getDouble("amount")));
					}
				}
				tooltip.add(disc);
			}// added a tag based durability system to prevent unbreaking and mending
			if (itemTag.contains("durability")) {
				MutableComponent durability = Component.literal(String.valueOf(itemTag.getInt("durability")) + "/100");
				tooltip.add(durability);
			}
		}
		super.appendHoverText(stack, level, tooltip, flag);
	}
}
