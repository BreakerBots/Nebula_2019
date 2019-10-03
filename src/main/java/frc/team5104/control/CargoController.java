package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;
import frc.team5104.subsystem.arm.Arm;
import frc.team5104.subsystem.arm.ArmSystems;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.superstructure.cargo.Cargo;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

class CargoController extends BreakerController {
	void update() {
		//Main Controls
		if (_Controls.Main._idle.getPressed()) 
			Cargo.idle();
		if (_Controls.Main._idle.getDoubleClick() == 2) 
			_Controls.Cargo._manualArm = !_Controls.Cargo._manualArm;
		
		if (_Controls.Cargo._intake.getPressed()) 
			Cargo.intake();
		if (_Controls.Cargo._eject.getPressed()) {
			Cargo.eject();
			_Controls.Cargo._ejectRumble.start();
		}
		
		//Manual Arm
		if (Arm.isManual()) {
			double force = _Controls.Cargo._armManual.getAxis() * 10;
//			console.log(force);
			ArmSystems.applyForce(force);
		}
		else if (Arm.isIntaking()) {
			double change = _Controls.Cargo._armManual.getAxis() * 1.0;
			Arm.changeDownPosition(change);
		}
		
		//Trapdoor
		if (_Controls.Cargo._trapdoorUp.getPressed()) {
			Chute.trapdoorUp();
			_Controls.Cargo._trapdoorUpRumble.start();
		}
		if (_Controls.Cargo._trapdoorDown.getPressed()) {
			Chute.trapdoorDown();
			_Controls.Cargo._trapdoorDownRumble.start();
		}
	}

	//Stop The Subsystem
	void idle() {
		Cargo.idle();
		Chute.trapdoorUp();
	}
}
