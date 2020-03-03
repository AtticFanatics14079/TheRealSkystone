package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.RROpModes.Autopaths.Red;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.NoDriveConfigure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveREV;

import kotlin.Unit;

//import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveBase;

@Autonomous(group = "drive")
public class RedSide3StoneMiddle extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveREV drive = new SampleMecanumDriveREV(hardwareMap);
        NoDriveConfigure mech = new NoDriveConfigure();
        mech.Configure(hardwareMap);

        Pose2d startPose = new Pose2d(-30.5,  -63.0, Math.toRadians(90.0));// changing this might make the path faster
        Pose2d ingest1 = new Pose2d(-34.5,-25, Math.toRadians(55.0));
        Pose2d ingest2 = new Pose2d(-41,-16.5, Math.toRadians(90.0));
        Pose2d ingest2stop = new Pose2d(ingest2.getX()+3.5, ingest2.getY()-3.3, Math.toRadians(185.0));
        Pose2d ingest2drive = new Pose2d(ingest2.getX()-7.5, ingest2.getY()-5.0, Math.toRadians(185.0));
        Pose2d ingest3 = new Pose2d(-58, -21, Math.toRadians(185.0));
        Pose2d foundationgrab = new Pose2d(48.0,-25.0,Math.toRadians(270.0));
        Pose2d foundationmid = new Pose2d (40.0, -40.0, Math.toRadians(225.0));
        Pose2d foundationdump = new Pose2d(20.0,-43.0,Math.toRadians(180.0));
        Pose2d foundationpickup = new Pose2d (22,-35,Math.toRadians(180.0));
        Pose2d foundationshove = new Pose2d(51.0,-44.0,Math.toRadians(180.0));
        Pose2d middlepassage = new Pose2d(2.0, -36.5, Math.toRadians(180.0));
        Pose2d parkposition = new Pose2d(4.0,-39.0, Math.toRadians(180.0));


