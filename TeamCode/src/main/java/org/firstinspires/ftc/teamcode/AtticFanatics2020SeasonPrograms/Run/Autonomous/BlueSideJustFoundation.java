package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Autonomous(name = "JustFoundationBlueSide")
public class BlueSideJustFoundation extends LinearOpMode {

    MecanumDrive Robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.Configure(hardwareMap);
        Robot.UnhookFoundation();
        waitForStart();
        Robot.Move(30, 1);
        Robot.Move(66);
        Robot.Move(18, 0.3, false);
        Robot.HookFoundation();
        Robot.Move(85, -0.5, false);
        Robot.UnhookFoundation();
        Robot.Move(129, -1);
    }
}
