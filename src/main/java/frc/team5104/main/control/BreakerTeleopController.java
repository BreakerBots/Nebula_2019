package frc.team5104.main.control;

import frc.team5104.main.Devices;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.subsystem.latch.Latch;
import frc.team5104.superstructure.cargo.Cargo;
import frc.team5104.util.controller.Control;

public class BreakerTeleopController {
	public static void update() {
		//Drive
		DriveController.handle();
		
		//Cargo
		if (Control.X.getPressed())
			Cargo.intake();
		if (Control.B.getPressed())
			Cargo.eject();
		if (Control.N.getPressed())
			Chute.rampUp();
		if (Control.S.getPressed())
			Chute.rampDown();
		
		//Hatch
		if (Control.RB.getPressed())
			Latch.hold();
		if (Control.LB.getPressed())
			Latch.intake();
			
		//Unjam
		if (Control.LIST.getPressed()) {
			Cargo.idle();
			Latch.idle();
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
