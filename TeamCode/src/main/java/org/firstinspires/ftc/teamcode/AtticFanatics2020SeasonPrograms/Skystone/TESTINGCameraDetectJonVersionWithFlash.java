package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

@Autonomous(name = "TESTING Jon Camera Detect")
public class TESTINGCameraDetectJonVersionWithFlash extends LinearOpMode {

    CameraDetect camera = new CameraDetect();
    int SkystonePosition = -2;
    ElapsedTime time;

    @Override
    public void runOpMode() throws InterruptedException {
        camera.instantCamera(hardwareMap);
        CameraDevice.getInstance().setFlashTorchMode(true);

        waitForStart();
        time = new ElapsedTime();
        while((SkystonePosition = camera.skystoneDetect(time)) == -2);
        System.out.println(SkystonePosition);
        telemetry.addData("SkystonePosition: ", SkystonePosition);
        telemetry.addData("Position: ", camera.positionSkystone);
        telemetry.addData("xCord: ", camera.xPosition);
        while(!isStopRequested())
            telemetry.update();
    }
}
