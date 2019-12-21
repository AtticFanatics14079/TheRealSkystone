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
    List<Double> hardware = new ArrayList<>(); //See hardwareValues in ValueStorage for each value.
    List<Integer> DesiredVals = new ArrayList<>(); //See hardwareValues in ValueStorage for each value.
    List<Double> Vals = new ArrayList<>(); //See hardwareValues in ValueStorage for each value.
    public volatile boolean stop;
    Double[] p = {1.0, 2.0, 3.0, 4.0};

    HardwareThread(ValueStorage Vals, HardwareMap hwMap){
        this.vals = Vals;
        config.Configure(hwMap);
        for(int i = 0; i < hardwareSize; i++)
            hardware.add(0.0);
    }

    public void run(){
        while(!stop) {
            try {
                readHardware(vals.changedParts(false, null));
                vals.hardware(true, hardware, time.milliseconds());
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
    }

    private void readHardware(List<Integer> desiredVals){
        //For sake of runtime, asking for drive motors will be 1 in desiredVals, scissor will be 2,
        //gripper will be 3, foundation hooks will be 4, extender will be 5, and rotate will be 6.

        DesiredVals.clear();
        DesiredVals.addAll(desiredVals);

        for(int i : DesiredVals){
            switch(i){
                case 0: hardware.set(0, config.Motors[1].getPower());
                    break;
                case 1: hardware.set(1, config.Motors[2].getPower());
                    break;
                case 2: hardware.set(2, config.Motors[3].getPower());
                    break;
                case 3: hardware.set(3, config.Motors[4].getPower());
                    break;
                case 4: hardware.set(4, config.Scissor.getPower());
                    break;
                case 5: hardware.set(5, config.FoundationLeft.getPosition());
                    break;
                case 6: hardware.set(6, config.FoundationRight.getPosition());
                    break;
                case 7: hardware.set(7, config.ExtendGripper.getPower());
                    break;
                case 8: hardware.set(8, config.RotateGripper.getPosition());
                    break;
                case 9: hardware.set(9, config.Gripper.getPosition());
                    break;
            }
        }
    }

    private void runHardware(List<Double> Values, List<Integer> desiredParts) {
        //Same values for desiredParts as above's desiredVals.

        Vals.clear();
        Vals.addAll(Values);
        DesiredVals.clear();
        DesiredVals.addAll(desiredParts);

        System.out.println("ftdyui " + desiredParts);

        for(int i : DesiredVals){
            switch(i){
                case 0: config.Motors[1].setPower(Values.get(0));
                    break;
                case 1: config.Motors[2].setPower(Values.get(1));
                    break;
                case 2: config.Motors[3].setPower(Values.get(2));
                    break;
                case 3: config.Motors[4].setPower(Values.get(3));
                    break;
                case 4: config.Scissor.setPower(Values.get(4));
                    break;
                case 5: config.FoundationLeft.setPosition(Values.get(5));
                    break;
                case 6: config.FoundationRight.setPosition(Values.get(6));
                    break;
                case 7: config.ExtendGripper.setPower(Values.get(7));
                    break;
                case 8: config.RotateGripper.setPosition(Values.get(8));
                    break;
                case 9: config.Gripper.setPosition(Values.get(9));
                    break;
            }
        }
    }

    public void Stop(){
        stop = true;
    }
}
