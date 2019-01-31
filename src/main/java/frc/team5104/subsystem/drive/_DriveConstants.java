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
	
	//Current Limiting
	public static final int _currentLimit = 60; //(Amps) [Measure (<250, or ~240/#ofmotorsinurdrivetrain)] The current limit of the drive motors
	
	//Speed Control (Regular)
	public static final double _kP = 0.285;
	public static final double _kI = 0;
	public static final double _kD = 12.0;
	
	//Speed Control (Feed Forward)
	public static final double _kF = 0;
	public static final double _kC = 0;
	public static final double _kV = 0;
	public static final double _kA = 0;
}
