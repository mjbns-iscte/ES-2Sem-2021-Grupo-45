package Grupo45.Projeto;

public class Condition {

	/**
     * Represents a metric to be used in the condition
     */
    private String metric;
    /**
     * Represents a signal to be used in the condition
     */
    private String signal;
    /**
     * Represents a values to be used and compared in the condition
     */
    private int n;

    /**
     * This constructor creates a condition when given a metric, a signal and an int value
     * @param metric is the metric to be used in the condition
     * @param signal is the signal to be used in the condition
     * @param n is the int value to be used in the condition
     */
    public Condition(String metric,String signal,int n) {
        this.metric=metric;
        this.n=n;
        this.signal=signal;

    }
    /**
     * This constructor creates a condition, when given a String in a specific format that uses ':' to separate the information. Example: Metric:Signal:Value
     * @param s is the String to be used to create the condition
     */
    public Condition(String s) {
        String[] details = s.split(":");

        this.metric=details[0];
        this.n=Integer.parseInt(details[2]);
        this.signal=details[1];
        
    } 
    /**
     * Given an int value, this method checks if the value is within the condition parameters
     * @param x is the value to be tested in the condition
     * @return returns a boolean that represents if the given value is in the condition (true) or not (false)
     */
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

    /**
     *Returns a String representation of the class
     */
    public String toString() {
        return metric + ":" + signal + ":" + n;
    }
    
    /**
     * 
     * @return returns the metric of the condition
     */
    public String getMetric() {
        return metric;
    }
    /**
     * @return returns the logical signal of the condition
     */
    public String getSignal() {
        return signal;
    }
    /**
     * @return returns the limit value of the condition as a String
     */
    public String getLimit() {
        return n + "";
    }
}