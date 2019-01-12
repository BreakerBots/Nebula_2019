package frc.team5104.subsystem.drive;

import frc.team5104.subsystem.BreakerSubsystem;

public class _DriveConstants extends BreakerSubsystem.Constants {
	//Measurements
	public static final double _wheelDiameter = 6.0/12.0; 	   //(Feet) [Measure] The diameter of the wheels
	public static final double _ticksPerInch = 2080; 
	public static final double _ticksPerRevolution = _ticksPerInch * _wheelDiameter * Math.PI; // 4907.5; //(Encoder Tick) [Measure] Encoder Ticks Per Wheel Revolution
	public static final double _wheelBaseWidth = 24.25 / 12.0;	   //(Feet) [Measure] The Distance from the Left and Right Wheels
	
	//Speed Adjustments
	public static final double _rightAccountForward = 1.000; //(TalSpeed) [Measure] Multiple the right motor by (For Driving Straight)
	public static final double _rightAccountReverse = 0.9881; //(TalSpeed) [Measure] Multiple the right motor by (For Driving Straight)
	public static final double _leftAccountForward  = 0.9881; //(TalSpeed) [Measure] Multiple the left  motor by (For Driving Straight)
	public static final double _leftAccountReverse  = 1.000; //(TalSpeed) [Measure] Multiple the left  motor by (For Driving Straight)
	public static final double _minSpeedHighGear = 0.2;
	public static final double _minSpeedLowGear = 0.2;
	
	//Gyro
	public static final double _gyroAngle = 0;   //(Degrees) [Measure] Yaw Angle of Gyro (Athena is 65)
	
	//Speed Control
	public static final double _rampSeconds	= 0.0; //(Seconds) [Tune/Choose]
	//public static final int _currentLimitPeak	= 80;  //(Current) [Tune/Choose]
	//public static final int _currentLimitPeakTime	= 10;  //(Milliseconds) [Tune/Choose]
	//public static final int _currentLimitSustained = 36;  //(Current) [Tune/Choose]
	public static final int _pidId = 0;
	public static final double _pidF = 0.59;
	public static final double _pidP = 0.00;
	public static final double _pidI = 0.00;
	public static final double _pidD = 0.00;
}