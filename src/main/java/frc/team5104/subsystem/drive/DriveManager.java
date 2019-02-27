/*BreakerBots Robotics Team 2019*/
package frc.team5104.subsystem.drive;

import frc.team5104.subsystem.BreakerSubsystem;

public class DriveManager extends BreakerSubsystem.Manager {
	public DriveManager() {
		DriveSystems.setup();
	}
	
	public void enabled() { }
	public void update() { }
	public void disabled() { }
}
