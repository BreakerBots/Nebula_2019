/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.climber;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.arm.Arm;
import frc.team5104.subsystem.arm.ArmManager;
import frc.team5104.subsystem.arm.ArmSystems;
import frc.team5104.superstructure.cargo.CargoManager;
import frc.team5104.superstructure.cargo.CargoSystems;
import frc.team5104.util.console;

public class ClimberManager extends BreakerSubsystem.Manager {
	static enum ClimberState { idle, climbing };
	static ClimberState currentState = ClimberState.idle;

	public static enum ClimberStage { 
		initial, //Moves the arm down to touch L3
		lift1,   //Fire first pistons and moves arm to keep the robot level
		lift2, 	 //Fire second pistons and moves arm  to keep the robot level
		forward,	 //Runs intake wheels and user drives drive motors
		end
	};
	static ClimberStage currentStage = ClimberStage.initial;
	static long currentStageStart = System.currentTimeMillis();
	
	public void update() {
		//Handles piston actuation and timing (all movement of the arm is handled in intake)
		switch (currentState) {
			case climbing: {
				switch (currentStage) {
					case initial:
						if (ArmSystems.Encoder.getDegrees() > ArmManager.climbInitController.target) {
							currentStage = ClimberStage.lift1;
							currentStageStart = System.currentTimeMillis();
						}
						
						CargoManager.beltInterpolator.deltaTime = 0.25;
						CargoManager.beltInterpolator.setSetpoint(0);
						CargoSystems.Belt.set(CargoManager.beltInterpolator.update());
						break;
					case lift1:
						ClimberSystems.extendStage1();
						if (System.currentTimeMillis() > currentStageStart + _ClimberConstants._lift1Length) {
							currentStage = ClimberStage.lift2;
							currentStageStart = System.currentTimeMillis();
						}
						
						CargoManager.beltInterpolator.deltaTime = 0.25;
						CargoManager.beltInterpolator.setSetpoint(0);
						CargoSystems.Belt.set(CargoManager.beltInterpolator.update());
						break;
					case lift2:
						ClimberSystems.extendStage1();
						ClimberSystems.extendStage2();
						
						if (System.currentTimeMillis() > currentStageStart + _ClimberConstants._lift2Length) {
							currentStage = ClimberStage.forward;
							currentStageStart = System.currentTimeMillis();
						}
						
						CargoManager.beltInterpolator.deltaTime = 0.25;
						CargoManager.beltInterpolator.setSetpoint(0);
						CargoSystems.Belt.set(CargoManager.beltInterpolator.update());
						break;
					case forward:
						ClimberSystems.extendStage1();
						ClimberSystems.extendStage2();
						
						CargoManager.beltInterpolator.deltaTime = 0.25;
						CargoManager.beltInterpolator.setSetpoint(_ClimberConstants._forwardWheelSpeed);
						CargoSystems.Belt.set(CargoManager.beltInterpolator.update());
						break;
					case end:
						ClimberSystems.retractAll();
						
						if (System.currentTimeMillis() > currentStageStart + _ClimberConstants._retractLength) {
							currentState = ClimberState.idle;
						}
						
						CargoManager.beltInterpolator.deltaTime = 0.25;
						CargoManager.beltInterpolator.setSetpoint(0);
						CargoSystems.Belt.set(CargoManager.beltInterpolator.update());
						break;
				}
				break;
			}
				
			case idle:
				ClimberSystems.retractAll();
				break;
		}
	}

	public void enabled() { currentState = ClimberState.idle; }
	public void disabled() { }
	public ClimberManager() { }
}
