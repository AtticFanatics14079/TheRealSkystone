package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.Recorded;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Autonomous(name="StraightBridgeInside") //CHANGE TO MATCH AUTO PATH NAME!!!
public class StraightBridgeInside extends LinearOpMode {

   MecanumDrive robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
    robot.setPower(1,0,0);
    wait(150);
    robot.setPower(0,1,0);
    wait(75);
    robot.setPowerZero();


    }
}