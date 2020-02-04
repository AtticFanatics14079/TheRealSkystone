package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.RROpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveREV;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class LGang extends LinearOpMode {
    //NoDriveConfigure mech = new NoDriveConfigure();
    SampleMecanumDriveBase drive;
    @Override
    public void runOpMode() throws InterruptedException {
        //mech.Configure(hardwareMap);
        drive = new SampleMecanumDriveREV(hardwareMap);
        Trajectory onward = drive.trajectoryBuilder()
                .forward(84)
                .build();
        Trajectory onward2 = drive.trajectoryBuilder()
                .forward(42)
                .build();
        waitForStart();
        if (isStopRequested()) return;
        drive.setPoseEstimate(new Pose2d(0,0,0));
        sleep(500);
        drive.followTrajectorySync(onward);
        turnToDegree(90, 0.6);
        drive.setPoseEstimate(new Pose2d(0,0,0));
        drive.followTrajectorySync(onward2);
    }

    public void turnToDegree(double Degree, double Power){

        double[] P = {0, -Power, -Power, Power, Power};

        double currentPos, startPos = Math.abs(drive.getExternalHeading() - Degree); //target = imuAccount(originalPos + Degrees);

        //setPower(P);

        while((currentPos = Math.abs(Degree - Math.toDegrees(drive.getExternalHeading()))) - 0.5 >= 0){
            //System.out.println(originalPos);
            /*if(startPos - currentPos < 10){
                Motors[1].setPower(P[1] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[1]));
                Motors[2].setPower(P[2] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[2]));
                Motors[3].setPower(P[3] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[3]));
                Motors[4].setPower(P[4] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[4]));
            }
            */
            if(currentPos < 90){
                drive.setMotorPowers(P[1] * (Math.pow(currentPos / 92.0, Math.abs(Power/0.4)) + 0.05), P[2] * (Math.pow(currentPos / 92.0, Math.abs(Power/0.5)) + 0.04), P[3] * (Math.pow(currentPos / 92.0, Math.abs(Power/0.4)) + 0.05), P[4] * (Math.pow(currentPos / 92.0, Math.abs(Power/0.4)) + 0.05));
            }
            else drive.setMotorPowers(P[1], P[2], P[3], P[4]);
        }

        drive.setMotorPowers(0, 0, 0, 0);
    }
}
