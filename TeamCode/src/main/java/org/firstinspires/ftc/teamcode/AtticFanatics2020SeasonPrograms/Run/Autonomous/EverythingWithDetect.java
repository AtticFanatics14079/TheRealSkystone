/*package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone.CameraDetect;

//WE ARE STARTING IN THE MIDDLE OF THE TILE THAT IS ABOVE THE RED DEPOT
@Autonomous(name = "EverythingDetect", group = "AutoOpModes")
public class EverythingWithDetect extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();
    CameraDetect camera = new CameraDetect();
    ElapsedTime time;
    @Override
    public void runOpMode() throws InterruptedException{
        //robot.setGrip(.1,hardwareMap);
        robot.Comp1Configure(hardwareMap);
        int SkystonePosition = -2;
        camera.instantCamera(hardwareMap);
        CameraDevice.getInstance().setFlashTorchMode(true);
        waitForStart();
        //Forward, then look for skystone.
        robot.MoveEncoderTicks(24,1, hardwareMap);
        time = new ElapsedTime();
        while((SkystonePosition = camera.skystoneDetect(time)) == -2);
        //int SkystonePosition = 2;

        //Strafe in front of skystone, pick it up
        //Turn left, drive to foundation
        if(SkystonePosition == 2){ // left
            //robot.StrafeEncoderTicks(-12.5,-1, hardwareMap);
            sleep(10000);
            robot.MoveEncoderTicks(53, 1, hardwareMap);
            sleep(3000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.MoveEncoderTicks(-30, 1, hardwareMap);
            robot.TurnDegreesEncoder(90,hardwareMap);
            robot.MoveEncoderTicks(186,1,hardwareMap);

        } else if(SkystonePosition == 1){ // center
            //robot.StrafeEncoderTicks(6, 1, hardwareMap);
            sleep(10000);
            robot.MoveEncoderTicks(53, 1, hardwareMap);
            sleep(3000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.MoveEncoderTicks(-30, 1, hardwareMap);
            robot.TurnDegreesEncoder(90,hardwareMap);
            robot.MoveEncoderTicks(208,1,hardwareMap);
        } else if(SkystonePosition == 0){ // right
            //robot.StrafeEncoderTicks(28,1,hardwareMap);
            sleep(10000);
            robot.MoveEncoderTicks(53, 1, hardwareMap);
            sleep(3000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.MoveEncoderTicks(-30, 1, hardwareMap);
            robot.TurnDegreesEncoder(90,hardwareMap);
            robot.MoveEncoderTicks(230,1,hardwareMap);
        }

        //Turn Right, drop block
        robot.TurnDegreesEncoder(-90,hardwareMap);
        sleep(1000);
        //Extend
        //Open Hand
        //retract
        robot.TurnDegreesEncoder(-90,hardwareMap);

        //Drive to second skystone, turn to face it, pick it up
        if(SkystonePosition == 2){
            robot.MoveEncoderTicks(305,1,hardwareMap);
        }
        else if(SkystonePosition == 1){
            robot.MoveEncoderTicks(325,1,hardwareMap);
        }
        else if(SkystonePosition == 0){
            robot.MoveEncoderTicks(345,1,hardwareMap);
        }
        robot.TurnDegreesEncoder(90,hardwareMap);
        sleep(1000);
        //Extend
        //Lower
        //Close hand
        //Raise
        //Retract

        //Turn, Drive to foundation with second skystone, drop it off
        robot.TurnDegreesEncoder(90,hardwareMap);
        /*
        if(SkystonePosition == 2){
            robot.MoveEncoderTicks(290,1,hardwareMap);
        }
        else if(SkystonePosition == 1){
            robot.MoveEncoderTicks(310,1,hardwareMap);
        }
        else if(SkystonePosition == 0){
            robot.StrafeEncoderTicks(330,1,hardwareMap);
        }
        System.out.println("Arrived at foundation");
        robot.TurnDegreesEncoder(90,hardwareMap);
        System.out.println("Dropped 2nd Skystone");
        //Extend
        //Open Hand

        //Drive up to foundation, Grip it, Drive back into wall
        robot.MoveEncoderTicks(15,1,hardwareMap);
        sleep(500);
        //Foundation hooks down
        robot.MoveEncoderTicks(90,-1,hardwareMap);
        sleep(500);
        //Foundation hooks up
        System.out.println("Foundation moved");

        //Under Bridge, on Neutral Side
        robot.StrafeEncoderTicks(70,1,hardwareMap);
        robot.MoveEncoderTicks(70,1,hardwareMap);
        robot.StrafeEncoderTicks(65,1,hardwareMap);1

    }
}
            */