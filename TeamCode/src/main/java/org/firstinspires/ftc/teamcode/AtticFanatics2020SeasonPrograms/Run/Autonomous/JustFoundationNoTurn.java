package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Disabled
@Autonomous(name = "JustFoundationBlueSide")
public class JustFoundationNoTurn extends LinearOpMode {

    MecanumDrive Robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.Configure(hardwareMap);
        waitForStart();
        Robot.Move(30, -0.8);
        Robot.Move(66);
        Robot.Move(18, 0.3, false);
        Robot.HookFoundation();
        Robot.Move(85, -0.5, false);
        Robot.UnhookFoundation();
        Robot.Move(129, -0.8);
    }
}
