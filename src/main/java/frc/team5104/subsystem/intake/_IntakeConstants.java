package frc.team5104.subsystem.intake;

public class _IntakeConstants {
	//PID
	public static final double _kP = 1.0;
	public static final double _kI = 0.0;
	public static final double _kD = 0.0;
	public static final double _tolerance = 1;
	
	//Encoder
	public static final double _ticksPerRevolution = 4096 * 1;
	public static final double _fullyUpDegrees = -18;
	
	//Setpoints
	public static final double _upPos = _fullyUpDegrees;
	public static final double _downPos = 90;
}
