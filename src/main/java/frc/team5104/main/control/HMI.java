/*BreakerBots Robotics Team 2019*/
package frc.team5104.main.control;

import frc.team5104.util.Curve;
import frc.team5104.util.controller.Control;

/**
 * The controls for the robot.
 */
public class HMI {
	//Main 
	public static class Main {
		public static final Control _toggleAuto = Control.MENU;
		public static final Control _idle = Control.LIST;
		// public static final Control _toggleCompressor =
		// public static final Control _toggleVision = 
	}
	
	//Drive
	public static class Drive {
		public static final Control _turn = Control.LX;
		public static final Control _forward = Control.RT;
		public static final Control _reverse = Control.LT;
		public static final Control _shift = Control.LJ;
		public static final Curve.BezierCurve _driveCurve = new Curve.BezierCurve(.2, 0, .2, 1);
		public static final double _driveCurveChange = 1.0;
		public static final double _turnCurveSpeedAdjust = 0.5;
		// public static final double _switchDrive = 
	}
	
	//Hatch
	public static class Hatch {
		public static final Control _intake = Control.LB;
		public static final Control _eject = Control.RB;
	}
	
	//Cargo
	public static class Cargo {
		public static final Control _intake = Control.X;
		public static final Control _eject = Control.B;
		public static final Control _up = Control.Y;
		public static final Control _down = Control.A;
	}
	
	//Climb
	public static class Climb {
		
	}
}