// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.Kinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.*;
import frc.robot.Swerve.SwerveModule;



/** Add your docs here. */
public class SwerveDriveTrainSubsystem extends SubsystemBase{

    SwerveModuleState frontModuleState ;
    SwerveModule frontLeftModule = new SwerveModule(3, 2, 3.0 , new Rotation2d(Math.toRadians(45)));
    SwerveModule frontRightModule = new SwerveModule(5, 4, 3.0, new Rotation2d(Math.toRadians(45)));
    SwerveModule backLeftModule = new SwerveModule(7, 6, 3 ,new Rotation2d(Math.toRadians(45)));
    SwerveModule backRightModule = new SwerveModule(9, 8, 3 ,new Rotation2d(Math.toRadians(45)));

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
        SmartDashboard.putNumberArray("Setting Initial State Value:",moduleStateLog);
         
        setDefaultCommand(new RunCommand(()-> Robot.swerveTrain.driveSwerve(), this ));

    }

    public void driveSwerve(){
        System.out.println("Swerve Drive");
        System.out.println();

        // double moduleStateLog[]=
        // {
        //     0, // Front Left Module angle
        //     0, // Front Left Module velocity -> m/s
        //     0, // Front Right Module angle 
        //     0, // Front Right Module Velocity -> m/s
        //     0, // Back Left Module angle
        //     0, // Back Left Module Velocity -> m/s 
        //     0, // Back Right Module angle 
        //     0  // Back Right Module velocity -> m/s

        // };
    }



    
}
