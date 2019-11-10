package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;


//WE ARE STARTING AT BOTTOM EDGE OF TILE ONE BELOW BLUE BUILDING SITE
@Autonomous(name = "JustFoundation", group = "AutoOpModes")
public class JustFoundation extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException{
        //robot.setGrip(.1,hardwareMap);
        robot.Configure(hardwareMap);
        robot.FoundationLeft.setPosition(0.1333);
        robot.FoundationRight.setPosition(1);
        waitForStart();
        //strafe left
        robot.StrafeEncoderTicks(-20,-1,hardwareMap);
        //Forward, will ram foundation a little bit
        //sleep(5000);
        robot.MoveEncoderTicks(76.2,1,hardwareMap);
        robot.FoundationLeft.setPosition(0.6833);
        robot.FoundationRight.setPosition(0.45);
        sleep(1500);
        //back to wall,
        robot.MoveEncoderTicks(-76.2,-1,hardwareMap);
        sleep(500);
        robot.FoundationLeft.setPosition(0.1333);
        robot.FoundationRight.setPosition(1);
        //strafe right under bridge, wall side
        //robot.StrafeEncoderTicks(61,1,hardwareMap);
    }
}