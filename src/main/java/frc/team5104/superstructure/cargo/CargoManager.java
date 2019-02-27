/*BreakerBots Robotics Team 2019*/
package frc.team5104.superstructure.cargo;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.subsystem.chute.ChuteSystems;
import frc.team5104.subsystem.climber.Climber;
import frc.team5104.subsystem.intake.Intake;
import frc.team5104.util.BezierCurve;
import frc.team5104.util.BezierCurveInterpolator;
import frc.team5104.util.Buffer;

public class CargoManager extends BreakerSubsystem.Manager {
	public static enum CargoState {
		idle,
		intake,
		eject
	}
	public static CargoState currentState = CargoState.idle;
	static long ejectStart = System.currentTimeMillis();
	BezierCurveInterpolator beltInterpolator = new BezierCurveInterpolator(0.05, new BezierCurve(0.4, 0.2, 0.0, 1));
	
	public void update() {
		if(Climber.isClimbing()) currentState = CargoState.idle;
		
		Chute.BeamAverage.update(ChuteSystems.BeamBreak.isHit());
				
		switch (currentState) {
			//Eject Mode
			case eject:
				Intake.up();
				
				beltInterpolator.setSetpoint(_CargoConstants._ejectSpeed);
				CargoSystems.Belt.set(beltInterpolator.update());
				
				if (System.currentTimeMillis() > ejectStart + 
						(Chute.isUp() ? _CargoConstants._ejectUpTime : _CargoConstants._ejectDownTime))
					Cargo.idle();
				
				break;
				
			//Intake Mode
			case intake:
				Intake.down();
				
				beltInterpolator.deltaTime = 0.05;
				beltInterpolator.setSetpoint(_CargoConstants._intakeSpeed);
				CargoSystems.Belt.set(beltInterpolator.update());
				
				if (Chute.BeamAverage.getBooleanOutput())
					Cargo.idle();
				
				break;
				
			//Idle Mode
			case idle:
				if(!Climber.isClimbing()) {
					Intake.up();
					
					beltInterpolator.deltaTime = 0.25;
					beltInterpolator.setSetpoint(0);
					CargoSystems.Belt.set(beltInterpolator.update());
				}
				
				break;
		}
	}

	public void disabled() { }
	public void enabled() { 
		Chute.BeamAverage = new Buffer(3, false);
	}
	public CargoManager() { 
		//IntakeSystems.csv.writeFile("vision_temp", "urmom.txt"); }
	}
}
