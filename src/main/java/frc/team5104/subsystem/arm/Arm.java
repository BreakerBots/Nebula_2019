/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.arm;

import frc.team5104.control._Controls;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.arm.ArmManager.ArmState;
import frc.team5104.subsystem.climber.Climber;
import frc.team5104.subsystem.climber.ClimberManager.ClimberStage;
import frc.team5104.util.console;

public class Arm extends BreakerSubsystem.Actions {
	public static void idle() {
		if(ArmManager.currentState != ArmState.calibrating)
			ArmManager.currentState = ArmState.idle;
	}
	
	public static void intake() {
		if (ArmManager.currentState != ArmState.calibrating)
			ArmManager.currentState = ArmState.intakeDown;
	}
	
	public static void zero() {
		ArmSystems.Encoder.zero();
	}

	public static boolean isManual() {
		if (_Controls.Cargo._manualArm)
			return true;
		else
			return Climber.isClimbing() && Climber.getStage() != ClimberStage.initial;
	}
}
