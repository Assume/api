package scripts.api.scriptapi.paint.paintables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
		Graphics2D gd = (Graphics2D) g;
		gd.setColor(DARK_GREY);
		/*
		 * (int) ((super.get().length * 19) - (super.get().length == 1 ? -5 :
		 * (Math.floor(super.get().length / 5) * 3)))
		 */
		Rectangle outer_rectangle = new Rectangle(x, y, width + getWidthAddition(getWidestData(g)),
				(super.get().length * 20) + 10);
		gd.fill(outer_rectangle);
		gd.setFont(ARIAL_SIZE_ELEVEN);
		Rectangle draw_rectangle = new Rectangle(x + 5, y + 7, 9, 15);
		boolean drawn_once = false;
		for (int i = 0; i < super.get().length; i++) {
			gd.setColor(VERY_LIGHT_GREY);
			int length = super.getStringPixelLength(get()[i], g);
			if (drawn_once)
				draw_rectangle.translate(0, 20);
			draw_rectangle.setSize(draw_rectangle.width + length, draw_rectangle.height);
			gd.fill(draw_rectangle);
			gd.setColor(Color.BLACK);
			gd.draw(draw_rectangle);
			gd.drawString(get()[i], x + 10, (int) draw_rectangle.getCenterY() + 5);
			draw_rectangle.setSize(draw_rectangle.width - length, draw_rectangle.height);
			drawn_once = true;
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

	private int getWidthAddition(int widest) {
		return widest < width ? 0 : widest - width + 30;
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
