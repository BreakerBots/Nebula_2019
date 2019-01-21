package frc.team5104.teleop;

import frc.team5104.main.Devices;
import frc.team5104.util.controller.Control;

public class BreakerTeleopController {
	public static void update() {
		//Drive
		Drive.handle();
		
		if (Control.MENU.getPressed()) {
			if (Devices.Main.compressor.enabled())
				Devices.Main.compressor.stop();
			else
				Devices.Main.compressor.start();
		}
	}
}
