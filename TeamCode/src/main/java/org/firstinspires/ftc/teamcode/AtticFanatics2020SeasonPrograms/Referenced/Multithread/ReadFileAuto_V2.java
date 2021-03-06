package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp1Configure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@Disabled
@Autonomous(name="Read File V2")
public class ReadFileAuto_V2 extends LinearOpMode {

    private final String fileName = "Test.txt";
    double[] tempValues = new double[11], prevLine = new double[11];
    ArrayList<double[]> Values = new ArrayList<>();
    Scanner reader;
    int count = 0;
    double tempTime;
    Comp1Configure robot = new Comp1Configure();

    @Override
    public void runOpMode() throws InterruptedException {

        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileName);

        robot.Configure(hardwareMap);

        try {
            //FileInputStream fis = new FileInputStream(file);
            reader = new Scanner(file);
        }
        catch(FileNotFoundException e){
        }

        while(!isStopRequested() && reader.hasNextDouble()){
            for(int i = 0; i < 11; i++) tempValues[i] = reader.nextDouble();
            Values.add(tempValues);
            tempValues = new double[11];
        }

        telemetry.addLine("Complete file read");
        telemetry.update();

        waitForStart();

        ElapsedTime time = new ElapsedTime();

        tempTime = Values.get(0)[0];

        while(!isStopRequested() && time.seconds() < 31){
            if(time.milliseconds() > tempTime){
                System.out.println(time.milliseconds() + " " + tempTime);
                telemetry.update();
                setHardware(Values.get(count));
                prevLine = Values.get(count);
                count++;
                if(count == Values.size()) break;
                tempTime = Values.get(count)[0];
            }
        }

        reader.close();

        telemetry.addData("Count: ", count);
        telemetry.update();

        while(!isStopRequested()){}
    }

    private void setHardware(double[] oneLine){
        if(prevLine[1] != oneLine[1]) robot.Motors[1].setPower(oneLine[1]);
        if(prevLine[2] != oneLine[2]) robot.Motors[2].setPower(oneLine[2]);
        if(prevLine[3] != oneLine[3]) robot.Motors[3].setPower(oneLine[3]);
        if(prevLine[4] != oneLine[4]) robot.Motors[4].setPower(oneLine[4]);
        if(prevLine[5] != oneLine[5]) robot.Scissor.setPower(oneLine[5]);
        if(prevLine[6] != oneLine[6]) robot.FoundationLeft.setPosition(oneLine[6]);
        if(prevLine[7] != oneLine[7]) robot.FoundationRight.setPosition(oneLine[7]);
        if(prevLine[8] != oneLine[8]) robot.ExtendGripper.setPower(oneLine[8]);
        if(prevLine[9] != oneLine[9]) robot.RotateGripper.setPosition(oneLine[9]);
        if(prevLine[10] != oneLine[10]) robot.Gripper.setPosition(oneLine[10]);
    }
}
