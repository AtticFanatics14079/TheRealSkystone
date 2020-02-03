package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.RRTests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveREV;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class SplineTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);
        waitForStart();

        if (isStopRequested()) return;
        drive.followTrajectorySync(
                drive.trajectoryBuilder()
                        .splineTo(new Pose2d(48, 48, Math.toRadians(90)))
                        .build()
        );
        drive.followTrajectorySync(
                drive.trajectoryBuilder()
                        .reverse()
                    .splineTo(new Pose2d(0,0,0))
                    .build()
        );
    }
}
