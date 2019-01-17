/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

public class _VisionConstants {
	public static double _maxXOffset = 27.0; // Outer X limit
	public static double _maxYOffset = 33.5; // Outer Y limit
	public static double _minXOffset = 0.5;  // Where to cap the adjustment
	public static double _minYOffset = 0.5;  // Cap 
	public static double _xTargetCoordinate = 0;	 // Adjust to angle the robot
	public static double _yTargetCoordinate = 13;	 // Adjust how close the robot gets
	public static double _px = 1;			 // Proprtional for x, tune as needed for good results
	public static double _py = 1;			 // Proportional for y, tune as needed for good results
	public static double _maxSpeed = 0.35;	 // Default speed, tune as needed
	
	// Pipeline indices
	public static int _line = 0;
	public static int _target = 1;
}
