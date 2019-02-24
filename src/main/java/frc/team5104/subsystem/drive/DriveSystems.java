/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.control._Controls;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.Units;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

public class DriveSystems extends BreakerSubsystem.Systems {
	//Device References (d + Device)
	static TalonSRX L1 = Devices.Drive.L1;
	static TalonSRX L2 = Devices.Drive.L2;
	static TalonSRX R1 = Devices.Drive.R1;
	static TalonSRX R2 = Devices.Drive.R2;
	
	//Motors
	static class motors {
		public static void set(double leftSpeed, double rightSpeed, ControlMode mode) {
			L1.set(mode, leftSpeed);
			R1.set(mode, rightSpeed);
		}
		public static void setWithFeedforward(double leftSpeed, double rightSpeed, double feedForward) {
			L1.set(ControlMode.Velocity, leftSpeed, DemandType.ArbitraryFeedForward, feedForward);
			R1.set(ControlMode.Velocity, rightSpeed, DemandType.ArbitraryFeedForward, feedForward);
		}
		
		public static double getLeftBusVoltage() { return L1.getBusVoltage(); }
		public static double getRightBusVoltage() { return R1.getBusVoltage(); }
	}
	
	//Encoders
	public static class encoders {
		/**
		 * Resets both the encoders to zero
		 */
		public static void reset(int timeoutMs) {
			L1.setSelectedSensorPosition(0, 0, timeoutMs);
			R1.setSelectedSensorPosition(0, 0, timeoutMs);
		}
		
		//Raw Encoder Positions
		public static int getRawLeftPosition() { return L1.getSelectedSensorPosition(0); }
		public static int getRawRightPosition() { return R1.getSelectedSensorPosition(0); }
		
		//Raw Encoder Velocities
		public static int getRawLeftVelocity() { return L1.getSelectedSensorVelocity(0); }
		public static int getRawRightVelocity() { return R1.getSelectedSensorVelocity(0); }
		
		//Feet Encoder Positions
		public static double getLeftPositionFeet() { return DriveUnits.ticksToFeet(getRawLeftPosition()); }
		public static double getRightPositionFeet() { return DriveUnits.ticksToFeet(getRawRightPosition()); }
		
		//Feet Encoder Velocities
		public static double getLeftVelocityFeet() { return DriveUnits.talonVelToFeetPerSecond(getRawLeftVelocity()); }
		public static double getRightVelocityFeet() { return DriveUnits.talonVelToFeetPerSecond(getRawRightVelocity()); }
		
		public static String getString() {
			try {
				return "L: " + getRawLeftPosition() + 
						", R: " + getRawRightPosition();
			} catch (Exception e) { e.printStackTrace(); return ""; }
		}
	}
	
	//Shifters
	public static class shifters {
		public static boolean inLowGear() {
			return Devices.Drive.shift.get() == DoubleSolenoid.Value.kReverse;
		}
		
		/**
		 * @param high True: Set to High Gear, False: Set to Low Gear
		 */
		public static void set(boolean high) {
			if (high ? inLowGear() : !inLowGear()) {
				console.log(c.DRIVE, high ? "Shifting High" : "Shifting Low");
				Devices.Drive.shift.set(high ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
				if (high) _Controls.Drive._shiftRumbleHigh.start();
				else _Controls.Drive._shiftRumbleLow.start();
			}
		}
		
		public static void toggle() {
			set(inLowGear());
		}
		
	}

	//Gyro
	public static class gyro {
		public static double getRawAngle() {
			return 0; //Devices.Drive.gyro.getFusedHeading();
		}
		
		public static double getAngle() {
			double a = -getRawAngle();
			a /= Math.cos(Units.degreesToRadians(_DriveConstants._gyroAngle));
			return a;
		}
		
		public static double getPitch() {
			return 0;
//			Devices.Drive.gyro.get
		}
		
		public static void reset(int timeoutMs) {
			//Devices.Drive.gyro.setFusedHeading(0, timeoutMs);
		}
	}
	
	//Setup
	static void setup() {
		//Wait until Talons are Ready to Recieve
		try {
			Thread.sleep(10);
		} catch (Exception e) { console.error(e); }
		
		// Left Talons Config
		L2.set(ControlMode.Follower, L1.getDeviceID());
        L1.configAllowableClosedloopError(0, 0, 10);
        L1.config_kF(0, _DriveConstants._kF, 10);
        L1.config_kP(0, _DriveConstants._kP, 10);
        L1.config_kI(0, _DriveConstants._kI, 10);
        L1.config_kD(0, _DriveConstants._kD, 10);
        
        // Right Talons Config
        R2.set(ControlMode.Follower, R1.getDeviceID());
        R1.configAllowableClosedloopError(0, 0, 10);
        R1.config_kF(0, _DriveConstants._kF, 10);
        R1.config_kP(0, _DriveConstants._kP, 10);
        R1.config_kI(0, _DriveConstants._kI, 10);
        R1.config_kD(0, _DriveConstants._kD, 10);
        
		//Stop the motors
		Drive.stop();
		
		//Reset Gyro + Encodersg
		gyro.reset(10);
		encoders.reset(10);
		
		//Make Sure Everything is caught up :)
		try { Thread.sleep(100); } catch (Exception e) { console.error(e); }
	}
}