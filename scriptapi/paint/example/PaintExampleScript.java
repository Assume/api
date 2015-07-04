package scripts.api.scriptapi.paint.example;

import java.awt.Graphics;
import java.awt.Point;

import org.tribot.script.Script;
import org.tribot.script.interfaces.MouseActions;
import org.tribot.script.interfaces.Painting;

import scripts.api.scriptapi.paint.CGUI;
import scripts.api.scriptapi.paint.PaintHandler;

public class PaintExampleScript extends Script implements Painting,
		MouseActions {

	private PaintHandler paint_handler;
	private CGUI gui;

	public PaintExampleScript() {
		this.gui = new CGUI();
		this.paint_handler = new ExamplePaintHandler(this.gui, "1.0.0");
	}

	@Override
	public void run() {
		while (true) {
			// your other script code
			this.paint_handler.update(getRunningTime());
		}
	}

	@Override
	public void onPaint(Graphics arg0) {
		this.paint_handler.draw(arg0, getRunningTime());
	}

	@Override
	public void mouseClicked(Point arg0, int arg1, boolean arg2) {
		if (!arg2)
			this.paint_handler.onClick(arg0);

	}

	@Override
	public void mouseDragged(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(Point arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

}
