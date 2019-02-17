/*BreakerBots Robotics Team 2019*/
package frc.team5104.main;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.first.cameraserver.CameraServerShared;
import edu.wpi.first.cameraserver.CameraServerSharedStore;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import frc.team5104.util.console;

public abstract class BreakerRobotControllerBase implements AutoCloseable {
	public static final long MAIN_THREAD_ID = Thread.currentThread().getId();

	protected final DriverStation m_ds;

	public static void main(String... args) {
		if (!HAL.initialize(500, 0))
			throw new IllegalStateException("Failed to initialize. Exploding");

		CameraServerJNI.enumerateSinks();

		HAL.report(tResourceType.kResourceType_Language, tInstances.kLanguage_Java);

		console.log("BreakerBots Robotics Team 2019");
		console.log("Running " + _RobotConstants._robotName + " code.");

		@SuppressWarnings("resource")
		BreakerRobotController robot = new BreakerRobotController();

		try {
			final File file = new File("/tmp/frc_versions/FRC_Lib_Version.ini");

			if (file.exists())
				file.delete();

			file.createNewFile();

			try (OutputStream output = Files.newOutputStream(file.toPath())) {
				output.write("Java ".getBytes());
				output.write(WPILibVersion.Version.getBytes());
			}

		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}

		boolean errorOnExit = false;
		try {
			robot.startCompetition();
		} 
		catch (Throwable throwable) {
			Throwable cause = throwable.getCause();
			if (cause != null) {
				throwable = cause;
			}
			DriverStation.reportError("Unhandled exception: " + throwable.toString(),
					throwable.getStackTrace());
			errorOnExit = true;
		} 
		finally {
			// startCompetition never returns unless exception occurs....
			DriverStation.reportWarning("Robots should work, but yours is bad.", false);
			if (errorOnExit) {
				DriverStation.reportError(
						"The startCompetition() method (or methods called by it) should have "
								+ "handled the exception above.", false);
			} else {
				DriverStation.reportError("Unexpected return from startCompetition() method.", false);
			}
		}
		System.exit(1);
	} 
	
	protected BreakerRobotControllerBase() {
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		setupCameraServerShared();
		inst.setNetworkIdentity("Robot");
		inst.startServer("/home/lvuser/networktables.ini");
		m_ds = DriverStation.getInstance();
		inst.getTable("LiveWindow").getSubTable(".status").getEntry("LW Enabled").setBoolean(false);

		LiveWindow.setEnabled(false);
		Shuffleboard.disableActuatorWidgets();
	}
	
	//Extra Functions
	public boolean isDisabled() { return m_ds.isDisabled(); }
	public boolean isEnabled() { return m_ds.isEnabled(); }
	public boolean isAutonomous() { return m_ds.isAutonomous(); }
	public boolean isTest() { return m_ds.isTest(); }
	public boolean isTeleop() { return m_ds.isOperatorControl(); }
	public abstract void startCompetition();
	public void close() throws Exception { } 
	private static void setupCameraServerShared() {
		CameraServerShared shared = new CameraServerShared() {
			public void reportVideoServer(int id) { HAL.report(tResourceType.kResourceType_PCVideoServer, id); }
			public void reportUsbCamera(int id) { HAL.report(tResourceType.kResourceType_UsbCamera, id); }
			public void reportDriverStationError(String error) { DriverStation.reportError(error, true); }
			public void reportAxisCamera(int id) { HAL.report(tResourceType.kResourceType_AxisCamera, id); }
			public Long getRobotMainThreadId() { return MAIN_THREAD_ID; }
		};
		CameraServerSharedStore.setCameraServerShared(shared);
	}
}