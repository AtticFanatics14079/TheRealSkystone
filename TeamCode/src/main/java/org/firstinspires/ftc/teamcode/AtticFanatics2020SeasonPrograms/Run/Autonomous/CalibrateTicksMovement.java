package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Autonomous(name = "CalibrateTicksMove", group = "AutoOpModes")
public class CalibrateTicksMovement extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.Configure(hardwareMap);
        waitForStart();
        robot.Move(100); // halfway
        robot.Turn(90);
        //robot.TurnDegreesCurrentPos(1440,hardwareMap); // four full rotations

        telemetry.addData("Motor 1 Position: ", robot.Motors[1].getCurrentPosition());
        telemetry.addData("Motor 2 Position: ", robot.Motors[2].getCurrentPosition());
        telemetry.addData("Motor 3 Position: ", robot.Motors[3].getCurrentPosition());
        telemetry.addData("Motor 4 Position: ", robot.Motors[4].getCurrentPosition());

        while(opModeIsActive())
            telemetry.update();
    }
}
