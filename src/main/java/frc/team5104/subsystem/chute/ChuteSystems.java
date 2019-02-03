/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.chute;

import frc.team5104.subsystem.BreakerSubsystem;

class ChuteSystems extends BreakerSubsystem.Systems {
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
	
	static class Belt {
		static void intake() {
			
		}
		
		static void eject() {
			
		}
		
		static void idle() {
			
		}
	}
	
	static class BeamBreak {
		static boolean isHit() {
			return true;
		}
		
		static boolean isNotHit() { return !isHit(); }
	}
}