package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TeleOp_FTCSim extends LinearOpMode {
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor shootwheel;
    private Servo artifactstopper;
//    boolean isShooting;

    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        shootwheel = hardwareMap.get(DcMotor.class, "shootwheel");
        artifactstopper = hardwareMap.get(Servo.class, "shootwheel");

        waitForStart();
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
//        isShooting = false;
        artifactstopper.setPosition(0.2);

        while(opModeIsActive()) {
            double leftPower, rightPower;
            double drive = gamepad1.right_stick_x;
            double turn = -gamepad1.left_stick_y;
            leftPower = Range.clip(drive + turn, -1.0, 1.0);
            rightPower = Range.clip(drive - turn, -1.0, 1.0);
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            if(gamepad1.a) {
                artifactstopper.setPosition(0);
                shootwheel.setPower(1);
//                isShooting = true;
            }
            else{
                artifactstopper.setPosition(0.2);
                shootwheel.setPower(0);
//                isShooting = false;
            }
        }
    }
}
