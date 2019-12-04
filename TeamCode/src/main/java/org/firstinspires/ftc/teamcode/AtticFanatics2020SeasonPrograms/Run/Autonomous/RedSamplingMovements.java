package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Autonomous

public class RedSamplingMovements extends LinearOpMode{
    MecanumDrive Robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.Configure(hardwareMap);
        waitForStart();
        Robot.Move(26);
        Robot.setScissorLevel(1, false);
        Robot.ExtendGripper(true);
        Robot.grabBlock(0); // Scissor finishes at level 1, maybe it should finish at level 0? (need a function for it)
        Robot.Move(-10);
        Robot.Turn(90); // all turns are supposed to be 90 rea degrees
        Robot.Move(40);
        Robot.setScissorLevel(1, true);
        Robot.Move(20);
        Robot.dropBlock();
        Robot.Move(-10);
        Robot.setScissorLevel(0, false);
        // FIRST SKYSTONE DELIVERED
        Robot.Move(-76);
        Robot.Turn(-90);
        Robot.setScissorLevel(1, true);
        Robot.Move(13);
        Robot.grabBlock(0);
        Robot.Move(-13);
        Robot.Turn(90);
        Robot.Move(74);
        Robot.dropBlock();
        Robot.Move(-5);
        Robot.ExtendGripper(false);
        Robot.setScissorLevel(0, false);
        Robot.Move(20,1);
        Robot.Move(-20);
    }
}
