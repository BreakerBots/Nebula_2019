/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.subsystem.drive.DriveActions;
import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.subsystem.drive.RobotDriveSignal.DriveUnit;

public class VisionManager {
	
	public static enum VisionPipeline {
		line,
		target;
	}
	
	public static void init() {
		VisionSystems.init();
		VisionActions.changePipeline(VisionPipeline.target);
	}
	
	public static void update() {
		RobotDriveSignal signal = new RobotDriveSignal(
				VisionActions.getForward() + VisionActions.getLeftOutput() - VisionActions.getRightOutput(), 
				VisionActions.getForward() + VisionActions.getRightOutput() - VisionActions.getLeftOutput(), 
				DriveUnit.percentOutput
			);
		DriveActions.applyMotorMinSpeed(signal);
		DriveActions.set(signal);
	}
}
