/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.BreakerPositionController;

class IntakeSystems extends BreakerSubsystem.Systems {
	//Devices
	static TalonSRX leftArm = Devices.Cargo.leftArm;
	static TalonSRX rightArm = Devices.Cargo.rightArm;
	static BreakerPositionController armUpController = new BreakerPositionController
			(_IntakeConstants._kP, _IntakeConstants._kI, _IntakeConstants._kD, _IntakeConstants._tol, _IntakeConstants._upPos);
	static BreakerPositionController armDownController = new BreakerPositionController
			(_IntakeConstants._kP, _IntakeConstants._kI, _IntakeConstants._kD, _IntakeConstants._tol, _IntakeConstants._downPos);
	
	static class Arm {
		private static void set(double speed) {
			leftArm.set(ControlMode.PercentOutput, speed);
			rightArm.set(ControlMode.PercentOutput, -speed);
		}
		
		static void up() {
			if(!armUpController.onTarget()) 
				set(armUpController.update(Encoder.getPosition()));
			 else
				set(0);
		}
		
		static void down() {
			if(!armDownController.onTarget()) 
				set(armDownController.update(Encoder.getPosition()));
			 else
				set(0);
		}
	}
	
	static class Encoder {
		static int getPosition() {
			return leftArm.getSelectedSensorPosition();
		}
		
		static void zero() {
			leftArm.setSelectedSensorPosition(0, 0, 10);
		}
	}
	
	static class LimitSwitch {
		static boolean isHit() {
			return true;
		}
		
		static boolean isNotHit() { return !isHit(); }
	}
}