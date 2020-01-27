package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.RRTuning;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import util.AngleUtils;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class FoundationTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);
        Trajectory driveToFoundation = drive.trajectoryBuilder()
                .forward(33.0)
                .build();
        Trajectory back = drive.trajectoryBuilder()
                .back(8.0)
                .build();
        Trajectory push = drive.trajectoryBuilder()
                .forward(15)
                .build();
        Trajectory parkSkybridge = drive.trajectoryBuilder()
                .strafeLeft(15.0)
                .back(40)
                .build();
        waitForStart();

        if (isStopRequested()) return;
        drive.followTrajectorySync(driveToFoundation);
        //Grab Foundation
        drive.turnSync(Math.toRadians(30));
        drive.followTrajectorySync(back);
        drive.turnSync(Math.toRadians(30));
        drive.followTrajectorySync(back);
        drive.turnSync(Math.toRadians(30));
        drive.followTrajectorySync(back);
        //Release Foundation
        drive.followTrajectorySync(push);
        drive.followTrajectorySync(parkSkybridge);
    }
}
