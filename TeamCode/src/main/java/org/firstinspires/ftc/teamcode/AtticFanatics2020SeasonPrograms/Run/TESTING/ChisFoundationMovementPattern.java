package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TESTING;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;

public class ChisFoundationMovementPattern extends LinearOpMode {

    MecanumDrive robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.Configure(hardwareMap);
        robot.FoundationLeft.setPosition(0.1333);
        robot.FoundationRight.setPosition(1);
        waitForStart();
        //robot.StrafeEncoderTicks(-15, -1, hardwareMap);
        robot.MoveEncoderTicks(70, 1, hardwareMap);
        robot.MoveEncoderTicks(7, 0.5, hardwareMap);
        robot.FoundationLeft.setPosition(0.6888);
        robot.FoundationRight.setPosition(0.53);
        sleep(300);
        robot.StrafeEncoderTicks(-55, -1, hardwareMap);
        robot.DiagonalEncoderTicks(-200, -1, -2, hardwareMap);
        telemetry.addLine("Le foundatione hath been yeeteth");
        telemetry.update();
    }
}
