/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.hatch;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;

public class HatchManager extends BreakerSubsystem.Manager {
	public static enum LatchState {
		idle,  //Trap: open, Lazyboy: either, Ejector: in
		hold,  //Trap: closed, Lazyboy: up, Ejector: in
		eject  //Trap: open, Lazyboy: up, Ejector: either
	}
	static LatchState currentState = LatchState.idle;
	static long ejectStartTime = System.currentTimeMillis();
	
	public void update() {
		switch (currentState) {
			case hold:
				HatchSystems.Trap.close();
				break;
			case idle:
				HatchSystems.Trap.close();
				break;
			case eject:
				HatchSystems.Trap.open();
				if (System.currentTimeMillis() > _HatchConstants._ejectModeLength + ejectStartTime)
					currentState = LatchState.idle;
				break;
			default:
				break;
		}
	}

	public void disabled() { }
	public void enabled(RobotMode mode) { }
	public HatchManager() { }
}
