package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

//import android.app.Activity;
//import android.content.Context;
//import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp2Configure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TeleOp(name= "Write TeleOp to File for Autonomous (Chris ignore this, don't start this code, don't do it, I SWEAR IF YOU DO IT)")
public class ControllerInput extends LinearOpMode {

    static ValueStorage vals = new ValueStorage();
    MovementAlgorithms move = new MovementAlgorithms();

    public static final double LEFT_OPEN = Comp2Configure.LEFT_OPEN;
    public static final double LEFT_CLOSE = Comp2Configure.LEFT_CLOSE;
    public static final double RIGHT_OPEN = Comp2Configure.RIGHT_OPEN;
    public static final double RIGHT_CLOSE = Comp2Configure.RIGHT_CLOSE;

    boolean[] changedParts = new boolean[10];
    double[] hardwareActions = new double[10]; //Same values as all the others.

    public final String FileName = "Test.txt"; //Change to record different paths
    public String FilePath;
    private double GAS = 1;

    private boolean IngesterOut = false, StopIngester = false, IngesterPressed = false, HooksOpen = true;
    private int loopNumb = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        System.out.println(3);
        HardwareThread hardware = new HardwareThread(vals, hardwareMap);
        System.out.println(4);
        hardware.start();
        System.out.println(5);
        WritingThread write = new WritingThread(vals, FileName);
        System.out.println(6);
        write.start();
        hardwareActions[4] = 0;
        hardwareActions[5] = 0;
        //All other code in init() goes above here
        while(!opModeIsActive()){
            //do stuff if we want to loop before pressing play
            telemetry.addData("File: ", write.file);
            telemetry.addData("Write Exists: ", write.isAlive());
            telemetry.addData("Write Ready: ", write.trace1);
            telemetry.addData("Hardware Ready: ", hardware.ready);
            telemetry.update();
        }
        hardware.startTime();
        write.Start();
        HooksOpen = true;
        //Anything else we want to do at the start of pressing play
        while(!isStopRequested()){
            //Main loop of the class
            telemetry.addData("File: ", write.file);
            telemetry.addData("Write Exists: ", write.isAlive());
            telemetry.addData("Hardware Time: ", hardware.time);
            telemetry.addData("Time Written: ", vals.timeWritten);
            telemetry.update();
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

        if(vals.receivedDesiredVals) Arrays.fill(changedParts, false);

        //Write what each input should correspond to (e.g. translating gamepad 1 left stick to
        //velocities for hardwareActions 1-4). Make sure to add it to the changedParts list every time.

        //At the moment, including drive train only.
        changedParts[0] = true; //This line is because we will always be moving the motors.
        changedParts[1] = true; //This line is because we will always be moving the motors.
        changedParts[2] = true; //This line is because we will always be moving the motors.
        changedParts[3] = true; //This line is because we will always be moving the motors.
        //changedParts[4] = true; //This line is because we will always be moving the motors.
        //changedParts[5] = true; //This line is because we will always be moving the motors.

        GAS = 1;

        if(gamepad1.right_bumper) GAS = 0.25;

        if(gamepad2.x) GAS = -GAS;

        if(Math.abs(gamepad1.left_stick_x) >= 0.3 && Math.abs(gamepad1.left_stick_y) < 0.3 && Math.abs(gamepad1.right_stick_x) < 0.3){
            double[] p = move.getPower(hardwareActions, gamepad1.left_stick_x * GAS, 0, 0);
            for(int i = 0; i < 4; i++) hardwareActions[i] = p[i];
        }
        else if(Math.abs(gamepad1.left_stick_y) >= 0.3 && Math.abs(gamepad1.left_stick_x) < 0.3 || Math.abs(gamepad1.right_stick_x) < 0.3){
            double[] p = move.getPower(hardwareActions, 0, -gamepad1.left_stick_y * GAS, 0);
            for(int i = 0; i < 4; i++) hardwareActions[i] = p[i];
        }
        else if(Math.abs(gamepad1.left_stick_x) >= 0.3 || Math.abs(gamepad1.left_stick_y) >= 0.3 || Math.abs(gamepad1.right_stick_x) >= 0.3){
            double[] p = move.getPower(hardwareActions, gamepad1.left_stick_x * GAS, -gamepad1.left_stick_y * GAS, Math.pow(gamepad1.right_stick_x, 3) * Math.abs(GAS));
            for(int i = 0; i < 4; i++) hardwareActions[i] = p[i];
        }
        else {
            hardwareActions[0] = 0.0;
            hardwareActions[1] = 0.0;
            hardwareActions[2] = 0.0;
            hardwareActions[3] = 0.0;
        }

        if(loopNumb < 1000){
            changedParts[4] = true;
            changedParts[5] = true;
        }

        if(IngesterOut){
            if(!gamepad2.right_bumper && !gamepad2.left_bumper && IngesterPressed) IngesterPressed = false;
            else if(gamepad2.right_bumper && !IngesterPressed) {IngesterOut = false; IngesterPressed = true;}
            else if(gamepad2.left_bumper && !IngesterPressed){IngesterOut = false; StopIngester = true; IngesterPressed = true;}
            setIngesters(0.5);
        }
        else if(StopIngester){
            if(!gamepad2.left_bumper && !gamepad2.right_bumper && IngesterPressed) IngesterPressed = false;
            else if(gamepad2.left_bumper && !IngesterPressed) {StopIngester = false; IngesterPressed = true;}
            else if(gamepad2.right_bumper && !IngesterPressed){StopIngester = false; IngesterOut = true; IngesterPressed = true;}
            setIngesters(0);
        }
        else{
            if(!gamepad2.left_bumper && !gamepad2.right_bumper && IngesterPressed) IngesterPressed = false;
            else if(gamepad2.left_bumper && !IngesterPressed) {StopIngester = true; IngesterPressed = true;}
            else if(gamepad2.right_bumper && !IngesterPressed) {IngesterOut = true; IngesterPressed = true;}
            setIngesters(-0.5);
        }

        if(gamepad1.dpad_up) {openHooks(true);}
        else if(gamepad1.dpad_down) {openHooks(false);}
        //else openHooks(HooksOpen);

        //No fancy algorithms atm, just passing velocities.
        vals.changedParts(true, changedParts);
        vals.runValues(true, hardwareActions);

        loopNumb++;
    }

    private void setIngesters(double power){
        if(hardwareActions[5] != power){
            changedParts[4] = true;
            changedParts[5] = true;
        }
        hardwareActions[4] = power;
        hardwareActions[5] = power;
    }

    private void openHooks(boolean open){
        //if(open != HooksOpen){changedParts[6] = true; changedParts[7] = true;}
        changedParts[6] = true;
        changedParts[7] = true;
        if(open){hardwareActions[6] = LEFT_OPEN; hardwareActions[7] = RIGHT_OPEN; HooksOpen = true;}
        else {hardwareActions[6] = LEFT_CLOSE; hardwareActions[7] = RIGHT_CLOSE; HooksOpen = false;}
    }
}
