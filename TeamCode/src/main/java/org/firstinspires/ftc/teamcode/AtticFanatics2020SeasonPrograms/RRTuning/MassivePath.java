package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.RRTuning;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(group = "drive")
public class MassivePath extends LinearOpMode {
    //RoadRunnerAutoConfigure mech = new RoadRunnerAutoConfigure();
    SampleMecanumDriveBase drive;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDriveREV(hardwareMap);
        drive.setPoseEstimate(new Pose2d(-20.0, -63.0, toRadians(90.0)));
        Trajectory toStone1 = drive.trajectoryBuilder()
                .splineTo(new Pose2d(-28.0,-26.0, toRadians(150))) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(new Pose2d(-28.0,-26.0, toRadians(150)));
        Trajectory toFoundation1 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(0.0,-40.0, toRadians(180)))
                .strafeTo(new Vector2d(40.0,-40.0))
                .splineTo(new Pose2d(50.0,-33.0,toRadians(270)))
                .strafeTo(new Vector2d(50.0,-25.0))
                .build();

        drive.setPoseEstimate(new Pose2d(50.0,-25.0,toRadians(270)));
        Trajectory pullFoundation = drive.trajectoryBuilder()
                .splineTo(new Pose2d(20.0,-50.0,toRadians(180)))
                .build();

        drive.setPoseEstimate(new Pose2d(20.0,-50.0,toRadians(180)));
        Trajectory toStone2 = drive.trajectoryBuilder()
                .splineTo(new Pose2d(-51.0,-26.0,toRadians(160))) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(new Pose2d(-51.0,-26.0,toRadians(160)));
        Trajectory toFoundation2 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(20.0,-50.0,toRadians(180)))
                .strafeTo(new Vector2d(50.0,-50.0))
                .build();

        drive.setPoseEstimate(new Pose2d(50,-50,toRadians(180)));
        Trajectory park = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(0.0,-35.0))
                .build();

        //Trajectory dragFoundation = drive.trajectoryBuilder()
        drive.setPoseEstimate(new Pose2d(-20.0, -63.0, toRadians(90.0)));

        waitForStart();
        drive.followTrajectorySync(toStone1);
        drive.followTrajectorySync(toFoundation1);
        drive.followTrajectorySync(pullFoundation);
        drive.followTrajectorySync(toStone2);
        drive.followTrajectorySync(toFoundation2);
        drive.followTrajectorySync(park);


    }

    private double toRadians(double degrees){
        return Math.toRadians(degrees);
    }

}
