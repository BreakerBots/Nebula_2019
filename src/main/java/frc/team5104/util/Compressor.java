package frc.team5104.util;

import frc.team5104.main.Devices;

public class Compressor {
	
	static Buffer buffer;
	
	//Pressure Sensors
	public static double getRawPressure() {
		return Devices.Other.compressorReader.getVoltage();
	}
	public static double getPressure() {
		return getRawPressure() * 50.5948 - 24.4587;
	}
	
	//Guesses
	public static boolean shouldRun(double batteryVoltage) {
		buffer.update(getPressure());
		return buffer.getIntAvg() < 40;
	}
	
	//Compressor Running
	public static void run() {
		Devices.Other.compressor.start();
		buffer = new Buffer(5, 45);
	}
	
	public static void stop() {
		Devices.Other.compressor.stop();
	}
	
	public static boolean isRunning() {
		return Devices.Other.compressor.enabled();
	}
}
