/*BreakerBots Robotics Team 2019*/
package frc.team5104.control;

import frc.team5104.util.Controller.Control;
import frc.team5104.util.Controller.ControlList;
import frc.team5104.util.Controller.Controllers;
import frc.team5104.util.Controller.Rumble;

/**
 * All the controls for the robot
 */
public class Controls {
	//Main 
	public static class Main {
		public static final Control _toggleVision = new Control(ControlList.DirectionPadDown, Controllers.Main);
		public static final Control _toggleAuto = new Control(ControlList.Menu, Controllers.Main);
		
		public static final Control _idle = new Control(ControlList.List, Controllers.Main);

		public static final Control _toggleCompressor = new Control(ControlList.DirectionPadLeft , Controllers.Main);
		public static final boolean _automaticCompressor = false;
	}
	
	//Drive
	public static class Drive {
		public static final Control _turn = new Control(ControlList.LeftJoystickX, Controllers.Main);
		public static final Control _forward = new Control(ControlList.RightTrigger, Controllers.Main);
		public static final Control _reverse = new Control(ControlList.LeftTrigger, Controllers.Main);
		
		public static final Control _shift = new Control(ControlList.LeftJoystickPress, Controllers.Main);
		public static final Rumble _shiftRumbleLow = new Rumble(0.25, false, 200, Controllers.Main);
		public static final Rumble _shiftRumbleHigh = new Rumble(0.75, false, 200, Controllers.Main);
		//public static final double _switchDrive = ;
	}
	
	//Hatch
	public static class Hatch {
		public static final Control _intake = new Control(ControlList.LeftBumper, Controllers.Main);
		public static final Control _eject = new Control(ControlList.RightBumper, Controllers.Main);
	}
	
	//Cargo
	public static class Cargo {
		public static final Control _intake = new Control(ControlList.X, Controllers.Main);
		public static final Control _eject = new Control(ControlList.B, Controllers.Main);
		public static final Control _trapdoorUp = new Control(ControlList.Y, Controllers.Main);
		public static final Control _trapdoorDown = new Control(ControlList.A, Controllers.Main);
		
		public static final Control _armManual = new Control(ControlList.RightJoystickY, Controllers.Main);
		public static final Control _beltManual = new Control(ControlList.DirectionPadRight, Controllers.Main);
	}
	
	//Climb
	public static class Climb {
		
	}
}
