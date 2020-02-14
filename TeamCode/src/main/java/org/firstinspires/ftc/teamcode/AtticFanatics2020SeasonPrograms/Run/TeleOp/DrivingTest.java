package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

@Autonomous
public class DrivingTest extends LinearOpMode {

    DcMotor[] Motors = new DcMotor[5];

    @Override
    public void runOpMode() throws InterruptedException {
        StatesConfigure config = new StatesConfigure();
        config.Configure(hardwareMap);
        //Manual version to check
        /*
        Motors[1] = hardwareMap.get(DcMotor.class, "back_left_motor");
        Motors[2] = hardwareMap.get(DcMotor.class, "front_left_motor");
        Motors[3] = hardwareMap.get(DcMotor.class, "front_right_motor");
        Motors[4] = hardwareMap.get(DcMotor.class, "back_right_motor");
        Motors[3].setDirection(DcMotorSimple.Direction.REVERSE);
        Motors[4].setDirection(DcMotorSimple.Direction.REVERSE);

        Motors[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         */

        while(!isStopRequested()){
            config.
                    Motors[1].setPower(gamepad1.left_stick_y);
            config.
                    Motors[2].setPower(gamepad1.left_stick_y);
            config.
                    Motors[3].setPower(gamepad1.left_stick_y);
            config.
                    Motors[4].setPower(gamepad1.left_stick_y);
        }
    }
}
