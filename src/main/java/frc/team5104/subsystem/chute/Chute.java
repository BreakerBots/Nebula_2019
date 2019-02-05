/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.chute;

import frc.team5104.subsystem.BreakerSubsystem;

public class Chute extends BreakerSubsystem.Actions {
	public static void intake() {
		ChuteSystems.Ramp.setIntake(true);
	}
	
	public static void eject() {
		ChuteSystems.Ramp.setIntake(true);
	}
	
	public static void idle() {
		ChuteSystems.Ramp.setIntake(false);
		ChuteSystems.Ramp.up();
	}
	
	public static void rampUp() {
		ChuteSystems.Ramp.up();
	}
	
	public static void rampDown() {
		ChuteSystems.Ramp.down();
	}
}
