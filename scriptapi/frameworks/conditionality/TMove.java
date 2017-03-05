package scripts.api.scriptapi.frameworks.conditionality;

import scripts.api.scriptapi.frameworks.conditionality.actions.TAction;
import scripts.api.scriptapi.frameworks.conditionality.conditions.TCondition;

public class TMove {

	private TCondition condition;
	private TAction action;

	public TMove(TCondition condition, TAction action) {
		this.condition = condition;
		this.action = action;
	}

}
