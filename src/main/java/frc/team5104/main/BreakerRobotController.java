package frc.team5104.main;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.DriverStation;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.jar.Manifest;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;

public class BreakerRobotController extends RobotBase {
	public static enum RobotMode {
		Disabled,
		Auto,
		Teleop,
		Test
	}
	private RobotMode currentMode = RobotMode.Disabled;
	private RobotMode lastMode = RobotMode.Disabled;
	private BreakerRobot robot;
	private final double loopPeriod = 20;

	public BreakerRobotController() {
	    HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_Iterative);
	}
	
	public void startCompetition() {
		console.sets.create("RobotInit");
		console.log(c.MAIN, t.INFO, "Initializing Code");
		
		//Initialize Robot
		robot = new Robot();
		
		//Run Odometry
		Odometry.run();
		
		//Update HAL
		HAL.observeUserProgramStarting();
		
		console.log(c.MAIN, "Devices Created and Seth Proofed");
		console.sets.log(c.MAIN, t.INFO, "RobotInit", "Initialization took ");
		
		//Main Loop
		while (true) {
			double st = Timer.getFPGATimestamp();
			
			loop();
			
			try {
				Thread.sleep(Math.round(loopPeriod - (Timer.getFPGATimestamp() - st)));
			} catch (Exception e) { console.error(e); }
		}
	}

	//Main Loop
	private void loop() {
		currentMode = 
				isDisabled()   ? RobotMode.Disabled : (
				isAutonomous() ? RobotMode.Auto 	   : (
				isTest()	   ? RobotMode.Test     : 
								 RobotMode.Teleop   ));
		
		switch(currentMode) {
			case Auto: {
				if (lastMode != currentMode)
					robot.autoEnabled();
					
				robot.autoLoop();
				HAL.observeUserProgramAutonomous();
				break;
			}
			case Teleop: {
				if (lastMode != currentMode)
					robot.teleopEnabled();
				
				robot.teleopLoop();
				HAL.observeUserProgramTeleop();
				break;
			}
			case Test: {
				if (lastMode != currentMode)
					robot.testEnabled();
				
				robot.testLoop();
				HAL.observeUserProgramTest();
				break;
			}
			case Disabled: {
				if (lastMode != currentMode)
					switch (lastMode) {
						case Auto: 	 { robot.autoDisabled(); break; }
						case Teleop: { robot.teleopDisabled(); break; }
						case Test: 	 { robot.testDisabled(); break; }
						default: break;
					}
				
				HAL.observeUserProgramDisabled();
				break;
			}
			default: break;
		}
		
		if (lastMode != currentMode) {
			if (currentMode == RobotMode.Disabled) {
				robot.mainDisabled();
				BreakerRobot.enabled = false;
			}
			else if (lastMode == RobotMode.Disabled) {
				robot.mainEnabled();
				BreakerRobot.enabled = true;
			}
			LiveWindow.setEnabled(currentMode == RobotMode.Disabled);
			BreakerRobot.mode = currentMode;
			lastMode = currentMode;
		}
		
		robot.mainLoop();
		LiveWindow.updateValues();
	}
	
	/**
	 * The Main Robot Interface. Called by this, Breaker Robot Controller
	 * <br>Override these methods to run code
	 * <br>Functions Call Order:
	 * <br> - All Enable/Disable Functions are called before the corresponding loop function
	 * <br> - Main Functions are called last (teleop, test, auto are before)
	 */
	public static abstract class BreakerRobot {
		public static boolean enabled;
		public static RobotMode mode;
		
		public void mainLoop() { }
		public void mainEnabled() { }
		public void mainDisabled() { }
		public void teleopLoop() { }
		public void teleopEnabled() { }
		public void teleopDisabled() { }
		public void autoLoop() { }
		public void autoEnabled() { }
		public void autoDisabled() { }
		public void testLoop() { }
		public void testEnabled() { }
		public void testDisabled() { }
	}

	public static void main(String... args) {
    if (!HAL.initialize(500, 0)) {
      throw new IllegalStateException("Failed to initialize. Terminating");
    }

    // Call a CameraServer JNI function to force OpenCV native library loading
    // Needed because all the OpenCV JNI functions don't have built in loading
    CameraServerJNI.enumerateSinks();

    HAL.report(tResourceType.kResourceType_Language, tInstances.kLanguage_Java);

    String robotName = "";
    if (args.length > 0) {
      robotName = args[0];
    } else {
      Enumeration<URL> resources = null;
      try {
        resources = Thread.currentThread()
            .getContextClassLoader().getResources("META-INF/MANIFEST.MF");
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      while (resources != null && resources.hasMoreElements()) {
        try {
          Manifest manifest = new Manifest(resources.nextElement().openStream());
          robotName = manifest.getMainAttributes().getValue("Robot-Class");
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    }

    System.out.println("Launch");

    BreakerRobotController robot = new BreakerRobotController();

    try {
      final File file = new File("/tmp/frc_versions/FRC_Lib_Version.ini");

      if (file.exists()) {
        file.delete();
      }

      file.createNewFile();

      try (OutputStream output = Files.newOutputStream(file.toPath())) {
        output.write("Java ".getBytes());
        output.write(WPILibVersion.Version.getBytes());
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    }

    boolean errorOnExit = false;
    try {
      robot.startCompetition();
    } catch (Throwable throwable) {
      Throwable cause = throwable.getCause();
      if (cause != null) {
        throwable = cause;
      }
      DriverStation.reportError("Unhandled exception: " + throwable.toString(),
          throwable.getStackTrace());
      errorOnExit = true;
    } finally {
      // startCompetition never returns unless exception occurs....
      DriverStation.reportWarning("Robots should not quit, but yours did!", false);
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
}