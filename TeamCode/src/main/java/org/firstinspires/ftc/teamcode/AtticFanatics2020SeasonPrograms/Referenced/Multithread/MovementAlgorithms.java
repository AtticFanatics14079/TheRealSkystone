package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp1Configure;

public class MovementAlgorithms extends Comp1Configure {

    //Input any and all algorithms here, this function will be used to repeatedly run checks. Most
    //likely only used in Autonomous.

    private final double MAX_DRIVE_ACCELERATION = 0.001;

    public double[] getPower(double[] prevValues, double sidewaysPower, double forwardPower, double angularPower){
        double[] p = new double[4];
        p[0] = -sidewaysPower + forwardPower - angularPower;
        p[1] = sidewaysPower + forwardPower - angularPower;
        p[2] = -sidewaysPower + forwardPower + angularPower;
        p[3] = sidewaysPower + forwardPower + angularPower;
        double max = Math.max(1.0, Math.abs(p[0]));
        max = Math.max(max, Math.abs(p[1]));
        max = Math.max(max, Math.abs(p[2]));
        max = Math.max(max, Math.abs(p[3])); //Constant is the number of rotations/second at power 1
        if(Math.abs((p[0] = p[0]/max) - prevValues[0]) > MAX_DRIVE_ACCELERATION) p[0] = prevValues[0] + Math.abs(p[0] - prevValues[0]) / (p[0] - prevValues[0]) * MAX_DRIVE_ACCELERATION;
        if(Math.abs((p[1] = p[1]/max) - prevValues[1]) > MAX_DRIVE_ACCELERATION) p[1] = prevValues[1] + Math.abs(p[1] - prevValues[1]) / (p[1] - prevValues[1]) * MAX_DRIVE_ACCELERATION;
        if(Math.abs((p[2] = p[2]/max) - prevValues[2]) > MAX_DRIVE_ACCELERATION) p[2] = prevValues[2] + Math.abs(p[2] - prevValues[2]) / (p[2] - prevValues[2]) * MAX_DRIVE_ACCELERATION;
        if(Math.abs((p[3] = p[3]/max) - prevValues[3]) > MAX_DRIVE_ACCELERATION) p[3] = prevValues[3] + Math.abs(p[3] - prevValues[3]) / (p[3] - prevValues[3]) * MAX_DRIVE_ACCELERATION;
        //Should cap increase in speed over cycle time to MAX_DRIVE_ACCELERATION, may slightly mess up diagonal but since is changing the robot we can compensate.
        return p;
    }
}
