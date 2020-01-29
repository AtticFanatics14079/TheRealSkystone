package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.RRTuning;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunnerAutoConfigure;

import kotlin.Unit;
import util.AngleUtils;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class FoundationTest extends LinearOpMode {
    RoadRunnerAutoConfigure mech = new RoadRunnerAutoConfigure();
    @Override
    public void runOpMode() throws InterruptedException {
        mech.Configure(hardwareMap);
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory driveToFoundation = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(-39,-15))
                .build();
        Trajectory back = drive.trajectoryBuilder()
                .splineTo(new Pose2d(34,40,Math.toRadians(90)))
                .build();
        Trajectory push = drive.trajectoryBuilder()
                .back(50)
                .build();
        Trajectory strafe = drive.trajectoryBuilder()
                .strafeLeft(27.0)
                .build();
        Trajectory park = drive.trajectoryBuilder()
                .forward(45)
                .build();
        waitForStart();

        if (isStopRequested()) return;
        mech.FoundationLeft.setPosition(RoadRunnerAutoConfigure.LEFT_OPEN);
        mech.FoundationRight.setPosition(RoadRunnerAutoConfigure.RIGHT_OPEN);
        drive.setPoseEstimate(new Pose2d(0,0,0));
        drive.followTrajectorySync(driveToFoundation);
        mech.FoundationLeft.setPosition(RoadRunnerAutoConfigure.LEFT_CLOSE);
        mech.FoundationRight.setPosition(RoadRunnerAutoConfigure.RIGHT_CLOSE);
        sleep(500);
        drive.setPoseEstimate(new Pose2d(0,0,0));
        drive.followTrajectorySync(back);
        drive.turnSync(Math.toRadians(30));
        //MAKE THIS AN IMU TURN
        drive.followTrajectorySync(push);
        drive.followTrajectorySync(strafe);
        mech.FoundationLeft.setPosition(RoadRunnerAutoConfigure.LEFT_OPEN);
        mech.FoundationRight.setPosition(RoadRunnerAutoConfigure.RIGHT_OPEN);
        drive.followTrajectorySync(park);
    }
}
