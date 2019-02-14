package frc.team5104.control;

import frc.team5104.main.BreakerRobotController;
import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;
import frc.team5104.vision.Vision;

public class StateController {
	public static void handle() {
		//Vision Toggling
		if (Controls.Main._toggleVision.getPressed()) {
			if (BreakerRobotController.getMode() == RobotMode.Vision)
				BreakerRobotController.setMode(RobotMode.Teleop);
			else
				Vision.runVision(RobotMode.Teleop);
		}
		
		//Auto Switching
		if (Controls.Main._toggleAuto.getPressed()) {
			//...
		}
	}
}
