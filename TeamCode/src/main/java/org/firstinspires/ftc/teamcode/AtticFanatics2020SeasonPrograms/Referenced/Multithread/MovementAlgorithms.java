package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;

import java.util.ArrayList;
import java.util.List;

public class MovementAlgorithms extends Configure {

    //Input any and all algorithms here, this function will be used to repeatedly run checks. Most
    //likely only used in Autonomous.

    public double[] getVelocity(double sidewaysPower, double forwardPower, double angularPower){
        double[] p = new double[4];
        p[0] = -sidewaysPower + forwardPower - angularPower;
        p[1] = sidewaysPower + forwardPower - angularPower;
        p[2] = -sidewaysPower + forwardPower + angularPower;
        p[3] = sidewaysPower + forwardPower + angularPower;
        double max = Math.max(1.0, Math.abs(p[0]));
        max = Math.max(max, Math.abs(p[1]));
        max = Math.max(max, Math.abs(p[2]));
        max = Math.max(max, Math.abs(p[3])); //Constant is the number of rotations/second at power 1
        p[0] = p[0]/ max;
        p[1] = p[1]/ max;
        p[2] = p[2]/ max;
        p[3] = p[3]/ max;

        return p;
    }
}
