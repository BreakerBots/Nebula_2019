package frc.team5104.vision;

import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.subsystem.drive.RobotDriveSignal.DriveUnit;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.BreakerPositionController;

class VisionMovement {
	//Movement Controlers
	static BreakerPositionController turnController = new BreakerPositionController(
				_VisionConstants._turnP, _VisionConstants._turnI, _VisionConstants._turnD, 
				_VisionConstants._minXOffset, _VisionConstants._xTargetCoordinate
			);
	static BreakerPositionController forwardController = new BreakerPositionController(
				_VisionConstants._forwardP, _VisionConstants._forwardI, _VisionConstants._forwardD, 
				_VisionConstants._minYOffset, _VisionConstants._yTargetCoordinate
			);
	
	//Main Movement Function
	static RobotDriveSignal getNextSignal() {
		return new RobotDriveSignal(
				getForward() - getTurn(), 
				getForward() + getTurn(), 
				DriveUnit.voltage
			);
	}
	
	//Turn Movement Function
	private static double getTurn() {
		if(Vision.targetVisible() && !turnController.onTarget()) {
			return BreakerMath.clamp(turnController.update(VisionSystems.limelight.getX()), -1, 1);
		}
		return 0;
	}
	
	//Forward Movement Function
	private static double getForward() {
		if(Vision.targetVisible() && !forwardController.onTarget()) {
			return BreakerMath.clamp(forwardController.update(VisionSystems.limelight.getY()), -1, 1);
		}
		return 0;
	}
	
	static void reset() {
		turnController.reset();
	}
}
