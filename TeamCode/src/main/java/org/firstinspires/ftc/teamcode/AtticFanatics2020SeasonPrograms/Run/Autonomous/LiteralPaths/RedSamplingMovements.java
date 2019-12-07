package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.LiteralPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone.CameraDetect;

@Autonomous(name = "OnBot Blue Everything")

public class RedSamplingMovements extends LinearOpMode{
    MecanumDrive Robot = new MecanumDrive();
    CameraDetect camera = new CameraDetect();

    int offset = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.Configure(hardwareMap);
        Robot.RotateGripper.setPosition(Robot.ROTATE_GRIPPER_STRAIGHT);
        Robot.UnhookFoundation();
        int SkystonePosition = -2;
        camera.instantCamera(hardwareMap);
        CameraDevice.getInstance().setFlashTorchMode(true);
        waitForStart();
        Robot.setScissorLevel(2, false);
        Robot.Move(14);
        ElapsedTime time = new ElapsedTime();
        while((SkystonePosition = camera.skystoneDetect(time)) == -2);

        //BEGIN DIFFERENT CASES HERE

        //FOR LEFT (Position 2): First strafe is 4", for middle (Position 1): is 12", and for right (Position 0): is 20".
        double wallstrafe;
        wallstrafe = 0;
        //double volcorrect = hardwareMap.voltageSensor.get("RIGHT").getVoltage();
        //telemetry.addData("Voltage: ", volcorrect);
        /*while(SkystonePosition == -2){
            if(gamepad1.a){
                SkystonePosition = 1;
            }
            if(gamepad1.b){
                SkystonePosition = 2;
            }
            if(gamepad1.x){
                SkystonePosition = 3;
            }
        }*/

        switch(SkystonePosition)
        {
            case 1: offset = 4;
                wallstrafe = 0.8;
                break;
            case 2: offset = 12;
                wallstrafe = 1.6;
                break;
            case 3: offset = 20;
                wallstrafe = 2.2;
                break;
        }

        Robot.Move(-16);
        Robot.ExtendGripper(true, false);
        // Robot.Move(offset, -1, -0.3);
        ElapsedTime wall = new ElapsedTime();
        Robot.setPower(-1,-.5,0);
        while(wallstrafe>wall.seconds()){
        }
        Robot.setPower(0,0,0);
        Robot.Gripper.setPosition(Robot.GRIPPER_OPEN);
        Robot.Move(26);
        Robot.grabBlock(); // Scissor finishes at level 1, maybe it should finish at level 0? (need a function for it)
        Robot.Move(-10);
        Robot.Turn(85, 0.7); // all turns are supposed to be 90 rea degrees
        Robot.Move(75 + offset, 0.8, false);
        Robot.setScissorLevel(2, false);
        Robot.Turn(-80, -0.5);
        Robot.Move(28);
        Robot.HookFoundation();
        Robot.dropBlock();
        Robot.Gripper.setPosition(Robot.GRIPPER_CLOSED);
        Robot.ExtendGripper(false, true);
        sleep(400);
        Robot.setScissorLevel(0, false);
        Robot.Move(8, 1);
        Robot.Motors[1].setPower(-1);
        Robot.Motors[2].setPower(-1);
        sleep(3000);
        Robot.setPowerZero();
        Robot.Move(2, -1);
        Robot.Move(32, 0.8, false);
        Robot.UnhookFoundation();
        Robot.Move(10, -1);
        Robot.Move(-45);

    }
}
