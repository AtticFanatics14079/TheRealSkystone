package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

public class gfdhjsk extends LinearOpMode {

    MecanumDrive drive = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();
        drive.Move(52);
        drive.Turn(-90);
        drive.Move(2);
        drive.HookFoundation();
        drive.Turn(60);
        drive.Move(40);
    }
}
