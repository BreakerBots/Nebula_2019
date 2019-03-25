/*BreakerBots Robotics Team 2019*/
package frc.team5104.superstructure.cargo;

import frc.team5104.control._Controls;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.arm.Arm;
import frc.team5104.subsystem.arm.ArmSystems;
import frc.team5104.subsystem.chute.Chute;
import frc.team5104.subsystem.chute.ChuteSystems;
import frc.team5104.subsystem.climber.Climber;
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
	public static BezierCurveInterpolator beltInterpolator = new BezierCurveInterpolator(0.05, new BezierCurve(0.4, 0.2, 0.0, 1));
	
	public void update() {
		if(Climber.isClimbing()) currentState = CargoState.idle;
		
		Chute.BeamAverage.update(ChuteSystems.BeamBreak.isHit());
		
		if (Arm.isManual()) {
			beltInterpolator.deltaTime = 0.05;
			
			if (currentState == CargoState.intake)
			//if(ArmSystems.Encoder.getDegrees() > _CargoConstants._intakeStartPos && !Chute.BeamAverage.getBooleanOutput()) 
				beltInterpolator.setSetpoint(_CargoConstants._intakeSpeed);
			else 
				beltInterpolator.setSetpoint(0);
			
			CargoSystems.Belt.set(beltInterpolator.update());
			
			if (Chute.BeamAverage.getBooleanOutput()) {
				_Controls.Cargo._storedRumble.start();
				Cargo.idle();
			}
			return;
		}
		
		switch (currentState) {
			//Eject Mode
			case eject:
				Arm.idle();
				
				beltInterpolator.setSetpoint(_CargoConstants._ejectSpeed);
				CargoSystems.Belt.set(beltInterpolator.update());
				
				if (System.currentTimeMillis() > ejectStart + 
						(Chute.isUp() ? _CargoConstants._ejectUpTime : _CargoConstants._ejectDownTime))
					Cargo.idle();
				
				break;
				
			//Intake Mode
			case intake:
				Arm.intake();
				
				beltInterpolator.deltaTime = 0.05;
				if(ArmSystems.Encoder.getDegrees() > _CargoConstants._intakeStartPos) 
					beltInterpolator.setSetpoint(_CargoConstants._intakeSpeed);
				else 
					beltInterpolator.setSetpoint(0);
 				CargoSystems.Belt.set(beltInterpolator.update());
				
				if (Chute.BeamAverage.getBooleanOutput()) {
					_Controls.Cargo._storedRumble.start();
					Cargo.idle();
				}
				
				break;
				
			//Idle Mode
			case idle:
				if(!Climber.isClimbing()) {
					Arm.idle();
					
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
