// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.MMRCommands;

/** Add your docs here. */
public class IO {

    public Joystick driveController = new Joystick(0);
    public XboxController controlController = new XboxController(1);

    //Elevator Buttons
    public JoystickButton A_Button = new JoystickButton(controlController, 1);
    public JoystickButton B_Button = new JoystickButton(controlController, 2);
    public JoystickButton X_Button = new JoystickButton(controlController, 3);
    public JoystickButton Y_Button = new JoystickButton(controlController, 4);

    // Servo
    public JoystickButton bumperButton = new JoystickButton(controlController, 5);

    //Algae
    public JoystickButton raiseButton = new JoystickButton(controlController, 7);
    public JoystickButton lowerButton = new JoystickButton(controlController, 8);
    public JoystickButton RatchetRelease = new JoystickButton(driveController, 7);
    public JoystickButton RatchetEngage = new JoystickButton(driveController, 8);

    //Align 
    public JoystickButton AlignButton = new JoystickButton(driveController, 1);
    public JoystickButton AlignLeftButton = new JoystickButton(driveController, 2);
    public JoystickButton AlignRightButton = new JoystickButton(driveController, 4);


    


    public IO(){

       
        bumperButton.onTrue(MMRCommands.releaseServo);
        bumperButton.onFalse(MMRCommands.closeServo); 


        A_Button.whileTrue(MMRCommands.raiseToL2);
        B_Button.whileTrue(MMRCommands.raiseToL3);
        Y_Button.onTrue(MMRCommands.raiseToL4);
        X_Button.onTrue(MMRCommands.zero);

        raiseButton.whileTrue(MMRCommands.raiseClimb);
        lowerButton.whileTrue(MMRCommands.LowerCLimb);
        RatchetEngage.whileTrue(MMRCommands.RatchetEnable);
        RatchetRelease.whileTrue(MMRCommands.RatchetDisable);


        AlignButton.toggleOnTrue(MMRCommands.Align);
        AlignLeftButton.onTrue(MMRCommands.AlignLeft);
        AlignRightButton.onTrue(MMRCommands.AlignRight);


        

    }


}
