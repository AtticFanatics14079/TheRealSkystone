package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadingThread extends Thread{

    private Scanner reader;
    ElapsedTime time;
    ValueStorage Vals;
    File file;
    BufferedReader read;
    public volatile boolean Stop;
    String line = " ";
    private double previousTime = -1;
    private double[] runValues = new double[10];
    private boolean[] desiredVals = new boolean[10];
    private int tempIn;
    private double tempVal;
    private boolean setTime = false;

    ReadingThread(ValueStorage vals, String fileName){
        this.Vals = vals;
        file = new File(fileName);
        try {
            reader = new Scanner(file);
        }
        catch(FileNotFoundException e){

        }
        Vals.runValues(true, runValues);
    }

    public void run() {
        while(!setTime && !Stop){}
        while(reader.hasNextLine() && !Stop){
            try {
                if(time.milliseconds() > previousTime) {
                    previousTime = (double)(reader.nextDouble());
                    for(int i = 0; i < 10; i++) {
                        if((runValues = Vals.runValues)[i] != (tempVal = reader.nextDouble())) {
                            runValues[i] = tempVal;
                            desiredVals[i] = true;
                        }
                    }
                    Vals.runValues(true, runValues);
                    Vals.changedParts(true, desiredVals);
                    for(int i = 0; i < 10; i++) desiredVals[i] = false;
                }
            }
            catch (Exception e){
                System.out.println("Reading Thread, " + e);
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
}
