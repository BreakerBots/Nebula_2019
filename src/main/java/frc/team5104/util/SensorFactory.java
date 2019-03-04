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
}
