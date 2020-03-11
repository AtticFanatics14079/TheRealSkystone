package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.RRTests;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveREV;

//THIS SHOULD MAKE THE ROBOT GO EXACTLY 6 FEET FORWARD

@Config
@Autonomous(group = "tuning")
public class StraightTest extends LinearOpMode {
    private static double distance = 60;
    ;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory trajectory = drive.trajectoryBuilder()
                .forward(distance)
                .build();

        waitForStart();

        drive.followTrajectorySync(trajectory);
        while(!isStopRequested()){
            telemetry.addLine("Waiting for stop");
        }
    }
}
