/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.main.BreakerRobotController;
import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.subsystem.hatch.Hatch;
import frc.team5104.superstructure.cargo.Cargo;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;
import frc.team5104.vision.VisionManager.ActionMode;

public class Vision {
	/** Gets the desired drivetrain output to aligh with the visible target */
	public static RobotDriveSignal getNextSignal() { return VisionMovement.getNextSignal();  }
	public static void reset() { VisionMovement.reset(); }
	
	/** Returns if there is a target currently visible to the limelight */
	public static boolean targetVisible() { return VisionSystems.limelight.getA() != 0; }
	
	/** Changes the state of the leds (on, off...) */
	public static void changeLEDState(int state) { VisionSystems.networkTable.setEntry("ledMode", (double)(state)); }
	
	/**
	 * Runs vision
	 */
	public static void runVision(RobotMode exitState, ActionMode exitAction) {
		BreakerRobotController.setMode(RobotMode.Vision);
		VisionManager.exitState = exitState;
		VisionManager.exitAction = exitAction;
		console.log(c.VISION, t.INFO, "Running Vision");
	}
	
	static void action(ActionMode exitAction) {
		switch(exitAction) {
		case cargo:
			Cargo.eject();
			break;
		case hatchEject:
			Hatch.eject();
			break;
		case hatchIntake:
			Hatch.intake();
			break;
		default:
			break;
		
		}
	}
}
