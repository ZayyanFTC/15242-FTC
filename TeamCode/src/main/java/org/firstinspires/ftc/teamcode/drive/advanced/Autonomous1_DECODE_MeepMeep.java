package org.firstinspires.ftc.teamcode.drive.advanced;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;

@Autonomous(name="Autonomous1_DECODE-SM_MeepMeep", group="STEAMachines_DECODE")
public class Autonomous1_DECODE_MeepMeep extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleTankDrive tankDrive = new SampleTankDrive(hardwareMap);
    }
}
