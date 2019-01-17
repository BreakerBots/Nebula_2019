/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import frc.team5104.util.console;

public class VisionActions {
	// Return what to add to the right wheels based on offset
	public static double getRightOutput() {
		double offSet = VisionSystems.limelight.getX() < _VisionConstants._xTargetCoordinate - _VisionConstants._minXOffset ? 
				VisionSystems.limelight.getX() : 0;
		double p = _VisionConstants._px * _VisionConstants._maxXOffset;
		return -offSet/p;
	}
	
	// Return what to add to the left wheels based on offset
	public static double getLeftOutput() {
		double offSet = VisionSystems.limelight.getX() > _VisionConstants._xTargetCoordinate + _VisionConstants._minXOffset ? 
				VisionSystems.limelight.getX() : 0;
		double p = _VisionConstants._px * _VisionConstants._maxXOffset;
		return offSet/p;
	}
	
	// Return forward values based on offset
	public static double getForward() {
		double offSet = VisionSystems.limelight.getY() < _VisionConstants._yTargetCoordinate - _VisionConstants._minYOffset ? 
				_VisionConstants._yTargetCoordinate - VisionSystems.limelight.getY() : 0;
		double p = _VisionConstants._py * _VisionConstants._maxYOffset;
		return offSet/p * _VisionConstants._maxSpeed;
	}
	
	// Change the pipeline you are using
	public static void changePipeline(VisionManager.VisionPipeline p) {
		console.log(VisionSystems.pipeline.change());
	}
}
