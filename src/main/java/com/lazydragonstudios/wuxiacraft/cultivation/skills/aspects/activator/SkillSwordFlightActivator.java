package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator;

import com.lazydragonstudios.wuxiacraft.capabilities.ClientAnimationState;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.SkillHitAspect;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter.NumberRangeParameter;
import com.lazydragonstudios.wuxiacraft.init.WuxiaGameRules;
import com.lazydragonstudios.wuxiacraft.init.WuxiaParticleTypes;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import com.lazydragonstudios.wuxiacraft.networking.BroadcastAnimationChangeRequestMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.util.SkillUtil;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.HitResult;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class SkillSwordFlightActivator extends SkillActivatorAspect {

	public SkillSwordFlightActivator(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("0.005"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("0.003"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("0"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("0"));
		this.skillParameters.put("strength_multiplier", new NumberRangeParameter("strength_multiplier", BigDecimal.ZERO, BigDecimal.ONE));
		this.activate = (caster, skill) -> {
			var casterCultivation = Cultivation.get(caster);
			var systemData = casterCultivation.getSystemData(System.ESSENCE);
			if (!(caster.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SwordItem)) return false;
			BigDecimal strengthValue = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
			var speed = strengthValue.multiply(new BigDecimal("9"));
			GameRules.Value<?> maxAgilityRule = caster.level().getGameRules().getRule(WuxiaGameRules.maxWuxiaAgility);
			var maxAgility = BigDecimal.valueOf(maxAgilityRule.getCommandResult()).divide(new BigDecimal("80"), RoundingMode.HALF_DOWN); // around 125% of max agi
			var speedCostMultiplier = BigDecimal.ONE;
			MathContext mc = new MathContext(8, RoundingMode.HALF_UP);
		/* 	if (speed.compareTo(maxAgility) > 0 && maxAgility > 0) {
				speedCostMultiplier = speed.divide(maxAgility, mc);
			}
			speed = speed.min(maxAgility); */
			var cost = skill.getAppliedStats(casterCultivation, SkillStat.COST).multiply(speedCostMultiplier, mc).multiply(new BigDecimal("0.4"), mc);
			if (!systemData.consumeEnergy(cost)) {
				skill.setStat(SkillStat.CURRENT_COOLDOWN, new BigDecimal("600")); // 30 s
				skill.setStat(SkillStat.CURRENT_MAX_COOLDOWN, new BigDecimal("600")); // 30 s
				return false;
			}
			var direction = caster.getLookAngle().scale(speed.floatValue());
			caster.setDeltaMovement(direction.x, direction.y, direction.z);
			caster.fallDistance = 0f;
			if (caster.level().isClientSide) {
				var animationState = ClientAnimationState.get(caster);
				if (!animationState.isSwordFlight()) {
					animationState.setSwordFlight(true);
					WuxiaPacketHandler.INSTANCE.sendToServer(new BroadcastAnimationChangeRequestMessage(animationState, casterCultivation.isCombat()));
				}
			}
			var result = SkillUtil.getHitResult(caster, 2, e -> e != caster);
			if (result.getType() == HitResult.Type.MISS) {
				for (var link : skill.getSkillChain()) {
					if (!(link instanceof SkillHitAspect hitAspect)) continue;
					hitAspect.activate(caster, skill, result);
					break;
				}
			}
			return true;
		};
	}

	@Override
	public BigDecimal applyStat(ICultivation cultivation, SkillStat stat, BigDecimal currentValue) {
		BigDecimal parameterModifier = (BigDecimal) this.getSkillParameters().get("strength_multiplier").getValue();
		return super.applyStat(cultivation, stat, currentValue).multiply(parameterModifier);
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.SWORD_FLIGHT.get();
	}
}
