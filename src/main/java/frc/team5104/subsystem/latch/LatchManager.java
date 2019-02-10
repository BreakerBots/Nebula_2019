/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.latch;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;

public class LatchManager extends BreakerSubsystem.Manager {
	public static enum LatchState {
		idle,  //Lazyboy: back, Dad: closed
		intake,//Lazyboy: up, Dad: open
		hold,  //Lazyboy: up, Dad: closed
		eject  //Lazyboy: back, Dad: open
	}
	static LatchState currentState = LatchState.idle;
	static long intakeStartTime = System.currentTimeMillis();
	static long ejectStartTime;
	
	public void update() {
		switch (currentState) {
			case hold:
				LatchSystems.Lazyboy.up();
				LatchSystems.Dad.close();
				break;
			case intake:
				LatchSystems.Lazyboy.up();
				LatchSystems.Dad.open();
				if (System.currentTimeMillis() > _LatchConstants._intakeModeLength + intakeStartTime)
					currentState = LatchState.hold;
				break;
			case idle:
				LatchSystems.Lazyboy.back();
				LatchSystems.Dad.close();
				break;
			case eject:
				LatchSystems.Lazyboy.back();
				LatchSystems.Dad.open();
				if (System.currentTimeMillis() > _LatchConstants._ejectModeLength + ejectStartTime)
					currentState = LatchState.idle;
				break;
			default:
				break;
		}
	}

	public void disabled() { }
	public void enabled(RobotMode mode) { }
	public LatchManager() { }
}
