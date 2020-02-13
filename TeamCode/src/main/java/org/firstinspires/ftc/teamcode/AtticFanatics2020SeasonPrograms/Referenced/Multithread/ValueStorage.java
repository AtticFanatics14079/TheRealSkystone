package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

public class ValueStorage {

    public volatile Double Time = 0.0; //In milliseconds

    private double[] hardwareValues = new double[10]; //Current values are:
    // motor power (0-3), ingester powers (4 and 5), foundation hook left and right positions (6 and 7),
    // extend power (8), gripper position (9), and scissor powers (10 and 11).
    // Add more as needed, but talk to me to make sure the positions line up.

    public double[] runValues = new double[10]; //Same values as above, but used after algorithms
    //determine what the power should be.

    public boolean[] changedParts = new boolean[10]; //Same as hardwareValues. Tells which parts
    // should be updated.

    public volatile Double timeWritten;

    public int skystonePosition = -1;

    public volatile boolean receivedDesiredVals = false;

    public double[] hardware(boolean writing, double[] values){
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
            timeWritten = Time = time;
            return 0;
        }
        return Time;
    }

    public synchronized int skyPos(boolean writing, int pos){
        if(writing){
            skystonePosition = pos;
            return 0;
        }
        return skystonePosition;
    }
}