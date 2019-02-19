/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import frc.team5104.auto.AutoSelector;
import frc.team5104.auto.BreakerPathScheduler;
import frc.team5104.control.BreakerTeleopController;
import frc.team5104.control.StateController;
import frc.team5104.subsystem.BreakerSubsystemManager;
import frc.team5104.subsystem.drive.DriveManager;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.subsystem.hatch.HatchManager;
import frc.team5104.subsystem.intake.IntakeSystems;
import frc.team5104.util.console;
import frc.team5104.util.CSV;
import frc.team5104.util.Compressor;
import frc.team5104.util.Controller;
import frc.team5104.vision.VisionManager;
import frc.team5104.vision.VisionMovement;
import frc.team5104.superstructure.cargo.CargoManager;

/**
 * Fallthrough from <strong>Breaker Robot Controller</strong>
 */
public class Robot extends BreakerRobotController.BreakerRobot {
	public Robot() {
		BreakerSubsystemManager.throwSubsystems(
			 new DriveManager(),
//			 new HatchManager(),
			 new CargoManager()
		);
	}
	
	//Main
	public void mainEnabled() {
		//TODO: ignore enable/disable between sandstorm/teleop
		Compressor.stop();
		console.logFile.start();
		console.log("Robot Enabled");
		BreakerSubsystemManager.enabled(BreakerRobotController.getMode());
		Odometry.reset();
		BreakerPathScheduler.set( AutoSelector.Paths.Curve.getPath() );
		CSV.init(new VisionMovement());
	}
	public void mainDisabled() {
		//TODO: ignore enable/disable between sandstorm/teleop
		console.log("Robot Disabled");
		BreakerSubsystemManager.disabled();
		console.logFile.end();
		CSV.writeFile("temp", "csvData");
	}
	
	public void mainLoop() {
//		console.log( IntakeSystems.LimitSwitch.isHit());
		CSV.update();
		if (enabled) {
			BreakerSubsystemManager.update();
			StateController.handle();
			Controller.update();
		}
	}

	//Auto
	public void autoStart() {
		console.log("Autonomous Started");
		Compressor.stop();
	}
	public void autoLoop() { BreakerPathScheduler.update(); }
	public void autoStop() {
		console.log("Autonomous Stopped");
	}
	
	//Teleop
	public void teleopStart() {
		console.log("Teleoperation Started");
	}
	public void teleopLoop() { BreakerTeleopController.update(); }
	public void teleopStop() {
		console.log("Teleoperation Stopped");
	}
	
	//Vision
	public void visionStart() { 
		console.log("Vision Started");
		VisionManager.start(); 
	}
	public void visionLoop() { VisionManager.update(); }
	public void visionStop() { 
		console.log("Vision Stopped");
		VisionManager.stop(); 
	}
}
