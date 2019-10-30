package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "RampTest")
public class TestingRAMP extends LinearOpMode {
    private final double INPUT_RAMP_DIFF = 0.001, EXTEND_LIMIT_FORWARD = 500000000, EXTEND_LIMIT_BACK = -500000000;
    public double RampDiff = INPUT_RAMP_DIFF; //Declare equal to the limits the extender should be extended to
    DcMotor m1, m2, m3, m4;
    @Override
    public void runOpMode() throws InterruptedException {
        m1 = hardwareMap.dcMotor.get("back_left_motor");
        m2 = hardwareMap.dcMotor.get("front_left_motor");
        m3 = hardwareMap.dcMotor.get("front_right_motor");
        m4 = hardwareMap.dcMotor.get("back_right_motor");
        m1.setDirection(DcMotor.Direction.REVERSE);
        m2.setDirection(DcMotor.Direction.REVERSE);
        m1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while(opModeIsActive()) {
            //ALL OF THESE BUTTONS "FULL SEND"
            if (gamepad1.a) { // RAMP MODE, REGULAR SPEED
                RampSpeed(0, 1, 0, 1);
            } else if (gamepad1.b) { // 1/4 SPEED
                setPower(0, 0.25f, 0);
            } else if (gamepad1.x) { // RAMP MODE, 1/4 SPEED
                RampSpeed(0, 1, 0, 4);
            } else { // STOP
                setPower(0, 0, 0);
                RampDiff = INPUT_RAMP_DIFF;

            }
            telemetry.addData("Motor1Power = ", m1.getPower());
            telemetry.addData("Motor2Power = ", m2.getPower());
            telemetry.addData("Motor3Power = ", m3.getPower());
            telemetry.addData("Motor4Power = ", m4.getPower());
            telemetry.addData("RampDiff = ", RampDiff);
            telemetry.update();
        }
    }
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
        m1.setPower(p1);
        m2.setPower(p2);
        m3.setPower(p3);
        m4.setPower(p4);
    }
    void RampSpeed(double px, double py, double pa, double SpeedDiv)
    {
            setPower(0, (py/10  + RampDiff) / SpeedDiv, 0);
            RampDiff += INPUT_RAMP_DIFF;
            if(m1.getPower()>1/SpeedDiv){
                m1.setPower(1/SpeedDiv);
                m2.setPower(1/SpeedDiv);
                m3.setPower(1/SpeedDiv);
                m4.setPower(1/SpeedDiv);
            }
    }
}
