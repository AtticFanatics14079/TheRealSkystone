package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;


public class HardwareThread extends Thread {

    ElapsedTime time;
    private ValueStorage vals;
    double[] readVals; //See hardwareValues in ValueStorage for each value.
    Boolean[] desiredVals; //See hardwareValues in ValueStorage for each value.
    double[] runValues; //See hardwareValues in ValueStorage for each value.
    public Configuration config;
    private volatile boolean stop;
    private boolean setTime = false;
    public double voltMult = 1, lastTime = 0;

    public HardwareThread(ValueStorage valStorage, HardwareMap hwMap){
        this.vals = valStorage;
        config = new Configuration(hwMap, vals);
        vals.setup(config.hardware.size());
        readVals = runValues = new double[config.hardware.size()];
        desiredVals = new Boolean[config.hardware.size()];
        Arrays.fill(desiredVals, false);
        vals.time(true, 0.0);
        vals.changedParts(true, desiredVals);
        //voltMult = 13.0/config.voltSense.getVoltage();
        //config.setBulkCachingManual();
    }

    public void run(){
        while(!setTime && !stop){}
        while(!stop) {
            if(time.milliseconds() - lastTime >= 1) {
                lastTime = time.milliseconds();
                vals.time(true, time.milliseconds());
                readHardware(vals.changedParts(false, null));
                vals.hardware(true, readVals);
                runHardware(vals.runValues(false, null), vals.changedParts(false, null));
            }
        }
    }

    public void startTime(ElapsedTime time){
        try {
            sleep((long) 0.5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.time = time;
        setTime = true;
    }

    private void readHardware(Boolean[] changedParts){

        //config.clearBulkCache();

        desiredVals = changedParts.clone();

        for(int i = 0; i < config.hardware.size(); i++)
            if(desiredVals[i])
                readVals[i] = config.hardware.get(i).getHardware();
    }

    private void runHardware(double[] Values, Boolean[] desiredVals) {
        //Same values for desiredParts as above's desiredVals.

        runValues = Values.clone();
        desiredVals = desiredVals.clone();

        for(int i = 0; i < config.hardware.size(); i++)
            if (desiredVals[i])
                config.hardware.get(i).setHardware(runValues[i]);
    }

    public void Stop(){
        stop = true;
    }
}
