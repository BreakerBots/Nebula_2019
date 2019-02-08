/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.subsystem.drive.Drive;
import frc.team5104.subsystem.drive.RobotDriveSignal;

public class VisionManager {
	
	public static void init() {
		VisionSystems.init();
		VisionSystems.networkTable.setEntry("pipeline", 1);
	}
	
	public static void start() {
		Vision.reset();
	}
	
	public static void update() {
		RobotDriveSignal signal = Vision.getNextSignal();
		Drive.applyMotorMinSpeedRough(signal);
		Drive.set(signal);
	}

	public static void stop() { }
}
