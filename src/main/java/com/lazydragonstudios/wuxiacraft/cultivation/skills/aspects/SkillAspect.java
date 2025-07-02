package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects;

import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter.SkillParameter;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class SkillAspect {

	protected final HashMap<SkillStat, BigDecimal> skillStats = new HashMap<>();

	protected final HashMap<String, SkillParameter<?>> skillParameters = new HashMap<>();

	public SkillAspect(ICultivation cultivation) {
	}

	public BigDecimal applyStat(ICultivation cultivation, SkillStat stat, BigDecimal currentValue) {
		return currentValue;
	}

	public SkillAspect setSkillStat(SkillStat stat, BigDecimal value) {
		if (stat.isModifiable) return this;
		this.skillStats.put(stat, value);
		return this;
	}

	public HashMap<String, SkillParameter<?>> getSkillParameters() {
		return skillParameters;
	}

	public abstract SkillAspectType getType();

	public BigDecimal getSkillStat(SkillStat stat) {
		return skillStats.getOrDefault(stat, BigDecimal.ZERO);
	}

	/**
	 * Will determine whether it can connect to next node
	 *
	 * @param aspect the next skill aspect trying to connect to this
	 * @return whether it can connect or not
	 */
	public abstract boolean canConnect(SkillAspect aspect);

	public abstract boolean canCompile(LinkedList<SkillAspect> aspectChain);

	public CompoundTag serialize() {
		var statsList = new ListTag();
		for (var stat : this.skillStats.keySet()) {
			var statTag = new CompoundTag();
			statTag.putString("stat-name", stat.toString());
			statTag.putString("stat-value", this.skillStats.get(stat).toPlainString());
			statsList.add(statTag);
		}
		var resultTag = new CompoundTag();
		resultTag.put("stats-list", statsList);
		var aspectType = this.getType();
		var typeLocation = WuxiaRegistries.SKILL_ASPECT.get().getKey(aspectType);
		if (typeLocation != null) {
			resultTag.putString("skill-type", typeLocation.toString());
		}
		var parametersTag = new CompoundTag();
		for (var parameterName : this.getSkillParameters().keySet()) {
			parametersTag.put(parameterName, this.getSkillParameters().get(parameterName).encode());
		}
		resultTag.put("parameters", parametersTag);
		return resultTag;
	}

	public void deserialize(CompoundTag tag) {
		if (tag.contains("stats-list")) {
			var statsTag = tag.get("stats-list");
			if (!(statsTag instanceof ListTag statsList)) return;
			for (var rawStat : statsList) {
				if (!(rawStat instanceof CompoundTag statTag)) continue;
				var stat = SkillStat.valueOf(statTag.getString("stat-name"));
				var value = new BigDecimal(statTag.getString("stat-value"));
				this.skillStats.put(stat, value);
			}
		}
		if (tag.contains("parameters")) {
			var parametersTag = tag.getCompound("parameters");
			for (var parameterName : this.getSkillParameters().keySet()) {
				if (!parametersTag.contains(parameterName)) continue;
				this.getSkillParameters().get(parameterName).decode(parametersTag.getCompound(parameterName));
			}
		}
	}

	public static SkillAspect readAspect(CompoundTag tag, ICultivation cultivation) {
		if (tag.contains("skill-type")) {
			var skillTypeName = tag.getString("skill-type");
			var skillType = WuxiaRegistries.SKILL_ASPECT.get().getValue(new ResourceLocation(skillTypeName));
			if (skillType == null) return null;
			var skillAspect = skillType.creator.create(cultivation);
			skillAspect.deserialize(tag);
			return skillAspect;
		}
		return null;
	}

}
