package Grupo45.Projeto;

public class Condition {

    private String metric;
    private String signal;
    private int n;

    public Condition(String metric,String signal,int n) {
        this.metric=metric;
        this.n=n;
        this.signal=signal;

    }
    public Condition(String s) {
        String[] details = s.split(":");

        this.metric=details[0];
        this.n=Integer.parseInt(details[2]);
        this.signal=details[1];
        
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
        case("<="):
            output = x<=n;
            break;
        case(">="):
            output = x>=n;
            break;
        case("="):
            output = x==n;
            break;
        }
        return output;
    }

    public String toString() {
        return metric + ":" + signal + ":" + n;
    }
    
    public String getMetric() {
        return metric;
    }
    public String getSignal() {
        return signal;
    }
    public String getLimit() {
        return n + "";
    }

}