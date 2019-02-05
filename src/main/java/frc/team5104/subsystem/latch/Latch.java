/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.latch;

import frc.team5104.subsystem.BreakerSubsystem;

public class Latch extends BreakerSubsystem.Actions {
	public static void intake() {
		LatchSystems.Lazyboy.up();
		LatchSystems.Dad.open();
	}
	
	public static void hold() {
		LatchSystems.Lazyboy.up();
		LatchSystems.Dad.close();
	}
	
	public static void idle() {
		LatchSystems.Lazyboy.down();
		LatchSystems.Dad.close();	
	}
}
