package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Autonomous(name="JustBridge")
public class RunUnderBridge extends LinearOpMode {

    MecanumDrive Robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.Configure(hardwareMap);
        waitForStart();
        Robot.Move(23);
    }
}
