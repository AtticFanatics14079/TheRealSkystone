package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.Recorded;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp2Configure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Autonomous(name="RedSideFoundationSkybridgePark")
public class RedSideFoundationSkybridgePark extends LinearOpMode {

    private final String fileName = "RedSideFoundationSkybridgePark.txt";
    double[] tempValues = new double[11], prevLine = new double[11];
    ArrayList<double[]> Values = new ArrayList<>();
    BufferedReader reader;
    FileInputStream fis;
    int count = 0;
    double tempTime;
    Comp2Configure robot = new Comp2Configure();
    public ArrayList<String> lines = new ArrayList<>();

    @Override
    public void runOpMode() throws InterruptedException {

        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileName);

        robot.Configure(hardwareMap);

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
}
