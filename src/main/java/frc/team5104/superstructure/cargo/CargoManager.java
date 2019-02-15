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
	
	public void update() {
		switch (currentState) {
			//Eject Mode
			case eject:
				Intake.up();
				
				CargoSystems.Belt.set(_CargoConstants._ejectSpeed);
				
				if (System.currentTimeMillis() > ejectStart + _CargoConstants._ejectTime)
					Cargo.idle();
				break;
				
			//Intake Mode
			case intake:
				Intake.down();
				
				CargoSystems.Belt.set(_CargoConstants._intakeSpeed);
				
				if (ChuteSystems.BeamBreak.isHit())
					Cargo.idle();
				break;
				
			//Idle Mode
			case idle:
				Intake.up();
				
				Chute.trapdoorUp();
				
				break;
		}
	}

	public void disabled() { }
	public void enabled(RobotMode mode) {  }
	public CargoManager() { 
		//IntakeSystems.csv.writeFile("vision_temp", "urmom.txt"); }
	}
}
