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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

public class SkillCoffinAspect extends SkillHitAspect {

	public SkillCoffinAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("3"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("1"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1.2"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("1.8"));
		var strength = cultivation.getStat(PlayerStat.STRENGTH);
		this.activation = (caster, skill, result) -> {
			if (caster.level().isClientSide) return false;
			if (result == null) return false;
			var casterCultivation = Cultivation.get(caster);
			var skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
			BlockPos blockPosBase = null;
			if (result instanceof BlockHitResult blockHitResult) {
				blockPosBase = blockHitResult.getBlockPos();
			} else if (result instanceof EntityHitResult entityHitResult) {
				var target = entityHitResult.getEntity();
				if (target instanceof LivingEntity livingEntity) {
					blockPosBase = livingEntity.blockPosition();
					var damage = skillStrength.multiply(new BigDecimal("0.5"));
					var damageSource = new WuxiaDamageSource(caster.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
							.getHolderOrThrow(WuxiaDamageTypes.SKILL_BREAK), WuxiaElements.PHYSICAL.get(), livingEntity, damage);
					target.hurt(damageSource, damageSource.getDamage().floatValue());
				}
			}
			if (blockPosBase != null) {
				for (int i1 = -2; i1<=2; i1++) {
					for (int i2 = -2; i2<=2; i2++) {
						for (int i3 = -2; i3<=2; i3++) {
							BlockPos blockPos = blockPosBase.above(i1).north(i2).west(i3);
							if (!caster.mayInteract(caster.level(), blockPos)) continue;
							if (!SkillUtil.canPlayerPlace(caster.level(), blockPos, caster.level().getBlockState(blockPos), caster)) return false;
							if (!(caster.level().getBlockState(blockPos).getBlock() instanceof AirBlock)) continue;
							if (caster.level() instanceof ServerLevel serverLevel) {
								serverLevel.setBlockAndUpdate(blockPos , Blocks.SANDSTONE.defaultBlockState());
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
		return WuxiaSkillAspects.COFFIN.get();
	}
}
