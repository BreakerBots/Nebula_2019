/*BreakerBots Robotics Team 2019*/
package frc.team5104.util;

/**
 * A closed loop controller that controls the velocity of a motor. 
 * This controller uses PIDF (Proportional, Integral, Derivative, Feed-Forward) to process the input into the desired output.
 * The constant values of P, I, D, and F are tuning values.
 */
public class BreakerVelocityController {
	public double _kP, _kI, _kD, _kF;
	public double err, deriv, integ;
	public double lastPoint, target, tolerance;

	public BreakerVelocityController(double P, double I, double D, double F) {	this(P, I, D, F, 0, .01); }
	public BreakerVelocityController(double P, double I, double D, double F, double tolerance) { this(P, I, D, F, 0, tolerance); }
	public BreakerVelocityController(double P, double I, double D, double F, double target, double tolerance) {
		_kP = P;
		_kI = I;
		_kD = D;
		_kF = F;
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
		return (err * _kP) + (integ * _kI) - (deriv * _kD) + (target * _kF);
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
