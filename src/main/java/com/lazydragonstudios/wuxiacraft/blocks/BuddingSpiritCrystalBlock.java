package com.lazydragonstudios.wuxiacraft.blocks;

import com.lazydragonstudios.wuxiacraft.init.WuxiaBlocks;
import com.lazydragonstudios.wuxiacraft.WuxiaCraft;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public final class BuddingSpiritCrystalBlock extends BuddingAmethystBlock
{
    private static final Direction[] DIRECTIONS = Direction.values();


    public BuddingSpiritCrystalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(40) == 0) {
            Direction side = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos neighborPos = pos.relative(side);
            BlockState neighborState = level.getBlockState(neighborPos);

            Block block = null;
            if (canClusterGrowAtState(neighborState)) {
                block = WuxiaBlocks.SPIRIT_CRYSTAL_CLUSTER_1.get();
            }
            else if (neighborState.getBlock() instanceof SpiritCrystalCluster && sameFacing(neighborState, side)) {
                String cuttoff = neighborState.getBlock().toString();
                cuttoff = cuttoff.substring(cuttoff.indexOf("spirit_crystal_cluster_") + "spirit_crystal_cluster_".length());
                cuttoff = cuttoff.substring(0, 1);
                int multi = Integer.valueOf(cuttoff);
                if (multi < 9 && random.nextInt((int)Math.pow(2, multi)) == 0) {
                    ResourceLocation id = new ResourceLocation(WuxiaCraft.MOD_ID, "spirit_crystal_cluster_"+(multi+1));
                    block = BuiltInRegistries.BLOCK.get(id); 
                }
            }
            if (block != null) {
                BlockState newState = block.defaultBlockState()
                        .setValue(AmethystClusterBlock.FACING, side)
                        .setValue(AmethystClusterBlock.WATERLOGGED, neighborState.getFluidState().getType() == Fluids.WATER);

                level.setBlockAndUpdate(neighborPos, newState);
            }
        }
    }

    private static boolean sameFacing(BlockState state, Direction side) {
        return state.getValue(AmethystClusterBlock.FACING) == side;
    }
}