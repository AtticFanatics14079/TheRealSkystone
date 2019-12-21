package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WritingThread extends Thread{

    ValueStorage vals;
    File file;
    public volatile boolean stop;
    private double lastTime = 0, time = -1;
    private Double temp;
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
                if((time = vals.time(false, 0)) - 5 >  lastTime){
                    Values.clear();
                    Values = vals.hardware(false, null, 0);
                    fileWrite.write(String.valueOf(time));
                    for(int i = 0; i < 10; i++){ //Change the < variable to account for size of hardware arraylist.
                        try {
                            if((temp = Values.get(i)) == null) temp = 0.0;
                            fileWrite.write(" " + (double)Math.round(temp * 1000.0) / 1000.0);
                        }
                        catch(Exception ex) {
                            System.out.println("Exception " + ex + " " + time);
                            fileWrite.write(" 0.0");
                        }
                        System.out.println("j");
                    }
                    fileWrite.newLine();
                    lastTime = time;
                }
            }
            catch(Exception e){
                System.out.println("Writing Thread, " + e);
            }
        }
        try {
            fileWrite.close();
        }
        catch(Exception e){
            System.out.println("Closing failed because " + e);
        }
    }

    public void Stop(){
        stop = true;
    }
}
