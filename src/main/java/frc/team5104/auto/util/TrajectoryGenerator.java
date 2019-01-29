/*BreakerBots Robotics Team 2019*/
package frc.team5104.auto.util;

import frc.team5104.util.BreakerMath;
import frc.team5104.util.console;

/**
 * <h1>Trajectory Generator</h1>
 * Baking fresh trajectories since 20-never...
 */
public class TrajectoryGenerator {
	//Speed Curve Strategies
	public static class SpeedProfile {
		private final String value_;
		private SpeedProfile(String value) { value_ = value; }
		public String toString() { return value_; }
	}
	public static final SpeedProfile StepProfile = new SpeedProfile("StepStrategy");
	public static final SpeedProfile TrapezoidalProfile = new SpeedProfile("TrapezoidalStrategy");
	public static final SpeedProfile SCurveProfile = new SpeedProfile("SCurvesStrategy");
	public static final SpeedProfile AutomaticProfile = new SpeedProfile("AutomaticStrategy");

	//Integration Methods
	private static class IntegrationMethod { 
		private final String value_;
		private IntegrationMethod(String value) { value_ = value; }
		public String toString() { return value_; }
	}
	private static final IntegrationMethod RectangularIntegration = new IntegrationMethod("RectangularIntegration");
	private static final IntegrationMethod TrapezoidalIntegration = new IntegrationMethod("TrapezoidalIntegration");

	/**
	 * Generates a trajectory that hits all the waypoints with the specified configuration
	 * @param waypoints The waypoints for the trajectory to hit
	 * @param config The configuration for the trajectory/generator
	 */
	public static Trajectory generate(Waypoint[] waypoints, double max_vel, double max_acc, double max_jerk, double dt) {
		if (waypoints.length < 2) {
			console.error("Not Enough Waypoints!");
			return null;
		}

		//Creates splines between each pair of waypoints
		Spline[] splines = new Spline[waypoints.length - 1];
		double[] spline_lengths = new double[splines.length];
		double total_distance = 0;
		for (int i = 0; i < splines.length; ++i) {
			splines[i] = new Spline();
			if (!Spline.reticulateSplines(waypoints[i], waypoints[i + 1], splines[i], Spline.CubicHermite)) {
				console.error("Invalid Path!");
				return null;
			}
			spline_lengths[i] = splines[i].calculateLength();
			total_distance += spline_lengths[i];
		}

		//Combines all the splines and smoothes them out
		Trajectory traj = generateFullTrajectory(max_vel, max_acc, max_jerk, dt, TrajectoryGenerator.SCurveProfile, 0.0, waypoints[0].theta, total_distance, 0.0, waypoints[0].theta);

		//Assings headings to segments
		int cur_spline = 0;
		double cur_spline_start_pos = 0;
		double length_of_splines_finished = 0;
		for (int i = 0; i < traj.length(); ++i) {
			double cur_pos = traj.get(i).position;

			boolean found_spline = false;
			while (!found_spline) {
				double cur_pos_relative = cur_pos - cur_spline_start_pos;
				if (cur_pos_relative <= spline_lengths[cur_spline]) {
					double percentage = splines[cur_spline].getPercentageForDistance(
									cur_pos_relative);
					traj.get(i).heading = splines[cur_spline].angleAt(percentage);
					double[] coords = splines[cur_spline].getXandY(percentage);
					traj.get(i).x = coords[0];
					traj.get(i).y = coords[1];
					found_spline = true;
				} else if (cur_spline < splines.length - 1) {
					length_of_splines_finished += spline_lengths[cur_spline];
					cur_spline_start_pos = length_of_splines_finished;
					++cur_spline;
				} else {
					traj.get(i).heading = splines[splines.length - 1].angleAt(1.0);
					double[] coords = splines[splines.length - 1].getXandY(1.0);
					traj.get(i).x = coords[0];
					traj.get(i).y = coords[1];
					found_spline = true;
				}
			}
		}

		return traj;
	}
	
