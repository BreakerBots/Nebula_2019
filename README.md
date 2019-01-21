# Deep Space 2019
The Robot code for all three (Alpha, Bravo, and Charlie) robots. Made for the 2019 FRC game Deep Space.

### Setup Instructions
- Download or Clone this Repository
- Open Eclipse and go to "File > Import"
- Under "Gradle" choose "Existing Gradle Project"
- Choose this repository as the "Project Root"
- Continue with default settings and import the project

### Building/Deploying
In the command line of this repository run `gradlew build` and then `gradlew deploy`.
You can also run `gradlew riolog` to connect to the logging from the robot.

## Features
- Onboard trajectory generation, caching, and following
- Better Code Organization
- New Console Class (console.java) for logging organization
- Custom Command Based System
- Subsystem Manager
- New Drive Code with integration into autonomous and a bezier curve helper
- Odometry/Kinematics, robot position calculating
- Custom TCP Socket to connect to the BreakerBoard

## Code Style
Based roughly off of the [Google Style Guide](http://google.github.io/styleguide/javaguide.html).

#### Naming:
- **Constants:** _ + name

