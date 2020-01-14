package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name="Read File with Vision")
public class ReadFileWithSkystone extends LinearOpMode {

    private final String fileName = "Test.txt";
    double[] tempValues = new double[11], prevLine = new double[11];
    ArrayList<double[]> Values = new ArrayList<>();
    BufferedReader reader;
    FileInputStream fis;
    int count = 0;
    double tempTime;
    Comp2Configure robot = new Comp2Configure();
    public ArrayList<String> lines = new ArrayList<>();

    //VISION VARIABLES
    private ElapsedTime runtime = new ElapsedTime();
    //-1 for debug, but we can keep it like this because if it works, it should change to either 0 or 255
    private static int valMid = -1;
    private static int valLeft = -1;
    private static int valRight = -1;
    private static float rectHeight = 1.5f / 8f;
    private static float rectWidth = .6f / 8f;
    private static float offsetX = 0f / 8f;//changing this moves the three rects and the three circles left or right, range : (-2, 2) not inclusive
    private static float offsetY = 0f / 8f;//changing this moves the three rects and circles up or down, range: (-4, 4) not inclusive
    private static float[] midPos = {4f / 8f + offsetX, 4f / 8f + offsetY};//0 = col, 1 = row
    private static float[] leftPos = {4f / 8f + offsetX, 2f / 8f + offsetY};
    private static float[] rightPos = {4f / 8f + offsetX, 6f / 8f + offsetY};
    //moves all rectangles right or left by amount. units are in ratio to monitor
    private final int rows = 640;
    private final int cols = 480;

    OpenCvCamera phoneCam;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.Configure(hardwareMap);

        //INIT VISION
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();//open camera
        phoneCam.setPipeline(new StageSwitchingPipeline());//different stages
        phoneCam.startStreaming(rows, cols, OpenCvCameraRotation.UPRIGHT);//display on RC

        if(valRight == 0){
            //Read right skystone position file
        }
        else if(valLeft == 0){
            //Read left skystone position file
        }
        else{
            //Read middle skystone position file
        }
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileName);

        try {
            fis = new FileInputStream(file);
            reader = new BufferedReader(new FileReader(fis.getFD()));
        }
        catch (IOException e) {
        }

        String tempString = "";

        while(!isStopRequested()){
            try {
                if((tempString = reader.readLine()) == null) break;
                lines.add(tempString);
            }
            catch (IOException e) {
            }
        }

        telemetry.addLine("File read, beginning string parsing.");
        telemetry.update();

        try {
            reader.close();
            fis.getFD().sync();
            fis.close();
        } catch (IOException e) {

        }

        int index;

        for(int i = 0; i < lines.size() && !isStopRequested(); i++){
            tempValues[0] = Double.valueOf(lines.get(i).substring(0, (index = lines.get(i).indexOf(" "))));
            for(int n = 1; n < 11; n++) tempValues[n] = Double.valueOf(lines.get(i).substring(index, (index += 7)));
            Values.add(tempValues);
            tempValues = new double[11];
        }

        telemetry.addLine("Parsing complete, finished initialization.");
        telemetry.addData("Values", valLeft+"   "+valMid+"   "+valRight);
        telemetry.update();

        waitForStart();

        ElapsedTime time = new ElapsedTime();

        tempTime = Values.get(0)[0];

        while(!isStopRequested()){
            if(time.milliseconds() > tempTime){
                setHardware(Values.get(count));
                prevLine = Values.get(count);
                count++;
                if(count == Values.size()) break;
                tempTime = Values.get(count)[0];
            }
        }

        double[] temp = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        setHardware(temp);
    }

    private void setHardware(double[] oneLine){
        if(prevLine[1] != oneLine[1]) robot.Motors[1].setPower(oneLine[1]);
        if(prevLine[2] != oneLine[2]) robot.Motors[2].setPower(oneLine[2]);
        if(prevLine[3] != oneLine[3]) robot.Motors[3].setPower(oneLine[3]);
        if(prevLine[4] != oneLine[4]) robot.Motors[4].setPower(oneLine[4]);
        if(prevLine[5] != oneLine[5]) robot.IngesterLeft.setPower(oneLine[5]);
        if(prevLine[6] != oneLine[6]) robot.IngesterRight.setPower(oneLine[6]);
        if(prevLine[7] != oneLine[7]) robot.FoundationRight.setPosition(oneLine[7]);
        if(prevLine[8] != oneLine[8]) robot.ExtendGripper.setPower(oneLine[8]);
        if(prevLine[9] != oneLine[9]) robot.RotateGripper.setPosition(oneLine[9]);
        if(prevLine[10] != oneLine[10]) robot.Gripper.setPosition(oneLine[10]);
    }
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
