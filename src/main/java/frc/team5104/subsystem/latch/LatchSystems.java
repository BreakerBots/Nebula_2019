/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.latch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.chute._ChuteConstants;

public class LatchSystems extends BreakerSubsystem.Systems {
	//Devices
	static DoubleSolenoid lean = Devices.Hatch.lean;
	static DoubleSolenoid latch = Devices.Hatch.latch;
	
	static class Lazyboy {
		static void down() {
			lean.set(_LatchConstants._leanDown);
		}
		static void up() {
			lean.set(_LatchConstants._leanUp);
		}
		
		static boolean isUp() {
			return lean.get() == _LatchConstants._leanUp;
		}
		static boolean isDown() { return !isUp(); }
	}
	
	static class Dad {
		static void open() {
			latch.set(_LatchConstants._latchOpen);
		}
		static void close() {
			latch.set(_LatchConstants._latchClose);
		}
		
		static boolean isOpen() {
			return latch.get() == _LatchConstants._latchOpen;
		}
		static boolean isClosed() { return !isOpen(); }
	}
}