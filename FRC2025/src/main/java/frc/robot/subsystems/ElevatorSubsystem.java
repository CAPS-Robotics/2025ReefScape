// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class ElevatorSubsystem extends SubsystemBase {
  /** Creates a new Elevator. */
  public SparkFlex elevatorMotor = new SparkFlex(Constants.kElevatorMotor, MotorType.kBrushless);

  public RelativeEncoder elevatorMotorEncoder = elevatorMotor.getEncoder();
  public Servo bucketServo = new Servo(0);
  
  //figure out distance of one rotation of the axle

  public double heightToraise;
  public double distancePerRotation = 5;
  public double currentRotations = elevatorMotorEncoder.getPosition();

  public DigitalInput bottomlimitSwitch = new DigitalInput(0);
  public DigitalInput toplimitSwitch = new DigitalInput(1);

 

  public ElevatorSubsystem()
  {
    elevatorMotorEncoder.setPosition(0);
    setDefaultCommand(new RunCommand(()-> Robot.elevator.closedServo(), this ));
    setDefaultCommand(new RunCommand(()-> Robot.elevator.getEncoder(), this ));


    

  }


  // public void raiseTo(double height){
    
  //   heightToraise = height;

  //   currentRotations = elevatorMotorEncoder.getPosition();
  //   // System.out.println("Encoder Value: "+ currentRotations);

  //   double numRotationRequired = height/distancePerRotation;
  //   // System.out.println("numRotationsRequired: " + numRotationRequired);

  //   // Vortex RPM = 6784 RPM
  //   // Vortex Gearbox = 20:1
  //   // 6784:339.2

  //     if (numRotationRequired <= currentRotations){
  //       setSpeed(0);
  //       if(currentRotations >= numRotationRequired+10)
  //         {
  //           setSpeed(-0.05);
  //         }else{
  //           setSpeed(0.1);
  //         }
  //     }else{
  //       setSpeed(1);
  //     }

      


  //   }
  
    public void zero(){

      elevatorMotorEncoder.setPosition(0);

    }


    public double getEncoder(){

     return elevatorMotorEncoder.getPosition();

    }
    public void raise(){

      setSpeed(1);
      System.out.println("UP");
      System.out.println("Motor Voltage:"+ elevatorMotor.getBusVoltage());
      System.out.println("Motor Current:"+ elevatorMotor.getOutputCurrent());


    }


    public void lower(){

      setSpeed(-1);
      System.out.println("Down");

    }


    public void setSpeed(double speed){
      double motorSpeed = Constants.kElevatorDampner*speed;
      if(motorSpeed > 0){
        if(toplimitSwitch.get()){
          elevatorMotor.set(0.01);
        }else{
          elevatorMotor.set(motorSpeed);
        }
      }else if (motorSpeed < 0){

        if(bottomlimitSwitch.get()){
          elevatorMotor.set(0);
        }else{
          elevatorMotor.set(motorSpeed);
        }

      }

    }


    public void releaseServo(){

      bucketServo.set(1);
      bucketServo.setAngle(90);
      System.out.println("Released");

    }

    public void closedServo(){
      bucketServo.set(0);
      bucketServo.set(0);
    }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
