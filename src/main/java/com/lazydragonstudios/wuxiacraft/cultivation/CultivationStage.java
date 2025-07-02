package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.cultivation.stats.*;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

public class CultivationStage implements StatsContainer {

	/**
	 * The next stage to this stage
	 * Null if not next stage
	 */
	@Nullable
	public final ResourceLocation nextStage;

	/**
	 * The previous stage to this stage
	 * This is used to when dropping back a stage when cultivation base is not stabilized
	 * Null if not previous stage
	 */
	@Nullable
	public final ResourceLocation previousStage;

	/**
	 * This is the realm this stage belongs to
	 */
	public final ResourceLocation realm;

	/**
	 * The cultivation system this belongs to
	 */
	public final System system;

	private final HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> elementalStats = new HashMap<>();

	private final HashMap<PlayerStat, BigDecimal> playerStats = new HashMap<>();

	/**
	 * The skill aspects this stage is going to unlock
	 */
	private final HashSet<ResourceLocation> skillsAspects;

	private final HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> systemElementalStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> systemStats = new HashMap<>();

	private Consumer<Player> onCultivate;

	private Consumer<Player> onCultivationFailure;

	private boolean regenEnergy;

	private boolean flight;

	/**
	 * Constructor for this cultivation stage
	 *
	 * @param system        the Stage Cultivation System
	 * @param realm         the realm this stage is in
	 * @param previousStage a reference to the previous stage, null if this is the first
	 * @param nextStage     a reference to the next stage, null if this is the last
	 */
	public CultivationStage(System system, ResourceLocation realm, @Nullable ResourceLocation previousStage, @Nullable ResourceLocation nextStage) {
		this.system = system;
		this.realm = realm;
		this.nextStage = nextStage;
		this.previousStage = previousStage;
		this.skillsAspects = new HashSet<>();
		this.regenEnergy = true;
		this.flight = false;
		this.onCultivate = p -> {
		};
		this.onCultivationFailure = p -> {
		};
	}

	public CultivationStage addSkill(ResourceLocation aspectLocation) {
		this.skillsAspects.add(aspectLocation);
		return this;
	}

	public boolean canRegenEnergy() {
		return regenEnergy;
	}

	public boolean canFly() {
		return this.flight;
	}

	public CultivationStage cannotRegenEnergy() {
		this.regenEnergy = false;
		return this;
	}

	public CultivationStage setFlight() {
		this.flight = true;
		return this;
	}

	public void cultivate(Player player) {
		this.onCultivate.accept(player);
	}

	public void cultivationFailure(Player player) {
		this.onCultivationFailure.accept(player);
	}

	public HashSet<ResourceLocation> getSkillsAspects() {
		HashSet<ResourceLocation> skills = new HashSet<>(this.skillsAspects);
		if (this.previousStage == null) return skills;
		var aux = WuxiaRegistries.CULTIVATION_STAGES.get().getValue(this.previousStage);
		if (aux == null) return skills;
		skills.addAll(aux.getSkillsAspects());
		return skills;
	}

	/**
	 * Recursively adds stats from previous stages
	 *
	 * @param stat The stat to be queried
	 * @return the stat value or zero if not found
	 */
	public @Nonnull BigDecimal getStat(PlayerStat stat) {
		BigDecimal stageValue = this.getPlayerStats().getOrDefault(stat, BigDecimal.ZERO);
		if (this.previousStage == null) return stageValue;
		var aux = WuxiaRegistries.CULTIVATION_STAGES.get().getValue(this.previousStage);
		if (aux == null) return stageValue;
		stageValue = stageValue.add(aux.getStat(stat));
		return stageValue;
	}

	@Override
	public HashMap<PlayerStat, BigDecimal> getPlayerStats() {
		return this.playerStats;
	}

	/**
	 * Recursively adds stats from previous stages
	 *
	 * @param stat    the stat to be queried
	 * @param element the element of the stat
	 * @return the stat value or zero if not found
	 */
	public @Nonnull BigDecimal getStat(PlayerElementalStat stat, ResourceLocation element) {
		var stageValue = this.getElementalStats()
				.getOrDefault(stat, new HashMap<>()).getOrDefault(element, BigDecimal.ZERO);
		if (this.previousStage == null) return stageValue;
		var aux = WuxiaRegistries.CULTIVATION_STAGES.get().getValue(this.previousStage);
		if (aux == null) return stageValue;
		stageValue = stageValue.add(aux.getStat(stat, element));
		return stageValue;
	}

	@Override
	public HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> getElementalStats() {
		return this.elementalStats;
	}

	/**
	 * Recursively adds stats from previous stages
	 *
	 * @param system the system to query the stat
	 * @param stat   the stat to be queried
	 * @return the stat value or zero if not found
	 */
	public @Nonnull BigDecimal getStat(System system, PlayerSystemStat stat) {
		var stageValue = this.getSystemStats().getOrDefault(system, new HashMap<>()).getOrDefault(stat, BigDecimal.ZERO);
		if (this.previousStage == null) return stageValue;
		var aux = WuxiaRegistries.CULTIVATION_STAGES.get().getValue(this.previousStage);
		if (aux == null) return stageValue;
		stageValue = stageValue.add(aux.getStat(system, stat));
		return stageValue;
	}

	@Override
	public HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> getSystemStats() {
		return this.systemStats;
	}

	/**
	 * Recursively adds stats from previous stages
	 *
	 * @param system  the system of the stat
	 * @param element the element of the stat
	 * @param stat    the stat to be queried
	 * @return the stat value or zero if not found
	 */
	public @Nonnull BigDecimal getStat(System system, ResourceLocation element, PlayerSystemElementalStat stat) {
		var stageValue = this.getSystemElementalStats()
				.getOrDefault(system, new HashMap<>()).getOrDefault(element, new HashMap<>()).getOrDefault(stat, BigDecimal.ZERO);
		if (this.previousStage == null) return stageValue;
		var aux = WuxiaRegistries.CULTIVATION_STAGES.get().getValue(this.previousStage);
		if (aux == null) return stageValue;
		stageValue = stageValue.add(aux.getStat(system, element, stat));
		return stageValue;
	}

	@Override
	public HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> getSystemElementalStats() {
		return this.systemElementalStats;
	}

	public CultivationStage setOnCultivate(Consumer<Player> onCultivate) {
		this.onCultivate = onCultivate;
		return this;
	}

	public CultivationStage setOnCultivationFailure(Consumer<Player> onCultivationFailure) {
		this.onCultivationFailure = onCultivationFailure;
		return this;
	}
}
