package scripts.api.scriptapi.generic;

import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;

public class Mouse07 {

	/**
	 * Fixes the mouse if there is something selected.
	 * <p>
	 * 
	 * @return True if it was fixed or had nothing selected in the first place,
	 *         false otherwise.
	 */
	public static final boolean fixSelected() {
		if (Game.isUptext("->")) {
			if (!ChooseOption.isOpen()) {
				Mouse.click(3);
			}
			if (Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					return ChooseOption.isOpen();
				}
			}, 250)) {
				return ChooseOption.select("Cancel");
			}
		}
		return true;
	}

}
