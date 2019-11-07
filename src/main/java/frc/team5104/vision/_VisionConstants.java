/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.webapp.Tuner.tunerInput;

public class _VisionConstants {
	//Coordinates
	public static double _targetStandardY = -8.5;
	//public static double _targetRocketY = 6.15;
	public static double _targetX = 12;//9.6;
	public static double _toleranceX = 0.3;
	public static double _toleranceY = 0.5;
	
	//Movement Control
	@tunerInput
	public static double _turnP =  .5;
	@tunerInput
	public static double _turnD =  4.0;
	@tunerInput
	public static double _leftOffset = 3.0;
	@tunerInput
	public static double _rightOffset = 3.7;
	@tunerInput
	public static double _forwardP = 2.5;
	@tunerInput
	public static double _forwardF = -2;
}
