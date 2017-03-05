package scripts.api.scriptapi.frameworks.conditionality.conditions;

public interface TCondition {

	default public boolean validate() {
		return true;
	};

}
