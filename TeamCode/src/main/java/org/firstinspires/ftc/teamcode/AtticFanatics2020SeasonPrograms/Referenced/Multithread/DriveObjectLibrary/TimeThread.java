package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import com.qualcomm.robotcore.util.ElapsedTime;

public class TimeThread extends Thread{

    private double seconds, value, endVal;
    private DriveObject drive;

    public TimeThread(double Seconds, double value, DriveObject drive){
        seconds = Seconds;
        this.value = value;
        this.drive = drive;
        endVal = 0;
    }

    public TimeThread(double Seconds, double value, double endVal, DriveObject drive){
        seconds = Seconds;
        this.value = value;
        this.drive = drive;
        this.endVal = endVal;
    }

    public void run() {
        switch(drive.getType()){
            case DcMotorImplEx:
                drive.set(value);
                break;
            case CRServo:
                drive.set(value);
                break;
            case Servo:
                drive.set(value);
            default:
                System.out.println("Invalid type for setting power.");
                return;
        }
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        drive.set(value);
    }
}
