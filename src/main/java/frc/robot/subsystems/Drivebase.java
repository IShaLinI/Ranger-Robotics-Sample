package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CAN;

public class Drivebase extends SubsystemBase {
    
    private final VictorSPX leftLeader = new VictorSPX(CAN.frontLeft);
    private final VictorSPX rightLeader = new VictorSPX(CAN.frontRight);
    private final VictorSPX leftFollower = new VictorSPX(CAN.backLeft);
    private final VictorSPX rightFollower = new VictorSPX(CAN.backRight);

    public Drivebase() {

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        rightLeader.setInverted(InvertType.InvertMotorOutput);
        rightFollower.setInverted(InvertType.FollowMaster);

    }

    public void setWheelOutputs(WheelSpeeds outputs) {
        leftLeader.set(ControlMode.PercentOutput, outputs.left);
        rightLeader.set(ControlMode.PercentOutput, outputs.right);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        WheelSpeeds desiredOutputs = DifferentialDrive.arcadeDriveIK(xSpeed, zRotation, true);
        setWheelOutputs(desiredOutputs);
    }

    public void log(){
        SmartDashboard.putNumber("Drivebase/Left Output", leftLeader.getMotorOutputPercent());
        SmartDashboard.putNumber("Drivebase/Right Output", rightLeader.getMotorOutputPercent());
    }

}
