// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import com.pathplanner.lib.util.*;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.Kinematics;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Swerve.SwerveModule;


/** Add your docs here. */
public class SwerveDriveTrainSubsystem extends SubsystemBase{


    SwerveModule frontRightModule = new SwerveModule(Constants.kFrontRightDrive, Constants.kFrontRightSteering, Constants.kFrontRightEncoder, Constants.kFrontRightEncoderOffset, true, false);
    SwerveModule frontLeftModule = new SwerveModule(Constants.kFrontLeftDrive, Constants.kFrontLeftSteering,Constants.kFrontLeftEncoder, Constants.kFrontLeftEncoderOffset);
    SwerveModule backRightModule = new SwerveModule(Constants.kBackRightDrive, Constants.kBackRightSteering,Constants.kBackRightEncoder, Constants.kBackRightEncoderOffset);
    SwerveModule backLeftModule = new SwerveModule(Constants.kBackLeftDrive, Constants.kBackLeftSteering, Constants.kBackLeftEncoder, Constants.kBackLeftEncoderOffset, true, true);

    
    SwerveModuleState states[];
    SwerveModulePosition[] position = {frontLeftModule.modulePosition.copy(), frontRightModule.modulePosition.copy(), backLeftModule.modulePosition.copy(), backRightModule.modulePosition.copy()};


    AHRS Navx = new AHRS(NavXComType.kMXP_SPI);
    // Uncomment to convert from double to Rotations2D
    Rotation2d Yaw;
    PIDController ResetToFusedHeading = new PIDController(0, 0, 0);

    Pose2d robotPose2d = new Pose2d();
    

    Translation2d frontLeft = new Translation2d((Constants.chasisWidth/2), (Constants.chasisLength/2));
    Translation2d frontRight = new Translation2d((Constants.chasisWidth/2), (-Constants.chasisLength/2));
    Translation2d backLeft = new Translation2d((-Constants.chasisWidth/2), (Constants.chasisLength/2));
    Translation2d backRight = new Translation2d((-Constants.chasisWidth/2), (-Constants.chasisLength/2));

    Rotation2d FLCurrentAngle;
    Rotation2d FRCurrentAngle;
    Rotation2d BLCurrentAngle;
    Rotation2d BRCurrentAngle;


    SwerveDriveKinematics kinematics = new SwerveDriveKinematics(frontLeft, frontRight, backLeft, backRight);

    SwerveDrivePoseEstimator swerveDrivePoseEstimator; 
    
    double distanceAprilTag;

    

    static ChassisSpeeds chassisSpeeds = new ChassisSpeeds(); 
    
    
    
        public SwerveDriveTrainSubsystem(){   
        // Calibrate
        // Uncomment if need to zero yaw when robot init
        // Navx.zeroYaw();
        
        
            
    
        //PathPlanner Init
        RobotConfig config = null;
        try{
          config = RobotConfig.fromGUISettings();
        } catch (Exception e) {
          // Handle exception as needed
          e.printStackTrace();
        }
    
        // Configure AutoBuilder last
            // AutoBuilder.configure(
            //     this::getPose, // Robot pose supplier
            //     this::resetPose2d, // Method to reset odometry (will be called if your auto has a starting pose)
            //     this::getcChassisSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
            //     (speeds, feedforwards) -> setSpeed(speeds), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
            //     new PPHolonomicDriveController( // PPHolonomicController is the built in path following controller for holonomic drive trains
            //             new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
            //             new PIDConstants(5.0, 0.0, 0.0) // Rotation PID constants
            //     ),
            //     config, 
            //      // The robot configuration
            //     () -> {
            //       // Boolean supplier that controls when the path will be mirrored for the red alliance
            //       // This will flip the path being followed to the red side of the field.
            //       // THE ORIGIN WILL REMAIN ON THE BLUE SIDE
    
            //       var alliance = DriverStation.getAlliance();
            //       if (alliance.isPresent()) {
            //         return alliance.get() == DriverStation.Alliance.Red;
            //       }
            //       return false;
            //     },
            //     this // Reference to this subsystem to set requirements
            // );
            
           
           
    
            double moduleStateLog[]=
            {
                frontLeftModule.getModuleState().angle.getRadians(), // Front Left Module angle
                frontLeftModule.getModuleState().speedMetersPerSecond, // Front Left Module velocity -> m/s
                frontRightModule.getModuleState().angle.getRadians(), // Front Right Module angle 
                frontRightModule.getModuleState().speedMetersPerSecond, // Front Right Module Velocity -> m/s
                backLeftModule.getModuleState().angle.getRadians(), // Back Left Module angle
                backLeftModule.getModuleState().speedMetersPerSecond, // Back Left Module Velocity -> m/s 
                backRightModule.getModuleState().angle.getRadians(), // Back Right Module angle 
                backRightModule.getModuleState().speedMetersPerSecond  // Back Right Module velocity -> m/s
    
            };
            // System.out.println("Front left module angle: "+ moduleStateLog[0]);
            // System.out.println("Front right module angle: "+ moduleStateLog[2]);
            // System.out.println("Back left module angle: "+ moduleStateLog[4]);
            // System.out.println("Back right module angle: "+ moduleStateLog[6]);
    
            // System.out.println();
    
            // System.out.println("Front left module speed: "+ moduleStateLog[1]);
            // System.out.println("Front right module speed: "+ moduleStateLog[3]);
            // System.out.println("Back left module speed: "+ moduleStateLog[5]);
            // System.out.println("Back right module speed: "+ moduleStateLog[7]);
    
    
           // SmartDashboard.putNumberArray("Setting Initial State Value:",moduleStateLog);
             
            setDefaultCommand(new RunCommand(()-> Robot.swerveTrain.driveSwerve(Robot.io.driveController), this ));
            
    
        }
    
