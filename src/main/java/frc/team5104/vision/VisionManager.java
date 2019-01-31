/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.subsystem.drive.DriveActions;
import frc.team5104.subsystem.drive.RobotDriveSignal;

public class VisionManager {
	
	public static enum VisionPipeline {
		line,
		target;
	}
	
	public static void init() {
		VisionSystems.init();
		VisionActions.changePipeline(VisionPipeline.target);
	}
	
	public static void enabled() {
		VisionActions.reset();
	}
	
	public static void update() {
		RobotDriveSignal signal = VisionActions.getNextSignal();
		DriveActions.applyMotorMinSpeedRough(signal);
		DriveActions.set(signal);
	}

	public static void disabled() { }
}
