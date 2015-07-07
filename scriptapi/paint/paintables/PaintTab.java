package scripts.api.scriptapi.paint.paintables;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import scripts.api.scriptapi.paint.Paintable;

public class PaintTab extends Paintable<String> {

	private PaintPanel panel;

	private final List<Paintable<?>> paintables;

	public PaintTab(String name, PaintPanel panel) {
		super(name, panel.x, panel.y + 15);
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
		g.setColor(TRANSPARENT_GREY);
		g.fillRect(super.x, super.y, panel.getWidth(), panel.getHeight());
		int total_height = 0;
		for (Paintable<?> temp_paintable : paintables) {
			temp_paintable.y = this.panel.y + 20 + total_height;
			temp_paintable.x = this.panel.x + 5;
			temp_paintable.draw(g, time);
			total_height += temp_paintable.getHeight() + 15;
		}
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
