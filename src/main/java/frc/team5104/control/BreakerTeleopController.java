package frc.team5104.control;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team5104.control.Controls.Hatch;
import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.subsystem.intake.IntakeSystems;
import frc.team5104.subsystem.latch.Latch;
import frc.team5104.subsystem.latch.LatchSystems;
import frc.team5104.superstructure.cargo.Cargo;
import frc.team5104.util.Compressor;
import frc.team5104.vision.Vision;
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
			Vision.runVision(RobotMode.Teleop, ActionMode.cargo);
		
		//Chute
		if (Controls.Cargo._trapdoorUp.getPressed())
			Chute.trapdoorUp();
		if (Controls.Cargo._trapdoorDown.getPressed()) {
			if (!Latch.hasHatch())
				Chute.trapdoorDown();
		}
		
		//Latch
		if (Controls.Hatch._eject.getPressed())
			LatchSystems.Dad.open();
//			Latch.eject();
//			Vision.runVision(RobotMode.Teleop, ActionMode.hatchEject);
		if (Controls.Hatch._intake.getPressed()) {
			LatchSystems.Dad.close();
//			Latch.intake();
//			Vision.runVision(RobotMode.Teleop, ActionMode.hatchIntake);
			Chute.trapdoorUp();
		}
			
		//Compressor
//		if (Controls.Main._toggleCompressor.getPressed()) {
//			if (Compressor.isRunning()) Compressor.stop();
//			else Compressor.run();
//		}
		if (Compressor.shouldRun(0)) Compressor.run();
		else Compressor.stop();
		Compressor.buffer.update(Compressor.getPressure());
		
	}
}
