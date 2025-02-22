// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

/** Add your docs here. */
public class Camera extends SubsystemBase{

    public String cameraName;
    public PhotonPipelineResult data;
    public PhotonTrackedTarget bestTarget;
    public PhotonCamera camera;

    public Camera(String name) {

        cameraName = name;
        camera = new PhotonCamera(cameraName);
        setDefaultCommand(new RunCommand(()-> Robot.camera.setData(), this ));

    }

    public void setData(){

        
        data = camera.getLatestResult();
        if (data.hasTargets() == true){

            List <PhotonTrackedTarget> targets = data.getTargets();
            PhotonTrackedTarget bestTarget = data.getBestTarget();
        
            double yaw = bestTarget.getYaw();
            double pitch = bestTarget.getPitch();
            double area = bestTarget.getArea();
            Transform3d pose = bestTarget.getBestCameraToTarget();
            List<TargetCorner> corners = bestTarget.getDetectedCorners();
            int AprilTag = bestTarget.fiducialId;
    
            
            System.out.println("Yaw:" + yaw);
            // System.out.println("Pitch:" + pitch);
            // System.out.println("Area:" + area);
            System.out.println("April Tag:"+ AprilTag);
    
        }
        

    }
}