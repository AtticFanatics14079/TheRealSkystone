package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.OldTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Disabled
@Autonomous(name = "CalibrateTicksTurnIMU", group = "AutoOpModes")
public class CalibrateTicksTurningIMU extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.Configure(hardwareMap);
        waitForStart();
        //robot.StrafeEncoderTicks(183,1, hardwareMap); // halfway

        robot.Turn(360, 1, true);

        telemetry.addData("Motor 1 Position: ", robot.Motors[1].getCurrentPosition());
        telemetry.addData("Motor 2 Position: ", robot.Motors[2].getCurrentPosition());
        telemetry.addData("Motor 3 Position: ", robot.Motors[3].getCurrentPosition());
        telemetry.addData("Motor 4 Position: ", robot.Motors[4].getCurrentPosition());

        while(opModeIsActive())
            telemetry.update();
    }
}
