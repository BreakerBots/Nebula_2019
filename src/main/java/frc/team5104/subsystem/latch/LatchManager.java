/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.latch;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;

public class LatchManager extends BreakerSubsystem.Manager {
	public static enum LatchState {
		idle,  //Lazyboy: back, Dad: closed
		intake,//Lazyboy: up, Dad: open
		hold   //Lazyboy: up, Dad: closed
	}
	static LatchState currentState = LatchState.idle;
	static long intakeStartTime = System.currentTimeMillis();
	
	public void update() {
		switch (currentState) {
			case hold:
				LatchSystems.Lazyboy.up();
				LatchSystems.Dad.close();
				break;
			case intake:
				LatchSystems.Lazyboy.up();
				LatchSystems.Dad.open();
				if (System.currentTimeMillis() + _LatchConstants._intakeModeLength > intakeStartTime)
					currentState = LatchState.hold;
				break;
			case idle:
				LatchSystems.Lazyboy.down();
				LatchSystems.Dad.close();
				break;
			default:
				break;
		}
	}

	public void disabled() { }
	public void enabled(RobotMode mode) { }
	public LatchManager() { }
}
