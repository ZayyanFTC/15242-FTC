package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class RobotAutoDrivetoBallsWithAprilTag_SM extends LinearOpMode {
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor shootwheel;
    private Servo artifactstopper;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private static final boolean USE_WEBCAM = true;
    boolean isShooting;
    double shootPower, horizontalInput, verticalInput;
    int maxDrivePower, mode, nArtifacts;
    AprilTagDetection myAprilTagDetection;
    @Override
    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        shootwheel = hardwareMap.get(DcMotor.class, "shootwheel");
        artifactstopper = hardwareMap.get(Servo.class, "artifactstoppper");
        initialSetup();
        initializeVisionPortal();
        shootPower = 0.8;
        maxDrivePower = 1;
        mode = 0;
        waitForStart();
        pickMode();
        displayVisionPortalData();
    }
    public void initialSetup() {
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        isShooting = false;
        artifactstopper.setPosition(0.2);
    }
    public void pickMode() {
        if(mode == 0) {
            gamepadDrive();
        }
        else if(mode == 1) {
            autoDrive();
        }
    }
    public void gamepadDrive() {
        while(opModeIsActive()) {
            horizontalInput = gamepad1.right_stick_x;
            verticalInput = gamepad1.left_stick_y;
            processDriveInputs();
            if(gamepad1.a && !isShooting) {
                shoot();
            }
        }
    }
    public void processDriveInputs() {
        leftDrive.setPower(verticalInput * maxDrivePower + horizontalInput * verticalInput);
        rightDrive.setPower(verticalInput * maxDrivePower - horizontalInput * verticalInput);
    }
    public void shoot() {
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        isShooting = true;
        artifactstopper.setPosition(0);
        shootwheel.setPower(shootPower);
        sleep(250);
        artifactstopper.setPosition(0.2);
        sleep(200);
        shootwheel.setPower(0);
        sleep(1500);
        isShooting = false;
    }
    public void autoDrive() {
        driveTogGoal();
        shootThreeArtifacts();
        driveToLoadingSpotAndBack();
        shootThreeArtifacts();
        driveToLoadingSpotAndBack();
        shootThreeArtifacts();
    }
    public void driveTogGoal() {
        leftDrive.setPower(1);
        rightDrive.setPower(1);
        sleep(1200);
        leftDrive.setPower(-1);
        rightDrive.setPower(1);
        sleep(230);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        sleep(500);
    }
    public void shootThreeArtifacts() {
        nArtifacts = 3;
        while(opModeIsActive() && nArtifacts > 0) {
            if(!isShooting) {
                shoot();
                nArtifacts =- 1;
            }
        }
    }
    public void driveToLoadingSpotAndBack() {
        leftDrive.setPower(-1);
        rightDrive.setPower(-1);
        sleep(1500);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        sleep(10000);
        leftDrive.setPower(1);
        rightDrive.setPower(1);
        sleep(1500);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        sleep(500);
    }

    public void initializeVisionPortal() {
        aprilTag = new AprilTagProcessor.Builder().build();
        VisionPortal.Builder builder = new VisionPortal.Builder();
        if(USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        }
        else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();
    }
    public void displayVisionPortalData() {
        List<AprilTagDetection> myAprilTagDetections = (aprilTag.getDetections());
        for (AprilTagDetection myAprilTag : myAprilTagDetections) {
            myAprilTagDetection = myAprilTag;
            telemetry.addData("ID", myAprilTagDetection.id);
            telemetry.addData("Range", myAprilTagDetection.ftcPose.range);
            telemetry.addData("Yaw", myAprilTagDetection.ftcPose.yaw);
        }
        telemetry.update();
=======
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
>>>>>>> 57600a4848bf346c668f53f99fb9c8e2d2650917
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


