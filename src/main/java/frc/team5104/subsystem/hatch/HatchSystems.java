/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.hatch;

import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.PneumaticFactory;

class HatchSystems extends BreakerSubsystem.Systems {
	//Lazyboy
	public static class Lazyboy {
		static void back() {
			Devices.Hatch.lazyBoy.set(PneumaticFactory.getOppositeValue(_HatchConstants._lazyBoyUp));
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
		public static void in() {
			Devices.Hatch.trap.set(_HatchConstants._trapIn);
		}
		public static void out() {
			Devices.Hatch.trap.set(PneumaticFactory.getOppositeValue(_HatchConstants._trapIn));
		}
		
		static boolean isOpen() {
			return Devices.Hatch.trap.get() == _HatchConstants._trapIn;
		}
		static boolean isClosed() { return !isOpen(); }
	}
	
	//Ejector (yeeter)
	public static class Ejector {
		public static void eject() {
			Devices.Hatch.ejector.set(_HatchConstants._ejectorOut);
		}
		public static void retract() {
			Devices.Hatch.ejector.set(PneumaticFactory.getOppositeValue(_HatchConstants._ejectorOut));
		}
		
		static boolean isOut() {
			return Devices.Hatch.ejector.get() == _HatchConstants._ejectorOut;
		}
		static boolean isIn() { return !isOut(); }
	}
}