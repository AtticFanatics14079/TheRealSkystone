package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

//WE ARE STARTING ON FOUNDATION SIDE
public class JustFoundation extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException{
        robot.setGrip(.1,hardwareMap);
        waitForStart();
        //strafe left
        robot.MoveEncoderTicks(60,1,0,hardwareMap);
        //Forward
        robot.MoveEncoderTicks(125,0,1,hardwareMap);

        //grab foundation
        sleep(1000);
        //back to wall,
        robot.MoveEncoderTicks(125,0,-1,hardwareMap);
        //strafe right under bridge
        robot.MoveEncoderTicks(180,1,0,hardwareMap);
    }
}
