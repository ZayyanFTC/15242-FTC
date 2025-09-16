package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class MEEP_MEEP_TEST extends LinearOpMode {
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor shootwheel;
    private Servo artifactstopper;
}
public void runOpMode() {
    leftDrive = hardwareMap(DcMotor.class, "leftDrive");
    rightDrive = hardwareMap(DcMotor.class, "rightDrive");
    shootwheel = hardwareMap(DcMotor.class, "shootwheel");
    artifactstopper = hardwareMap(Servo.class, "artifactstopper");
}