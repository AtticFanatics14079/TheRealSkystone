package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Skystone TeleOp", group = "Sensor")
public class SkystoneTeleOp extends OpMode {

    TeleOpMecanum Drive = new TeleOpMecanum();

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        Drive.Move(hardwareMap, gamepad1, gamepad2);
    }
}