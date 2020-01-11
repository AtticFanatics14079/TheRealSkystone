package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.ChrisIngesterTeleOp;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.TeleOpMecanum;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Chris TeleOp", group = "Sensor")
public class ChrisTeleOp extends OpMode {

    ChrisIngesterTeleOp Drive = new ChrisIngesterTeleOp();

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