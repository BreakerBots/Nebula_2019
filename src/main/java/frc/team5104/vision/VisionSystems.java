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
			NetworkTableEntry tx = table.getEntry("tx");
			return tx.getDouble(0.0);
		}

		public static double getY() {
			NetworkTableEntry ty = table.getEntry("ty");
			return ty.getDouble(0.0);
		}

		public static double getA() {
			NetworkTableEntry ta = table.getEntry("ta");
			return ta.getDouble(0.0);
		}

		public static double getS() {
			NetworkTableEntry ts = table.getEntry("ts");
			return ts.getDouble(5104.0);
		}
	}
	
	// Pipelines
	static class pipeline {
		static NetworkTableEntry pl = table.getEntry("pipeline");
		static int pn = (int)(pl.getDouble(0));
		
		public static void set(VisionPipeline p) {
			switch(p) {
			case target:
				pl.setDouble(_VisionConstants._target);
				break;
			case line:
				pl.setDouble(_VisionConstants._line);
				break;
			}
		}
	}
}
