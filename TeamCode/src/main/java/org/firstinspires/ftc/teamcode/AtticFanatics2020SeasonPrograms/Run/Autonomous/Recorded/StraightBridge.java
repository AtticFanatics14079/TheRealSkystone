package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.Recorded;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp2Configure;

@Autonomous(name="StraightBridge") //CHANGE TO MATCH AUTO PATH NAME!!!
public class StraightBridge extends LinearOpMode {

    Comp2Configure robot = new Comp2Configure();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.Configure(hardwareMap);
        waitForStart();
    robot.Motors[1].setPower(1);
    robot.Motors[2].setPower(1);
    robot.Motors[3].setPower(1);
    robot.Motors[4].setPower(1);
    sleep(300);
    robot.Motors[1].setPower(0);
    robot.Motors[2].setPower(0);
    robot.Motors[3].setPower(0);
    robot.Motors[4].setPower(0);


    }
}