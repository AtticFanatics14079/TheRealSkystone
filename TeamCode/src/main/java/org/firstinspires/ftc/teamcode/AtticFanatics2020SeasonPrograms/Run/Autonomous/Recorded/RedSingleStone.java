package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.Recorded;

import android.os.Environment;
import android.util.Log;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name="RedSingleStone") //CHANGE TO MATCH AUTO PATH NAME!!!
public class RedSingleStone extends LinearOpMode {

    private final String fileNameLeft = "RedLeftSingleSkybridge.txt"; //CHANGE THIS VARIABLE TO MATCH FILE NAME!!!
    private final String fileNameMiddle = "RedMiddleSingleSkybridge.txt"; //CHANGE THIS VARIABLE TO MATCH FILE NAME!!!
    private final String fileNameRight = "RedRightSingleSkybridge.txt"; //CHANGE THIS VARIABLE TO MATCH FILE NAME!!!

    //The only values that should be changed are marked above, everything below should remain a black box. Tell me if something goes wrong.

    double[] tempValuesLeft = new double[11];
    double[] tempValuesMiddle = new double[11];
    double[] tempValuesRight = new double[11];
    ArrayList<double[]> ValuesLeft = new ArrayList<>();
    ArrayList<double[]> ValuesMiddle = new ArrayList<>();
    ArrayList<double[]> ValuesRight = new ArrayList<>();
    BufferedReader reader1;
    BufferedReader reader2;
    BufferedReader reader3;
    FileInputStream fis1;
    FileInputStream fis2;
    FileInputStream fis3;
    public ArrayList<String> linesLeft = new ArrayList<>();
    public ArrayList<String> linesMiddle = new ArrayList<>();
    public ArrayList<String> linesRight = new ArrayList<>();

    double [] prevLine = new double[11];
    int count = 0;
    double tempTime;
    Comp2Configure robot = new Comp2Configure();

    //0 means skystone, 1 means yellow stone
    //-1 for debug, but we can keep it like this because if it works, it should change to either 0 or 255
    private static int valMid = -1;
    private static int valLeft = -1;
    private static int valRight = -1;

    private static float rectHeight = 1.5f / 8f;
    private static float rectWidth = .6f / 8f;

    private static float offsetX = 0f / 8f;//changing this moves the three rects and the three circles left or right, range : (-2, 2) not inclusive
    private static float offsetY = 3f / 8f;//changing this moves the three rects and circles up or down, range: (-4, 4) not inclusive

    private static float[] midPos = {4f / 8f + offsetX, 3f / 10f + offsetY};//0 = col, 1 = row
    private static float[] leftPos = {4f / 8f + offsetX, 1f / 10f + offsetY};
    private static float[] rightPos = {4f / 8f + offsetX, 5f / 10f + offsetY};
    //moves all rectangles right or left by amount. units are in ratio to monitor

    private final int rows = 640;
    private final int cols = 480;

    OpenCvCamera phoneCam;


    @Override
    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();//open camera
        phoneCam.setPipeline(new StageSwitchingPipeline());//different stages
        phoneCam.startStreaming(rows, cols, OpenCvCameraRotation.UPRIGHT);

