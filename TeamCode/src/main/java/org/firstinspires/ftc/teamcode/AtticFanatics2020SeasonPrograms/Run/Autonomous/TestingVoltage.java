package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

@Autonomous(name = "Testing Voltage")
public class TestingVoltage extends LinearOpMode {

    StatesConfigure config = new StatesConfigure();

    @Override
    public void runOpMode() throws InterruptedException {
        config.Configure(hardwareMap);

        waitForStart();

        ElapsedTime time = new ElapsedTime();

        while(time.seconds() < 5){
            config.Motors[1].setPower(0.5 * (13 / config.voltSense.getVoltage()));
            config.Motors[2].setPower(0.5 * (13 / config.voltSense.getVoltage()));
            config.Motors[3].setPower(0.5 * (13 / config.voltSense.getVoltage()));
            config.Motors[4].setPower(0.5 * (13 / config.voltSense.getVoltage()));
        }

        config.Motors[1].setPower(0);
        config.Motors[2].setPower(0);
        config.Motors[3].setPower(0);
        config.Motors[4].setPower(0);
    }
}
