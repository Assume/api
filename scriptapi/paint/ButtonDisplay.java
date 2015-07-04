package scripts.api.scriptapi.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class ButtonDisplay extends Paintable<String> {

	private int height;
	private int width;

	public ButtonDisplay(String t, int x, int y, int width, int height) {
		this(false, t, x, y, width, height);
	}

	private ButtonDisplay(boolean can_hide, String t, int x, int y, int width,
			int height) {
		super(t, x, y, can_hide);
		this.height = height;
		this.width = width;
	}

	@Override
	public void draw(Graphics g, long time) {
		g.setColor(TRANSPARENT_GREY);
		g.setFont(ARIAL_SIZE_ELEVEN);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		g.drawString(super.get(), x + getAddition(width), y + 10);
	}

	@Override
	protected abstract void onClick();

	private int getAddition(int total_length) {
		if (total_length < 20)
			return 3;
		if (total_length < 60)
			return 4;
		if (total_length < 100)
			return 5;
		return 6;
	}

	@Override
	protected boolean isInClick(Point p) {
		Rectangle rec = new Rectangle(x, y, width, height);
		return rec.contains(p);
	}

}
