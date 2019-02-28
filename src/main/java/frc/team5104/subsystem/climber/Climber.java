/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.climber;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.climber.ClimberManager.ClimberStage;
import frc.team5104.subsystem.climber.ClimberManager.ClimberState;

public class Climber extends BreakerSubsystem.Actions {
	public static void climb() {
		ClimberManager.currentState = ClimberState.climbing;
		ClimberManager.currentStage = ClimberStage.initial;
	}
	
	public static void endClimb() {
		ClimberManager.currentState = ClimberState.idle;
	}
	
	public static boolean isClimbing() {
		return ClimberManager.currentState == ClimberState.climbing;
	}
	
	public static ClimberManager.ClimberStage getStage() {
		return ClimberManager.currentStage;
	}
}
