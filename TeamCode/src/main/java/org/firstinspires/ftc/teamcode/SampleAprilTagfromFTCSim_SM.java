
/***********************************************************************
 *                                                                      *
 * OnbotJava Editor is still : beta! Please inform us of any bugs       |
 * on our discord channel! https://discord.gg/e7nVjMM                   *
 * Only BLOCKS code is submitted when in Arena                          *
 *                                                                      *
 ***********************************************************************/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyFIRSTJavaOpMode extends LinearOpMode {
    DcMotor driveLeft;
    DcMotor driveRight;
    DcMotor shootwheel;
    Servo artifactstopper;
    ColorSensor color1;
    DistanceSensor distance1;
    IMU imu;

    double myVisionPortalBuilder, nArtifacts, myAprilTagDetections, myVisionPortal, horizontalInput, myAprilTagDetection, shootPower, isShooting, verticalInput, myApriltagProcessor, maxDrivePower, myAprilTagProcessorBuilder, mode;

    // Describe this function...
    public void initializeVisionPortal(){
        myVisionPortalBuilder = new VisionPortal.Builder();
        myVisionPortal = (myVisionPortalBuilder.build());
        myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "webcam"));
        myAprilTagProcessorBuilder = new AprilTagProcessor.Builder();
        myApriltagProcessor = (myAprilTagProcessorBuilder.build());
        myVisionPortalBuilder.addProcessor(myApriltagProcessor);
    }

    // Describe this function...
    public void inititalSetup(){
        // Put initialization blocks here
        driveLeft.setDirection(DcMotor.Direction.REVERSE);
        isShooting = false;
        // Holds back artifacts until we start shooting
        artifactstopper.setPosition(0.2);
    }

    // Describe this function...
    public void pickMode(){
        // Switch based on the mode variable
        if (mode == 0) {
            keyboardDrive();
        } else if (mode == 1) {
            gamepadDrive();
        } else if (mode == 2) {
            autoDrive();
        }
    }

    // Describe this function...
    public void keyboardDrive(){
        while (opModeIsActive()) {
            // Convert keyboard input to a final direction value
            horizontalInput = keyboard.isPressed(108) - keyboard.isPressed(106);
            verticalInput = keyboard.isPressed(105) - keyboard.isPressed(107);
            processDriveInputs();
            if (keyboard.isPressed(112) && !isShooting) {
                shoot();
            }
            displayVisionPortalData();
        }
    }

    // Describe this function...
    public void gamepadDrive(){
        while (opModeIsActive()) {
            horizontalInput = gamepad1.right_stick_x;
            verticalInput = gamepad1.left_stick_y;
            processDriveInputs();
            if (gamepad1.a && !isShooting) {
                shoot();
            }
            displayVisionPortalData();
        }
    }

    // Describe this function...
    public void autoDrive(){
        driveToGoal();
        shootThreeArtifacts();
        driveToLoadingSpotAndBack();
        shootThreeArtifacts();
        keyboardDrive();
    }

    // Describe this function...
    public void driveToGoal(){
        driveLeft.setPower(1);
        driveRight.setPower(1);
        sleep(1200);
        driveLeft.setPower(-1);
        driveRight.setPower(1);
        sleep(230);
        driveLeft.setPower(0);
        driveRight.setPower(0);
        sleep(500);
    }

    // Describe this function...
    public void driveToLoadingSpotAndBack(){
        driveLeft.setPower(-1);
        driveRight.setPower(-1);
        sleep(1500);
        driveLeft.setPower(0);
        driveRight.setPower(0);
        sleep(10000);
        driveLeft.setPower(1);
        driveRight.setPower(1);
        sleep(1500);
        driveLeft.setPower(0);
        driveRight.setPower(0);
        sleep(500);
    }

    // Describe this function...
    public void shootThreeArtifacts(){
        nArtifacts = 3;
        while (opModeIsActive() && nArtifacts > 0) {
            // Put loop blocks here
            if (!isShooting) {
                shoot();
                nArtifacts -= 1;
            }
            displayVisionPortalData();
        }
    }

    // Describe this function...
    public void processDriveInputs(){
        // Combine inputs to create drive and turn (or both!)
        driveLeft.setPower(verticalInput * maxDrivePower + horizontalInput * maxDrivePower);
        driveRight.setPower(verticalInput * maxDrivePower - horizontalInput * maxDrivePower);
    }

    // Describe this function...
    public void shoot(){
        // Don"t move while shooting
        driveLeft.setPower(0);
        driveRight.setPower(0);
        isShooting = true;
        // Let one artifact come through
        artifactstopper.setPosition(0);
        shootwheel.setPower(shootPower);
        sleep(250);
        // Stop the next artifact
        artifactstopper.setPosition(0.2);
        sleep(200);
        shootwheel.setPower(0);
        sleep(1500);
        // Allow for a new shot to be triggered
        isShooting = false;
    }

    // Describe this function...
    public void displayVisionPortalData(){
        myAprilTagDetections = (myApriltagProcessor.getDetections());
        for (String myAprilTagDetection2 : myAprilTagDetections) {
            myAprilTagDetection = myAprilTagDetection2;
            telemetry.addData("ID", (myAprilTagDetection.id));
            telemetry.addData("Range", (myAprilTagDetection.ftcPose.range));
            telemetry.addData("Yaw", (myAprilTagDetection.ftcPose.yaw));
        }
        telemetry.update();
    }


    @Override
    public void runOpMode() {
        driveLeft = hardwareMap.get(DcMotor.class, "driveLeft");
        driveRight = hardwareMap.get(DcMotor.class, "driveRight");
        shootwheel = hardwareMap.get(DcMotor.class, "shootwheel");
        artifactstopper = hardwareMap.get(Servo.class, "artifactstopper");
        color1 = hardwareMap.get(ColorSensor.class, "color1");
        distance1 = hardwareMap.get(DistanceSensor.class, "distance1");
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        inititalSetup();
        initializeVisionPortal();
        shootPower = 0.8;
        maxDrivePower = 1;
        // mode 0 = keyboard, 1 = gamepad, 2 = autonomous
        mode = 2;
        waitForStart();
        //   pickMode();
        displayVisionPortalData();
    }

}
