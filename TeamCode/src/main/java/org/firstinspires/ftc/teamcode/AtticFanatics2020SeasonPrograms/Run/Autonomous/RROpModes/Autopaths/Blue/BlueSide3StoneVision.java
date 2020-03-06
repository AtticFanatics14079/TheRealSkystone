package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.RROpModes.Autopaths.Blue;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.NoDriveConfigure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveREV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

@Config
@Autonomous
public class BlueSide3StoneVision extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //0 means skystone, 1 means yellow stone
    //-1 for debug, but we can keep it like this because if it works, it should change to either 0 or 255
    private static int valMid = -1;
    private static int valLeft = -1;
    private static int valRight = -1;

    public static float rectHeight = 1.5f / 8f;
    public static float rectWidth = .6f / 8f;

    public static float offsetY = -1.3f / 8f;//changing this moves the three rects and the three circles left or right, range : (-2, 2) not inclusive
    public static float offsetX = 1f / 8f;//changing this moves the three rects and circles up or down, range: (-4, 4) not inclusive

    public static float[] midPos = {4f / 8f + offsetX, 4f / 8f + offsetY};//0 = col, 1 = row
    public static float[] leftPos = {4f / 8f + offsetX, 2f / 8f + offsetY};
    public static float[] rightPos = {4f / 8f + offsetX, 6f / 8f + offsetY};
    //moves all rectangles right or left by amount. units are in ratio to monitor

    private final int rows = 640;
    private final int cols = 480;

    OpenCvCamera phoneCam;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveREV drive = new SampleMecanumDriveREV(hardwareMap);
        NoDriveConfigure mech = new NoDriveConfigure();
        mech.Configure(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();//open camera
        phoneCam.setPipeline(new StageSwitchingPipeline());//different stages
        phoneCam.startStreaming(rows, cols, OpenCvCameraRotation.UPRIGHT);//display on RC
        //width, height
        //width = height in this case, because camera is in portrait mode.

        Pose2d startPoselower = new Pose2d(-30.5,  61.0, Math.toRadians(270.0));// changing this might make the path faster
        Pose2d ingest1lower = new Pose2d(-42.5,20.5, Math.toRadians(305.0));
        Pose2d ingest2lower = new Pose2d(-47,16, Math.toRadians(270.0));
        Pose2d ingest2stoplower = new Pose2d(ingest2lower.getX()+2.5, ingest2lower.getY()+2.5, Math.toRadians(170.0));
        Pose2d ingest2drivelower = new Pose2d(ingest2lower.getX()-8, ingest2lower.getY()+2.5, Math.toRadians(170.0));
        Pose2d foundationgrablower = new Pose2d(46.0,25.0,Math.toRadians(90.0));
        Pose2d foundationmidlower = new Pose2d (40.0, 40.0, Math.toRadians(135.0));
        Pose2d foundationdumplower = new Pose2d(20.0,43.0,Math.toRadians(180.0));
        Pose2d foundationpickuplower = new Pose2d (22.0,39.0,Math.toRadians(180.0));
        Pose2d foundationshovelower = new Pose2d(51.0,52.0,Math.toRadians(180.0));
        Pose2d middlepassagelower = new Pose2d(4.0, 39, Math.toRadians(180.0));
        Pose2d parkpositionlower = new Pose2d(4.0,43.0, Math.toRadians(180.0));

        Pose2d startPosemiddle = new Pose2d(-30.5,  62.0, Math.toRadians(270.0));// changing this might make the path faster
        Pose2d ingest1middle = new Pose2d(-37,25, Math.toRadians(305.0));
        Pose2d ingest2middle = new Pose2d(-39,18.5, Math.toRadians(270.0));
        Pose2d ingest2stopmiddle = new Pose2d(ingest2middle.getX()+3.5, ingest2middle.getY()+2.5, Math.toRadians(165.0));
        Pose2d ingest2drivemiddle = new Pose2d(ingest2middle.getX()-10, ingest2middle.getY()+2.5, Math.toRadians(165.0));
        Pose2d ingest3middle = new Pose2d(-56, 26, Math.toRadians(165.0));
        Pose2d foundationgrabmiddle = new Pose2d(48.0,25.0,Math.toRadians(90.0));
        Pose2d foundationmidmiddle = new Pose2d (40.0, 40.0, Math.toRadians(135.0));
        Pose2d foundationdumpmiddle = new Pose2d(20.0,43.0,Math.toRadians(180.0));
        Pose2d foundationpickupmiddle = new Pose2d (21,38, Math.toRadians(180.0));
        Pose2d foundationshovemiddle = new Pose2d(51.0,52.0,Math.toRadians(180.0));
        Pose2d middlepassagemiddle = new Pose2d(2.0, 39, Math.toRadians(180.0));
        Pose2d parkpositionmiddle = new Pose2d(4.0,44.0, Math.toRadians(180.0));

        Pose2d startPoseupper = new Pose2d(-30.5,  60.0, Math.toRadians(270.0));// changing this might make the path faster
        Pose2d ingest1upper = new Pose2d(-28.5,25, Math.toRadians(305.0));
        Pose2d ingest2upper = new Pose2d(-30,20.5, Math.toRadians(270.0));
        Pose2d ingest2stopupper = new Pose2d(ingest2upper.getX()+4.5, ingest2upper.getY()+3, Math.toRadians(175.0));
        Pose2d ingest2driveupper = new Pose2d(-40.5, 23.5, Math.toRadians(175.0));
        Pose2d ingest3upper = new Pose2d(-48.0, 24, Math.toRadians(170.0));
        Pose2d foundationgrabupper = new Pose2d(48.0,25.0,Math.toRadians(90.0));
        Pose2d foundationmidupper = new Pose2d (40.0, 40.0, Math.toRadians(135.0));
        Pose2d foundationdumpupper = new Pose2d(20.0,43.0,Math.toRadians(180.0));
        Pose2d foundationpickupupper = new Pose2d (21,39,Math.toRadians(180.0));
        Pose2d foundationshoveupper = new Pose2d(51.0,50.0,Math.toRadians(180.0));
        Pose2d middlepassageupper = new Pose2d(2.0, 39.0, Math.toRadians(180.0));
        Pose2d parkpositionupper = new Pose2d(4.0,43.0, Math.toRadians(180.0));


        drive.setPoseEstimate(startPoseupper);
        Trajectory toStone1upper = drive.GasTrajectoryBuilder()
                .splineTo(ingest1upper) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest1upper);
        Trajectory toFoundation1upper
                = drive.GasTrajectoryBuilder()
                .addMarker(0.0,() ->{
                    mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
                    return Unit.INSTANCE;
                })
                .addMarker(0.4,() ->{
                    mech.IngesterMotor.setPower(0);
                    return Unit.INSTANCE;
                })
                .reverse()
                .splineTo(middlepassageupper)
                .addMarker(() -> {
                    mech.ScissorLeft.setTargetPosition(mech.levels[2]);
                    mech.ScissorRight.setTargetPosition(mech.levels[2]);
                    return Unit.INSTANCE;
                })
                .strafeTo(new Vector2d(middlepassageupper.getX()+40, middlepassageupper.getY()))
                .addMarker(() ->{
                    mech.ExtendGripper.setTargetPosition(mech.EXTEND_OUT);
                    return Unit.INSTANCE;
                })
                .splineTo(new Pose2d(foundationgrabupper.getX(), foundationgrabupper.getY()+8, foundationgrabupper.getHeading()))
                .strafeTo(new Vector2d(foundationgrabupper.getX(),foundationgrabupper.getY()))
                .build();

        drive.setPoseEstimate(foundationgrabupper);
        Trajectory pullFoundationupper = drive.GasTrajectoryBuilder()
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
                .splineTo(foundationmidupper)
                .splineTo(foundationdumpupper)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(-0.8);
                    mech.FoundationLeft.setPosition(mech.LEFT_OPEN);
                    mech.FoundationRight.setPosition(mech.RIGHT_OPEN);
                    return Unit.INSTANCE;
                })
                .splineTo(middlepassageupper)
                .splineTo(ingest2upper) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest2upper);
        Trajectory ingestStone2upper = drive.GasTrajectoryBuilder()
                .reverse()
                .splineTo (ingest2stopupper)
                .build();

        drive.setPoseEstimate(ingest2stopupper);
        Trajectory ingestStone2Driveupper = drive.GasTrajectoryBuilder()
                .strafeTo (new Vector2d(ingest2driveupper.getX(), ingest2driveupper.getY()))
                .build();

        drive.setPoseEstimate(ingest2driveupper);
        Trajectory toFoundation2upper = drive.GasTrajectoryBuilder()
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
                .splineTo(middlepassageupper)
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
                .splineTo(foundationpickupupper)
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

        drive.setPoseEstimate(foundationpickupupper);
        Trajectory toStone3upper = drive.GasTrajectoryBuilder()
                .addMarker(() ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .addMarker(0.4, () ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[0]);
                    mech.ScissorRight.setTargetPosition(mech.levels[0]);
                    return Unit.INSTANCE;
                })
                .splineTo(middlepassageupper)
                .splineTo(ingest3upper)
                .build();

        drive.setPoseEstimate(ingest3upper);
        Trajectory toFoundation3upper = drive.GasTrajectoryBuilder()
                .reverse()
                .addMarker(0.0, () -> {
                    mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
                    return Unit.INSTANCE;
                })
                .addMarker(0.4, () -> {
                    mech.IngesterMotor.setPower(0);
                    return Unit.INSTANCE;
                })
                .splineTo(middlepassageupper)
                .addMarker(() -> {
                    mech.ScissorLeft.setTargetPosition(mech.levels[5]);
                    mech.ScissorRight.setTargetPosition(mech.levels[5]);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationpickupupper)
                .build();

        drive.setPoseEstimate(foundationpickupupper);
        Trajectory foundationshovingupper = drive.GasTrajectoryBuilder()
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
                .strafeTo(new Vector2d(foundationshoveupper.getX(), foundationshoveupper.getY()))
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

        drive.setPoseEstimate(foundationshoveupper);
        Trajectory parkupper = drive.GasTrajectoryBuilder()
                .splineTo(parkpositionupper)
                .build();


        drive.setPoseEstimate(startPosemiddle);
        Trajectory toStone1middle = drive.GasTrajectoryBuilder()
                .strafeTo(new Vector2d(startPosemiddle.getX()-7.2, startPosemiddle.getY()-8.0))
                .splineTo(ingest1middle) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest1middle);
        Trajectory toFoundation1middle = drive.GasTrajectoryBuilder()
                .addMarker(0.0,() ->{
                    mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
                    return Unit.INSTANCE;
                })
                .addMarker(0.4,() ->{
                    mech.IngesterMotor.setPower(0);
                    return Unit.INSTANCE;
                })
                .reverse()
                .splineTo(middlepassagemiddle)
                .addMarker(() -> {
                    mech.ScissorLeft.setTargetPosition(mech.levels[2]);
                    mech.ScissorRight.setTargetPosition(mech.levels[2]);
                    return Unit.INSTANCE;
                })
                .strafeTo(new Vector2d(middlepassagemiddle.getX()+40, middlepassagemiddle.getY()))
                .addMarker(() ->{
                    mech.ExtendGripper.setTargetPosition(mech.EXTEND_OUT);
                    return Unit.INSTANCE;
                })
                .splineTo(new Pose2d(foundationgrabmiddle.getX(), foundationgrabmiddle.getY()+8, foundationgrabmiddle.getHeading()))
                .strafeTo(new Vector2d(foundationgrabmiddle.getX(),foundationgrabmiddle.getY()))
                .build();

        drive.setPoseEstimate(foundationgrabmiddle);
        Trajectory pullFoundationmiddle = drive.GasTrajectoryBuilder()
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
                .splineTo(foundationmidmiddle)
                .splineTo(foundationdumpmiddle)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(-0.8);
                    mech.FoundationLeft.setPosition(mech.LEFT_OPEN);
                    mech.FoundationRight.setPosition(mech.RIGHT_OPEN);
                    return Unit.INSTANCE;
                })
                .splineTo(middlepassagemiddle)
                .splineTo(ingest2middle) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest2middle);
        Trajectory ingestStone2middle = drive.GasTrajectoryBuilder()
                .reverse()
                .splineTo (ingest2stopmiddle)
                .build();

        drive.setPoseEstimate(ingest2stopmiddle);
        Trajectory ingestStone2Drivemiddle = drive.GasTrajectoryBuilder()
                .strafeTo (new Vector2d(ingest2drivemiddle.getX(), ingest2drivemiddle.getY()))
                .build();

        drive.setPoseEstimate(ingest2drivemiddle);
        Trajectory toFoundation2middle = drive.GasTrajectoryBuilder()
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
                .splineTo(new Pose2d(middlepassagemiddle.getX()-15, middlepassagemiddle.getY(), middlepassagemiddle.getHeading()))
                .splineTo(middlepassagemiddle)
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
                .splineTo(foundationpickupmiddle)
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

        drive.setPoseEstimate(foundationpickupmiddle);
        Trajectory toStone3middle = drive.GasTrajectoryBuilder()
                .addMarker(() ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .addMarker(0.4, () ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[0]);
                    mech.ScissorRight.setTargetPosition(mech.levels[0]);
                    return Unit.INSTANCE;
                })
                .splineTo(middlepassagemiddle)
                .splineTo(ingest3middle)
                .build();

        drive.setPoseEstimate(ingest3middle);
        Trajectory toFoundation3middle = drive.GasTrajectoryBuilder()
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
                .splineTo(middlepassagemiddle)
                .addMarker(() -> {
                    mech.ScissorLeft.setTargetPosition(mech.levels[5]);
                    mech.ScissorRight.setTargetPosition(mech.levels[5]);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationpickupmiddle)
                .build();

        drive.setPoseEstimate(foundationpickupmiddle);
        Trajectory foundationshovingmiddle = drive.GasTrajectoryBuilder()
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
                .strafeTo(new Vector2d(foundationshovemiddle.getX(), foundationshovemiddle.getY()))
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

        drive.setPoseEstimate(foundationshovemiddle);
        Trajectory parkmiddle = drive.GasTrajectoryBuilder()
                .splineTo(parkpositionmiddle)
                .build();

        drive.setPoseEstimate(startPoselower);
        Trajectory toStone1lower = drive.GasTrajectoryBuilder()
                .strafeTo(new Vector2d(startPoselower.getX()-15.2, startPoselower.getY()-8.0))
                .splineTo(ingest1lower) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest1lower);
        Trajectory toFoundation1lower = drive.GasTrajectoryBuilder()
                .addMarker(0.0,() ->{
                    mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
                    return Unit.INSTANCE;
                })
                .addMarker(0.4,() ->{
                    mech.IngesterMotor.setPower(0);
                    return Unit.INSTANCE;
                })
                .reverse()
                .splineTo(middlepassagelower)
                .addMarker(() -> {
                    mech.ScissorLeft.setTargetPosition(mech.levels[2]);
                    mech.ScissorRight.setTargetPosition(mech.levels[2]);
                    return Unit.INSTANCE;
                })
                .strafeTo(new Vector2d(middlepassagelower.getX()+40, middlepassagelower.getY()))
                .addMarker(() ->{
                    mech.ExtendGripper.setTargetPosition(mech.EXTEND_OUT);
                    return Unit.INSTANCE;
                })
                .splineTo(new Pose2d(foundationgrablower.getX(), foundationgrablower.getY()+8, foundationgrablower.getHeading()))
                .strafeTo(new Vector2d(foundationgrablower.getX(),foundationgrablower.getY()))
                .build();

        drive.setPoseEstimate(foundationgrablower);
        Trajectory pullFoundationlower = drive.GasTrajectoryBuilder()
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
                .splineTo(foundationmidlower)
                .splineTo(foundationdumplower)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(-0.8);
                    mech.FoundationLeft.setPosition(mech.LEFT_OPEN);
                    mech.FoundationRight.setPosition(mech.RIGHT_OPEN);
                    return Unit.INSTANCE;
                })
                .splineTo(middlepassagelower)
                .splineTo(ingest2lower) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest2lower);
        Trajectory ingestStone2lower = drive.GasTrajectoryBuilder()
                .reverse()
                .splineTo (ingest2stoplower)
                .build();

        drive.setPoseEstimate(ingest2stoplower);
        Trajectory ingestStone2Drivelower = drive.GasTrajectoryBuilder()
                .strafeTo (new Vector2d(ingest2drivelower.getX(), ingest2drivelower.getY()))
                .build();

        drive.setPoseEstimate(ingest2drivelower);
        Trajectory toFoundation2lower = drive.GasTrajectoryBuilder()
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
                .splineTo(new Pose2d(middlepassagelower.getX()-15, middlepassagelower.getY(), middlepassagelower.getHeading()))
                .splineTo(middlepassagelower)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(0);
                    mech.ScissorLeft.setTargetPosition(mech.levels[4] + 150);
                    mech.ScissorRight.setTargetPosition(mech.levels[4] + 150);
                    return Unit.INSTANCE;
                })
                .addMarker(3, () ->{
                    mech.ExtendGripper.setTargetPosition(mech.EXTEND_OUT);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationpickuplower)
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

        drive.setPoseEstimate(foundationpickuplower);
        Trajectory foundationshovinglower = drive.GasTrajectoryBuilder()
                .addMarker(1, () -> {
                    mech.Gripper.setPosition(mech.GRIPPER_OPEN);
                    return Unit.INSTANCE;
                })
                .addMarker(1.4, () ->{
                    mech.ExtendGripper.setTargetPosition(0);
                    return Unit.INSTANCE;
                })
                .strafeTo(new Vector2d(foundationshovelower.getX(), foundationshovelower.getY()))
                .build();

        drive.setPoseEstimate(foundationshovelower);
        Trajectory parklower = drive.GasTrajectoryBuilder()
                .addMarker(() ->{
                    mech.ScissorLeft.setTargetPosition(mech.levels[0]);
                    mech.ScissorRight.setTargetPosition(mech.levels[0]);
                    return Unit.INSTANCE;
                })
                .splineTo(parkpositionlower)
                .build();

        telemetry.addLine("Trajectories Done Calculating");
        telemetry.update();
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
        mech.ScissorLeft.setTargetPosition(0);
        mech.ScissorRight.setTargetPosition(0);
        mech.ScissorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mech.ScissorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mech.ScissorLeft.setPower(1);
        mech.ScissorRight.setPower(1);
        mech.ExtendGripper.setTargetPosition(0);
        mech.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mech.ExtendGripper.setPower(0.8);
        waitForStart();
        runtime.reset();
        phoneCam.pauseViewport(); // stops livestream video to save CPU resources
        /*THIS IS A NEW STYLE OF ROADRUNNER PATHS, IT IS PROBABLY BETTER
        SINCE YOU DON'T NEED drive.setPoseEstimate
        INSIDE OF THESE IF STATEMENTS SHOULD GO THE STONE SPECIFIC CODE
        */
        //255 is a regular stone, 0 is a skystone
        if(valRight == 0){ // STONE LEFT
            drive.setPoseEstimate(startPoseupper);
            mech.IngesterMotor.setPower(0.8); //Off is the same but setPower(0)
            drive.followTrajectorySync(toStone1upper);
            drive.followTrajectorySync(toFoundation1upper);
            mech.FoundationLeft.setPosition(mech.LEFT_CLOSE);
            mech.FoundationRight.setPosition(mech.RIGHT_CLOSE);
            sleep(500);
            drive.followTrajectorySync(pullFoundationupper);
            drive.followTrajectorySync(ingestStone2upper);
            mech.IngesterMotor.setPower(0.8);
            drive.followTrajectorySync(ingestStone2Driveupper);
            //mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
            drive.followTrajectorySync(toFoundation2upper);
            mech.Gripper.setPosition(mech.GRIPPER_OPEN);
            sleep(600);
            //mech.IngesterMotor.setPower(0.8);
            mech.IngesterMotor.setPower(0.8);
            drive.followTrajectorySync(toStone3upper);
            //mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
            drive.followTrajectorySync(toFoundation3upper);
            drive.followTrajectorySync(foundationshovingupper);
            drive.followTrajectorySync(parkupper);
        }
        else if(valMid == 0){ // STONE MIDDLE
            drive.setPoseEstimate(startPosemiddle);
            mech.IngesterMotor.setPower(1); //Off is the same but setPower(0)
            drive.followTrajectorySync(toStone1middle);
            drive.followTrajectorySync(toFoundation1middle);
            mech.FoundationLeft.setPosition(mech.LEFT_CLOSE);
            mech.FoundationRight.setPosition(mech.RIGHT_CLOSE);
            sleep(500);
            drive.followTrajectorySync(pullFoundationmiddle);
            drive.followTrajectorySync(ingestStone2middle);
            mech.IngesterMotor.setPower(0.8);
            drive.followTrajectorySync(ingestStone2Drivemiddle);
            //mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
            drive.followTrajectorySync(toFoundation2middle);
            mech.Gripper.setPosition(mech.GRIPPER_OPEN);
            sleep(600);
            //mech.IngesterMotor.setPower(0.8);
            mech.IngesterMotor.setPower(0.8);
            drive.followTrajectorySync(toStone3middle);
            //mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
            drive.followTrajectorySync(toFoundation3middle);
            drive.followTrajectorySync(foundationshovingmiddle);
            drive.followTrajectorySync(parkmiddle);
        }
        else{ // STONE RIGHT
            drive.setPoseEstimate(startPoselower);
            mech.IngesterMotor.setPower(0.8); //Off is the same but setPower(0)
            drive.followTrajectorySync(toStone1lower);
            drive.followTrajectorySync(toFoundation1lower);
            mech.FoundationLeft.setPosition(mech.LEFT_CLOSE);
            mech.FoundationRight.setPosition(mech.RIGHT_CLOSE);
            sleep(500);
            drive.followTrajectorySync(pullFoundationlower);
            drive.followTrajectorySync(ingestStone2lower);
            mech.IngesterMotor.setPower(0.8);
            drive.followTrajectorySync(ingestStone2Drivelower);
            //mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
            drive.followTrajectorySync(toFoundation2lower);
            mech.Gripper.setPosition(mech.GRIPPER_OPEN);
            sleep(600);
            //mech.Gripper.setPosition(mech.GRIPPER_CLOSED);
            drive.followTrajectorySync(foundationshovinglower);
            drive.followTrajectorySync(parklower);
        }
    }

    //detection pipeline
    static class StageSwitchingPipeline extends OpenCvPipeline
    {
        Mat yCbCrChan2Mat = new Mat();
        Mat thresholdMat = new Mat();
        Mat all = new Mat();
        List<MatOfPoint> contoursList = new ArrayList<>();

        enum Stage
        {//color difference. greyscale
            detection,//includes outlines
            THRESHOLD,//b&w
            RAW_IMAGE,//displays raw view
        }

        private Stage stageToRenderToViewport = Stage.detection;
        private Stage[] stages = Stage.values();

        @Override
        public void onViewportTapped()
        {
            /*
             * Note that this method is invoked from the UI thread
             * so whatever we do here, we must do quickly.
             */

            int currentStageNum = stageToRenderToViewport.ordinal();

            int nextStageNum = currentStageNum + 1;

            if(nextStageNum >= stages.length)
            {
                nextStageNum = 0;
            }

            stageToRenderToViewport = stages[nextStageNum];
        }

        @Override
        public Mat processFrame(Mat input)
        {
            contoursList.clear();
            /*
             * This pipeline finds the contours of yellow blobs such as the Gold Mineral
             * from the Rover Ruckus game.
             */

            //color diff cb.
            //lower cb = more blue = skystone = white
            //higher cb = less blue = yellow stone = grey
            Imgproc.cvtColor(input, yCbCrChan2Mat, Imgproc.COLOR_RGB2YCrCb);//converts rgb to ycrcb
            Core.extractChannel(yCbCrChan2Mat, yCbCrChan2Mat, 2);//takes cb difference and stores

            //b&w
            Imgproc.threshold(yCbCrChan2Mat, thresholdMat, 102, 255, Imgproc.THRESH_BINARY_INV);

            //outline/contour
            Imgproc.findContours(thresholdMat, contoursList, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            yCbCrChan2Mat.copyTo(all);//copies mat object
            //Imgproc.drawContours(all, contoursList, -1, new Scalar(255, 0, 0), 3, 8);//draws blue contours


            //get values from frame
            double[] pixMid = thresholdMat.get((int)(input.rows()* midPos[1]), (int)(input.cols()* midPos[0]));//gets value at circle
            valMid = (int)pixMid[0];

            double[] pixLeft = thresholdMat.get((int)(input.rows()* leftPos[1]), (int)(input.cols()* leftPos[0]));//gets value at circle
            valLeft = (int)pixLeft[0];

            double[] pixRight = thresholdMat.get((int)(input.rows()* rightPos[1]), (int)(input.cols()* rightPos[0]));//gets value at circle
            valRight = (int)pixRight[0];

            //create three points
            Point pointMid = new Point((int)(input.cols()* midPos[0]), (int)(input.rows()* midPos[1]));
            Point pointLeft = new Point((int)(input.cols()* leftPos[0]), (int)(input.rows()* leftPos[1]));
            Point pointRight = new Point((int)(input.cols()* rightPos[0]), (int)(input.rows()* rightPos[1]));

            //draw circles on those points
            Imgproc.circle(all, pointMid,5, new Scalar( 255, 0, 0 ),1 );//draws circle
            Imgproc.circle(all, pointLeft,5, new Scalar( 255, 0, 0 ),1 );//draws circle
            Imgproc.circle(all, pointRight,5, new Scalar( 255, 0, 0 ),1 );//draws circle

            //draw 3 rectangles
            Imgproc.rectangle(//1-3
                    all,
                    new Point(
                            input.cols()*(leftPos[0]-rectWidth/2),
                            input.rows()*(leftPos[1]-rectHeight/2)),
                    new Point(
                            input.cols()*(leftPos[0]+rectWidth/2),
                            input.rows()*(leftPos[1]+rectHeight/2)),
                    new Scalar(0, 255, 0), 3);
            Imgproc.rectangle(//3-5
                    all,
                    new Point(
                            input.cols()*(midPos[0]-rectWidth/2),
                            input.rows()*(midPos[1]-rectHeight/2)),
                    new Point(
                            input.cols()*(midPos[0]+rectWidth/2),
                            input.rows()*(midPos[1]+rectHeight/2)),
                    new Scalar(0, 255, 0), 3);
            Imgproc.rectangle(//5-7
                    all,
                    new Point(
                            input.cols()*(rightPos[0]-rectWidth/2),
                            input.rows()*(rightPos[1]-rectHeight/2)),
                    new Point(
                            input.cols()*(rightPos[0]+rectWidth/2),
                            input.rows()*(rightPos[1]+rectHeight/2)),
                    new Scalar(0, 255, 0), 3);

            switch (stageToRenderToViewport)
            {
                case THRESHOLD:
                {
                    return thresholdMat;
                }

                case detection:
                {
                    return all;
                }

                case RAW_IMAGE:
                {
                    return input;
                }

                default:
                {
                    return input;
                }
            }
        }

    }
}

