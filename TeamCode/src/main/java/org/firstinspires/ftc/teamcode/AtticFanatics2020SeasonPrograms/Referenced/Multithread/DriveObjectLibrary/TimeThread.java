package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import com.qualcomm.robotcore.util.ElapsedTime;

public class TimeThread implements Runnable{

    private double seconds, value;
    private DriveObject drive;

    public TimeThread(double Seconds, double value, DriveObject drive){
        seconds = Seconds;
        this.value = value;
        this.drive = drive;
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
        ElapsedTime time = new ElapsedTime();
        while(time.seconds() < seconds) {}
        drive.set(value);
    }
}
