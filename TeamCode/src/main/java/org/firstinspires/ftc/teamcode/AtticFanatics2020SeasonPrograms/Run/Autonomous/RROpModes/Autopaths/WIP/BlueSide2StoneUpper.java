package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.RROpModes.Autopaths.WIP;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.NoDriveConfigure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveREV;

//import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveBase;

@Autonomous(group = "drive")
public class BlueSide2StoneUpper extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveREV drive = new SampleMecanumDriveREV(hardwareMap);

        NoDriveConfigure mech = new NoDriveConfigure();
        mech.Configure(hardwareMap);
        Pose2d startPose = new Pose2d(-20.0,  63.0, Math.toRadians(270.0));// changing this might make the path faster
        Pose2d ingest1 = new Pose2d(-22.0,24.0, Math.toRadians(215.0));
        Pose2d ingest1stop = new Pose2d(-32, 14, Math.toRadians(215.0));
        Pose2d ingest2 = new Pose2d(-46.0,24.0, Math.toRadians(215.0));
        Pose2d ingest2stop = new Pose2d(-56.0,14.0, Math.toRadians(215.0));
        Pose2d foundationgrab = new Pose2d(50.0,25.0,Math.toRadians(90.0));
        Pose2d foundationdump = new Pose2d(20.0,40.0,Math.toRadians(180.0));
        Pose2d foundationshove = new Pose2d(50.0,40.0,Math.toRadians(180.0));
        Pose2d middlepassage = new Pose2d(0.0, 40.0, Math.toRadians(180.0));
        Pose2d parkposition = new Pose2d(0.0,35.0, Math.toRadians(180.0));

        drive.setPoseEstimate(startPose);
        Trajectory toStone1 = drive.trajectoryBuilder()
                .splineTo(ingest1) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest1);
        Trajectory ingestStone1 = drive.trajectoryBuilder()
                .splineTo (ingest1stop )
                .build();

        drive.setPoseEstimate(ingest1stop);
        Trajectory ingestStone1back = drive.trajectoryBuilder()
                .reverse()
                .splineTo (ingest1)
                .build();

        drive.setPoseEstimate(ingest1);
        Trajectory toFoundation1 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(middlepassage)
                .strafeTo(new Vector2d(middlepassage.getX()+40, middlepassage.getY()))
                .splineTo(new Pose2d(foundationgrab.getX(), foundationgrab.getY()+8, foundationgrab.getHeading()))
                .strafeTo(new Vector2d(foundationgrab.getX(),foundationgrab.getY()))
                .build();

        drive.setPoseEstimate(foundationgrab);
        Trajectory pullFoundation = drive.trajectoryBuilder()
                .splineTo(foundationdump)
                .build();

        drive.setPoseEstimate(foundationdump);
        Trajectory toStone2 = drive.trajectoryBuilder()
                .splineTo(ingest2) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest2);
        Trajectory ingestStone2 = drive.trajectoryBuilder()
                .splineTo(ingest2stop)
                .build();

        drive.setPoseEstimate(ingest2stop);
        Trajectory ingestStone2back = drive.trajectoryBuilder()
                .reverse()
                .splineTo (ingest2)
                .build();

        drive.setPoseEstimate(ingest2);
        Trajectory toFoundation2 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(foundationdump)
                .strafeTo(new Vector2d(foundationshove.getX(),foundationshove.getY()))
                .build();

        drive.setPoseEstimate(foundationshove);
        Trajectory park = drive.trajectoryBuilder()
                .splineTo(parkposition)
                .build();

        //Trajectory dragFoundation = drive.trajectoryBuilder()
        drive.setPoseEstimate(new Pose2d(-20.0, 63.0, toRadians(270.0)));

        waitForStart();
        drive.followTrajectorySync(toStone1);
        drive.followTrajectorySync(ingestStone1);
        drive.followTrajectorySync(ingestStone1back);
        drive.followTrajectorySync(toFoundation1);
        drive.followTrajectorySync(pullFoundation);
        drive.followTrajectorySync(toStone2);
        drive.followTrajectorySync(ingestStone2);
        drive.followTrajectorySync(ingestStone2back);
        drive.followTrajectorySync(toFoundation2);
        drive.followTrajectorySync(park);


    }

    private double toRadians(double degrees){
        return Math.toRadians(degrees);
    }

}