        // public double getCurrentYaw(){
    
        //     Yaw = Navx.getYaw();
        //     return Yaw;
    
        // }
    
        
        public SwerveModulePosition[]  updatePositions(){
            position[0] = frontLeftModule.getModulePosition();
            position[1] = frontRightModule.getModulePosition();
            position[2] = backLeftModule.getModulePosition();
            position[3] = backRightModule.getModulePosition();
    
            return position;
        }
    
        public void driveSwerve(Joystick driveController){
                            // System.out.println("Swerve Drive");

                            Yaw = Navx.getRotation2d();
                
                            
                    
                            // FLCurrentAngle = new Rotation2d(frontLeftModule.encoder.get()/1.0*2*Math.PI);
                            // FRCurrentAngle = new Rotation2d(frontRightModule.encoder.get()/1.0*2*Math.PI);
                            // BLCurrentAngle = new Rotation2d(backLeftModule.encoder.get()/1.0*2*Math.PI);
                            // BRCurrentAngle = new Rotation2d(backRightModule.encoder.get()/1.0*2*Math.PI);
                    
                            //Optimization
                            // frontLeftModule.moduleState.optimize(FLCurrentAngle);
                            // frontRightModule.moduleState.optimize(FRCurrentAngle);
                            // backLeftModule.moduleState.optimize(BLCurrentAngle);
                            // backRightModule.moduleState.optimize(BRCurrentAngle);
                    
                    
                    
                            double velocityX = -1 * driveController.getX();
                            double velocityY = 1 * driveController.getY();
                            
                            double omega = 1 * driveController.getZ();
                    // if (driveController.getLeftTriggerAxis() > 0.1){
                    //     omega = -1 * driveController.getLeftTriggerAxis();
                    // }else if(driveController.getRightTriggerAxis() > 0.1){
                    //     omega = driveController.getRightTriggerAxis();
                    // }
                    
            // System.out.println("Converted Yaw" + convertedYaw + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            if(Math.abs(velocityX) < 0.1) velocityX = 0;
            if(Math.abs(velocityY) < 0.1) velocityY = 0;
            if(Math.abs(omega) < 0.1) omega = 0;
    
       
    
            System.out.println("Velocity X: " + velocityX);
            System.out.println("Velocity Y: "+ velocityY);
            System.out.println("Omega: "+omega);
            // System.out.println("Yaw2"+Yaw2+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

    
            chassisSpeeds = new ChassisSpeeds();
            chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(velocityX,velocityY, omega, Yaw
            );
    
            setSpeed(chassisSpeeds);
    
            
            position[0] = frontLeftModule.modulePosition;
            position[1] = frontRightModule.modulePosition;
            position[2] = backLeftModule.modulePosition;
            position[3] = backRightModule.modulePosition;
    
    
            // swerveDrivePoseEstimator.update(getCurrentYaw(), updatePositions());
    
            // SmartDashboard.putNumber("Front Left Module Enocoder", frontLeftModule.encoderValue);
            // SmartDashboard.putNumber("Front Right Module Enocoder", frontRightModule.encoderValue);
            // SmartDashboard.putNumber("Back Left Module Enocoder", backLeftModule.encoderValue);
            // SmartDashboard.putNumber("Back Right Module Enocoder", backRightModule.encoderValue);
    
    
    
        }

