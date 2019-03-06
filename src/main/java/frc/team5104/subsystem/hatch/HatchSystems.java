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
	
	//Flaps
	public static class Flaps {
		public static void in() {
			Devices.Hatch.flaps.set(_HatchConstants._flapsIn);
		}
		public static void out() {
			Devices.Hatch.flaps.set(PneumaticFactory.getOppositeValue(_HatchConstants._flapsIn));
		}
		
		static boolean isOpen() {
			return Devices.Hatch.flaps.get() == _HatchConstants._flapsIn;
		}
		static boolean isClosed() { return !isOpen(); }
	}
	
	//Ejector (yeeter)
	public static class Ejector {
		public static void yeet() {
			Devices.Hatch.ejector.set(_HatchConstants._ejectorOut);
		}
		public static void pullOut() {
			Devices.Hatch.ejector.set(PneumaticFactory.getOppositeValue(_HatchConstants._ejectorOut));
		}
		
		static boolean isOut() {
			return Devices.Hatch.ejector.get() == _HatchConstants._ejectorOut;
		}
		static boolean isIn() { return !isOut(); }
	}
}