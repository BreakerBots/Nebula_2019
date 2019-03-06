package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;
import frc.team5104.subsystem.climber.Climber;

public class ClimbController extends BreakerController {
	void update() {
		if (_Controls.Climb._climb.getDoubleClick() == 2) {
			if(Climber.isClimbing()) 
				Climber.endClimb();
			else 
				Climber.climb();
		}
	}

}
