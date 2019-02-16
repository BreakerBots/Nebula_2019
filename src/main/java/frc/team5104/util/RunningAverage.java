package frc.team5104.util;
/**
 * <h1>Boolean Change Listener</h1>
 * Send a boolean every tick to this class, and if that boolean changes (true => false, false => true) it will return true, else false
 * Similar to a button pressed event in controller.java
 */
public class RunningAverage {
	private double[] values;
	public RunningAverage(int n, double init) {
		values = new double[n];
		for(int i = 0; i < n; i++) 
			values[i] = init;
	}
	public RunningAverage(int n, int init) {
		values = new double[n];
		for(int i = 0; i < n; i++) 
			values[i] = init;
	}
	public RunningAverage(int n, boolean init) {
		values = new double[n];
		for(int i = 0; i < n; i++) 
			values[i] = init ? 1 : 0;
	}
	
	//Update
	public void update(double n) {
		for(int i = 0; i < values.length - 1; i++) 
			values[i] = values[i + 1];
		values[values.length - 1] = n;
	}
	public void update(int n) {
		for(int i = 0; i < values.length - 1; i++) 
			values[i] = values[i + 1];
		values[values.length - 1] = n;
	}
	public void update(boolean b) {
		for(int i = 0; i < values.length - 1; i++) 
			values[i] = values[i + 1];
		values[values.length - 1] = b ? 1 : 0;
	}
	
	//Getters
	public double getDoubleAvg() {
		double sum = 0;
		for(int i = 0; i < values.length; i++) 
			sum += values[i];
		return sum / values.length;
	}
	
	public int getIntAvg() {
		double sum = 0;
		for(int i = 0; i < values.length; i++) 
			sum += values[i];
		return (int)(sum) / values.length;
	}
	public boolean getBooleanAvg() {
		double sum = 0;
		for(int i = 0; i < values.length; i++) 
			sum += values[i];
		return (int)(sum) / values.length == 1 ? true : false;
	}
}
