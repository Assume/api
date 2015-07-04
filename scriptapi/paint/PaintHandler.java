package scripts.api.scriptapi.paint;

import java.awt.Graphics;
import java.awt.Point;

public abstract class PaintHandler {

	public abstract void update(long run_time);

	public abstract void draw(Graphics g, long run_time);

	public final void onClick(Point p) {
		Paintable.onClick(p);
	}

	protected String getFormattedTime(long time) {
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

}
