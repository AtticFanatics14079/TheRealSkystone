package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous (name = "UseOpenCV")
public class UseOpenCV extends LinearOpMode {

    OpenCVDetectorClass camera = new OpenCVDetectorClass();

    @Override
    public void runOpMode() throws InterruptedException {
        camera.camSetup(hardwareMap);
        waitForStart();
        while(!isStopRequested()){
            telemetry.addData("Values", camera.getVals()[1]+"   "+camera.getVals()[0]+"   "+camera.getVals()[2]);
            telemetry.addData("Height", camera.rows);
            telemetry.addData("Width", camera.cols);

            telemetry.update();
            sleep(100);
        }
    }
}
