// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class AlgaeSubsystem extends SubsystemBase {


  SparkMax Algae = new SparkMax(11, MotorType.kBrushless);
  RelativeEncoder AlgaeEncoder = Algae.getEncoder();
  DigitalOutput ResetSwitch = new DigitalOutput(2);
  DigitalOutput StopSwicth = new DigitalOutput(3);


  public AlgaeSubsystem() {
    setDefaultCommand(new RunCommand(()-> Robot.algae.currentEncoder(), this ));
  }


  public double currentEncoder(){

    SmartDashboard.putNumber("Algae Encoder Value", AlgaeEncoder.getPosition());
    return AlgaeEncoder.getPosition();
   
  }

  public void RemoveAlgae(double endpoint){

    if(endpoint+0.2 > currentEncoder()){
      Algae.set(0.5);
    }else if(endpoint < currentEncoder()){
      if(endpoint > currentEncoder()){
        Algae.set(0.2);
      }
      } 
      Algae.set(0.015);
    } 

    
  

  public void resetArmEncoder(double endpoint){
    if(endpoint+0.2 < currentEncoder()){
      Algae.set(-0.2);
    }else if(endpoint > currentEncoder()){
      Algae.set(-0.02);
    }


    

  }

  public void resetArm(){
    if(ResetSwitch.get()){

      Algae.set(0);
      AlgaeEncoder.setPosition(0);
      
    }else{
      
      Algae.set(-0.5*Constants.kAlgaeDampner);
      SmartDashboard.putBoolean("Limit Switch", ResetSwitch.get());
      
    }

    SmartDashboard.putBoolean("ResetButton Pressed", true);
    

    
  }

  public void forward(){

    Algae.set(1*Constants.kAlgaeDampner);


  }

  public void Backward(){

    Algae.set(-0.5*Constants.kAlgaeDampner);


  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
