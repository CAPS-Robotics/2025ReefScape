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
import frc.robot.subsystems.SwerveDriveTrainSubsystem;


/** Add your docs here. */
public class SwerveModule {

    public SparkMax steeringMotor;
    public SparkFlex driveMotor;
    public SwerveModuleState moduleState;
    public AnalogEncoder encoder;
    public PIDController pidController; 
    public double encoderValue;
    public double encoderOffset;
    private int invert = 1;
    private int reversed = 1;
    public double endpoint;
    public double pidSpeed;
    public double errorValue;
   


 

    public SwerveModule(int driveMotorPort,int steeringMotorPort, int encoderPort, double offset, boolean inverted, boolean reverse)
    {

        //Motors
        steeringMotor = new SparkMax(steeringMotorPort, MotorType.kBrushless);
        driveMotor = new SparkFlex(driveMotorPort, MotorType.kBrushless);

        //Module State
        moduleState = new SwerveModuleState();
       
        //Encoder
        encoder = new AnalogEncoder(encoderPort);


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

            reversed = -1;
        }


   //GR - 150/7:1
         


    };

    public SwerveModule(int driveMotorPort,int steeringMotorPort, int encoderPort, double offset)
    {

        //Motors
        steeringMotor = new SparkMax(steeringMotorPort, MotorType.kBrushless);
        driveMotor = new SparkFlex(driveMotorPort, MotorType.kBrushless);

        


        //Module State
        moduleState = new SwerveModuleState();
       
        //Encoder
        encoder = new AnalogEncoder(encoderPort);


        //PID 
        //original 3
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
 
    public void setModuleState(SwerveModuleState state){
        

        moduleState = state;
    //todo -> Scale Motor Speed

        // System.out.println("Module Angle: "+ SwerveDriveTrainSubsystem.FLCurrentAngle);
        
        driveMotor.set(reversed*moduleState.speedMetersPerSecond*0.5);
        
        
        
        encoderValue = (this.encoder.get()+encoderOffset);
        if(encoderValue > 1){
            encoderValue -= 1;
        }
        if (encoderValue < 0 ){
            encoderValue += 1;
        }
        endpoint = (moduleState.angle.getRadians()/(Math.PI*2));
        pidSpeed = pidController.calculate(encoderValue, endpoint);    
        errorValue = 1 - Math.abs(pidController.getError());

        if (errorValue <= pidController.getErrorTolerance()){
            pidSpeed = 0;
        }
        // System.out.println("Error: "+ errorValue);
        steeringMotor.set(invert*pidSpeed*0.5);
       
        
    }

}

