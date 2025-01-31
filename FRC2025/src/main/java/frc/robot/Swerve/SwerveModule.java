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


 

    public SwerveModule(int driveMotorPort,int steeringMotorPort, int encoderPort, double offset, boolean inverted)
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
        
        driveMotor.set(moduleState.speedMetersPerSecond);
        
        
        
        encoderValue = (this.encoder.get()+encoderOffset);
        if(encoderValue > 1){
            encoderValue -= 1;
        }

        if (encoderValue < 0 ){
            encoderValue += 1;
        }
        double endpoint = (moduleState.angle.getRadians()/(Math.PI*2));
        double pidSpeed = pidController.calculate(encoderValue, endpoint);
        System.out.println();
        System.out.println("Encoder Value: "+encoderValue);
        System.out.println("Endpoint: "+endpoint);
        System.out.println("PID speed: "+pidSpeed);
        System.out.println("Error Tolerance: "+ pidController.getErrorTolerance());
        System.out.println();

        
        double errorValue = 1 - Math.abs(pidController.getError());

        if (errorValue <= pidController.getErrorTolerance()){
            pidSpeed = 0;
        }
        // System.out.println("Error: "+ errorValue);
        steeringMotor.set(invert*pidSpeed);
       
        
    }

}

