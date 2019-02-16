/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.chute;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.Buffer;

public class Chute extends BreakerSubsystem.Actions {
	public static Buffer BeamAverage;

	public static void trapdoorUp() {
		ChuteSystems.Trapdoor.up();
	}
	
	public static void trapdoorDown() {
		ChuteSystems.Trapdoor.down();
	}
	
	public static boolean isUp() {
		return ChuteSystems.Trapdoor.isUp();
	}
	
	public static boolean isDown() {
		return ChuteSystems.Trapdoor.isDown();
	}
}
