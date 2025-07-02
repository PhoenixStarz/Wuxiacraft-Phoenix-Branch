package com.lazydragonstudios.wuxiacraft.cultivation.stats;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public interface BodyStatsContainer {

	HashMap<BodyStat, BigDecimal> bodyStats = new HashMap<>();

	default <T extends BodyStatsContainer> T setStat(BodyStat stat, BigDecimal value) {
		if (stat.isModifiable) return (T) this;
		this.bodyStats.put(stat, value);
		return (T) this;
	}

	@Nonnull
	default BigDecimal getStat(BodyStat stat) {
		return this.bodyStats.getOrDefault(stat, BigDecimal.ZERO);
	}

}
