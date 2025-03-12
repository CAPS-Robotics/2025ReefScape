// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class AlgaeSubsystem extends SubsystemBase {


  SparkMax Algae = new SparkMax(11, MotorType.kBrushless);
  RelativeEncoder AlgaeEncoder = Algae.getEncoder();


  public AlgaeSubsystem() {
    // setDefaultCommand(new RunCommand(()-> Robot.algae.currentEncoder(), this ));
  }


  public double currentEncoder(){

    SmartDashboard.putNumber("Algae Encoder Value", AlgaeEncoder.getPosition());
    return AlgaeEncoder.getPosition();
   
  }

  public void RemoveAlgae(double endpoint){

    if(endpoint > currentEncoder()){
      Algae.set(1*Constants.kAlgaeDampner);
    }else if(endpoint < currentEncoder()){
      Algae.set(0);
    }

  }

  public void resetArm(double endpoint){
    if(endpoint < currentEncoder()){
      Algae.set(-1*Constants.kAlgaeDampner);
    }else if(endpoint > currentEncoder()){
      Algae.set(0);
    }
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
