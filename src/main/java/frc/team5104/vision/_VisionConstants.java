/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

public class _VisionConstants {
	//Coordinates
	public static double _maxXOffset = 27.0; // Outer X limit
	public static double _maxYOffset = 36.5; // Outer Y limit
	public static double _minXOffset = 0.3;  // Where to cap the adjustment
	public static double _minYOffset = 0.1;  // Cap 
	public static double _xTargetCoordinate = 0; 	// Adjust to angle the robot
	public static double _yTargetCoordinate = 30;	// Adjust how close the robot gets
	
	//Movement Control
	public static double _turnP =  0.02;
	public static double _turnI =  0.00; 
	public static double _turnD =  0.05;
	public static double _forwardP = 0.50;
	public static double _forwardI = 0.00;
	public static double _forwardD = 0.00;
	public static double _maxSpeed = 0.3;
}
