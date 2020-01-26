package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.Comp2TeleOpMecanum;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.Comp3TeleOpMecanum;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "States TeleOp", group = "Sensor")
public class StatesTeleOp extends OpMode {

    Comp3TeleOpMecanum Drive = new Comp3TeleOpMecanum();

    @Override
    public void init() {
        Drive.Configure(hardwareMap);
        //Drive.RotateGripper.setPosition(Drive.ROTATE_GRIPPER_STRAIGHT);
    }

    @Override
    public void loop() {
            Drive.Move(hardwareMap, gamepad1, gamepad2);
            telemetry.addData("Running Back: ", Drive.runningBack);
    }
}