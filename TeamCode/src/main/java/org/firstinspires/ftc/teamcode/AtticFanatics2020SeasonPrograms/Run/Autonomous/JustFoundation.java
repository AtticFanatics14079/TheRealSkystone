package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;


//WE ARE STARTING AT BOTTOM EDGE OF TILE ONE BELOW BLUE BUILDING SITE
//@Autonomous(name = "JustFoundation", group = "AutoOpModes")
/*public class JustFoundation extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException{
        //robot.setGrip(.1,hardwareMap);
        waitForStart();
        //strafe left
        robot.MoveEncoderTicks(45,-1,0,hardwareMap);
        //Forward, will ram foundation a little bit
        robot.MoveEncoderTicks(85,0,1,hardwareMap);

        //grab foundation
        sleep(1500);
        //back to wall,
        robot.MoveEncoderTicks(85,0,-1,hardwareMap);
        sleep(500);
        //strafe right under bridge, wall side
        robot.MoveEncoderTicks(140,1,0,hardwareMap);
    }
}
*/