package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;

import java.util.ArrayList;
import java.util.List;

public class MovementAlgorithms extends Configure {

    //Input any and all algorithms here, this function will be used to repeatedly run checks. Most
    //likely only used in Autonomous.

    public List<Double> getVelocity(double sidewaysPower, double forwardPower, double angularPower){
        List<Double> p = new ArrayList<>();
        p.add(-sidewaysPower + forwardPower - angularPower);
        p.add(sidewaysPower + forwardPower - angularPower);
        p.add(-sidewaysPower + forwardPower + angularPower);
        p.add(sidewaysPower + forwardPower + angularPower);
        double max = Math.max(1.0, Math.abs(p.get(0)));
        max = Math.max(max, Math.abs(p.get(1)));
        max = Math.max(max, Math.abs(p.get(2)));
        max = Math.max(max, Math.abs(p.get(3))); //Constant is the number of rotations/second at power 1
        p.set(0, p.get(0)/ max);
        p.set(1, p.get(1)/ max);
        p.set(2, p.get(2)/ max);
        p.set(3, p.get(3)/ max);

        return p;
    }

    public List<Double> accountForSmallDistances(List<Double> runValues){
        for(int i = 0; i < runValues.size() - 1; i++){
            if(runValues.get(i) < 0.1) runValues.set(i, 0.0);
        }
        return  runValues;
    }
}
