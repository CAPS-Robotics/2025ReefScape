// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Swerve;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import com.revrobotics.spark.SparkFlex;


/** Add your docs here. */
public class SwerveModule {

    private SparkMax steeringMotor;
    private SparkFlex driveMotor;
    private SwerveModuleState moduleState;

    public SwerveModule(int driveMotorPort,int steeringMotorPort, double MetersPerSecond, Rotation2d angle ){

        steeringMotor = new SparkMax(steeringMotorPort, MotorType.kBrushless);
        driveMotor = new SparkFlex(driveMotorPort, MotorType.kBrushless);

         moduleState = new SwerveModuleState(MetersPerSecond, angle);

    }

    public SwerveModuleState getModuleState(){
        return moduleState;
    }

}

