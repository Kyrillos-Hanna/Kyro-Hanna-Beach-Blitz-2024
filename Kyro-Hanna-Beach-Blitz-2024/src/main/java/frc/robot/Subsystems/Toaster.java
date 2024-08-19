// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants.ToasterConstants;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Toaster extends SubsystemBase {
  private CANSparkMax m_wheel1Motor = new CANSparkMax(ToasterConstants.toasterWheelMotor1ID, MotorType.kBrushless);
  private CANSparkMax m_wheel2Motor = new CANSparkMax(ToasterConstants.toasterWheelMotor2ID, MotorType.kBrushless);
  private CANSparkMax m_rollerMotor = new CANSparkMax(ToasterConstants.toasterRollerMotorID, MotorType.kBrushless);

  private WaitCommand m_waitCommand;
  
  /** Creates a new Toaster. */
  public Toaster() {
    m_wheel2Motor.follow(m_wheel1Motor, true);
  }

  public void runWheelMotors(double speed) {
    m_wheel1Motor.set(speed);
  }

  public void runRollerMotor(double speed) {
    m_rollerMotor.set(speed);
  }

  public Command inTake(double wheelSpeed, double rollerSpeed) {
    return this.run(() -> {
      runWheelMotors(wheelSpeed);
      runRollerMotor(rollerSpeed);
    });
  }

  public Command shoot(double speed, double delay) {
    return this.run(() -> runWheelMotors(speed)).withTimeout(delay).andThen(this.run(() -> runRollerMotor(speed)));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
