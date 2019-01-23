/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import frc.team5104.auto.BreakerPathScheduler;
import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystemManager;
import frc.team5104.subsystem.drive.DriveManager;
import frc.team5104.subsystem.drive.DriveSystems;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.teleop.BreakerTeleopController;
import frc.team5104.teleop.Drive;
import frc.team5104.util.console;
import frc.team5104.util.controller;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;
import frc.team5104.vision.VisionManager;

/**
 * Fallthrough from <strong>Breaker Robot Controller</strong>
 */
public class Robot extends BreakerRobotController.BreakerRobot {
	public Robot() {
		BreakerSubsystemManager.throwSubsystems(
			 new DriveManager()
		);
		
		VisionManager.init();
		
		//CameraServer.getInstance().startAutomaticCapture();
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
		Drive.log.writeFile("vision_temp", "urmom");
	}

	public void mainLoop() {
		if (enabled) {
			BreakerSubsystemManager.update();
			
			//Vision Toggling
			if (HMI.Main._toggleVision.getPressed()) {
				BreakerRobotController.setMode(BreakerRobotController.getMode() == RobotMode.Vision ? RobotMode.Teleop : RobotMode.Vision);
				console.log(c.VISION, t.INFO, BreakerRobotController.getMode() == RobotMode.Vision ? "Switched to vision" : "Switched to drive");
			}
			
			//Auto Switching
			if (HMI.Main._toggleAuto.getPressed()) {
				//...
			}
			
			controller.update();
		}
	}

	//Auto
	public void autoEnabled() {
		//BreakerPathScheduler.set(
		//	AutoSelector.getAuto()
 		//	AutoSelector.Paths.Curve.getPath()
		//);
	}
	public void autoLoop() { BreakerPathScheduler.update(); }
	
	//Teleop
	public void teleopLoop() { BreakerTeleopController.update(); }
	
	//Vision
	public void visionLoop() { VisionManager.update(); }
	public void visionEnabled() { VisionManager.enabled(); }
	public void visionDisabled() { VisionManager.disabled(); }
}
