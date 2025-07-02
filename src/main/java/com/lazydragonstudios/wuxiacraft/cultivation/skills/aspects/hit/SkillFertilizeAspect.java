package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.phys.BlockHitResult;

import java.math.BigDecimal;

public class SkillFertilizeAspect extends SkillHitAspect {

	public SkillFertilizeAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("1.5"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("1"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("20"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("50"));
		this.activation = (caster, skill, result) -> {
			Level level = caster.level();
			if (level.isClientSide) return false;
			if (result == null) return false;
			var casterCultivation = Cultivation.get(caster);
			var skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
			if (result instanceof BlockHitResult blockHitResult) {
				if (skillStrength.compareTo(BigDecimal.valueOf(6)) <= 0) return false;
				var blockPos = blockHitResult.getBlockPos();
				var abovePos = blockPos.above();
				if (!caster.mayInteract(level, blockPos)) return false;
				var blockState = level.getBlockState(blockPos);
				var aboveState = level.getBlockState(abovePos);
				BonemealableBlock bonemealableBlock = null;
				if (aboveState.getBlock() instanceof BonemealableBlock ableBlock) {
					bonemealableBlock = ableBlock;
					blockPos = abovePos;
					blockState = aboveState;
				}
				else if (blockState.getBlock() instanceof BonemealableBlock ableBlock) bonemealableBlock = ableBlock;
				if (bonemealableBlock == null) return false;
				if (!(level instanceof ServerLevel)) return false;
				if (!bonemealableBlock.isValidBonemealTarget(level, blockPos, blockState, level.isClientSide)) return false;
				if (!bonemealableBlock.isBonemealSuccess(level, level.random, blockPos, blockState)) return true;
				bonemealableBlock.performBonemeal((ServerLevel) level, level.random, blockPos, blockState);
				level.levelEvent(1505, blockPos, 0);
				return true;
			}
			return false;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.FERTILIZE.get();
	}
}
