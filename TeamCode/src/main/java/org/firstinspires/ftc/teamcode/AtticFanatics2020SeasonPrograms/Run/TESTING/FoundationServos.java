/*package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TESTING;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

public class FoundationServos extends LinearOpMode {

    MecanumDrive robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.Configure(hardwareMap);
        waitForStart();

        robot.MoveEncoderTicks(1, 0, hardwareMap);

        robot.FoundationLeft.setPosition(1);
        robot.FoundationRight.setPosition(1);

        sleep(3000);

        robot.FoundationRight.setPosition(0);
        robot.FoundationLeft.setPosition(0);
    }
}


 */