package scripts.api.scriptapi.paint;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import scripts.api.scriptapi.paint.paintables.tabs.PaintTab;

public abstract class PaintHandler {

	private final List<Paintable<?>> paintables;

	private boolean hide_all_elements;
	
	protected String version;

	public PaintHandler(String version) {
		this.version = version;
		this.paintables = new ArrayList<Paintable<?>>();
		this.hide_all_elements = false;
	}

	public void register(Paintable<?> paintable) {
		this.paintables.add(paintable);
	}

	public boolean allElementsCollapsed() {
		return this.hide_all_elements;
	}

	public void setCollapseAllElements(boolean what) {
		this.hide_all_elements = what;
	}

	public abstract void update(long run_time);

	public abstract void draw(Graphics g, long run_time);

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

	/*
	 * @param p the point being checked
	 * 
	 * @return true if any element contains Point p, else false
	 */
	public final boolean isAnyInClick(Point p) {
		for (Paintable<?> x : paintables)
			if (x.isOpen() && x.isInClick(p))
				return true;
		return false;
	}

	public final void drawAll(Graphics g, long time) {
		if (allElementsCollapsed()) {
			for (Paintable<?> x : paintables)
				if (!x.isCollapseable() && x.isOpen())
					x.draw(g, time);
		} else {
			for (Paintable<?> x : paintables) {
				if (x.isOpen())
					x.draw(g, time);
				else {
					if (x.open_button != null && x.isCollapseable()
							&& !isPaintTab(x))
						x.open_button.draw(g, time);
				}
			}
		}
	}

	private final boolean isPaintTab(Paintable<?> x) {
		return x instanceof PaintTab;
	}

	/*
	 * Calls the onClick method on all open Paintables that contain Point p
	 * 
	 * @param p the location of the click
	 */
	public final void onClick(Point p) {
		for (Paintable<?> x : paintables)
			if (x.isOpen() && x.isInClick(p)) {
				x.onClick(p);
				return;
			}
	}

}
