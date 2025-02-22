// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.MMRCommands;

/** Add your docs here. */
public class IO {

    public Joystick driveController = new Joystick(0);
    public XboxController controlController = new XboxController(1);



    public JoystickButton ServoButton = new JoystickButton(driveController, 1);


    public JoystickButton raiseButton = new JoystickButton(controlController, 1);
    public JoystickButton lowerButton = new JoystickButton(controlController, 2);

    public JoystickButton xButton = new JoystickButton(controlController, 3);

    public JoystickButton yButton = new JoystickButton(driveController, 4);
    public JoystickButton zButton = new JoystickButton(driveController, 3);
    public JoystickButton bButton = new JoystickButton(driveController, 2);
    public JoystickButton leftBumper = new JoystickButton(driveController, 4);
    public JoystickButton RightBumper = new JoystickButton(driveController, 5);


    public IO(){

        yButton.onTrue(MMRCommands.zero);
        xButton.onTrue(MMRCommands.releaseServo);
        bButton.onTrue(MMRCommands.raiseClimb);


        lowerButton.onTrue(MMRCommands.lowerElevator);
        raiseButton.onTrue(MMRCommands.raiseElevator);
        
    }

}
