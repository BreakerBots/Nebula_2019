/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.util.Compressor;
import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;

/**
 * Handles teleoperation control
 */
public class BreakerMainController {
	private static void update(RobotMode currentMode) {
		//Teleop
		if (currentMode == RobotMode.Teleop) {
			//Drive
			DriveController.handle();
			
			//Cargo
			CargoController.handle();
			
			//Hatch
			HatchController.handle();
		}
		
		//Compressor
		if (currentMode == RobotMode.Teleop || currentMode == RobotMode.Test)
			CompressorController.handle();
		else
			Compressor.stop();
		
		//Test Mode
		if (currentMode == RobotMode.Test) {
			DriveController.forceIdle();
			CargoController.forceIdle();
			HatchController.forceIdle();
		}
		
		//Disabled
		if (currentMode != RobotMode.Disabled) {
			//State Switching
			StateController.handle();
		}
	}
	
	//Crash Trackers
	public static void handle(RobotMode currentMode) {
		try { update(currentMode); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	static abstract class BreakerController {
		static void handle() { try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); } }
		static void update() { }
		static void forceIdle() { try { idle(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); } }
		static void idle() { }
	}
}
