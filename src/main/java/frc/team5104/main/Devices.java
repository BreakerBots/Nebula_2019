/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.subsystem.drive._DriveConstants;
import frc.team5104.util.PneumaticFactory;
import frc.team5104.util.TalonFactory;
import frc.team5104.util.PneumaticFactory.PCM;
import frc.team5104.util.TalonFactory.TalonSettings;

/**
 * All Devices used in Code
 */
public class Devices {
	//Drive
	public static class Drive {
		public static TalonSRX L1 = TalonFactory.getTalon(11, new TalonSettings(NeutralMode.Brake, true, _DriveConstants._currentLimit, true));
		public static TalonSRX L2 = TalonFactory.getTalon(12, new TalonSettings(NeutralMode.Brake, true, _DriveConstants._currentLimit, true));
		public static TalonSRX R1 = TalonFactory.getTalon(13, new TalonSettings(NeutralMode.Brake, false, _DriveConstants._currentLimit, true));
		public static TalonSRX R2 = TalonFactory.getTalon(14, new TalonSettings(NeutralMode.Brake, false, _DriveConstants._currentLimit, true));
		
//		public static PigeonIMU gyro = new PigeonIMU(L2);
		
		public static DoubleSolenoid shift = PneumaticFactory.getDoubleSolenoid(PCM.Red, 6, 7);
	}
	
	//Cargo
	public static class Cargo {
		public static TalonSRX leftArm = TalonFactory.getTalon(21);
		public static TalonSRX rightArm = TalonFactory.getTalon(22);
		
		public static DoubleSolenoid trapdoor = PneumaticFactory.getDoubleSolenoid(PCM.Red, 4, 5);
		public static DigitalInput photoelectricSensor = new DigitalInput(9); //0 for astro
		
		public static TalonSRX belt = TalonFactory.getTalon(31);
	}
	
	//Hatch
	public static class Hatch {
		public static DoubleSolenoid trap = PneumaticFactory.getDoubleSolenoid(PCM.Red, 3, 2);
		public static DoubleSolenoid lazyBoy = PneumaticFactory.getDoubleSolenoid(PCM.Red, 1, 0);
		//public static DoubleSolenoid ejector = PneumaticFactory.getDoubleSolenoid(PCM.Red, ?, ?);
	}
	
	//Climber
	public static class Climber {
		//public static DoubleSolenoid stage1 = PneumaticFactory.getDoubleSolenoid(PCM.?, ?, ?);
		//public static DoubleSolenoid stage2 = PneumaticFactory.getDoubleSolenoid(PCM.?, ?, ?);
	}
	
	//Other
	public static class Other {
		public static Compressor compressor = PneumaticFactory.getCompressor(PCM.Gold);
		public static AnalogInput compressorReader = new AnalogInput(3);
	}
}
