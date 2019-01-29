/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.drive;

import frc.team5104.subsystem.BreakerSubsystem;

public class _DriveConstants extends BreakerSubsystem.Constants {
	//Measurements
	public static final double _wheelDiameter = 6.0/12.0; 	   //(Feet) [Measure] The diameter of the wheels
	public static final double _ticksPerRevolution = 4096.0 * 3.0 * (54.0/30.0); //(Encoder Tick) [Measure] Encoder Ticks Per Wheel Revolution
	public static final double _wheelBaseWidth = 24.25 / 12.0;	   //(Feet) [Measure] The Distance from the Left and Right Wheels
	
	//Speed Adjustments
	public static final double _rightAccountForward = 1.000; //(TalSpeed) [Measure] Multiple the right motor by (For Driving Straight)
	public static final double _rightAccountReverse = 1.000; //(TalSpeed) [Measure] Multiple the right motor by (For Driving Straight)
	public static final double _leftAccountForward  = 1.000; //(TalSpeed) [Measure] Multiple the left  motor by (For Driving Straight)
	public static final double _leftAccountReverse  = 1.000; //(TalSpeed) [Measure] Multiple the left  motor by (For Driving Straight)
	public static final double _minSpeedHighGearForward = 0.061;
	public static final double _minSpeedHighGearTurn = 0.22;
	public static final double _minSpeedLowGearForward = 0.065;
	public static final double _minSpeedLowGearTurn = 0.225;
	
	//Gyro
	public static final double _gyroAngle = 0;   //(Degrees) [Measure] Yaw Angle of Gyro (Athena is 65)
	
	//Speed Control
	public static final double _rampSeconds	= 0.0; //(Seconds) [Tune/Choose]
	//public static final int _currentLimitPeak	= 80;  //(Current) [Tune/Choose]
	//public static final int _currentLimitPeakTime	= 10;  //(Milliseconds) [Tune/Choose]
	//public static final int _currentLimitSustained = 36;  //(Current) [Tune/Choose]
	public static final int _pidId = 0;
	public static final double _pidF = 0.042227359;
	public static final double _pidP = 10;//0.285;
	public static final double _pidI = 0;
	public static final double _pidD = 0;//12.0;
}
