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

/**
 * This is the main robot class.
 */
public class Robot extends TimedRobot {

  //This is where you should instantiate all of your subsystems.
  private final Drivebase drivebase = new Drivebase();

  //Example autonomous comamnd
  private Command autonomousCommand = Commands.none();

  //This is where you should instantiate all of your driver controllers.
  private CommandXboxController driverController = new CommandXboxController(0);

  /**
   * This method is run when the robot is first started up and should be 
   * used for any initialization code, such as default commands for subsystems.
   */
  @Override
  public void robotInit() {
    drivebase.setDefaultCommand(
      drivebase.arcadeDrive(
        () -> -MathUtil.applyDeadband(driverController.getLeftY(),0.05) * DriverConstants.DRIVE_MULTIPLIER, 
        () -> MathUtil.applyDeadband(driverController.getRightX(),0.05) * DriverConstants.TURN_MULTIPLIER
      )
    );
  }

  /**
   * This method is called every robot loop, no matter the mode the robot is in.
   * This is where you should put code that you want to run constantly.
   * 
   * The default loop rate is 20 loops per second.
   * 
   * Note: For Safety reasons, you should not put any code that moves the robot in this method.(it wont work reguardless)
   */
  @Override
  public void robotPeriodic() {
    //We have to run the command scheduler in order for all of the commands to work.
    CommandScheduler.getInstance().run();

    //Log the drivebase continuously.
    drivebase.log();
  }

  /**
   * This method is called once when the robot enters the autonomous mode.
   * 
   * This is where you should be scheduling your autonomous command(s).
   * 
   * Note: Make sure to cancel your autonomous command(s) when the robot exits autonomous mode.
   */
  @Override
  public void autonomousInit() {

    autonomousCommand = drivebase.arcadeDrive(() -> 0.25, () -> 0).withTimeout(2);

    CommandScheduler.getInstance().schedule(autonomousCommand);

  }

  /**
   * This method is called once when the robot exits the autonomous mode.
   * 
   * This is where you should be canceling your autonomous command(s).
   */
  @Override
  public void autonomousExit() {
    if (autonomousCommand.isScheduled() == true) {
      autonomousCommand.cancel();
    }
  }

}
