package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name = "UseOpenCV")
public class UseOpenCV extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();
    Detector camera = new Detector(runtime);

    @Override
    public void runOpMode() throws InterruptedException {
        camera.initCV(hardwareMap);
        waitForStart();
        camera.runtime.reset();
        while(!isStopRequested()){
            telemetry.addData("Values", camera.getVals()[0]+"   "+camera.getVals()[1]+"   "+camera.getVals()[2]);

            telemetry.update();
            sleep(50);

        }
    }
}
