package frc.team5104.subsystem.climber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main._RobotConstants;
import frc.team5104.webapp.Tuner.tunerInput;

public class _ClimberConstants {
	public static final DoubleSolenoid.Value _stage1In = _RobotConstants._isCompBot ? 
			DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse;
	public static final DoubleSolenoid.Value _stage2In = _RobotConstants._isCompBot ? 
			DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse;
	
	@tunerInput
	public static double _forwardWheelSpeed = 0.3; //percent
	@tunerInput
	public static long lift1Length = 2000; //ms
	@tunerInput
	public static long lift2Length = 2000; //ms
}
