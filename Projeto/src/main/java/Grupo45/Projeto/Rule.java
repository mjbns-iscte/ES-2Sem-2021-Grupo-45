package Grupo45.Projeto;

import java.util.ArrayList;

/**
 * This class represents a Rule that can be applied to an Excel file with metric values to check if it is a Code Smell
* @author G45
* @author Andre Amado, Guilherme Henriques, João Guerra, Miguel Nunes, Francisco Mendes, Tiago Geraldo
* @version 1.0
*/
public class Rule {
	/**
	 * The name of the rule
	 */
	private String ruleName;
	/**
	 * This array saves the conditions of the rule
	 */
	private ArrayList<Condition> conditions;
	/**
	 * This array saves the logical operators of the rule
	 */
	private ArrayList<String> operator;

	/**
	 * This constructor creates a Rule if given a name, conditions, and operators
	 * @param ruleName is the name of the rule
	 * @param conditions is an array with all the conditions to be used by the rule
	 * @param operator is an array with all the operators to be used by the rule
	 */
	public Rule(String ruleName, ArrayList<Condition> conditions, ArrayList<String> operator) {
		this.ruleName = ruleName;
		this.conditions = conditions;
		this.operator = operator;
	}

	/**
	 * 	 This constructor creates a Rule if given a specific String with name, conditions, and operators values separated by ': :'

	 * @param rule is the String to be used by the constructor in the format: RuleName: :ConditionName:Signal:Value: :Operator: :ConditionName...
	 */
	public Rule(String rule) {
		String[] details = rule.split(": :");

		ruleName = details[0];
		conditions = new ArrayList<>();
		operator = new ArrayList<>();
		for (int i = 1; i != details.length; i++) {
			if (i % 2 != 0) {
				Condition cond = new Condition(details[i]);
				conditions.add(cond);
			} else {
				operator.add(details[i]);
			}
		}
	}

	/**
	 * Given an array of Integer values, checks if the values are true for the Rule's Conditions. The first Integer of the array will be tested
	 * for the first condition, the second Integer of the array will be tested for the second condition and so on
	 * @param value is the array of Integer values to be used to evaluate if the conditions are true or false
	 * @return returns a boolean representing if the values given are within the Rule Conditions
	 */
	public boolean ruleEvaluate(ArrayList<Integer> value) {
		boolean output = true;
		if (conditions.size() != value.size() && value.size() != operator.size() + 1) {
			throw new IllegalArgumentException("Rule needs more values");
		}
		for (int i = 0; i != conditions.size(); i++) {
			if (i == 0) {
				output = conditions.get(i).evaluate(value.get(i));
			} else {
				switch (operator.get(i - 1)) {
				case ("AND"):
					output = output && conditions.get(i).evaluate(value.get(i));
				case ("OR"):
					output = output || conditions.get(i).evaluate(value.get(i));
				}
			}
		}
		return output;
	}

	public String getRuleName() {
		return ruleName;
	}

	/**
	 * Returns a String representing a Rule
	 */
	public String toString() {
		String out = ruleName;
		int x = 0;
		for (int i = 0; i != conditions.size(); i++) {
			if (i >= 1) {
				out = out + ": :" + operator.get(x);
				x++;
			}
			out = out + ": :" + conditions.get(i).toString();
		}
		return out;
	}

	/**
	 * 
	 * @return returns the number of Conditions of a Rule
	 */
	public int getNumberOfConditions() {
		return conditions.size();
	}

	/**
	 * Given an int value, returns the Condition in that position of the array
	 * @param i is the position of the Conditions array to get
	 * @return returns a Condition
	 */
	public Condition getCondition(int i) {
		return conditions.get(i);
	}

	/**
	 * @return returns a boolean that represents if a Rule has class conditions(true) or method conditions
	 */
	public boolean isClassRule() {
		boolean out = false;

		for (int i = 0; i != conditions.size(); i++) {
			if (conditions.get(i).getMetric().split("_")[conditions.get(i).getMetric().split("_").length - 1]
					.equals("class"))
				out = true;
		}
		return out;
	}

	/**
	 * Given an int value, returns the operator in that position
	 * @param i is the of the array position to obtain
	 * @return returns an operator
	 */
	public String getOperator(int i) {
		return operator.get(i);
	}

}