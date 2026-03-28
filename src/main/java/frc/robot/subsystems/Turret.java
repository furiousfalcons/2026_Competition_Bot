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

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {
    SparkMax turretMotor;
    private final PIDController pidController;
    private double currentAngle;
    PhotonCamera turretCamera;
    PhotonPipelineResult result;
    RelativeEncoder turretEncoder;
    //

    public Turret(){
        turretMotor = new SparkMax(Constants.SubsystemConstants.turretID, SparkLowLevel.MotorType.kBrushless);
        turretEncoder = turretMotor.getEncoder();

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
        result = turretCamera.getLatestResult();  // ahould we take this out of periodic

    }    

    public void turretAutoAlign(){

        if (result.hasTargets()) {
            currentAngle = result.getBestTarget().getYaw();
            double turretAngle = turretEncoder.getPosition(); // need to make sure directions match
            if (Math.abs(currentAngle*42/360 + turretAngle) > 14){ // limits so it doesn't break turret
                turretMotor.set(0);
                return;
            }
            
        } else {
            turretMotor.set(0);
            return;
        }

        
        double output = pidController.calculate(currentAngle, 0);
        //output = MathUtil.clamp(output, -0.5, 0.5);
        turretMotor.set(output);

    }

    public void turretLeft(){
        double angle = turretEncoder.getPosition();

        if (Math.abs(angle) < 14){
            turretMotor.set(Constants.SubsystemConstants.turretSpeed);
        }
        else{
            turretMotor.set(0);
        }
    }

    public void turretRight(){
        double angle = turretEncoder.getPosition();

        if (Math.abs(angle) < 14){
            turretMotor.set(-1*Constants.SubsystemConstants.turretSpeed); //idk which of these needs to have the -1. tbd!
        }
        else{
            turretMotor.set(0);
        }
    }

    public void turretStop(){
        turretMotor.set(0);
    }

}
