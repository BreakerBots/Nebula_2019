/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.arm;

import frc.team5104.control._Controls;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.climber.Climber;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.BreakerPID;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.webapp.Tuner.tunerOutput;

public class ArmManager extends BreakerSubsystem.Manager {

	public static enum ArmState {
		calibrating,
		idle,
		intakeDown,
		intakeHold,
		climbing, 
		manual
	}
	static ArmState currentState = ArmState.calibrating;
	
	static BreakerPID downController = new BreakerPID
			(_ArmConstants._downP, 0, 0, _ArmConstants._downTolerance);
	static BreakerPID upController = new BreakerPID
			(_ArmConstants._upP, 0, 0, _ArmConstants._upTolerance);
	private static double upPosAdj = 0;
	public static BreakerPID climbInitController = new BreakerPID(
			_ArmConstants._climbInitP, _ArmConstants._climbInitI, 0, _ArmConstants._climbInitTolerance
			);
	
	@tunerOutput
	public static double getLeftCurrentDraw() {
		return ArmSystems.getLeftCurrentDraw();
	}
	@tunerOutput
	public static double getRightCurrentDraw() {
		return ArmSystems.getRightCurrentDraw();
	}
//	@tunerOutput
//	public static double getTarget() {
//		return _ArmConstants._upPos + upPosAdj;
//	}
//	@tunerOutput
//	public static double getCurrent() {
//		return ArmSystems.Encoder.getDegrees();
//	}
	
	public void update() {
		upController._kP = _ArmConstants._upP;
		downController._kP = _ArmConstants._downP;
		
		if (Climber.isClimbing())
			currentState = ArmState.climbing;
		
		if (ArmSystems.LimitSwitch.isHit()) {
			ArmSystems.Encoder.zero();
			upPosAdj = 0;
		}
		
		if (ArmSystems.getLeftCurrentDraw() > 26 || ArmSystems.getRightCurrentDraw() > 26) {
			console.log(c.CARGO, "Hit Amp Max, Setting to Manual");
			_Controls.Cargo._manualArm = true;
		}
		
		if (Arm.isManual()) {
			currentState = ArmState.manual;
			return;
		}
		
		switch (currentState) {
			//Idle
			case idle:
				upController.setTarget(_ArmConstants._upPos + upPosAdj);
				double force = upController.update(ArmSystems.Encoder.getDegrees());
				
				if (ArmSystems.LimitSwitch.isHit() == false && force < 1) {
					upPosAdj -= 0.8;
					upController.setTarget(_ArmConstants._upPos + upPosAdj);
					force = upController.update(ArmSystems.Encoder.getDegrees());
				}
				
				if(upController.onTarget() == false) 
					ArmSystems.applyForce(force);
				else {
					ArmSystems.applyForce(0);
				}
				break;
				
			//Intake Down
			case intakeDown:
				downController.setTarget(_ArmConstants._downPos);
				if(downController.onTarget() == false)
					ArmSystems.applyForce(downController.update(ArmSystems.Encoder.getDegrees()));
				else {
					currentState = ArmState.intakeHold;
					_Controls.Cargo._intakeRumble.start();
				}
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
				
			// Manual
			case manual:
				break;
				
			// Calibrating
			case calibrating:
				if (ArmSystems.LimitSwitch.isHit()) {
					currentState = ArmState.idle;
					ArmSystems.Encoder.zero();
				}
				else {
					ArmSystems.applyForce(-_ArmConstants._calibrateSpeed);
				}
		}
	}
	
	
	public void enabled() {
		upController.reset();
		downController.reset();
		climbInitController.reset();
		//_Controls.Cargo._manualArm
		currentState = ArmState.calibrating;
	}
	public void disabled() { }
}
