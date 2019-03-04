/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.arm;

import frc.team5104.control._Controls;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.climber.Climber;
import frc.team5104.subsystem.drive.DriveSystems;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.BreakerPositionController;
import frc.team5104.util.console;
import frc.team5104.webapp.Tuner.tunerOutput;

public class ArmManager extends BreakerSubsystem.Manager {

	public static enum ArmState {
		calibrating,
		idle,
		intakeDown,
		intakeHold,
		climbing
	}
	static ArmState currentState = ArmState.calibrating;
	
	static BreakerPositionController armController = new BreakerPositionController
			(_ArmConstants._armP, _ArmConstants._armI, _ArmConstants._armD, _ArmConstants._armTolerance);
	static BreakerPositionController climbController = new BreakerPositionController
			(_ArmConstants._climbP, _ArmConstants._climbI, _ArmConstants._climbD, _ArmConstants._climbTolerance, 0);
	public static BreakerPositionController climbInitController = new BreakerPositionController(
			_ArmConstants._climbInitP, _ArmConstants._climbInitI, 0, _ArmConstants._climbInitTolerance
			);
	private static double upPosAdd = 0;
	
	@tunerOutput
	public static double getCurrent() {
		return DriveSystems.gyro.getPitch();
	}
	@tunerOutput
	public static double lastOutput = 0;
	
	public void update() {
		climbController._kP = _ArmConstants._climbP;
		climbController._kI = _ArmConstants._climbI;
		climbController._kD = _ArmConstants._climbD;
		climbController.tolerance = _ArmConstants._climbTolerance;
		
		if (Climber.isClimbing())
			currentState = ArmState.climbing;
		
		if (ArmSystems.LimitSwitch.isHit()) {
			ArmSystems.Encoder.zero();
			upPosAdd = 0;
		}
		
		if (_Controls.Cargo._manualArm)
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
				switch (Climber.getStage()) {
					//Stage 0 (Move the arm to a level touching L3 but not yet lifting)
					case initial:
						climbInitController.setTarget(_ArmConstants._climbInitDegrees);
						if(climbInitController.onTarget() == false) 
							ArmSystems.applyForce(BreakerMath.clamp(climbInitController.update(ArmSystems.Encoder.getDegrees()), 0, _ArmConstants._climbInitMax));
						else 
							ArmSystems.applyForce(0);
						break;
					//Stage 1/2 (Move arm to level out the robot using PID referencing the pitch of the gyro)
					default:
						double f = climbController.update(-DriveSystems.gyro.getPitch());
						lastOutput = f;
						climbController.setTarget(0);
						if(climbController.onTarget() == false) 
							ArmSystems.applyForce(f);
						else
							ArmSystems.applyForce(0);
						break;
				} 
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
		climbController.reset();
		climbInitController.reset();
		currentState = ArmState.calibrating;
	}
	public void disabled() { }
}
