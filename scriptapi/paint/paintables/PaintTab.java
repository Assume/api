package scripts.api.scriptapi.paint.paintables;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import scripts.api.scriptapi.paint.Paintable;

public class PaintTab extends Paintable<String> {

	private int x;
	private int y;

	private PaintPanel panel;

	private final List<Paintable<?>> paintables;

	public PaintTab(String name, int x, int y, PaintPanel panel) {
		super(name, x, y);
		this.paintables = new ArrayList<Paintable<?>>();
		this.panel = panel;
		this.setOpen(false);
	}

	public void add(Paintable<?> x) {
		this.paintables.add(x);
	}

	public void draw(Graphics g, long time) {
		if (!this.isOpen())
			return;
		g.fillRect(x, y, panel.getWidth(), panel.getHeight());
		for (Paintable<?> x : paintables)
			x.draw(g, time);

	}

	@Override
	protected boolean isInClick(Point p) {
		return false;
	}

	@Override
	protected void onClick(Point p) {
		return;
	}

	@Override
	public int getWidth() {
		int max_width = 0;
		for (Paintable<?> x : paintables) {
			int temp_width = x.getWidth();
			if (temp_width > max_width)
				max_width = temp_width;
		}
		return max_width;
	}

	@Override
	public int getHeight() {
		int max_height = 0;
		for (Paintable<?> x : paintables) {
			int temp_width = x.getHeight();
			if (temp_width > max_height)
				max_height = temp_width;
		}
		return max_height;
	}

}
