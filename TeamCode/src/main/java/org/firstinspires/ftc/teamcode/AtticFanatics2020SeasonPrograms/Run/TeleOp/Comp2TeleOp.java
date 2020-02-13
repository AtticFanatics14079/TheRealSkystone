package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.Comp2TeleOpMecanum;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Comp 2 TeleOp", group = "Sensor")
public class Comp2TeleOp extends OpMode {

    Comp2TeleOpMecanum Drive = new Comp2TeleOpMecanum();

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

            */
            telemetry.addData("Scissor Left", Drive.ScissorLeft.getCurrentPosition());
            telemetry.addData("Scissor Right", Drive.ScissorRight.getCurrentPosition());
            telemetry.addData("Level: ", Drive.level);
            telemetry.addData("Gripper: ", Drive.Gripper.getPosition());
            //telemetry.addData("Extend Gripper: ", Drive.ExtendGripper.getPower());
            telemetry.update();
    }
}