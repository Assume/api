package scripts.api.scriptapi.paint.paintables;

import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api2007.types.RSGroundItem;

import scripts.api.scriptapi.paint.Paintable;

public class LootItemDisplay extends Paintable<RSGroundItem> {

	public LootItemDisplay(RSGroundItem t, int x, int y) {
		super(t, x, y);
	}

	@Override
	public void draw(Graphics g, long time) {

	}

	@Override
	protected boolean isInClick(Point p) {
		return false;
	}

	@Override
	public void update(RSGroundItem t) {

	}

}
