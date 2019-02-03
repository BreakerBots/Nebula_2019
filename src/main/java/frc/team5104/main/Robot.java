/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import frc.team5104.auto.AutoSelector;
import frc.team5104.auto.BreakerPathScheduler;
import frc.team5104.control.BreakerTeleopController;
import frc.team5104.control.StateController;
import frc.team5104.subsystem.BreakerSubsystemManager;
import frc.team5104.subsystem.drive.DriveManager;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.util.console;
import frc.team5104.util.controller;
import frc.team5104.vision.VisionManager;

/**
 * Fallthrough from <strong>Breaker Robot Controller</strong>
 */
public class Robot extends BreakerRobotController.BreakerRobot {
	public Robot() {
		BreakerSubsystemManager.throwSubsystems(
			 new DriveManager()
		);
	}
	
	//Main
	public void mainEnabled() {
		Devices.Main.compressor.stop();
		BreakerSubsystemManager.enabled(BreakerRobotController.getMode());
		console.logFile.start();
		Odometry.reset();
	}
	public void mainDisabled() {
		BreakerSubsystemManager.disabled();
		console.logFile.end();
	}
	public void mainLoop() {
		if (enabled) {
			BreakerSubsystemManager.update();
			StateController.handle();
			controller.update();
		}
	}

	//Auto
	public void autoStart() {
		Devices.Main.compressor.stop();
		BreakerPathScheduler.set( AutoSelector.Paths.Curve.getPath() );
	}
	public void autoLoop() { BreakerPathScheduler.update(); }
	
	//Teleop
	public void teleopLoop() { BreakerTeleopController.update(); }
	
	//Vision
	public void visionLoop() { VisionManager.update(); }
	public void visionStart() { VisionManager.start(); }
	public void visionStop() { VisionManager.stop(); }
}
