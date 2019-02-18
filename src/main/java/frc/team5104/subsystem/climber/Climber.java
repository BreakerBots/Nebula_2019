/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.climber;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.climber.ClimberManager.ClimberStage;
import frc.team5104.subsystem.climber.ClimberManager.ClimberState;

public class Climber extends BreakerSubsystem.Actions {
	public static void Climb() {
		ClimberManager.currentStage = ClimberStage.stage1;
		ClimberManager.currentState = ClimberState.climbing;
	}
	
	public static void StopClimb() {
		ClimberManager.currentState = ClimberState.idle;
	}
}
