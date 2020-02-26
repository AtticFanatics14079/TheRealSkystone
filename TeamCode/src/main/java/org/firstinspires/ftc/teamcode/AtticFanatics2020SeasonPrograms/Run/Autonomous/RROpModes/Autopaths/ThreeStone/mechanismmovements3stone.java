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

import static org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure.EXTEND_OUT;
import static org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure.GRIPPER_CLOSED;
import static org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure.GRIPPER_OPEN;

//import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveBase;

@Autonomous(group = "drive")
public class mechanismmovements3stone extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveREV drive = new SampleMecanumDriveREV(hardwareMap);
        NoDriveConfigure mech = new NoDriveConfigure();
        mech.Configure(hardwareMap);
        mech.ScissorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mech.ScissorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mech.ScissorLeft.setTargetPosition(0);
        mech.ScissorRight.setTargetPosition(0);
        mech.ScissorLeft.setPower(1);
        mech.ScissorRight.setPower(1);
        mech.ScissorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mech.ScissorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mech.ExtendGripper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mech.ExtendGripper.setTargetPosition(0);
        mech.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mech.ExtendGripper.setPower(0.8);


        Pose2d startPose = new Pose2d(-30.5,  -63.0, Math.toRadians(90.0));
        Pose2d Position1 = new Pose2d(-30.5, -53.0, Math.toRadians(91.0));
        Pose2d Position2 = new Pose2d(-30.5, -43.0, Math.toRadians(92.0));
        Pose2d Position3 = new Pose2d(-30.5, 23.0, Math.toRadians(93.0));

//        mech.ingester.setPower(0.5);
        drive.setPoseEstimate(Position1);
        Trajectory foundationshoving = drive.trajectoryBuilder()
                .addMarker(0, () -> {
                    mech.IngesterMotor.setPower(0);
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[2]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[2]);
                    return Unit.INSTANCE;
                })
                .addMarker(0.5,() ->{
                    mech.ExtendGripper.setTargetPosition(NoDriveConfigure.EXTEND_OUT);
                    return Unit.INSTANCE;
                })
                .addMarker(0.9,() ->{
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[1]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[1]);
                    return Unit.INSTANCE;
                })
                .addMarker(1,() ->{
                    mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
                    return Unit.INSTANCE;
                })
                .addMarker(1.2,() ->{
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[2]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[2]);
                    return Unit.INSTANCE;
                })
                .addMarker(1.6,() ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .addMarker(2,() ->{
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[0]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[0]);
                    return Unit.INSTANCE;
                })
                .splineTo(Position3)//MARKERS WILL RUN UNTIL THIS IS COMPLETED!!!
                .splineTo(Position1)
                .build();

        drive.setPoseEstimate(Position1);
        Trajectory drive2 = drive.trajectoryBuilder()

                .splineTo(Position2)
                .build();

        drive.setPoseEstimate(Position2);
        Trajectory drive3 = drive.trajectoryBuilder()

                .splineTo(Position3)
                .build();

        drive.setPoseEstimate(startPose);
        mech.Gripper.setPosition(GRIPPER_CLOSED);
        waitForStart();
        drive.followTrajectorySync(foundationshoving);
        drive.followTrajectorySync(drive2);
        //drive.followTrajectorySync(drive3);
    }

    private double toRadians(double degrees){
        return Math.toRadians(degrees);
    }

}
