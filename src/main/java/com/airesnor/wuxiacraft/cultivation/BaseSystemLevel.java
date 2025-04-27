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
		BODY_LEVELS.add(new BaseSystemLevel("mortal_body", "body_mortal_1", "Mortal Body", 1, 666.0, 1.0, false, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_mortal_1", "body_mortal_2", "Body Cleansing", 5, 1010.0, 1.8, false, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_mortal_2", "body_mortal_3", "Body Refinment", 5, 1870.0, 3.0, false, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_mortal_3", "body_mortal_4", "Body Hardening", 5, 4095.0, 9.0, true, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_mortal_4", "body_law_1", "Body Transformation", 5, 13382.0, 32.0, true, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_law_1", "body_law_2", "Earth Law Infusion", 10, 91631.0, 130, true, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_law_2", "body_law_3", "Sky Law Infusion", 10, 904520.0, 522.0, true, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_law_3", "body_law_4", "Heavenly Body", 10, 10655642.0, 2193.0, true, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_law_4", "body_immortal_1", "Martial Body", 10, 136439034.0, 9651.0, true, false));
		BODY_LEVELS.add(new BaseSystemLevel("body_immortal_1", "body_immortal_2", "Saint Body", 15, 2043416073.0, 48254.0, true, true));
		BODY_LEVELS.add(new BaseSystemLevel("body_immortal_2", "body_immortal_3", "Half Immortal Body", 15, 30641076279.0, 241274.0, true, true));
		BODY_LEVELS.add(new BaseSystemLevel("body_immortal_3", "body_immortal_4", "Immortal Body", 15, 481030894517.0, 1254629.0, true, true));
		BODY_LEVELS.add(new BaseSystemLevel("body_immortal_4", "body_godhood_1", "True Immortal Body", 15, 7888785412969.0, 6774998.0, true, true));
		BODY_LEVELS.add(new BaseSystemLevel("body_godhood_1", "body_godhood_2", "Immortal Martial Body", 20, 145942067482976.0, 40649991.0, true, true));
		BODY_LEVELS.add(new BaseSystemLevel("body_godhood_2", "body_godhood_3", "Godlike Infusion", 20, 2699926183371534.0, 243899950.0, true, true));
		BODY_LEVELS.add(new BaseSystemLevel("body_godhood_3", "body_godhood_4", "Godly Body", 20, 51838573581725354.0, 1512179694.0, true, true));
		BODY_LEVELS.add(new BaseSystemLevel("body_godhood_4", "body_godhood_4", "True Godly Body", 10000000, 1031587571487599995.0, 9677950043.0, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("mortal_essence", "essence_mortal_1", "Mortal Essence", 1, 666.0, 1.0, false, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_mortal_1", "essence_mortal_2", "Qi Gathering", 5, 1010.0, 1.8, false, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_mortal_2", "essence_mortal_3", "Qi Purification", 5, 1870.0, 3.0, false, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_mortal_3", "essence_mortal_4", "Qi Paths Refinement", 5, 4095.0, 9.0, true, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_mortal_4", "essence_law_1", "DanTian Condensing", 5, 13382.0, 32.0, true, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_law_1", "essence_law_2", "Earth Law", 10, 91631.0, 130, true, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_law_2", "essence_law_3", "Sky Law", 10, 904520.0, 522.0, true, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_law_3", "essence_law_4", "Heavenly Law", 10, 10655642.0, 2193.0, true, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_law_4", "essence_immortal_1", "Martial Law", 10, 136439034.0, 9651.0, true, false));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_immortal_1", "essence_immortal_2", "Immortality Law", 15, 2043416073.0, 48254.0, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_immortal_2", "essence_immortal_3", "Immortality Refinement", 15, 30641076279.0, 241274.0, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_immortal_3", "essence_immortal_4", "Immortal Foundation", 15, 481030894517.0, 1254629.0, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_immortal_4", "essence_godhood_1", "True Immortal", 15, 7888785412969.0, 6774998.0, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_godhood_1", "essence_godhood_2", "Martial Immortal", 20, 145942067482976.0, 40649991.0, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_godhood_2", "essence_godhood_3", "Half God", 20, 2699926183371534.0, 243899950.0, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_godhood_3", "essence_godhood_4", "Godly Phenomenon", 20, 51838573581725354.0, 1512179694.0, true, true));
		ESSENCE_LEVELS.add(new BaseSystemLevel("essence_godhood_4", "essence_godhood_4", "True God", 10000000, 1031587571487599995.0, 9677950043.0, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("mortal_soul", "divine_mortal_1", "Mortal Soul", 1, 666.0, 1.0, false, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_mortal_1", "divine_mortal_2", "Soul Feeling", 5, 1010.0, 1.8, false, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_mortal_2", "divine_mortal_3", "Soul Condensing", 5, 1870.0, 3.0, false, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_mortal_3", "divine_mortal_4", "Soul Forging", 5, 4095.0, 9.0, true, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_mortal_4", "divine_law_1", "Perfect Soul", 5, 13382.0, 32.0, true, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_law_1", "divine_law_2", "Earth Law Sensation", 10, 91631.0, 130, true, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_law_2", "divine_law_3", "Sky Law Sensation", 10, 904520.0, 522.0, true, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_law_3", "divine_law_4", "Heavenly Foundation", 10, 10655642.0, 2193.0, true, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_law_4", "divine_immortal_1", "Martial Core", 10, 136439034.0, 9651.0, true, false));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_immortal_1", "divine_immortal_2", "Divine Spark", 15, 2043416073.0, 48254.0, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_immortal_2", "divine_immortal_3", "Divine Sense", 15, 30641076279.0, 241274.0, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_immortal_3", "divine_immortal_4", "Divine Will", 15, 481030894517.0, 1254629.0, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_immortal_4", "divine_godhood_1", "Divine World", 15, 7888785412969.0, 6774998.0, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_godhood_1", "divine_godhood_2", "Immoral Nebula", 20, 145942067482976.0, 40649991.0, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_godhood_2", "divine_godhood_3", "Divine Galaxy", 20, 2699926183371534.0, 243899950.0, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_godhood_3", "divine_godhood_4", "Godly Domain", 20, 51838573581725354.0, 1512179694.0, true, true));
		DIVINE_LEVELS.add(new BaseSystemLevel("divine_godhood_4", "divine_godhood_4", "True Gods Universe", 10000000, 1031587571487599995.0, 9677950043.0, true, true));
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
