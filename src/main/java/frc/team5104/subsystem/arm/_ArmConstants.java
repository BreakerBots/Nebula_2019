package frc.team5104.subsystem.arm;

import frc.team5104.webapp.Tuner.tunerInput;

public class _ArmConstants {
	//General Arm Movement
	public static final double _armP = 0.3;
	public static final double _armI = 0.0;
	public static final double _armD = 0.0;
	public static final double _armTolerance = 1;
	public static final double _upVLimit = 6;
	public static final double _downVLimit = 12;
	
	//Climb Arm Movement
	public static final double _climbInitP = 0.16;
	public static final double _climbInitI = 0.001;
	public static final double _climbInitTolerance = 1;
	public static final double _climbInitMax = 8;
	public static final double _climbInitDegrees = 70;
	
	//Intake Hold Arm Movement
	public static double _intakeHoldDownP = 0.1;
	public static double _intakeHoldUpP = 0.3;
	
	//Encoder
	public static final double _ticksPerRevolution = 4096 * (50.0 / 34.0); // * (48.0 / 36.0);
	public static final double _fullyUpDegrees = -48;
	
	//Current Limit
	@tunerInput
	public static final int _currentLimit = 60;
	
	//Setpoints
	public static final double _upPos = _fullyUpDegrees;
//	@tunerInput
	public static double _downPos = 110;
}
