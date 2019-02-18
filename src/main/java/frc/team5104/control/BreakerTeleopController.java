package frc.team5104.control;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.subsystem.intake.IntakeSystems;
import frc.team5104.superstructure.cargo.Cargo;
import frc.team5104.util.Compressor;
import frc.team5104.vision.Vision;
import frc.team5104.vision.VisionMovement.VisionTarget;
import frc.team5104.vision.VisionManager.ActionMode;

public class BreakerTeleopController {
	public static void update() {
		//Drive
		DriveController.handle();
		
		if (Controls.Main._idle.getPressed()) 
			Cargo.idle();
		
		//Cargo
		Devices.Cargo.belt.set(ControlMode.PercentOutput, Controls.Cargo._beltManual.getHeld() ? 1 : 0);
		IntakeSystems.Arm.setVoltage(Controls.Cargo._armManual.getAxis() * 10);
		if (Controls.Cargo._intake.getPressed()) 
			Cargo.intake();
		if (Controls.Cargo._eject.getPressed())
			Vision.runVision(RobotMode.Teleop, ActionMode.cargo, Chute.isDown() ? 
					VisionTarget.rocket : VisionTarget.standard);
		
		//Chute
		if (Controls.Cargo._trapdoorUp.getPressed())
			Chute.trapdoorUp();
		if (Controls.Cargo._trapdoorDown.getPressed()) {
			Chute.trapdoorDown();
		}
		
		//Latch
		if (Controls.Hatch._eject.getPressed())
//			HatchSystems.Trap.open();
//			Latch.eject();
			Vision.runVision(RobotMode.Teleop, ActionMode.hatchEject, VisionTarget.standard);
		if (Controls.Hatch._intake.getPressed()) {
//			HatchSystems.Trap.close();
//			Latch.intake();
			Chute.trapdoorUp();
			Vision.runVision(RobotMode.Teleop, ActionMode.hatchIntake, VisionTarget.standard);
		}
			
		//Compressor
		if (Controls.Main._automaticCompressor) {
			if (Compressor.shouldRun(0)) 
				Compressor.run();
			else 
				Compressor.stop();
			Compressor.buffer.update(Compressor.getPressure());
		}
		else {
			if (Controls.Main._toggleCompressor.getPressed()) {
				if (Compressor.isRunning()) 
					Compressor.stop();
				else 
					Compressor.run();
			}
		}
	}
}
