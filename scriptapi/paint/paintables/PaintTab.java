package scripts.api.scriptapi.paint.paintables;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import scripts.api.scriptapi.paint.Paintable;

public class PaintTab extends Paintable<String> {

	private PaintPanel panel;

	private boolean draw_background;

	private final List<Paintable<?>> paintables;

	public PaintTab(String name, PaintPanel panel) {
		super(name, panel.x, panel.y + 15);
		this.paintables = new ArrayList<Paintable<?>>();
		this.panel = panel;
		this.draw_background = true;
	}

	public void setDrawBackground(boolean what) {
		this.draw_background = what;
	}

	public void add(Paintable<?> x) {
		this.paintables.add(x);
	}

	public void draw(Graphics g, long time) {
		if (!this.isOpen())
			return;
		g.setColor(TRANSPARENT_GREY);
		if (this.draw_background)
			g.fillRect(super.x, super.y, panel.getWidth(), panel.getHeight());
		int total_height = 0;
		for (Paintable<?> temp_paintable : paintables) {
			temp_paintable.y = this.panel.y + 20 + total_height;
			temp_paintable.x = this.draw_background ? this.panel.x + 5 : this.panel.x;
			temp_paintable.draw(g, time);
			total_height += temp_paintable.getHeight() + 15;
		}
	}

	@Override
	protected boolean isInClick(Point p) {
		return false;
	}

	@Override
	public void onClick(Point p) {
		for (Paintable<?> x : paintables) {
			if (x instanceof ButtonDisplay) {
				ButtonDisplay temp = (ButtonDisplay) x;
				if (temp.isInClick(p))
					temp.onClick(p);
			}
		}

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
