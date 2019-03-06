/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.arm;

import frc.team5104.control._Controls;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.climber.Climber;
import frc.team5104.subsystem.climber.ClimberManager.ClimberStage;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.BreakerPID;
import frc.team5104.util.console;

public class ArmManager extends BreakerSubsystem.Manager {

	public static enum ArmState {
		calibrating,
		idle,
		intakeDown,
		intakeHold,
		climbing
	}
	static ArmState currentState = ArmState.calibrating;
	
	static BreakerPID armController = new BreakerPID
			(_ArmConstants._armP, _ArmConstants._armI, _ArmConstants._armD, _ArmConstants._armTolerance);
	private static double upPosAdd = 0;
	public static BreakerPID climbInitController = new BreakerPID(
			_ArmConstants._climbInitP, _ArmConstants._climbInitI, 0, _ArmConstants._climbInitTolerance
			);
	
	public void update() {
		if (Climber.isClimbing())
			currentState = ArmState.climbing;
		
		if (ArmSystems.LimitSwitch.isHit()) {
			ArmSystems.Encoder.zero();
			upPosAdd = 0;
		}
		
		if (Arm.isManual())
			return;
		
		switch (currentState) {
			//Idle
			case idle:
				armController.setTarget(_ArmConstants._upPos);
				double force = armController.update(ArmSystems.Encoder.getDegrees());
				
				if (ArmSystems.LimitSwitch.isHit() == false && force < 1) {
					upPosAdd -= 0.5;
					armController.setTarget(_ArmConstants._upPos + upPosAdd);
					force = armController.update(ArmSystems.Encoder.getDegrees());
				}
				
				if(armController.onTarget() == false) 
					ArmSystems.applyForce(force);
				else {
					ArmSystems.applyForce(0);
				}
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
				//Stage 0 (Move the arm to a level touching L3 but not yet lifting)
				climbInitController.setTarget(_ArmConstants._climbInitDegrees);
				if(climbInitController.onTarget() == false) 
					ArmSystems.applyForce(BreakerMath.clamp(climbInitController.update(ArmSystems.Encoder.getDegrees()), 0, _ArmConstants._climbInitMax));
				else 
					ArmSystems.applyForce(0);
				break;
			case calibrating:
				if (ArmSystems.LimitSwitch.isHit()) {
					currentState = ArmState.idle;
					ArmSystems.Encoder.zero();
				}
				else {
					ArmSystems.applyForce(-4);
				}
		}
	}
	
	
	public void enabled() {
		armController.reset();
		climbInitController.reset();
		currentState = ArmState.calibrating;
	}
	public void disabled() { }
}
