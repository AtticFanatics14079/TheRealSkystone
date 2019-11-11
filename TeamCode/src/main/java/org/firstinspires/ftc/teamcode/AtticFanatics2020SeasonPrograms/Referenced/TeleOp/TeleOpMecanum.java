package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;

public class TeleOpMecanum extends Configure {

    private final double INPUT_RAMP_DIFF = 0.003; //Increase per cycle of speed during RampSpeed
    private final double EXTEND_LIMIT_FORWARD = 500000000, EXTEND_LIMIT_BACK = -500000000;  //Declare equal to the limits the extender should be extended to

    private HardwareMap hwMap;

    public double RampDiff = 0.2;

    public void Move(HardwareMap ahwMap, Gamepad G1, Gamepad G2)
    {
        hwMap = ahwMap;

        //if(G1.left_bumper) RampSpeed(G1.left_stick_x, G1.left_stick_y, G1.right_stick_x, 4); //RampSpeed at 1/4 speed

        /*else */if(G1.right_bumper) setPower(G1.left_stick_x/4, G1.left_stick_y/4, G1.right_stick_x/4); //Quarter speed option

        else if(Math.abs(G1.left_stick_y) >= 0.4 && Math.abs(G1.left_stick_x) < 0.4) setPower(0, G1.left_stick_y, G1.right_stick_x); //Enables easier direct forward movement

        else if(Math.abs(G1.left_stick_x) >= 0.4 && Math.abs(G1.left_stick_y) < 0.4) setPower(G1.left_stick_x, 0, G1.right_stick_x); //Enables easier direct sideways movement

        else setPower(G1.left_stick_x, G1.left_stick_y, G1.right_stick_x); //Normal move, no bells and whistles here

        /*if(!G1.left_bumper && !G1.right_bumper) {
            RampDiff = INPUT_RAMP_DIFF; //Reset ramping after stopping ramping
            SetZeroBehavior(true, hwMap); //Set to brake
        }
        */

        if(G2.a) Gripper.setPosition(.6); //Move claw to not gripped position
        else if(G2.b) Gripper.setPosition(.1); //Move claw to gripped position

        if(G2.right_trigger != 0) RotateGripper.setPosition(RotateGripper.getPosition() + 0.005); //Rotate one way
        else if(G2.left_trigger != 0) RotateGripper.setPosition(RotateGripper.getPosition() - 0.005); //Rotate other way

        if(Math.abs(G2.left_stick_y - 0.1) > 0 && ExtendGripper.getCurrentPosition() < EXTEND_LIMIT_FORWARD && ExtendGripper.getCurrentPosition() > EXTEND_LIMIT_BACK) ExtendGripper.setPower(G2.left_stick_y); //Move extender, but not past limits

        //Add other
    }

    /*public void RampSpeed(double px, double py, double pa, double SpeedDiv)
    {
        SetZeroBehavior(false, hwMap);

        if(px == 0 && py == 0 && pa == 0)
        {
            setPower(0, 0, 0);
            RampDiff = INPUT_RAMP_DIFF;
            return;
        }
        setPower((px * RampDiff)/SpeedDiv, (py * RampDiff) / SpeedDiv, (pa * RampDiff) / SpeedDiv);
        if(RampDiff < 1)
            RampDiff += INPUT_RAMP_DIFF;
    }
     */

    //The following method is code from Team 16072's virtual_robot program. Small changes are only to make it fit our format, the bulk of the method was written by them.
    public void setPower(double px, double py, double pa){ //Multiplied pa by -1 to suit turning requests
        double p1 = -px + py + pa;
        double p2 = px + py + pa;
        double p3 = -px + py - pa;
        double p4 = px + py - pa;
        double max = Math.max(1.0, Math.abs(p1));
        max = Math.max(max, Math.abs(p2));
        max = Math.max(max, Math.abs(p3));
        max = Math.max(max, Math.abs(p4));
        p1 /= max;
        p2 /= max;
        p3 /= max;
        p4 /= max;
        Motors[1].setPower(-p1);
        Motors[2].setPower(-p2);
        Motors[3].setPower(-p3);
        Motors[4].setPower(-p4);
    }
}
