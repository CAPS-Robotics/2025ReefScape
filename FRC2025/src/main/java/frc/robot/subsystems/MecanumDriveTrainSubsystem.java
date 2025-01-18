// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.RunCommand; 
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class MecanumDriveTrainSubsystem extends SubsystemBase {


  // SparkMax frontLeftMotor = new SparkMax(5, MotorType.kBrushless);
  // SparkMax frontRightMotor = new SparkMax(4, MotorType.kBrushless);
  // SparkMax backLeftMotor = new SparkMax(2, MotorType.kBrushless);
  // SparkMax backRightMotor = new SparkMax(3, MotorType.kBrushless);
  
  // MecanumDrive m_robotDrive =  new MecanumDrive(frontLeftMotor::set, backLeftMotor::set, frontRightMotor::set, backRightMotor::set);
  
  
  

  public  MecanumDriveTrainSubsystem(){
    // frontRightMotor.setInverted(true);
    // backRightMotor.setInverted(true);
  setDefaultCommand(new RunCommand(() -> Robot.mecanumTrain.driveT(Robot.io.driveController), this) );
  }


  public void driveT(XboxController drivController){
    double ySpeed = drivController.getLeftX();
    double xSpeed = drivController.getRightY();
    double zRotation = drivController.getLeftTriggerAxis();

    if(Math.abs(ySpeed) < 0.1) ySpeed = 0;
    if(Math.abs(xSpeed) < 0.1) xSpeed = 0;
    System.out.println("X speed: "+xSpeed);
    System.out.println("y speed: "+ySpeed);


    

   // m_robotDrive.driveCartesian(xSpeed, ySpeed, zRotation);
  }

  public void move_motor(){
  //  frontLeftMotor.set(0.25);
  //   frontRightMotor.set(0.25);
  //   backLeftMotor.set(0.25);
  //   backRightMotor.set(0.25);
  }

 

  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
  }
}
