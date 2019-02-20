/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;

/**
 * Handles manual mode switching between auto/vision/teleop
 */
class StateController extends BreakerController {
	void update() {
		//Vision Toggling
//		if (_Controls.Main._toggleVision.getPressed()) {
//			if (BreakerRobotController.getMode() == RobotMode.Vision)
//				BreakerRobotController.setMode(RobotMode.Teleop);
//			else
//				Vision.runVision(RobotMode.Teleop, null, Chute.isDown() ? 
//						VisionMovement.VisionTarget.rocket : VisionMovement.VisionTarget.standard);
//		}
		
		//Auto Switching
		if (_Controls.Main._toggleAuto.getPressed()) {
			//...
		}
	}
}
