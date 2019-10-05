package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumTeleOpDrive extends ConfigureRobot {

    public void Move(HardwareMap ahwMap, Gamepad G1, Gamepad G2)
    {
        if(!Configured)
        {
            Configure(ahwMap);
            Configured = true;
        }

        setPower(-G1.left_stick_x, G1.left_stick_y, G1.right_stick_x);

        //Other checks go here
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
