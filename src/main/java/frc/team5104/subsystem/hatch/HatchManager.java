/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.hatch;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;

public class HatchManager extends BreakerSubsystem.Manager {
	public static enum LatchState {
		intake,//Trap: open, Lazyboy: up, Ejector: in
		idle,  //Trap: open, Lazyboy: either, Ejector: in
		hold,  //Trap: closed, Lazyboy: up, Ejector: in
		eject  //Trap: open, Lazyboy: up, Ejector: either
	}
	static LatchState currentState = LatchState.idle;
	static long ejectStartTime = System.currentTimeMillis();
	static boolean ejectHard = false;
	static long intakeStartTime = System.currentTimeMillis();
	
	public void update() {
		switch (currentState) {
			case hold:
				HatchSystems.Trap.close();
				HatchSystems.Lazyboy.up();
				HatchSystems.Ejector.pullOut();
				break;
			case idle:
				HatchSystems.Trap.close();
				HatchSystems.Ejector.pullOut();
				break;
			case eject:
				HatchSystems.Trap.open();
				HatchSystems.Lazyboy.up();
				
				if (ejectHard)
					HatchSystems.Ejector.yeet();
				else
					HatchSystems.Ejector.pullOut();
					
				if (System.currentTimeMillis() > _HatchConstants._ejectModeLength + ejectStartTime)
					currentState = LatchState.idle;
				break;
			case intake:
				HatchSystems.Trap.open();
				HatchSystems.Lazyboy.up();
				HatchSystems.Ejector.pullOut();
				
				if (System.currentTimeMillis() > _HatchConstants._intakeModeLength + intakeStartTime)
					currentState = LatchState.idle;
			default:
				break;
		}
	}

	public void disabled() { }
	public void enabled(RobotMode mode) { }
	public HatchManager() { }
}
