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

    // public Joystick driveController = new Joystick(0);
    public XboxController controlController = new XboxController(1);
    public Joystick driveController = new Joystick(0);


    //Elevator Buttons
    public JoystickButton l2Button = new JoystickButton(controlController, 3);
    public JoystickButton l3Button = new JoystickButton(controlController, 4);
    public JoystickButton l4Button = new JoystickButton(controlController, 1);
    public JoystickButton zeroButton = new JoystickButton(controlController, 2);

    // Servo
    public JoystickButton servoButton = new JoystickButton(controlController, 6);

    //Algae
    // public JoystickButton raiseButton = new JoystickButton(controlController, 7);
    // public JoystickButton lowerButton = new JoystickButton(controlController, 8);
    // public JoystickButton RatchetRelease = new JoystickButton(driveController, 7);
    // public JoystickButton RatchetEngage = new JoystickButton(driveController, 8);

    // //Align 
    // public JoystickButton AlignButton = new JoystickButton(driveController, 1);
    // public JoystickButton AlignLeftButton = new JoystickButton(driveController, 5);
    // public JoystickButton AlignRightButton = new JoystickButton(driveController, 6);
   

    


    public IO(){

       
        servoButton.onTrue(MMRCommands.releaseServo);
        servoButton.onFalse(MMRCommands.closeServo); 


        l2Button.onTrue(MMRCommands.raiseToL2);
        l3Button.onTrue(MMRCommands.raiseToL3);
        l4Button.onTrue(MMRCommands.raiseToL4);
        zeroButton.onTrue(MMRCommands.zero);

        // raiseButton.whileTrue(MMRCommands.raiseClimb);
        // lowerButton.whileTrue(MMRCommands.LowerCLimb);
        // RatchetEngage.whileTrue(MMRCommands.RatchetEnable);
        // RatchetRelease.whileTrue(MMRCommands.RatchetDisable);


        // AlignButton.whileTrue(MMRCommands.Align);
        // AlignLeftButton.whileTrue(MMRCommands.AlignLeft);
        // AlignRightButton.whileTrue(MMRCommands.AlignRight);


        

    }


}
