package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

//import android.app.Activity;
//import android.content.Context;
//import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.MovementAlgorithms;
//import org.firstinspires.ftc.teamcode.NewMultithreadRecord.WritingProcesses.WritingThread;

import java.util.Arrays;

@TeleOp(name= "Write TeleOp to File for Autonomous")
public class ControllerInput extends LinearOpMode {

    static ValueStorage vals = new ValueStorage();
    MovementAlgorithms move = new MovementAlgorithms();

    Boolean[] changedParts = new Boolean[10];
    Double[] hardwareActions = new Double[10]; //Same values as all the others.

    HardwareThread hardware;

    public final String FileName = "Test.txt"; //Change to record different paths
    public String FilePath;
    private double GAS = 1, straightGas, sideGas, turnGas, lastTime = 0;

    private boolean IngesterOut = false, StopIngester = false, IngesterPressed = false, HooksOpen = true;
    private int loopNumb = 0;

    private double voltMult = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        hardware = new HardwareThread(vals, hardwareMap);
        hardware.start();
        //WritingThread write = new WritingThread(vals, FileName);
        //write.start();
        //All other code in init() goes above here
        while(!opModeIsActive()){
            //do stuff if we want to loop before pressing play
        }
        ElapsedTime time = new ElapsedTime();
        hardware.startTime(time);
        //write.Start();
        //HooksOpen = true;
        //Anything else we want to do at the start of pressing play
        //this.voltMult = hardware.voltMult;

        while(!isStopRequested()){
            //Main loop of the class
            if(time.milliseconds() - lastTime >= 5) {
                lastTime = time.milliseconds();
                getInput();
            }
        }
        hardware.Stop();
        //write.Stop();

        ElapsedTime time2 = new ElapsedTime();

        while(hardware.isAlive() && time2.seconds() < 1.0){
        }

        /*if(write.isAlive()) {
            write.stop();
        }

         */
        if(hardware.isAlive()) hardware.stop();
    }

    private void getInput(){

        System.out.println("here");

        if(vals.receivedDesiredVals) Arrays.fill(changedParts, false);

        //Write what each input should correspond to (e.g. translating gamepad 1 left stick to
        //velocities for hardwareActions 1-4). Make sure to add it to the changedParts list every time.

        changedParts[0] = true; //This line is because we will always be changing these motors.
        changedParts[1] = true; //This line is because we will always be changing these motors.
        changedParts[2] = true; //This line is because we will always be changing these motors.
        changedParts[3] = true; //This line is because we will always be changing these motors.

        GAS = voltMult;

       if(gamepad1.right_bumper) GAS = 0.25 * GAS; //Quarter speed option

        if(gamepad2.x) GAS = -GAS; //Inverted movement

        straightGas = sideGas = GAS;

        turnGas = Math.abs(GAS);

        if(Math.abs(gamepad1.left_stick_y) >= 0.3 && Math.abs(gamepad1.left_stick_x) < 0.3) sideGas = 0; //Enables easier direct forward movement

        else if(Math.abs(gamepad1.left_stick_x) >= 0.3 && Math.abs(gamepad1.left_stick_y) < 0.3) straightGas = 0; //Enables easier direct sideways movement

        setPower(move.getPower(gamepad1.left_stick_x * sideGas, -gamepad1.left_stick_y * straightGas, Math.pow(gamepad1.right_stick_x, 3) * turnGas)); //Normal move, no bells and whistles here

        //No fancy algorithms atm, just passing velocities.

        loopNumb++;
    }

    private void setPower(double[] p){
        hardware.config.hardware.get(0).set(p[0]);
        hardware.config.hardware.get(1).set(p[1]);
        hardware.config.hardware.get(2).set(p[2]);
        hardware.config.hardware.get(3).set(p[3]);
    }
}
