package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class MecanumDrive extends Configure {

    Acceleration gravity;
    Orientation HeadingAdjust, CurrentOrientation;

    boolean Configured = false;

    public void runOpMode() throws InterruptedException {
    }

    public void MoveEncoderTicks(double NumbCM, double SidewaysPower, double ForwardPower, HardwareMap ahwMap) {

        if (!Configured)
        {
            Configure(ahwMap);
            Configured = true;
        }

        ResetMotorEncoders(ahwMap);

        //Mess with numbers, as different circumference.
        double Ticks = 36.1275 * NumbCM;

        HeadingAdjust = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        setPower(SidewaysPower, ForwardPower, 0f);

        //No IMU implementation yet
        //This is assuming every movement has opposite sides (1+3, 2+4) moving equal and no skidding (so prob wont work)

        while((Motors[1].getPower() != 0 || Motors[2].getPower() != 0) & (Motors[3].getPower() != 0 || Motors[4].getPower() != 0)) {
            for(int Counter = 1; Counter <= 4; ++Counter)
            {
                if(Math.abs(Motors[Counter].getCurrentPosition()) >= Math.abs(Ticks))
                {
                    Motors[Counter].setPower(0);
                    if((Motors[1].getPower() == 0 || Motors[2].getPower() == 0) & (Motors[3].getPower() == 0 || Motors[4].getPower() == 0))
                    {
                        setPower(0, 0, 0);
                        break;
                    }
                }
            }
        }

        CurrentOrientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        if(Math.abs(CurrentOrientation.firstAngle - HeadingAdjust.firstAngle) > 2)
            TurnDegrees(CurrentOrientation.firstAngle - HeadingAdjust.firstAngle, ahwMap);

        setPower(0, 0, 0);
    }

    public void TurnDegrees(double Degrees, HardwareMap ahwMap)
    {
        //TURNING LEFT IS POSITIVE!!!

        if (!Configured)
        {
            //Motors = Config.Configure(Motors);
            Configured = true;
        }

        ResetMotorEncoders(ahwMap);

        //Mess with numbers if different circumference.
        double Ticks = Degrees * 19.8;

        setPower(0, 0, Degrees);

        while((Motors[1].getPower() != 0 || Motors[3].getPower() != 0) & (Motors[2].getPower() != 0 || Motors[4].getPower() != 0))
        {
            for(int Counter = 1; Counter <= 4; ++Counter)
            {
                if(Math.abs(Motors[Counter].getCurrentPosition()) >= Math.abs(Ticks))
                    Motors[Counter].setPower(0);
            }
        }

        setPower(0, 0, 0);
    }

    //The following method is code from Team 16072's virtual_robot program. Small changes are only to make it fit our format, the bulk of the method was written by them.
    void setPower(double px, double py, double pa){
        double p1 = -px + py - pa;
        double p2 = px + py - pa;
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
