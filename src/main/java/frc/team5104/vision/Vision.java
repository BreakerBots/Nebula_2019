/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.subsystem.drive.RobotDriveSignal;

public class Vision {
	/** Gets the desired drivetrain output to aligh with the visible target */
	public static RobotDriveSignal getNextSignal() { return VisionMovement.getNextSignal();  }
	public static void reset() { VisionMovement.reset(); }
	
	/** Returns if there is a target currently visible to the limelight */
	public static boolean targetVisible() { return VisionSystems.limelight.getA() != 0; }
	
	/** Changes the state of the leds (on, off...) */
	public static void changeLEDState(int state) { VisionSystems.networkTable.setEntry("ledMode", (double)(state)); }
}
