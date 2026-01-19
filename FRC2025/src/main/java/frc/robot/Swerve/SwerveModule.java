// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Swerve;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AnalogEncoder;
import frc.robot.Constants;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;


public class SwerveModule {

    public SparkMax steeringMotor;
    public SparkFlex driveMotor;
    public SwerveModuleState moduleState;
    public SwerveModulePosition modulePosition;
    public AnalogEncoder encoder;
    public RelativeEncoder driveEncoder;
    public PIDController pidController; 
    public double encoderValue;
    public double encoderOffset;
    public double distanceMoved = 0;
    private int invert = 1;
    private double reversed = 1;
    public double endpoint;
    public double pidSpeed;
    public double errorValue;
    public Rotation2d Angle = Rotation2d.fromDegrees(0);
   


 

    public SwerveModule(int driveMotorPort,int steeringMotorPort, int encoderPort, double offset, boolean inverted, boolean reverse)
    {

        //Motors
        steeringMotor = new SparkMax(steeringMotorPort, MotorType.kBrushless);
        driveMotor = new SparkFlex(driveMotorPort, MotorType.kBrushless);

        //Module State
        moduleState = new SwerveModuleState();
        modulePosition = new SwerveModulePosition(0,Rotation2d.fromDegrees(0));
        
       
        //Encoder
        encoder = new AnalogEncoder(encoderPort);
        driveEncoder = driveMotor.getEncoder();


        //PID 
        //original 3
        pidController = new PIDController(3, 0, 0);
        pidController.enableContinuousInput(0, 1);
        pidController.setTolerance(0.001);

        //Offset
        encoderOffset = offset;

        //Invert
        if (inverted == true){

            invert = -1;
        }

        if (reverse == true){

            reversed = -1.05;
        }


         


    };

    public SwerveModule(int driveMotorPort,int steeringMotorPort, int encoderPort, double offset)
    {

        //Motors
        steeringMotor = new SparkMax(steeringMotorPort, MotorType.kBrushless);
        driveMotor = new SparkFlex(driveMotorPort, MotorType.kBrushless);


        //Module State
        moduleState = new SwerveModuleState();
        modulePosition = new SwerveModulePosition(0,Rotation2d.fromDegrees(0));

        
       
        //Encoder
        encoder = new AnalogEncoder(encoderPort);
        driveEncoder = driveMotor.getEncoder();
        


        //PID 
        pidController = new PIDController(3, 0, 0);
        pidController.enableContinuousInput(0, 1);
        pidController.setTolerance(0.001);

        //Offset
        encoderOffset = offset;

   //GR - 150/7:1
         


    };


    public SwerveModuleState getModuleState(){
        return moduleState;
    }
    public SwerveModulePosition getModulePosition(){
       return modulePosition = new SwerveModulePosition(distanceMoved, Angle);
    }
 
    public void setModuleState(SwerveModuleState state){

        
        moduleState = state;
            
       
        //Set Drive Speed
       


    

        //Offset Calculations
        encoderValue = (this.encoder.get()+encoderOffset);
        if(encoderValue > 1){
            encoderValue -= 1;
        }
        if (encoderValue < 0 ){
            encoderValue += 1;
        }

       
        moduleState.optimize(Rotation2d.fromRadians((encoderValue/1)*(Math.PI*2)));


        endpoint = (moduleState.angle.getRadians()/(Math.PI*2));
        pidSpeed = pidController.calculate(encoderValue, endpoint);    
        errorValue = 1 - Math.abs(pidController.getError());

        if (errorValue <= pidController.getErrorTolerance()){
            pidSpeed = 0;
        }

        //Set Steering Speed
        steeringMotor.set(invert*pidSpeed*Constants.kSwerveDampner);
        driveMotor.set(reversed*moduleState.speedMetersPerSecond*Constants.kSwerveDampner);

       
        
    }

    public void setModulePosition(){

        distanceMoved = driveEncoder.getPosition()*(Units.inchesToMeters(4)*Math.PI)/(6.75);

        Angle = Rotation2d.fromRadians((encoder.get()/1)*(Math.PI*2));
    }

}

