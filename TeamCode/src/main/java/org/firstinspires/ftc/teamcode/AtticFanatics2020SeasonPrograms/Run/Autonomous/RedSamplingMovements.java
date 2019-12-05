package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone.CameraDetect;


@Autonomous(name = "Blue Everything")

public class RedSamplingMovements extends LinearOpMode{
    MecanumDrive Robot = new MecanumDrive();
    CameraDetect camera = new CameraDetect();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.Configure(hardwareMap);
        Robot.RotateGripper.setPosition(Robot.ROTATE_GRIPPER_STRAIGHT);
        Robot.UnhookFoundation();
        Robot.setScissorLevel(2, true);
        int SkystonePosition = -2;
        camera.instantCamera(hardwareMap);
        CameraDevice.getInstance().setFlashTorchMode(true);
        waitForStart();
        Robot.Gripper.setPosition(Robot.GRIPPER_OPEN);
        Robot.Move(14);
        ElapsedTime time = new ElapsedTime();
        while((SkystonePosition = camera.skystoneDetect(time)) == -2);
        telemetry.addData("", SkystonePosition);
        telemetry.update();

        //BEGIN DIFFERENT CASES HERE

        //FOR LEFT (Position 2): First strafe is 4", for middle (Position 1): is 12", and for right (Position 0): is 20".

        //Robot.Move(12);
        /*Robot.ExtendGripper(true);
        Robot.grabBlock(); // Scissor finishes at level 1, maybe it should finish at level 0? (need a function for it)
        Robot.Move(-10);
        Robot.Turn(85, 0.6); // all turns are supposed to be 90 rea degrees
        Robot.Move(75);
        Robot.setScissorLevel(2, false);
        Robot.Turn(-85, -0.6);
        Robot.Move(15);
        Robot.HookFoundation();
        Robot.dropBlock();
        Robot.ExtendGripper(false);
        Robot.Gripper.setPosition(Robot.GRIPPER_CLOSED);
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
        */
        Robot.Gripper.setPosition(Robot.GRIPPER_CLOSED);
        sleep(400);
        Robot.setScissorLevel(0, true);
    }
}
