package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

@Autonomous
public class TestNewAuto extends LinearOpMode {

    MecanumDriveStates drive = new MecanumDriveStates();

    @Override
    public void runOpMode() throws InterruptedException {
        drive.Configure(hardwareMap);
        waitForStart();
        drive.drive(0.5, 10);
        while(opModeIsActive()){
            telemetry.addData("Motor 1 Veloc: ", drive.Motors[1].getVelocity());
            telemetry.addData("Motor 2 Veloc: ", drive.Motors[2].getVelocity());
            telemetry.addData("Motor 3 Veloc: ", drive.Motors[3].getVelocity());
            telemetry.addData("Motor 4 Veloc: ", drive.Motors[4].getVelocity());
            telemetry.addData("Motor 1 Pos: ", drive.Motors[1].getCurrentPosition());
            telemetry.addData("Motor 2 Pos: ", drive.Motors[2].getCurrentPosition());
            telemetry.addData("Motor 3 Pos: ", drive.Motors[3].getCurrentPosition());
            telemetry.addData("Motor 4 Pos: ", drive.Motors[4].getCurrentPosition());
            telemetry.update();
        };
    }
}
