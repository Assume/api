package scripts.api.scriptapi.paint.paintables.generic;

import scripts.api.scriptapi.paint.CGUI;
import scripts.api.scriptapi.paint.paintables.ButtonDisplay;

public class ShowGUIButton extends ButtonDisplay {

	private CGUI gui;

	public ShowGUIButton(int x, int y, int height, CGUI gui) {
		this(true, "Show GUI", x, y, 57, height, gui);
	}

	private ShowGUIButton(boolean can_hide, String t, int x, int y, int width,
			int height, CGUI gui) {
		super(t, x, y, width, height);
		this.gui = gui;
	}

	@Override
	protected void onClick() {
		this.gui.setVisible(true);
	}

}
