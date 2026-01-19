// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.security.DrbgParameters.Reseed;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.naming.spi.DirStateFactory.Result;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
import org.photonvision.proto.Photon;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

/** Add your docs here. */
public class PoseEstimatorSubsystem extends SubsystemBase{

    public String cameraName;
    public PhotonPipelineResult data;
    public PhotonTrackedTarget bestTarget;
    public PhotonCamera camera;
    public double yaw;
    public double pitch;
    public double area;
    public static EstimatedRobotPose pose; 
    
    public int AprilTag;
    boolean targetVisible = false;
    public static double targetYaw = 0.0;
    public static double targetRange = 0.0;
    public Rotation3d cameraHeading = new Rotation3d(0, 0, 0);
    public Transform3d Robot_to_cam = new Transform3d( Units.inchesToMeters(10), Units.inchesToMeters(6.5), Units.inchesToMeters(38.5), cameraHeading);
    public static Rotation3d Apriltag1 = new Rotation3d(0, 0, -Math.PI/2);
    public static Rotation3d Apriltag2 = new Rotation3d(0, 0, Math.PI);

    private static final List<Pose3d> fieldAprilTag = Collections.unmodifiableList(List.of(
        new Pose3d(Units.inchesToMeters(168), Units.inchesToMeters(149), Units.inchesToMeters(14.5), Apriltag1),
        new Pose3d(Units.inchesToMeters(292), Units.inchesToMeters(26), Units.inchesToMeters(21), Apriltag2)
    ));
    // public PhotonPoseEstimator photonPoseEstimator;
    static SwerveDrivePoseEstimator poseEstimator =  null;
      
           
    
    
        private double previousPoseTimestamp = 0;
        private List<PhotonTrackedTarget> readTargets;
    
    
     
    
    
        public PoseEstimatorSubsystem(String name) {
            // System.out.println("Swerve Kinematics:" + Robot.swerveTrain.getSwerveKinematics());
            // System.out.println("Current Rotation 2D:" + Robot.swerveTrain.getcurrentRotation2d());
           
    
    
            poseEstimator = new SwerveDrivePoseEstimator
                (Robot.swerveTrain.getSwerveKinematics(), 
                Robot.swerveTrain.getcurrentRotation2d(),
                Robot.swerveTrain.getStates(),  
                Robot.swerveTrain.getintialPose2d());
            cameraName = name;
            camera = new PhotonCamera(cameraName);
            setDefaultCommand(new RunCommand(()-> Robot.camera.updateRobotPose(), this ));
    
        }
    
    
    
        public void setData(){
            
            // Optional<EstimatedRobotPose> estimatedPoseResult = photonPoseEstimator.update(data);
            
            var results = camera.getAllUnreadResults();
            if (!results.isEmpty()) {
                // Camera processed a new frame since last
                // Get the last one in the list.
                var result = results.get(results.size() - 1);
                if (result.hasTargets()) {
                    // At least one AprilTag was seen by the camera
                    for (var target : result.getTargets()) {
                        if ((target.getFiducialId() > 5 && target.getFiducialId() < 12) || (target.getFiducialId() > 16 && target.getFiducialId() < 23)) {
                            // Found Tag 7, record its information
                            targetYaw = target.getYaw();
                            // osition = 
                            targetRange =
                                    PhotonUtils.calculateDistanceToTargetMeters(
                                            Units.inchesToMeters(10), // Measured with a tape measure, or in CAD.
                                            0.17, // From 2024 game manual for ID 7
                                            Units.degreesToRadians(90), // Measured with a protractor, or in CAD.
                                            Units.degreesToRadians(target.getPitch()));
    
                            targetVisible = true;
                            System.out.println("Current yaw: " + target.getYaw());
                        }
                    }
                }
            }
        
            SmartDashboard.putNumber("Target Yaw", targetYaw);
            SmartDashboard.putNumber("Target Range", targetRange);
    
      
        }
    
        public void updateRobotPose(){
            var aprilTagResult  = camera.getLatestResult();
            double currentTimeStamp = aprilTagResult.getTimestampSeconds();
            if (aprilTagResult.hasTargets() && currentTimeStamp != previousPoseTimestamp){
    
                readTargets = aprilTagResult.getTargets();
                var bestTarget = aprilTagResult.getBestTarget();
                int bestTargetID = bestTarget.getFiducialId();
                if (bestTarget.getPoseAmbiguity() <= 0.2 && bestTargetID >= 0 && bestTargetID > fieldAprilTag.size()){
                    var targetPose = fieldAprilTag.get(bestTargetID);
                    Transform3d camTofieldAprilTag = bestTarget.getBestCameraToTarget();
                    Pose3d camPose3d  = targetPose.transformBy(camTofieldAprilTag.inverse());
    
                    //find transform of the bots center to camera
                    Pose3d visionMeasurement = camPose3d.transformBy(Robot_to_cam);
                    poseEstimator.addVisionMeasurement(visionMeasurement.toPose2d(), currentTimeStamp);
                }
    
            }
            
    
            // System.out.println("Current Pose: " + getFormattedPose() );
        }
    
    //     public static Pose2d getCurrentPose(){
    //         return poseEstimator.getEstimatedPosition();
    // }

    // public static String getFormattedPose(){
    //     var pose = getCurrentPose();
    //     return String.format("(%.2f, %.2f) %.2f degrees",
    //         pose.getX(),
    //         pose.getY(),
    //         pose.getRotation());
    // }

    public static double getTargetYaw(){

        return targetYaw;
    }

    public static double getTargetRange(){
        return targetRange;
    }
    

    
}