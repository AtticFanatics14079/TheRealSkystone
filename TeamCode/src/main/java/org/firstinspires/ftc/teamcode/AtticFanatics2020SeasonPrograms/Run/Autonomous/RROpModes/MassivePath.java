package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.RROpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.NoDriveConfigure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveREV;

@Autonomous(group = "drive")
public class MassivePath extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveREV drive = new SampleMecanumDriveREV(hardwareMap);

        NoDriveConfigure mech = new NoDriveConfigure();
        mech.Configure(hardwareMap);

        drive.setPoseEstimate(new Pose2d(-20.0, -63.0, toRadians(90.0)));
        Trajectory toStone1 = drive.trajectoryBuilder()
                .splineTo(new Pose2d(-30.0,-24.0, toRadians(135))) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(new Pose2d(-30.0, -24.0, toRadians(135)));
        Trajectory ingestStone1 = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(-40.0,-14.0))
                .strafeTo(new Vector2d(-30.0, -24.0))
                .build();

        drive.setPoseEstimate(new Pose2d(-30.0,-24.0, toRadians(135)));
        Trajectory toFoundation1 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(0.0,-40.0, toRadians(180)))
                .strafeTo(new Vector2d(40.0,-40.0))
                .splineTo(new Pose2d(50.0,-33.0,toRadians(270)))
                .strafeTo(new Vector2d(50.0,-25.0))
                .build();

        drive.setPoseEstimate(new Pose2d(50.0,-25.0,toRadians(270)));
        Trajectory pullFoundation = drive.trajectoryBuilder()
                .splineTo(new Pose2d(20.0,-40.0,toRadians(180)))
                .build();

        drive.setPoseEstimate(new Pose2d(20.0,-40.0,toRadians(180)));
        Trajectory toStone2 = drive.trajectoryBuilder()
                .splineTo(new Pose2d(-38.0,-35.0,toRadians(135))) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(new Pose2d(-38.0, -35.0, toRadians(135)));
        Trajectory ingestStone2 = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(-46.0, -21.0))
                .splineTo(new Pose2d(-57.0, -21.0, toRadians(180)))
                .strafeTo(new Vector2d(-38.0, -35.0))
                .build();

        drive.setPoseEstimate(new Pose2d(-38.0,-21.0,toRadians(180)));
        Trajectory toFoundation2 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(20.0,-40.0,toRadians(180)))
                .strafeTo(new Vector2d(50.0,-40.0))
                .build();

        drive.setPoseEstimate(new Pose2d(50,-40,toRadians(180)));
        Trajectory park = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0.0,-35.0, toRadians(180)))
                .build();

        //Trajectory dragFoundation = drive.trajectoryBuilder()
        drive.setPoseEstimate(new Pose2d(-20.0, -63.0, toRadians(90.0)));

        waitForStart();
        drive.followTrajectorySync(toStone1);
        drive.followTrajectorySync(ingestStone1);
        drive.followTrajectorySync(toFoundation1);
        drive.followTrajectorySync(pullFoundation);
        drive.followTrajectorySync(toStone2);
        drive.followTrajectorySync(ingestStone2);
        drive.followTrajectorySync(toFoundation2);
        drive.followTrajectorySync(park);


    }

    private double toRadians(double degrees){
        return Math.toRadians(degrees);
    }

}
