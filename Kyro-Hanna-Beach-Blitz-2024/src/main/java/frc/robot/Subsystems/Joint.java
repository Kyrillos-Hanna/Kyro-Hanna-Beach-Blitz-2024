// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.JointConstants;
import edu.wpi.first.math.controller.PIDController;

public class Joint extends SubsystemBase {
  private CANSparkMax motor1 = new CANSparkMax(JointConstants.kMotor1ID, CANSparkLowLevel.MotorType.kBrushless);
  private CANSparkMax motor2 = new CANSparkMax(JointConstants.kMotor1ID, CANSparkLowLevel.MotorType.kBrushless);

  private RelativeEncoder encoder1 = motor1.getEncoder();

  private PIDController m_pid = new PIDController(0, 0, 0);
  
  /** Creates a new Joint. */
  public Joint() {
    configMotor(motor1, JointConstants.kMotor1StallLimit, JointConstants.kMotor1FreeLimit, false);
    configMotor(motor2, JointConstants.kMotor1StallLimit, JointConstants.kMotor2FreeLimit, true, motor1);
    encoder1.setPositionConversionFactor(JointConstants.kGearRatio);
  }

  private void configMotor(CANSparkMax motor, int stallLimit, int freeLimit, boolean setInverted, CANSparkMax leader) {
    configMotor(motor, stallLimit, freeLimit, setInverted);
    motor.follow(leader);
    motor.burnFlash();
  }

  private void configMotor(CANSparkMax motor, int stallLimit, int freeLimit, boolean invert) {
    motor.restoreFactoryDefaults();
    motor.setIdleMode(CANSparkBase.IdleMode.kBrake);
    motor.setSmartCurrentLimit(stallLimit, freeLimit);
    motor.setInverted(invert);
    motor.burnFlash();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
