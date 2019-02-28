/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.arm;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.arm.ArmManager.ArmState;

public class Arm extends BreakerSubsystem.Actions {
	public static void idle() {
		ArmManager.currentState = ArmState.idle;
	}
	
	public static void intake() {
		ArmManager.currentState = ArmState.intakeDown;
	}
	
	public static void zero() {
		ArmSystems.Encoder.zero();
	}
}
