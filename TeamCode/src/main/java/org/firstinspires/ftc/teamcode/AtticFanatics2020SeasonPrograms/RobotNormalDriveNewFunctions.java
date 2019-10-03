package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;

public class RobotNormalDriveNewFunctions extends ConfigureRobot {

    BNO055IMU imu;

    DcMotor[] Motors;

    //Orientation angles;
    Acceleration gravity;

    boolean Configured;

    public void runOpMode() throws InterruptedException {
    }

    public void MoveEncoderTicks(double NumbCM) {

        telemetry.addLine("(2) About to check for configured");
        if (!Configured) {
            telemetry.addLine("(3) About to configure");
            Configure(Motors);
            Configured = true;
        }

        telemetry.addLine("(6) Configured, resetting motor encoders");
        ResetMotorEncoders(Motors[1], Motors[2], Motors[3], Motors[4]);

        double HeadingAdjust = angles.firstAngle;

        double TurnAmount;

        //Mess with numbers, as different circumference.
        double Ticks = 36.1275 * NumbCM;

        telemetry.addLine("(8) About to run");
        if (NumbCM > 0) {
            Motors[1].setPower(1);
            Motors[2].setPower(1);
            Motors[3].setPower(1);
            Motors[4].setPower(1);
        } else {
            Motors[1].setPower(-1);
            Motors[2].setPower(-1);
            Motors[3].setPower(-1);
            Motors[4].setPower(-1);
        }
        //This is the experimental part. What it should do is check if a motor has gone the intended distance, then stop. After this, if 1+ motor(s) still needs to move farther, the stopped motor will move only enough to keep the robot straight until all motors are at the right position.
        while (Motors[1].isBusy() || Motors[2].isBusy() || Motors[3].isBusy() || Motors[4].isBusy()) {
            for (int Counter = 1; Counter <= 4; Counter++) {
                if (Motors[Counter].getCurrentPosition() >= Ticks)
                    Motors[Counter].setPower(0);
            }
        }
    }

    public void TurnEncoderTicks(double Degrees) //TURNING RIGHT IS POSITIVE!!! Also this is NOT using IMU, test both to compare.
    {
        if (!Configured)
        {
            Configure(Motors);
            Configured = true;
        }

        double Ticks = Degrees * 19.8;

        if (Degrees < 0)
        {
            Motors[1].setPower(-1);
            Motors[2].setPower(1);
            Motors[3].setPower(-1);
            Motors[4].setPower(1);

            while (Motors[1].isBusy() || Motors[2].isBusy() || Motors[3].isBusy() || Motors[4].isBusy())
            {
                for(int Counter = 1; Counter <= 4; Counter++)
                {
                    Ticks = -Ticks;
                    if (Motors[Counter].getCurrentPosition() >= Ticks)
                        Motors[Counter].setPower(0);
                }
            }
        }
        else {

            Motors[1].setPower(1);
            Motors[2].setPower(-1);
            Motors[3].setPower(1);
            Motors[4].setPower(-1);

            while (Motors[1].isBusy() || Motors[2].isBusy() || Motors[3].isBusy() || Motors[4].isBusy())
            {
                for(int Counter = 1; Counter <= 4; Counter++)
                {
                    if (Motors[Counter].getCurrentPosition() >= Ticks)
                        Motors[Counter].setPower(0);
                    Ticks = -Ticks;
                }
            }
        }
    }
}
