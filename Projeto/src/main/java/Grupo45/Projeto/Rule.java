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

    public boolean ruleEvaluate(ArrayList<Integer> value) {
        boolean output=true;
        if(conditions.size()!=value.size() && value.size()!=operator.size()+1) {
            throw new IllegalArgumentException("Esta regra necessita de mais valores");
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
                out.concat(operator.get(x));
                x++;
            }
            out.concat(" " + conditions.get(i).toString());
        }
        System.out.println("Rules: "+out);
        return out;
    }

    public int getNumberOfConditions() {
        return conditions.size();
    }
    public Condition getCondition(int i) {
        return conditions.get(i);
    }

}