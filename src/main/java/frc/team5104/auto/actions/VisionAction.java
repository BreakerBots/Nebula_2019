/*BreakerBots Robotics Team 2019*/
package frc.team5104.auto.actions;

import frc.team5104.auto.BreakerPathAction;
import frc.team5104.main.BreakerRobotController;
import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.vision.Vision;
import frc.team5104.vision.VisionMovement;

public class VisionAction extends BreakerPathAction {
    public VisionAction() { }

    public void init() {
    	Vision.runVision(RobotMode.Auto, null, VisionMovement.VisionTarget.standard);
    }

    public boolean update() {
    	return BreakerRobotController.getMode() == RobotMode.Auto;
    }

    public void end() {
    	
    }
}
