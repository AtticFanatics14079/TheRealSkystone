package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.OldTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDriveStates;

@Autonomous(name = "CalibrateTicksTurn", group = "AutoOpModes")
public class CalibrateTicksTurning extends LinearOpMode {
    MecanumDriveStates robot = new MecanumDriveStates();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.Configure(hardwareMap);
        waitForStart();
        //robot.StrafeEncoderTicks(183,1, hardwareMap); // halfway

        //sleep(5000);

        robot.turnToDegree(90, 1);

        telemetry.addData("Motor 1 Position: ", robot.Motors[1].getCurrentPosition());
        telemetry.addData("Motor 2 Position: ", robot.Motors[2].getCurrentPosition());
        telemetry.addData("Motor 3 Position: ", robot.Motors[3].getCurrentPosition());
        telemetry.addData("Motor 4 Position: ", robot.Motors[4].getCurrentPosition());
        telemetry.update();

        while(opModeIsActive()) {
            System.out.println("Looping");
            telemetry.addData("IMU: ", robot.getHeading());
            telemetry.update();
        }
    }
}
