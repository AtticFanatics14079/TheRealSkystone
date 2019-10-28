package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
//WE ARE STARTING IN THE MIDDLE OF THE TILE THAT IS ABOVE THE RED DEPOT
public class UsEverythingThemBridge extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException{
        robot.setGrip(.1,hardwareMap);
        waitForStart();
        //strafe right - open - forward - extend
        robot.MoveEncoderTicks(57.785,1,0,hardwareMap);
        robot.GrabDrop(true,hardwareMap);
        robot.MoveEncoderTicks(25,0,1,hardwareMap);
        robot.ExtendGripper(25,hardwareMap);
        //scissor down

        //grab
        robot.GrabDrop(false,hardwareMap);
        //scissor up

        // back to one tile - strafe left - forward
        robot.MoveEncoderTicks(57.785,0,-1,hardwareMap);
        robot.MoveEncoderTicks(519.75,-1,0,hardwareMap);
        robot.MoveEncoderTicks(57.75,0,1,hardwareMap);
        //drop
        robot.GrabDrop(true,hardwareMap);
        //back
        robot.MoveEncoderTicks(57.75,0,-1,hardwareMap);
        //right again to other skystone - forward
        robot.MoveEncoderTicks(700, 1, 0,hardwareMap);
        robot.MoveEncoderTicks(50,0,1,hardwareMap);
        //grab
        robot.GrabDrop(false,hardwareMap);
        // left again - forward
        robot.MoveEncoderTicks(-700,-1,0,hardwareMap);
        robot.MoveEncoderTicks(50,0,1,hardwareMap);
        //drop
        robot.GrabDrop(true,hardwareMap);
        //grab foundation

        //pull back
        robot.MoveEncoderTicks(50,0,-1,hardwareMap);
        // strafe right to clear foundation - forward  -
        robot.MoveEncoderTicks(70,1,0,hardwareMap);
        robot.MoveEncoderTicks(50,0,1,hardwareMap);
        // strafe right under bridge
        robot.MoveEncoderTicks(500,1,0,hardwareMap);
    }
}
