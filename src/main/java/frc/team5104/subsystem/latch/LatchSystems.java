/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.latch;

import frc.team5104.subsystem.BreakerSubsystem;

public class LatchSystems extends BreakerSubsystem.Systems {
	static class Lazyboy {
		static void down() {
			
		}
		static void up() {
			
		}
		
		static boolean isUp() {
			return true;
		}
		static boolean isDown() { return !isUp(); }
	}
	
	static class Dad {
		static void open() {
			
		}
		static void close() {
			
		}
		
		static boolean isOpen() {
			return true;
		}
		static boolean isClosed() { return !isOpen(); }
	}
}