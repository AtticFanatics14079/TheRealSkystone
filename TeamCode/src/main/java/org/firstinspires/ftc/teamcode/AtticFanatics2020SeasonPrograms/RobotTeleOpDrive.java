package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms;

import com.qualcomm.robotcore.hardware.DcMotor;

public class RobotTeleOpDrive extends ConfigureRobot {

    DcMotor[] Motors = new DcMotor[5];

    boolean Configured;

    public boolean Move()
    {
        if (!Configured)
        {
            Motors = Configure(Motors);
            Configured = true;
        }
        while(gamepad1.right_stick_x < -0.1 || gamepad1.right_stick_x > 0.1)
        {
            double SignedDistance = DistanceFromCenter(true);
            if(gamepad1.right_stick_x < 0)
                SignedDistance *= -1;
            Motors[1].setPower(SignedDistance);
            Motors[2].setPower(-SignedDistance);
            Motors[3].setPower(SignedDistance);
            Motors[4].setPower(-SignedDistance);
        }
        while(gamepad1.right_stick_x > -0.1 || gamepad1.right_stick_x < 0.1)
        {
            double SignedDistance = DistanceFromCenter(true);
            if(gamepad1.right_stick_y < 0)
                SignedDistance *= -1;
            Motors[1].setPower(SignedDistance);
            Motors[2].setPower(SignedDistance);
            Motors[3].setPower(SignedDistance);
            Motors[4].setPower(SignedDistance);
        }

        return Configured;
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }

    public double DistanceFromCenter(boolean LeftStick)
    {
        double Sqrt;
        if(LeftStick)
            Sqrt = Math.sqrt(gamepad1.left_stick_y * gamepad1.left_stick_y + gamepad1.left_stick_x * gamepad1.left_stick_x);
        else Sqrt = Math.sqrt(gamepad1.right_stick_y * gamepad1.right_stick_y + gamepad1.right_stick_y * gamepad1.right_stick_y);
        return Sqrt;
    }
}