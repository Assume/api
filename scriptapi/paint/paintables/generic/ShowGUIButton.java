package scripts.api.scriptapi.paint.paintables.generic;

import java.awt.Point;

import scripts.api.scriptapi.paint.paintables.ButtonDisplay;
import scripts.api.scriptapi.paint.types.CGUI;

public class ShowGUIButton extends ButtonDisplay {

	private CGUI gui;

	public ShowGUIButton(CGUI gui) {
		this(230, 459, 12, gui);
	}

	public ShowGUIButton(int x, int y, int height, CGUI gui) {
		this(true, "Show GUI", x, y, 57, height, gui);
	}

	private ShowGUIButton(boolean can_hide, String t, int x, int y, int width,
			int height, CGUI gui) {
		super(t, x, y, width, height);
		this.gui = gui;
	}

	@Override
	public void onClick(Point p) {
		this.gui.setVisible(true);
	}

}
