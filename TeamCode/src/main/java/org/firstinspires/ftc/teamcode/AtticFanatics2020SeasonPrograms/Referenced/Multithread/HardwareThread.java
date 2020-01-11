package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;

import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HardwareThread extends Thread {

    ElapsedTime time;
    Configure config = new Configure();
    private ValueStorage vals;
    private int hardwareSize = 10;
    double[] hardware = new double[10]; //See hardwareValues in ValueStorage for each value.
    boolean[] DesiredVals = new boolean[10]; //See hardwareValues in ValueStorage for each value.
    double[] Vals = new double[10]; //See hardwareValues in ValueStorage for each value.
    double[] prevVals = new double[10]; //See hardwareValues in ValueStorage for each value.
    public volatile boolean stop;
    public boolean ready = false;
    private boolean setTime = false;
    private final double MAX_ACCELERATION = 2;

    HardwareThread(ValueStorage Vals, HardwareMap hwMap){
        this.vals = Vals;
        config.Configure(hwMap);
        vals.time(true, 0.0);
    }

    public void run(){
        while(!setTime && !stop){ready = true;}
        while(!stop) {
            try {
                readHardware(vals.changedParts(false, null));
                vals.hardware(true, hardware);
                vals.time(true, time.milliseconds());
                runHardware(vals.runValues(false, null), vals.changedParts(false, null));
                System.out.println("One hardware loop " + time.milliseconds());
                //That line looks sketch but I'm pretty sure it's runtime efficient.
            } catch (Exception e) {
                System.out.print("Houston, we have a problem. (Hardware thread), ");
                System.out.println(e);
            }
        }
        //SET EVERYTHING WE USE TO 0, VERY IMPORTANT!!!
        config.Motors[1].setPower(0);
        config.Motors[2].setPower(0);
        config.Motors[3].setPower(0);
        config.Motors[4].setPower(0);
    }

    public void startTime(){
        time = new ElapsedTime();
        setTime = true;
    }

    private void readHardware(boolean[] desiredVals){
        //For sake of runtime, asking for drive motors will be 1 in desiredVals, scissor will be 2,
        //gripper will be 3, foundation hooks will be 4, extender will be 5, and rotate will be 6.

        DesiredVals = desiredVals.clone();

        for(int i = 0; i < 10; i++){
            if(DesiredVals[i]) {
                switch (i) {
                    case 0:
                        hardware[0] = config.Motors[1].getPower();
                        break;
                    case 1:
                        hardware[1] = config.Motors[2].getPower();
                        break;
                    case 2:
                        hardware[2] = config.Motors[3].getPower();
                        break;
                    case 3:
                        hardware[3] = config.Motors[4].getPower();
                        break;
                    case 4:
                        hardware[4] = config.Scissor.getPower();
                        break;
                    case 5:
                        hardware[5] = config.FoundationLeft.getPosition();
                        break;
                    case 6:
                        hardware[6] = config.FoundationRight.getPosition();
                        break;
                    case 7:
                        hardware[7] = config.ExtendGripper.getPower();
                        break;
                    case 8:
                        hardware[8] = config.RotateGripper.getPosition();
                        break;
                    case 9:
                        hardware[9] = config.Gripper.getPosition();
                        break;
                }
            }
        }
    }

    private void runHardware(double[] Values, boolean[] desiredParts) {
        //Same values for desiredParts as above's desiredVals.

        Vals = Values.clone();
        DesiredVals = desiredParts.clone();

        for(int i = 0; i < 10; i++) {
            if (DesiredVals[i]) {
                switch (i) {
                    case 0:
                        if(Math.abs(Vals[0] - prevVals[0]) > MAX_ACCELERATION) Vals[0] = prevVals[0] + MAX_ACCELERATION;
                        config.Motors[1].setPower(Vals[0]);
                        break;
                    case 1:
                        if(Vals[1] - prevVals[1] > MAX_ACCELERATION) Vals[1] = prevVals[1] + MAX_ACCELERATION;
                        config.Motors[2].setPower(Vals[1]);
                        break;
                    case 2:
                        if(Vals[2] - prevVals[2] > MAX_ACCELERATION) Vals[2] = prevVals[2] + MAX_ACCELERATION;
                        config.Motors[3].setPower(Vals[2]);
                        break;
                    case 3:
                        if(Vals[3] - prevVals[3] > MAX_ACCELERATION) Vals[3] = prevVals[3] + MAX_ACCELERATION;
                        config.Motors[4].setPower(Vals[3]);
                        break;
                    case 4:
                        if(Vals[4] - prevVals[4] > MAX_ACCELERATION) Vals[4] = prevVals[4] + MAX_ACCELERATION;
                        config.Scissor.setPower(Vals[4]);
                        break;
                    case 5:
                        //if(Vals[5] - prevVals[5] > MAX_ACCELERATION) Vals[5] = prevVals[5] + MAX_ACCELERATION;
                        config.FoundationLeft.setPosition(Vals[5]);
                        break;
                    case 6:
                        //if(Vals[6] - prevVals[6] > MAX_ACCELERATION) Vals[6] = prevVals[6] + MAX_ACCELERATION;
                        config.FoundationRight.setPosition(Vals[6]);
                        break;
                    case 7:
                        if(Vals[7] - prevVals[7] > MAX_ACCELERATION) Vals[7] = prevVals[7] + MAX_ACCELERATION;
                        config.ExtendGripper.setPower(Vals[7]);
                        break;
                    case 8:
                        //if(Vals[8] - prevVals[8] > MAX_ACCELERATION) Vals[8] = prevVals[8] + MAX_ACCELERATION;
                        config.RotateGripper.setPosition(Vals[8]);
                        break;
                    case 9:
                        //if(Vals[9] - prevVals[9] > MAX_ACCELERATION) Vals[9] = prevVals[9] + MAX_ACCELERATION;
                        config.Gripper.setPosition(Vals[9]);
                        break;
                }
            }
        }

        prevVals = Vals.clone();
    }

    public void Stop(){
        stop = true;
    }
}
