// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Swerve;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogEncoder;
import com.revrobotics.spark.SparkFlex;
import frc.robot.subsystems.*;


/** Add your docs here. */
public class SwerveModule {

    public SparkMax steeringMotor;
    public SparkFlex driveMotor;
    private SwerveModuleState moduleState;
    public AnalogEncoder encoder;
    public PIDController pidController; 

 

    public SwerveModule(int driveMotorPort,int steeringMotorPort){
        steeringMotor = new SparkMax(steeringMotorPort, MotorType.kBrushless);
        driveMotor = new SparkFlex(driveMotorPort, MotorType.kBrushless);

        moduleState = new SwerveModuleState();

        pidController = new PIDController(0.03, 0, 0);
        pidController.enableContinuousInput(0, 1);


        };

    

    public SwerveModuleState getModuleState(){
        return moduleState;
    }
 
    public void setModuleState(SwerveModuleState state){
        moduleState = state;

        driveMotor.set(moduleState.speedMetersPerSecond);
        double encoderValue = SwerveDriveTrainSubsystem.frontLeftEncoder.get();
        double endpoint = moduleState.angle.getRadians()/(Math.PI*2);
        System.out.println("Endpoint: "+endpoint);
        steeringMotor.set(pidController.calculate(encoderValue, endpoint));
       
        
    }

}

