/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.hatch;

import frc.team5104.subsystem.BreakerSubsystem;

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
	
	public void update() {
		switch (currentState) {
			case hold:
				HatchSystems.Trap.out();
				HatchSystems.Lazyboy.up();
				HatchSystems.Ejector.retract();
				break;
			case idle:
				HatchSystems.Trap.in();
				HatchSystems.Lazyboy.back();
				HatchSystems.Ejector.retract();
				break;
			case eject:
				HatchSystems.Trap.in();
				HatchSystems.Lazyboy.up();
				
				if (ejectHard)
					HatchSystems.Ejector.eject();
				else
					HatchSystems.Ejector.retract();
					
				if (System.currentTimeMillis() > _HatchConstants._ejectModeLength + ejectStartTime)
					currentState = HatchState.idle;
				break;
			case intake:
				HatchSystems.Trap.in();
				HatchSystems.Lazyboy.up();
				HatchSystems.Ejector.retract();
				
				if (System.currentTimeMillis() > _HatchConstants._intakeModeLength + intakeStartTime)
					currentState = HatchState.hold;
			default:
				break;
		}
	}

	public void disabled() { }
	public void enabled() { }
	public HatchManager() { }
}
