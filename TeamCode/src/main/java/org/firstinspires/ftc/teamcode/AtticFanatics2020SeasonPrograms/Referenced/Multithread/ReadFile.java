package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Read Test File")
public class ReadFile extends LinearOpMode {

    ValueStorage vals = new ValueStorage();
    public final String fileName = "Test.txt";

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareThread hardware = new HardwareThread(vals, hardwareMap);
        hardware.start();
        ReadingThread read = new ReadingThread(vals, fileName);
        read.start();
        waitForStart();
        hardware.startTime();
        read.startTime();
        while(!isStopRequested() && read.isAlive()){}
        hardware.Stop();
        read.Stop();
        ElapsedTime time = new ElapsedTime();
        while((read.isAlive() || hardware.isAlive()) && time.seconds() < 1){}
        hardware.stop();
        read.stop();
    }
}
