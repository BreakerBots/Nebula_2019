package frc.team5104.main.control;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team5104.main.Devices;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.subsystem.intake.IntakeSystems;
import frc.team5104.subsystem.latch.Latch;
import frc.team5104.util.controller.Control;

public class BreakerTeleopController {
	public static void update() {
		//Drive
		DriveController.handle();
		
		//Cargo
		Devices.Cargo.belt.set(ControlMode.PercentOutput, Control.S.getHeld() ? 1 : 0);
		IntakeSystems.Arm.set(Control.RY.getAxis());
//		if (Control.X.getPressed())
//			Cargo.intake();
//		if (Control.B.getPressed())
//			Cargo.eject();
//		if (Control.LIST.getPressed())
//			Cargo.idle();
		
		//Chute
		if (Control.N.getPressed())
			Chute.trapdoorUp();
		if (Control.S.getPressed()) {
			Chute.trapdoorDown();
			Latch.idle();
		}
		
		//Latch
		if (Control.LB.getPressed())
			Latch.idle();
		if (Control.RB.getPressed()) {
			Latch.intake();
			Chute.trapdoorUp();
		}
			

		
		//Compressor
		if (Control.MENU.getPressed()) {
			if (Devices.Main.compressor.enabled())
				Devices.Main.compressor.stop();
			else
				Devices.Main.compressor.start();
		}
	}
}