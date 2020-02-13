package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Autonomous(name="Read File V3")
public class ReadFileAuto_V3 extends LinearOpMode {

    private final String fileName = "Test.txt";
    double[] tempValues = new double[11], prevLine = new double[11];
    ArrayList<double[]> Values = new ArrayList<>();
    BufferedReader reader;
    FileInputStream fis;
    int count = 0;
    double tempTime, voltMult = 1;
    StatesConfigure robot = new StatesConfigure();
    public ArrayList<String> lines = new ArrayList<>();

    @Override
    public void runOpMode() throws InterruptedException {

        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileName);

        robot.Configure(hardwareMap);

        //voltMult = 13.0/robot.voltSense.getVoltage();

        try {
            fis = new FileInputStream(file);
            reader = new BufferedReader(new FileReader(fis.getFD()));
        }
        catch (IOException e) {
        }

        String tempString = "";

        int index;

        while(!isStopRequested()){
            try {
                if((tempString = reader.readLine()) == null) break;
                tempValues[0] = Double.valueOf(tempString.substring(0, (index = tempString.indexOf(" "))));
                for(int n = 1; n < 11; n++) tempValues[n] = Double.valueOf(tempString.substring(index, (index += 7)));
                Values.add(tempValues);
                tempValues = new double[11];
            }
            catch (IOException e) {
            }
        }

        try {
            reader.close();
            fis.getFD().sync();
            fis.close();
        } catch (IOException e) {
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
        voltMult = 13.0/robot.voltSense.getVoltage();
        if(prevLine[1] != oneLine[1]) robot.Motors[1].setPower(oneLine[1] * voltMult);
        if(prevLine[2] != oneLine[2]) robot.Motors[2].setPower(oneLine[2] * voltMult);
        if(prevLine[3] != oneLine[3]) robot.Motors[3].setPower(oneLine[3] * voltMult);
        if(prevLine[4] != oneLine[4]) robot.Motors[4].setPower(oneLine[4] * voltMult);
        if(prevLine[5] != oneLine[5]) robot.ingester.setPower(oneLine[5]);
        if(prevLine[7] != oneLine[6]) robot.FoundationLeft.setPosition(oneLine[6]);
        if(prevLine[8] != oneLine[7]) robot.FoundationRight.setPosition(oneLine[7]);
        if(prevLine[9] != oneLine[8]) {
            robot.ExtendGripper.setTargetPosition((int)oneLine[8]);
            robot.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.ExtendGripper.setPower(1);
        }
        if(prevLine[10] != oneLine[9]) robot.Gripper.setPosition(oneLine[9]);
        if(prevLine[11] != oneLine[11]) {
            robot.ScissorLeft.setTargetPosition((int)oneLine[11]);
            robot.ScissorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.ScissorLeft.setPower(oneLine[11]/Math.abs(oneLine[11]));
        }
        if(prevLine[12] != oneLine[12]) {
            robot.ScissorRight.setTargetPosition((int)oneLine[12]);
            robot.ScissorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.ScissorRight.setPower(oneLine[11]/Math.abs(oneLine[11]));
        }
    }
}
