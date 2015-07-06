package scripts.api.scriptapi.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api.Timing;

import scripts.api.scriptapi.paint.paintables.ButtonDisplay;
import scripts.api.scriptapi.paint.paintables.generic.OpenButton;

public abstract class Paintable<T> {

	private static final List<Paintable<?>> PAINTABLES = new ArrayList<Paintable<?>>();

	private static boolean collapse_all = false;

	public static boolean allElementsCollapsed() {
		return collapse_all;
	}

	/*
	 * Sets the value of hide_all_elements to the value of what
	 * 
	 * @param what true if to show all elements, false otherwise
	 */
	public static void setHideAllElements(boolean what) {
		collapse_all = what;
	}

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

	public Paintable(T t, int x, int y) {
		this(t, x, y, true);
	}

	public Paintable(T t, int x, int y, boolean collapseable) {
		this.t = t;
		this.is_open = true;
		this.collapseable = collapseable;
		this.x = x;
		this.y = y;
		if (!(this instanceof OpenButton)) {
			this.open_button = new OpenButton(x, y, this);
			this.open_button.register();
		}
		this.last_state_change = System.currentTimeMillis() - 5000;
	}

	public void register() {
		PAINTABLES.add(this);
	}

	/*
	 * Removes self from the list of Paintables
	 */
	public void remove() {
		PAINTABLES.remove(this);
	}

	protected abstract void onClick();

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
		if (Timing.timeFromMark(last_state_change) < 500)
			return;
		this.is_open = what;
		this.last_state_change = System.currentTimeMillis();
	}

	public static void drawAll(Graphics g, long time) {
		if (allElementsCollapsed()) {
			for (Paintable<?> x : PAINTABLES)
				if (!x.collapseable && x.isOpen())
					x.draw(g, time);
		} else {
			for (Paintable<?> x : PAINTABLES) {
				if (x.isOpen())
					x.draw(g, time);
				else {
					if (x.open_button != null && x.collapseable)
						x.open_button.draw(g, time);
				}
			}
		}
	}

	/*
	 * Calls the onClick method on all open Paintables that contain Point p
	 * 
	 * @param p the location of the click
	 */
	public static void onClick(Point p) {
		for (Paintable<?> x : PAINTABLES)
			if (x.isOpen() && x.isInClick(p))
				x.onClick();
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

	/*
	 * @param p the point being checked
	 * 
	 * @return true if any element contains Point p, else false
	 */
	public static boolean isAnyInClick(Point p) {
		for (Paintable<?> x : PAINTABLES)
			if (x.is_open && x.isInClick(p))
				return true;
		return false;
	}

}
