package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp1Configure;

public class SwerveTeleOpDriveUNFINISHED extends Comp1Configure {

    DcMotor[] Motors = new DcMotor[5];

    boolean Configured;

    public boolean Move(HardwareMap hwMap, Gamepad G1, Gamepad G2)
    {
        if (!Configured)
        {
            Configure(hwMap);
            Configured = true;
        }
        while(G1.right_stick_x != 0)
        {
            double SignedDistance = DistanceFromCenter(true, G1, G2);
            if(G1.right_stick_x < 0)
                SignedDistance *= -1;
            Motors[1].setPower(SignedDistance);
            Motors[2].setPower(-SignedDistance);
            Motors[3].setPower(SignedDistance);
            Motors[4].setPower(-SignedDistance);
        }
        while(G1.right_stick_x == 0)
        {
            double SignedDistance = DistanceFromCenter(true, G1, G2);
            if(G1.left_stick_y < 0)
                SignedDistance *= -1;
            Motors[1].setPower(SignedDistance);
            Motors[2].setPower(SignedDistance);
            Motors[3].setPower(SignedDistance);
            Motors[4].setPower(SignedDistance);
        }

        return Configured;
    }

    public double DistanceFromCenter(boolean LeftStick, Gamepad G1, Gamepad G2)
    {
        double Sqrt;
        if(LeftStick)
            Sqrt = Math.sqrt(G1.left_stick_y * G1.left_stick_y + G1.left_stick_x * G1.left_stick_x);
        else Sqrt = Math.sqrt(G1.right_stick_y * G1.right_stick_y + G1.right_stick_y * G1.right_stick_y);
        return Sqrt;
    }
}