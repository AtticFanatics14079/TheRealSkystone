package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone.CameraDetect;

@Autonomous(name = "Hit Block")
public class HitBlock extends LinearOpMode {

    //STARTING POSITION HAS PHONE 20 CM AWAY FROM THE DEPOT (HORIZONTALLY) AND DRIVES TO BE 45 CM AWAY FROM THE BLOCKS

    public void runOpMode() throws InterruptedException {
        CameraDetect camera = new CameraDetect();
        MecanumDrive robot = new MecanumDrive();
        ElapsedTime time;

        robot.Configure(hardwareMap);
        int sposition = -2;
        camera.instantCamera(hardwareMap);
        CameraDevice.getInstance().setFlashTorchMode(true);
        waitForStart();

        robot.MoveEncoderTicks(75,1, hardwareMap);

        time = new ElapsedTime();

        while((sposition = camera.skystoneDetect(time)) == -2);

        switch(sposition){
            case 2: robot.StrafeEncoderTicks(-30, -1, hardwareMap);
            telemetry.addLine("left"); break;
            case 1: robot.StrafeEncoderTicks(15,1,hardwareMap);
            telemetry.addLine("mid"); break;
            case 0: robot.StrafeEncoderTicks(16,1,hardwareMap);
                 telemetry.addLine("right");
            break;
        }
       // robot.MoveEncoderTicks(45,1,hardwareMap);


    }
}
