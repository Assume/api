package scripts.api.scriptapi.paint.generic;

import scripts.api.scriptapi.paint.ButtonDisplay;
import scripts.api.scriptapi.paint.Paintable;

public class OpenButton extends ButtonDisplay {

	private Paintable<?> paintable;

	public OpenButton(int x, int y, Paintable<?> paintable) {
		this(false, "Open", x, y, 35, 12, paintable);
	}

	private OpenButton(boolean can_hide, String t, int x, int y, int width,
			int height, Paintable<?> paintable) {
		super(t, x, y, width, height);
		this.paintable = paintable;
		this.setOpen(false);
	}

	@Override
	protected void onClick() {
		paintable.setOpen(true);
		this.setOpen(false);
	}

}
