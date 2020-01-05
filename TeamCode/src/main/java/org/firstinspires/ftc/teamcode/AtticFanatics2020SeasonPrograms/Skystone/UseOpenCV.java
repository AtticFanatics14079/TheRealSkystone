package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class UseOpenCV extends OpMode {

    OpenCVDetectorClass camera = new OpenCVDetectorClass();


    @Override
    public void init() {
        camera.camSetup(hardwareMap);
        //other hardware configs
    }

    @Override
    public void init_loop() {
        camera.getVals();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        //stop the camera
        //our movements

    }

    @Override
    public void loop() {

    }
}
