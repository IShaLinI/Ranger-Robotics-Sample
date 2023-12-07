package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CAN;

public class Drivebase extends SubsystemBase {
    
    private final VictorSPX frontLeft = new VictorSPX(CAN.frontLeft);
    private final VictorSPX frontRight = new VictorSPX(CAN.frontRight);
    private final VictorSPX backLeft = new VictorSPX(CAN.backLeft);
    private final VictorSPX backRight = new VictorSPX(CAN.backRight);

    public Drivebase() {

        backLeft.follow(frontLeft);
        backRight.follow(frontRight);

        frontRight.setInverted(true);
        backRight.setInverted(true);

    }

    public void setWheelOutputs(WheelSpeeds outputs) {
        frontLeft.set(ControlMode.PercentOutput, outputs.left);
        frontRight.set(ControlMode.PercentOutput, outputs.right);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        WheelSpeeds desiredOutputs = DifferentialDrive.arcadeDriveIK(xSpeed, zRotation, true);
        setWheelOutputs(desiredOutputs);
    }

    public void log(){
        SmartDashboard.putNumber("Drivebase/Left Output", frontLeft.getMotorOutputPercent());
        SmartDashboard.putNumber("Drivebase/Right Output", frontRight.getMotorOutputPercent());
    }

}
