/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionSystems {
	//Limelight Getters
	static class limelight {
		private static double getEntry(String value) { return networkTable.getEntry(value).getDouble(5104); }
		public static double getX() { return getEntry("tx"); }
		public static double getY() { return getEntry("ty"); }
		public static double getA() { return getEntry("ta"); }
		public static double getS() { return getEntry("ts"); }
	}
	
	//Network Table
	static NetworkTable table;
	public static void init() { table = NetworkTableInstance.getDefault().getTable("limelight"); }
	static class networkTable {
		public static NetworkTableEntry getEntry(String key) { return table.getEntry(key); }
		public static void setEntry(String key, double entry) { table.getEntry(key).setDouble(entry); }
	}
}
