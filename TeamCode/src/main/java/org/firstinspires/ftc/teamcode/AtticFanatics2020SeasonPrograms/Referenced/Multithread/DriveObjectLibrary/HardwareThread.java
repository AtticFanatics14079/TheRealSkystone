package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import android.support.v4.util.Pair;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.Arrays;


public class HardwareThread extends Thread {

    ElapsedTime time;
    private ValueStorage vals;
    Pair<Double, Double>[] hardwareVals; //See hardwareValues in ValueStorage for each value.
    Boolean[] changedParts; //See hardwareValues in ValueStorage for each value.
    Double[] runVals; //See hardwareValues in ValueStorage for each value.
    //public Configuration config;
    public ConfigurationRR config;
    private volatile boolean stop;
    private boolean setTime = false;
    public double voltMult = 1, lastTime = 0;

    public HardwareThread(ValueStorage valStorage, HardwareMap hwMap){
        this.vals = valStorage;
        config = new ConfigurationRR(hwMap, vals);
        vals.setup(config.hardware.size());
        runVals = new Double[config.hardware.size()];
        hardwareVals = new Pair[config.hardware.size()];
        changedParts = new Boolean[config.hardware.size()];
        Arrays.fill(changedParts, false);
        Arrays.fill(runVals, 0.0);
        vals.time(true, 0.0);
        vals.changedParts(true, changedParts);
        vals.runValues(true, runVals);
        //voltMult = 13.0/config.voltSense.getVoltage();
        config.setBulkCachingManual();
    }

    public void run(){
        while(!setTime && !stop){}
        while(!stop) {
            if(time.milliseconds() - lastTime >= 5) {
                lastTime = time.milliseconds();
                vals.time(true, time.milliseconds());
                readHardware(vals.changedParts(false, null));
                vals.hardware(true, hardwareVals);
                runHardware(vals.runValues(false, null), vals.changedParts(false, null));
            }
        }
        for(DriveObject d : config.hardware) d.endAllThreads();
    }

    public void startTime(ElapsedTime time){
        try {
            sleep((long) 2.5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.time = time;
        setTime = true;
    }

    private void readHardware(Boolean[] changedParts){

        config.clearBulkCache();

        this.changedParts = changedParts.clone();

        for(int i = 0; i < config.hardware.size(); i++) {
            if (changedParts[i])
                hardwareVals[i] = config.hardware.get(i).getHardware();
        }
    }

    private void runHardware(double[] Values, Boolean[] changedParts) {
        //Same values for desiredParts as above's changedParts.

        for(int i = 0; i < Values.length; i++) runVals[i] = Values[i];
        this.changedParts = changedParts.clone();

        for(int i = 0; i < config.hardware.size(); i++) {
            if (this.changedParts[i])
                config.hardware.get(i).setHardware(runVals[i]);
        }
    }

    public void Stop(){
        stop = true;
    }
}
