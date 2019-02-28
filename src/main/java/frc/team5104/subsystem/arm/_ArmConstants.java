package frc.team5104.subsystem.arm;

public class _ArmConstants {
	//General Arm Movement
	public static final double _armP = 1.0;
	public static final double _armI = 0.0;
	public static final double _armD = 0.0;
	public static final double _armTolerance = 1;
	
	//Climb Arm Movement
	public static final double _climbP = 1.0;
	public static final double _climbI = 0.0;
	public static final double _climbD = 0.0;
	public static final double _climbTolerance = 1;
	
	//Intake Hold Arm Movement
	public static final double _intakeHoldDownP = 0.1;
	public static final double _intakeHoldUpP = 0.3;
	
	//Encoder
	public static final double _ticksPerRevolution = 4096;
	public static final double _fullyUpDegrees = -18;
	
	//Current Limit
	public static final int _currentLimit = 60;
	
	//Setpoints
	public static final double _upPos = _fullyUpDegrees;
	public static final double _downPos = 90;
	
	//Climb
	public static final double _stage0Target = 0;
	public static final double _stage1Target = 0;
	public static final double _stage2Target = 0;
}
