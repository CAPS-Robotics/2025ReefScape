// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
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
    public JoystickButton joystickButton = new JoystickButton(controlController, 9);

    //Algae
    public JoystickButton raiseButton = new JoystickButton(controlController, 7);
    public JoystickButton lowerButton = new JoystickButton(controlController, 8);
    


    public IO(){

       
        joystickButton.onTrue(MMRCommands.releaseServo);
        joystickButton.onFalse(MMRCommands.closeServo); 

        A_Button.onTrue(MMRCommands.raiseToL2);
        B_Button.onTrue(MMRCommands.raiseToL3);
        Y_Button.onTrue(MMRCommands.raiseToL4);
        X_Button.onTrue(MMRCommands.zero);

        // rightBumper.onTrue(MMRCommands.RemoveAlgae);
        // rightBumper.onFalse(MMRCommands.ResetAlgae);
        raiseButton.onTrue(MMRCommands.ForwardAlgae);
        lowerButton.onTrue(MMRCommands.ReverseAlgae);
        

    }


}
