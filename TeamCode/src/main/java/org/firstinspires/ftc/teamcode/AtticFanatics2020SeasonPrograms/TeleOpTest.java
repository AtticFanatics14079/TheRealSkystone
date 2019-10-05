package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp Test 9/29/19", group = "Sensor")
public class TeleOpTest extends OpMode {

    MecanumTeleOpDrive Drive = new MecanumTeleOpDrive();

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        Drive.Move(hardwareMap, gamepad1, gamepad2);
    }
}