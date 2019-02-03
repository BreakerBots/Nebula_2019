/*BreakerBots Robotics Team 2019*/
package frc.team5104.superstructure.cargo;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.superstructure.cargo.CargoManager.CargoState;

public class Cargo extends BreakerSubsystem.Actions {
	public static void intake() {
		CargoManager.currentState = CargoState.intake;
	}
	
	public static void eject() {
		CargoManager.currentState = CargoState.eject;
		CargoManager.ejectStart = System.currentTimeMillis();
	}
	
	public static void idle() {
		CargoManager.currentState = CargoState.idle;
	}
}
