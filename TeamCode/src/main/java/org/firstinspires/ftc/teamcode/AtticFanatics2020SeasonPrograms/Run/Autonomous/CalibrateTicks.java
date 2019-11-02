package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Autonomous(name = "CalibrateTicks", group = "AutoOpModes")
public class CalibrateTicks extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        robot.MoveEncoderTicks(183,0,1,hardwareMap); // halfway

        //robot.TurnDegreesEncoder(1440,hardwareMap); // four full rotations
    }
}
