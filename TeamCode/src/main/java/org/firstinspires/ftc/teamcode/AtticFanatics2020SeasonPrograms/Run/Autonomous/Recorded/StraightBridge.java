package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.Recorded;

import android.os.Environment;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp2Configure;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name="StraightBridge") //CHANGE TO MATCH AUTO PATH NAME!!!
public class StraightBridge extends LinearOpMode {

    Comp2Configure robot = new Comp2Configure();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.Configure(hardwareMap);
        waitForStart();
    robot.Motors[1].setPower(1);
    robot.Motors[2].setPower(1);
    robot.Motors[3].setPower(1);
    robot.Motors[4].setPower(1);
    sleep(300);
    robot.Motors[1].setPower(0);
    robot.Motors[2].setPower(0);
    robot.Motors[3].setPower(0);
    robot.Motors[4].setPower(0);


    }
}