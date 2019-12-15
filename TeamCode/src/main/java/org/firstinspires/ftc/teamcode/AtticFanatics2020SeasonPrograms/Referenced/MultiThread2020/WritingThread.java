package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.MultiThread2020;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WritingThread extends Thread{

    ValueStorage vals;
    File file;
    public boolean stop;
    private double lastTime = 0, time = -1;
    BufferedWriter fileWrite;
    List<Double> Values = new ArrayList<>();

    WritingThread(ValueStorage Vals, String fileName){
        vals = Vals;
        File file = new File(fileName);
        try {
            fileWrite = new BufferedWriter(new FileWriter(file));
        }
        catch(Exception e) {
        }
    }

    public void run(){
        while(!stop){
            try{
                if((time = vals.Time) != lastTime){
                    Values = vals.hardware(false, null, 0);
                    fileWrite.write(time + " ");
                    for(int i = 0; i < Values.size(); i++){
                        fileWrite.write(i + " " + Values.get(i));
                    }
                    fileWrite.newLine();
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }

    public void Stop(){
        stop = true;
    }
}
