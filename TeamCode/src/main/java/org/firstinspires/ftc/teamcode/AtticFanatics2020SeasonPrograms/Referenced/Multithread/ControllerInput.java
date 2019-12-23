package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TeleOp(name= "Write TeleOp to File for Autonomous (Chris ignore this, don't start this code, don't do it, I SWEAR IF YOU DO IT)")
public class ControllerInput extends LinearOpMode {

    Configure robot = new Configure();
    static ValueStorage vals = new ValueStorage();
    MovementAlgorithms move = new MovementAlgorithms();

    boolean[] changedParts = new boolean[10];
    double[] hardwareActions = new double[10]; //Same values as all the others.

    public final String FileName = "Test.txt";

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareThread hardware = new HardwareThread(vals, hardwareMap);
        hardware.start();
        WritingThread write = new WritingThread(vals, FileName);
        write.start();
        //All other code in init() goes above here
        while(!opModeIsActive()){
            //do stuff if we want to loop before pressing play
        }
        hardware.startTime();
        //Anything else we want to do at the start of pressing play
        while(!isStopRequested()){
            //Main loop of the class
            try{
                getInput();
            }
            catch (Exception e){
                System.out.println("Problem in main thread! \n" + e);
            }
        }
        hardware.Stop();
        write.Stop();

        ElapsedTime time = new ElapsedTime();

        while(hardware.isAlive() || write.isAlive() && time.seconds() < 1.0){
        }

        if(write.isAlive()) {
            write.stop();
        }
        if(hardware.isAlive()) hardware.stop();
    }

    private void getInput(){

        Arrays.fill(changedParts, false);

        //Write what each input should correspond to (e.g. translating gamepad 1 left stick to
        //velocities for hardwareActions 1-4). Make sure to add it to the changedParts list every time.

        //At the moment, including drive train only.
        changedParts[0] = true; //This line is because we will always be moving the motors.
        changedParts[1] = true; //This line is because we will always be moving the motors.
        changedParts[2] = true; //This line is because we will always be moving the motors.
        changedParts[3] = true; //This line is because we will always be moving the motors.

        if(Math.abs(gamepad1.left_stick_x) >= 0.2 || Math.abs(gamepad1.left_stick_y) >= 0.2 || Math.abs(gamepad1.right_stick_x) >= 0.2){
            double[] p = move.getVelocity(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            for(int i = 0; i < 4; i++) hardwareActions[i] = p[i];
        }
        else {
            hardwareActions[0] = 0.0;
            hardwareActions[1] = 0.0;
            hardwareActions[2] = 0.0;
            hardwareActions[3] = 0.0;
        }

        //No fancy algorithms atm, just passing velocities.
        vals.changedParts(true, changedParts);
        vals.runValues(true, hardwareActions);
    }
}
