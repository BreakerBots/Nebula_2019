package frc.team5104.subsystem.arm;

import frc.team5104.webapp.Tuner.tunerInput;

public class _ArmConstants {
	//General Arm Movement
	public static final double _armP = 0.3;
	public static final double _armI = 0.0;
	public static final double _armD = 0.0;
	public static final double _armTolerance = 1;
	public static final double _upVLimit = 4;
	public static final double _downVLimit = 12;
	
	//Climb Arm Movement
	public static final double _climbP = 1.0;
	public static final double _climbI = 0.0;
	public static final double _climbD = 0.0;
	public static final double _climbTolerance = 1;
	
	//Intake Hold Arm Movement
	public static double _intakeHoldDownP = 0.1;
	public static double _intakeHoldUpP = 0.3;
	
	//Encoder
	public static final double _ticksPerRevolution = 4096 * (50.0 / 34.0); // * (48.0 / 36.0);
	public static final double _fullyUpDegrees = -48;
	
	//Current Limit
	public static final int _currentLimit = 60;
	
	//Setpoints
	public static final double _upPos = _fullyUpDegrees;
	@tunerInput
	public static double _downPos = 105;
	
	//Climb
	public static final double _stage0Target = 0;
	public static final double _stage1Target = 0;
	public static final double _stage2Target = 0;
}
