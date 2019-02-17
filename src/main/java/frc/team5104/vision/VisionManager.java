/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.main.BreakerRobotController;
import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.drive.Drive;
import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.vision.VisionMovement.VisionTarget;

public class VisionManager {
	static RobotMode exitState;
	static ActionMode exitAction;
	static VisionTarget target;
	
	public static enum ActionMode {
		cargo,
		hatchIntake, 
		hatchEject
	}
	
	public static void init() {
		VisionSystems.init();
		VisionSystems.networkTable.setEntry("pipeline", 0);
	}
	
	public static void start() {
		Vision.reset();
	}
	
	public static void update() {
		if (VisionMovement.isFinished()) {
			BreakerRobotController.setMode(exitState);
			Vision.action(exitAction);
		}
		else {
			RobotDriveSignal signal = Vision.getNextSignal();
			Drive.applyMotorMinSpeedRough(signal);
			Drive.set(signal);
		}
	}

	public static void stop() {
		
	}
}
