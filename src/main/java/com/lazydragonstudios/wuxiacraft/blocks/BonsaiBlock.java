package com.lazydragonstudios.wuxiacraft.blocks;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.init.WuxiaBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BonsaiBlock extends SweetBerryBushBlock {

    protected final Supplier<Supplier<Item>> item;
    private final boolean hurtEntityInside;

    public BonsaiBlock(Properties properties, Supplier<Supplier<Item>> item, boolean hurtEntityInside) {
        super(properties);
        this.item = item;
        this.hurtEntityInside = hurtEntityInside;
    }

    public BonsaiBlock(Supplier<Supplier<Item>> item, boolean hurtEntityInside) {
        this(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH), item, hurtEntityInside);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return item.get().get().getDefaultInstance();
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        int age = state.getValue(AGE);
        boolean isMaxAge = age == MAX_AGE;
        if (!isMaxAge) {
            return InteractionResult.FAIL;
        } else if (age > 1) {
            popResource(level, pos, new ItemStack(item.get().get(), 1));
            level.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            BlockState blockState = state.setValue(AGE, 1);
            level.setBlock(pos, blockState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));
            return InteractionResult.sidedSuccess(level.isClientSide());
        } else {
            return super.use(state, level, pos, player, hand, hit);
        }
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
    }

    @Override
	public boolean isBonemealSuccess (Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
		return false;
	}

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        super.randomTick(state, worldIn, pos, random);
        int i = state.getValue(AGE);
        String cuttoff = state.getBlock().toString();
        cuttoff = cuttoff.substring(cuttoff.indexOf("bonsai_") + "bonsai_".length());
        cuttoff = cuttoff.substring(0, 1);
        int multi = Integer.valueOf(cuttoff);
        if (worldIn.getRawBrightness(pos.above(), 0) < 9) return;
        if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(5+(int)Math.pow(2, multi)) != 0)) return;
        if (i < 3) {
            worldIn.setBlock(pos, state.setValue(AGE, i + 1), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        } else if (multi < 9 && random.nextInt(100) == 0) {
            ResourceLocation id = new ResourceLocation(WuxiaCraft.MOD_ID, "bonsai_"+(multi+1));
            Block newblock = BuiltInRegistries.BLOCK.get(id);
            BlockState blockState = newblock.defaultBlockState();
            worldIn.setBlock(pos, blockState.setValue(AGE, 1), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, blockState);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

}

