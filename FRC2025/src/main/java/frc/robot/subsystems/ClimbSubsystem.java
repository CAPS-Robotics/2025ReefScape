// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {

  public SparkMax motor1 = new SparkMax(Constants.kClimbMotor1, MotorType.kBrushed);
  public SparkMax motor2 = new SparkMax(Constants.kClimbMotor2, MotorType.kBrushed);

  public double dampner = 1;
  /** Creates a new ClimbSubsystem. */
  public ClimbSubsystem() {

  }


  public void lower(){

    motor1.set(-1*dampner);
    motor2.set(-1*dampner);

  }

  public void raise(){

    motor1.set(1*dampner);
    motor2.set(1*dampner);

  }

  


  @Override

  public void periodic() {
    // This method will be called once per scheduler run
  }
}
