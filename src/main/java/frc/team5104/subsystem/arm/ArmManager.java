/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.arm;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.climber.Climber;
import frc.team5104.subsystem.drive.DriveSystems;
import frc.team5104.util.BreakerPositionController;

public class ArmManager extends BreakerSubsystem.Manager {

	public static enum ArmState {
		idle,
		intakeDown,
		intakeHold,
		climbing
	}
	static ArmState currentState = ArmState.idle;
	
	public static BreakerPositionController armController = new BreakerPositionController
			(_ArmConstants._armP, _ArmConstants._armI, _ArmConstants._armD, _ArmConstants._armTolerance);
	static BreakerPositionController climbController = new BreakerPositionController
			(_ArmConstants._climbP, _ArmConstants._climbI, _ArmConstants._climbD, _ArmConstants._climbTolerance, 0);
	
	public void update() {
		if (Climber.isClimbing())
			currentState = ArmState.climbing;
		
		switch (currentState) {
			//Idle
			case idle:
				armController.setTarget(_ArmConstants._upPos);
				if(!armController.onTarget()) 
					ArmSystems.applyForce(armController.update(ArmSystems.Encoder.getDegrees()));
				 else
					ArmSystems.applyForce(0);
				break;
				
			//Intake Down
			case intakeDown:
				armController.setTarget(_ArmConstants._downPos);
				if(armController.onTarget() == false)
					ArmSystems.applyForce(armController.update(ArmSystems.Encoder.getDegrees()));
				else
					currentState = ArmState.intakeHold;
				break;
			//Intake Hold
			case intakeHold:
				double error = _ArmConstants._downPos - ArmSystems.Encoder.getDegrees();
				
				//Above Target
				if (ArmSystems.Encoder.getDegrees() < _ArmConstants._downPos) {
					ArmSystems.applyForce(_ArmConstants._intakeHoldDownP * error);
				}
				//Below Target
				else {
					ArmSystems.applyForce(_ArmConstants._intakeHoldUpP * error);
				}
				break;
				
			//Climbing
			case climbing:
				switch (Climber.getStage()) {
					//Stage 0 (Move the arm to a level touching L3 but not yet lifting)
					case initial:
						armController.setTarget(_ArmConstants._stage0Target);
						if(!armController.onTarget()) 
							ArmSystems.applyForce(armController.update(ArmSystems.Encoder.getDegrees()));
						else 
							ArmSystems.applyForce(0);
						break;
					//Stage 1/2 (Move arm to level out the robot using PID referencing the pitch of the gyro)
					default:
						if(climbController.onTarget() == false) 
							ArmSystems.applyForce(climbController.update(DriveSystems.gyro.getPitch()));
						else
							ArmSystems.applyForce(0);
						break;
				} 
				break;
		}
	}
	
	public void enabled() {
		armController.reset();
		climbController.reset();
	}
	public void disabled() { }
}
