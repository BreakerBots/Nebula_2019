/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;
import frc.team5104.main.RobotState;
import frc.team5104.main.RobotState.RobotMode;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.vision.Vision;
import frc.team5104.vision.VisionMovement.VisionTarget;

/**
 * Handles manual mode switching between auto/vision/teleop
 */
class StateController extends BreakerController {
	void update() {
		//Vision Toggling
		if (_Controls.Main._toggleVision.getPressed()) {
			if (RobotState.getMode() == RobotMode.Vision)
				RobotState.setMode(RobotMode.Teleop);
			else
				Vision.runVision(RobotMode.Teleop, null, Chute.isDown() ? 
						VisionTarget.rocket : VisionTarget.standard);
		}
		
		//Auto Switching
		if (_Controls.Main._toggleAuto.getPressed()) {
			//...
		}
	}
}
