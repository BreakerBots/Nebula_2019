package frc.team5104.control;

import frc.team5104.main.BreakerRobotController;
import frc.team5104.main.HMI;
import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;

public class StateController {
	public static void handle() {
		//Vision Toggling
		if (HMI.Main._toggleVision.getPressed()) {
			BreakerRobotController.setMode(BreakerRobotController.getMode() == RobotMode.Vision ? RobotMode.Teleop : RobotMode.Vision);
			console.log(c.VISION, t.INFO, BreakerRobotController.getMode() == RobotMode.Vision ? "Switched to vision" : "Switched to drive");
		}
		
		//Auto Switching
		if (HMI.Main._toggleAuto.getPressed()) {
			//...
		}
	}
}
