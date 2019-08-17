package frc.team5104.subsystem.arm;

import frc.team5104.main._RobotConstants;
import frc.team5104.webapp.Tuner.tunerInput;

public class _ArmConstants {
	//General Arm Movement
	public static final double _upP = 0.1;
	public static final double _upTolerance = 1;
	public static final double _downP = 0.2;
	public static final double _downTolerance = 1;
	public static final double _upVLimit = 7;//6;
	public static final double _downVLimit = 9;//12;
	public static final double _calibrateSpeed = 4;//4;
	
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
	public static final double _ticksPerRevolution = 4096 * (50.0 / 34.0) * (_RobotConstants._isCompBot ? 400 : 1); // * (48.0 / 36.0);
	public static final double _fullyUpDegrees = -48;
	
	//Current Limit
	@tunerInput
	public static final int _currentLimit = 60;
	
	//Setpoints
	public static final double _upPos = _fullyUpDegrees;
	@tunerInput
	public static double _downPos = 115; //105
}
