package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class MEEP_MEEP_TEST extends LinearOpMode {
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor shootwheel;
    private Servo artifactstopper;
    boolean isShooting;
    int mode;

    public void runOpMode() {
        leftDrive = hardwareMap(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap(DcMotor.class, "rightDrive");
        shootwheel = hardwareMap(DcMotor.class, "shootwheel");
        artifactstopper = hardwareMap(Servo.class, "artifactstopper");
        initialSetup();
        pickMode();
        mode = 0
    }
    public void initialSetup() {
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        isShooting = false;
        artifactstopper.setPosition(0.2);
    }
    public void pickMode() {
        if (mode == 0) {
            autoDrive();
        }
        else if (mode == 1) {
            gamepadDrive();
        }
    }
    public void autoDrive() {

    }
    public void gamepadDrive() {
        
    }
}