//        mech.ingester.setPower(0.8);

        drive.setPoseEstimate(startPose);
        Trajectory toStone1 = drive.GasTrajectoryBuilder()
                .strafeTo(new Vector2d(startPose.getX()-7.2, startPose.getY()+8.0))
                .splineTo(ingest1) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest1);
        Trajectory toFoundation1 = drive.GasTrajectoryBuilder()
                .addMarker(0.0,() ->{
                    mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
                    return Unit.INSTANCE;
                })
                .addMarker(0.4,() ->{
                    mech.IngesterMotor.setPower(0);
                    return Unit.INSTANCE;
                })
                .reverse()
                .splineTo(middlepassage)
                .addMarker(() -> {
                    mech.ScissorLeft.setTargetPosition(mech.levels[2]);
                    mech.ScissorRight.setTargetPosition(mech.levels[2]);
                    return Unit.INSTANCE;
                })
                .strafeTo(new Vector2d(middlepassage.getX()+40, middlepassage.getY()))
                .addMarker(() ->{
                    mech.ExtendGripper.setTargetPosition(mech.EXTEND_OUT);
                    return Unit.INSTANCE;
                })
                .splineTo(new Pose2d(foundationgrab.getX(), foundationgrab.getY()-8, foundationgrab.getHeading()))
                .strafeTo(new Vector2d(foundationgrab.getX(),foundationgrab.getY()))
                .build();

        drive.setPoseEstimate(foundationgrab);
        Trajectory pullFoundation = drive.GasTrajectoryBuilder()
                .addMarker(() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[3]);
                    mech.ScissorRight.setTargetPosition(mech.levels[3]);
                    return Unit.INSTANCE;
                })
                .addMarker(0.2,() ->{
                    mech.Gripper.setPosition(mech.GRIPPER_OPEN);
                    return Unit.INSTANCE;
                })
                .addMarker(0.5,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[2]);
                    mech.ScissorRight.setTargetPosition(mech.levels[2]);
                    return Unit.INSTANCE;
                })
                .addMarker(0.9,() ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .addMarker(1.3,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[0]);
                    mech.ScissorRight.setTargetPosition(mech.levels[0]);
                    return Unit.INSTANCE;
                })
                /*.addMarker(0.0,() ->{
                    mech.ExtendGripper.setTargetPosition(mech.EXTEND_OUT);
                    mech.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    mech.ExtendGripper.setPower(1);
                    return Unit.INSTANCE;
                })
                .addMarker(0.2,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[1]);
                    mech.ScissorRight.setTargetPosition(mech.levels[1]);
                    return Unit.INSTANCE;
                })
                .addMarker(0.3, () ->{
                    mech.Gripper.setPosition(mech.GRIPPER_OPEN);
                    return Unit.INSTANCE;
                })
                .addMarker(0.4,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[2]);
                    mech.ScissorRight.setTargetPosition(mech.levels[2]);
                    return Unit.INSTANCE;
                })
                .addMarker(1.0,() ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .addMarker(1.3,() -> {
                    mech.IngesterMotor.setPower(0.8);
                    mech.ScissorLeft.setTargetPosition(mech.levels[0]);
                    mech.ScissorRight.setTargetPosition(mech.levels[0]);
                    return Unit.INSTANCE;
                })
               /* .addMarker(new Vector2d(foundationdump.getX()+5,foundationdump.getY()-15),() ->{
                    mech.FoundationLeft.setPosition(mech.LEFT_OPEN);
                    mech.FoundationRight.setPosition(mech.RIGHT_OPEN);
                    return Unit.INSTANCE;
                })
                */
                .splineTo(foundationmid)
                .splineTo(foundationdump)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(-0.8);
                    mech.FoundationLeft.setPosition(mech.LEFT_OPEN);
                    mech.FoundationRight.setPosition(mech.RIGHT_OPEN);
                    return Unit.INSTANCE;
                })
                .splineTo(middlepassage)
                .splineTo(ingest2) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest2);
        Trajectory ingestStone2 = drive.GasTrajectoryBuilder()
                .reverse()
                .splineTo (ingest2stop)
                .build();

        drive.setPoseEstimate(ingest2stop);
        Trajectory ingestStone2Drive = drive.GasTrajectoryBuilder()
                .strafeTo (new Vector2d(ingest2drive.getX(), ingest2drive.getY()))
                .build();

        drive.setPoseEstimate(ingest2drive);
        Trajectory toFoundation2 = drive.GasTrajectoryBuilder()
                .reverse()
                .addMarker(() -> {
                    mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
                    return Unit.INSTANCE;
                })
                .addMarker(0.3, () -> {
                    mech.ScissorLeft.setTargetPosition(mech.levels[1] + 30);
                    mech.ScissorRight.setTargetPosition(mech.levels[1] + 30);
                    return Unit.INSTANCE;
                })
                .splineTo(new Pose2d(middlepassage.getX()-15, middlepassage.getY(), middlepassage.getHeading()))
                .splineTo(middlepassage)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(0);
                    mech.ScissorLeft.setTargetPosition(mech.levels[4]);
                    mech.ScissorRight.setTargetPosition(mech.levels[4]);
                    return Unit.INSTANCE;
                })
                .addMarker(2.4, () ->{
                    mech.ExtendGripper.setTargetPosition(mech.EXTEND_OUT);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationpickup)
                /*.addMarker(0.4, () -> {
                    mech.IngesterMotor.setPower(0);
                    mech.ScissorLeft.setTargetPosition(mech.levels[2]);
                    mech.ScissorRight.setTargetPosition(mech.levels[2]);
                    return Unit.INSTANCE;
                })
                .addMarker(0.7,() ->{
                    mech.ExtendGripper.setTargetPosition(mech.EXTEND_OUT);
                    return Unit.INSTANCE;
                })
                .addMarker(0.9,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[1]);
                    mech.ScissorRight.setTargetPosition(mech.levels[1]);
                    return Unit.INSTANCE;
                })
                .addMarker(1.0,() ->{
                    mech.Gripper.setPosition(mech.GRIPPER_OPEN);
                    return  Unit.INSTANCE;
                })
                .addMarker(1.1,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[2]);
                    mech.ScissorRight.setTargetPosition(mech.levels[2]);
                    return Unit.INSTANCE;
                })
                .addMarker(1.6,() ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .addMarker(1.8,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[0]);
                    mech.ScissorRight.setTargetPosition(mech.levels[0]);
                    return Unit.INSTANCE;
                })

                 */
                .build();

        drive.setPoseEstimate(foundationpickup);
        Trajectory toStone3 = drive.GasTrajectoryBuilder()
                .addMarker(() ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .addMarker(0.4, () ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[0]);
                    mech.ScissorRight.setTargetPosition(mech.levels[0]);
                    return Unit.INSTANCE;
                })
                .splineTo(middlepassage)
                .splineTo(ingest3)
                .build();

        drive.setPoseEstimate(ingest3);
        Trajectory toFoundation3 = drive.GasTrajectoryBuilder()
                .reverse()
                .addMarker(0.0, () -> {
                    mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
                    return Unit.INSTANCE;
                })
                .addMarker(0.4, () -> {
                    mech.IngesterMotor.setPower(0);
                    return Unit.INSTANCE;
                })
                .addMarker(() -> {
                    return Unit.INSTANCE;
                })
                .splineTo(middlepassage)
                .addMarker(() -> {
                    mech.ScissorLeft.setTargetPosition(mech.levels[5]);
                    mech.ScissorRight.setTargetPosition(mech.levels[5]);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationpickup)
                .build();

        drive.setPoseEstimate(foundationpickup);
        Trajectory foundationshoving = drive.GasTrajectoryBuilder()
                .addMarker(() ->{
                    mech.ExtendGripper.setTargetPosition(mech.EXTEND_OUT);
                    return Unit.INSTANCE;
                })
                .addMarker(0.5, () ->{
                    mech.Gripper.setPosition(mech.GRIPPER_OPEN);
                    return Unit.INSTANCE;
                })
                .addMarker(1.2,() ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .addMarker(1.6,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[0]);
                    mech.ScissorRight.setTargetPosition(mech.levels[0]);
                    return Unit.INSTANCE;
                })
                .reverse()
                .strafeTo(new Vector2d(foundationshove.getX(), foundationshove.getY()))
                /*.addMarker(() -> {
                    mech.IngesterMotor.setPower(0);
                    mech.ScissorLeft.setTargetPosition(mech.levels[2]);
                    mech.ScissorRight.setTargetPosition(mech.levels[2]);
                    return Unit.INSTANCE;
                })
                .addMarker(1.2,() ->{
                    mech.ExtendGripper.setTargetPosition(mech.EXTEND_OUT);
                    return Unit.INSTANCE;
                })
                .addMarker(1.4,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[1]);
                    mech.ScissorRight.setTargetPosition(mech.levels[1]);
                    return Unit.INSTANCE;
                })
                .addMarker(1.5,() ->{
                    mech.Gripper.setPosition(mech.GRIPPER_OPEN);
                    return Unit.INSTANCE;
                })
                .addMarker(1.6,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[2]);
                    mech.ScissorRight.setTargetPosition(mech.levels[2]);
                    return Unit.INSTANCE;
                })
                .addMarker(2.1,() ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .addMarker(2.4,() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[0]);
                    mech.ScissorRight.setTargetPosition(mech.levels[0]);
                    return Unit.INSTANCE;
                })

                 */
                .build();

        drive.setPoseEstimate(foundationshove);
        Trajectory park = drive.GasTrajectoryBuilder()
                .splineTo(parkposition)
                .build();

        //Trajectory dragFoundation = drive.GasTrajectoryBuilder()

        drive.setPoseEstimate(startPose);
        mech.Gripper.setPosition(mech.GRIPPER_OPEN);
        ElapsedTime time = new ElapsedTime();
        double prevTime = time.seconds();
        while(time.seconds() - prevTime < 1) {
            mech.ScissorLeft.setPower(-0.2);
            mech.ScissorRight.setPower(-0.2);
        }
        while(time.seconds() - prevTime < 2) {
            mech.ScissorLeft.setPower(0);
            mech.ScissorRight.setPower(0);
        }
        mech.ScissorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mech.ScissorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mech.ScissorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mech.ScissorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mech.ScissorRight.setTargetPosition(0);
        mech.ScissorLeft.setTargetPosition(0);
        mech.ScissorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mech.ScissorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mech.ScissorRight.setPower(1);
        mech.ScissorLeft.setPower(1);
        mech.ExtendGripper.setTargetPosition(0);
        mech.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mech.ExtendGripper.setPower(0.8);
        waitForStart();
        mech.IngesterMotor.setPower(1); //Off is the same but setPower(0)
        drive.followTrajectorySync(toStone1);
        drive.followTrajectorySync(toFoundation1);
        mech.FoundationLeft.setPosition(mech.LEFT_CLOSE);
        mech.FoundationRight.setPosition(mech.RIGHT_CLOSE);
        sleep(500);
        drive.followTrajectorySync(pullFoundation);
        drive.followTrajectorySync(ingestStone2);
        mech.IngesterMotor.setPower(0.8);
        drive.followTrajectorySync(ingestStone2Drive);
            //mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
        drive.followTrajectorySync(toFoundation2);
        mech.Gripper.setPosition(mech.GRIPPER_OPEN);
        sleep(600);
            //mech.IngesterMotor.setPower(0.8);
        mech.IngesterMotor.setPower(0.8);
        drive.followTrajectorySync(toStone3);
            //mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
        drive.followTrajectorySync(toFoundation3);
        drive.followTrajectorySync(foundationshoving);
        drive.followTrajectorySync(park);
    }

    private double toRadians(double degrees){
        return Math.toRadians(degrees);
    }

}
