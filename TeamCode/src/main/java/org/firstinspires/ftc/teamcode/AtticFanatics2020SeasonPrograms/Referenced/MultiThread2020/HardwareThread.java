package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.MultiThread2020;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.MultiThread2020.ControllerInput;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.MultiThread2020.ValueStorage;

import java.util.ArrayList;
import java.util.List;

public class HardwareThread extends Thread {

    ElapsedTime time = new ElapsedTime();
    Configure config = new Configure();
    private ValueStorage vals = new ValueStorage();
    List<Double> hardware = new ArrayList<>(10); //See hardwareValues in ValueStorage for each value.
    List<Integer> DesiredVals = new ArrayList<>(); //See hardwareValues in ValueStorage for each value.
    List<Double> Vals = new ArrayList<>(); //See hardwareValues in ValueStorage for each value.
    Double[] p = {1.0, 2.0, 3.0, 4.0};

    HardwareThread(ValueStorage Vals, HardwareMap hwMap){
        this.vals = Vals;
        config.Configure(hwMap);
    }

    public void run(){
        while(true) {
            try {
                System.out.println("0");
                readHardware(vals.changedParts(false, null));
                System.out.println("2");
                vals.hardware(true, hardware, time.milliseconds());
                System.out.println("3");
                runHardware(vals.runValues(false, null), vals.changedParts(false, null));
                System.out.println("5");
                //That line looks sketch but I'm pretty sure it's runtime efficient.
            } catch (Exception e) {
                System.out.println("Houston, we have a problem. (Hardware thread)");
                System.out.println(e);
            }
        }
    }

    public void startTime(){
        time.reset();
    }

    private void readHardware(List<Integer> desiredVals){
        //For sake of runtime, asking for drive motors will be 1 in desiredVals, scissor will be 2,
        //gripper will be 3, foundation hooks will be 4, extender will be 5, and rotate will be 6.

        System.out.println("1");

        hardware.clear();
        DesiredVals.clear();
        DesiredVals.addAll(desiredVals);

        for(int i : DesiredVals){

            System.out.println("1.5");

            switch(i){
                case 1:
                    System.out.println("1.6");
                    hardware.add(0, config.Motors[1].getPower());
                    System.out.println("1.7");
                    hardware.add(1, config.Motors[2].getPower());
                    System.out.println("1.8");
                    hardware.add(2, config.Motors[3].getPower());
                    System.out.println("1.85");
                    hardware.add(3, config.Motors[4].getPower());
                    System.out.println("1.9");
                    break;
                case 2: hardware.add(4, config.Scissor.getPower());
                break;
                case 3: hardware.add(9, config.Gripper.getPosition());
                break;
                case 4:
                    hardware.add(5, config.FoundationLeft.getPosition());
                    hardware.add(6, config.FoundationRight.getPosition());
                    break;
                case 5: hardware.add(7, config.ExtendGripper.getPower());
                break;
                case 6: hardware.add(8, config.RotateGripper.getPosition());
                break;
                default: System.out.println("Default");
            }
        }

        System.out.println("1.95");
    }

    private void runHardware(List<Double> Values, List<Integer> desiredParts) {
        //Same values for desiredParts as above's desiredVals.

        Vals.clear();
        Vals.addAll(Values);
        DesiredVals.clear();
        DesiredVals.addAll(desiredParts);

        System.out.println("4");

        for (int i : DesiredVals) {

            System.out.println("4.5");

            switch (i) {
                case 1:
                    System.out.println("4.6, M[1] = " + config.Motors[1] + ", DesiredVals = " + DesiredVals);
                    config.Motors[1].setPower(Vals.get(0));
                    System.out.println("4.7, M[2] = " + config.Motors[2] + ", Values.get(1) = " + Vals.get(1));
                    config.Motors[2].setPower(Vals.get(1));
                    config.Motors[3].setPower(Vals.get(2));
                    config.Motors[4].setPower(Vals.get(3));
                    break;
                case 2: config.Scissor.setPower(Vals.get(4));
                    break;
                case 3: config.Gripper.setPosition(Vals.get(9));
                    break;
                case 4:
                    config.FoundationLeft.setPosition(Vals.get(5));
                    config.FoundationRight.setPosition(Vals.get(6));
                    break;
                case 5:  config.ExtendGripper.setPower(Vals.get(7));
                    break;
                case 6:  config.RotateGripper.setPosition(Vals.get(8));
                    break;
            }
        }

        System.out.println("4.9");
    }
}
