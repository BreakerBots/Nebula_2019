/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.climber;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.climber.ClimberManager.ClimberStage;
import frc.team5104.subsystem.climber.ClimberManager.ClimberState;

public class Climber extends BreakerSubsystem.Actions {
	public static void Climb() {
		ClimberManager.currentState = ClimberState.climbing;
		ClimberManager.currentStage = ClimberStage.stage0;
	}
	
	public static void StopClimb() {
		ClimberManager.currentState = ClimberState.idle;
	}
	
	public static boolean isClimbing() {
		return ClimberManager.currentState == ClimberState.climbing;
	}
	
	public static ClimberManager.ClimberStage getStage() {
		return ClimberManager.currentStage;
	}
	
	public static void nextStage() {
		if(ClimberManager.currentStage == ClimberStage.stage0) ClimberManager.currentStage = ClimberStage.stage1;
		else if(ClimberManager.currentStage == ClimberStage.stage1) ClimberManager.currentStage = ClimberStage.stage2;
		else StopClimb();
	}
}
