// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.Constants;



/** Add your docs here. */
public class MMRCommands {

    
    public static RunCommand raiseToL2 = new RunCommand(()-> Robot.elevator.raiseTo(Constants.kLevel_2), Robot.elevator);
    public static RunCommand raiseToL3 = new RunCommand(()-> Robot.elevator.raiseTo(Constants.kLevel_3), Robot.elevator);
    public static RunCommand raiseToL4 = new RunCommand(()-> Robot.elevator.raiseTo(Constants.kLevel_4), Robot.elevator);

    public static RunCommand raiseElevator = new RunCommand(()-> Robot.elevator.raise(), Robot.elevator);
    public static RunCommand lowerElevator = new RunCommand(()-> Robot.elevator.lower(), Robot.elevator);

    public static RunCommand zero = new RunCommand(()-> Robot.elevator.zero(), Robot.elevator);
    
    public static InstantCommand releaseServo = new InstantCommand(()-> Robot.elevator.releaseServo(), Robot.elevator);
    public static InstantCommand closeServo = new InstantCommand(()-> Robot.elevator.closedServo(), Robot.elevator);
    public static RunCommand raiseClimb = new RunCommand(()-> Robot.climb.raise(), Robot.climb);
    public static RunCommand lowerClimb = new RunCommand(()-> Robot.climb.lower(), Robot.climb);

    
    public static RunCommand stop = new RunCommand(()-> Robot.elevator.stop(), Robot.elevator);


}
