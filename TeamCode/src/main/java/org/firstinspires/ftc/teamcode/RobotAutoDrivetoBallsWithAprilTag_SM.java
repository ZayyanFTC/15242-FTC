package org.firstodeinspires.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Autonomous(name="RobotAutoDrivetoBallsWithAprilTag", group = "Concept")
public class RobotAutoDrivetoBallsWithAprilTag_SM extends LinearOpMode {
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private VisionPortal visionPortal;               // Used to manage the video source.
    private AprilTagProcessor aprilTag;              // Used for managing the AprilTag detection process.
    private AprilTagDetection desiredTag = null;
    boolean isShooting;
    int same;


public void runOpMode() {
    leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
    rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
    initialSetup();
    getAprilTag();
    if (aprilTag == 20) {
        leftDrive.setPower(0.5);
        rightDrive.setPower(-0.5);
    }
    if (aprilTag == 21) {
        moveToGPP();
    }
    if (aprilTag == 22) {
        moveToPGP();
    }
    if (aprilTag == 23) {
        moveToPPG();
    }
    else {
        leftDrive.setPower(-0.5);
        rightDrive.setPower(0.5);
    }
}

public void initialSetup() {
    leftDrive.setDirection(DcMotor.Direction.REVERSE);
    rightDrive.setDirection(DcMotor.Direction.FORWARD);
    leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    isShooting = false;
}

public void getAprilTag() {

}

public void moveToGPP() {
    leftDrive.setTargetPosition(targetTicks); // Set the desired encoder tick count
    leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    leftDrive.setPower(1); // Set the motor power
    while (opModeIsActive() && leftDrive.isBusy()) {
        // Wait for motor to reach target position
    }
    leftDrive.setPower(0); // Stop the motor
    leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Switch back to a different mode if needed
}

public void moveToPGP() {

}

public void moveToPPG() {

}

}


