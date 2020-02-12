package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class TestMovement extends LinearOpMode {

    DcMotor[] Motors = new DcMotor[5];

    @Override
    public void runOpMode() throws InterruptedException {

        Motors[1] = hardwareMap.get(DcMotor.class, "back_left_motor");
        Motors[2] = hardwareMap.get(DcMotor.class, "front_left_motor");
        Motors[3] = hardwareMap.get(DcMotor.class, "front_right_motor");
        Motors[4] = hardwareMap.get(DcMotor.class, "back_right_motor");
        Motors[3].setDirection(DcMotorSimple.Direction.REVERSE);
        Motors[4].setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        Motors[1].setPower(1);
        Motors[2].setPower(1);
        Motors[3].setPower(1);
        Motors[4].setPower(1);

        while (!isStopRequested()){
        }
    }
}
