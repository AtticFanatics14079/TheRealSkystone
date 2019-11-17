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
        robot.UnhookFoundation();
        waitForStart();
        //strafe left
        //robot.Move(20,-1);
        //Forward, will ram foundation a little bit
        //sleep(5000);
        robot.Move(76.2);
        robot.HookFoundation();
        sleep(1500);
        //back to wall,
        robot.Move(-76.2);
        sleep(500);
        robot.UnhookFoundation();
        //strafe right under bridge, wall side
        //robot.StrafeEncoderTicks(61,1,hardwareMap);
    }
}