	/**
	 * 
	 * @param config
	 * @param strategy
	 * @param start_vel
	 * @param start_heading
	 * @param goal_pos
	 * @param goal_vel
	 * @param goal_heading
	 * @return
	 */
	private static Trajectory generateFullTrajectory(double max_vel, double max_acc, double max_jerk, double dt, SpeedProfile strategy, double start_vel, double start_heading, double goal_pos, double goal_vel, double goal_heading) {
		// Choose an automatic strategy.
		if (strategy == AutomaticProfile) {
			if (start_vel == goal_vel && start_vel == max_vel)
				strategy = StepProfile;
			else if (start_vel == goal_vel && start_vel == 0)
				strategy = SCurveProfile;
			else
				strategy = TrapezoidalProfile;
		}

		Trajectory traj;
		if (strategy == StepProfile) {
			double impulse = (goal_pos / max_vel) / dt;

			// Round down, meaning we may undershoot by less than max_vel*dt.
			// This is due to discretization and avoids a strange final
			// velocity.
			int time = (int) (Math.floor(impulse));
			traj = secondOrderFilter(1, 1, dt, max_vel, max_vel, impulse, time, TrapezoidalIntegration);

		} else if (strategy == TrapezoidalProfile) {
			// How fast can we go given maximum acceleration and deceleration?
			double start_discount = .5 * start_vel * start_vel / max_acc;
			double end_discount = .5 * goal_vel * goal_vel / max_acc;

			double adjusted_max_vel = BreakerMath.min(max_vel,
							Math.sqrt(max_acc * goal_pos - start_discount
											- end_discount));
			double t_rampup = (adjusted_max_vel - start_vel) / max_acc;
			double x_rampup = start_vel * t_rampup + .5 * max_acc
							* t_rampup * t_rampup;
			double t_rampdown = (adjusted_max_vel - goal_vel) / max_acc;
			double x_rampdown = adjusted_max_vel * t_rampdown - .5
							* max_acc * t_rampdown * t_rampdown;
			double x_cruise = goal_pos - x_rampdown - x_rampup;

			// The +.5 is to round to nearest
			int time = (int) ((t_rampup + t_rampdown + x_cruise
							/ adjusted_max_vel) / dt + .5);

			// Compute the length of the linear filters and impulse.
			int f1_length = (int) Math.ceil((adjusted_max_vel
							/ max_acc) / dt);
			double impulse = (goal_pos / adjusted_max_vel) / dt
							- start_vel / max_acc / dt
							+ start_discount + end_discount;
			traj = secondOrderFilter(f1_length, 1, dt, start_vel,
							adjusted_max_vel, impulse, time, TrapezoidalIntegration);

		} else if (strategy == SCurveProfile) {
			// How fast can we go given maximum acceleration and deceleration?
			double adjusted_max_vel = Math.min(max_vel,
							(-max_acc * max_acc + Math.sqrt(max_acc
											* max_acc * max_acc * max_acc
											+ 4 * max_jerk * max_jerk * max_acc
											* goal_pos)) / (2 * max_jerk));

			// Compute the length of the linear filters and impulse.
			int f1_length = (int) Math.ceil((adjusted_max_vel
							/ max_acc) / dt);
			int f2_length = (int) Math.ceil((max_acc
							/ max_jerk) / dt);
			double impulse = (goal_pos / adjusted_max_vel) / dt;
			int time = (int) (Math.ceil(f1_length + f2_length + impulse));
			traj = secondOrderFilter(f1_length, f2_length, dt, 0,
							adjusted_max_vel, impulse, time, TrapezoidalIntegration);

		} else {
			return null;
		}

		// Now assign headings by interpolating along the path.
		// Don't do any wrapping because we don't know units.
		double total_heading_change = goal_heading - start_heading;
		for (int i = 0; i < traj.length(); ++i) {
			traj.segments_[i].heading = start_heading + total_heading_change
							* (traj.segments_[i].position)
							/ traj.segments_[traj.length() - 1].position;
		}

		return traj;
	}

	private static Trajectory secondOrderFilter(int f1_length, int f2_length, double dt, double start_vel, double max_vel, double total_impulse, int length, IntegrationMethod integration) {
		if (length <= 0)
			return null;
		Trajectory traj = new Trajectory(length);

		TrajectorySegment last = new TrajectorySegment();
		last.position = 0;
		last.velocity = start_vel;
		last.acceleration = 0;
		last.jerk = 0;
		last.deltaTime = dt;

		// f2 is the average of the last f2_length samples from f1, so while we
		// can recursively compute f2's sum, we need to keep a buffer for f1.
		double[] f1 = new double[length];
		f1[0] = (start_vel / max_vel) * f1_length;
		double f2;
		for (int i = 0; i < length; ++i) {
			// Apply input
			double input = Math.min(total_impulse, 1);
			if (input < 1) {
				// The impulse is over, so decelerate
				input -= 1;
				total_impulse = 0;
			} else {
				total_impulse -= input;
			}

			// Filter through F1
			double f1_last;
			if (i > 0) {
				f1_last = f1[i - 1];
			} else {
				f1_last = f1[0];
			}
			f1[i] = Math.max(0.0, Math.min(f1_length, f1_last + input));

			f2 = 0;
			// Filter through F2
			for (int j = 0; j < f2_length; ++j) {
				if (i - j < 0)
					break;

				f2 += f1[i - j];
			}
			f2 = f2 / f1_length;

			// Velocity is the normalized sum of f2 * the max velocity
			traj.segments_[i].velocity = f2 / f2_length * max_vel;

			if (integration == RectangularIntegration) {
				traj.segments_[i].position = traj.segments_[i].velocity * dt + last.position;
			} 
			else if (integration == TrapezoidalIntegration) {
				traj.segments_[i].position = (last.velocity
								+ traj.segments_[i].velocity) / 2.0 * dt + last.position;
			}
			traj.segments_[i].x = traj.segments_[i].position;
			traj.segments_[i].y = 0;

			// Acceleration and jerk are the differences in velocity and
			// acceleration, respectively.
			traj.segments_[i].acceleration = (traj.segments_[i].velocity - last.velocity) / dt;
			traj.segments_[i].jerk = (traj.segments_[i].acceleration - last.acceleration) / dt;
			traj.segments_[i].deltaTime = dt;

			last = traj.segments_[i];
		}

		return traj;
	}
}
