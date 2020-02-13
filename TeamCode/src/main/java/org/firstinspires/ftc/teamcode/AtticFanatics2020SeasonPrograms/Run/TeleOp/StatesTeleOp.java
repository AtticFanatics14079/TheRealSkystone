package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.StatesTeleOpMecanum;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "States TeleOp", group = "Sensor")
public class StatesTeleOp extends LinearOpMode {

    StatesTeleOpMecanum Drive = new StatesTeleOpMecanum();

    @Override
    public void runOpMode() {
        Drive.Configure(hardwareMap);
        Drive.setBulkCachingManual();
        //Running scissors down to keep starting position consistent
        ElapsedTime time = new ElapsedTime();
        double prevTime = time.seconds();
        while(time.seconds() - prevTime < 1) {
            Drive.ScissorLeft.setPower(-0.2);
            Drive.ScissorRight.setPower(-0.2);
        }
        while(time.seconds() - prevTime < 2) {
            Drive.ScissorLeft.setPower(0);
            Drive.ScissorRight.setPower(0);
        }
        Drive.ScissorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Drive.ScissorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Drive.ScissorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Drive.ScissorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        Drive.ingester.setPower(0.5);
        Drive.Capstone.setPosition(Drive.CAPSTONE_CLOSED);
        Drive.startTime();
        while(!isStopRequested()) {
            Drive.Move(hardwareMap, gamepad1, gamepad2);
            telemetry.addData("Level: ", Drive.level);
            telemetry.addData("NextStack: ", Drive.nextStack);
            telemetry.addData("Extend: ", Drive.ExtendGripper.getCurrentPosition());
            telemetry.addData("ScissorLeft: ", Drive.ScissorLeft.getCurrentPosition());
            telemetry.addData("ScissorRight: ", Drive.ScissorRight.getCurrentPosition());
            telemetry.addData("Macro State: ", Drive.Macro);
            telemetry.addData("Capping Mode: ", Drive.Capping);
            telemetry.addData("Robot Role: ", Drive.status);
            telemetry.update();
        }
    }
}