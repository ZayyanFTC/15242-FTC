package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Autonomous1_DECODE_SM extends LinearOpMode {
    private DcMotor leftDrive;
    private DcMotor rightDrive;

    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        waitForStart();
        while(opModeIsActive()) {
            
        }
    }
}
