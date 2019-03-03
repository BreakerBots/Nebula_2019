package frc.team5104.subsystem.hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.webapp.Tuner.tunerInput;

public class _HatchConstants {
	//Dad
	public static DoubleSolenoid.Value _trapIn = DoubleSolenoid.Value.kReverse;
	
	//Lazy Boy
	public static DoubleSolenoid.Value _lazyBoyUp = DoubleSolenoid.Value.kForward;
	
	//Ejector
	public static DoubleSolenoid.Value _ejectorOut = DoubleSolenoid.Value.kForward;
	
	//Modes
	@tunerInput
	public static int _intakeModeLength = 1000;
	@tunerInput
	public static int _ejectModeLength = 1000;
}
