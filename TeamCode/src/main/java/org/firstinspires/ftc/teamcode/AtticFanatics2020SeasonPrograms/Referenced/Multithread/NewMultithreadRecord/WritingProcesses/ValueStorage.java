package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord.WritingProcesses;

public class ValueStorage {

    private volatile Double Time = 0.0; //In milliseconds

    private double[] hardwareValues;

    private double[] runValues; //Same values as above, but used after algorithms
    //determine what the power should be.

    private boolean[] changedParts; //Same as hardwareValues. Tells which parts
    // should be updated.

    public volatile boolean receivedDesiredVals = false;

    public void setup(int size){
        hardwareValues = runValues = new double[size];
        changedParts = new boolean[size];
    }

    public synchronized double[] hardware(boolean writing, double[] values){
        if(writing) {
            hardwareValues = values.clone();
            return null;
        }
        return hardwareValues;
    }

    public synchronized boolean[] changedParts(boolean Writing, boolean[] desiredParts){
        if (Writing) {
            changedParts = desiredParts.clone();
            receivedDesiredVals = false;
            return null;
        }
        receivedDesiredVals = true;
        return changedParts;
    }

    public synchronized double[] runValues(boolean Writing, double[] values){
        if(Writing) {
            runValues = values.clone();
            return null;
        }
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