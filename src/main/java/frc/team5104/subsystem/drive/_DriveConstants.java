/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.drive;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.webapp.Tuner.tunerInput;

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
	public static final double _minSpeedHighGearForward = 0.08;
	public static final double _minSpeedHighGearTurn = 0.20;
	public static final double _minSpeedLowGearForward = 0.04;
	public static final double _minSpeedLowGearTurn = 0.10;
	
	//Current Limiting
	public static final int _currentLimit = 60; //(Amps) [Measure (<250, or ~240/#ofmotorsinurdrivetrain)] The current limit of the drive motors
	
	//Speed Control (Regular)
	public static double _kP = 0.285;
	public static double _kI = 0;
	public static double _kD = 12.0;
	
	//Speed Control (Feed Forward)
	public static final double _kV = 0.0608;//0.570275;
	public static final double _kA = 0;//0.257666;
	public static final double _kS = 1.4171;
	//r-squared = 0.9439
}
