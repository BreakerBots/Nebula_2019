package frc.team5104.util;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

public class PneumaticFactory {
	
	//PCMs
	public static enum PCM {
		Red(0), 
		Gold(1);
		int deviceId;
		PCM(int deviceId) { this.deviceId = deviceId; }
	};
	
	/**
	 * Creates a double solenoid on the specified PCM, with the specified channels
	 * @param pcm The PCM the solenoid is plugged into
	 * @param forwardChannel The forward channel the solenoid is plugged into
	 * @param reverseChannel The reverse channel the solenoid is plugged into
	 */
	public static DoubleSolenoid getDoubleSolenoid(PCM pcm, int forwardChannel, int reverseChannel) {
		return new DoubleSolenoid(pcm.deviceId, forwardChannel, reverseChannel);
	}
	
	/**
	 * Creates a single solenoid on the specified PCM, with the specified channel
	 * @param pcm The PCM the solenoid is plugged into
	 * @param channel
	 */
	public static Solenoid getSolenoid(PCM pcm, int channel) {
		return new Solenoid(pcm.deviceId, channel);
	}
	
	/**
	 * Creates a compressor on the specified PCM
	 * @param pcm The PCM the compressor is plugged into
	 */
	public static Compressor getCompressor(PCM pcm) {
		return new Compressor(pcm.deviceId);
	}
}