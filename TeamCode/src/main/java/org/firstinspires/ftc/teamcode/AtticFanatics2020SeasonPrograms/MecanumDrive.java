package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class MecanumDrive extends ConfigureRobot {

    DcMotor[] Motors = new DcMotor[5];

    Acceleration gravity;
    Orientation orientation;

    boolean Configured = false;

    ConfigureRobot Config = new ConfigureRobot();

    public void runOpMode() throws InterruptedException {
    }

    public void MoveEncoderTicks(double NumbCM, double SidewaysPower, double ForwardPower) {

        if (!Configured)
        {
            Motors = Config.Configure(Motors);
            Configured = true;
        }

        Config.ResetMotorEncoders(Motors[1], Motors[2], Motors[3], Motors[4]);

        //Mess with numbers, as different circumference.
        double Ticks = 36.1275 * NumbCM;

        setPower(SidewaysPower, ForwardPower, 0f);

        //No IMU implementation yet
        //This is assuming every movement has opposite sides (1+3, 2+4) moving equal and no skidding (so prob wont work)
        while((Motors[1].isBusy() || Motors[3].isBusy()) & (Motors[2].isBusy() || Motors[4].isBusy()))
        {
            for(int Counter = 1; Counter <= 4; ++Counter)
            {
                if(Math.abs(Motors[Counter].getCurrentPosition()) >= Math.abs(Ticks))
                    Motors[Counter].setPower(0);
            }
        }
    }

    public void TurnDegrees(int Degrees)
    {
        //TURNING LEFT IS POSITIVE!!!

        if (!Configured)
        {
            Motors = Config.Configure(Motors);
            Configured = true;
        }

        Config.ResetMotorEncoders(Motors[1], Motors[2], Motors[3], Motors[4]);

        //Mess with numbers if different circumference.
        double Ticks = Degrees * 19.8;

        setPower(0, 0, Degrees);

        while((Motors[1].isBusy() || Motors[2].isBusy()) & (Motors[3].isBusy() || Motors[4].isBusy()))
        {
            for(int Counter = 1; Counter <= 4; ++Counter)
            {
                if(Math.abs(Motors[Counter].getCurrentPosition()) >= Math.abs(Ticks))
                    Motors[Counter].setPower(0);
            }
        }
    }

    //The following method is code from Team 16072's virtual_robot program. Small changes are only to make it fit our format, the bulk of the method was written by them.
    void setPower(double px, double py, double pa){
        double p1 = -px + py - pa;
        double p2 = px + py + -pa;
        double p3 = -px + py + pa;
        double p4 = px + py + pa;
        double max = Math.max(1.0, Math.abs(p1));
        max = Math.max(max, Math.abs(p2));
        max = Math.max(max, Math.abs(p3));
        max = Math.max(max, Math.abs(p4));
        p1 /= max;
        p2 /= max;
        p3 /= max;
        p4 /= max;
        Motors[1].setPower(p1);
        Motors[2].setPower(p2);
        Motors[3].setPower(p3);
        Motors[4].setPower(p4);
    }
}
