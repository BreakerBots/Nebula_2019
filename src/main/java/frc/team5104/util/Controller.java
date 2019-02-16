/*BreakerBots Robotics Team 2019*/
package frc.team5104.util;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;

/**
 * <h1>Controller</h1>
 * A much simpler to use version of WPI's Joystick (controller) class
 * There is also a bunch of added functional for rumble and click events
 * Every single control in the Control Enum is treated as a Button and Axis
 * Features:
 *  - Rumble (Soft, Hard)
 *  - Rumble for Duration (Soft, Hard)
 *	- Get Held Time (time button has been held for)
 *	- Get Axis Value
 *	- Get Button Down
 *	- Button Pressed/Released Event (Events are true for one tick (and then false) when triggered)
 *  
 * For Xbox One Controllers references the official control sheet: https://support.xbox.com/en-US/xbox-one/accessories/xbox-one-wireless-controller
 */
public class Controller {
	//List of Controllers
	public static enum Controllers {
		Main(0);
		//Secondary(1);
		private Joystick handler;
		private Controllers(int slot) { this.handler = new Joystick(slot); }
	};
	
	//Xbox One Controller Controls
	public static enum ControlList { 
		//Buttons
		A(1, 1), B(2, 1), X(3, 1), Y(4, 1),	
		LeftBumper(5, 1),	RightBumper(6, 1),	
		Menu(7, 1),	List(8, 1),	
				
		//Direction Pad (D-Pad)
		DirectionPadUpLeft(315, 3), DirectionPadUp(0, 3), DirectionPadUpRight(45, 3), 
		DirectionPadRight(90, 3),	
		DirectionPadDownLeft(225, 3), DirectionPadDown(180, 3), DirectionPadDownRight(135, 3), 
		DirectionPadLeft(270, 3),
		
		//Joysticks
		LeftJoystickX(0, 2), LeftJoystickY(1, 2), LeftJoystickPress(9, 1),
		RightJoystickX(4, 2), RightJoystickY(5, 2), RightJoystickPress(10, 1),
		
		//Triggers
		LeftTrigger(2, 2), RightTrigger(3, 2);					 
		
		private int slot, type;
		private ControlList(int slot, int type) { this.slot = slot; this.type = type; }
	}
		
	private static abstract class ControllerElement { abstract void update(); }
	private static List<ControllerElement> register = new ArrayList<ControllerElement>();
	
	public static class Control extends ControllerElement {
		public ControlList control;
		public Controllers controller;
		public boolean reversed; 
		public double deadzone;
		private boolean val, lastVal, pressed, released;
		private long time;
		
		/**
		 * Creates a control object that can be referenced later.
		 * @param control The specified control
		 * @param controller The target controller for this rumble.
		 */
		public Control(ControlList control, Controllers controller) { this(control, controller, false, 0.6); }
		/**
		 * Creates a control object that can be referenced later.
		 * @param control The specified control
		 * @param controller The target controller for this rumble.
		 * @param reversed (For axis only) When converting from axis to button: if the axis should be flipped before
		 * @param deadzone (For axis only) When converting from axis to button: the crossover point in which the axis should be considered pressed
		 */
		public Control(ControlList control, Controllers controller, boolean reversed, double deadzone) {
			this.control = control;
			this.controller = controller;
			this.reversed = reversed;
			this.deadzone = deadzone;
			register.add(this);
		}
		
		/**Returns the percent of the axis, Just The Default Axis*/
		public double getAxis() { return controller.handler.getRawAxis(control.slot); }
		/**Returns true if button is down, Just The Default Button State*/
		public boolean getHeld() { return val; }
		/**Returns how long the button has been held down for, if not held down returns 0*/
		public double getHeldTime() { return val ? ((double)(System.currentTimeMillis() - time))/1000 : 0; }
		/**Returns true for one tick if button goes from up to down*/
		public boolean getPressed() { return pressed; }
		/**Returns true for one tick if button goes from down to up*/
		public boolean getReleased() { return released; }
		/**Returns the time the click lasted for, for one tick when button goes from down to up*/
		public double getClickTime() { return released ? ((double)(System.currentTimeMillis() - time))/1000 : 0; }
		/**Returns true for one tick if the button has been held for the specified time*/
		public boolean getHeldEvent(double time) { return Math.abs(getHeldTime() - time) <= 0.01; }
		
		void update() {
			pressed = false;
			released = false;
			
			if (control.type == 1)
				val = controller.handler.getRawButton(control.slot);
			else if (control.type == 2)
				val = (controller.handler.getRawAxis(control.slot) * (reversed ? -1 : 1)) > deadzone ? true : false;
			else if (control.type == 3)
				val = controller.handler.getPOV() == control.slot;
				
			if (val != lastVal) {
				lastVal = val;
				if (val == true) { 
					pressed = true; 
					time = System.currentTimeMillis(); 
				}
				else released = true;
			}
		}
	}
	
	public static class Rumble extends ControllerElement {
		private static long timerTarget; 
		private static boolean timerRunning = false;
		public Controllers controller;
		public boolean hard;
		public int timeoutMs;
		public double strength;
		/**
		 * Creates a saveable rumble object that can be referenced later.
		 * @param strength (0-1) Perecent strength of the rumble
		 * @param hard If the rumble should be hard (a deeper rumble) or soft (a lighter rumble)
		 * @param controller The target controller for this rumble.
		 */
		public Rumble(double strength, boolean hard, Controllers controller) { this(strength, hard, Integer.MIN_VALUE, controller); }
		/**
		 * Creates a saveable rumble object that can be referenced later.
		 * @param strength (0-1) Perecent strength of the rumble
		 * @param hard If the rumble should be hard (a deeper rumble) or soft (a lighter rumble)
		 * @param timeoutMs The time (milliseconds) in which the rumble should automatically stop.
		 * @param controller The target controller for this rumble.
		 */
		public Rumble(double strength, boolean hard, int timeoutMs, Controllers controller) {
			this.strength = strength;
			this.hard = hard;
			this.timeoutMs = timeoutMs;
			this.controller = controller;
			register.add(this);
		}
		
		/**
		 * Starts the rumble (specified in the constructor)
		 */
		public void start() {
			controller.handler.setRumble(hard ? RumbleType.kRightRumble : RumbleType.kLeftRumble, strength);
			if (timeoutMs != Integer.MIN_VALUE) {
				timerTarget = System.currentTimeMillis() + timeoutMs; 
				timerRunning = true;
			}
		}
		
		void update() {
			if (timerRunning && System.currentTimeMillis() >= timerTarget) { 
				controller.handler.setRumble(hard ? RumbleType.kRightRumble : RumbleType.kLeftRumble, 0); 
				timerRunning = false;
			}
		}
	}
	
	public static void update() { 
		for (ControllerElement obj : register) { 
			obj.update(); 
		} 
	}
}