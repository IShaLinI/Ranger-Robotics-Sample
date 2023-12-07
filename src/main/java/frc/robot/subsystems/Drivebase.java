package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CAN;

/*
 * This is the Drivebase subsystem. It contains all the code pertaining to the drivebase.
 */
public class Drivebase extends SubsystemBase {
    
    /**
     * These are the motor controllers for the drivebase.
     */
    private final VictorSPX leftLeader = new VictorSPX(CAN.leftLeader);
    private final VictorSPX rightLeader = new VictorSPX(CAN.rightLeader);
    private final VictorSPX leftFollower = new VictorSPX(CAN.leftFollower);
    private final VictorSPX rightFollower = new VictorSPX(CAN.rightFollower);

    /**
     * This is the constructor for the Drivebase subsystem.
     * 
     * When the drivebase object is created in Robot.java, this code will run once.
     * 
     * This is where you should do things like configuring motor controllers or other devices required for the drivebase.
     */
    public Drivebase() {

        /**
         * Each side of the drivebase has 2 motors that are mechanically linked together.
         * If you accidentally set one of the motors to a different value than the other,
         * you risk damaging the drive gearbox. To prevent this from happening, we set
         * the follower motors to follow the leader motors, so that they always have the same value.
         */
        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        /**
         * Because of the orientation of the motors on the drivebase,
         * the right side of the drivebase needs to be inverted.
         * 
         * The main reason for this is so that when a positive X input is provided to the drivebase,
         * the robot will drive forward. If the right side of the drivebase was not inverted,
         * the robot would turn in place when a positive X input is provided.
         */
        rightLeader.setInverted(InvertType.InvertMotorOutput);
        rightFollower.setInverted(InvertType.FollowMaster);

    }

    /**
     * 
     * This method is responsible for setting the percent output of the drivebase motors.
     * Since this drivebase has no sensors to determine the wheels speeds, we can only set the percent output.
     * 
     * The percent output is a value between -1 and 1 that represents the percentage of the battery voltage that the motor will output.
     * 
     * @param outputs WheelSpeeds object containing the desired percent output for each side of the drivebase.
     */
    public void setWheelOutputs(WheelSpeeds outputs) {
        // Set the percent output of the motors to the desired value.
        leftLeader.set(ControlMode.PercentOutput, outputs.left);
        rightLeader.set(ControlMode.PercentOutput, outputs.right);
    }

    /**
     * This method is responsible for controlling the drivebase using 
     * the arcade drive style of inputs, instead of setting left and right percentages.
     * 
     * @param xSpeed The desired speed in the X direction(forward is + / backward is -) Range: (-1, 1)
     * @param zRotation The desired rotation speed about the Z axis(clockwise is - / counterclockwise is +) Range: (-1, 1)
     * @return A command to be scheduled, that will run the drivebase.
     */
    public CommandBase arcadeDrive(DoubleSupplier xSpeed, DoubleSupplier zRotation) {
        return this.run( //Run the internal code continuously
            () -> setWheelOutputs( //Calling our setWheelOutputs method with the desired values
                DifferentialDrive.arcadeDriveIK( //This is a WPIlib helper method that converts arcade drive inputs to wheel speeds 
                    xSpeed.getAsDouble(), //Get the desired X speed from the continuous X input coming from the controller
                    zRotation.getAsDouble(), //Get the desired Z rotation from the continuous Z input coming from the controller
                    true //This is a boolean that determines whether or not to square the inputs. This is useful for making the robot easier to control at low speeds.
                )
            )
        );
    }

    /**
     * This method is responsible for logging drivebase data to Shuffleboard.
     * 
     * You can open Shuffleboard by pressing CTRL + SHIFT + P and searching for "Start Tool".
     * Make sure to select "Shuffleboard" from the dropdown menu, NOT SMARTDASHBOARD.
     */
    public void log(){
        SmartDashboard.putNumber("Drivebase/Left Output", leftLeader.getMotorOutputPercent());
        SmartDashboard.putNumber("Drivebase/Right Output", rightLeader.getMotorOutputPercent());
    }

}
