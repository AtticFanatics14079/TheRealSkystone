package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.Comp1TeleOpMecanum;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Skystone TeleOp", group = "Sensor")
public class Comp1TeleOp extends OpMode {

    Comp1TeleOpMecanum Drive = new Comp1TeleOpMecanum();

    @Override
    public void init() {
        Drive.Configure(hardwareMap);
        //Drive.RotateGripper.setPosition(Drive.ROTATE_GRIPPER_STRAIGHT);
    }

    @Override
    public void loop() {
            Drive.Move(hardwareMap, gamepad1, gamepad2);
            /*telemetry.addData("Rotate Position: ", Drive.RotateGripper.getPosition());
            telemetry.addData("Scissor Position: ", -Drive.Scissor.getCurrentPosition());
            telemetry.addData("Extend Gripper Position: ", Drive.ExtendGripper.getCurrentPosition());
            telemetry.addData("Foundation Left: ", Drive.FoundationLeft.getPosition());
            telemetry.addData("Foundation Right: ", Drive.FoundationRight.getPosition());
            telemetry.addData("Gripper: ", Drive.Gripper.getPosition());

            */telemetry.update();
    }
}