package frc.team5104.util;

public class BreakerPIDController {
	public double _kP, _kI, _kD;
	public double err, deriv, integ;
	public double lastPoint, target, tolerance;

	public BreakerPIDController(double P, double I, double D) {	this(P, I, D, 0, .01); }
	public BreakerPIDController(double P, double I, double D, double tolerance) { this(P, I, D, 0, tolerance); }
	public BreakerPIDController(double P, double I, double D, double target, double tolerance) {
		_kP = P;
		_kI = I;
		_kD = D;
		this.target = target;
		this.tolerance = tolerance;
		reset();
	}
	
	public void setTarget(double target) { this.target = target; }
	public void setTolerance(double tolerance) { this.tolerance = tolerance; }
	
	public double update(double current) {
		if (lastPoint != Double.POSITIVE_INFINITY) {
			err = target - current;
			deriv = current - lastPoint;
			integ = integ + err;
		}
		
		lastPoint = current;
		return (err * _kP) + (integ * _kI) - (deriv * _kD);
	}
	
	public boolean onTarget() {
		return Math.abs(target - lastPoint) <= tolerance;
	}
	
	public void reset() {
		err = 0;
		deriv = 0;
		integ = 0;
		lastPoint = Double.POSITIVE_INFINITY;
	}
}
