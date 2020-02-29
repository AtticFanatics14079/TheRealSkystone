package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

public class ValueStorage {

    private volatile double Time = 0.0; //In milliseconds

    private double[] hardwareValues;

    private double[] runValues; //Same values as above, but used after algorithms
    //determine what the power should be.

    private Boolean[] changedParts; //Same as hardwareValues. Tells which parts
    // should be updated.

    public volatile boolean receivedDesiredVals = false;

    public void setup(int size){
        hardwareValues = runValues = new double[size];
        changedParts = new Boolean[size];
    }

    public synchronized double[] hardware(boolean writing, double[] values){
        if(writing) {
            hardwareValues = values.clone();
            return null;
        }
        return hardwareValues;
    }

    public synchronized Boolean[] changedParts(boolean Writing, Boolean[] desiredParts){
        if (Writing) {
            if(receivedDesiredVals) for(int i = 0; i < changedParts.length; i++) changedParts[i] = false;
            for(int i = 0; i < desiredParts.length; i++) if(desiredParts[i] != null) changedParts[i] = desiredParts[i];
            receivedDesiredVals = false;
            return null;
        }
        receivedDesiredVals = true;
        return changedParts;
    }

    public synchronized double[] runValues(boolean Writing, Double[] values){
        if(Writing) {
            for(int i = 0; i < values.length; i++) if(values[i] != null) runValues[i] = values[i];
            return null;
        }
        System.out.println(runValues[0]);
        return runValues;
    }

    public synchronized double time(boolean writing, double time){
        if(writing){
            Time = time;
            return 0;
        }
        return Time;
    }
}