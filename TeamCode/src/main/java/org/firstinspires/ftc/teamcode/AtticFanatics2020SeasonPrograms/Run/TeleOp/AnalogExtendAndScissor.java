package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp1Configure;

@TeleOp(name = "Analog Extend/Scissor")
public class AnalogExtendAndScissor extends OpMode {

    Comp1Configure Config = new Comp1Configure();

    @Override
    public void init() {
        Config.Configure(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad2.dpad_down) Config.Scissor.setPower(1);
        else if(gamepad2.dpad_up) Config.Scissor.setPower(-1);
        else Config.Scissor.setPower(0);
        if(gamepad2.dpad_left) Config.ExtendGripper.setPower(-1);
        else if(gamepad2.dpad_right) Config.ExtendGripper.setPower(1);
        else Config.ExtendGripper.setPower(0);

        telemetry.addData("Extend Gripper Position: ", Config.ExtendGripper.getCurrentPosition());
        telemetry.addData("Scissor Position: ", Config.Scissor.getCurrentPosition());
        telemetry.update();
    }
}
