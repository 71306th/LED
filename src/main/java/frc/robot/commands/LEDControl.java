// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.LED;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class LEDControl extends Command {

  private final LED s_LED;

  private final XboxController js1 = new XboxController(0);

  private int mode;

  public LEDControl(LED subsystem) {
    s_LED = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    mode = 0;
  }

  @Override
  public void execute() {
    if(js1.getAButton()) {
      mode = 1;
    }
    if(js1.getBButton()) {
      mode = 2;
    }
    if(js1.getXButton()) {
      mode = 3;
    }
    if(js1.getYButton()) {
      mode = 4;
    }
    if(js1.getBackButton()) {
      mode = 0;
    }
    if(js1.getStartButton()) {
      mode = 5;
    }
    s_LED.setMode(mode);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
