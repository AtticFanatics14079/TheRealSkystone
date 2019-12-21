package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import java.util.ArrayList;
import java.util.List;

public class ValueStorage {

    public double Time = 0; //In milliseconds

    private List<Double> hardwareValues = new ArrayList<>(); //Current values are:
    // motor velocities (0-3), scissor velocity(4), foundation hook left and right positions (5 and 6),
    // extend velocity (7),rotate position (8), and gripper position (9).
    // Add more as needed, but talk to me to make sure the positions line up.

    public List<Double> runValues = new ArrayList<>(); //Same values as above, but used after algorithms
    //determine what the power should be.

    public List<Integer> changedParts = new ArrayList<>(); //Same as hardwareValues. Tells which parts
    // should be updated.

    public List<Double> hardware(boolean writing, List<Double> values, double time){
        if(writing) {
            Time = time;
            hardwareValues.clear();
            hardwareValues.addAll(values);
        }
        return hardwareValues;
    }

    public synchronized List<Integer> changedParts(boolean Writing, List<Integer> desiredParts){
        if (Writing) {
            changedParts.clear();
            changedParts.addAll(desiredParts);
        }
        System.out.println(changedParts);
        return changedParts;
    }

    public synchronized List<Double> runValues(boolean Writing, List<Double> values){
        if(Writing) {
            runValues.clear();
            runValues.addAll(values);
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