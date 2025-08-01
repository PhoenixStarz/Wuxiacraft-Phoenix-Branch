package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.AspectContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public interface ICultivation {

	@Nullable
	ResourceLocation getBodyTransformation();

	HashMap<ResourceLocation, HashMap<PlayerElementalStat, BigDecimal>> getElementalStats();

	FormationStatsContainer getFormationStats();

	BigDecimal getStat(PlayerStat stat);

	/**
	 *
	 * @param stat the stat you want the value of
	 * @param fullValue true = should not use regulated values, false = regulate values
	 * @return the stat value
	 */
	BigDecimal getStat(PlayerStat stat, boolean fullValue);

	BigDecimal getStat(ResourceLocation elementLocation, PlayerElementalStat stat);

	BigDecimal getStat(System system, PlayerSystemStat stat);

	BigDecimal getStat(System system, ResourceLocation element, PlayerSystemElementalStat stat);

	void setBodyTransformation(ResourceLocation bodyTransformation);

	void setStat(PlayerStat stat, BigDecimal value);

	void setStat(ResourceLocation element, PlayerElementalStat stat, BigDecimal value);

	void setStat(System system, PlayerSystemStat stat, BigDecimal value);

	void setStat(System system, ResourceLocation element, PlayerSystemElementalStat stat, BigDecimal value);

	void addStat(PlayerStat stat, BigDecimal value);

	void addStat(ResourceLocation element, PlayerElementalStat stat, BigDecimal value);

	void addStat(System system, PlayerSystemStat stat, BigDecimal value);

	void addStat(System system, ResourceLocation element, PlayerSystemElementalStat stat, BigDecimal value);

	default boolean canFly() {
		boolean canFly = false;
		for(var system : System.values()) {
			canFly = canFly || this.getSystemData(system).getStage().canFly();
		}
		return canFly;
	}

	@Nullable
	UUID getSectId();

	void setSectId(@Nullable UUID sectId);

	SystemContainer getSystemData(System system);

	double getExtraHealthFromAttributes();

	void setExtraHealthFromAttributes(double extraHealthFromAttributes);

	/**
	 * Adds cultivation base to the player, it should only be used in cultivate handlers from each stage
	 *
	 * @param player the player that is cultivating
	 * @param system the system that is being cultivated
	 * @param amount the amount to be added in the cultivation
	 */
	void addCultivationBase(Player player, System system, BigDecimal amount);

	/**
	 * Attempts to break through the next stage in the specified system
	 *
	 * @param system the system of the stage to break through
	 * @return true if successful in the breakthrough
	 */
	boolean attemptBreakthrough(System system);

	void calculateStats();

	CompoundTag serialize();

	void deserialize(CompoundTag tag);

	boolean isExercising();

	void setExercising(boolean exercising);

	AspectContainer getAspects();

	SkillContainer getSkills();

	boolean isCombat();

	void setCombat(boolean combat);

	/**
	 * Utility to increment to the tick timer
	 */
	void advanceTimer();

	/**
	 * Utility to reset timer.
	 * Should only be used when a sync message is sent
	 */
	void resetTimer();

	/**
	 * @return the time ticker. It's just for not exposing the ticker.
	 */
	int getTimer();
	
	//
	void advanceCultTimer();

	void resetCultTimer();

	int getCultTimer();
	//

	boolean isDivineSense();

	void setDivineSense(boolean divineSense);

	boolean isWithinFormationRange();

	void setWithinFormationRange(double x, double y, double z);

	void setFormation(BlockPos blockPos);

	@Nullable
	BlockPos getFormation();

	double getAgilityRegulator();

	void setAgilityRegulator(double agilityRegulator);

	double getStrengthRegulator();

	void setStrengthRegulator(double strengthRegulator);

	MasterDiscipleContainer getMasterDiscipleContainer();
}
