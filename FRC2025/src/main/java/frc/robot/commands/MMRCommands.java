// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Robot;
import frc.robot.Constants;


  
/** Add your docs here. */
public class MMRCommands {
    //Algae Commands
    // public static RunCommand ForwardAlgae = new RunCommand(()-> Robot.algae.RemoveAlgae(1), Robot.algae);
    // public static RunCommand ReverseAlgae = new RunCommand(()-> Robot.algae.resetArm(), Robot.algae);
    public static RunCommand Extend  = new RunCommand(()-> Robot.algae.extendArm(), Robot.algae);
    public static RunCommand Reset = new RunCommand(()-> Robot.algae.ResetArm(2.4), Robot.algae);

    //Elevator Commands
    public static RunCommand raiseToL2 = new RunCommand(()-> Robot.elevator.raiseTo(Constants.kLevel_2), Robot.elevator);
    public static RunCommand raiseToL3 = new RunCommand(()-> Robot.elevator.raiseTo(Constants.kLevel_3), Robot.elevator);
    public static RunCommand raiseToL4 = new RunCommand(()-> Robot.elevator.raiseTo(Constants.kLevel_4), Robot.elevator);

    public static RunCommand raiseElevator = new RunCommand(()-> Robot.elevator.raise(), Robot.elevator);
    public static RunCommand lowerElevator = new RunCommand(()-> Robot.elevator.lower(), Robot.elevator);

    public static RunCommand zero = new RunCommand(()-> Robot.elevator.zero(), Robot.elevator);
    
    //Servo Commands
    public static InstantCommand releaseServo = new InstantCommand(()-> Robot.elevator.releaseServo(), Robot.elevator);
    public static InstantCommand closeServo = new InstantCommand(()-> Robot.elevator.closedServo(), Robot.elevator);
    
    //Climb Commands
    public static RunCommand raiseClimb = new RunCommand(()-> Robot.climb.raise(), Robot.climb);
    public static WaitCommand Wait = new WaitCommand(1);

    
    public static RunCommand lowerClimb = new RunCommand(()-> Robot.climb.lower(), Robot.climb);
    public static InstantCommand RatchetEnable = new InstantCommand(()-> Robot.climb.Ratchet(), Robot.climb);
    public static InstantCommand RatchetDisable = new InstantCommand(()-> Robot.climb.switchRatchet(), Robot.climb);

    public static SequentialCommandGroup LowerCLimb = new SequentialCommandGroup(RatchetEnable, Wait, lowerClimb );

    //Elevator Stop
    public static RunCommand stop = new RunCommand(()-> Robot.elevator.stop(), Robot.elevator);


    //Alignment
    public static RunCommand Align = new RunCommand(()-> Robot.swerveTrain.Align(), Robot.swerveTrain);
    public static RunCommand AlignLeft = new RunCommand(()-> Robot.swerveTrain.AlignLeft(), Robot.swerveTrain);
    public static RunCommand AlignRight = new RunCommand(()-> Robot.swerveTrain.AlignRight(), Robot.swerveTrain);

    //Auto
    public static InstantCommand calcDistance = new InstantCommand(()-> Robot.swerveTrain.calcStartingDistance(88), Robot.swerveTrain);
    public static RunCommand driveAuto = new RunCommand(()-> Robot.swerveTrain.driveAuto(0.1), Robot.swerveTrain);

    public static SequentialCommandGroup auton = new SequentialCommandGroup(calcDistance, driveAuto);
}
