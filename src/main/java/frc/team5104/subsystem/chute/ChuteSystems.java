/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.chute;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

public class ChuteSystems extends BreakerSubsystem.Systems {
	//Devices 
	static DoubleSolenoid trapdoor = Devices.Cargo.trapdoor;
	static TalonSRX ramp = Devices.Cargo.ramp;
	// photoelectric sensor
	
	static class Ramp {
		static void up() {
			trapdoor.set(_ChuteConstants._up);
		}
		
		static void down() {
			trapdoor.set(_ChuteConstants._down);
		}
		
		static void setIntake(boolean set) {
			if(set)
				ramp.set(ControlMode.PercentOutput, _ChuteConstants._rampSpeed);
			else
				ramp.set(ControlMode.PercentOutput, 0);
		}
		
		static boolean isUp() {
			return trapdoor.get() == _ChuteConstants._up;
		}
		static boolean isDown() { return !isUp(); }
	}
	
	public static class BeamBreak {
		public static boolean isHit() {
			return true;
		}
		public static boolean isNotHit() { return !isHit(); }
	}
}