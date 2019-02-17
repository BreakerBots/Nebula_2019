/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.hatch;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.hatch.HatchManager.LatchState;

public class Hatch extends BreakerSubsystem.Actions {
	public static void intake() {
		HatchManager.currentState = LatchState.hold;
	}
	
	public static void eject() {
		HatchManager.ejectStartTime = System.currentTimeMillis();
		HatchManager.currentState = LatchState.eject;
	}
	
	public static boolean hasHatch() {
		return HatchManager.currentState == LatchState.hold;
	}
}
