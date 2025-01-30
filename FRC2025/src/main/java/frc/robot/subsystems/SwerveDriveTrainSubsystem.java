// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Swerve.SwerveModule;
import edu.wpi.first.wpilibj.AnalogEncoder;



/** Add your docs here. */
public class SwerveDriveTrainSubsystem extends SubsystemBase{

    // public static AnalogEncoder frontLeftEncoder = new AnalogEncoder(0);
    // AnalogEncoder frontRightEncoder = new AnalogEncoder(1);
    // AnalogEncoder backLeftEncoder = new AnalogEncoder(3);
    // AnalogEncoder backRightEncoder = new AnalogEncoder(2);



    SwerveModule frontRightModule = new SwerveModule(3, 2,0);
    SwerveModule frontLeftModule = new SwerveModule(5, 4,1);
    SwerveModule backRightModule = new SwerveModule(9, 8,2);
    SwerveModule backLeftModule = new SwerveModule(7, 6, 3);

    Translation2d frontLeft = new Translation2d((Constants.chasisWidth/2), (Constants.chasisLength/2));
    Translation2d frontRight = new Translation2d((Constants.chasisWidth/2), (-Constants.chasisLength/2));
    Translation2d backLeft = new Translation2d((-Constants.chasisWidth/2), (Constants.chasisLength/2));
    Translation2d backRight = new Translation2d((-Constants.chasisWidth/2), (-Constants.chasisLength/2));

    public static Rotation2d FLCurrentAngle;
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

    public void driveSwerve(XboxController drivController){
        // System.out.println("Swerve Drive");

        FLCurrentAngle = new Rotation2d(frontLeftModule.encoder.get()/1.0*2*Math.PI);
        FRCurrentAngle = new Rotation2d(frontRightModule.encoder.get()/1.0*2*Math.PI);
        BLCurrentAngle = new Rotation2d(backLeftModule.encoder.get()/1.0*2*Math.PI);
        BRCurrentAngle = new Rotation2d(backRightModule.encoder.get()/1.0*2*Math.PI);



        // frontLeftModule.moduleState.optimize(FLCurrentAngle);
        // frontRightModule.moduleState.optimize(FRCurrentAngle);
        // backLeftModule.moduleState.optimize(BLCurrentAngle);
        // backRightModule.moduleState.optimize(BRCurrentAngle);

        double velocityX = -1 * drivController.getLeftY();
        double velocityY = -1 *drivController.getLeftX();
        double omega = drivController.getRightX();

        if(Math.abs(velocityX) < 0.1) velocityX = 0;
        if(Math.abs(velocityY) < 0.1) velocityY = 0;
        if(Math.abs(omega) < 0.1) omega = 0;
   

        // System.out.println("Velocity X: " + velocityX);
        // System.out.println("Velocity Y: "+VelocityY);
        // System.out.println("Omega: "+omega);

        chassisSpeeds = new ChassisSpeeds(velocityX,velocityY, 0);

        setSpeed(chassisSpeeds);

    }

    public void setSpeed(ChassisSpeeds speed){
        // System.out.println("Setting states " );
        System.out.println("Module Angle:" + backRightModule.encoder.get());
        SwerveModuleState states[] = kinematics.toSwerveModuleStates(speed);
        // System.out.println("Setting states "+states );
        frontLeftModule.setModuleState(states[0]);
        // frontRightModule.setModuleState(states[1]);
        // backLeftModule.setModuleState(states[2]);
        // backRightModule.setModuleState(states[3]);
    }


}