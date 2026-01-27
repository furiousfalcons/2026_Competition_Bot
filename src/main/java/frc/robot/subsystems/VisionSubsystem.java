
package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Constants.VisionConstants;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
    PhotonCamera frontCamera = new PhotonCamera(VisionConstants.USB_CAMERA_NAME); // Declare the name of the camera used in the pipeline
    PhotonCamera backCamera = new PhotonCamera(VisionConstants.USB_CAMERA2_NAME);

    @Override
    public void periodic() {
    }
        
}