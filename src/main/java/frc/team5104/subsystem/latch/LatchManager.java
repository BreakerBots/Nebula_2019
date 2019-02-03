/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.latch;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;

public class LatchManager extends BreakerSubsystem.Manager {
	public static enum LatchState {
		idle,  //Lazyboy: back, Dad: open
		intake,//Lazyboy: up, Dad: open
		hold   //Lazyboy: up, Dad: closed
	}
	
	public void enabled(RobotMode mode) {
		
	}
	
	public void update() {
		
	}

	public void disabled() {
		
	}
	
	public LatchManager() {

	}
}
