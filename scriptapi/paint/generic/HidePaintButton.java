package scripts.api.scriptapi.paint.generic;

import scripts.api.scriptapi.paint.ButtonDisplay;
import scripts.api.scriptapi.paint.Paintable;

public class HidePaintButton extends ButtonDisplay {

	public HidePaintButton(int x, int y, int height) {
		this(false, "Hide", x, y, 28, height);
	}

	private HidePaintButton(boolean can_hide, String t, int x, int y,
			int width, int height) {
		super(t, x, y, width, height);
	}

	@Override
	protected void onClick() {
		Paintable.setHideAllElements(!Paintable.elementsAreHidden());
		update(Paintable.elementsAreHidden() ? "Show" : "Hide");
	}

}
