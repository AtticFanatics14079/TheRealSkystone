package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import android.os.Environment;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadingThread extends Thread{

    private Scanner reader;
    ElapsedTime time;
    ValueStorage Vals;
    File file;
    BufferedReader read;
    public volatile boolean Stop;
    String line = " ";
    private double nextTime = -1;
    private double[] runValues = new double[10], tempValues = new double[11], prevLine = new double[11];
    private boolean[] desiredVals = new boolean[10];
    private int count = 0;
    private double tempVal;
    private boolean setTime = false;
    boolean trace1, trace2, trace3;
    ArrayList<double[]> Values = new ArrayList<>();

    ReadingThread(ValueStorage vals, String fileName){
        this.Vals = vals;

        file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileName);

        try {
            //FileInputStream fis = new FileInputStream(file);
            reader = new Scanner(file);
        }
        catch(FileNotFoundException e){
        }
        Vals.runValues(true, runValues);

    }

    public void run() {
        while(!Stop && reader.hasNextLine()){
            for(int i = 0; i < 11; i++) tempValues[i] = reader.nextDouble();
            Values.add(tempValues);
            tempValues = new double[11];
        }
        nextTime = Values.get(0)[0];
        while(!setTime && !Stop){}
        while(!Stop){
            if(time.milliseconds() > nextTime) {
                addToRunValues(Values.get(count));
                Vals.runValues(true, runValues);
                Vals.changedParts(true, desiredVals);
                for(int i = 0; i < 10; i++) desiredVals[i] = false;
                prevLine = Values.get(count);
                count++;
                if(count == Values.size()) break;
                nextTime = Values.get(count)[0];
            }
        }
        reader.close();
    }

    public void Stop(){
        Stop = true;
    }

    public void startTime(){
        time = new ElapsedTime();
        setTime = true;
    }

    private void addToRunValues(double[] oneLine){
        if(prevLine[1] != oneLine[1]) {runValues[0] = oneLine[1]; desiredVals[0] = true;}
        if(prevLine[2] != oneLine[2]) {runValues[1] = oneLine[2]; desiredVals[1] = true;}
        if(prevLine[3] != oneLine[3]) {runValues[2] = oneLine[3]; desiredVals[2] = true;}
        if(prevLine[4] != oneLine[4]) {runValues[3] = oneLine[4]; desiredVals[3] = true;}
        if(prevLine[5] != oneLine[5]) {runValues[4] = oneLine[5]; desiredVals[4] = true;}
        if(prevLine[6] != oneLine[6]) {runValues[5] = oneLine[6]; desiredVals[5] = true;}
        if(prevLine[7] != oneLine[7]) {runValues[6] = oneLine[7]; desiredVals[6] = true;}
        if(prevLine[8] != oneLine[8]) {runValues[7] = oneLine[8]; desiredVals[7] = true;}
        if(prevLine[9] != oneLine[9]) {runValues[8] = oneLine[9]; desiredVals[8] = true;}
        if(prevLine[10] != oneLine[10]) {runValues[9] = oneLine[10]; desiredVals[9] = true;}
    }
}
