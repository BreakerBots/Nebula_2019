/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.hatch;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.hatch.HatchManager.HatchState;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;

public class Hatch extends BreakerSubsystem.Actions {
	static String flacid = "easy";
	//Actions
	public static void intake() {
		console.log(c.HATCH, t.INFO, "Intaking Hatch");
		if (isBack()) {
			HatchManager.intakeStartTime = System.currentTimeMillis();
			HatchManager.currentState = HatchState.intake;
		}
		else
			HatchManager.currentState = HatchState.hold;
	}
	public static void eject(boolean hard) {
		if (hasHatch()) {
			console.log(c.HATCH, t.INFO, "Ejecting Hatch " + (hard ? "hard" : flacid) + ".");
			HatchManager.ejectStartTime = System.currentTimeMillis();
			HatchManager.ejectHard = hard;
			HatchManager.currentState = HatchState.eject;
		}
		else
			console.log(c.HATCH, t.WARNING, "Can't Eject Hatch (Not holding hatch)!");
	}
	public static void idle() {
		HatchManager.currentState = HatchState.idle;
	}
	
	//Other
	public static void foldBack() { HatchSystems.Lazyboy.back(); }
	public static void foldUp() { HatchSystems.Lazyboy.up(); }
	public static boolean isBack() { return HatchSystems.Lazyboy.isBack(); }
	
	//Getters
	public static boolean hasHatch() {
		return HatchManager.currentState == HatchState.hold;
	}
}
