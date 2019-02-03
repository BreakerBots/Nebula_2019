/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.chute;

import frc.team5104.subsystem.BreakerSubsystem;

public class ChuteSystems extends BreakerSubsystem.Systems {
	static class Ramp {
		static void up() {
			
		}
		static void down() {
			
		}
		
		static boolean isUp() {
			return true;
		}
		static boolean isDown() { return !isUp(); }
	}
	
	public static class BeamBreak {
		public static boolean isHit() {
			return true;
		}
		public static boolean isNotHit() { return !isHit(); }
	}
}