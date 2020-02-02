package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.RRTuning;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunnerAutoConfigure;

import kotlin.Unit;
import util.AngleUtils;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class FoundationTest extends LinearOpMode {
    RoadRunnerAutoConfigure mech = new RoadRunnerAutoConfigure();
    SampleMecanumDriveBase drive;
    @Override
    public void runOpMode() throws InterruptedException {
        mech.Configure(hardwareMap);
        drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory driveToFoundation = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(-39,-15))
                .build();
        Trajectory back = drive.trajectoryBuilder()
                .splineTo(new Pose2d(34,40,Math.toRadians(90)))
                .build();
        Trajectory push = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(-30,0))
                .build();
        Trajectory strafe = drive.trajectoryBuilder()
                .strafeLeft(27.0)
                .build();
        Trajectory park = drive.trajectoryBuilder()
                .forward(45)
                .build();
        waitForStart();

        /*if (isStopRequested()) return;
        mech.FoundationLeft.setPosition(RoadRunnerAutoConfigure.LEFT_OPEN);
        mech.FoundationRight.setPosition(RoadRunnerAutoConfigure.RIGHT_OPEN);
        drive.setPoseEstimate(new Pose2d(0,0,0));
        drive.followTrajectorySync(driveToFoundation);
        mech.FoundationLeft.setPosition(RoadRunnerAutoConfigure.LEFT_CLOSE);
        mech.FoundationRight.setPosition(RoadRunnerAutoConfigure.RIGHT_CLOSE);
        sleep(500);
        drive.setPoseEstimate(new Pose2d(0,0,0));
        drive.followTrajectorySync(back);
        turnToDegree(90,0.8);
        drive.setPoseEstimate(new Pose2d(0,0,0));
        drive.followTrajectorySync(push);

         */

        sleep(3000);
        turnToDegree(0, 0.8);
    }



    public void turnToDegree(double Degree, double Power){

        double currentPos, actualPos;
        actualPos = Math.toDegrees(drive.getExternalHeading());
        System.out.println(actualPos);
        System.out.println(Degree);

        if((actualPos-Degree)>(360-(actualPos-Degree))){
            Power = Math.abs(Power);
            System.out.println("Power was positive");
        }
        else{
            Power = -1*Math.abs(Power);
            System.out.println("Power was negative");
        }

        double[] P = {0, -Power, -Power, Power, Power};

        //setPower(P);

        while((currentPos = Math.abs(Degree - (actualPos = Math.toDegrees(drive.getExternalHeading())))) - 1 >= 0 && !isStopRequested()){
            //System.out.println(originalPos);
            /*if(startPos - currentPos < 10){
                Motors[1].setPower(P[1] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[1]));
                Motors[2].setPower(P[2] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[2]));
                Motors[3].setPower(P[3] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[3]));
                Motors[4].setPower(P[4] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[4]));
            }
            */
            //if(actualPos > Degree) Power = Math.abs(Power);
            //else Power = -1 * Math.abs(Power);

            if(currentPos < 80){
                drive.setMotorPowers(P[1] * (Math.pow(currentPos / 80.0, Math.abs(Power/0.5)) + 0.1), P[2] * (Math.pow(currentPos / 80.0, Math.abs(Power/0.5)) + 0.1), P[3] * (Math.pow(currentPos / 80.0, Math.abs(Power/0.5)) + 0.1), P[4] * (Math.pow(currentPos / 80.0, Math.abs(Power/0.5)) + 0.1));
            }
            else drive.setMotorPowers(P[1], P[2], P[3], P[4]);
        }

        drive.setMotorPowers(0, 0, 0, 0);

        //drive.setPoseEstimate(new Pose2d(0,0,0));
    }
}
