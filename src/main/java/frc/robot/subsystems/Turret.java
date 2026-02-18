package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {
    SparkMax turretMotor;
    private final PIDController pidController;
    private double currentAngle;
    PhotonCamera turretCamera;
    PhotonPipelineResult result;

    public Turret(){
        turretMotor = new SparkMax(7, SparkLowLevel.MotorType.kBrushed);

        pidController = new PIDController(0.11, 0.0, 0.0);

        turretCamera = new PhotonCamera("TurretCamera");


        // not sure why we need this, but it was in last year's code ¯\(°_o)/¯
        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(IdleMode.kBrake);
        config.inverted(true); // may or may not need this depending on which direction is defined as positive
        config.smartCurrentLimit(40);
        turretMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
    }

    public void periodic(){
        result = turretCamera.getLatestResult(); 

    }    

    public void turretAutoAlign(){

        if (result.hasTargets()) {
            currentAngle = result.getBestTarget().getYaw();
        } else {
            turretMotor.set(0);
            return;
        }
        
        double output = pidController.calculate(currentAngle, 0);
        output = MathUtil.clamp(output, -0.5, 0.5);
        turretMotor.set(output);

    }

}
