/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.intake;

import frc.team5104.subsystem.BreakerSubsystem;

public class Intake extends BreakerSubsystem.Actions {
	public static void intake() {
		IntakeSystems.Arm.down();
	}
	
	public static void idle() {
		IntakeSystems.Arm.up();
	}
}
