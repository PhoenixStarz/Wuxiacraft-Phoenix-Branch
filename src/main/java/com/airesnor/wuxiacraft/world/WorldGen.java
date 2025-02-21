package com.airesnor.wuxiacraft.world;

import com.airesnor.wuxiacraft.blocks.WuxiaBlocks;
import com.airesnor.wuxiacraft.world.dimensions.WuxiaDimensions;
import com.google.common.base.Predicate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0) {
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if (world.provider.getDimension() == WuxiaDimensions.MINING.getId()) {
			generateMiningWorld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider, BlockMatcher.forBlock(Blocks.STONE));
		} else if (world.provider.getDimension() == WuxiaDimensions.ELEMENTAL.getId()) {
			generateElementalWorld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider, BlockMatcher.forBlock(Blocks.STONE));
			generateElementalWorld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider, BlockMatcher.forBlock(Blocks.DIRT));
			generateElementalWorld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider, BlockMatcher.forBlock(WuxiaBlocks.ICY_STONE));
			generateElementalWorld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider, BlockMatcher.forBlock(WuxiaBlocks.METALLIC_STONE));
			generateElementalWorld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider, BlockMatcher.forBlock(WuxiaBlocks.FIERY_STONE));
		} else if (world.provider.getDimension() == WuxiaDimensions.SKY.getId()) {
			generateSkyWorld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider, BlockMatcher.forBlock(Blocks.STONE));
		}
	}

	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		generateOre(WuxiaBlocks.NATURAL_ODDITY_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 30, 4, 4);
		generateOre(WuxiaBlocks.WEAK_LIFE_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 20, 50, 5, 7);
		generateOre(WuxiaBlocks.SOUL_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 40, 4, 6);
		generateOre(WuxiaBlocks.PRIMORDIAL_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 30, 4, 5);
		generateOre(WuxiaBlocks.FIVE_ELEMENT_PURE_CRYSTAL_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 20, 3, 3);
		generateOre(WuxiaBlocks.PURE_QI_CRYSTAL_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 20, 3, 3);
		generateOre(WuxiaBlocks.EARTH_LAW_CRYSTAL_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 15, 2, 2);
	}

	private void generateMiningWorld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, Predicate<IBlockState> replaceWithOre) {
		generateOre(Blocks.IRON_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 120, 4, 8, replaceWithOre);
		generateOre(Blocks.GOLD_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 90, 4, 7, replaceWithOre);
		generateOre(Blocks.DIAMOND_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 60, 3, 5, replaceWithOre);
		generateOre(Blocks.EMERALD_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 60, 3, 5, replaceWithOre);

		generateOre(WuxiaBlocks.NATURAL_ODDITY_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 90, 4, 4, replaceWithOre);
		generateOre(WuxiaBlocks.EARTH_LAW_CRYSTAL_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 20, 40, 5, 7, replaceWithOre);
		generateOre(WuxiaBlocks.SKY_LAW_CRYSTAL_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 30, 4, 6, replaceWithOre);
		generateOre(WuxiaBlocks.HEAVENLY_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 25, 4, 5, replaceWithOre);
		generateOre(WuxiaBlocks.RAINBOW_LAW_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 20, 3, 3, replaceWithOre);
		generateOre(WuxiaBlocks.SKY_AND_EARTH_CRYSTAL_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 15, 3, 3, replaceWithOre);
		generateOre(WuxiaBlocks.LAW_NEXUS_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 15, 2, 2, replaceWithOre);
	}

	private void generateElementalWorld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, Predicate<IBlockState> replaceWithOre) {
		generateOre(Blocks.COAL_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 0, 80, 12, 8, replaceWithOre);
		generateOre(Blocks.IRON_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 0, 80, 4, 6, replaceWithOre);
		generateOre(Blocks.GOLD_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 30, 50, 4, 5, replaceWithOre);
		generateOre(Blocks.DIAMOND_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 25, 3, 3, replaceWithOre);
		generateOre(Blocks.EMERALD_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 20, 3, 3, replaceWithOre);

		generateOre(WuxiaBlocks.NATURAL_ODDITY_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 50, 4, 4, replaceWithOre);
		generateOre(WuxiaBlocks.LAW_NEXUS_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 20, 40, 5, 7, replaceWithOre);
		generateOre(WuxiaBlocks.WAR_CRYSTAL_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 30, 4, 6, replaceWithOre);
		generateOre(WuxiaBlocks.GOLD_SPIRIT_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 25, 4, 5, replaceWithOre);
		generateOre(WuxiaBlocks.YIN_YANG_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 20, 3, 3, replaceWithOre);
		generateOre(WuxiaBlocks.TRANSCENDENT_CRYSTAL_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 15, 3, 3, replaceWithOre);
		generateOre(WuxiaBlocks.IMMORTALITY_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 15, 2, 2, replaceWithOre);
	}

	private void generateSkyWorld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, Predicate<IBlockState> replaceWithOre) {
		generateOre(WuxiaBlocks.NATURAL_ODDITY_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 50, 4, 4, replaceWithOre);
		generateOre(WuxiaBlocks.IMMORTALITY_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 20, 40, 5, 4, replaceWithOre);
		generateOre(WuxiaBlocks.ASCENDED_IMMORTALITY_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 10, 30, 4, 4, replaceWithOre);
		generateOre(WuxiaBlocks.IMMORTAL_WILL_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 25, 4, 3, replaceWithOre);
		generateOre(WuxiaBlocks.STELLAR_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 20, 3, 3, replaceWithOre);
		generateOre(WuxiaBlocks.DIVINE_ORIGIN_STONE_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 15, 3, 2, replaceWithOre);
		generateOre(WuxiaBlocks.BOUNDLESS_VOID_CRYSTAL_VEIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 15, 2, 1, replaceWithOre);
	}

	private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {
		int deltaY = maxY - minY;

		for (int i = 0; i < chances; i++) {
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));

			WorldGenMinable generator = new WorldGenMinable(ore, 1 + random.nextInt(size));
			generator.generate(world, random, pos);
		}
	}

	private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances, Predicate<IBlockState> replaceWithOre) {
		int deltaY = maxY - minY;

		for (int i = 0; i < chances; i++) {
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));

			WorldGenMinable generator = new WorldGenMinable(ore, 1 + random.nextInt(size), replaceWithOre);
			generator.generate(world, random, pos);
		}
	}
}
