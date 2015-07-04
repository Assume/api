package scripts.api.scriptapi.paint;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;

public enum SkillData {
	ATTACK(SKILLS.ATTACK, 1), STRENGTH(SKILLS.STRENGTH, 1), DEFENCE(
			SKILLS.DEFENCE, 1), RANGED(SKILLS.RANGED, 1), PRAYER(SKILLS.PRAYER), MAGIC(
			SKILLS.MAGIC, 1), HITPOINTS(SKILLS.HITPOINTS, 1), CRAFTING(
			SKILLS.CRAFTING), MINING(SKILLS.MINING), SMITHING(SKILLS.SMITHING), FISHING(
			SKILLS.FISHING), COOKING(SKILLS.COOKING), FIREMAKING(
			SKILLS.FIREMAKING), WOODCUTTING(SKILLS.WOODCUTTING), AGILITY(
			SKILLS.AGILITY), HERBLORE(SKILLS.HERBLORE), THIEVING(
			SKILLS.THIEVING), FLETCHING(SKILLS.FLETCHING), SLAYER(
			SKILLS.SLAYER, 1), FARMING(SKILLS.FARMING), CONSTRUCIONT(
			SKILLS.CONSTRUCTION), HUNTER(SKILLS.HUNTER);

	public static final int COMBAT_TYPE = 1;

	public static final int NON_COMBAT_TYPE = 2;

	public static final int ALL_TYPE = 3;

	private SKILLS skill;
	private int type;

	private int start_exp;
	private int start_level;
	private int exp;
	private int level;
	private int exp_to_next_level;
	private int percent_to_next_level;

	SkillData(SKILLS skill) {
		this.skill = skill;
		this.start_exp = 0;
		this.start_level = 0;
		this.type = NON_COMBAT_TYPE;
	}

	SkillData(SKILLS skill, int type) {
		this.skill = skill;
		this.start_exp = 0;
		this.start_level = 0;
		this.type = type;
	}

	private void init() {
		this.start_exp = Skills.getXP(skill);
		this.start_level = skill.getActualLevel();
	}

	public int getType() {
		return this.type;
	}

	public int getExperienceGained() {
		return this.exp;
	}

	public int getLevelsGained() {
		return this.level;
	}

	public int getExperienceToNextLevel() {
		return this.exp_to_next_level;
	}

	public int getPercentToNextLevel() {
		return this.percent_to_next_level;
	}

	public static void initiate() {
		for (SkillData x : SkillData.values())
			x.init();
	}

	public void update() {
		this.exp = skill.getXP() - this.start_exp;
		this.level = skill.getActualLevel() - this.start_level;
		this.exp_to_next_level = Skills.getXPToNextLevel(skill);
		this.percent_to_next_level = Skills.getPercentToNextLevel(skill);
	}

	public static void updateAll() {
		for (SkillData x : values())
			x.update();
	}

	public int getStartingLevel() {
		return this.start_level;
	}

	public static int getTotalExperienceGained() {
		int tot = 0;
		for (SkillData x : values())
			tot += x.getExperienceGained();
		return tot;
	}

	public static SkillData[] getAllForType(int type) {
		if (type == ALL_TYPE)
			return values();
		if (type < 1 || type > 3)
			return new SkillData[0];
		List<SkillData> temp_list = new ArrayList<SkillData>();
		for (SkillData x : values())
			if (x.getType() == type)
				temp_list.add(x);
		return temp_list.toArray(new SkillData[temp_list.size()]);

	}

	public static SkillData[] getSkillsWithExperienceGained(SkillData[] all_for_type) {
		List<SkillData> skills = new ArrayList<SkillData>();
		for (SkillData x : all_for_type)
			if (x.getExperienceGained() > 0)
				skills.add(x);
		return skills.toArray(new SkillData[skills.size()]);

	}
}
