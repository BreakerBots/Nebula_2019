/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.main.RobotState;
import frc.team5104.main.RobotState.RobotMode;
import frc.team5104.subsystem.drive.Drive;
import frc.team5104.subsystem.drive.DriveSignal;
import frc.team5104.util.CrashLogger;
import frc.team5104.util.console;
import frc.team5104.util.CrashLogger.Crash;
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
		Vision.changeLEDState(false);
	}
	
	public static void start() {
		Vision.reset();
		Vision.changeLEDState(true);
	}
	
	public static void handle() {
		try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	private static void update() {
		if (VisionMovement.isFinished()) {
//			RobotState.setMode(exitState);
//			if (exitAction != null)
//				Vision.action(exitAction);
			//Drive.set
			Drive.stop();
		}
		else {
			DriveSignal signal = Vision.getNextSignal();
			Drive.applyMotorMinSpeedRough(signal);
			Drive.set(signal);
		}
	}

	public static void stop() {
		Vision.changeLEDState(false);
	}
}
