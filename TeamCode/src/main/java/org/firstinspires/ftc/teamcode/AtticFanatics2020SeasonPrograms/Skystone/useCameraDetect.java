package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
@TeleOp(name="CameraDetect", group ="Concept")
public class useCameraDetect extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        CameraDetect camera = new CameraDetect();
        waitForStart();
        int finalposition;
        finalposition = camera.getSkystone(hardwareMap);

        switch (finalposition){
            case 0: System.out.println("Path 0"); break;
            case 1: System.out.println("Path 1"); break;
            case 2: System.out.println("Path 2"); break;
        }
    }
}
