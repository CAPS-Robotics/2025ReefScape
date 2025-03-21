// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class ServoSubsystem extends SubsystemBase {
  /** Creates a new ServoSubsystem. */

  public Servo bucketServo = new Servo(0);


  public ServoSubsystem() {
      // setDefaultCommand(new RunCommand(()-> Robot.servo.closedServo(), this ));

  }


  public void releaseServo(){

    bucketServo.setAngle(0);

    System.out.println(bucketServo.get());
    System.out.println("Released");

  }

  public void closedServo(){
    bucketServo.setAngle(180);
    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
