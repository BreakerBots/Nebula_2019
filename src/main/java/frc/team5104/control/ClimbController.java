package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;
import frc.team5104.subsystem.climber.Climber;

public class ClimbController extends BreakerController {
	void update() {
		if (_Controls.Climb._manualClimb == false) {
			if (_Controls.Climb._climb.getPressed()) {
				if(Climber.isClimbing()) 
					Climber.endClimb();
				else 
					Climber.climb();
			}
		}
		else {
			if (_Controls.Climb._stage1.getPressed()) {
				
			}
			if (_Controls.Climb._stage2.getPressed()) {
				
			}
		}
	}

}
