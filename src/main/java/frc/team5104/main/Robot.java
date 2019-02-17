/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team5104.auto.AutoSelector;
import frc.team5104.auto.BreakerPathScheduler;
import frc.team5104.control.BreakerTeleopController;
import frc.team5104.control.StateController;
import frc.team5104.subsystem.BreakerSubsystemManager;
import frc.team5104.subsystem.drive.DriveManager;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.subsystem.latch.LatchManager;
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
			 new LatchManager(),
			 new CargoManager()
		);
	}
	
	//Main
	public void mainEnabled() {
		//Ignore second enabling after Sandstorm
		if (DriverStation.getInstance().isFMSAttached() ? BreakerRobotController.isSandstorm() : true) {
			//Initialization
			Compressor.stop();
			BreakerSubsystemManager.enabled(BreakerRobotController.getMode());
			console.logFile.start();
			Odometry.reset();
			BreakerPathScheduler.set( AutoSelector.Paths.Curve.getPath() );
		}
		CSV.init(new VisionMovement());
	}
	public void mainDisabled() {
		BreakerSubsystemManager.disabled();
		console.logFile.end();
		CSV.writeFile("temp", "csvData");
	}
	
	public void mainLoop() {
		CSV.update();
		if (enabled) {
			BreakerSubsystemManager.update();
			StateController.handle();
			Controller.update();
		}
	}

	//Auto
	public void autoStart() {
		Compressor.stop();
	}
	public void autoLoop() { BreakerPathScheduler.update(); }
	
	//Teleop
	public void teleopLoop() { BreakerTeleopController.update(); }
	
	//Vision
	public void visionLoop() { VisionManager.update(); }
	public void visionStart() { VisionManager.start(); }
	public void visionStop() { VisionManager.stop(); }
}
