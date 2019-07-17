/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.main.RobotState;
import frc.team5104.main.RobotState.RobotMode;
import frc.team5104.subsystem.drive.Drive;
import frc.team5104.subsystem.drive.DriveAutoTuneDS;
import frc.team5104.subsystem.drive.DriveAutoTuneMS;
import frc.team5104.util.Compressor;
import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;

/**
 * Handles teleoperation control
 */
public class BreakerMainController {
	
	static DriveController driveController = new DriveController();
	static HatchController hatchController = new HatchController();
	static CargoController cargoController = new CargoController();
	static ClimbController climbController = new ClimbController();
	static StateController stateController = new StateController();
	static CompressorController compressorController = new CompressorController();
	
	//Enabled
	private static void enabled() {
		//DriveAutoTuneMS.init();
		DriveAutoTuneDS.init();
	}
	
	//Update
	private static void update() {
		RobotMode currentMode = RobotState.getMode();
		
		//Teleop
		if (currentMode == RobotMode.Teleop) {
			//Drive
			//driveController.handle();
			//DriveCharacterization.update();
			//Drive.set(DriveAutoTune.getNextSignal());
			Drive.set(DriveAutoTuneDS.getNextSignal());
			
			//Cargo
			cargoController.handle();
			
			//Hatch
			hatchController.handle();
			
			//Climb 
			climbController.handle();
			
			//Compressor
			compressorController.handle();
		}
		
		//Vision
//		if (_Controls.Main._toggleVision.getPressed())
//			Vision.toggleState();
		
		//Compressor
		if (currentMode != RobotMode.Teleop && currentMode != RobotMode.Test)
			Compressor.stop();
		
		//Test Mode
		if (currentMode == RobotMode.Test) {
			driveController.forceIdle();
			cargoController.forceIdle();
			hatchController.forceIdle();
			Compressor.run();
		}
		
		//Enabled
		if (RobotState.isEnabled()) {
			//State Switching
			stateController.handle();
		}
	}
	
	//Crash Trackers
	public static void handle() {
		try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	public static void init() {
		try { enabled(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	static abstract class BreakerController {
		void handle() { try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); } }
		abstract void update();
		void forceIdle() { try { idle(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); } }
		void idle() {  }
	}
}
