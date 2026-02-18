
package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;

public class VisionSubsystem extends SubsystemBase {
    PhotonCamera frontCamera = new PhotonCamera(VisionConstants.USB_CAMERA1_NAME); // Declare the name of the camera used in the pipeline
    PhotonCamera backCamera = new PhotonCamera(VisionConstants.USB_CAMERA2_NAME);

    @Override
    public void periodic() {
    }
        
}