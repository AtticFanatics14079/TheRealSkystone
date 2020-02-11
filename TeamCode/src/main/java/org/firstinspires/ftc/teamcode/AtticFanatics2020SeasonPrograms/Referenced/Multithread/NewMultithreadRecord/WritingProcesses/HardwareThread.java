package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord.WritingProcesses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord.Configuration;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord.Setup.DriveObject;

public class HardwareThread extends Thread {

    ElapsedTime time;
    private ValueStorage vals;
    double[] readVals; //See hardwareValues in ValueStorage for each value.
    boolean[] desiredVals; //See hardwareValues in ValueStorage for each value.
    double[] runValues; //See hardwareValues in ValueStorage for each value.
    Configuration config;
    private volatile boolean stop;
    private boolean setTime = false;
    public double voltMult = 1, lastTime = 0;

    HardwareThread(ValueStorage valStorage, HardwareMap hwMap){
        this.vals = valStorage;
        config = new Configuration(hwMap);
        vals.setup(config.hardware.size());
        readVals = runValues = new double[config.hardware.size()];
        desiredVals = new boolean[config.hardware.size()];
        vals.time(true, 0.0);
        vals.changedParts(true, desiredVals);
        //voltMult = 13.0/config.voltSense.getVoltage();
        config.setBulkCachingManual();
    }

    public void run(){
        while(!setTime && !stop){}
        while(!stop) {
            if(time.milliseconds() - lastTime >= 1) {
                readHardware(vals.changedParts(false, null));
                vals.hardware(true, readVals);
                vals.time(true, time.milliseconds());
                runHardware(vals.runValues(false, null), vals.changedParts(false, null));
            }
        }
    }

    public void startTime(ElapsedTime time){
        this.time = time;
        setTime = true;
    }

    private void readHardware(boolean[] changedParts){

        config.clearBulkCache();

        desiredVals = changedParts.clone();

        for(int i = 0; i < config.hardware.size(); i++)
            if(desiredVals[i])
                readVals[i] = config.hardware.get(i).get();
    }

    private void runHardware(double[] Values, boolean[] desiredVals) {
        //Same values for desiredParts as above's desiredVals.

        runValues = Values.clone();
        desiredVals = desiredVals.clone();

        for(int i = 0; i < config.hardware.size(); i++)
            if (desiredVals[i])
                config.hardware.get(i).set(runValues[i]);
    }

    public void Stop(){
        stop = true;
    }
}
