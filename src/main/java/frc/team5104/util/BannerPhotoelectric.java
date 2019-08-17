package frc.team5104.util;

import edu.wpi.first.wpilibj.AnalogInput;

public class BannerPhotoelectric {
	private AnalogInput analogInput;
	public int disconnectValue;
	
	//Constructor
	public BannerPhotoelectric(int port) { this(port, 205); }
	public BannerPhotoelectric(int port, int disconnectValue) {
		analogInput = new AnalogInput(port);
		this.disconnectValue = disconnectValue;
	}
	
	public boolean get() {
		return !(Math.round(analogInput.getAverageVoltage() / 5.0) == 1);
	}

	public boolean disconnected() {
		return analogInput.getAverageValue() < disconnectValue;
	}
}
