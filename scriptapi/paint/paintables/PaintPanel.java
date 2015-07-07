package scripts.api.scriptapi.paint.paintables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import scripts.api.scriptapi.paint.Paintable;

public class PaintPanel extends Paintable<PaintTab> {

	private static final int TAB_SELECTOR_WIDTH = 40;

	private static final int TAB_SELECTOR_HEIGHT = 15;

	private List<PaintTab> tabs;

	private PaintTab current_tab;

	private int width;
	private int height;

	public PaintPanel(int x, int y, int width, int height) {
		super(null, x, y, false);
		this.tabs = new ArrayList<PaintTab>();
		this.width = width;
		this.height = height;
		this.current_tab = null;
	}

	@Override
	public void draw(Graphics g, long time) {
		int cur_x = super.x;
		for (PaintTab x : tabs) {
			g.setColor(x == current_tab ? Color.GRAY : VERY_LIGHT_GREY);
			g.fillRect(cur_x, super.y, TAB_SELECTOR_WIDTH, TAB_SELECTOR_HEIGHT);
			g.drawString(x.get(), cur_x + 10, super.y + 5);
			g.setColor(Color.BLACK);
			g.drawRect(cur_x, super.y, TAB_SELECTOR_WIDTH, TAB_SELECTOR_HEIGHT);
			cur_x += 42;
		}
		if (this.current_tab != null && this.current_tab.isOpen())
			this.current_tab.draw(g, time);
	}

	public void addTab(PaintTab tab) {
		this.tabs.add(tab);
		if (this.current_tab == null)
			this.current_tab = tabs.get(0);
	}

	@Override
	protected boolean isInClick(Point p) {
		Rectangle rec = new Rectangle(super.x, super.y, this.width, this.height);
		return rec.contains(p);
	}

	@Override
	protected void onClick(Point p) {
		PaintTab tab = getClickedTab(p);
		if (tab == null)
			return;
		if (tab == current_tab)
			current_tab.setOpen(!current_tab.isOpen());
		else {
			this.current_tab.setOpen(false);
			this.current_tab = tab;
			this.current_tab.setOpen(true);
		}
	}

	private PaintTab getClickedTab(Point p) {
		for (int i = 0; i < tabs.size(); i++) {
			int temp_x = super.x + ((TAB_SELECTOR_WIDTH + 2) * i);
			Rectangle rec = new Rectangle(temp_x, super.y, TAB_SELECTOR_WIDTH,
					TAB_SELECTOR_HEIGHT);
			if (rec.contains(p))
				return tabs.get(i);
		}
		return null;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

}
