/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.main.BreakerRobotController.RobotMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main.Devices;
import frc.team5104.util.Compressor;
import frc.team5104.util.CrashLogger;
import frc.team5104.util.CrashLogger.Crash;
import frc.team5104.util.console;

/**
 * Handles teleoperation control
 */
public class BreakerMainController {
	
	static DriveController driveController = new DriveController();
	static HatchController hatchController = new HatchController();
	static CargoController cargoController = new CargoController();
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
			
			if (_Controls.Climb._stage1.getPressed())
				Devices.Climber.stage1.set(
						Devices.Climber.stage1.get() == DoubleSolenoid.Value.kForward ? 
								DoubleSolenoid.Value.kReverse : 
									DoubleSolenoid.Value.kForward
					);
			if (_Controls.Climb._stage2.getPressed())
				Devices.Climber.stage2.set(
						Devices.Climber.stage2.get() == DoubleSolenoid.Value.kForward ? 
								DoubleSolenoid.Value.kReverse : 
									DoubleSolenoid.Value.kForward
					);
		}
		
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
