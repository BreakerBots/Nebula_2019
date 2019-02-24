/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.main.BreakerRobotController.RobotMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main.Devices;
import frc.team5104.util.Compressor;
import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;
import frc.team5104.vision.Vision;

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
	
	private static void update(RobotMode currentMode) {
		//Teleop
		if (currentMode == RobotMode.Teleop) {
			//Drive
			driveController.handle();
			
			//Cargo
			cargoController.handle();
			
			//Hatch
			hatchController.handle();
			
			//Climb 
			climbController.handle();
		}
		
		//Vision
		if (_Controls.Main._toggleVision.getPressed())
			Vision.toggleState();
		
		//Compressor
		if (currentMode == RobotMode.Teleop || currentMode == RobotMode.Test)
			compressorController.handle();
		else
			Compressor.stop();
		
		//Test Mode
		if (currentMode == RobotMode.Test) {
			driveController.forceIdle();
			cargoController.forceIdle();
			hatchController.forceIdle();
		}
		
		//Disabled
		if (currentMode != RobotMode.Disabled) {
			//State Switching
			stateController.handle();
		}
	}
	
	//Crash Trackers
	public static void handle(RobotMode currentMode) {
		try { update(currentMode); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	static abstract class BreakerController {
		void handle() { try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); } }
		abstract void update();
		void forceIdle() { try { idle(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); } }
		void idle() {  }
	}
}
