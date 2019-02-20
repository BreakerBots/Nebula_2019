package frc.team5104.util;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

public class SensorFactory {
	/**
	 * Creates a DigitalInput object to handle the connection on the roboRIO
	 * @param channel The channel of the connection on the roboRIO
	 */
	public static DigitalInput getDigitalInput(int channel) {
		return new DigitalInput(channel);
	}
	
	/**
	 * Creates a AnalogInput object to handle the connection on the roboRIO
	 * @param channel The channel of the connection on the roboRIO
	 */
	public static AnalogInput getAnalogInput(int channel) {
		return new AnalogInput(channel);
	}
	
	/**
	 * Creates a PigeonIMU object to handle the gyro plugged in through a talon
	 * @param talonDeviceId The device id of the talon the gyro is plugged in to
	 */
	public static PigeonIMU getPigionImu(int talonDeviceId) {
		return new PigeonIMU(talonDeviceId);
	}
}
