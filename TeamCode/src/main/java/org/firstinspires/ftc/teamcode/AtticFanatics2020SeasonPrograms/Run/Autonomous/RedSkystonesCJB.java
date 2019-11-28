package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

public class RedSkystonesCJB extends LinearOpMode {
    MecanumDrive Robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.Configure(hardwareMap);
        Robot.Move(10);
        Robot.ExtendGripper(true);
        Robot.grabBlock(); // Scissor finishes at level 1, maybe it should finish at level 0?
        Robot.MoveScissor(0); // Clear the skybridge
        Robot.Move(-10);
        Robot.Turn(-90);
        Robot.Move(50);
        Robot.dropBlock();
        // FIRST SKYSTONE DELIVERED
        Robot.Move(-74);
        Robot.Turn(90);
        Robot.Move(10);
        Robot.MoveScissor(1);
        Robot.grabBlock();
        Robot.MoveScissor(0); // Clear the skybridge
        Robot.Move(-10);
        Robot.Turn(-90);
        Robot.Move(74);
        Robot.dropBlock();
        Robot.Move(-15);

    }
}
