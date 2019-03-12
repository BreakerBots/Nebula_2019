/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.hatch;

import frc.team5104.control._Controls;
import frc.team5104.main.RobotState;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

public class HatchManager extends BreakerSubsystem.Manager {
	public static enum HatchState {
		intake,//Trap: in, Lazyboy: up, Ejector: in
		idle,  //Trap: in, Lazyboy: either, Ejector: in
		hold,  //Trap: out, Lazyboy: up, Ejector: in
		eject  //Trap: in, Lazyboy: up, Ejector: either
	}
	static HatchState currentState = HatchState.idle;
	static long ejectStartTime = System.currentTimeMillis();
	static boolean ejectHard = false;
	static long intakeStartTime = System.currentTimeMillis();
	static boolean fastIntake = false;
	
	public void update() {
		switch (currentState) {
			case hold:
				HatchSystems.Flaps.out();
				HatchSystems.Lazyboy.up();
				HatchSystems.Ejector.pullOut();
				break;
			case idle:
				HatchSystems.Flaps.in();
				HatchSystems.Lazyboy.back();
				HatchSystems.Ejector.pullOut();
				break;
			case eject:
				HatchSystems.Flaps.in();
				HatchSystems.Lazyboy.up();
				
				if (ejectHard && System.currentTimeMillis() > _HatchConstants._ejectorDelay + ejectStartTime) {
					HatchSystems.Ejector.yeet();
				}
				else
					HatchSystems.Ejector.pullOut();
					
				if (System.currentTimeMillis() > _HatchConstants._ejectModeLength + ejectStartTime)
					currentState = HatchState.idle;
				break;
			case intake:
				HatchSystems.Flaps.in();
				HatchSystems.Lazyboy.up();
				HatchSystems.Ejector.pullOut();
				
				if (fastIntake) {
					if (System.currentTimeMillis() > _HatchConstants._intakeModeFastLength + intakeStartTime) {
						fastIntake = false;
						currentState = HatchState.hold;
						_Controls.Hatch._holdRumble.start();
						console.log(c.HATCH, "Finished Sandstorm/Fast Hatch Intake");
					}
				}
				else if (System.currentTimeMillis() > _HatchConstants._intakeModeLength + intakeStartTime) {
					currentState = HatchState.hold;
					_Controls.Cargo._intakeRumble.start();
				}
			default:
				break;
		}
	}

	public void disabled() { }
	public void enabled() {
		if (RobotState.isSandstorm()) {
			fastIntake = true;
			intakeStartTime = System.currentTimeMillis();
			currentState = HatchState.intake;
			console.log(c.HATCH, "Starting Sandstorm/Fast Hatch Intake");
		}
	}
	public HatchManager() { }
}
