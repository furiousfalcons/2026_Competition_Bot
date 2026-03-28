// package frc.robot.subsystems;

// import com.revrobotics.spark.SparkBase.PersistMode;
// import com.revrobotics.spark.SparkBase.ResetMode;
// import com.revrobotics.spark.SparkLowLevel;
// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
// import com.revrobotics.spark.config.SparkMaxConfig;

// import edu.wpi.first.math.MathUtil;
// import edu.wpi.first.math.controller.PIDController;

// import org.photonvision.PhotonCamera;
// import org.photonvision.targeting.PhotonPipelineResult;

// import com.revrobotics.RelativeEncoder;

// import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants.SubsystemConstants;

// public class HoodSubsystem extends SubsystemBase {
//     SparkMax hoodMotor;
//     private final PIDController pidController;
//     private double currentAngle;
//     PhotonCamera hoodCamera;
//     PhotonPipelineResult result;
//     RelativeEncoder hoodEncoder;
//     //

//     public HoodSubsystem(){
//         hoodMotor = new SparkMax(SubsystemConstants.hoodID, SparkLowLevel.MotorType.kBrushless);
//         hoodEncoder = hoodMotor.getEncoder();

//         pidController = new PIDController(0.11, 0.0, 0.0);

//         hoodCamera = new PhotonCamera("HoodCamera");


//         // not sure why we need this, but it was in last year's code ¯\(°_o)/¯
//         SparkMaxConfig config = new SparkMaxConfig();
//         config.idleMode(IdleMode.kBrake);
//         config.inverted(true); // may or may not need this depending on which direction is defined as positive
//         config.smartCurrentLimit(40);
//         hoodMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
//     }

//     public void periodic(){}    

//     public void hoodAutoAlign(){
//         result = hoodCamera.getLatestResult(); 
//         double hoodAngle = hoodEncoder.getPosition();

//         if (result.hasTargets()) {
//             currentAngle = result.getBestTarget().getPitch();
//             if (Math.abs(currentAngle*42/360 + hoodAngle) > 4){ // limits so it doesn't break turret
//                 hoodMotor.set(0);
//                 return;
//             }
//         } else {
//             hoodMotor.set(0);
//             return;
//         }
        
//         double output = pidController.calculate(currentAngle, 0);
//         //output = MathUtil.clamp(output, -0.5, 0.5);
//         hoodMotor.set(output);

//     }

//     public void hoodUp(){
//         double angle = hoodEncoder.getPosition();

//         if (Math.abs(angle) > 0){
//             hoodMotor.set(SubsystemConstants.hoodSpeed);
//         }
//         else{
//             hoodMotor.set(0);
//         }
//     }

//     public void hoodDown(){
//         double angle = hoodEncoder.getPosition();

//         if (Math.abs(angle) < 4){
//             hoodMotor.set(-1*SubsystemConstants.hoodSpeed); //idk which of these needs to have the -1. tbd!
//         }
//         else{
//             hoodMotor.set(0);
//         }
//     }

//     public void hoodStop(){
//         hoodMotor.set(0);
//     }

// }
