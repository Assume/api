package scripts.api.scriptapi.paint.paintables;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import scripts.api.scriptapi.paint.Paintable;

public class ImageDisplay extends Paintable<BufferedImage> {

	public ImageDisplay(BufferedImage img, int x, int y) {
		super(img, x, y);
	}

	@Override
	public void draw(Graphics g, long time) {
		g.drawImage(super.get(), x, y, null);

	}

	@Override
	public int getWidth() {
		return super.get().getWidth();
	}

	@Override
	public int getHeight() {
		return super.get().getHeight();
	}

	@Override
	public boolean isInClick(Point p) {
		Rectangle rec = new Rectangle(x, y, super.get().getWidth(), super.get()
				.getHeight());
		return rec.contains(p);
	}
}
