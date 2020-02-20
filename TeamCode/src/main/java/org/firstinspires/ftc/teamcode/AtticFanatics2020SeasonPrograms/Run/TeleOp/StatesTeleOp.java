package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.Comp2TeleOpMecanum;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.Comp3TeleOpMecanum;
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
        Drive.ExtendGripper.setTargetPosition(30);
        Drive.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(!isStopRequested() && Drive.ExtendGripper.isBusy() && !isStarted()){}
        Drive.ExtendGripper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Drive.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart();
        Drive.ingesterStates = StatesConfigure.Ingester.STOPPEDIN;
        //Drive.ingester.setPower(0.65);
        Drive.Capstone.setPosition(Drive.CAPSTONE_CLOSED);
        Drive.startTime();
        while(!isStopRequested()) {
            Drive.Move(hardwareMap, gamepad1, gamepad2);
            telemetry.addData("Block Level: ", Drive.level - 2);
            telemetry.addData("NextStack: ", Drive.nextStack);
            telemetry.addData("Extend: ", Drive.ExtendGripper.getCurrentPosition());
            telemetry.addData("ScissorLeft: ", Drive.ScissorLeft.getCurrentPosition());
            telemetry.addData("ScissorRight: ", Drive.ScissorRight.getCurrentPosition());
            telemetry.addData("Macro State: ", Drive.Macro);
            telemetry.addData("Capping Mode: ", Drive.stack);
            telemetry.addData("Robot Role: ", Drive.status);
            telemetry.addData("Motor1 Current: ", Drive.Motors[1].getCurrent(CurrentUnit.MILLIAMPS));
            telemetry.addData("Motor2 Current: ", Drive.Motors[2].getCurrent(CurrentUnit.MILLIAMPS));
            telemetry.addData("Motor3 Current: ", Drive.Motors[3].getCurrent(CurrentUnit.MILLIAMPS));
            telemetry.addData("Motor4 Current: ", Drive.Motors[4].getCurrent(CurrentUnit.MILLIAMPS));
            telemetry.addData("Ingester Current: ", Drive.ingester.getCurrent(CurrentUnit.MILLIAMPS));
            telemetry.addData("ScissorLeft Current: ", Drive.ScissorLeft.getCurrent(CurrentUnit.MILLIAMPS));
            telemetry.addData("ScissorRight Current: ", Drive.ScissorRight.getCurrent(CurrentUnit.MILLIAMPS));
            telemetry.addData("ExtendGripper Current: ", Drive.ExtendGripper.getCurrent(CurrentUnit.MILLIAMPS));
            telemetry.update();
        }
    }
}