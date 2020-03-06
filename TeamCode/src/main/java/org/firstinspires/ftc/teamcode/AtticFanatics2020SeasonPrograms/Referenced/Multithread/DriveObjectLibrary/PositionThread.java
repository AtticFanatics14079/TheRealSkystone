package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class PositionThread implements Runnable{

    private ArrayList<Integer> pos = new ArrayList<>(); //Arrays are all ordered according to partNum.
    private ArrayList<Double> maxSpeed = new ArrayList<>(), totalError = new ArrayList<>(), lastError = new ArrayList<>();
    private double lastTime;
    private volatile ArrayList<DriveObject> drive = new ArrayList<>();
    private ArrayList<Boolean> finishedParts = new ArrayList<>();
    private ArrayList<Double[]> PID = new ArrayList<>();
    private boolean stillGoing = true, group = false;
    private volatile boolean stop;

    public PositionThread(int[] position, double[] maxSpeed, DriveObject[] drive){
        int n = 0;
        for(DriveObject d : drive) {
            int i = d.getPartNum();
            this.drive.set(i, d);
            pos.set(i, position[n++]);
            this.maxSpeed.set(i, maxSpeed[n]);
            PID.set(i, d.getPID().clone());
            totalError.set(i, 0.0);
            lastError.set(i, 0.0);
            finishedParts.set(i, false);
        }
    }

    public PositionThread(int position, double maxSpeed, int partNum, DriveObject drive){
        int i = drive.getPartNum();
        this.drive.set(i, drive);
        pos.set(i, position);
        this.maxSpeed.set(i, maxSpeed);
        PID.set(i, drive.getPID().clone());
    }

    public PositionThread(int position, double maxSpeed, int partNum, DriveObject[] drive){
        for(DriveObject d : drive) {
            int i = d.getPartNum();
            this.drive.set(i, d);
            pos.set(i, position);
            this.maxSpeed.set(i, maxSpeed);
            PID.set(i, d.getPID().clone());
        }
    }

    public void run(){
        ElapsedTime time = new ElapsedTime();
        lastTime = 0;
        double speed;
        while(!stop && stillGoing) {
            if(time.milliseconds() - lastTime >= 5) {
                lastTime = time.milliseconds();
                stillGoing = false;
                if(group){
                    stillGoing = true;
                    speed = groupToPosition();
                    for(DriveObject d : drive) {
                        d.setClassification(DriveObject.classification.Default);
                        if(Math.abs(speed) < 0.05) {
                            stop = true;
                            d.set(0);
                        }
                        else d.set(speed);
                        d.setClassification(DriveObject.classification.toPosition);
                    }
                }
                else for(int i = 0; i < drive.length; i++){
                    if(drive[i] != null){
                        stillGoing = true;
                        speed = toPosition(i);
                        drive[i].setClassification(DriveObject.classification.Default);
                        drive[i].set(speed);
                        if(Math.abs(speed) < 0.05) { //This is using speed as an indicator to stop, may change later.
                            drive[i].set(0);
                            drive[i] = null;
                            finishedParts[i] = true;
                        }
                        drive[i].setClassification(DriveObject.classification.toPosition);
                    }
                }
            }
        }
    }

    private double toPosition(int i){
        double speed = 0, error = pos.get(i) - drive.get(i).get();
        totalError.set(i, totalError.get(i) + error);
        speed += PID.get(i)[0] * error;
        speed += PID.get(i)[1] * totalError.get(i);
        speed += PID.get(i)[2] * (error - lastError.get(i));

        if(speed > 1) speed = 1;

        speed *= maxSpeed[i];

        lastError[i] = error;

        return speed;
    }

    private double groupToPosition(){
        double speed = 0, error = pos[0] - (drive[0].get() + drive[1].get() + drive[2].get() + drive[3].get()) / 4.0;
        System.out.println(pos[0] + " error: " + error);
        System.out.println(error * PID[0][0]);
        System.out.println(PID[0][0]);
        totalError[0] += error;
        speed += PID[0][0] * error;
        System.out.println(speed);
        speed += PID[0][1] * totalError[0];
        System.out.println(speed);
        speed += PID[0][2] * (error - lastError[0]);
        System.out.println(speed);

        if(Math.abs(speed) > 1) speed /= Math.abs(speed);

        speed *= Math.abs(maxSpeed[0]);

        lastError[0] = error;

        System.out.println(speed);

        return speed;
    }

    public void stopPart(int partNum){
        if(drive.get(partNum) == null) return;
        pos.set(partNum, null);
        maxSpeed.set(partNum, null);
        totalError.set(partNum, null);
        lastError.set(partNum, null);
        finishedParts.set(partNum, null);
        PID.set(partNum, null);
        drive.set(partNum, null);
    }

    public void setPart(int targetPos, double maxSpeed, DriveObject d){

    }

    public void Stop(){
        stop = true;
    }
}
