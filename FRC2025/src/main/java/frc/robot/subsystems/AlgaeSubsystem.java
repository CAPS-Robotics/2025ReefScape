// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import pabeles.concurrency.ConcurrencyOps.Reset;
import edu.wpi.first.wpilibj2.command.*;

public class AlgaeSubsystem extends SubsystemBase {


  public SparkMax Algae = new SparkMax(12, MotorType.kBrushless);
  public RelativeEncoder AlgaeEncoder = Algae.getEncoder();
  public DigitalInput ResetSwitch = new DigitalInput(2);

  public AlgaeSubsystem() {
    SmartDashboard.putBoolean("Reset Switch", false);
    // setDefaultCommand(new RunCommand(()-> Robot.algae.Stop(), this ));

  }


  public void Stop(){
    setSpeed(0);
  }

  public void extendArm(){
    Algae.set(0.1);

   
  }

  public void ResetArm(double endpoint){

   Algae.set(-0.1);

    // if(endpoint <  AlgaeEncoder.getPosition()){
    //   setSpeed(-1*Constants.kAlgaeDampner);
    // }else if(endpoint < AlgaeEncoder.getPosition()){
    //   if(AlgaeEncoder.getPosition()-endpoint >= 0.1){
    //     setSpeed(-1*Constants.kAlgaeDampner);
    //   }else{
    //     setSpeed(-0.015);
    //   }
    // }

    
  } 

  public void setSpeed(double input){
    double speed = input;
    if(speed > 0 ){
      if(!ResetSwitch.get()){
        Algae.set(0);
        AlgaeEncoder.setPosition(0);
        System.out.println("Arm Reset");
      }else{
        Algae.set(speed);
      }
    }
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
