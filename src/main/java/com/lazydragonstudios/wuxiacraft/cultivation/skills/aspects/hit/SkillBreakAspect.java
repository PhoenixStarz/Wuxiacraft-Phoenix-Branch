package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit;

import com.lazydragonstudios.wuxiacraft.combat.WuxiaDamageSource;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillEventHandler;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter.IntegerRangeParameter;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaDamageTypes;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
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

public class SkillBreakAspect extends SkillHitAspect {

	public SkillBreakAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("1"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("1"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1.2"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("1.8"));
		var essenceData = cultivation.getSystemData(System.ESSENCE);
		var strength = essenceData.getStat(PlayerStat.STRENGTH);
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
				var blockPos = blockHitResult.getBlockPos();
				if (!caster.mayInteract(caster.level(), blockPos)) return false;
				var blockState = caster.level().getBlockState(blockPos);
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
					var destroyHardness = new BigDecimal(destroySpeed * 100f);
					var destroySteps = skillStrength.divide(destroyHardness, new MathContext(4, RoundingMode.HALF_DOWN));
					var destroyProgress = levelBreakBlocks.get(blockPos);
					destroyProgress.breakerId = caster.getId();
					destroyProgress.lastUpdatedTick = serverTickCount;
					destroyProgress.destroyProgress += destroySteps.floatValue();
				}
				return true;
			} else if (result instanceof EntityHitResult entityHitResult) {
				var target = entityHitResult.getEntity();
				if (target instanceof LivingEntity livingEntity) {
					var damage = skillStrength.multiply(new BigDecimal("0.5"));
					var damageSource = new WuxiaDamageSource(caster.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(WuxiaDamageTypes.SKILL_BREAK), WuxiaElements.PHYSICAL.get(), livingEntity, damage);
					target.hurt(damageSource, damageSource.getDamage().floatValue());
				}
				return true;
			}
			return false;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.BREAK.get();
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
