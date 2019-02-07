/*BreakerBots Robotics Team 2019*/
package frc.team5104.superstructure.cargo;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

class CargoSystems extends BreakerSubsystem.Systems {
	static class Belt {
		static void set(double speed) {
			Devices.Cargo.belt.set(ControlMode.PercentOutput, speed);
		}
	}
}