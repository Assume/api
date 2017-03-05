package scripts.api.scriptapi.frameworks.conditionality.factory;

import scripts.api.scriptapi.frameworks.conditionality.TMove;
import scripts.api.scriptapi.frameworks.conditionality.actions.TAction;
import scripts.api.scriptapi.frameworks.conditionality.conditions.TCondition;
import scripts.api.scriptapi.frameworks.conditionality.conditions.defaults.TAlwaysTrueCondition;

public class TMoveFactory {

	private TAction action;

	private TCondition condition;

	public TMoveFactory() {
		this.action = null;
		this.condition = null;
	}

	public void setAction(TAction action) {
		this.action = action;
	}

	public void setCondition(TCondition condition) {
		this.condition = condition;
	}

	public TMove make() {
		if (action == null)
			return null;
		if (condition == null)
			return new TMove(new TAlwaysTrueCondition(), action);
		return new TMove(condition, action);
	}

}