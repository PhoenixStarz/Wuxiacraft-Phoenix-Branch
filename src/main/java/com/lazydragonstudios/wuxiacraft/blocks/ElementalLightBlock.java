package com.lazydragonstudios.wuxiacraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ElementalLightBlock extends Block {

	public ElementalLightBlock(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		return super.getLightEmission(state, level, pos);
	}
}
