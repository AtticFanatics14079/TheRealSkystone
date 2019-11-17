/*package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp.SkystoneDetectorExample;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Skystone.CameraDetect;

//WE ARE STARTING IN THE MIDDLE OF THE TILE THAT IS ABOVE THE RED DEPOT
@Autonomous(name = "PlaceSkystonesUnmovedF", group = "AutoOpModes")
public class PlaceSkystonesUnmovedF extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException{
        CameraDetect camera = new CameraDetect();
        MecanumDrive robot = new MecanumDrive();
        ElapsedTime time;

        int SkystonePosition = -2;
        robot.Configure(hardwareMap);
        camera.instantCamera(hardwareMap);
        CameraDevice.getInstance().setFlashTorchMode(true);
        waitForStart();

        robot.MoveEncoderTicks(22,1, hardwareMap);

        time = new ElapsedTime();

        while((SkystonePosition = camera.skystoneDetect(time)) == -2);

        //Strafe in front of skystone, pick it up
        //Turn left, drive to foundation
        if(SkystonePosition == 2){ // left
            robot.StrafeEncoderTicks(-20,-1,hardwareMap);
            sleep(1000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.TurnDegreesCurrentPos(-90,hardwareMap);
            robot.MoveEncoderTicks(230,1,hardwareMap);
        } else if(SkystonePosition == 1){ // center
            sleep(1000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.TurnDegreesCurrentPos(-90,hardwareMap);
            robot.MoveEncoderTicks(250,1,hardwareMap);
        } else if(SkystonePosition == 0){ // right
            robot.StrafeEncoderTicks(20,1,hardwareMap);
            sleep(1000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.TurnDegreesCurrentPos(-90,hardwareMap);
            robot.MoveEncoderTicks(270,1,hardwareMap);
        }

        //Turn Right, drop block
        robot.TurnDegreesCurrentPos(90,hardwareMap);
        sleep(1000);
        //Extend
        //Open Hand
        //retract
        robot.TurnDegreesEncoder(90,hardwareMap);

        //Drive to second skystone, turn to face it, pick it up
        if(SkystonePosition == 2){
            robot.MoveEncoderTicks(305,1,hardwareMap);
        }
        else if(SkystonePosition == 1){
            robot.MoveEncoderTicks(325,1,hardwareMap);
        }
        else if(SkystonePosition == 0) {
            robot.MoveEncoderTicks(345, 1, hardwareMap);
        }
        robot.TurnDegreesCurrentPos(-90,hardwareMap);
        sleep(1000);
        //Extend
        //Lower
        //Close hand
        //Raise
        //Retract

        //Turn, Drive to foundation with second skystone, drop it off
        robot.TurnDegreesEncoder(-90,hardwareMap);
        if(SkystonePosition == 2){
            robot.MoveEncoderTicks(290,1,hardwareMap);
        }
        else if(SkystonePosition == 1){
            robot.MoveEncoderTicks(310,1,hardwareMap);
        }
        else if(SkystonePosition == 0){
            robot.MoveEncoderTicks(330,1,hardwareMap);
        }
        System.out.println("Arrived at foundation");
        robot.TurnDegreesEncoder(90,hardwareMap);
        System.out.println("Dropped 2nd Skystone");
        //Extend
        //Open Hand

        //robot.StrafeEncoderTicks(150,0.95,0.05,hardwareMap);
    }
}

 */
