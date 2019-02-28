/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

public class ArmSystems extends BreakerSubsystem.Systems {
	static void applyForce(double force) {
		setVoltage(force);
	}
	
	public static void setVoltage(double voltage) {
		Devices.Cargo.leftArm.set(ControlMode.PercentOutput, voltage / Devices.Cargo.leftArm.getBusVoltage());
		Devices.Cargo.rightArm.set(ControlMode.PercentOutput, voltage / Devices.Cargo.rightArm.getBusVoltage());
	}
	
	//Encoder (Vex Integrated Mag Encoder)
	public static class Encoder {
		public static int getRawRotation() { return Devices.Cargo.leftArm.getSelectedSensorPosition(); }
		public static double getDegrees() { 
			return getRawRotation() / _ArmConstants._ticksPerRevolution * 360 + _ArmConstants._fullyUpDegrees; 
		}
		static void zero() { Devices.Cargo.leftArm.setSelectedSensorPosition(0, 0, 10); }
	}
	
	//Limit Switch (Talon Tach)
	public static class LimitSwitch {
		public static boolean isHit() { 
			return !Devices.Cargo.rightArm.getSensorCollection().isFwdLimitSwitchClosed(); 
		}
		static boolean isNotHit() { return !isHit(); }
	}
}