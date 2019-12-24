package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.RRTuning;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//THIS SHOULD MAKE THE ROBOT GO EXACTLY 6 FEET FORWARD

@Config
@Autonomous(group = "tuning")
public class StraightTest extends LinearOpMode {
    private static double distance = 72;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory trajectory = drive.trajectoryBuilder()
                .forward(distance)
                .build();

        waitForStart();

        drive.followTrajectorySync(trajectory);
    }
}
