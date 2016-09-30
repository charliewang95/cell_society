package layout.rule;

public class Parameter {
	
	private double myValue;
	private double myMin;
	private double myMax;
	private String myMessage;
	
	public Parameter(double initial, String message, double min, double max){
		myValue = initial;
		myMessage = message;
		myMin = min;
		myMax = max;
	}
	
	public double getValue(){
		return myValue;
	}
	
	public void setValue(double newValue){
		myValue = newValue;
	}
	
	public double getMax(){
		return myMax;
	}
	
	public double getMin(){
		return myMin;
	}
	
	public String getMessage(){
		return myMessage;
	}

}