        File fileLeft = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileNameLeft);
        File fileMiddle = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileNameMiddle);
        File fileRight = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileNameRight);

        robot.Configure(hardwareMap);

        try {
            fis1 = new FileInputStream(fileLeft);
            reader1 = new BufferedReader(new FileReader(fis1.getFD()));
            fis2 = new FileInputStream(fileMiddle);
            reader2 = new BufferedReader(new FileReader(fis2.getFD()));
            fis3 = new FileInputStream(fileRight);
            reader3 = new BufferedReader(new FileReader(fis3.getFD()));
        }
        catch (IOException e) {
        }

        String tempString = "";

        while(!isStopRequested()){
            try {
                if((tempString = reader1.readLine()) == null) break;
                linesLeft.add(tempString);
            }
            catch (IOException e) {
            }
        }
        while(!isStopRequested()){
            try {
                if((tempString = reader2.readLine()) == null) break;
                linesMiddle.add(tempString);
            }
            catch (IOException e) {
            }
        }
        while(!isStopRequested()){
            try {
                if((tempString = reader3.readLine()) == null) break;
                linesRight.add(tempString);
            }
            catch (IOException e) {
            }
        }

        telemetry.addLine("File read, beginning string parsing.");
        telemetry.update();

        try {
            reader1.close();
            fis1.getFD().sync();
            fis1.close();
            reader2.close();
            fis2.getFD().sync();
            fis2.close();
            reader3.close();
            fis3.getFD().sync();
            fis3.close();
        } catch (IOException e) {

        }

        int index;

        for(int i = 0; i < linesLeft.size() && !isStopRequested(); i++){
            tempValuesLeft[0] = Double.valueOf(linesLeft.get(i).substring(0, (index = linesLeft.get(i).indexOf(" "))));
            for(int n = 1; n < 11; n++) tempValuesLeft[n] = Double.valueOf(linesLeft.get(i).substring(index, (index += 7)));
            ValuesLeft.add(tempValuesLeft);
            tempValuesLeft = new double[11];
        }
        for(int i = 0; i < linesMiddle.size() && !isStopRequested(); i++){
            tempValuesMiddle[0] = Double.valueOf(linesMiddle.get(i).substring(0, (index = linesMiddle.get(i).indexOf(" "))));
            for(int n = 1; n < 11; n++) tempValuesMiddle[n] = Double.valueOf(linesMiddle.get(i).substring(index, (index += 7)));
            ValuesMiddle.add(tempValuesMiddle);
            tempValuesMiddle = new double[11];
        }
        for(int i = 0; i < linesRight.size() && !isStopRequested(); i++){
            tempValuesRight[0] = Double.valueOf(linesRight.get(i).substring(0, (index = linesRight.get(i).indexOf(" "))));
            for(int n = 1; n < 11; n++) tempValuesRight[n] = Double.valueOf(linesRight.get(i).substring(index, (index += 7)));
            ValuesRight.add(tempValuesRight);
            tempValuesRight = new double[11];
        }

        telemetry.addLine("Parsing complete");
        telemetry.update();

        waitForStart();
        ElapsedTime time = new ElapsedTime();

        if(valMid == 0){
            tempTime = ValuesMiddle.get(0)[0];
            Log.e("TAG","valMid");
            while(!isStopRequested()){
                if(time.milliseconds() > tempTime){
                    setHardware(ValuesMiddle.get(count));
                    prevLine = ValuesMiddle.get(count);
                    count++;
                    if(count == ValuesMiddle.size()) break;
                    tempTime = ValuesMiddle.get(count)[0];
                }
            }
        }else if(valLeft == 0){ // DONT ASK
            tempTime = ValuesRight.get(0)[0];
            Log.e("TAG","valRight");
            while(!isStopRequested()){
                if(time.milliseconds() > tempTime){
                    setHardware(ValuesRight.get(count));
                    prevLine = ValuesRight.get(count);
                    count++;
                    if(count == ValuesRight.size()) break;
                    tempTime = ValuesRight.get(count)[0];
                }
            }
        }
        else{ //do ValLeft Stuff
            tempTime = ValuesLeft.get(0)[0];
            Log.e("TAG","valLeft");
            while(!isStopRequested()){
                Log.e("TAG","Entered Loop");
                if(time.milliseconds() > tempTime){
                    Log.e("TAG","1");
                    setHardware(ValuesLeft.get(count));
                    Log.e("TAG","2");
                    prevLine = ValuesLeft.get(count);
                    count++;
                    if(count == ValuesLeft.size()) break;
                    tempTime = ValuesLeft.get(count)[0];
                    Log.e("TAG","3");
                }
            }
        }
        double[] temp = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        setHardware(temp);
    }

    private void setHardware(double[] oneLine){
        if(prevLine[1] != oneLine[1]) robot.Motors[1].setPower(0.8*oneLine[1]);
        if(prevLine[2] != oneLine[2]) robot.Motors[2].setPower(0.8*oneLine[2]);
        if(prevLine[3] != oneLine[3]) robot.Motors[3].setPower(0.8*oneLine[3]);
        if(prevLine[4] != oneLine[4]) robot.Motors[4].setPower(0.8*oneLine[4]);
        if(prevLine[5] != oneLine[5]) robot.IngesterLeft.setPower(oneLine[5]);
        if(prevLine[6] != oneLine[6]) robot.IngesterRight.setPower(oneLine[6]);
        if(prevLine[7] != oneLine[7]) robot.FoundationLeft.setPosition(oneLine[7]);
        if(prevLine[8] != oneLine[8]) robot.FoundationRight.setPosition(oneLine[8]);
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