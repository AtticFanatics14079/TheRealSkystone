package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

@Autonomous(name = "Testing Mecanum Drive")
public class TestingClassReferences extends LinearOpMode {

    //The next line instantiates a supplementary class. The "Vera" variable can be any name as long as it is referenced by the same name later, but we're keepin it this way for now gang cause Vera carried this one.
    //RobotMecanum vera = new RobotMecanum();
    MecanumDrive Drive = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();
        //vera.SetMotorPower(.5);

//        Drive.MoveEncoderTicks(80, 0, 1, hardwareMap);
       // Drive.MoveEncoderTicks(250, -1, 0, hardwareMap);

        Drive.TurnDegrees(90, hardwareMap); //FINALLY WORKS
        while (opModeIsActive())
        {

            telemetry.addData("Motors[1].getCurrentPosition", Drive.Motors[1].getCurrentPosition());
            telemetry.addData("Motors[2].getCurrentPosition", Drive.Motors[2].getCurrentPosition());
            telemetry.addData("Motors[3].getCurrentPosition", Drive.Motors[3].getCurrentPosition());
            telemetry.addData("Motors[4].getCurrentPosition", Drive.Motors[4].getCurrentPosition());
            telemetry.update();


        }

        //Drive.MoveEncoderTicks(20, 1, 0, hardwareMap);

        //vera.SidewaysMovement(30, Configured);

        //Drive.TurnDegrees(90); //TURNING LEFT IS POSITIVE!!!
        //Drive.TurnDegrees(-90); //TURNING LEFT IS POSITIVE!!!
    }
}