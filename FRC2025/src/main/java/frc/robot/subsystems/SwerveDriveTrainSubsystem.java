// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
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

    // SwerveModuleState frontLeftOptimized = frontLeftModule.moduleState.optimize(frontLeftModule.moduleState.angle);


    

    Translation2d frontLeft = new Translation2d((Constants.chasisWidth/2), (Constants.chasisLength/2));
    Translation2d frontRight = new Translation2d((Constants.chasisWidth/2), (-Constants.chasisLength/2));
    Translation2d backLeft = new Translation2d((-Constants.chasisWidth/2), (Constants.chasisLength/2));
    Translation2d backRight = new Translation2d((-Constants.chasisWidth/2), (-Constants.chasisLength/2));

    Rotation2d FLCurrentAngle;
    Rotation2d FRCurrentAngle;
    Rotation2d BLCurrentAngle;
    Rotation2d BRCurrentAngle;


    SwerveDriveKinematics kinematics = new SwerveDriveKinematics(frontLeft, frontRight, backLeft, backRight);

    ChassisSpeeds chassisSpeeds; 

    public SwerveDriveTrainSubsystem(){
        
       

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

    public void driveSwerve(Joystick drivController){
        // System.out.println("Swerve Drive");

        FLCurrentAngle = new Rotation2d(frontLeftModule.encoder.get()/1.0*2*Math.PI);
        FRCurrentAngle = new Rotation2d(frontRightModule.encoder.get()/1.0*2*Math.PI);
        BLCurrentAngle = new Rotation2d(backLeftModule.encoder.get()/1.0*2*Math.PI);
        BRCurrentAngle = new Rotation2d(backRightModule.encoder.get()/1.0*2*Math.PI);



        // frontLeftModule.moduleState.optimize(FLCurrentAngle);
        // frontRightModule.moduleState.optimize(FRCurrentAngle);
        // backLeftModule.moduleState.optimize(BLCurrentAngle);
        // backRightModule.moduleState.optimize(BRCurrentAngle);

        double velocityX = -1 * drivController.getX();
        double velocityY = -1 *drivController.getY();
        double omega = drivController.getZ();
        if(Math.abs(velocityX) < 0.1) velocityX = 0;
        if(Math.abs(velocityY) < 0.1) velocityY = 0;
        if(Math.abs(omega) < 0.1) omega = 0;

   

        // System.out.println("Velocity X: " + velocityX);
        // System.out.println("Velocity Y: "+VelocityY);
        // System.out.println("Omega: "+omega);

        chassisSpeeds = new ChassisSpeeds(velocityX,velocityY, omega);

        setSpeed(chassisSpeeds);

    }

    public void setSpeed(ChassisSpeeds speed){
        // System.out.println("Setting states " );
        SwerveModuleState states[] = kinematics.toSwerveModuleStates(speed);

        // System.out.println("Setting states "+states );
        frontLeftModule.setModuleState(states[0]);
        frontRightModule.setModuleState(states[1]);
        backLeftModule.setModuleState(states[2]);
        backRightModule.setModuleState(states[3]);

        // System.out.println();
        // System.out.println("Encoder Value: "+backLeftModule.encoderValue);
        // System.out.println("Endpoint: "+backLeftModule.endpoint);
        // System.out.println("PID speed: "+backLeftModule.pidSpeed);
        // System.out.println("Error Tolerance: "+ backLeftModule.pidController.getErrorTolerance());
        // System.out.println();

    }


}