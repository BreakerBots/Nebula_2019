/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.climber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.PneumaticFactory;

class ClimberSystems extends BreakerSubsystem.Systems {
	//Devices
	static DoubleSolenoid stage1 = Devices.Climber.stage1;
	static DoubleSolenoid stage2 = Devices.Climber.stage2;
	
	static void extendStage1() {
		stage1.set(_ClimberConstants._stage1In);
	}
	
	static boolean extendedStage1() {
		return !(stage1.get() == _ClimberConstants._stage1In);
	}
	
	static void extendStage2() {
		stage2.set(_ClimberConstants._stage2In);
	}

	static boolean extendedStage2() {
		return !(stage2.get() == _ClimberConstants._stage2In);
	}
	
	static void retractAll() {
		stage1.set(PneumaticFactory.getOppositeValue(_ClimberConstants._stage1In));
		stage2.set(PneumaticFactory.getOppositeValue(_ClimberConstants._stage2In));
	}
}