package frc.team5104.subsystem.drive;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;

/**
 * Characterizes the drivetrain (measures _kC, _kV, _kA feedforwards)
 * 
 * Intructions:
 *  - Download "data_logger.py" and "data_analyzer.py" from https://github.com/robotpy/robot-characterization
 *  - Run "data_logger.py" while connected to the robot
 *  - Call the update function here in a main loop
 */
public class DriveCharacterization {
	NetworkTableEntry InputTable;
	NetworkTableEntry OutputTable;
	double lastSpeed = 0;
	Number[] data = new Number[9];
	
	public void init() {
		NetworkTableInstance.getDefault().setUpdateRate(0.010);
		InputTable = NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
		OutputTable = NetworkTableInstance.getDefault().getEntry("/robot/telemetry");
	}
	
	public void update() {
		double battery = RobotController.getBatteryVoltage();
		double motorVolts = battery * Math.abs(lastSpeed);

		double speed = InputTable.getDouble(0);
		Drive.set(new RobotDriveSignal(speed, speed));
		lastSpeed = speed;

		data[0] = Timer.getFPGATimestamp();
		data[1] = battery;
		data[2] = speed;
		data[3] = motorVolts;
		data[4] = motorVolts;
		data[5] = DriveSystems.encoders.getLeftPositionFeet();
		data[6] = DriveSystems.encoders.getRightPositionFeet();
		data[7] = DriveSystems.encoders.getLeftVelocityFeet();
		data[8] = DriveSystems.encoders.getRightVelocityFeet();

		OutputTable.setNumberArray(data);
	}
}
