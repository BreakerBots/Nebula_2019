package frc.team5104.vision;

import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.subsystem.drive.RobotDriveSignal.DriveUnit;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.BreakerPositionController;
import frc.team5104.util.Buffer;
import frc.team5104.util.CSV.CSVLoggable;
import frc.team5104.util.console;
import frc.team5104.vision.VisionSystems.limelight;

public class VisionMovement implements CSVLoggable {
	//Movement Controllers
	static BreakerPositionController turnController = new BreakerPositionController(
			_VisionConstants._turnP, 0, _VisionConstants._turnD, 
			_VisionConstants._toleranceX, _VisionConstants._targetX
		);
	static Buffer buffer = new Buffer(10, 45);
	//Is Finished
	static boolean isFinished() {
		boolean onTarget = Math.abs(_VisionConstants._targetY - VisionSystems.limelight.getY()) 
				<= _VisionConstants._toleranceY;
		console.log(Vision.targetVisible() + " " + onTarget);
		return Vision.targetVisible() && onTarget;
	}
	
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
		if(!isFinished()) {
			double x = VisionSystems.limelight.getX() * getScaleFactor();
			x += x < _VisionConstants._targetX ? _VisionConstants._leftOffset : _VisionConstants._rightOffset;
			return BreakerMath.clamp(
					turnController.update(x), -4, 4);
		}
		return 0;
	}
	
	//Forward Movement Function
	private static double getForward() {
//		if(!isFinished()) {
//			return BreakerMath.clamp(forwardController.update(VisionSystems.limelight.getY()), -9, 9);
//		}
		return (Math.abs(_VisionConstants._targetY - VisionSystems.limelight.getY()) 
				<= _VisionConstants._toleranceY) ? 0 : 2;
	}
	
	public static double getScaleFactor() {
		double d = Math.abs(_VisionConstants._targetY - limelight.getY()) > _VisionConstants._toleranceY ? 
				_VisionConstants._targetY - limelight.getY() : 0;
		if (d == 0) return 0;
		d = (2.0 / (d + 10)) + 0.8;
		buffer.update(d);
		
		return buffer.getDoubleAvg(); 
	}
	
	static void reset() {
		turnController.reset();
	}

	public String[] getHeader() {
		return new String[] { "turnCurrent", "turnTarget", "forwardCurrent", "forwardTarget", "distance" };
	}

	public String[] getData() {
		return new String[] { ""+VisionSystems.limelight.getX(), ""+turnController.target, 
							  ""+VisionSystems.limelight.getY(), ""+_VisionConstants._targetY,
							  ""+getScaleFactor()};
	}
}
