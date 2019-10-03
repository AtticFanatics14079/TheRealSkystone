package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp Test 9/29/19", group = "Sensor")
public class TeleOpTest extends OpMode {

    RobotTeleOpDrive Drive = new RobotTeleOpDrive();

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        Drive.Move();
    }
}