package Grupo45.Projeto;

import java.util.ArrayList;



public class Rule {
    private String ruleName;
    private ArrayList<Condition> conditions;
    private ArrayList<String> operator;

    public Rule(String ruleName, ArrayList<Condition> conditions,  ArrayList<String> operator) {
        this.ruleName=ruleName;
        this.conditions=conditions;
        this.operator=operator;

    }
    
    public Rule(String rule) {
    	//is_Long_Method LOC_method.>.10 OR CYCLO_method.>.5
        String[] details = rule.split(": :");

        ruleName=details[0];
        conditions = new ArrayList<>();
        operator = new ArrayList<>();
        for(int i=1;i!=details.length;i++) {
        	if ( i % 2 != 0 ) {
        		Condition cond = new Condition(details[i]);
        		conditions.add(cond);
        	}else {
        		operator.add(details[i]);
        	}
        	
        }

    }

    public boolean ruleEvaluate(ArrayList<Integer> value) {
        boolean output=true;
        if(conditions.size()!=value.size() && value.size()!=operator.size()+1) {
            throw new IllegalArgumentException("Rule needs more values");
        }
        for(int i=0; i!=conditions.size();i++) {
            if(i==0) {
                output =conditions.get(i).evaluate(value.get(i));
            }else {
                switch(operator.get(i-1)) {
                case("AND"):
                    output= output && conditions.get(i).evaluate(value.get(i));
                case("OR"):
                    output= output || conditions.get(i).evaluate(value.get(i));

                }
            }
        }
            return output;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String toString() {
        String out=ruleName;
        int x=0;
        for(int i=0;i!=conditions.size();i++) {
            if(i>=1) {
            	out= out + ": :" + operator.get(x);
                x++;
            }
            out= out + ": :" + conditions.get(i).toString();
           
        }
       
        return out;
    }
    


    
    public int getNumberOfConditions() {
        return conditions.size();
    }
    public Condition getCondition(int i) {
        return conditions.get(i);
    }
    public boolean isClassRule() {
    	boolean out = false;
    	
    	 for(int i=0;i!=conditions.size();i++) {
    		 if(conditions.get(i).getMetric().split("_")[conditions.get(i).getMetric().split("_").length-1].equals("class")) out=true;
    	 }
    	 return out;
    }
    public String getOperator(int i) {
    	return operator.get(i);
    }

}