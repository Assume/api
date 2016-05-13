package scripts.api.scriptapi.paint.paintables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import org.tribot.api2007.types.RSCharacter;

import scripts.api.scriptapi.paint.Paintable;

public class RSCharacterHealthDisplay extends Paintable<RSCharacter> {

	private static final int DEFAULT_X = 5;

	private static final int DEFAULT_Y = 15;

	private static final int WIDTH = 125;
	private static final int HEIGHT = 30;

	public RSCharacterHealthDisplay() {
		this(null, DEFAULT_X, DEFAULT_Y);
	}

	public RSCharacterHealthDisplay(RSCharacter t) {
		this(t, DEFAULT_X, DEFAULT_Y);
	}

	public RSCharacterHealthDisplay(RSCharacter t, int x, int y) {
		super(t, x, y);
	}

	@Override
	public void draw(Graphics g, long time) {
		if (super.get() == null || !super.get().isInCombat()) 
			return;
		
		g.setFont(ARIAL_SIZE_ELEVEN);
		// OUTER BOX
		g.setColor(TRANSPARENT_GREY);
		g.fillRect(super.x, super.y, WIDTH, HEIGHT);

		g.setColor(Color.BLACK);
		g.drawRect(super.x, super.y, WIDTH, HEIGHT);

		int amount = getBarPixelLength();
		
		// FULL BAR
		g.setColor(Color.GRAY);
		g.fillRect(super.x + 3 + amount, super.y + 14, 120 - amount, 14);

		// PERCENT BAR
		g.setColor(DARK_GREY);
		g.fillRect(super.x + 3, super.y + 14, amount, 14);

		g.setColor(Color.WHITE);
		g.drawString(super.get().getName(), super.x + 50, super.y + 11);

		g.drawString(
				super.get().getHealth() + "/" + super.get().getMaxHealth(),
				super.x + 51, super.y + 25);

	}

	private int getBarPixelLength() {
		if (super.get() == null)
			return 0;
		int health = super.get().getMaxHealth();
		if (health == 0)
			return 0;
		return (int) Math
				.ceil(((double) super.get().getHealth() / (double) health) * 120);
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public boolean isInClick(Point p) {
		return new Rectangle(super.x, super.y, WIDTH, HEIGHT).contains(p);
	}

}
