/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team5104.main.Devices;
import frc.team5104.main._RobotConstants;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.TalonFactory;
import frc.team5104.util.console;
import frc.team5104.webapp.Tuner.tunerOutput;

public class ArmSystems extends BreakerSubsystem.Systems {
	public static void applyForce(double force) {
		if (ArmSystems.LimitSwitch.isHit() && force < 0) force = 0;
		force = BreakerMath.clamp(force, -_ArmConstants._upVLimit, _ArmConstants._downVLimit);
		setVoltage(force);
	}
	
	private static void setVoltage(double voltage) {
		console.log(Encoder.getRawRotation());
		Devices.Cargo.leftArm.set(ControlMode.PercentOutput, voltage / Devices.Cargo.leftArm.getBusVoltage());
		Devices.Cargo.rightArm.set(ControlMode.PercentOutput, voltage / Devices.Cargo.rightArm.getBusVoltage());
	}
	
	//Encoder (Vex Integrated Mag Encoder)
	public static class Encoder {
		public static int getRawRotation() { 
			return Devices.Cargo.rightArm.getSelectedSensorPosition() * -1; 
		}
		@tunerOutput
		public static double getDegrees() { 
			return getRawRotation() / _ArmConstants._ticksPerRevolution * 360 + _ArmConstants._fullyUpDegrees; 
		}
		static void zero() { Devices.Cargo.rightArm.setSelectedSensorPosition(0, 0, 10); }
		public static boolean disconnected() {
			return TalonFactory.magEncoderDisconnected(Devices.Cargo.rightArm);
		}
	}
	
	//Limit Switch (Talon Tach)
	public static class LimitSwitch {
		public static boolean isHit() { 
			return Devices.Cargo.limitSwitch.get();
			//return !Devices.Cargo.rightArm.getSensorCollection().isFwdLimitSwitchClosed(); 
		}
		static boolean isNotHit() { return !isHit(); }
	}
}