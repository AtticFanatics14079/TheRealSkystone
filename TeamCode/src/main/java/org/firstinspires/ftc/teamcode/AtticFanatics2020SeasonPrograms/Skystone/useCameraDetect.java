package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class useCameraDetect extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        CameraDetect camera = new CameraDetect();
        waitForStart();
        int finalposition;
        finalposition = camera.getSkystone(hardwareMap);
    }
}
