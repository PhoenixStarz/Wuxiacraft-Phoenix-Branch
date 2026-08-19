package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit;

import com.lazydragonstudios.wuxiacraft.combat.WuxiaDamageSource;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillEventHandler;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter.IntegerRangeParameter;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaDamageTypes;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import com.lazydragonstudios.wuxiacraft.util.SkillUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

public class SkillOreMineAspect extends SkillHitAspect {

	public SkillOreMineAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("9"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("1"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1.2"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("1.8"));
		var strength = cultivation.getStat(PlayerStat.STRENGTH);
		var maxToolLevel = 0;
		if (strength.compareTo(new BigDecimal("124")) >= 0) maxToolLevel = 3;
		else if (strength.compareTo(new BigDecimal("43.5")) >= 0) maxToolLevel = 2;
		else if (strength.compareTo(BigDecimal.TEN) >= 0) maxToolLevel = 1;
		this.skillParameters.put("tool_level", new IntegerRangeParameter("tool_level", 0, maxToolLevel));
		this.activation = (caster, skill, result) -> {
			if (caster.level().isClientSide) return false;
			if (result == null) return false;
			var casterCultivation = Cultivation.get(caster);
			var skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
			if (result instanceof BlockHitResult blockHitResult) {
				skillStrength = skillStrength.multiply(new BigDecimal("8.25")); // this is needed, else break becomes too slow
				BlockPos blockPosBase = blockHitResult.getBlockPos();
				for (int i1 = -2; i1<=2; i1++) {
					for (int i2 = -2; i2<=2; i2++) {
						for (int i3 = -2; i3<=2; i3++) {
							BlockPos blockPos = blockPosBase.above(i1).north(i2).west(i3);
							if (!caster.mayInteract(caster.level(), blockPos)) continue;
							var blockState = caster.level().getBlockState(blockPos);
							if (!SkillUtil.canPlayerBreak(caster.level(), blockPos, blockState, caster)) return false;
							var destroySpeed = blockState.getDestroySpeed(caster.level(), blockPos);
							int toolLevel = (Integer) this.getSkillParameters().get("tool_level").getValue();
							if (destroySpeed < 0) return false;
							if (toolLevel < 3 && blockState.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
								return false;
							} else if (toolLevel < 2 && blockState.is(BlockTags.NEEDS_IRON_TOOL)) {
								return false;
							} else if (toolLevel < 1 && blockState.is(BlockTags.NEEDS_STONE_TOOL)) {
								return false;
							}
							if (caster.level() instanceof ServerLevel serverLevel) {
								SkillEventHandler.skillBlockDestroyProgress.putIfAbsent(serverLevel, new HashMap<>());
								var levelBreakBlocks = SkillEventHandler.skillBlockDestroyProgress.get(serverLevel);
								int serverTickCount = serverLevel.getServer().getTickCount();
								levelBreakBlocks.putIfAbsent(blockPos, new SkillEventHandler.BlockDestroyProgress(0, caster.getId(), serverTickCount));
								var destroyHardness = new BigDecimal(destroySpeed * 100f).max(BigDecimal.ONE);
								var destroySteps = skillStrength.divide(destroyHardness, new MathContext(4, RoundingMode.HALF_DOWN));
								var destroyProgress = levelBreakBlocks.get(blockPos);
								destroyProgress.breakerId = caster.getId();
								destroyProgress.lastUpdatedTick = serverTickCount;
								destroyProgress.destroyProgress += destroySteps.floatValue();
							}
						}
					}
				}
				return true;
			}
			return false;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.ORE_MINE.get();
	}

	@Override
	public BigDecimal applyStat(ICultivation cultivation, SkillStat stat, BigDecimal currentValue) {
		var addend = BigDecimal.ZERO;
		if (stat == SkillStat.COST) {
			BigDecimal toolLevel = BigDecimal.valueOf((Integer) this.getSkillParameters().get("tool_level").getValue());
			addend = toolLevel.multiply(new BigDecimal("0.3"));
		}
		return super.applyStat(cultivation, stat, currentValue).add(addend);
	}
}
