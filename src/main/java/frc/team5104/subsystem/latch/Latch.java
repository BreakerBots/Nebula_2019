/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.latch;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.latch.LatchManager.LatchState;

public class Latch extends BreakerSubsystem.Actions {
	public static void intake() {
		LatchManager.intakeStartTime = System.currentTimeMillis();
		LatchManager.currentState = LatchState.intake;
	}
	
	public static void eject() {
		LatchManager.ejectStartTime = System.currentTimeMillis();
		LatchManager.currentState = LatchState.eject;
	}
	
	public static boolean hasHatch() {
		return LatchManager.currentState == LatchState.hold;
	}
}
