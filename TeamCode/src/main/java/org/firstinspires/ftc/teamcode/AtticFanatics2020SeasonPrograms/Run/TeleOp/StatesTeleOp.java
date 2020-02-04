package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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
        //Drive.RotateGripper.setPosition(Drive.ROTATE_GRIPPER_STRAIGHT);
        waitForStart();
        Drive.ingester.setPower(0.5);
        Drive.startTime();
        while(!isStopRequested()) {
            Drive.Move(hardwareMap, gamepad1, gamepad2);
            telemetry.addData("Level: ", Drive.level);
            telemetry.addData("NextStack: ", Drive.nextStack);
            telemetry.addData("Extend: ", Drive.ExtendGripper.getCurrentPosition());
            telemetry.addData("ScissorLeft: ", Drive.ScissorLeft.getCurrentPosition());
            telemetry.addData("ScissorRight: ", Drive.ScissorRight.getCurrentPosition());
            telemetry.addData("Macro State: ", Drive.Macro);
            telemetry.addData("Pressed: ", Drive.Pressed);
            telemetry.addData("StartTime: ", Drive.startTime);
            telemetry.addData("Time: ", Drive.time.seconds());
            telemetry.update();
        }
    }
}