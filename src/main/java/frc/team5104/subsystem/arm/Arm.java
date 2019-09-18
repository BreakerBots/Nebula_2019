/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.arm;

import frc.team5104.control._Controls;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.arm.ArmManager.ArmState;
import frc.team5104.subsystem.climber.Climber;
import frc.team5104.subsystem.climber.ClimberManager.ClimberStage;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

public class Arm extends BreakerSubsystem.Actions {
	public static void idle() {
		if(ArmManager.currentState != ArmState.calibrating)
			ArmManager.currentState = ArmState.idle;
	}
	
	public static void intake() {
		if (ArmManager.currentState != ArmState.calibrating && !isManual())
			ArmManager.currentState = ArmState.intakeDown;
	}
	
	public static void zero() {
		ArmSystems.Encoder.zero();
	}

	public static boolean isManual() {
		if (_Controls.Cargo._manualArm)
			return true; 
		else if (ArmSystems.Encoder.disconnected()) {
			console.error(c.CARGO, "Encoder Disconnected!");
			return true;
		}
		else
			return Climber.isClimbing() && Climber.getStage() != ClimberStage.initial;
	}
	
	public static boolean isIntaking() {
		return ArmManager.currentState == ArmState.intakeDown || ArmManager.currentState == ArmState.intakeHold;
	}
	
	public static void changeDownPosition(double change) {
		_ArmConstants._downPos = BreakerMath.clamp(_ArmConstants._downPos + change, 90, 125);
		intake();
	}
}
