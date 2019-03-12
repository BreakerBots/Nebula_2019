package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;
import frc.team5104.util.Compressor;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

class CompressorController extends BreakerController {
	void update() {
		if (_Controls.Main._manualCompressor) {
			if (_Controls.Main._toggleCompressor.getPressed()) {
				console.log(c.OTHER, "Air Pressure: " + String.format("%.2f", Compressor.getPressure()));
				if (Compressor.isRunning()) {
					console.log(c.OTHER, "Stopping Compressor");
					Compressor.stop();
				}
				else {
					console.log(c.OTHER, "Running Compressor");
					Compressor.run();
				}
			}
		}
		else {
			if (Compressor.shouldRun(0)) 
				Compressor.run();
			else 
				Compressor.stop();
			Compressor.buffer.update(Compressor.getPressure());
		}
	}
}
