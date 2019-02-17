/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

public class HatchSystems extends BreakerSubsystem.Systems {
	//Lazyboy
	public static class Lazyboy {
		static void back() {
			Devices.Hatch.lazyBoy.set(_HatchConstants._lazyBoyUp == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
		}
		static void up() {
			Devices.Hatch.lazyBoy.set(_HatchConstants._lazyBoyUp);
		}
		
		static boolean isUp() {
			return Devices.Hatch.lazyBoy.get() == _HatchConstants._lazyBoyUp;
		}
		static boolean isBack() { return !isUp(); }
	}
	
	//Rose
	public static class Trap {
		public static void open() {
			Devices.Hatch.trap.set(_HatchConstants._trapOpen);
		}
		public static void close() {
			Devices.Hatch.trap.set(_HatchConstants._trapOpen == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
		}
		
		static boolean isOpen() {
			return Devices.Hatch.trap.get() == _HatchConstants._trapOpen;
		}
		static boolean isClosed() { return !isOpen(); }
	}
	
	//Ejector (yeeter)
	public static class Ejector {
		public static void yeet() {
			
		}
		
		public static void pullOut() {
			
		}
		
		static boolean isOut() {
			return false;
		}
	}
}