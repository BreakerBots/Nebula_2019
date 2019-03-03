package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.subsystem.hatch.Hatch;

public class HatchController extends BreakerController {
	void update() {
		if (_Controls.Hatch._eject.getDoubleClick() == 1)
			Hatch.eject(false);
		else if (_Controls.Hatch._eject.getDoubleClick() == 2)
			Hatch.eject(true);
			//Vision.runVision(RobotMode.Teleop, ActionMode.hatchEject, VisionTarget.standard);
		if (_Controls.Hatch._intake.getPressed()) {
			Hatch.intake();
			Chute.trapdoorUp();
			//Vision.runVision(RobotMode.Teleop, ActionMode.hatchIntake, VisionTarget.standard);
		}
	}

	//Stop The Subsystem
	void idle() {
		Hatch.idle();
		Hatch.foldBack();
	}
}
