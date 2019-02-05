/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.util.TalonFactory;

/**
 * All Devices used in Athena's Code
 */
public class Devices {

	//Main
	public static class Main {
		//public static PowerDistributionPanel pdp = new PowerDistributionPanel();
		public static Compressor compressor = new Compressor();
		public static AnalogInput compressorReader = new AnalogInput(0);
	}
	
	//Drive
	public static class Drive {
		public static TalonSRX L1 = TalonFactory.getTalon(11);
		public static TalonSRX L2 = TalonFactory.getTalon(12);
		public static TalonSRX R1 = TalonFactory.getTalon(13);
		public static TalonSRX R2 = TalonFactory.getTalon(14);
		
		public static PigeonIMU gyro = new PigeonIMU(L2);
		
		public static DoubleSolenoid shift = new DoubleSolenoid(0, 1);
	}
	
	//Cargo
	public static class Cargo {
		public static TalonSRX leftArm = TalonFactory.getTalon(21);
		public static TalonSRX rightArm = TalonFactory.getTalon(22);
		public static TalonSRX ramp = TalonFactory.getTalon(31);
		public static DoubleSolenoid trapdoor = new DoubleSolenoid(2, 3);
	}
	
	//Hatch
	public static class Hatch {
		public static DoubleSolenoid latch = new DoubleSolenoid(4, 5);
		public static DoubleSolenoid lean = new DoubleSolenoid(6, 7);
	}
}
