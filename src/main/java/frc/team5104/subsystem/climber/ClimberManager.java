/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.climber;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;

class ClimberManager extends BreakerSubsystem.Manager {
	static enum ClimberState { idle, climbing };
	static ClimberState currentState = ClimberState.idle;

	static enum ClimberStage { stage1, stage2 };
	static ClimberStage currentStage = ClimberStage.stage1;
	
	public void update() {
		switch (currentState) {
			case climbing:
				
				if (currentStage == ClimberStage.stage1) {
					ClimberSystems.extendStage1();
					//IntakeSystems.Arm.setVoltage(5)
				}
				else {
					ClimberSystems.extendStage1();
					ClimberSystems.extendStage2();
					//IntakeSystems.Arm.setVoltage(10)
				}
				
				break;
			case idle:
				ClimberSystems.retractAll();
				break;
		}
	}

	public void enabled(RobotMode mode) { }
	public void disabled() { }
	public ClimberManager() { }
}
