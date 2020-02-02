package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.Comp2TeleOpMecanum;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.Comp3TeleOpMecanum;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.StatesTeleOpMecanum;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "States TeleOp", group = "Sensor")
public class StatesTeleOp extends OpMode {

    StatesTeleOpMecanum Drive = new StatesTeleOpMecanum();

    @Override
    public void init() {
        Drive.Configure(hardwareMap);
        Drive.setBulkCachingManual();
        //Drive.RotateGripper.setPosition(Drive.ROTATE_GRIPPER_STRAIGHT);
    }

    @Override
    public void loop() {
            Drive.Move(hardwareMap, gamepad1, gamepad2);
            telemetry.addData("Level: ", Drive.level);
            telemetry.addData("Extend: ", Drive.ExtendGripper.getCurrentPosition());
            telemetry.addData("ScissorLeft: ", Drive.ScissorLeft.getCurrentPosition());
            telemetry.addData("ScissorRight: ", Drive.ScissorRight.getCurrentPosition());
            telemetry.update();
    }
}