package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import android.util.Pair;

import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class PositionThread extends Thread {

    private ArrayList<Integer> pos = new ArrayList<>(); //Arrays are all ordered according to partNum.
    private ArrayList<Double> maxSpeed = new ArrayList<>(), totalError = new ArrayList<>(), lastError = new ArrayList<>();
    private double lastTime;
    private volatile ArrayList<Pair<DriveObject, DriveObject>> drive = new ArrayList<>();
    private volatile ArrayList<Double[]> PID = new ArrayList<>();
    private boolean stillGoing = true, group = false;
    private volatile boolean stop;

    public PositionThread(int position, double maxSpeed, DriveObject drive){
        int i = drive.getPartNum();
        this.drive.set(i, new Pair(drive, drive));
        pos.set(i, position);
        this.maxSpeed.set(i, Math.abs(maxSpeed));
        PID.set(i, drive.getPID().clone());
    }

    public PositionThread(int position, double maxSpeed, DriveObject[] drive){
        int i = 0;
        group = true;
        for(DriveObject d : drive) {
            this.drive.set(i, new Pair(drive, drive));
            pos.set(i, position);
            this.maxSpeed.set(i, Math.abs(maxSpeed));
            PID.set(i, d.getPID().clone());
            i++;
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
                    for(Pair <DriveObject, DriveObject> d : drive) {
                        if(Math.abs(speed) < 0.05) {
                            stop = true;
                            d.first.set(0);
                        }
                        else d.first.setPower(speed);
                    }
                }
                else if(drive.get(0) != null){
                    stillGoing = true;
                    speed = toPosition();
                    drive.get(0).first.setPower(speed);
                    if(Math.abs(speed) < 0.05) { //This is using speed as an indicator to stop, may change later.
                        drive.get(0).first.set(0);
                        drive.set(0, null);
                    }
                }
            }
        }
    }

    private double toPosition(){
        double speed = 0, error = pos.get(0) - drive.get(0).second.get();
        totalError.set(0, totalError.get(0) + error);
        speed += PID.get(0)[0] * error;
        speed += PID.get(0)[1] * totalError.get(0);
        speed += PID.get(0)[2] * (error - lastError.get(0));

        if(speed > 1) speed = 1;

        speed *= maxSpeed.get(0);

        lastError.set(0, error);

        return speed;
    }

    private double groupToPosition(){
        double speed = 0, error = pos.get(0);
        for(Pair<DriveObject, DriveObject> n : drive) error -= n.second.get()/4.0;
        totalError.set(0, totalError.get(0) + error);
        speed += PID.get(0)[0] * error;
        speed += PID.get(0)[1] * totalError.get(0);
        speed += PID.get(0)[2] * (error - lastError.get(0));

        if(Math.abs(speed) > 1) speed /= Math.abs(speed);

        speed *= Math.abs(maxSpeed.get(0));

        lastError.set(0, error);

        return speed;
    }

    public void stopPart(int partNum){
        if(drive.get(partNum) == null) return;
        pos.set(partNum, null);
        maxSpeed.set(partNum, null);
        totalError.set(partNum, null);
        lastError.set(partNum, null);
        PID.set(partNum, null);
        drive.set(partNum, null);
    }

    public void Stop(){
        stop = true;
    }
}
