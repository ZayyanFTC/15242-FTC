package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class SamplefromFTCSim_SM extends LinearOpMode{

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
        artifactstopper = hardwareMap.get(Servo.class , "artifactsstopper");
        initialSetup();
        shootPower = 0.8;
        maxDrivePower = 1;
        mode = 2;
        waitForStart();
        pickMode();
        initializeVisionPortal();
        displayAprilTag();
    }
    public void initialSetup() {
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        isShooting = false;
        artifactstopper.setPosition(0.2);
    }
    public void pickMode() {
        if (mode == 0) {
            gamepadDrive();
        }
        else if (mode == 1) {
            autoDrive();
        }
    }
    public void gamepadDrive() {
        while(opModeIsActive()) {
            horizontalInput = gamepad1.right_stick_x; //nilai dari -1 hingga 1
            verticalInput = gamepad1.left_stick_y;
            processDriveInputs();
            if (gamepad1.a && !isShooting) {
                shoot();
            }
        }
    }
    public void processDriveInputs() {
        leftDrive.setPower(verticalInput * maxDrivePower + horizontalInput * maxDrivePower);
        rightDrive.setPower(verticalInput * maxDrivePower - horizontalInput * maxDrivePower);
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
        driveToGoal();
        shootThreeArtifacts();
        driveToLoadingSpotAndBack();
        shootThreeArtifacts();
    }
    public void driveToGoal() {
        leftDrive.setPower(1);
        rightDrive.setPower(1);
        sleep(1200);
        leftDrive.setPower(-1);
        rightDrive.setPower(-1);
        sleep(230);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        sleep(500);
    }
    public void shootThreeArtifacts() {
        nArtifacts = 3;
        while(opModeIsActive() && nArtifacts > 0) {
            if (!isShooting) {
                shoot();
                nArtifacts -= 1;
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
    private void initializeVisionPortal() {
        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                //.setDrawAxes(false)
                //.setDrawCubeProjection(false)
                //.setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                // ... these parameters are fx, fy, cx, cy.

                .build();

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        //aprilTag.setDecimation(3);

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        //visionPortal.setProcessorEnabled(aprilTag, true);
    }
    public void displayAprilTag() {
        List<AprilTagDetection> myAprilTagDetections = (aprilTag.getDetections());
        for (AprilTagDetection myAprilTag : myAprilTagDetections) {
            myAprilTagDetection = myAprilTag;
            telemetry.addData("ID", myAprilTagDetection.id);
            telemetry.addData("Range", myAprilTagDetection.ftcPose.range);
            telemetry.addData("Yaw", myAprilTagDetection.ftcPose.yaw);

        }
        telemetry.update();
    }
}