        public void calcStartingDistance(double StartingDistanceFromAprilTag){

            distanceAprilTag = StartingDistanceFromAprilTag*Math.tan(Camera.getTargetYaw());

        }
        public void driveAuto(double kPforDistance){
            System.out.println("IN THE AUTO DRIVE");

            double distanceToAprilTag = distanceAprilTag/Math.tan(Camera.getTargetYaw());



            double forward = (distanceToAprilTag)*kPforDistance*Constants.kSwerveDampner;
        
            chassisSpeeds = new ChassisSpeeds(0, 0.3, 0);
            setSpeed(chassisSpeeds);
          
        }
        
    
        public void alignWithAprilTag(double kPforDegreeAlign, double desired_distance, double kPforStrafeAlign, double targetRange){
           
            SmartDashboard.putNumber("align target Yaw", Camera.getTargetYaw());
            double strafe = (Camera.getTargetYaw())*kPforDegreeAlign*Constants.kSwerveDampner;
        
            double omega =(desired_distance - targetRange)*kPforStrafeAlign*Constants.kSwerveDampner;
            
    
            chassisSpeeds = new ChassisSpeeds(strafe, 0, omega);
            setSpeed(chassisSpeeds);
            
        }

        public void alignWithAprilTag(double DesiredYaw, double kPforDegreeAlign, double desired_distance, double kPforStrafeAlign, double targetRange){
           
            SmartDashboard.putNumber("align target Yaw", Camera.getTargetYaw());
            double strafe = (Camera.getTargetYaw()-DesiredYaw)*kPforDegreeAlign*Constants.kSwerveDampner;
        
            double omega =(desired_distance - targetRange)*kPforStrafeAlign*Constants.kSwerveDampner;
            
    
            chassisSpeeds = new ChassisSpeeds(strafe, 0, omega);
            setSpeed(chassisSpeeds);
            
        }

        public void Align(){
            alignWithAprilTag( 0.1, 0, 0, 0);
            System.out.println("qwertyuiopoiuytrewqwertyuiopoiuytrewq   wertyuiop");
        }

        public void AlignLeft(){
            alignWithAprilTag( 25, 0.1, 0, 0.5, Camera.targetRange);
            
        }

        public void AlignRight(){
            alignWithAprilTag(-20, 0.1, 0, 0.5, Camera.targetRange);
        }

            
        // public Pose2d getPose(){
        //             swerveDrivePoseEstimator.update(getCurrentYaw(), updatePositions());
        //             return swerveDrivePoseEstimator.getEstimatedPosition();
            
        // }
                
        public void resetPose2d(Pose2d resetPose2d){
                    swerveDrivePoseEstimator.resetPose(resetPose2d);
        }
            
        public ChassisSpeeds getcChassisSpeeds(){
                    // swerveDrivePoseEstimator.update(getCurrentYaw(), updatePositions());
                    return chassisSpeeds;
        }

        public void resetHeading(double veloForward, double veloStrafe, double targetHeading){
            ResetToFusedHeading.setSetpoint(targetHeading);


            double currentHeading = Navx.getFusedHeading();

            double rotationValue = ResetToFusedHeading.calculate(currentHeading, targetHeading);

            chassisSpeeds = new ChassisSpeeds(veloForward, veloStrafe, rotationValue);
            setSpeed(chassisSpeeds);
        }
            
            
        public  void setSpeed(ChassisSpeeds speed){
            // System.out.println("Setting states " );
            System.out.println("Speed: "+speed);
            states = kinematics.toSwerveModuleStates(speed);
            

            // System.out.println("Setting states "+states );
            frontLeftModule.setModuleState(states[0]);
            frontRightModule.setModuleState(states[1]);
            backLeftModule.setModuleState(states[2]);
            backRightModule.setModuleState(states[3]);

            //Printing out yaw
            // SmartDashboard.putNumber("YAW", Yaw);   
            

            // System.out.println();
            // System.out.println("Encoder Value: "+backLeftModule.encoderValue);
            // System.out.println("Endpoint: "+backLeftModule.endpoint);
            // System.out.println("PID speed: "+backLeftModule.pidSpeed);
            // System.out.println("Error Tolerance: "+ backLeftModule.pidController.getErrorTolerance());
            // System.out.println();
            frontLeftModule.setModulePosition();
            frontRightModule.setModulePosition();
            backLeftModule.setModulePosition();
            backRightModule.setModulePosition();

            // swerveDrivePoseEstimator.update(getCurrentYaw(), updatePositions());
        
        }


}