/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.main.RobotState;
import frc.team5104.main.RobotState.RobotMode;
import frc.team5104.subsystem.drive.DriveSignal;
import frc.team5104.subsystem.hatch.Hatch;
import frc.team5104.superstructure.cargo.Cargo;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;
import frc.team5104.vision.VisionManager.ActionMode;
import frc.team5104.vision.VisionMovement.VisionTarget;

public class Vision {
	/** Gets the desired drivetrain output to aligh with the visible target */
	public static DriveSignal getNextSignal() { return VisionMovement.getNextSignal();  }
	public static void reset() { VisionMovement.reset(); }
	
	/** Returns if there is a target currently visible to the limelight */
	public static boolean targetVisible() { return VisionSystems.limelight.getA() != 0; }
	
	/** Changes the state of the leds (on, off...) */
	public static void changeLEDState(boolean state) { 
		VisionSystems.networkTable.setEntry("ledMode", state ? 0 : 1); 
	}

	public static void init() {
		VisionSystems.networkTable.setEntry("camMode", 0);
	}
	
	/** Toggle the state of the limelight */
	public static void toggleDrivingMode() {
//		boolean driverMode = VisionSystems.networkTable.getEntry("camMode").getDouble(-1) == 1;
//		VisionSystems.networkTable.setEntry("camMode", driverMode ? 0 : 1);
//		changeLEDState(driverMode ? 1 : 0);
//		changeLEDState(0);
//		boolean driverMode = VisionSystems.networkTable.getEntry("pipeline").getDouble(-1) == 1;
//		VisionSystems.networkTable.setEntry("pipeline", driverMode ? 0 : 1);
	}
	
	/**
	 * Runs vision
	 */
	public static void runVision(RobotMode exitState, ActionMode exitAction, VisionTarget target) {
		RobotState.setMode(RobotMode.Vision);
		VisionManager.exitState = exitState;
		VisionManager.exitAction = exitAction;
		VisionManager.target = target;
		console.log(c.VISION, t.INFO, "Running Vision");
	}
	
	static void action(ActionMode exitAction) {
		switch(exitAction) {
		case cargo:
			Cargo.eject();
			break;
		case hatchEject:
			Hatch.eject(false);
			break;
		case hatchIntake:
			Hatch.intake();
			break;
		default:
			break;
		
		}
	}
}
