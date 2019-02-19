package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.subsystem.intake.IntakeSystems;
import frc.team5104.superstructure.cargo.Cargo;

class CargoController extends BreakerController {
	static void update() {
		//Main Controls
		if (_Controls.Main._idle.getPressed()) 
			Cargo.idle();
		if (_Controls.Cargo._intake.getPressed()) 
			Cargo.intake();
		if (_Controls.Cargo._eject.getPressed())
			//Vision.runVision(RobotMode.Teleop, ActionMode.cargo, Chute.isDown() ? VisionTarget.rocket : VisionTarget.standard);
			Cargo.eject();
		
		//Manual Arm
		double _armVoltage = _Controls.Cargo._armManual.getAxis() * 10;
		if(IntakeSystems.LimitSwitch.isHit() && _armVoltage < 0) _armVoltage = 0;
		IntakeSystems.Arm.setVoltage(_armVoltage);
		
		//Trapdoor
		if (_Controls.Cargo._trapdoorUp.getPressed())
			Chute.trapdoorUp();
		if (_Controls.Cargo._trapdoorDown.getPressed()) {
			Chute.trapdoorDown();
		}
	}

	//Stop The Subsystem
	static void idle() {
		Cargo.idle();
		Chute.trapdoorUp();
	}
}
