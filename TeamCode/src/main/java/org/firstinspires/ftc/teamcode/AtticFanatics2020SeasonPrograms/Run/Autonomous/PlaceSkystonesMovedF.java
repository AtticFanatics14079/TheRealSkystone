package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;

//WE ARE STARTING IN THE MIDDLE OF THE TILE THAT IS ABOVE THE RED DEPOT
@Autonomous(name = "PlaceSkystonesMovedF", group = "AutoOpModes")
public class PlaceSkystonesMovedF extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException{
        //robot.setGrip(.1,hardwareMap);
        waitForStart();
        //Forward, then look for skystone.
        robot.MoveEncoderTicks(70,0,1,hardwareMap);
        int SkystonePosition = (int)(3*(Math.random())); // Random for now, replace with actual detection later
        //int SkystonePosition = 2;
        System.out.println(SkystonePosition);
        sleep(1000);
        //Strafe in front of skystone, pick it up
        //Turn left, drive to foundation
        if(SkystonePosition == 0){ // left
            robot.MoveEncoderTicks(20,-1,0,hardwareMap);
            sleep(1000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.TurnDegreesEncoder(-90,hardwareMap);
            robot.MoveEncoderTicks(140,0,1,hardwareMap);
        } else if(SkystonePosition == 1){ // center
            sleep(1000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.TurnDegreesEncoder(-90,hardwareMap);
            robot.MoveEncoderTicks(160,0,1,hardwareMap);
        } else if(SkystonePosition ==2){ // right
            robot.MoveEncoderTicks(20,1,0,hardwareMap);
            sleep(1000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.TurnDegreesEncoder(-90,hardwareMap);
            robot.MoveEncoderTicks(180,0,1,hardwareMap);
        }
        //Might want to turn left+turn back with extension, if robot is not far enough to the left
        sleep(1000);
        //Extend
        //Open Hand
        //retract
        robot.TurnDegreesEncoder(180,hardwareMap);

        //Drive to second skystone, turn to face it, pick it up
        if(SkystonePosition == 0){
            robot.MoveEncoderTicks(215,0,1,hardwareMap);
        }
        else if(SkystonePosition == 1){
            robot.MoveEncoderTicks(235,0,1,hardwareMap);
        }
        else if(SkystonePosition == 2){
            robot.MoveEncoderTicks(255,0,1,hardwareMap);
        }
        robot.TurnDegreesEncoder(-90,hardwareMap);
        sleep(1000);
        //Extend
        //Lower
        //Close hand
        //Raise
        //Retract

        //Turn, Drive to foundation with second skystone, drop it off
        robot.TurnDegreesEncoder(-90,hardwareMap);
        if(SkystonePosition == 0){
            robot.MoveEncoderTicks(210,0,1,hardwareMap);
        }
        else if(SkystonePosition == 1){
            robot.MoveEncoderTicks(230,0,1,hardwareMap);
        }
        else if(SkystonePosition == 2){
            robot.MoveEncoderTicks(250,0,1,hardwareMap);
        }
        System.out.println("Arrived at foundation");
        System.out.println("Dropped 2nd Skystone");
        sleep(1000);
        //Extend
        //Open Hand

        robot.MoveEncoderTicks(50,0.1,-1,hardwareMap);
    }
}
