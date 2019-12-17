package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.AtticFanaticsCodes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WritingThread extends Thread{

    ValueStorage vals;
    File file;
    public volatile boolean stop;
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
                if((time = vals.Time) - 10 >  lastTime){
                    Values.clear();
                    Values = vals.hardware(false, null, 0);
                    if(Values != null) {
                        fileWrite.write(time + " ");
                        for(int i = 0; i < Values.size(); i++){
                            try {
                                fileWrite.write(i + " " + Values.get(i) + " ");
                            }
                            catch(Exception ex) {fileWrite.write(i + " 0.0 ");}
                        }
                        fileWrite.newLine();
                    }
                    lastTime = time;
                }
            }
            catch(Exception e){
                System.out.println("Writing Thread, " + e);
            }
        }
    }

    public void Stop(){
        stop = true;
    }
}
