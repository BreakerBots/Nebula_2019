/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

public class VisionActions {
	// Return what to add to the right wheels based on offset
	public static double getRightOutput() {
		if(VisionSystems.limelight.getA() != 0) {
			double offSet = VisionSystems.limelight.getX() < 
					_VisionConstants._xTargetCoordinate - _VisionConstants._minXOffset ? 
					VisionSystems.limelight.getX() : 0;
			double p = _VisionConstants._px * _VisionConstants._maxXOffset;
			return -offSet/p;
		}
		return 0;
	}
	
	// Return what to add to the left wheels based on offset
	public static double getLeftOutput() {
		if(VisionSystems.limelight.getA() != 0) {
			double offSet = VisionSystems.limelight.getX() > 
			_VisionConstants._xTargetCoordinate + _VisionConstants._minXOffset ? 
					VisionSystems.limelight.getX() : 0;
			double p = _VisionConstants._px * _VisionConstants._maxXOffset;
			return offSet/p;
		}
		return 0;
	}
	
	// Return forward values based on offset
	public static double getForward() {
		if(VisionSystems.limelight.getA() != 0) {
			double offSet = VisionSystems.limelight.getY() < 
					_VisionConstants._yTargetCoordinate - _VisionConstants._minYOffset ? 
					_VisionConstants._yTargetCoordinate - VisionSystems.limelight.getY() : 0;
			double p = _VisionConstants._py * _VisionConstants._maxYOffset;
			return offSet/p * _VisionConstants._maxSpeed;
		}
		return 0;
	}
	
	// Change the pipeline you are using
	public static void changePipeline(VisionManager.VisionPipeline p) {
		VisionSystems.pipeline.set(p);
	}
}
