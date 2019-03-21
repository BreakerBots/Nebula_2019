/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.subsystem.drive.DriveSignal;
import frc.team5104.subsystem.drive.DriveSignal.DriveUnit;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.BreakerPID;
import frc.team5104.util.Buffer;
import frc.team5104.util.console;
import frc.team5104.vision.VisionSystems.limelight;
import frc.team5104.webapp.Tuner.tunerOutput;

public class VisionMovement {
	public static enum VisionTarget {
		rocket,
		standard
	}
	
	//Movement Controllers
	static BreakerPID turnController = new BreakerPID(
			_VisionConstants._turnP, 0, _VisionConstants._turnD, 
			_VisionConstants._toleranceX, _VisionConstants._targetX
		);
	static Buffer scaleFactorBuffer = new Buffer(10, 45);
	static Buffer turnBuffer = new Buffer(10, 0);
	static Buffer forwardBuffer = new Buffer(10, 0);
	
	//Is Finished
	public static double _targetY() {
		//return VisionManager.target == VisionTarget.rocket ? _VisionConstants._targetRocketY : 
		return _VisionConstants._targetStandardY;
	}
	private static double targetX() {
		return getScaleFactor()*getScaleFactor()*341.697 + getScaleFactor()*-762.138 + 433.147 + 7;
//		return _VisionConstants._targetX;
	}
	
	static boolean isFinished() {
		boolean onTarget = Math.abs(_targetY() - VisionSystems.limelight.getY()) 
				<= _VisionConstants._toleranceY;
		return Vision.targetVisible() && onTarget;
	}
	
	//Main Movement Function
	static DriveSignal getNextSignal() {
		return new DriveSignal(
				getForward() - getTurn(), 
				getForward() + getTurn(), 
				DriveUnit.voltage
			);
	}
	
	//Turn Movement Function
	private static double getTurn() {
		if(!isFinished()) {
			turnController._kP = _VisionConstants._turnP;
			turnController._kD = _VisionConstants._turnD;
			turnController.target = targetX();
			double x = VisionSystems.limelight.getX() * getScaleFactor();
			x += x < targetX() ? _VisionConstants._leftOffset : _VisionConstants._rightOffset;
			turnBuffer.update(
					BreakerMath.clamp(turnController.update(x), -4, 4)
				);
			return turnBuffer.getDoubleOutput();
		}
		return 0;
	}
	
	//Forward Movement Function
	private static double getForward() {
		double distance = -(_targetY() - VisionSystems.limelight.getY()) + _VisionConstants._forwardF;
		boolean on_target;
		if (Vision.targetVisible())
			on_target = distance <= _VisionConstants._toleranceY;
		else {
			distance = 0;
			on_target = false; 
		}
		forwardBuffer.update(on_target ? 0 : _VisionConstants._forwardP * distance);
		return forwardBuffer.getDoubleOutput();
	}
	
	public static double getScaleFactor() {
		double distance = Math.abs(_targetY() - limelight.getY()) > _VisionConstants._toleranceY ? 
				_targetY() - limelight.getY() : 0;
				
		//on target
		if (distance == 0) return 0;
		
		if (Vision.targetVisible())
			distance = (1.3 / (distance + 10)) + 0.77;
		else
			distance = 0;
		scaleFactorBuffer.update(distance);
		
		return scaleFactorBuffer.getDoubleOutput(); 
	}
	
	static void reset() {
		turnController.reset();
		scaleFactorBuffer = new Buffer(10, 45);
		turnBuffer = new Buffer(10, 0);
		forwardBuffer = new Buffer(10, 0);
	}

	@tunerOutput
	private static double turnCurrent() {
		return VisionSystems.limelight.getX();
	}
	@tunerOutput
	private static double turnTarget() {
		return turnController.target;
	}
	@tunerOutput
	private static double forwardCurrent() {
		return VisionSystems.limelight.getY();
	}
	@tunerOutput
	private static double forwardTarget() {
		return _targetY();
	}
	@tunerOutput
	private static double distance() {
		return getScaleFactor();
	}
}
