package frc.team5104.subsystem.hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main._RobotConstants;
import frc.team5104.webapp.Tuner.tunerInput;

public class _HatchConstants {
	public static int _ejectorDelay = 100;

	//Dad
	public static DoubleSolenoid.Value _flapsIn = _RobotConstants._isCompBot ? DoubleSolenoid.Value.kForward 
			: DoubleSolenoid.Value.kReverse;
	
	//Lazy Boy
	public static DoubleSolenoid.Value _lazyBoyUp = DoubleSolenoid.Value.kForward;
	
	//Ejector
	public static DoubleSolenoid.Value _ejectorOut = // _RobotConstants._isCompBot ? DoubleSolenoid.Value.kReverse 
		 DoubleSolenoid.Value.kForward;
	
	//Modes
	@tunerInput
	public static int _intakeModeLength = 700;
	@tunerInput
	public static int _intakeModeFastLength = 300;
	@tunerInput
	public static int _ejectModeLength = _ejectorDelay + 200;
}
