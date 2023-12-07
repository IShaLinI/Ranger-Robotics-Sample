// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.DriverConstants;
import frc.robot.subsystems.Drivebase;

public class Robot extends TimedRobot {

  private final Drivebase drivebase = new Drivebase();

  private Command autonomousCommand = Commands.none();
  private CommandXboxController driverController = new CommandXboxController(0);

  @Override
  public void robotInit() {
    drivebase.setDefaultCommand(
      drivebase.arcadeDrive(
        () -> -MathUtil.applyDeadband(driverController.getLeftY(),0.05) * DriverConstants.DRIVE_MULTIPLIER, 
        () -> MathUtil.applyDeadband(driverController.getRightX(),0.05) * DriverConstants.TURN_MULTIPLIER
      )
    );
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    drivebase.log();
  }

  @Override
  public void autonomousInit() {

    autonomousCommand = drivebase.arcadeDrive(() -> 0.25, () -> 0).withTimeout(2);

    CommandScheduler.getInstance().schedule(autonomousCommand);

  }

  @Override
  public void autonomousExit() {
    if (autonomousCommand.isScheduled() == true) {
      autonomousCommand.cancel();
    }
  }

}
