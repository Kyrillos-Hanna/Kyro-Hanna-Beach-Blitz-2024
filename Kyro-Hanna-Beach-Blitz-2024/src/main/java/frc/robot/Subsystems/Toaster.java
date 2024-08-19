// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkBase;
import frc.robot.Constants.ToasterConstants;

public class Toaster extends SubsystemBase {
  private CANSparkMax m_wheel1Motor = new CANSparkMax(ToasterConstants.kWheelMotor1ID, CANSparkLowLevel.MotorType.kBrushless);
  private CANSparkMax m_wheel2Motor = new CANSparkMax(ToasterConstants.kWheelMotor2ID, CANSparkLowLevel.MotorType.kBrushless);
  private CANSparkMax m_rollerMotor = new CANSparkMax(ToasterConstants.kRollerMotorID, CANSparkLowLevel.MotorType.kBrushless);
  
  /** Creates a new Toaster. */
  public Toaster() {
    configMotor(m_wheel1Motor, ToasterConstants.kWheelMotorsStallLimit, ToasterConstants.kWheelMotorsFreeLimit);
    configMotor(m_wheel2Motor, ToasterConstants.kWheelMotorsStallLimit, ToasterConstants.kWheelMotorsFreeLimit, m_wheel1Motor);
    configMotor(m_rollerMotor, ToasterConstants.kRollerMotorStallLimit, ToasterConstants.kRollerMotorFreeLimit);
  }

  private void configMotor(CANSparkMax motor, int stallLimit, int freelimit, CANSparkMax leader) {
    configMotor(motor, stallLimit, freelimit);
    motor.follow(leader);
    motor.burnFlash();
  }

  private void configMotor(CANSparkMax motor, int stallLimit, int freeLimit) {
    motor.restoreFactoryDefaults();
    motor.setIdleMode(CANSparkBase.IdleMode.kCoast);
    motor.setSmartCurrentLimit(stallLimit, freeLimit);
    motor.burnFlash();
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
    return this.run(() -> runWheelMotors(speed))
      .withTimeout(delay)
      .andThen(this.run(() -> runRollerMotor(speed)));
  }

  public Command turnOffToaster() {
    return this.run(() -> {
      runWheelMotors(0);
      runRollerMotor(0);
    });
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
