/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.latch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

public class LatchSystems extends BreakerSubsystem.Systems {
	public static class Lazyboy {
		static void back() {
			Devices.Hatch.lazyBoy.set(_LatchConstants._lazyBoyUp == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
		}
		static void up() {
			Devices.Hatch.lazyBoy.set(_LatchConstants._lazyBoyUp);
		}
		
		static boolean isUp() {
			return Devices.Hatch.lazyBoy.get() == _LatchConstants._lazyBoyUp;
		}
		static boolean isBack() { return !isUp(); }
	}
	
	public static class Dad {
		public static void open() {
			Devices.Hatch.dad.set(_LatchConstants._dadOpen);
		}
		public static void close() {
			Devices.Hatch.dad.set(_LatchConstants._dadOpen == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
		}
		
		static boolean isOpen() {
			return Devices.Hatch.dad.get() == _LatchConstants._dadOpen;
		}
		static boolean isClosed() { return !isOpen(); }
	}
}