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
        robot.FoundationLeft.setPosition(0);
        robot.FoundationRight.setPosition(0);
        waitForStart();
        //strafe left
        robot.StrafeEncoderTicks(-45,-1,hardwareMap);
        //Forward, will ram foundation a little bit
        robot.MoveEncoderTicks(85,1,hardwareMap);

        robot.FoundationLeft.setPosition(1);
        robot.FoundationRight.setPosition(1);
        sleep(1500);
        //back to wall,
        robot.MoveEncoderTicks(-85,-1,hardwareMap);
        sleep(500);
        //strafe right under bridge, wall side
        robot.StrafeEncoderTicks(140,1,hardwareMap);
    }
}