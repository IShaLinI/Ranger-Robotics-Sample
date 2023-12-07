package frc.robot;

/**
 * This file contains all of the constants used in the robot code. Any constant should be defined here and used throughout the code.
 */
public class Constants {
    
    /**
     * This class contains all of the robot's CAN Ids.
     * These are used to identify the motor controllers
     * and other CAN devices on the robot.
     */
    public static final class CAN {
        public static final int leftLeader = 1;
        public static final int rightLeader = 0;
        public static final int leftFollower = 3;
        public static final int rightFollower = 2;
    }

    /**
     * This class contains all of the constants related to the primary driver's preferences.
     */
    public static final class DriverConstants {
        public static final double TURN_MULTIPLIER = 0.5;
        public static final double DRIVE_MULTIPLIER = 0.5;
    }

}
