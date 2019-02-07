/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.chute;

import frc.team5104.subsystem.BreakerSubsystem;

public class Chute extends BreakerSubsystem.Actions {
	public static void trapdoorUp() {
		ChuteSystems.Trapdoor.up();
	}
	
	public static void trapdoorDown() {
		ChuteSystems.Trapdoor.down();
	}
}
