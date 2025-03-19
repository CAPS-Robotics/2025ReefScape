// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.Robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {

  public SparkMax motor1 = new SparkMax(11, MotorType.kBrushless);
  public Servo RatchetServo = new Servo(1);

  public double dampner = 0.5;
  /** Creates a new ClimbSubsystem. */
  public ClimbSubsystem() {
    System.out.println("qwertyuioppoiuytrewqertyuiopoiuytr");

    setDefaultCommand(new RunCommand(()->Robot.climb.stop(), this));

  }


  public void lower(){

    motor1.set(-1*dampner);
  }

  public void raise(){

    motor1.set(1*dampner);

  }


  public void stop(){
    motor1.set(0);
  }

  public void Ratchet(){
    RatchetServo.setAngle(25);
  }
  

  public void switchRatchet(){
    RatchetServo.setAngle(0);
  }
  



  @Override

  public void periodic() {
    // This method will be called once per scheduler run
  }
}
