package com.lazydragonstudios.wuxiacraft.item;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.client.gui.RuneSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RuneStencil extends DiggerItem {

	public static final TagKey<Block> MINEABLE_BLOCKS = BlockTags.create(new ResourceLocation(WuxiaCraft.MOD_ID, "mineable/rune_stencil"));

	public RuneStencil(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, Properties pProperties) {
		super(pAttackDamageModifier, pAttackSpeedModifier, pTier, MINEABLE_BLOCKS, pProperties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		if (pPlayer.isCrouching()) {
			if (pLevel.isClientSide) {
				openRuneSelectionScreen();
			}
			return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
		}
		return super.use(pLevel, pPlayer, pUsedHand);
	}

	@OnlyIn(Dist.CLIENT)
	private void openRuneSelectionScreen() {
		Minecraft.getInstance().setScreen(new RuneSelectionScreen(Component.empty()));
	}
}
