package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;
import frc.team5104.subsystem.arm.ArmSystems;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.superstructure.cargo.Cargo;
import frc.team5104.util.console;

class CargoController extends BreakerController {
	void update() {
		//Main Controls
		if (_Controls.Main._idle.getPressed()) 
			Cargo.idle();
		if (_Controls.Cargo._intake.getPressed()) 
			Cargo.intake();
		if (_Controls.Cargo._eject.getPressed())
			//Vision.runVision(RobotMode.Teleop, ActionMode.cargo, Chute.isDown() ? VisionTarget.rocket : VisionTarget.standard);
			Cargo.eject();
		
		//Manual Arm
		if (_Controls.Cargo._manualArm) {
			double _armVoltage = -_Controls.Cargo._armManual.getAxis() * 10;
			if (ArmSystems.LimitSwitch.isHit() && _armVoltage < 0) _armVoltage = 0;
			ArmSystems.setVoltage(_armVoltage);
		}
		
		//Trapdoor
		if (_Controls.Cargo._trapdoorUp.getPressed())
			Chute.trapdoorUp();
		if (_Controls.Cargo._trapdoorDown.getPressed()) {
			Chute.trapdoorDown();
		}
	}

	//Stop The Subsystem
	void idle() {
		Cargo.idle();
		Chute.trapdoorUp();
	}
}
