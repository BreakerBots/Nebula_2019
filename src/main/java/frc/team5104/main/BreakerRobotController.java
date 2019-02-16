/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;
import frc.team5104.vision.VisionManager;

public class BreakerRobotController extends BreakerRobotControllerBase {
	//Modes
	public static enum RobotMode { Disabled, Auto, Teleop, Test, Vision; }
	private static RobotMode currentMode = RobotMode.Disabled;
	private static RobotMode lastMode = RobotMode.Disabled;
	public static void setMode(RobotMode mode) { currentMode = mode; }
	public static RobotMode getMode() { return currentMode; }
	
	private static BreakerRobot robot;
	private static final double loopPeriod = 20;
	
	private static boolean isSandstorm;
	public static boolean isSandstorm() { return isSandstorm; }
	
	//Init
	public void startCompetition() {
		HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_Iterative);
		console.sets.create("RobotInit");
		console.log(c.MAIN, t.INFO, "Initializing Code");
		
		robot = new Robot();
		
		HAL.observeUserProgramStarting();
		
		//Initialize Vision
		VisionManager.init();
		
		//Run Odometry
		Odometry.run();
		
		console.log(c.MAIN, "Devices Created and Seth Proofed");
		console.sets.log(c.MAIN, t.INFO, "RobotInit", "Initialization took ");
		
		//Main Loop
		while (true) {
			double st = Timer.getFPGATimestamp();
			loop();
			try { Thread.sleep(Math.round(loopPeriod - (Timer.getFPGATimestamp() - st))); } catch (Exception e) { console.error(e); }
		}
	}

	//Main Loop
	private void loop() {
		if (isDisabled())
			setMode(RobotMode.Disabled);
		
		if (isAutonomous())
			isSandstorm = true;
		
		else if (isEnabled()) {
			//Forced Through Driver Station
			if (isTest())
				setMode(RobotMode.Test);
			
			//Default to Teleop
			else if (currentMode == RobotMode.Disabled)
				setMode(RobotMode.Teleop);
		}
		
		//Handle Modes
		switch(currentMode) {
			case Auto: {
				if (lastMode != currentMode)
					robot.autoStart();
					
				robot.autoLoop();
				HAL.observeUserProgramAutonomous();
				break;
			}
			case Teleop: {
				if (lastMode != currentMode)
					robot.teleopStart();
				
				robot.teleopLoop();
				HAL.observeUserProgramTeleop();
				DriverStation.getInstance().waitForData(0.2);
				break;
			}
			case Vision: {
				if (lastMode != currentMode)
					robot.visionStart();
				
				robot.visionLoop();
				HAL.observeUserProgramTeleop();
				break;
			}
			case Test: {
				if (lastMode != currentMode)
					robot.testStart();
				
				robot.testLoop();
				HAL.observeUserProgramTest();
				break;
			}
			case Disabled: {
				if (lastMode != currentMode)
					switch (lastMode) {
						case Auto: 	 { robot.autoStop(); break; }
						case Teleop: { robot.teleopStop(); break; }
						case Test: 	 { robot.testStop(); break; }
						case Vision :{ robot.visionStop(); break; }
						default: break;
					}
				
				HAL.observeUserProgramDisabled();
				break;
			}
			default: break;
		}
		
		//Handle Main Disabling
		if (lastMode != currentMode) {
			if (currentMode == RobotMode.Disabled) {
				robot.mainDisabled();
				BreakerRobot.enabled = false;
			}
			else if (lastMode == RobotMode.Disabled) {
				robot.mainEnabled();
				BreakerRobot.enabled = true;
			}
			LiveWindow.setEnabled(currentMode == RobotMode.Disabled);
			lastMode = currentMode;
		}
		
		//Update Main Robot Loop
		robot.mainLoop();
		
		//Update Live Window
		LiveWindow.updateValues();
	}
	
	//Child Class
	/**
	 * The Main Robot Interface. Called by this, Breaker Robot Controller
	 * <br>Override these methods to run code
	 * <br>Functions Call Order:
	 * <br> - All Enable/Disable Functions are called before the corresponding loop function
	 * <br> - Main Functions are called last (teleop, test, auto are before)
	 */
	public static abstract class BreakerRobot {
		public static boolean enabled;
		
		public void mainLoop() { }
		public void mainEnabled() { }
		public void mainDisabled() { }
		public void teleopLoop() { }
		public void teleopStart() { }
		public void teleopStop() { }
		public void autoLoop() { }
		public void autoStart() { }
		public void autoStop() { }
		public void testLoop() { }
		public void testStart() { }
		public void testStop() { }
		public void visionLoop() { }
		public void visionStart() { }
		public void visionStop() { }
	}
}