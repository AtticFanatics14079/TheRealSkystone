package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.AtticFanaticsCodes;

import java.util.ArrayList;
import java.util.List;

public class ValueStorage {

    public double Time = 0; //In milliseconds

    private volatile List<Double> hardwareValues = new ArrayList<>(); //Current values are:
    // motor velocities (0-3), scissor velocity(4), foundation hook left and right positions (5 and 6),
    // extend velocity (7),rotate position (8), and gripper position (9).
    // Add more as needed, but talk to me to make sure the positions line up.

    public volatile List<Double> runValues = new ArrayList<>(); //Same values as above, but used after algorithms
    //determine what the power should be.

    public volatile List<Integer> changedParts = new ArrayList<>(); //Drive motors are 1, scissors are 2, gripper is 3,
    //foundation hooks are 4, extender is 5, and rotate is 6. Tells which parts should be updated.

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