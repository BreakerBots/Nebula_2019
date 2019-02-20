package frc.team5104.control;

import frc.team5104.control.BreakerMainController.BreakerController;
import frc.team5104.util.Compressor;

class CompressorController extends BreakerController {
	void update() {
		if (_Controls.Main._automaticCompressor) {
			if (Compressor.shouldRun(0)) 
				Compressor.run();
			else 
				Compressor.stop();
			Compressor.buffer.update(Compressor.getPressure());
		}
		else {
			if (_Controls.Main._toggleCompressor.getPressed()) {
				if (Compressor.isRunning()) 
					Compressor.stop();
				else 
					Compressor.run();
			}
		}
	}
}
