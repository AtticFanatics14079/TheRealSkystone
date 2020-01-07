package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name = "UseOpenCV")
public class UseOpenCV extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();
    OpenCVDetectorClass camera = new OpenCVDetectorClass(runtime,hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {
        camera.initCV();
        waitForStart();
        runtime.reset();
        while(!isStopRequested()){
            telemetry.addData("Values", camera.getVals()[1]+"   "+camera.getVals()[0]+"   "+camera.getVals()[2]);

            telemetry.update();
            sleep(50);
        }
    }
}
