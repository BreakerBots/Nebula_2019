package frc.team5104.subsystem.hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class _HatchConstants {
	//Dad
	public static DoubleSolenoid.Value _trapOpen = DoubleSolenoid.Value.kForward;
	
	//Lazy Boy
	public static DoubleSolenoid.Value _lazyBoyUp = DoubleSolenoid.Value.kForward;
	
	//Ejector
	public static DoubleSolenoid.Value _ejectorOut = DoubleSolenoid.Value.kForward;
	
	//Modes
	public static int _intakeModeLength = 1000;
	public static int _ejectModeLength = 1000;
}
