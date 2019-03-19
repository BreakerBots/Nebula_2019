package frc.team5104.util;

import edu.wpi.first.wpilibj.AnalogInput;

public class BannerPhotoelectric {
	private AnalogInput analogInput;
	public BannerPhotoelectric(int port) {
		analogInput = new AnalogInput(port);
	}
	
	public boolean get() {
		return !(Math.round(analogInput.getAverageVoltage() / 5.0) == 1);
	}
}
