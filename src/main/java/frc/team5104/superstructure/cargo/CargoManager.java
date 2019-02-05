/*BreakerBots Robotics Team 2019*/
package frc.team5104.superstructure.cargo;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.subsystem.chute.ChuteSystems;
import frc.team5104.subsystem.intake.Intake;

class CargoManager extends BreakerSubsystem.Manager {
	public static enum CargoState {
		idle,
		intake,
		eject
	}
	static CargoState currentState = CargoState.idle;
	static long ejectStart = System.currentTimeMillis();
	
	public void enabled(RobotMode mode) {
		
	}
	
	public void update() {
		switch (currentState) {
			case eject:
				Intake.idle();
				Chute.eject();
//				CargoSystems.Motor.set(1);
				
				if (System.currentTimeMillis() > ejectStart + 1000)
					Cargo.idle();
				break;
			case idle:
				Intake.idle();
				Chute.idle();
				break;
			case intake:
				Intake.intake();
				Chute.intake();
//				CargoSystems.Motor.set(1);
				
				if (ChuteSystems.BeamBreak.isHit())
					Cargo.idle();
				break;
			default:
				Intake.idle();
				Chute.idle();
				break;
		}
	}

	public void disabled() {
		
	}
	
	public CargoManager() {

	}
}
