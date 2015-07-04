package scripts.api.scriptapi.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api.Timing;

import scripts.api.scriptapi.paint.generic.OpenButton;

public abstract class Paintable<T> {

	protected final static Color DARK_GREY = new Color(0, 0, 0, 175);
	protected final static Color VERY_LIGHT_GREY = new Color(
			Color.BLACK.getRed(), Color.BLACK.getGreen(),
			Color.BLACK.getBlue(), 125);
	protected final static Color TRANSPARENT_GREY = new Color(155, 155, 155,
			110);

	protected final static Font ARIAL_SIZE_ELEVEN = new Font("Arial", 0, 11);
	protected final static Font ARIAL_SIZE_NINE = new Font("Arial", 0, 9);

	private static final List<Paintable<?>> PAINTABLES = new ArrayList<Paintable<?>>();

	private static boolean hide_all_elements = false;

	private T t;
	private boolean isOpen;
	private boolean can_hide;

	protected int x;
	protected int y;

	protected long last_state_change;

	protected ButtonDisplay open_button;

	public Paintable(T t, int x, int y) {
		this(t, x, y, true);
	}

	public Paintable(T t, int x, int y, boolean can_hide) {
		this.t = t;
		this.isOpen = true;
		this.can_hide = can_hide;
		this.x = x;
		this.y = y;
		if (!(this instanceof OpenButton))
			this.open_button = new OpenButton(x, y, this);
		this.last_state_change = System.currentTimeMillis();
		PAINTABLES.add(this);
	}

	/*
	 * Draws the Paintable object
	 */
	public abstract void draw(Graphics g, long time);

	/*
	 * Generic onClick method for all Paintables, anything extending {@link
	 * scripts.api.scriptapi.paint.ButtonDisplay} must override this
	 */
	protected void onClick() {
		if (Timing.timeFromMark(last_state_change) > 100 && can_hide) {
			setOpen(false);
		}
	}

	protected abstract boolean isInClick(Point p);

	/*
	 * Draws a button to allow the user to reopen the Paintable
	 * 
	 * @param g the Graphics object
	 * 
	 * @param time run time of the script
	 */
	protected void drawCollapsedButton(Graphics g, long time) {
		if ((this instanceof OpenButton || x == -1 || y == -1)
				&& (!(this instanceof ExperienceDisplay)))
			return;
		if (!this.open_button.isOpen())
			this.open_button.setOpen(true);
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
		return this.isOpen;
	}

	/*
	 * @param what true if individual element should be displayed, false
	 * otherwise
	 */
	public void setOpen(boolean what) {
		this.isOpen = what;
		this.last_state_change = System.currentTimeMillis();
	}

	/*
	 * Removes self from the list of Paintables
	 */
	public void remove() {
		PAINTABLES.remove(this);
	}

	public static void drawAll(Graphics g, long time) {
		for (Paintable<?> x : PAINTABLES) {
			if (!hide_all_elements) {
				if (x.isOpen)
					x.draw(g, time);
				else
					x.drawCollapsedButton(g, time);
			} else {
				if (!x.can_hide)
					if (x.isOpen)
						x.draw(g, time);
					else
						x.drawCollapsedButton(g, time);
			}
		}
	}

	public static boolean elementsAreHidden() {
		return hide_all_elements;
	}

	/*
	 * Sets the value of hide_all_elements to the value of what
	 * 
	 * @param what true if to show all elements, false otherwise
	 */
	public static void setHideAllElements(boolean what) {
		hide_all_elements = what;
	}

	/*
	 * Calls the onClick method on all open Paintables that contain Point p
	 * 
	 * @param p the location of the click
	 */
	public static void onClick(Point p) {
		for (Paintable<?> x : PAINTABLES)
			if (x.isOpen && x.isInClick(p))
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
			if (x.isOpen && x.isInClick(p))
				return true;
		return false;
	}

}
