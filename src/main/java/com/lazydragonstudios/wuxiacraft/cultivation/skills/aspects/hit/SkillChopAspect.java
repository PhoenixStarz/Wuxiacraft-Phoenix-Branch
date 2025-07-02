package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit;

import com.lazydragonstudios.wuxiacraft.combat.WuxiaDamageSource;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.init.WuxiaDamageTypes;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import com.lazydragonstudios.wuxiacraft.util.SkillUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.TickTask;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.math.BigDecimal;
import java.util.HashSet;

public class SkillChopAspect extends SkillHitAspect {

	public SkillChopAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("3"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("1"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1.6"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("1.4"));
		this.activation = (caster, skill, result) -> {
			if (caster.level().isClientSide) return false;
			if (result == null) return false;
			var server = caster.level().getServer();
			if (server == null) return false;
			var casterCultivation = Cultivation.get(caster);
			var skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
			if (result instanceof BlockHitResult blockHitResult) {
				if (skillStrength.compareTo(BigDecimal.valueOf(16)) <= 0) return false;
				var blockPos = blockHitResult.getBlockPos();
				var treeBlocks = SkillUtil.getLogsToBreak(blockPos, caster.level(), new HashSet<>());
				for (var pos : treeBlocks) {
					server.doRunTask(new TickTask(1, () -> {
						if (!caster.mayInteract(caster.level(), pos)) return;
						caster.level().destroyBlock(pos, true, caster);
					}));
				}
				return true;
			} else if (result instanceof EntityHitResult entityHitResult) {
				var target = entityHitResult.getEntity();
				if (target instanceof LivingEntity livingEntity) {
					var damage = skillStrength.multiply(new BigDecimal("2.5"));
					var damageSource = new WuxiaDamageSource(caster.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(WuxiaDamageTypes.SKILL_CHOP),
							WuxiaElements.PHYSICAL.get(), livingEntity, damage).setInstantDeath();
					target.hurt(damageSource, damageSource.getDamage().floatValue());
				}
				return true;
			}
			return false;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.CHOP.get();
	}
}
