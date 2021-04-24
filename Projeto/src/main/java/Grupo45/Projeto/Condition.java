package Grupo45.Projeto;

public class Condition {

    private String metric;

    String signal;
    private int n;

    public Condition(String metric,String signal,int n) {
        this.metric=metric;
        this.n=n;
        this.signal=signal;

    }
    public boolean evaluate(int x) {
        boolean output=false;

        switch(signal) {
        case("<"):
            output = x<n;
            break;
        case(">"):
            output = x>n;
            break;
        }
        return output;
    }

    public String toString() {
        return metric+signal+n;
    }

}