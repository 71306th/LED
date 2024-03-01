// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.LEDControl;
import frc.robot.subsystems.LED;
import edu.wpi.first.wpilibj2.command.Command;


public class RobotContainer {

  private final LED m_LED = new LED();

  private final LEDControl teleLED = new LEDControl(m_LED);

  public RobotContainer() {
    m_LED.setDefaultCommand(teleLED);

    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return null;
  }
}
