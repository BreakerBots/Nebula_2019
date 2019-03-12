/*BreakerBots Robotics Team 2019*/
package frc.team5104.util;

import frc.team5104.main.Devices;
import frc.team5104.util.CSV.CSVLoggable;
import frc.team5104.webapp.Tuner.tunerOutput;

/**
 * Handles the compressor and the compressor reader together
 */
public class Compressor implements CSVLoggable {
	
	public static Buffer buffer = new Buffer(5, 0);
	
	//Pressure Sensors
	public static double getRawPressure() {
		return Devices.Other.compressorReader.getVoltage();
	}
	
	@tunerOutput
	public static double getPressure() {
		return getRawPressure() * 50.5948 - 24.4587;
	}
	
	public static String getString() {
		buffer.update(getPressure());
		return String.format("%.2f", buffer.getDoubleOutput());
	}
	
	//Guesses
	public static boolean shouldRun(double batteryVoltage) {
		return true;
		//return buffer.getIntAvg() < 40;
	}
	
	//Compressor Running
	public static void run() {
		Devices.Other.compressor.start();
	}
	
	public static void stop() {
		Devices.Other.compressor.stop();
	}
	
	public static boolean isRunning() {
		return Devices.Other.compressor.enabled();
	}

	public String[] getCSVHeader() {
		return new String[] { "psi" };
	}
	public String[] getCSVData() {
		return new String[] { ""+getPressure() };
	}
	public String getCSVName() {
		return "Pneumatics";
	}
}
