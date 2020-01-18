package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.OldPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp1Configure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone.CameraDetect;

@Autonomous(name = "Red Side Everything")

public class RedSideUsEverything extends LinearOpMode{
    MecanumDrive Robot = new MecanumDrive();
    CameraDetect camera = new CameraDetect();

    int offset = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.Configure(hardwareMap);
        Robot.RotateGripper.setPosition(Comp1Configure.ROTATE_GRIPPER_STRAIGHT);
        Robot.UnhookFoundation();
        int SkystonePosition = -2;
        camera.instantCamera(hardwareMap);
        CameraDevice.getInstance().setFlashTorchMode(true);
        waitForStart();
        Robot.setScissorLevel(2, false);
        Robot.Move(14);
        ElapsedTime time = new ElapsedTime();
        while((SkystonePosition = camera.skystoneDetect(time)) == -2);
        //camera.deactivate();

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
                wallstrafe = 1.3;
                break;
            case 2: offset = 12;
                wallstrafe = 1.7;
                break;
            case 3: offset = 16;
                wallstrafe = 2.4;
                break;
        }

        Robot.Move(-16);
        Robot.ExtendGripper(true, false);
        Robot.Move(offset * 4, 1, -0.5);
        /*ElapsedTime wall = new ElapsedTime();
        Robot.setPower(-1,-.5,0);
        while(wallstrafe>wall.seconds()){
        }
        */
        Robot.Gripper.setPosition(Comp1Configure.GRIPPER_OPEN);
        Robot.Move(26);
        Robot.grabBlock(); // Scissor finishes at level 1, maybe it should finish at level 0? (need a function for it)
        Robot.Move(-10);
        Robot.Turn(-82, -0.7); // all turns are supposed to be 90 rea degrees
        Robot.Move(76 + offset, 0.8, false);
        Robot.setScissorLevel(2, false);
        Robot.Turn(82, 0.7);
        Robot.Move(30);
        Robot.Motors[1].setPower(1);
        Robot.Motors[2].setPower(1);
        sleep(100);
        Robot.setPower(0, 0, 0);
        Robot.HookFoundation();
        Robot.dropBlock();
        Robot.setScissorLevel(3, false);
        Robot.Gripper.setPosition(Comp1Configure.GRIPPER_CLOSED);
        Robot.ExtendGripper(false, true);
        Robot.setScissorLevel(0, false);
        Robot.Move(-8, -1);
        Robot.Motors[3].setPower(-1);
        Robot.Motors[4].setPower(-1);
        sleep(3000);
        Robot.setPower(0, 0, 0);
        Robot.Move(2, 1);
        Robot.Move(32, 0.8, false);
        Robot.UnhookFoundation();
        //Robot.Move(10, -1); //Not enough time
        //Robot.Move(-45);

    }
}
