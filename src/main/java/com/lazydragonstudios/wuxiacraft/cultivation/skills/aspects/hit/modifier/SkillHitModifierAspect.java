package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.modifier;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillDescriptor;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspect;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.ISkillHitAction;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter.NumberRangeParameter;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;

import java.math.BigDecimal;
import java.util.LinkedList;

public class SkillHitModifierAspect extends SkillAspect {

	public ISkillHitAction activation;

	public SkillHitModifierAspect(ICultivation cultivation) {
		super(cultivation);
		this.activation = (p, l, r) -> false;
	}

	public SkillAspect setActivation(ISkillHitAction activation) {
		this.activation = activation;
		return this;
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.POISON_EFFECT.get();
	}

	public boolean activate(Player player, SkillDescriptor skill, HitResult result) {
		return this.activation.hit(player, skill, result);
	}

	@Override
	public boolean canConnect(SkillAspect aspect) {
		return false;
	}

	@Override
	public boolean canCompile(LinkedList<SkillAspect> aspectChain) {
		return false;
	}
}
