/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.intake;

import frc.team5104.subsystem.BreakerSubsystem;

class IntakeSystems extends BreakerSubsystem.Systems {
	static class Arm {
		static void up() {
			
		}
		
		static void down() {
			
		}
	}
	
	static class Encoder {
		static int getPosition() {
			return 0;
		}
		
		static void zero() {
			
		}
	}
	
	static class LimitSwitch {
		static boolean isHit() {
			return true;
		}
		
		static boolean isNotHit() { return !isHit(); }
	}
}