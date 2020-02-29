package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PositionThread implements Runnable{

    private int[] pos;
    private double[] maxSpeed, totalError, lastError;
    private double lastTime;
    private volatile DriveObject[] drive;
    private boolean[] finishedParts;
    private double[][] pid;
    private boolean stillGoing = true, group = false;
    private volatile boolean stop;

    public PositionThread(int[] position, double[] maxSpeed, DriveObject[] drive){
        pos = position;
        this.maxSpeed = maxSpeed;
        finishedParts = new boolean[drive.length];
        lastError = totalError = new double[drive.length];
        pid = new double[drive.length][3];
        for(int i = 0; i < drive.length; i++) {
            pid[i] = drive[i].getPID();
            pid[i][0] /= 10000;
        }
        this.drive = drive;
    }

    public PositionThread(int position, double maxSpeed, DriveObject drive){
        pos = new int[]{position};
        this.maxSpeed = new double[]{maxSpeed};
        this.drive = new DriveObject[]{drive};
        finishedParts = new boolean[1];
        pid = new double[1][3];
        pid[0] = drive.getPID();
        pid[0][0] /= 10000;
        lastError = totalError = new double[1];
    }

    public PositionThread(int position, double maxSpeed, DriveObject[] drive){
        pos = new int[]{position};
        this.maxSpeed = new double[]{maxSpeed};
        this.drive = drive;
        finishedParts = new boolean[1];
        pid = new double[1][3];
        pid[0] = drive[0].getPID();
        pid[0][0] /= 10000;
        lastError = totalError = new double[1];
        group = true;
    }

    public void run(){
        ElapsedTime time = new ElapsedTime();
        lastTime = 0;
        double speed;
        while(!stop && stillGoing) {
            if(time.milliseconds() - lastTime >= 1) {
                System.out.println("Hello");
                lastTime = time.milliseconds();
                stillGoing = false;
                if(group){
                    stillGoing = true;
                    speed = groupToPosition();
                    System.out.println(speed);
                    for(DriveObject d : drive) {
                        d.setClassification(DriveObject.classification.Default);
                        if(speed < 0.05) {
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
                        if(speed < 0.05) { //This is using speed as an indicator to stop, may change later.
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
        double speed = 0, error = pos[i] - drive[i].get();
        totalError[i] += error;
        speed += pid[i][0] * error;
        speed += pid[i][1] * totalError[i];
        speed += pid[i][2] * (error - lastError[i]);

        if(speed > 1) speed = 1;

        speed *= maxSpeed[i];

        lastError[i] = error;

        return speed;
    }

    private double groupToPosition(){
        double speed = 0, error = pos[0] - (drive[0].get() + drive[1].get() + drive[2].get() + drive[3].get()) / 4.0;
        totalError[0] += error;
        speed += pid[0][0] * error;
        speed += pid[0][1] * totalError[0];
        speed += pid[0][2] * (error - lastError[0]);

        if(speed > 1) speed = 1;

        speed *= maxSpeed[0];

        lastError[0] = error;

        return speed;
    }

    public void stopPart(int partNum){
        for(int i = 0; i < drive.length; i++){
            if(drive[i].getPartNum() == partNum){
                drive[i] = null;
                return;
            }
        }
    }

    public void Stop(){
        stop = true;
    }
}
