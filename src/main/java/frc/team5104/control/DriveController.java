package frc.team5104.control;

import frc.team5104.subsystem.drive.Drive;
import frc.team5104.subsystem.drive.DriveSystems;
import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.subsystem.drive.RobotDriveSignal.DriveUnit;
import frc.team5104.util.Curve;
import frc.team5104.util.CurveInterpolator;
import frc.team5104.util.Deadband;

public class DriveController {
	//Drive
	
	public static final Curve.BezierCurve _driveCurve = new Curve.BezierCurve(.2, 0, .2, 1);
	public static final double _driveCurveChange = 1.0;
	public static final double _turnCurveSpeedAdjust = 0.5;
	
	public static final CurveInterpolator vTeleopLeftSpeed  = new CurveInterpolator(_driveCurveChange, _driveCurve);
	public static final CurveInterpolator vTeleopRightSpeed = new CurveInterpolator(_driveCurveChange, _driveCurve);
	
	public static void handle() {
		//Get inputs
		double turn = Controls.Drive._turn.getAxis();
		double forward = Controls.Drive._forward.getAxis() - Controls.Drive._reverse.getAxis();

		//Apply controller deadbands
		turn = -Deadband.get(turn,  0.1);
		forward = Deadband.get(forward, 0.01);
		
		//Apply bezier curve
		double x1 = (1 - Math.abs(forward)) * (1 - _turnCurveSpeedAdjust) + _turnCurveSpeedAdjust;
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
		signal = Drive.applyDriveStraight(signal);
		
		//Apply min speed
		signal = Drive.applyMotorMinSpeed(signal);
		
		//Set talon speeds
		Drive.set(signal);
		
		//Shifting
		if (Controls.Drive._shift.getPressed())
			DriveSystems.shifters.toggle();
	}
}
