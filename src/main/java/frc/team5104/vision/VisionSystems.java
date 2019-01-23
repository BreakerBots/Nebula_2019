/*BreakerBots Robotics Team 2019*/
package frc.team5104.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team5104.vision.VisionManager.VisionPipeline;

public class VisionSystems {
	// Variables
	static NetworkTable table;
	
	public static void init() {
		table = NetworkTableInstance.getDefault().getTable("limelight");
	}
	
	// Limelight
	static class limelight {
		public static double getX() {
			NetworkTableEntry tx = networkTable.getEntry("tx");
			return tx.getDouble(0.0);
		}

		public static double getY() {
			NetworkTableEntry ty = networkTable.getEntry("ty");
			return ty.getDouble(0.0);
		}

		public static double getA() {
			NetworkTableEntry ta = networkTable.getEntry("ta");
			return ta.getDouble(0.0);
		}

		public static double getS() {
			NetworkTableEntry ts = networkTable.getEntry("ts");
			return ts.getDouble(5104.0);
		}
	}
	
	// Pipelines
	static class pipeline {
//		static int pn = (int)(pl.getDouble(0));
		
		public static void set(VisionPipeline p) {
			switch(p) {
			case target:
				networkTable.setEntry("pipeline", (double)(_VisionConstants._target));
				break;
			case line:
				networkTable.setEntry("pipeline", (double)(_VisionConstants._line));
				break;
			}
		}
	}
	
	// Network Table
	static class networkTable {
		public static NetworkTableEntry getEntry(String key) {
			return table.getEntry(key);
		}
		
		public static void setEntry(String key, Double entry) {
			NetworkTableEntry nte = table.getEntry(key);
			nte.setDouble(entry);
		}
	}
}
