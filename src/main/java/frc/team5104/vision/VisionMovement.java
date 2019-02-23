/*BreakerBots Robotics Team 2019*/
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
	public static enum VisionTarget {
		rocket,
		standard
	}
	
	//Movement Controllers
	static BreakerPositionController turnController = new BreakerPositionController(
			_VisionConstants._turnP, 0, _VisionConstants._turnD, 
			_VisionConstants._toleranceX, _VisionConstants._targetX
		);
	static Buffer buffer = new Buffer(10, 45);
	
	//Is Finished
	public static double _targetY() {
		return VisionManager.target == VisionTarget.rocket ? _VisionConstants._targetRocketY : 
			_VisionConstants._targetStandardY;
	}

	
	static boolean isFinished() {
		boolean onTarget = Math.abs(_targetY() - VisionSystems.limelight.getY()) 
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
		return (Math.abs(_targetY() - VisionSystems.limelight.getY()) 
				<= _VisionConstants._toleranceY) ? 0 : 2;
	}
	
	public static double getScaleFactor() {
		double d = Math.abs(_targetY() - limelight.getY()) > _VisionConstants._toleranceY ? 
				_targetY() - limelight.getY() : 0;
		if (d == 0) return 0;
		d = (1.3 / (d + 10)) + 0.77;
		buffer.update(d);
		
		return buffer.getDoubleOutput(); 
	}
	
	static void reset() {
		turnController.reset();
	}

	public String[] getCSVHeader() { 
		return new String[] { "turnCurrent", "turnTarget", "forwardCurrent", "forwardTarget", "distance" };
	}
	public String[] getCSVData() {
		return new String[] { ""+VisionSystems.limelight.getX(), ""+turnController.target, 
							  ""+VisionSystems.limelight.getY(), ""+_targetY(),
							  ""+getScaleFactor()};
	}
	public String getCSVName() { return "VisionPID"; }
}
