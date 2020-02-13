package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

//import android.content.Context;
//import android.os.Environment;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DecimalFormat;

public class WritingThread extends Thread{

    ValueStorage vals;
    File file;
    public volatile boolean stop = false, Start = false;
    BufferedWriter fileWrite;
    FileWriter fWriter;
    double[] Values = new double[10];
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
            file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileName);
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
        double LastTime = -1, Time;
        DecimalFormat df = new DecimalFormat("+0.000;-");
        while(!Start && !stop){}
        while(!stop){
            try{
                //if((time = vals.time(false, 0)) - 1 >  lastTime){
                if((Time = vals.time(false, 0)) - 0.1 > LastTime){
                    Values = vals.hardware(false, null);
                    fileWrite.write(String.valueOf(Time));
                    //fWriter.write(String.valueOf(time));
                    for(int i = 0; i < 10; i++){ //Change the < variable to account for size of hardware arraylist.
                        try {
                            fileWrite.write(" " + df.format(Values[i]));
                            //fWriter.write(" " + (double)Math.round(Values[i] * 1000.0) / 1000.0);
                        }
                        catch(Exception ex) {
                            System.out.println("Exception " + ex + " " + Time);
                            fileWrite.write(" 0.0");
                            //fWriter.write(" 0.0");
                        }
                    }
                    fileWrite.newLine();
                    //fWriter.write("\n");
                    //fWriter.flush();
                    LastTime = Time;
                }
            }
            catch(Exception e){
                System.out.println("Writing Thread, " + e);
            }
        }
        try {
            fileWrite.flush();
            fWriter.flush();
            fileWrite.close();
            fWriter.close();
            fos.getFD().sync();
            fos.close();
        }
        catch(Exception e){
            System.out.println("Closing failed because " + e);
        }
        finally {
            System.gc();
        }
    }

    public void Stop(){
        stop = true;
    }

    public void Start(){
        Start = true;
    }
}
