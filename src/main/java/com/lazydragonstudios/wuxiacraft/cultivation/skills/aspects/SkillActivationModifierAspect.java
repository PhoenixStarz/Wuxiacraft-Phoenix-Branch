package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects;

import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.SkillHitAspect;

import java.util.LinkedList;

public class SkillActivationModifierAspect extends SkillAspect {

	public SkillActivationModifierAspect(ICultivation cultivation) {
		super(cultivation);
	}

	@Override
	public SkillAspectType getType() {
		return null;
	}

	@Override
	public boolean canConnect(SkillAspect aspect) {
		return aspect instanceof SkillActivationModifierAspect || aspect instanceof SkillHitAspect;
	}

	@Override
	public boolean canCompile(LinkedList<SkillAspect> aspectChain) {
		return false;
	}
}
