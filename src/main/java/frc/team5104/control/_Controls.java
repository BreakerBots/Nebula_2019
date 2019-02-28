/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.util.Controller.Control;
import frc.team5104.util.Controller.ControlList;
import frc.team5104.util.Controller.Rumble;

/**
 * All the controls for the robot
 */
public class _Controls {
	//Main 
	static class Main {
		public static final Control _toggleVision = new Control(ControlList.DirectionPadUp);
		public static final Control _toggleAuto = new Control(ControlList.Menu);
		
		public static final Control _idle = new Control(ControlList.List);
		
		public static boolean _manualCompressor = true;
		public static final Control _toggleCompressor = new Control(ControlList.DirectionPadLeft);
	}
	
	//Drive
	public static class Drive {
		public static final Control _turn = new Control(ControlList.LeftJoystickX);
		public static final Control _forward = new Control(ControlList.RightTrigger);
		public static final Control _reverse = new Control(ControlList.LeftTrigger);
		
		public static final Control _shift = new Control(ControlList.LeftJoystickPress);
		public static final Rumble _shiftRumbleLow = new Rumble(0.25, false, 200);
		public static final Rumble _shiftRumbleHigh = new Rumble(0.75, false, 200);
		//public static final double _switchDrive = ;
	}
	
	//Hatch
	static class Hatch {
		public static final Control _intake = new Control(ControlList.LeftBumper);
		public static final Control _eject = new Control(ControlList.RightBumper);
	}
	
	//Cargo
	static class Cargo {
		public static final Control _intake = new Control(ControlList.X);
		public static final Control _eject = new Control(ControlList.B);
		public static final Control _trapdoorUp = new Control(ControlList.Y);
		public static final Control _trapdoorDown = new Control(ControlList.A);
		
		public static boolean _manualArm = true;
		public static final Control _armManual = new Control(ControlList.RightJoystickY);
	}
	
	//Climb
	static class Climb {
		public static final Control _climb = new Control(ControlList.DirectionPadRight);
		
		public static boolean _manualClimb = true;
		public static final Control _stage1 = new Control(ControlList.DirectionPadRight);
		public static final Control _stage2 = new Control(ControlList.DirectionPadDown);
	}
}
