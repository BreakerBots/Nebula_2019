/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.climber.Climber;
import frc.team5104.subsystem.climber.ClimberManager;
import frc.team5104.subsystem.drive.DriveSystems.gyro;
import frc.team5104.superstructure.cargo.CargoManager;
import frc.team5104.util.BreakerPositionController;
import frc.team5104.util.CSV.CSVLoggable;

public class IntakeSystems extends BreakerSubsystem.Systems implements CSVLoggable {
	//Devices
	static BreakerPositionController armController = new BreakerPositionController
			(_IntakeConstants._kP, _IntakeConstants._kI, _IntakeConstants._kD, _IntakeConstants._tolerance);
	static BreakerPositionController climbController = new BreakerPositionController
			(_IntakeConstants._cP, _IntakeConstants._cI, _IntakeConstants._cD, _IntakeConstants._ctolerance, 0);
	static boolean isDown = false;
	
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
			armController.setTarget(_IntakeConstants._upPos);
			if(!armController.onTarget()) 
				applyForce(armController.update(Encoder.getDegrees()));
			 else
				applyForce(0);
			isDown = false;
		}
		
		static void down() {
//			csv.update(new String[] { ""+Encoder.getDegrees(), ""+armController.target });
			if(!Climber.isClimbing() && !isDown) {
				// Move arm down
				armController.setTarget(_IntakeConstants._downPos);
				if(!armController.onTarget()) applyForce(armController.update(Encoder.getDegrees()));
				else { applyForce(0); isDown = true; }
			} else if(!Climber.isClimbing() && isDown) {
				// Hold arm at level for Cargo
			} else if(Climber.isClimbing() && Climber.getStage() == ClimberManager.ClimberStage.stage0) {
				armController.setTarget(_IntakeConstants._stage0Target);
				if(!armController.onTarget()) applyForce(armController.update(Encoder.getDegrees()));
				else applyForce(0);
			}
		}

		static void climb() {
//			csv.update(new String[] { ""+Encoder.getDegrees(), ""+armController.target });
//			climbController.setTarget(Climber.getStage() == ClimberManager.ClimberStage.stage1 ? _IntakeConstants._stage1Target : _IntakeConstants._stage2Target);
			if(!climbController.onTarget()) applyForce(climbController.update(gyro.getPitch()));
			else if(climbController.onTarget() && Climber.getStage() == ClimberManager.ClimberStage.stage1) Climber.nextStage();
			else applyForce(0);
		}
	}
	
	public static class Encoder {
		public static int getRawRotation() { return Devices.Cargo.leftArm.getSelectedSensorPosition(); }
		public static double getDegrees() { 
			return getRawRotation() / _IntakeConstants._ticksPerRevolution * 360 + _IntakeConstants._fullyUpDegrees; 
		}
		
		static void zero() { Devices.Cargo.leftArm.setSelectedSensorPosition(0, 0, 10); }
	}
	
	public static class LimitSwitch {
		public static boolean isHit() { 
			return Devices.Cargo.rightArm.getSensorCollection().isFwdLimitSwitchClosed(); 
		}
		static boolean isNotHit() { return !isHit(); }
	}

	public String[] getCSVHeader() {
		return new String[] { "current", "target" };
	}
	public String[] getCSVData() {
		return new String[] { ""+Encoder.getDegrees(), ""+armController.target };
	}
	public String getCSVName() { return "IntakePID"; }
}