// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  private final Toaster m_toaster = new Toaster();
  private final CommandXboxController m_controller = new CommandXboxController(0);
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    m_controller.a().whileTrue(m_toaster.inTake(-0.5, -0.5));
    m_controller.b().whileTrue(m_toaster.shoot(0.5, 0.75));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}