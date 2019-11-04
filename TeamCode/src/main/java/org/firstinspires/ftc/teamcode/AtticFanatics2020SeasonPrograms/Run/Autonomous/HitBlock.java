package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import android.graphics.Camera;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone.CameraDetect;

@Autonomous(name = "Hit Block")
public class HitBlock extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        CameraDetect camera = new CameraDetect();
        MecanumDrive robot = new MecanumDrive();

        int sposition;
        waitForStart();
        robot.MoveEncoderTicks(79,1, hardwareMap);
        sposition = camera.getSkystone(hardwareMap);
        telemetry.addData("xPosition: ", camera.getxPosition());
        switch(sposition){
            case 0: robot.StrafeEncoderTicks(30, 1, hardwareMap);
            telemetry.addLine("left"); break;
            case 1: robot.StrafeEncoderTicks(15,1,hardwareMap);
            telemetry.addLine("mid"); break;
            case 2: robot.StrafeEncoderTicks(45,1,hardwareMap); telemetry.addLine("right");
            break;
        }
        robot.MoveEncoderTicks(45,1,hardwareMap);


    }
}
