package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.RROpModes.Autopaths.ThreeStone;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.NoDriveConfigure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveREV;

import kotlin.Unit;

//import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveBase;

@Autonomous(group = "drive")
public class RedSide3StoneUpper extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveREV drive = new SampleMecanumDriveREV(hardwareMap);
        NoDriveConfigure mech = new NoDriveConfigure();
        mech.Configure(hardwareMap);
        Pose2d startPose = new Pose2d(-30.5,  -63.0, Math.toRadians(90.0));// changing this might make the path faster
        Pose2d ingest1 = new Pose2d(-26.5,-25.5, Math.toRadians(60.0));
        Pose2d ingest2 = new Pose2d(-48.0,-23.0, Math.toRadians(90.0));
        Pose2d ingest3 = new Pose2d(-32.0, -24.0, Math.toRadians(120.0));
        Pose2d ingest3stop = new Pose2d (-36.0, -18.0, Math.toRadians(90));
        Pose2d foundationgrab = new Pose2d(48.0,-25.0,Math.toRadians(270.0));
        Pose2d foundationmid = new Pose2d (40.0, -40.0, Math.toRadians(225.0));
        Pose2d foundationdump = new Pose2d(20.0,-40.0,Math.toRadians(180.0));
        Pose2d foundationpickup = new Pose2d (20.0,-40.0,Math.toRadians(180.0));
        Pose2d foundationshove = new Pose2d(51.0,-40.0,Math.toRadians(180.0));
        Pose2d middlepassage = new Pose2d(0.0, -40.0, Math.toRadians(180.0));
        Pose2d parkposition = new Pose2d(4.0,-36.0, Math.toRadians(180.0));


//        mech.ingester.setPower(0.5);

        drive.setPoseEstimate(startPose);
        Trajectory toStone1 = drive.trajectoryBuilder()
                .splineTo(ingest1) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest1);
        Trajectory toFoundation1 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(middlepassage)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(0);
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[2]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[2]);
                    mech.ScissorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    mech.ScissorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    mech.ScissorLeft.setPower(1);
                    mech.ScissorRight.setPower(1);
                    return Unit.INSTANCE;
                })
                .strafeTo(new Vector2d(middlepassage.getX()+40, middlepassage.getY()))
                .splineTo(new Pose2d(foundationgrab.getX(), foundationgrab.getY()-8, foundationgrab.getHeading()))
                .strafeTo(new Vector2d(foundationgrab.getX(),foundationgrab.getY()))
                .build();


        drive.setPoseEstimate(foundationgrab);
        Trajectory pullFoundation = drive.trajectoryBuilder()
                .addMarker(0.5,() ->{
                    mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
                    return Unit.INSTANCE;
                })
                .addMarker(1.1,() ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationmid)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(0.5);
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[0]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[0]);
                    return Unit.INSTANCE;
                })
                .addMarker(new Vector2d(foundationdump.getX()+5,foundationdump.getY()-15),() ->{
                    mech.FoundationLeft.setPosition(NoDriveConfigure.LEFT_OPEN);
                    mech.FoundationRight.setPosition(NoDriveConfigure.RIGHT_OPEN);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationdump)
                .splineTo(middlepassage)
                .splineTo(ingest2) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest2);
        Trajectory toFoundation2 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(middlepassage)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(0);
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[2]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[2]);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationpickup)
                .build();

        drive.setPoseEstimate(foundationdump);
        Trajectory toStone3 = drive.trajectoryBuilder()
                .splineTo(ingest3)
                .splineTo(ingest3stop)
                .build();

        drive.setPoseEstimate(ingest3);
        Trajectory toFoundation3 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(middlepassage)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(0);
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[2]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[2]);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationpickup)
                .build();

        drive.setPoseEstimate(foundationpickup);
        Trajectory foundationshoving = drive.trajectoryBuilder()
                .reverse()
                .addMarker(() -> {
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[0]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[0]);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationshove)
                .build();

        drive.setPoseEstimate(foundationshove);
        Trajectory park = drive.trajectoryBuilder()
                .splineTo(parkposition)
                .build();

        //Trajectory dragFoundation = drive.trajectoryBuilder()
        drive.setPoseEstimate(startPose);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
        waitForStart();
            mech.IngesterMotor.setPower(0.5); //Off is the same but setPower(0)
        drive.followTrajectorySync(toStone1);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_CLOSED);
        drive.followTrajectorySync(toFoundation1);
            mech.ExtendGripper.setTargetPosition(NoDriveConfigure.EXTEND_OUT);
            mech.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mech.ExtendGripper.setPower(1);

            mech.FoundationLeft.setPosition(NoDriveConfigure.LEFT_CLOSE);
            mech.FoundationRight.setPosition(NoDriveConfigure.RIGHT_CLOSE);
            sleep(500);

            drive.followTrajectorySync(pullFoundation);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_CLOSED);
        drive.followTrajectorySync(toFoundation2);
            mech.ExtendGripper.setTargetPosition(NoDriveConfigure.EXTEND_OUT);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(600);
            mech.ExtendGripper.setTargetPosition(0);
            sleep(400);
        mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[0]);
        mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[0]);
            mech.IngesterMotor.setPower(0.5);
        drive.followTrajectorySync(toStone3);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_CLOSED);
        drive.followTrajectorySync(toFoundation3);
            mech.ExtendGripper.setTargetPosition(NoDriveConfigure.EXTEND_OUT);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(600);
            mech.ExtendGripper.setTargetPosition(0);
            sleep(400);
        drive.followTrajectorySync(foundationshoving);
        drive.followTrajectorySync(park);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(1000);


    }

    private double toRadians(double degrees){
        return Math.toRadians(degrees);
    }

}
