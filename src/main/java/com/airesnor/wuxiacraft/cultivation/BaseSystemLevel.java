package com.airesnor.wuxiacraft.cultivation;

import com.airesnor.wuxiacraft.utils.MathUtils;

import javax.annotation.Nonnull;
import java.util.LinkedList;

public class BaseSystemLevel {

	public static final LinkedList<BaseSystemLevel> BODY_LEVELS = new LinkedList<>();
	public static final LinkedList<BaseSystemLevel> ESSENCE_LEVELS = new LinkedList<>();
	public static final LinkedList<BaseSystemLevel> DIVINE_LEVELS = new LinkedList<>();

	public static BaseSystemLevel DEFAULT_BODY_LEVEL;
	public static BaseSystemLevel DEFAULT_ESSENCE_LEVEL;
	public static BaseSystemLevel DEFAULT_DIVINE_LEVEL;

	public static void initializeLevels() {
		BODY_LEVELS.add(new BaseSystemLevel("mortal_body", "body_mortal_1", "Mortal Body", 1, 666.667, 1, false, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_mortal_1", "body_mortal_2", "Body Cleansing", 9, 4000, 1.8, false, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_mortal_2", "body_mortal_3", "Body Training", 9, 24766.95, 14.76, false, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_mortal_3", "body_immortal_1", "Body Transformation", 9, 317987.39, 131.66, true, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_immortal_1", "body_immortal_2", "Earthly Body", 9, 7054903.53, 1269.19, true, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_immortal_2", "body_immortal_3", "Heavenly Body", 9, 187825047.84, 14976.50, true, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_immortal_3", "body_godhood_1", "Supreme Body", 9, 10369096617.65, 176722.67, true, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_godhood_1", "body_godhood_2", "Saint Body", 9, 989172587340.57, 2212567.81, true, true));
		BODY_LEVELS.add(new BaseSystemLevel("body_godhood_2", "body_godhood_3", "Divine Body", 9, 113235986928378.00, 29294397.78, true, true));
		BODY_LEVELS.add(new BaseSystemLevel("body_godhood_3", "body_godhood_3", "Godly Body", 9, 38706539279813700.00, 451133725.75, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("mortal_essence", "essence_mortal_1", "Mortal Essence", 1, 666.667, 1, false, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_mortal_1", "essence_mortal_2", "Essence Gathering", 9, 4000, 1.8, false, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_mortal_2", "essence_mortal_3", "Essence Consolidation", 9, 24766.95, 14.76, false, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_mortal_3", "essence_immortal_1", "Revolving Core", 9, 317987.39, 131.66, true, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_immortal_1", "essence_immortal_2", "Immortal Sea", 9, 7054903.53, 1269.19, true, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_immortal_2", "essence_immortal_3", "Immortal Transformation", 9, 187825047.84, 14976.50, true, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_immortal_3", "essence_godhood_1", "True Immortal", 9, 10369096617.65, 176722.67, true, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_godhood_1", "essence_godhood_2", "True God", 9, 989172587340.57, 2212567.81, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_godhood_2", "essence_godhood_3", "God King", 9, 113235986928378.00, 29294397.78, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_godhood_3", "essence_godhood_3", "World God", 9, 38706539279813700.00, 451133725.75, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("mortal_soul", "divine_mortal_1", "Mortal Soul", 1, 666.667, 1, false, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_mortal_1", "divine_mortal_2", "Soul Condensing", 9, 4000, 1.8, false, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_mortal_2", "divine_mortal_3", "Soul Forging", 9, 24766.95, 14.76, false, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_mortal_3", "divine_immortal_1", "Perfect Soul", 9, 317987.39, 131.66, true, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_immortal_1", "divine_immortal_2", "Spiritual Sense", 9, 7054903.53, 1269.19, true, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_immortal_2", "divine_immortal_3", "Spiritual Foundation", 9, 187825047.84, 14976.50, true, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_immortal_3", "divine_godhood_1", "Spiritual Core", 9, 10369096617.65, 176722.67, true, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_godhood_1", "divine_godhood_2", "Divine Sense", 9, 989172587340.57, 2212567.81, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_godhood_2", "divine_godhood_3", "Divine Will", 9, 113235986928378.00, 29294397.78, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_godhood_3", "divine_godhood_3", "True Divine Soul World", 9, 38706539279813700.00, 451133725.75, true, true));
		DEFAULT_BODY_LEVEL = BODY_LEVELS.get(0);
		DEFAULT_ESSENCE_LEVEL = ESSENCE_LEVELS.get(0);
		DEFAULT_DIVINE_LEVEL = DIVINE_LEVELS.get(0);
	}


	/**
	 * the level identifier
	 */
	public final String levelName;

	/**
	 * Next level name for when progress hits max progress
	 */
	public final String nextLevelName;

	/**
	 * A name to display to the client
	 */
	public final String displayName;

	/**
	 * The amount of sub levels this level is gonna have
	 */
	public final int subLevels;

	/**
	 * Used for calculating sub levels max progress to go to next level
	 */
	public final double baseProgress;

	/**
	 * Used for calculations that depends on divine cultivation
	 */
	public final double baseModifier;

	/**
	 * Used to know when to call tribulations
	 */
	public final boolean callsTribulation;

	/**
	 * Used to know when to call tribulations each sub level;
	 */
	public final boolean tribulationEachSubLevel;

	public BaseSystemLevel(String levelName, String nextLevelName, String displayName, int subLevels, double baseProgress, double baseModifier, boolean callsTribulation, boolean tribulationEachSubLevel) {
		this.levelName = levelName;
		this.nextLevelName = nextLevelName;
		this.displayName = displayName;
		this.subLevels = subLevels;
		this.baseProgress = baseProgress;
		this.baseModifier = baseModifier;
		this.callsTribulation = callsTribulation;
		this.tribulationEachSubLevel = tribulationEachSubLevel;
	}

	/**
	 * Gets the amount of progress needed to get past this level
	 * @param subLevel the sub level currently in
	 * @return the amount of progress
	 */
	public double getProgressBySubLevel(int subLevel){
		if(MathUtils.between(subLevel, 0, this.subLevels -1)) {
			double value = this.baseProgress * (Math.pow(1.2, subLevel));
			if(subLevel == this.subLevels-1) {
				value*=1.5;
			}
			return value;
		}
		return this.baseProgress;
	}

	/**
	 * Gets the modifier this level has, used when making calculations
	 * @param subLevel the sub level currently in
	 * @return the modifier
	 */
	public double getModifierBySubLevel(int subLevel) {
		if(MathUtils.between(subLevel, 0, this.subLevels -1)) {
			return this.baseModifier * (1 + subLevel * 0.72);
		}
		return this.baseModifier;
	}

	/**
	 * Gets the next level for this level, used when leveling up or checking if level is greater than
	 * @param listToSearch the list this level belongs so the search can go smoothly
	 * @return the level after this
	 */
	public BaseSystemLevel nextLevel(LinkedList<BaseSystemLevel> listToSearch) {
		for(BaseSystemLevel level : listToSearch) {
			if(level.levelName.equals(this.nextLevelName)) return level;
		}
		return null;
	}

	/**
	 * Checks if this level is grater than level passed
	 * @param level level to be compared
	 * @param listToSearch the list this level belongs so the search can go smoothly
	 * @return if this level is greater than level in param
	 */
	public boolean greaterThan(BaseSystemLevel level, LinkedList<BaseSystemLevel> listToSearch) {
		boolean greater = false;
		if(listToSearch.contains(this) && listToSearch.contains(level)) {
			BaseSystemLevel aux = level;
			while(aux != aux.nextLevel(listToSearch)) {
				aux = aux.nextLevel(listToSearch);
				if(aux == level) {
					greater = true;
					break;
				}
			}
		}
		return greater;
	}

	public String getLevelName(int subLevel) {
		if(MathUtils.between(subLevel, 0, this.subLevels-1)) {
			return this.displayName + " Rank " + (subLevel+1);
		} else {
			return this.displayName;
		}
	}

	@Nonnull
	public static BaseSystemLevel getLevelInListByName(LinkedList<BaseSystemLevel> listToSearch, String levelName) {
		for(BaseSystemLevel level : listToSearch) {
			if(level.levelName.equals(levelName)) return level;
		}
		return listToSearch.get(0); // if not find get the first level
	}

}
