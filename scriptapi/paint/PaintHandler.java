package scripts.api.scriptapi.paint;

import java.awt.Graphics;
import java.awt.Point;

public abstract class PaintHandler {

	public abstract void update(long run_time);

	public abstract void draw(Graphics g, long run_time);

	public final void onClick(Point p) {
		Paintable.onClick(p);
	}

}
