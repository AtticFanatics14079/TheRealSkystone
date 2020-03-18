package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous(name = "Test")
public class Test extends LinearOpMode {

    ValueStorage vals = new ValueStorage();

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareThread hardware = new HardwareThread(vals, hardwareMap);
        hardware.start();
        for(DriveObject d : hardware.config.hardware) {
            d.setClassification(DriveObject.classification.toPosition);
            d.setPID(0.003, 0.005, 1);
        }
        System.out.println(hardware.config.hardware.get(0).getPID()[0] + " hf");
        hardware.startTime(new ElapsedTime());
        Sequence s = new Sequence(() -> hardware.config.hardware.get(0).groupSetTargetPosition(3000, 1, hardware.config.hardware), null);
        Sequence s2 = new Sequence(() -> hardware.config.hardware.get(0).groupSetTargetPosition(0, 1, hardware.config.hardware), s);
        Thread t = new Thread(s2);
        t.start();
        while (!isStopRequested()){}
        hardware.Stop();
    }
}
