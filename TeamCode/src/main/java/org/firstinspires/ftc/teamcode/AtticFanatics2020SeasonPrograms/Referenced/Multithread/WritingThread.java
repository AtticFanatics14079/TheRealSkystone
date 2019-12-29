package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import android.content.Context;
import android.os.Environment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WritingThread extends Thread{

    ValueStorage vals;
    File file;
    public volatile boolean stop = false, Start = false;
    public double lastTime = 0, time;
    private double temp;
    BufferedWriter fileWrite;
    FileWriter fWriter;
    double[] Values = new double[10];
    boolean trace1, trace2, trace3, trace4, trace5, trace6, trace7;
    private FileOutputStream fos;

    WritingThread(ValueStorage Vals, String fileName) {
        /*vals = Vals;
        cont = cxt;
        file = new File(fileName);
        try {
            //if(file.exists()) file.delete();
            //file.createNewFile();
            error = false;
            //fos = cont.openFileOutput(fileName, Context.MODE_PRIVATE);

            FileWriter FileWrite = new FileWriter(file.getPath());
            fileOutputError = false;
            fileWrite = new BufferedWriter(new FileWriter(fileName));
            error2 = false;
        }
        catch(Exception e){
            System.out.println("Error " + e);
        }
        System.out.println(file.getAbsolutePath());
        */

        this.vals = Vals;

        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileName);
            if(file.exists()) {
                boolean fileDeleted = file.delete();
                if(!fileDeleted) {
                }

                boolean fileCreated = file.createNewFile();
                if(!fileCreated) {
                }
            }

            fos = new FileOutputStream(file, true);

            try {
                fWriter = new FileWriter(fos.getFD());
                fileWrite = new BufferedWriter(fWriter);
            } catch (Exception e) {
                System.out.println("Error " + e);
            }
        } catch (Exception e) {
            System.out.println("Error2 " + e);
        }
    }

    public void run(){
        while(!Start && !stop){
            trace1 = true;
        }
        while(!stop){
            try{
                trace2 = true;
                if((time = vals.time(false, 0)) >  lastTime){
                    trace3 = true;
                    Values = vals.hardware(false, null, 0);
                    trace4 = true;
                    fileWrite.write(String.valueOf(time));
                    //fWriter.write(String.valueOf(time));
                    trace5 = true;
                    for(int i = 0; i < 10; i++){ //Change the < variable to account for size of hardware arraylist.
                        trace6 = true;
                        try {
                            fileWrite.write(" " + (double)Math.round(Values[i] * 1000.0) / 1000.0);
                            //fWriter.write(" " + (double)Math.round(Values[i] * 1000.0) / 1000.0);
                            trace7 = true;
                        }
                        catch(Exception ex) {
                            System.out.println("Exception " + ex + " " + time);
                            fileWrite.write(" 0.0");
                            //fWriter.write(" 0.0");
                        }
                    }
                    fileWrite.newLine();
                    //fWriter.write("\n");
                    lastTime = time;
                }
            }
            catch(Exception e){
                System.out.println("Writing Thread, " + e);
            }
        }
        try {
            //fWriter.flush();
            fileWrite.close();
            fWriter.close();
            fos.getFD().sync();
            fos.close();
        }
        catch(Exception e){
            System.out.println("Closing failed because " + e);
        }
    }

    public void Stop(){
        stop = true;
    }

    public void Start(){
        Start = true;
    }
}
