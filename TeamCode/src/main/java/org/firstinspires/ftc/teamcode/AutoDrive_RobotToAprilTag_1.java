package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class AutoDrive_RobotToAprilTag_1 extends LinearOpMode {
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor shootwheel;
    private Servo artifactstopper;
    boolean isShooting;
    double shootPower, horizontalInput, verticalInput;
    int maxDrivePower, mode;

    @Override
    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        shootwheel = hardwareMap.get(DcMotor.class, "shootwheel");
        artifactstopper = hardwareMap.get(Servo.class, "artifactstoppper");
        initialSetup();
        shootPower = 0.8;
        maxDrivePower = 1;
        mode = 0;
        waitForStart();
        pickMode();
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
}
