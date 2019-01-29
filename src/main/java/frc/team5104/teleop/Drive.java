package frc.team5104.teleop;

import frc.team5104.main.Devices;
import frc.team5104.main.HMI;
import frc.team5104.main.Robot;
import frc.team5104.subsystem.drive.DriveActions;
import frc.team5104.subsystem.drive.DriveSystems;
import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.subsystem.drive._DriveConstants;
import frc.team5104.subsystem.drive.RobotDriveSignal.DriveUnit;
import frc.team5104.util.Curve;
import frc.team5104.util.CurveInterpolator;
import frc.team5104.util.Deadband;
import frc.team5104.util.Units;
import frc.team5104.util.controller.Control;

public class Drive {
	//Drive
	public static final CurveInterpolator vTeleopLeftSpeed  = new CurveInterpolator(HMI.Drive._driveCurveChange, HMI.Drive._driveCurve);
	public static final CurveInterpolator vTeleopRightSpeed = new CurveInterpolator(HMI.Drive._driveCurveChange, HMI.Drive._driveCurve);
	
	public static void handle() {
		//Get inputs
		double turn = HMI.Drive._turn.getAxis();
		double forward = HMI.Drive._forward.getAxis() - HMI.Drive._reverse.getAxis();

		//Apply controller deadbands
		turn = -Deadband.get(turn,  0.1);
		forward = Deadband.get(forward, 0.01);
		
		//Apply bezier curve
		double x1 = (1 - Math.abs(forward)) * (1 - HMI.Drive._turnCurveSpeedAdjust) + HMI.Drive._turnCurveSpeedAdjust;
		turn = Curve.getBezierCurve(turn, x1, 0.4, 1, 0.2);
		
		//Apply inertia affect
		vTeleopLeftSpeed.setSetpoint(forward - turn);
		vTeleopRightSpeed.setSetpoint(forward + turn);
		RobotDriveSignal signal = new RobotDriveSignal(
			vTeleopLeftSpeed.update(), 
			vTeleopRightSpeed.update(), 
			DriveUnit.percentOutput
		);
		
		//Apply drive straight effects
		signal = DriveActions.applyDriveStraight(signal);
		
		//Apply min speed
		signal = DriveActions.applyMotorMinSpeed(signal);
		
		if (Control.X.getHeld()) {
			signal.leftSpeed = 5;
			signal.rightSpeed = 5;
			signal.unit = DriveUnit.feetPerSecond;
			Robot.csv.update(new String[] { 
	    			""+Units.talonVelToFeetPerSecond(Devices.Drive.L1.getSelectedSensorVelocity(), _DriveConstants._ticksPerRevolution, _DriveConstants._wheelDiameter), 
	    			""+5,
	    			""+Units.talonVelToFeetPerSecond(Devices.Drive.R1.getSelectedSensorVelocity(), _DriveConstants._ticksPerRevolution, _DriveConstants._wheelDiameter), 
	    			""+5
	    		});
		}
		
		//Set talon speeds
		DriveActions.set(signal);
		
		//Shifting
		if (HMI.Drive._shift.getPressed())
			DriveSystems.shifters.toggle();
	}
}
