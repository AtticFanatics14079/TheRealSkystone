package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import java.util.ArrayList;
import java.util.List;

public class ValueStorage {

    public volatile double Time = 0; //In milliseconds

    private double[] hardwareValues = new double[10]; //Current values are:
    // motor velocities (0-3), scissor velocity(4), foundation hook left and right positions (5 and 6),
    // extend velocity (7),rotate position (8), and gripper position (9).
    // Add more as needed, but talk to me to make sure the positions line up.

    public double[] runValues = new double[10]; //Same values as above, but used after algorithms
    //determine what the power should be.

    public boolean[] changedParts = new boolean[10]; //Same as hardwareValues. Tells which parts
    // should be updated.

    public double[] hardware(boolean writing, double[] values, double time){
        if(writing) {
            Time = time;
            hardwareValues = values.clone();
        }
        return hardwareValues;
    }

    public synchronized boolean[] changedParts(boolean Writing, boolean[] desiredParts){
        if (Writing) {
            changedParts = desiredParts.clone();
        }
        return changedParts;
    }

    public synchronized double[] runValues(boolean Writing, double[] values){
        if(Writing) {
            runValues = values.clone();
        }
        return runValues;
    }

    public synchronized double time(boolean writing, double time){
        if(writing){
            Time = time;
        }
        return Time;
    }
}