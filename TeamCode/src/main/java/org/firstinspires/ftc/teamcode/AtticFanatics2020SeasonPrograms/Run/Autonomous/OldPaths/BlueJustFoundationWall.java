package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.OldPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Disabled
@Autonomous(name = "Blue Foundation - Wall")
public class BlueJustFoundationWall extends LinearOpMode {
    MecanumDrive Robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {

        Robot.Configure(hardwareMap);
        Robot.UnhookFoundation();
        waitForStart();
        Robot.Move(14, 0.9);
        //Robot.Move(5);

        Robot.Move(32);
        Robot.Move(2, 0.3, false);
        Robot.HookFoundation();
        //Robot.Move(5, -0.8, false);
        Robot.Motors[1].setPower(-1);
        Robot.Motors[2].setPower(-1);
        Robot.Motors[3].setPower(-0.07);
        Robot.Motors[4].setPower(-0.07);
        sleep(3200);
        Robot.setPower(0, 0, 0);
        Robot.Move(2, -1);
        Robot.Move(35, 0.6, false);
        Robot.UnhookFoundation();
        Robot.Move(13, 1);
        Robot.Move(-45);

    }
}
