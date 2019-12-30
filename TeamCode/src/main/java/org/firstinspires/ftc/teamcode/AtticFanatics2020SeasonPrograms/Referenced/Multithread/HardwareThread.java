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
    public volatile boolean stop;
    private boolean setTime = false;

    HardwareThread(ValueStorage Vals, HardwareMap hwMap){
        this.vals = Vals;
        config.Configure(hwMap);
    }

    public void run(){
        while(!setTime && !stop){}
        while(!stop) {
            try {
                readHardware(vals.changedParts(false, null));
                vals.hardware(true, hardware);
                vals.time(true, time.milliseconds());
                runHardware(vals.runValues(false, null), vals.changedParts(false, null));
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
                        config.Motors[1].setPower(Vals[0]);
                        break;
                    case 1:
                        config.Motors[2].setPower(Vals[1]);
                        break;
                    case 2:
                        config.Motors[3].setPower(Vals[2]);
                        break;
                    case 3:
                        config.Motors[4].setPower(Vals[3]);
                        break;
                    case 4:
                        config.Scissor.setPower(Vals[4]);
                        break;
                    case 5:
                        config.FoundationLeft.setPosition(Vals[5]);
                        break;
                    case 6:
                        config.FoundationRight.setPosition(Vals[6]);
                        break;
                    case 7:
                        config.ExtendGripper.setPower(Vals[7]);
                        break;
                    case 8:
                        config.RotateGripper.setPosition(Vals[8]);
                        break;
                    case 9:
                        config.Gripper.setPosition(Vals[9]);
                        break;
                }
            }
        }
    }

    public void Stop(){
        stop = true;
    }
}
