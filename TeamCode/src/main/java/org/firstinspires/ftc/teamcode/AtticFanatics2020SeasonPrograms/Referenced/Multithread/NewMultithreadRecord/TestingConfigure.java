package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TestingConfigure extends LinearOpMode {

    Configuration config = new Configuration(hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {
        //If this works I am a legend.
        //config.hardware.get("ingester").set(0.5);
        while(!isStopRequested()){}
    }
}
