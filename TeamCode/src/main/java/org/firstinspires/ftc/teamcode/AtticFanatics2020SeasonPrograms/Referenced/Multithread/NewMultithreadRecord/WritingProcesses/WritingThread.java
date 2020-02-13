package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord.WritingProcesses;

//import android.content.Context;
//import android.os.Environment;

//import android.os.Environment;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DecimalFormat;

public class WritingThread extends Thread{

    private ValueStorage vals;
    private File file;
    private volatile boolean stop = false, Start = false;
    private BufferedWriter fileWrite;

    public WritingThread(ValueStorage valStorage, String fileName) {

        this.vals = valStorage;

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

            fileWrite = new BufferedWriter(new FileWriter(new FileOutputStream(file).getFD()));

        } catch (Exception e) {
            System.out.println("Exception " + e + " in WritingThread.");
        }
    }

    public void run(){
        double LastTime = -1, Time;
        double[] Values;
        DecimalFormat df = new DecimalFormat("+0.000;-");
        while(!Start && !stop){}
        while(!stop){
            try{
                if((Time = vals.time(false, 0)) - 0.1 > LastTime){
                    Values = vals.hardware(false, null);
                    fileWrite.write(String.valueOf(Time));
                    for(int i = 0; i < Values.length; i++){
                        try {
                            fileWrite.write(" " + df.format(Values[i]));
                        }
                        catch(Exception ex) {
                            System.out.println("Exception " + ex + " " + Time);
                            fileWrite.write(" 0.0");
                        }
                    }
                    fileWrite.newLine();
                    LastTime = Time;
                }
            }
            catch(Exception e){
                System.out.println("Writing Thread, " + e);
            }
        }
        try {
            fileWrite.flush();
            fileWrite.close();
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
