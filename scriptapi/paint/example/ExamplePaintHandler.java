package scripts.api.scriptapi.paint.example;

import java.awt.Graphics;

import scripts.api.scriptapi.paint.CGUI;
import scripts.api.scriptapi.paint.PaintHandler;
import scripts.api.scriptapi.paint.Paintable;
import scripts.api.scriptapi.paint.SkillData;
import scripts.api.scriptapi.paint.paintables.DataDisplay;
import scripts.api.scriptapi.paint.paintables.ExperienceDisplay;
import scripts.api.scriptapi.paint.paintables.generic.ShowGUIButton;

public class ExamplePaintHandler extends PaintHandler {

	private String version;

	private ExperienceDisplay experience_display;
	private ShowGUIButton show_gui_button;
	private DataDisplay generic_data_display;

	public ExamplePaintHandler(CGUI gui, String version) {
		this.experience_display = new ExperienceDisplay(
				SkillData.NON_COMBAT_TYPE);
		this.show_gui_button = new ShowGUIButton(gui);
		this.generic_data_display = new DataDisplay();

	}

	private String[] getGenericDataDisplay(long run_time) {
		String[] info_array = { "Runtime: " + getFormattedTime(run_time),
				"Version" + version };
		return info_array;
	}

	@Override
	public void update(long run_time) {
		this.generic_data_display.update(getGenericDataDisplay(run_time));
		SkillData.updateAll();
	}

	@Override
	public void draw(Graphics g, long run_time) {
		Paintable.drawAll(g, run_time);

	}

}
