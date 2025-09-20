package org.firstinspires.ftc.teamcode;

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
    private static final int DESIRED_TAG_ID = -1;
    boolean isShooting = true;
    int tagID = -1;

    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        initialSetup();
        DcMotor[] drive = {leftDrive, rightDrive};
        initializeVisionPortal();
        getAprilTag();
        waitForStart();
        if (aprilTag == 20) {
        displayVisionPortalData();
        if (tagID == 20) {
            leftDrive.setPower(0.5);
            rightDrive.setPower(-0.5);
        }
        else if (tagID == 21) {
            moveToGPP();
        }
        else if (tagID == 22) {
            moveToPGP();
        }
        else if (tagID == 23) {
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
        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isShooting = false;
    }

public void getAprilTag() {

}

public void moveToGPP() {
    drive.setTargetPosition(targetTicks); // Set the desired encoder tick count
    drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    drive.setPower(1); // Set the motor power
    while (opModeIsActive() && leftDrive.isBusy()) {
        // Wait for motor to reach target position
    }
    drive.setPower(0); // Stop the motor
    drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Switch back to a different mode if needed
}

public void moveToPGP() {

}

public void moveToPPG() {

}
public void initializeVisionPortal() {
    aprilTag = new AprilTagProcessor.Builder().build();
    VisionPortal.Builder builder = new VisionPortal.Builder();

    if(USE_WEBCAM) {
       builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam"));
    }

    else {
        builder.setCamera(BuiltinCameraDirection.BACK);
    }

    builder.addProcessor(aprilTag);
    visionPortal = builder.build();
}

public void displayVisionPortalData() {
    boolean targetFound = true;
    double drive, turn = 0;
    while(opModeIsActive()) {
        targetFound = false;
        desireTag = null;
        List<AprilTagDetection> myAprilTagDetections = (aprilTag.getDetections());
        for (AprilTagDetection myAprilTag : myAprilTagDetections) {
            if(myAprilTag.metadata != null) {
                if((DESIRED_TAG_ID < 0) || (myAprilTag.id == DESIRED_TAG_ID)) {
                    targetFound = true;
                    desiredTag = myAprilTag;
                    break;
                }
                else {
                    telemetry.addData("Skipping", "Tag ID %d is not desired", detection.id);
                }
            }
            else {
                telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
            }
             myAprilTagDetection = myAprilTag;
             telemetry.addData("ID", myAprilTagDetection.id);
             telemetry.addData("Range", myAprilTagDetection.ftcPose.range);
             telemetry.addData("Yaw", myAprilTagDetection.ftcPose.yaw);
        }
     telemetry.update();
    }
}
}


