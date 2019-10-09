package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="TESTING Set Target Position and IsBusy")
public class TESTINGCurrentPosition extends LinearOpMode {

    DcMotor[] Motors = new DcMotor[5];

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        Motors[1] = hardwareMap.get(DcMotor.class, "back_left_motor");
        Motors[2] = hardwareMap.get(DcMotor.class, "front_left_motor");
        Motors[3] = hardwareMap.get(DcMotor.class, "front_right_motor");
        Motors[4] = hardwareMap.get(DcMotor.class, "back_right_motor");

        Motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[4].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Motors[3].setDirection(DcMotorSimple.Direction.REVERSE);
        Motors[4].setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        runtime.reset();

        for(int Counter = 1; Counter <= 5; ++Counter)
        {
            Run(Motors);
        }

        double TimeRan = runtime.milliseconds();

        while(opModeIsActive())
        {
            telemetry.addData("Time Ran: ", TimeRan);
            telemetry.update();
        }
    }

    public void Run(DcMotor[] Motors)
    {
        Motors[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Motors[1].setPower(1);
        Motors[2].setPower(1);
        Motors[3].setPower(1);

        while(Motors[1].getPower() != 0 || Motors[2].getPower() != 0 || Motors[3].getPower() != 0)
        {
            for(int Counter = 1; Counter <= 4; ++Counter)
            {
                if(Math.abs(Motors[Counter].getCurrentPosition()) >= Math.abs(4000 - 150))
                    Motors[Counter].setPower(0);
                telemetry.addData("Motors[1].getCurrentPosition: ", Motors[1].getCurrentPosition());
                telemetry.addData("Motors[2].getCurrentPosition: ", Motors[2].getCurrentPosition());
                telemetry.addData("Motors[3].getCurrentPosition: ", Motors[3].getCurrentPosition());
                telemetry.update();
            }
        }

        Motors[1].setPower(0);
        Motors[2].setPower(0);
        Motors[3].setPower(0);
    }
}
