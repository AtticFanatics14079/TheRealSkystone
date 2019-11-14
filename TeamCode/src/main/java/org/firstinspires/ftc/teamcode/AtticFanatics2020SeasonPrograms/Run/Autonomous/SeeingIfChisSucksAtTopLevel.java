package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

public class SeeingIfChisSucksAtTopLevel extends LinearOpMode {

    MecanumDrive robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.Configure(hardwareMap);
        waitForStart();

        robot.GrabDrop(true);
        robot.Move(10);
        robot.Turn(-90);
        robot.ExtendGripper(20);
        robot.GrabDrop(false);
        robot.ExtendGripper(-20);
        robot.Turn(90);
        robot.Move(50);
        robot.Move(10,1);
        robot.Move(20, 0.5, -1);
    }
}
