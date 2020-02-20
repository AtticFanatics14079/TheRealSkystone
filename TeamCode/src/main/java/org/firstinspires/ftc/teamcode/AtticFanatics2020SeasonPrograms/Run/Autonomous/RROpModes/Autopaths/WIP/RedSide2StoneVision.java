package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.RROpModes.Autopaths.WIP;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.NoDriveConfigure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveBase;
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


/**
 * Created by maryjaneb  on 11/13/2016.
 *
 * nerverest ticks
 * 60 1680
 * 40 1120
 * 20 560
 *
 * monitor: 640 x 480
 *YES
 */
@Config
@Autonomous
public class RedSide2StoneVision extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //0 means skystone, 1 means yellow stone
    //-1 for debug, but we can keep it like this because if it works, it should change to either 0 or 255
    private static int valMid = -1;
    private static int valLeft = -1;
    private static int valRight = -1;

    private static float rectHeight = 1.5f / 8f;
    private static float rectWidth = .6f / 8f;

    private static float offsetY = 1.3f / 8f;//changing this moves the three rects and the three circles left or right, range : (-2, 2) not inclusive
    private static float offsetX = 2f / 8f;//changing this moves the three rects and circles up or down, range: (-4, 4) not inclusive

    private static float[] midPos = {4f / 8f + offsetX, 4f / 8f + offsetY};//0 = col, 1 = row
    private static float[] leftPos = {4f / 8f + offsetX, 2f / 8f + offsetY};
    private static float[] rightPos = {4f / 8f + offsetX, 6f / 8f + offsetY};
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



            Pose2d startPoselower = new Pose2d(-30.5,  -63.0, Math.toRadians(90.0));// changing this might make the path faster
            Pose2d ingest1lower = new Pose2d(-43.0, -26.0, Math.toRadians(80.0));
            Pose2d ingest1stoplower = new Pose2d(-44, -13, Math.toRadians(90.0));
            Pose2d ingest2lower = new Pose2d(-50.0, -20.0, Math.toRadians(90.0));
            Pose2d ingest2stoplower = new Pose2d(-46.0, -23.5, Math.toRadians(180.0));
            Pose2d ingest2drivelower = new Pose2d(-54.0, -25.0, Math.toRadians(180.0));
            Pose2d foundationgrablower = new Pose2d(48.0, -25.0, Math.toRadians(270.0));
            Pose2d foundationmidlower = new Pose2d(40.0, -40.0, Math.toRadians(225.0));
            Pose2d foundationdumplower = new Pose2d(20.0, -45.0, Math.toRadians(180.0));
            Pose2d foundationpickuplower = new Pose2d(20.0, -40.0, Math.toRadians(180.0));
            Pose2d foundationshovelower = new Pose2d(50.0, -40.0, Math.toRadians(180.0));
            Pose2d middlepassagelower = new Pose2d(0.0, -40.0, Math.toRadians(180.0));
            Pose2d parkpositionlower = new Pose2d(4.0, -36.0, Math.toRadians(180.0));

            Pose2d startPosemiddle = new Pose2d(-30.5,  -63.0, Math.toRadians(90.0));// changing this might make the path faster
            Pose2d ingest1middle = new Pose2d(-36.0,-26.5, Math.toRadians(75.0));
            Pose2d ingest1stopmiddle = new Pose2d(-37, -13, Math.toRadians(90.0));
            Pose2d ingest2middle = new Pose2d(-55.0,-24.0, Math.toRadians(90.0));
            Pose2d ingest2stopmiddle = new Pose2d(-55.0,-14.0, Math.toRadians(90.0));
            Pose2d foundationgrabmiddle = new Pose2d(48.0,-25.0,Math.toRadians(270.0));
            Pose2d foundationmidmiddle = new Pose2d (40.0, -40.0, Math.toRadians(225.0));
            Pose2d foundationdumpmiddle = new Pose2d(20.0,-45.0,Math.toRadians(180.0));
            Pose2d foundationpickupmiddle = new Pose2d (20.0,-40.0,Math.toRadians(180.0));
            Pose2d foundationshovemiddle = new Pose2d(50.0,-40.0,Math.toRadians(180.0));
            Pose2d middlepassagemiddle = new Pose2d(0.0, -40.0, Math.toRadians(180.0));
            Pose2d parkpositionmiddle = new Pose2d(4.0,-36.0, Math.toRadians(180.0));

            Pose2d startPoseupper = new Pose2d(-30.5,  -63.0, Math.toRadians(90.0));// changing this might make the path faster
            Pose2d ingest1upper = new Pose2d(-28.0,-26.0, Math.toRadians(80.0));
            Pose2d ingest1stopupper = new Pose2d(-29, -13, Math.toRadians(90.0));
            Pose2d ingest2upper = new Pose2d(-47.0,-24.0, Math.toRadians(90.0));
            Pose2d ingest2stopupper = new Pose2d(-47.0,-14.0, Math.toRadians(90.0));
            Pose2d foundationgrabupper = new Pose2d(48.0,-25.0,Math.toRadians(270.0));
            Pose2d foundationmidupper = new Pose2d (40.0, -40.0, Math.toRadians(225.0));
            Pose2d foundationdumpupper = new Pose2d(20.0,-45.0,Math.toRadians(180.0));
            Pose2d foundationpickupupper = new Pose2d (20.0,-40.0,Math.toRadians(180.0));
            Pose2d foundationshoveupper = new Pose2d(50.0,-40.0,Math.toRadians(180.0));
            Pose2d middlepassageupper = new Pose2d(0.0, -40.0, Math.toRadians(180.0));
            Pose2d parkpositionupper = new Pose2d(4.0,-36.0, Math.toRadians(180.0));



            drive.setPoseEstimate(startPoselower);
            Trajectory toStone1lower = drive.trajectoryBuilder()
                    .splineTo(ingest1lower) // MIDDLE STONE POSITION
                    .build();

            drive.setPoseEstimate(ingest1lower);
            Trajectory ingestStone1lower = drive.trajectoryBuilder()
                    .splineTo (ingest1stoplower)
                    .build();


            drive.setPoseEstimate(ingest1stoplower);
            Trajectory toFoundation1lower = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(middlepassagelower)
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
                    .strafeTo(new Vector2d(middlepassagelower.getX()+40, middlepassagelower.getY()))
                    .splineTo(new Pose2d(foundationgrablower.getX(), foundationgrablower.getY()-8, foundationgrablower.getHeading()))
                    .strafeTo(new Vector2d(foundationgrablower.getX(),foundationgrablower.getY()))
                    .build();


            drive.setPoseEstimate(foundationgrablower);
            Trajectory pullFoundationlower = drive.trajectoryBuilder()
                    .splineTo(foundationmidlower)
                    .addMarker(() -> {
                        mech.IngesterMotor.setPower(0.5);
                        mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[0]);
                        mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[0]);
                        return Unit.INSTANCE;
                    })
                    .splineTo(foundationdumplower)
                    .build();

            drive.setPoseEstimate(foundationdumplower);
            Trajectory toStone2lower = drive.trajectoryBuilder()
                    .splineTo(middlepassagelower)
                    .splineTo(ingest2lower) // MIDDLE STONE POSITION
                    .build();

            drive.setPoseEstimate(ingest2lower);
            Trajectory ingestStone2lower = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo (ingest2stoplower)
                    .build();

            drive.setPoseEstimate(ingest2stoplower);
            Trajectory ingestStone2Drivelower = drive.trajectoryBuilder()
                    .strafeTo (new Vector2d(ingest2drivelower.getX(), ingest2drivelower.getY()))
                    .build();

            drive.setPoseEstimate(ingest2drivelower);
            Trajectory toFoundation2lower = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(middlepassagelower)
                    .addMarker(() -> {
                        mech.IngesterMotor.setPower(0);
                        mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[3]);
                        mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[3]);
                        return Unit.INSTANCE;
                    })
                    .splineTo(foundationpickuplower)
                    .strafeTo(new Vector2d(foundationshovelower.getX(),foundationshovelower.getY()))
                    .build();
            drive.setPoseEstimate(foundationshovelower);
            Trajectory parklower = drive.trajectoryBuilder()
                    .addMarker(() -> {
                        mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[0]);
                        mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[0]);
                        return Unit.INSTANCE;
                    })
                    .splineTo(parkpositionlower)
                    .build();


        drive.setPoseEstimate(startPosemiddle);
        Trajectory toStone1middle = drive.trajectoryBuilder()
                .splineTo(ingest1middle) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest1middle);
        Trajectory ingestStone1middle = drive.trajectoryBuilder()
                .splineTo (ingest1stopmiddle)
                .build();


        drive.setPoseEstimate(ingest1stopmiddle);
        Trajectory toFoundation1middle = drive.trajectoryBuilder()
                .reverse()
                .splineTo(middlepassagemiddle)
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
                .strafeTo(new Vector2d(middlepassagemiddle.getX()+40, middlepassagemiddle.getY()))
                .splineTo(new Pose2d(foundationgrabmiddle.getX(), foundationgrabmiddle.getY()-8, foundationgrabmiddle.getHeading()))
                .strafeTo(new Vector2d(foundationgrabmiddle.getX(),foundationgrabmiddle.getY()))
                .build();


        drive.setPoseEstimate(foundationgrabmiddle);
        Trajectory pullFoundationmiddle = drive.trajectoryBuilder()
                .splineTo(foundationmidmiddle)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(0.5);
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[0]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[0]);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationdumpmiddle)
                .build();

        drive.setPoseEstimate(foundationdumpmiddle);
        Trajectory toStone2middle = drive.trajectoryBuilder()
                .splineTo(middlepassagemiddle)
                .splineTo(ingest2middle) // MIDDLE STONE POSITION
                .build();

        drive.setPoseEstimate(ingest2middle);
        Trajectory ingestStone2middle = drive.trajectoryBuilder()
                .strafeTo (new Vector2d(ingest2stopmiddle.getX(), ingest2stopmiddle.getY()))
                .build();


        drive.setPoseEstimate(ingest2stopmiddle);
        Trajectory toFoundation2middle = drive.trajectoryBuilder()
                .reverse()
                .splineTo(middlepassagemiddle)
                .addMarker(() -> {
                    mech.IngesterMotor.setPower(0);
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[2]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[2]);
                    return Unit.INSTANCE;
                })
                .splineTo(foundationpickupmiddle)
                .strafeTo(new Vector2d(foundationshovemiddle.getX(),foundationshovemiddle.getY()))
                .build();

        drive.setPoseEstimate(foundationshovemiddle);
        Trajectory parkmiddle = drive.trajectoryBuilder()
                .addMarker(() -> {
                    mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[0]);
                    mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[0]);
                    return Unit.INSTANCE;
                })
                .splineTo(parkpositionmiddle)
                .build();


            drive.setPoseEstimate(startPoseupper);
            Trajectory toStone1upper = drive.trajectoryBuilder()
                    .splineTo(ingest1upper) // MIDDLE STONE POSITION
                    .build();

            drive.setPoseEstimate(ingest1upper);
            Trajectory ingestStone1upper = drive.trajectoryBuilder()
                    .splineTo (ingest1stopupper)
                    .build();


            drive.setPoseEstimate(ingest1stopupper);
            Trajectory toFoundation1upper = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(middlepassageupper)
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
                    .strafeTo(new Vector2d(middlepassageupper.getX()+40, middlepassageupper.getY()))
                    .splineTo(new Pose2d(foundationgrabupper.getX(), foundationgrabupper.getY()-8, foundationgrabupper.getHeading()))
                    .strafeTo(new Vector2d(foundationgrabupper.getX(),foundationgrabupper.getY()))
                    .build();


            drive.setPoseEstimate(foundationgrabupper);
            Trajectory pullFoundationupper = drive.trajectoryBuilder()
                    .splineTo(foundationmidupper)
                    .addMarker(() -> {
                        mech.IngesterMotor.setPower(0.5);
                        mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[0]);
                        mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[0]);
                        return Unit.INSTANCE;
                    })
                    .splineTo(foundationdumpupper)
                    .build();

            drive.setPoseEstimate(foundationdumpupper);
            Trajectory toStone2upper = drive.trajectoryBuilder()
                    .splineTo(middlepassageupper)
                    .splineTo(ingest2upper) // MIDDLE STONE POSITION
                    .build();

            drive.setPoseEstimate(ingest2upper);
            Trajectory ingestStone2upper = drive.trajectoryBuilder()
                    .strafeTo (new Vector2d(ingest2stopupper.getX(), ingest2stopupper.getY()))
                    .build();


            drive.setPoseEstimate(ingest2stopupper);
            Trajectory toFoundation2upper = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(middlepassageupper)
                    .addMarker(() -> {
                        mech.IngesterMotor.setPower(0);
                        mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[2]);
                        mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[2]);
                        return Unit.INSTANCE;
                    })
                    .splineTo(foundationpickupupper)
                    .strafeTo(new Vector2d(foundationshoveupper.getX(),foundationshoveupper.getY()))
                    .build();

            drive.setPoseEstimate(foundationshoveupper);
            Trajectory parkupper = drive.trajectoryBuilder()
                    .addMarker(() -> {
                        mech.ScissorLeft.setTargetPosition(NoDriveConfigure.levels[0]);
                        mech.ScissorRight.setTargetPosition(NoDriveConfigure.levels[0]);
                        return Unit.INSTANCE;
                    })
                    .splineTo(parkpositionupper)
                    .build();

        waitForStart();
        runtime.reset();
        phoneCam.pauseViewport(); // stops livestream video to save CPU resources
        /*THIS IS A NEW STYLE OF ROADRUNNER PATHS, IT IS PROBABLY BETTER
        SINCE YOU DON'T NEED drive.setPoseEstimate
        INSIDE OF THESE IF STATEMENTS SHOULD GO THE STONE SPECIFIC CODE
        */
        //255 is a regular stone, 0 is a skystone
        if(valLeft == 0){ // STONE LEFT
            drive.setPoseEstimate(startPoseupper);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            waitForStart();
            mech.IngesterMotor.setPower(0.5); //Off is the same but setPower(0)
            drive.followTrajectorySync(toStone1upper);
            drive.followTrajectorySync(ingestStone1upper);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_CLOSED);
            sleep(300);
            drive.followTrajectorySync(toFoundation1upper);
            mech.ExtendGripper.setTargetPosition(NoDriveConfigure.EXTEND_OUT);
            mech.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mech.ExtendGripper.setPower(1);
            mech.FoundationLeft.setPosition(NoDriveConfigure.LEFT_CLOSE);
            mech.FoundationRight.setPosition(NoDriveConfigure.RIGHT_CLOSE);
            sleep(500);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(600);
            mech.ExtendGripper.setTargetPosition(0);
            drive.followTrajectorySync(pullFoundationupper);
            mech.FoundationLeft.setPosition(NoDriveConfigure.LEFT_OPEN);
            mech.FoundationRight.setPosition(NoDriveConfigure.RIGHT_OPEN);
            sleep(500);
            drive.followTrajectorySync(toStone2upper);
            drive.followTrajectorySync(ingestStone2upper);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_CLOSED);
            sleep(300);
            drive.followTrajectorySync(toFoundation2upper);
            mech.ExtendGripper.setTargetPosition(NoDriveConfigure.EXTEND_OUT);
            sleep(200);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(600);
            mech.ExtendGripper.setTargetPosition(0);
            sleep(400);
            drive.followTrajectorySync(parkupper);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(1000);;
        }
        else if(valMid == 0){ // STONE MIDDLE
            drive.setPoseEstimate(startPosemiddle);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            waitForStart();
            mech.IngesterMotor.setPower(0.5); //Off is the same but setPower(0)
            drive.followTrajectorySync(toStone1middle);
            drive.followTrajectorySync(ingestStone1middle);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_CLOSED);
            sleep(300);
            drive.followTrajectorySync(toFoundation1middle);
            mech.ExtendGripper.setTargetPosition(NoDriveConfigure.EXTEND_OUT);
            mech.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mech.ExtendGripper.setPower(1);
            mech.FoundationLeft.setPosition(NoDriveConfigure.LEFT_CLOSE);
            mech.FoundationRight.setPosition(NoDriveConfigure.RIGHT_CLOSE);
            sleep(500);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(600);
            mech.ExtendGripper.setTargetPosition(0);
            drive.followTrajectorySync(pullFoundationmiddle);
            mech.FoundationLeft.setPosition(NoDriveConfigure.LEFT_OPEN);
            mech.FoundationRight.setPosition(NoDriveConfigure.RIGHT_OPEN);
            sleep(500);
            drive.followTrajectorySync(toStone2middle);
            drive.followTrajectorySync(ingestStone2middle);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_CLOSED);
            sleep(300);
            drive.followTrajectorySync(toFoundation2middle);
            mech.ExtendGripper.setTargetPosition(NoDriveConfigure.EXTEND_OUT);
            sleep(200);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(600);
            mech.ExtendGripper.setTargetPosition(0);
            sleep(400);
            drive.followTrajectorySync(parkmiddle);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(1000);;
        }
        else{ // STONE RIGHT
            drive.setPoseEstimate(startPoselower);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            waitForStart();
            mech.IngesterMotor.setPower(0.5); //Off is the same but setPower(0)
            drive.followTrajectorySync(toStone1lower);
            drive.followTrajectorySync(ingestStone1lower);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_CLOSED);
            sleep(300);
            drive.followTrajectorySync(toFoundation1lower);
            mech.ExtendGripper.setTargetPosition(NoDriveConfigure.EXTEND_OUT);
            mech.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            mech.ExtendGripper.setPower(0.8);
            mech.FoundationLeft.setPosition(NoDriveConfigure.LEFT_CLOSE);
            mech.FoundationRight.setPosition(NoDriveConfigure.RIGHT_CLOSE);
            sleep(300);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(400);
            mech.ExtendGripper.setTargetPosition(0);
            drive.followTrajectorySync(pullFoundationlower);
            mech.FoundationLeft.setPosition(NoDriveConfigure.LEFT_OPEN);
            mech.FoundationRight.setPosition(NoDriveConfigure.RIGHT_OPEN);
            sleep(500);
            drive.followTrajectorySync(toStone2lower);
            drive.followTrajectorySync(ingestStone2lower);
            drive.followTrajectorySync(ingestStone2Drivelower);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_CLOSED);
            sleep(300);
            drive.followTrajectorySync(toFoundation2lower);
            mech.ExtendGripper.setTargetPosition(NoDriveConfigure.EXTEND_OUT);
            sleep(200);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(400);
            mech.ExtendGripper.setTargetPosition(0);
            sleep(300);
            drive.followTrajectorySync(parklower);
            mech.Gripper.setPosition(NoDriveConfigure.GRIPPER_OPEN);
            sleep(1000);
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
