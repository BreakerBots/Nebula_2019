/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.BreakerPositionController;

public class IntakeSystems extends BreakerSubsystem.Systems {
	//Devices
	static BreakerPositionController armUpController = new BreakerPositionController
			(_IntakeConstants._kP, _IntakeConstants._kI, _IntakeConstants._kD, _IntakeConstants._tolerance, _IntakeConstants._upPos);
	static BreakerPositionController armDownController = new BreakerPositionController
			(_IntakeConstants._kP, _IntakeConstants._kI, _IntakeConstants._kD, _IntakeConstants._tolerance, _IntakeConstants._downPos);
	
	public static class Arm {
		public static void set(double voltage) {
			Devices.Cargo.leftArm.set(ControlMode.PercentOutput, voltage / Devices.Cargo.leftArm.getBusVoltage());
			Devices.Cargo.rightArm.set(ControlMode.PercentOutput, -voltage / Devices.Cargo.rightArm.getBusVoltage());
		}
		
		static void up() {
			if(!armUpController.onTarget()) 
				set(armUpController.update(Encoder.getDegrees()));
			 else
				set(0);
		}
		
		static void down() {
			if(!armDownController.onTarget()) 
				set(armDownController.update(Encoder.getDegrees()));
			 else
				set(0);
		}
	}
	
	static class Encoder {
		static int getRawRotation() {
			return Devices.Cargo.leftArm.getSelectedSensorPosition();
		}
		
		static double getPercentRotation() {
			return getRawRotation() / _IntakeConstants._ticksPerRevolution;
		}
		
		static double getDegrees() {
			return getPercentRotation() * 360;
		}
		
		static void zero() {
			Devices.Cargo.leftArm.setSelectedSensorPosition(0, 0, 10);
		}
	}
	
	static class LimitSwitch {
		static boolean isHit() {
			return true;
		}
		
		static boolean isNotHit() { return !isHit(); }
	}
}