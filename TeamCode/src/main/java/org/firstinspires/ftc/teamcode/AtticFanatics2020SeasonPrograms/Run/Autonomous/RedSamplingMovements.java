package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Autonomous(name = "Red Place Skystones Moved")

public class RedSamplingMovements extends LinearOpMode{
    MecanumDrive Robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.Configure(hardwareMap);
        Robot.RotateGripper.setPosition(Robot.ROTATE_GRIPPER_STRAIGHT);
        Robot.UnhookFoundation();
        Robot.setScissorLevel(2, true);
        waitForStart();
        Robot.Gripper.setPosition(Robot.GRIPPER_OPEN);
        Robot.Move(26, 0.7, false);
        Robot.ExtendGripper(true);
        Robot.grabBlock(); // Scissor finishes at level 1, maybe it should finish at level 0? (need a function for it)
        Robot.Move(-10, -0.6, false);
        Robot.Turn(85); // all turns are supposed to be 90 rea degrees
        Robot.Move(75);
        Robot.setScissorLevel(2, false);
        Robot.Turn(-85, 0.7);
        Robot.Move(27);
        Robot.HookFoundation();
        Robot.dropBlock();
        Robot.ExtendGripper(false);
        Robot.Gripper.setPosition(Robot.GRIPPER_CLOSED);
        sleep(550);
        Robot.setScissorLevel(0, false);
        Robot.Move(12, 1);
        Robot.Motors[1].setPower(-1);
        Robot.Motors[2].setPower(-1);
        sleep(3000);
        Robot.setPower(0, 0, 0);
        Robot.Move(2, -1);
        Robot.Move(35, 0.6, false);
        Robot.UnhookFoundation();
        Robot.Move(11.5, -1);
        Robot.Move(-45);
    }
}
