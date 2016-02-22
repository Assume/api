package scripts.api.scriptapi.paint.paintables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import scripts.api.scriptapi.paint.Paintable;

public class DataDisplay extends Paintable<String[]> {

	private int width;
	private int height;

	public DataDisplay() {
		this(new String[0], 255, 349, 260, 76);
	}

	public DataDisplay(String[] t, int x, int y, int width, int height) {
		super(t, x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics g, long time) {
		g.setColor(DARK_GREY);
		int widest = getWidestData(g);
		int width_addition = widest < width ? 0 : widest - width + 30;
		g.fillRect(
				x,
				y,
				width + width_addition,
				(int) ((super.get().length * 19) - (super.get().length == 1 ? -5
						: (Math.floor(super.get().length / 5) * 3))));
		g.setFont(ARIAL_SIZE_ELEVEN);
		int counter = 0;
		for (String s : super.get()) {
			g.setColor(VERY_LIGHT_GREY);
			int length = super.getStringPixelLength(s, g);
			g.fillRect(x + 5, (y + 4) + (17 * counter), length + 20, 14);
			g.setColor(Color.BLACK);
			g.drawRect(x + 5, (y + 4) + (17 * counter), length + 20, 14);
			g.drawString(s, x + 15, (y + 15) + 17 * counter);
			counter++;
		}
	}

	private int getWidestData(Graphics g) {
		int longest = 0;
		for (String x : super.get()) {
			int length = super.getStringPixelLength(x, g);
			if (length > longest)
				longest = length;
		}
		return longest;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean isInClick(Point p) {
		Rectangle rec = new Rectangle(x, y, width, height);
		return rec.contains(p);
	}

}
