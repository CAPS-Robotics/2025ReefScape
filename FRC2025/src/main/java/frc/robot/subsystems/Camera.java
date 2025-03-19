// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

/** Add your docs here. */
public class Camera extends SubsystemBase{

    public String cameraName;
    public PhotonPipelineResult data;
    public PhotonTrackedTarget bestTarget;
    public PhotonCamera camera;
    public double yaw;
    public double pitch;
    public double area;
    public Transform3d pose; 
    public int AprilTag;
    boolean targetVisible = false;
    public static double targetYaw = 0.0;
    public static double targetRange = 0.0;


    public Camera(String name) {

        cameraName = name;
        camera = new PhotonCamera(cameraName);
        setDefaultCommand(new RunCommand(()-> Robot.camera.setData(), this ));

    }

    public void setData(){
        
        
        var results = camera.getAllUnreadResults();
        if (!results.isEmpty()) {
            // Camera processed a new frame since last
            // Get the last one in the list.
            var result = results.get(results.size() - 1);
            if (result.hasTargets()) {
                // At least one AprilTag was seen by the camera
                for (var target : result.getTargets()) {
                    if (target.getFiducialId() == 7 ) {
                        // Found Tag 7, record its information
                        targetYaw = target.getYaw();
                        targetRange =
                                PhotonUtils.calculateDistanceToTargetMeters(
                                        Units.inchesToMeters(10), // Measured with a tape measure, or in CAD.
                                        0.17, // From 2024 game manual for ID 7
                                        Units.degreesToRadians(90), // Measured with a protractor, or in CAD.
                                        Units.degreesToRadians(target.getPitch()));

                        targetVisible = true;
                    }
                }
            }
        }
    
        SmartDashboard.putNumber("Target Yaw", targetYaw);
        SmartDashboard.putNumber("Target Range", targetRange);

  
    }

    public static double getTargetYaw(){

        return targetYaw;
    }

    public static double getTargetRange(){
        return targetRange;
    }
    

    
}