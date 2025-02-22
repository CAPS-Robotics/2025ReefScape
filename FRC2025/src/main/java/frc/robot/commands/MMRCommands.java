// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;



/** Add your docs here. */
public class MMRCommands {

    
    // public static RunCommand raiseTo = new RunCommand(()-> Robot.elevator.raiseTo(200), Robot.elevator);
    public static InstantCommand zero = new InstantCommand(()-> Robot.elevator.zero(), Robot.elevator);
    public static InstantCommand releaseServo = new InstantCommand(()-> Robot.elevator.releaseServo(), Robot.elevator);
    public static InstantCommand closeServo = new InstantCommand(()-> Robot.elevator.closedServo(), Robot.elevator);
    public static RunCommand raiseClimb = new RunCommand(()-> Robot.climb.raise(), Robot.climb);
    public static RunCommand lowerClimb = new RunCommand(()-> Robot.climb.lower(), Robot.climb);

    public static RunCommand raiseElevator = new RunCommand(()-> Robot.elevator.raise(), Robot.elevator);
    public static RunCommand lowerElevator = new RunCommand(()-> Robot.elevator.lower(), Robot.elevator);
    public static RunCommand stop = new RunCommand(()-> Robot.elevator.stop(), Robot.elevator);


}
