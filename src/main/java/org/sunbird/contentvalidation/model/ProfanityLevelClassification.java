package org.sunbird.contentvalidation.model;

import java.util.HashMap;
import java.util.Map;

public enum ProfanityLevelClassification {

	NOT_OFFENSIVE("Not Offensive", 1), OFFENSIVE("Offensive", 2), EXTREMELY_OFFENSIVE("Extremely Offensive", 3),
	UNDEFINED("Undefined", 4);

	private static final Map<String, ProfanityLevelClassification> BY_LEVEL_NAME = new HashMap<>(
			3);
	private static final Map<Integer, ProfanityLevelClassification> BY_LEVEL_WEIGHT = new HashMap<>(
			3);

	static {
		for (ProfanityLevelClassification p : values()) {
			BY_LEVEL_NAME.put(p.levelName, p);
			BY_LEVEL_WEIGHT.put(p.level, p);
		}
	}

	private String levelName;
	private int level;
	
	public String getLevelName() {
		return levelName;
	}

	public static ProfanityLevelClassification getLevelByName(String levelName) {
		ProfanityLevelClassification p = BY_LEVEL_NAME.get(levelName);
		if (p != null) {
			return p;
		}
		return UNDEFINED;
	}

	public static ProfanityLevelClassification getLevelNameByWeight(int level) {
		ProfanityLevelClassification p = BY_LEVEL_WEIGHT.get(level);
		if (p != null) {
			return p;
		}
		return UNDEFINED;
	}

	private ProfanityLevelClassification(String levelName, int level) {
		this.levelName = levelName;
		this.level = level;
	}
}
