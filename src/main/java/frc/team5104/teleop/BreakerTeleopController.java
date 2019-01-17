package frc.team5104.teleop;

import frc.team5104.util.controller;

public class BreakerTeleopController {
	public static void update() {
		//Drive
		Drive.handle();
		
		//Updates
		controller.update();
	}
}
