package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.LiteralPaths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp1Configure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone.CameraDetect;


@Autonomous(name = "NoStrafeEverything")
@Disabled
public class NoStrafeEverything extends LinearOpMode{
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
        Robot.Gripper.setPosition(Comp1Configure.GRIPPER_OPEN);
        Robot.Move(14);
        ElapsedTime time = new ElapsedTime();
        while((SkystonePosition = camera.skystoneDetect(time)) == -2);
        telemetry.addData("", SkystonePosition);
        telemetry.update();

        //BEGIN DIFFERENT CASES HERE

        //FOR LEFT (Position 2): First strafe is 4", for middle (Position 1): is 12", and for right (Position 0): is 20".

        switch(SkystonePosition)
        {
            case 1: offset = 4;
            break;
            case 2: offset = 12;
            break;
            case 3: offset = 20;
            break;
        }

        Robot.ExtendGripper(true, false);
        Robot.Move(offset, -0.6);
        Robot.Move(12);
        Robot.grabBlock(); // Scissor finishes at level 1, maybe it should finish at level 0? (need a function for it)
        Robot.Move(-10);
        Robot.Turn(85, 0.7); // all turns are supposed to be 90 rea degrees
        Robot.Move(75 + offset);
        Robot.setScissorLevel(2, false);
        Robot.Turn(-85, -0.7);
        Robot.Move(15);
        Robot.HookFoundation();
        Robot.dropBlock();
        Robot.ExtendGripper(false, true);
        Robot.Gripper.setPosition(Comp1Configure.GRIPPER_CLOSED);
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
