/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;

public class BreakerRobotController extends BreakerRobotControllerBase {
	public static enum RobotMode {
		Disabled,
		Auto,
		Teleop,
		Test,
		Vision;
	}
	private static RobotMode currentMode = RobotMode.Disabled;
	private static RobotMode lastMode = RobotMode.Disabled;
	private static BreakerRobot robot;
	private static final double loopPeriod = 20;

	public static void setMode(RobotMode mode) { currentMode = mode; }
	public static RobotMode getMode() { return currentMode; }
	
	public static void startCompetition() {
		HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_Iterative);
		
		console.sets.create("RobotInit");
		console.log(c.MAIN, t.INFO, "Initializing Code");
		
		//Initialize Robot
		robot = new Robot();
		
		//Run Odometry
		Odometry.run();
		
		//Update HAL
		HAL.observeUserProgramStarting();
		
		console.log(c.MAIN, "Devices Created and Seth Proofed");
		console.sets.log(c.MAIN, t.INFO, "RobotInit", "Initialization took ");
		
		//Main Loop
		while (true) {
			double st = Timer.getFPGATimestamp();
			
			loop();
			
			try {
				Thread.sleep(Math.round(loopPeriod - (Timer.getFPGATimestamp() - st)));
			} catch (Exception e) { console.error(e); }
		}
	}

	//Main Loop
	private static void loop() {
		if (isDisabled())
			setMode(RobotMode.Disabled);
		else if (isAutonomous())
			setMode(RobotMode.Auto);
		else if (isTest())
			setMode(RobotMode.Test);
		
		//Handle Modes
		switch(currentMode) {
			case Auto: {
				if (lastMode != currentMode)
					robot.autoEnabled();
					
				robot.autoLoop();
				HAL.observeUserProgramAutonomous();
				break;
			}
			case Teleop: {
				if (lastMode != currentMode)
					robot.teleopEnabled();
				
				robot.teleopLoop();
				HAL.observeUserProgramTeleop();
				break;
			}
			case Vision: {
				if (lastMode != currentMode)
					robot.visionEnabled();
				
				robot.visionLoop();
				HAL.observeUserProgramTeleop();
				break;
			}
			case Test: {
				if (lastMode != currentMode)
					robot.testEnabled();
				
				robot.testLoop();
				HAL.observeUserProgramTest();
				break;
			}
			case Disabled: {
				if (lastMode != currentMode)
					switch (lastMode) {
						case Auto: 	 { robot.autoDisabled(); break; }
						case Teleop: { robot.teleopDisabled(); break; }
						case Test: 	 { robot.testDisabled(); break; }
						case Vision :{ robot.visionDisabled(); break; }
						default: break;
					}
				
				HAL.observeUserProgramDisabled();
				break;
			}
			default: break;
		}
		
		//Handle Mode Change
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
		public void teleopEnabled() { }
		public void teleopDisabled() { }
		public void autoLoop() { }
		public void autoEnabled() { }
		public void autoDisabled() { }
		public void testLoop() { }
		public void testEnabled() { }
		public void testDisabled() { }
		public void visionLoop() { }
		public void visionEnabled() { }
		public void visionDisabled() { }
	}
}