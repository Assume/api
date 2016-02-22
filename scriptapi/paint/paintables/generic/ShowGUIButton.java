package scripts.api.scriptapi.paint.paintables.generic;

import java.awt.Point;

import javax.swing.JFrame;

import scripts.api.scriptapi.paint.paintables.ButtonDisplay;

public class ShowGUIButton extends ButtonDisplay {

	private JFrame gui;

	public ShowGUIButton(JFrame gui) {
		this(230, 460, 12, gui);
	}

	public ShowGUIButton(int x, int y, int height, JFrame gui) {
		this(true, "Show GUI", x, y, 57, height, gui);
	}

	private ShowGUIButton(boolean can_hide, String t, int x, int y, int width,
			int height, JFrame gui) {
		super(t, x, y, width, height);
		this.gui = gui;
	}

	@Override
	public void onClick(Point p) {
		this.gui.setVisible(true);
	}

}
