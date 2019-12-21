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
    private List<Double> runValues = new ArrayList<>();
    private List<Integer> desiredVals = new ArrayList<>();
    private int tempIn;
    private double tempVal;

    ReadingThread(ValueStorage vals, String fileName){
        this.Vals = vals;
        file = new File(fileName);
        try {
            reader = new Scanner(file);
        }
        catch(FileNotFoundException e){

        }
        for(int i = 0; i < 10; i++) runValues.add(0.0);
        Vals.runValues(true, runValues);
    }

    public void run() {
        while(reader.hasNextLine() && !Stop){
            try {
                if(time.milliseconds() > previousTime) {
                    System.out.println("yeet");
                    previousTime = (double)(reader.nextDouble());
                    System.out.println("1");
                    for(int i = 0; i < 10; i++) {
                        System.out.println("reading");
                        if(Vals.runValues.get(i) != (tempVal = reader.nextDouble())) {
                            runValues.set(i, tempVal);
                            desiredVals.add(i);
                        }
                    }
                    Vals.runValues(true, runValues);
                    Vals.changedParts(true, desiredVals);
                    desiredVals.clear();
                }
            }
            catch (Exception e){
                System.out.println("Reading Thread, " + e);
            }
        }

    }

    public void Stop(){
        Stop = true;
    }

    public void startTime(){
        time = new ElapsedTime();
    }
}
