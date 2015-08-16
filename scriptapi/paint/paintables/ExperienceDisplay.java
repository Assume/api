package scripts.api.scriptapi.paint.paintables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import scripts.api.scriptapi.paint.Calculations;
import scripts.api.scriptapi.paint.Paintable;
import scripts.api.scriptapi.paint.SkillData;

public class ExperienceDisplay extends Paintable<Integer> {

	private boolean move_up;

	public ExperienceDisplay(Integer type) {
		this(type, true);
	}

	public ExperienceDisplay(Integer type, boolean move_up) {
		this(type, 8, 320, move_up);
	}

	public ExperienceDisplay(Integer type, int x, int y, boolean move_up) {
		super(type, x, y);
		this.move_up = move_up;
	}

	@Override
	public int getWidth() {
		return 242;
	}

	@Override
	public int getHeight() {
		SkillData[] data = SkillData.getSkillsWithExperienceGained(SkillData
				.getAllForType(super.get()));
		return 16 * data.length;
	}

	@Override
	public void draw(Graphics g, long time) {

		int i = 0;
		for (final SkillData skill : SkillData.getAllForType(super.get())) {
			if (skill.getExperienceGained() > 0) {
				g.setFont(ARIAL_SIZE_NINE);
				int t_y;
				if (this.move_up)
					t_y = super.y - (16 * i);
				else
					t_y = super.y + (16 * i);

				int percent = skill.getPercentToNextLevel();
				int amount = (percent * 242) / 100;
				// Bar
				g.setColor(Color.GRAY);
				g.fillRect(super.x + amount, t_y, 242 - amount, 13);

				// Progress
				g.setColor(DARK_GREY);
				g.fillRect(super.x, t_y,
						amount, 13);

				// Trim
				g.setColor(Color.BLACK);
				g.drawRect(super.x, t_y, 242, 13);

				// Text
				g.setColor(Color.WHITE);
				g.drawString(toString(time, skill), super.x + 3, t_y + 11);

				// Close button
				g.setFont(ARIAL_SIZE_ELEVEN);
				g.setColor(TRANSPARENT_GREY);
				g.fillRect(super.x + 244, t_y, 13, 13);
				g.setColor(Color.BLACK);
				g.drawRect(super.x + 244, t_y, 13, 13);
				g.drawString("x", super.x + 249, t_y + 10);

				i++;
			}
		}
	}

	private String toString(Long runtime, SkillData skill) {
		return (skill.toString() + " | "
				+ (skill.getLevelsGained() + skill.getStartingLevel()) + "("
				+ skill.getLevelsGained() + ") | "
				+ Calculations.formatNumber(skill.getExperienceGained()) + " XP | "
				+ Calculations.formatNumber(getExperiencePerHour(runtime, skill))
				+ " XP/HR | TTL: " + getFormattedTime(getTimeToLevel(runtime,
					skill)));
	}

	private int getExperiencePerHour(long runtime, SkillData skill) {
		return (int) ((3600000.0 / runtime) * skill.getExperienceGained());
	}

	private long getTimeToLevel(long runtime, SkillData skill) {
		long ttl = (long) ((skill.getExperienceToNextLevel() * 3600000.0) / getExperiencePerHour(
				runtime, skill));
		return ttl > 300000000 ? 0 : ttl;
	}

	private String getFormattedTime(long time) {
		long seconds = 0;
		long minutes = 0;
		long hours = 0;
		seconds = time / 1000;
		if (seconds >= 60) {
			minutes = seconds / 60;
			seconds -= (minutes * 60);
		}
		if (minutes >= 60) {
			hours = minutes / 60;
			minutes -= (hours * 60);
		}
		return (hours + ":" + minutes + ":" + seconds);
	}

	@Override
	public void onClick(Point p) {
		if (p.x <= super.x + 242) {
			super.onClick(p);
			return;
		}
		SkillData skill = getSkillClicked(p);
		if (skill != null)
			skill.reset();

	}

	private SkillData getSkillClicked(Point p) {
		SkillData[] data = SkillData.getSkillsWithExperienceGained(SkillData
				.getAllForType(super.get()));
		int t_t = Math.abs((super.y + data.length * 16) - p.y);
		if (t_t <= 16 && data.length > 0)
			return data[0];
		int index = (int) Math.ceil(((double) t_t - 32) / 16.0);
		if (index >= data.length)
			return null;
		return data[index];
	}

	@Override
	protected boolean isInClick(Point p) {
		SkillData[] data = SkillData.getSkillsWithExperienceGained(SkillData
				.getAllForType(super.get()));
		if (data.length == 0)
			return false;
		int height = 16 * data.length;
		Rectangle rec = new Rectangle(x, (y + 13) - height, 257, height);
		return rec.contains(p);
	}

}
