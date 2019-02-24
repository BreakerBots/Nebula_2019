/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.climber;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.intake.Intake;
import frc.team5104.subsystem.intake.IntakeSystems;

public class ClimberManager extends BreakerSubsystem.Manager {
	static enum ClimberState { idle, climbing };
	static ClimberState currentState = ClimberState.idle;

	public static enum ClimberStage { stage0, stage1, stage2 };
	static ClimberStage currentStage = ClimberStage.stage0;
	
	public void update() {
		switch (currentState) {
			case climbing:
				if (currentStage == ClimberStage.stage0) {
					Intake.down();
				} else if (currentStage == ClimberStage.stage1) {
					Intake.climb();
					ClimberSystems.extendStage1();
				} else if (currentStage == ClimberStage.stage2){
					Intake.climb();
					ClimberSystems.extendStage1();
					ClimberSystems.extendStage2();
				}
				
				break;
			case idle:
				ClimberSystems.retractAll();
				Intake.up();
				break;
		}
	}

	public void enabled(RobotMode mode) { }
	public void disabled() { }
	public ClimberManager() { }
}
