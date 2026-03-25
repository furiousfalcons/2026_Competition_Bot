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

public class ShooterSubsystem extends SubsystemBase {
    SparkMax shooterMotor;
    SparkMax kickerMotor;
    SparkMax indexerMotor;

    //

    public ShooterSubsystem(){
        shooterMotor = new SparkMax(Constants.SubsystemConstants.shooterID, SparkLowLevel.MotorType.kBrushless);
        kickerMotor = new SparkMax(Constants.SubsystemConstants.kickerID, SparkLowLevel.MotorType.kBrushless);
        indexerMotor = new SparkMax(Constants.SubsystemConstants.indexerID, SparkLowLevel.MotorType.kBrushless);


        // not sure why we need this, but it was in last year's code ¯\(°_o)/¯
        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(IdleMode.kBrake);
        config.inverted(true); // may or may not need this depending on which direction is defined as positive
        config.smartCurrentLimit(40);
        shooterMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        kickerMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        indexerMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
       
    }

    public void periodic(){

    }    

    public void shoot(){

        shooterMotor.set(Constants.SubsystemConstants.shooterSpeed);
        kickerMotor.set(Constants.SubsystemConstants.kickerSpeed);
        indexerMotor.set(Constants.SubsystemConstants.indexerSpeed);

    }


    public void shootStop(){
        shooterMotor.set(0);
        kickerMotor.set(0);
        indexerMotor.set(0);
    }

}
