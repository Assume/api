package scripts.api.scriptapi.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;

import org.tribot.api.Timing;

import scripts.api.scriptapi.paint.paintables.ButtonDisplay;
import scripts.api.scriptapi.paint.paintables.generic.OpenButton;

public abstract class Paintable<T> {

	protected final static Color DARK_GREY = new Color(0, 0, 0, 175);
	protected final static Color VERY_LIGHT_GREY = new Color(255, 255, 255, 150);
	protected final static Color TRANSPARENT_GREY = new Color(155, 155, 155,
			110);

	protected final static Font ARIAL_SIZE_ELEVEN = new Font("Arial", 0, 11);
	protected final static Font ARIAL_SIZE_NINE = new Font("Arial", 0, 9);

	private T t;

	private boolean is_open;
	private boolean collapseable;

	protected int x;
	protected int y;

	protected long last_state_change;

	protected ButtonDisplay open_button;

	protected PaintHandler handler;

	public Paintable(int x, int y) {
		this(null, x, y);
	}

	public Paintable(T t, int x, int y) {
		this(t, x, y, true);
	}
	
	public Paintable(T t, int x, int y, boolean collapseable) {
		this.t = t;
		this.is_open = true;
		this.collapseable = collapseable;
		this.x = x;
		this.y = y;
		this.last_state_change = System.currentTimeMillis() - 5000;
	}

	public void register(PaintHandler handler) {
		this.handler = handler;
		this.handler.register(this);
		if (!(this instanceof OpenButton)) {
			this.open_button = new OpenButton(x, y, this);
			this.open_button.register(handler);
		}
	}

	public final PaintHandler getHandler() {
		return this.handler;
	}

	protected void onClick(Point p) {
		this.setOpen(false);
		this.open_button.setOpen(true);
	}

	public abstract int getWidth();
	
	public abstract int getHeight();
	
	/*
	 * Draws the Paintable object
	 */
	public abstract void draw(Graphics g, long time);

	/*
	 * Generic onClick method for all Paintables, anything extending {@link
	 * scripts.api.scriptapi.paint.ButtonDisplay} must override this
	 */

	protected abstract boolean isInClick(Point p);

	/*
	 * Draws a button to allow the user to reopen the Paintable
	 * 
	 * @param g the Graphics object
	 * 
	 * @param time run time of the script
	 */
	protected void drawCollapsedButton(Graphics g, long time) {
		if ((this instanceof OpenButton || x == -1 || y == -1))
			return;
		this.open_button.draw(g, time);
	}

	/*
	 * @param t the value to update stored value t with
	 */
	public void update(T t) {
		this.t = t;
	}

	/*
	 * @return the generic value stored
	 */
	public T get() {
		return this.t;
	}

	/*
	 * @return true if open, false otherwise
	 */
	public boolean isOpen() {
		return this.is_open;
	}

	/*
	 * @param what true if individual element should be displayed, false
	 * otherwise
	 */
	public void setOpen(boolean what) {
		if (Timing.timeFromMark(last_state_change) < 300)
			return;
		this.is_open = what;
		this.last_state_change = System.currentTimeMillis();
	}

	/*
	 * @param s the String to be evaluated
	 * 
	 * @param g the graphics object
	 * 
	 * @return the length of the String in pixels, 0 if g or s == null
	 */
	protected static int getStringPixelLength(String s, Graphics g) {
		if (s == null || g == null)
			return 0;
		int x = 0;
		for (int c1 = 0; c1 < s.length(); c1++) {
			char ch = s.charAt(c1);
			x += g.getFontMetrics().charWidth(ch);
		}
		return x;
	}

	/*
	 * @param num the number to be formatted
	 * 
	 * @return the value as a String, at its smallest value, with a letter
	 * following signifying its size
	 */
	public static String formatNumber(int num) {
		DecimalFormat df = new DecimalFormat("0");
		double i = num;
		if (i >= 1000000)
			if (i % 1000000 == 0)
				return df.format(i / 1000000) + "M";
			else
				return (i / 1000000) + "M";
		if (i >= 1000)
			return df.format((i / 1000)) + "k";
		return "" + num;
	}

	public boolean isCollapseable() {
		return this.collapseable;
	}

}
