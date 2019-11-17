package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Autonomous(name = "CalibrateTicksPreciseMove", group = "AutoOpModes")
public class TestingExtraPreciseMove extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.Configure(hardwareMap);
        waitForStart();
        robot.Move(57.79, 1, true); // halfway
        //robot.TurnDegreesCurrentPos(1440,hardwareMap); // four full rotations

        telemetry.addData("Motor 1 Position: ", robot.Motors[1].getCurrentPosition());
        telemetry.addData("Motor 2 Position: ", robot.Motors[2].getCurrentPosition());
        telemetry.addData("Motor 3 Position: ", robot.Motors[3].getCurrentPosition());
        telemetry.addData("Motor 4 Position: ", robot.Motors[4].getCurrentPosition());
        telemetry.addData("IMU Heading", robot.CurrentPos.firstAngle);

        while(opModeIsActive())
            telemetry.update();
    }
}
