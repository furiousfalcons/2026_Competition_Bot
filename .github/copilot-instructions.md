## Quick orientation

This repository is a WPILib Java (GradleRIO) Command-based robot project (Java 17). Key entry points:

- `src/main/java/frc/robot/Robot.java` — uses Littleton `LoggedRobot` and configures logging (NT4/WPILOG).
- `src/main/java/frc/robot/RobotContainer.java` — central wiring: subsystems, default commands, and trigger bindings.
- `src/main/java/frc/robot/subsystems/DriveSubsystem.java` — swerve drive implementation (MAX Swerve modules, odometry, PathPlanner AutoBuilder).
- `src/main/java/frc/robot/Configs.java` — hardware `SparkMaxConfig` setups for MAXSwerveModule.
- `src/main/java/frc/robot/Constants.java` — all tuning constants and CAN IDs.
- `src/main/deploy/pathplanner/` — PathPlanner config, navgrid, and saved paths used by AutoBuilder.

Build system and environment:

- Gradle wrapper on Windows: use `gradlew.bat` (e.g. `.
  gradlew.bat build`) or `./gradlew` on *nix. Plugin: `edu.wpi.first.GradleRIO` (2026.2.1). Java 17.
- Common tasks: `gradlew build`, `gradlew test`, `gradlew deploy` (deploys to RoboRIO configured by team number / .wpilib preferences). Simulation features are enabled in `build.gradle` (wpi.sim).

Project-specific patterns an AI should follow

- Command-based wiring: prefer creating Commands/Subsystems under `src/main/java/frc/robot/commands` and `.../subsystems`. `RobotContainer` should own bindings and default commands. Example: default swerve drive is set with `new RunCommand(...)` in `RobotContainer`.
- Constants & Configs: hardware constants live in `Constants` (CAN IDs, physical dims). Motor/controller configs live in `Configs` (see `Configs.MAXSwerveModule` static block). When adding hardware, update both files.
- Swerve conventions: module offsets and kinematics are centralized in `Constants.DriveConstants`. Use `DriveSubsystem` helper methods: `drive(...)`, `setX()`, `resetEncoders()`, `getPose()`, and `visionPose(...)`.
- PathPlanner integration: `DriveSubsystem` calls `AutoBuilder.configure(...)` and `RobotContainer` uses `AutoBuilder.buildAutoChooser()`; PathPlanner assets are in `src/main/deploy/pathplanner/`.
- Logging: project uses `org.littletonrobotics.junction` (LoggedRobot, Logger, NT4Publisher, WPILOGWriter). Use `Logger.recordOutput(...)` and `@AutoLogOutput` for values that should appear in logs.

Notable code examples (copyable patterns)

- Set default drive command (in `RobotContainer`):

  new RunCommand(() -> m_robotDrive.drive(-MathUtil.applyDeadband(...), ...), m_robotDrive);

- Configure a MAX Swerve module (in `Configs`):

  drivingConfig.encoder.positionConversionFactor(drivingFactor);
  turningConfig.absoluteEncoder.inverted(true).positionConversionFactor(2*Math.PI);

- Produce an auto chooser (in `RobotContainer`):

  autoChooser = AutoBuilder.buildAutoChooser();
  SmartDashboard.putData("Auto Mode", autoChooser);

What to look at when modifying or adding features

- If adding motors or changing CAN IDs, update `Constants.DriveConstants` and `Configs.MAXSwerveModule` where conversion factors and PID gains live.
- If adding commands that require odometry/pose, use `DriveSubsystem.getPose()` and `resetPose(...)` so AutoBuilder and path following remain compatible.
- Follow existing logging keys (e.g., `Logger.recordOutput("Chassis", ...)`) to keep continuity in AdvantageScope/WPILOG traces.

Developer workflows / commands (Windows PowerShell)

- Build: `.
  gradlew.bat build`
- Run tests: `.
  gradlew.bat test`
- Deploy to RoboRIO: `.
  gradlew.bat deploy` (team number is read from `.wpilib/wpilib_preferences.json` or CLI parameters)
- Simulation: simulation support is enabled in `build.gradle` (see `wpi.sim.addGui()`); use the standard WPILib Gradle simulation tasks from the WPILib extension or `gradlew` tasks the WPILib toolchain exposes.

Files worth reading for more context

- `README.md` — repo description
- `build.gradle` — GradleRIO plugin, deploy config, and simulation options
- `src/main/java/frc/robot/Robot.java` — logging bootstrap and mode handling (REAL vs SIM vs REPLAY)
- `src/main/java/frc/robot/RobotContainer.java` — input bindings and default commands
- `src/main/java/frc/robot/subsystems/DriveSubsystem.java` — swerve control, odometry, PathPlanner wiring
- `src/main/deploy/pathplanner/*` — path and navgrid assets

Limitations / things I could not infer

- How CI (if any) is run — no `.github/workflows` present in repo. If you have a specific CI setup, add a workflow and note it here.
- Team number and local WPILib preferences are environment-specific (`.wpilib/wpilib_preferences.json`) and are not checked in.

If any area is incomplete or you'd like more detail (examples for adding a new subsystem, unit tests, or a standard logging key schema), tell me which part to expand and I will iterate.
