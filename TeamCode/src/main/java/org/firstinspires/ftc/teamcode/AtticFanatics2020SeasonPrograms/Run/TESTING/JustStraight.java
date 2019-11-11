package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TESTING;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
@TeleOp (name = "JustStraight", group = "Testing")
public class JustStraight extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive erika = new MecanumDrive();
        erika.Configure(hardwareMap);
        waitForStart();
        erika.MoveEncoderTicks(50,1,hardwareMap);
    }
}
