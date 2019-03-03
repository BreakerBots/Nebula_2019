/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team5104.main.RobotState.RobotMode;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;
import frc.team5104.vision.VisionManager;
import frc.team5104.webapp.Webapp;

class RobotController extends RobotBase {
	//Modes
	private BreakerRobot robot;
	private RobotState state = RobotState.getInstance();
	private final double loopPeriod = 20;
	
	//Initialization
	public void startCompetition() {
		HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_Iterative);
		console.sets.create("RobotInit");
		console.log(c.MAIN, t.INFO, "Initializing Code");
		
		robot = new Robot();
		
		HAL.observeUserProgramStarting();
		
		//Start Web App
		Webapp.init();
		
		//Initialize Vision
		VisionManager.init();
		
		//Run Odometry
		Odometry.run();
		
		console.log(c.MAIN, "Devices Created and Seth Proofed");
		console.sets.log(c.MAIN, t.INFO, "RobotInit", "Initialization took ");
		
		//Main Loop
		while (true) {
			double st = Timer.getFPGATimestamp();
			
			//Call main loop function (and crash tracker)
			try {
				loop();
			} catch (Exception e) {
				CrashLogger.logCrash(new Crash("main", e));
			}
			
			try { Thread.sleep(Math.round(loopPeriod - (Timer.getFPGATimestamp() - st))); } catch (Exception e) { console.error(e); }
		}
	}

	//Main Loop
	private void loop() {
		if (isDisabled())
			state.currentMode = RobotMode.Disabled;
		
		if (isAutonomous())
			state.isSandstorm = true;
		
		else if (isEnabled()) {
			//Forced Through Driver Station
			if (isTest())
				state.currentMode = RobotMode.Test;
			
			//Default to Teleop
			else if (state.currentMode == RobotMode.Disabled)
				state.currentMode = RobotMode.Teleop;
		}
		
		//Handle Modes
		switch(state.currentMode) {
			case Auto: {
				try {
					//Auto
					if (state.lastMode != state.currentMode)
						robot.autoStart();
						
					robot.autoLoop();
					HAL.observeUserProgramAutonomous();
				} catch (Exception e) {
					CrashLogger.logCrash(new Crash("main", e));
				}
				break;
			}
			case Teleop: {
				try {
					//Teleop
					if (state.lastMode != state.currentMode)
						robot.teleopStart();
					
					robot.teleopLoop();
					HAL.observeUserProgramTeleop();
					DriverStation.getInstance().waitForData(0.2);
				} catch (Exception e) {
					CrashLogger.logCrash(new Crash("main", e));
				}
				break;
			}
			case Vision: {
				try {
					//Vision
					if (state.lastMode != state.currentMode)
						robot.visionStart();
					
					robot.visionLoop();
					HAL.observeUserProgramTeleop();
				} catch (Exception e) {
					CrashLogger.logCrash(new Crash("main", e));
				}
				break;
			}
			case Test: {
				try {
					//Test
					if (state.lastMode != state.currentMode)
						robot.testStart();
					
					robot.testLoop();
					HAL.observeUserProgramTest();
				} catch (Exception e) {
					CrashLogger.logCrash(new Crash("main", e));
				}
				break;
			}
			case Disabled: {
				try {
					//Disabled
					if (state.lastMode != state.currentMode)
						switch (state.lastMode) {
							case Auto: 	 { robot.autoStop(); break; }
							case Teleop: { robot.teleopStop(); break; }
							case Test: 	 { robot.testStop(); break; }
							case Vision :{ robot.visionStop(); break; }
							default: break;
						}
					
					HAL.observeUserProgramDisabled();
				} catch (Exception e) {
					CrashLogger.logCrash(new Crash("main", e));
				}
				break;
			}
			default: break;
		}
		
		//Handle Main Disabling
		try {
			if (state.lastMode != state.currentMode) {
				if (state.currentMode == RobotMode.Disabled) {
					robot.mainDisabled();
				}
				else if (state.lastMode == RobotMode.Disabled) {
					robot.mainEnabled();
				}
				LiveWindow.setEnabled(state.currentMode == RobotMode.Disabled);
				state.lastMode = state.currentMode;
			}
		} catch (Exception e) {
			CrashLogger.logCrash(new Crash("main", e));
		}
		
		//Update Main Robot Loop
		try {
			robot.mainLoop();
		} catch (Exception e) {
			CrashLogger.logCrash(new Crash("main", e));
		}
		
		//Update Live Window
		LiveWindow.updateValues();
	}
	
	public static void main(String[] args) {
		RobotController.startRobot(RobotController::new);
	}
	
	//Child Class
	/**
	 * The Main Robot Interface. Called by this, Breaker Robot Controller
	 * <br>Override these methods to run code
	 * <br>Functions Call Order:
	 * <br> - All Enable/Disable Functions are called before the corresponding loop function
	 * <br> - Main Functions are called last (teleop, test, auto are before)
	 */
	static abstract class BreakerRobot {
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