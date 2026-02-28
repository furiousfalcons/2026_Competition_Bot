package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  private final SparkMax intakeOutputMotor;
  private final SparkMax intakeDownMotor;
  private final double intakeSpeed = 0.5;

  public Intake() {
    intakeDownMotor = new SparkMax(
      Constants.INTAKE_DOWN_MOTOR_ID,
      MotorType.kBrushless
);

    intakeOutputMotor = new SparkMax(
        Constants.INTAKEOUTPUT_MOTOR_ID,
        MotorType.kBrushless
    );

    intakeOutputMotor.set(0.0);
    intakeDownMotor.set(0.0);
  }

  public void runIntake() {
    intakeOutputMotor.set(intakeSpeed);
  }

  public void stopIntake() {
    intakeOutputMotor.set(0.0);
  }
  
  public void intakeDown() {
    intakeDownMotor.set(-0.4);
  }
  
  public void stopIntakeDown() {
    intakeDownMotor.set(0.0);
  }

}
