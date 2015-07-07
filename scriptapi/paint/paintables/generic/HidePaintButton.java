package scripts.api.scriptapi.paint.paintables.generic;

import java.awt.Point;

import scripts.api.scriptapi.paint.paintables.ButtonDisplay;

public class HidePaintButton extends ButtonDisplay {

	public HidePaintButton() {
		this(320, 428, 12);
	}

	public HidePaintButton(int x, int y, int height) {
		this(false, "Hide", x, y, 30, height);
	}

	private HidePaintButton(boolean can_hide, String t, int x, int y,
			int width, int height) {
		super(t, x, y, width, height);
	}

	@Override
	protected void onClick(Point p) {
		super.getHandler().setCollapseAllElements(!super.getHandler().allElementsCollapsed());
		update(super.getHandler().allElementsCollapsed() ? "Show" : "Hide");
	}

}
