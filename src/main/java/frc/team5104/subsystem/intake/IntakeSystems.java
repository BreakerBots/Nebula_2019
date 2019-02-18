/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.BreakerPositionController;
import frc.team5104.util.CSV.CSVLoggable;

public class IntakeSystems extends BreakerSubsystem.Systems implements CSVLoggable {
	//Devices
	static BreakerPositionController armController = new BreakerPositionController
			(_IntakeConstants._kP, _IntakeConstants._kI, _IntakeConstants._kD, _IntakeConstants._tolerance);
	
	public static class Arm {
		public static void setVoltage(double voltage) {
			Devices.Cargo.leftArm.set(ControlMode.PercentOutput, -voltage / Devices.Cargo.leftArm.getBusVoltage());
			Devices.Cargo.rightArm.set(ControlMode.PercentOutput, voltage / Devices.Cargo.rightArm.getBusVoltage());
		}
		
		static void applyForce(double force) {
			setVoltage(force);
		}
		
		static void up() {
//			csv.update(new String[] { ""+Encoder.getDegrees(), ""+armController.target });
//			armController.setTarget(_IntakeConstants._upPos);
//			if(!armController.onTarget()) 
//				applyForce(armController.update(Encoder.getDegrees()));
//			 else
				applyForce(0);
		}
		
		static void down() {
//			csv.update(new String[] { ""+Encoder.getDegrees(), ""+armController.target });
//			armController.setTarget(_IntakeConstants._downPos);
//			if(!armController.onTarget()) 
//				applyForce(armController.update(Encoder.getDegrees()));
//			else
				applyForce(0);
		}
	}
	
	public static class Encoder {
		public static int getRawRotation() { return Devices.Cargo.leftArm.getSelectedSensorPosition(); }
		public static double getDegrees() { 
			return getRawRotation() / _IntakeConstants._ticksPerRevolution * 360 + _IntakeConstants._fullyUpDegrees; 
		}
		
		static void zero() { Devices.Cargo.leftArm.setSelectedSensorPosition(0, 0, 10); }
	}
	
	static class LimitSwitch {
		static boolean isHit() { return true; }
		static boolean isNotHit() { return !isHit(); }
	}

	public String[] getHeader() {
		return new String[] { "current", "target" };
	}

	public String[] getData() {
		return new String[] { ""+Encoder.getDegrees(), ""+armController.target };
	}
}