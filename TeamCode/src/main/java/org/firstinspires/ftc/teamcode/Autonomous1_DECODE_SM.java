package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous1_DECODE-SM", group="STEAMachines_DECODE")
public class Autonomous1_DECODE_SM extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive;
    private DcMotor rightDrive;

    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        waitForStart();

        //Move (forward)
        leftDrive.setPower(-0.7);
        rightDrive.setPower(0.7);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.7)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        //Turn (left)
        leftDrive.setPower(0.3);
        rightDrive.setPower(0.3);
        runtime.reset();
        while(opModeIsActive() && runtime.seconds()<0.3) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }
}
