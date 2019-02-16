package frc.team5104.vision;

import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.subsystem.drive.RobotDriveSignal.DriveUnit;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.BreakerPositionController;
import frc.team5104.util.console;

class VisionMovement {
	//Movement Controllers
	static BreakerPositionController turnController = new BreakerPositionController(
			_VisionConstants._turnP, _VisionConstants._turnI, _VisionConstants._turnD, 
			_VisionConstants._toleranceX, _VisionConstants._targetX
		);
	static BreakerPositionController forwardController = new BreakerPositionController(
			_VisionConstants._forwardP, _VisionConstants._forwardI, _VisionConstants._forwardD, 
			_VisionConstants._toleranceY, _VisionConstants._targetY
		);
	
	//Is Finished
	static boolean isFinished() {
		return Vision.targetVisible() && turnController.onTarget() && forwardController.onTarget();
	}
	
	//Main Movement Function
	static RobotDriveSignal getNextSignal() {
		console.log(getTurn());
		return new RobotDriveSignal(
				getForward() - getTurn(), 
				getForward() + getTurn(), 
				DriveUnit.voltage
			);
	}
	
	//Turn Movement Function
	private static double getTurn() {
		VisionManager.csv.update(new String[] { ""+VisionSystems.limelight.getX(), ""+turnController.target });
		if(!isFinished()) {
			return BreakerMath.clamp(turnController.update(VisionSystems.limelight.getX()), -9, 9);
		}
		return 0;
	}
	
	//Forward Movement Function
	private static double getForward() {
		if(!isFinished()) {
			return BreakerMath.clamp(forwardController.update(VisionSystems.limelight.getY()), -9, 9);
		}
		return 0;
	}
	
	static void reset() {
		turnController.reset();
	}
}
