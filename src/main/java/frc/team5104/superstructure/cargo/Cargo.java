/*BreakerBots Robotics Team 2019*/
package frc.team5104.superstructure.cargo;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.superstructure.cargo.CargoManager.CargoState;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

public class Cargo extends BreakerSubsystem.Actions {
	public static void intake() {
		console.log(c.CARGO, "Intaking");
		CargoManager.currentState = CargoState.intake;
	}
	
	public static void eject() {
		console.log(c.CARGO, "Ejecting");
		CargoManager.currentState = CargoState.eject;
		CargoManager.ejectStart = System.currentTimeMillis();
	}
	
	public static void idle() {
		console.log(c.CARGO, "Idle");
		CargoManager.currentState = CargoState.idle;
	}
}
