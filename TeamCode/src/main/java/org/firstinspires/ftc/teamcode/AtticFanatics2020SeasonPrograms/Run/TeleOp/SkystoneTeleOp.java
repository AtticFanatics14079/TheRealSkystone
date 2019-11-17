package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

        import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp.TeleOpMecanum;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Skystone TeleOp", group = "Sensor")
public class SkystoneTeleOp extends OpMode {

    TeleOpMecanum Drive = new TeleOpMecanum();

    @Override
    public void init() {
        Drive.Configure(hardwareMap);
    }

    @Override
    public void loop() {
        Drive.Move(hardwareMap, gamepad1, gamepad2);
        telemetry.addData("FoundationLeft Position: ", Drive.FoundationLeft.getPosition());
        telemetry.addData("FoundationRight Position: ", Drive.FoundationRight.getPosition());
        telemetry.update();
    }
}