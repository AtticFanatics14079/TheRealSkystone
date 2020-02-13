package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

//import android.app.Activity;
//import android.content.Context;
//import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp2Configure;

import java.util.Arrays;

@TeleOp(name= "Write TeleOp to File for Autonomous")
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
    private double GAS = 1, straightGas, sideGas, turnGas, lastTime = 0;

    private boolean IngesterOut = false, StopIngester = false, IngesterPressed = false, HooksOpen = true;
    private int loopNumb = 0;

    private double voltMult = 1;

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
        ElapsedTime time = new ElapsedTime();
        hardware.startTime(time);
        write.Start();
        HooksOpen = true;
        //Anything else we want to do at the start of pressing play
        //this.voltMult = hardware.voltMult;

        while(!isStopRequested()){
            //Main loop of the class
            if(time.milliseconds() - lastTime >= 1) {
                lastTime = time.milliseconds();
                getInput();
            }
        }
        hardware.Stop();
        write.Stop();

        ElapsedTime time2 = new ElapsedTime();

        while(hardware.isAlive() || write.isAlive() && time2.seconds() < 1.0){
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

        changedParts[0] = true; //This line is because we will always be changing these motors.
        changedParts[1] = true; //This line is because we will always be changing these motors.
        changedParts[2] = true; //This line is because we will always be changing these motors.
        changedParts[3] = true; //This line is because we will always be changing these motors.

        GAS = voltMult;

        /*else */if(gamepad1.right_bumper) GAS = 0.25 * GAS; //Quarter speed option

        if(gamepad2.x) GAS = -GAS; //Inverted movement

        straightGas = sideGas = GAS;

        turnGas = Math.abs(GAS);

        if(Math.abs(gamepad1.left_stick_y) >= 0.3 && Math.abs(gamepad1.left_stick_x) < 0.3) sideGas = 0; //Enables easier direct forward movement

        else if(Math.abs(gamepad1.left_stick_x) >= 0.3 && Math.abs(gamepad1.left_stick_y) < 0.3) straightGas = 0; //Enables easier direct sideways movement

        double[] p = move.getPower(hardwareActions, gamepad1.left_stick_x * sideGas, -gamepad1.left_stick_y * straightGas, Math.pow(gamepad1.right_stick_x, 3) * turnGas); //Normal move, no bells and whistles here

        for(int i = 0; i < 4; i++) hardwareActions[i] = p[i];

        if(IngesterOut){
            if(!gamepad2.right_bumper && !gamepad2.left_bumper && IngesterPressed) IngesterPressed = false;
            else if(gamepad2.right_bumper && !IngesterPressed) {IngesterOut = false; IngesterPressed = true; setIngesters(-0.5);}
            else if(gamepad2.left_bumper && !IngesterPressed){IngesterOut = false; StopIngester = true; IngesterPressed = true; setIngesters(0);}
        }
        else if(StopIngester){
            if(!gamepad2.left_bumper && !gamepad2.right_bumper && IngesterPressed) IngesterPressed = false;
            else if(gamepad2.left_bumper && !IngesterPressed) {StopIngester = false; IngesterPressed = true; setIngesters(-0.5);}
            else if(gamepad2.right_bumper && !IngesterPressed){StopIngester = false; IngesterOut = true; IngesterPressed = true; setIngesters(0.5);}
        }
        else{
            if(!gamepad2.left_bumper && !gamepad2.right_bumper && IngesterPressed) IngesterPressed = false;
            else if(gamepad2.left_bumper && !IngesterPressed) {StopIngester = true; IngesterPressed = true; setIngesters(0);}
            else if(gamepad2.right_bumper && !IngesterPressed) {IngesterOut = true; IngesterPressed = true; setIngesters(0.5);}
        }

        if(gamepad1.dpad_up) {openHooks(true);}
        else if(gamepad1.dpad_down) {openHooks(false);}
        //else openHooks(HooksOpen);

        if(gamepad1.x) setScissors(-1);
        else if(gamepad1.y) setScissors(1);
        else setScissors(0);

        if(gamepad1.dpad_left) extend(1);
        else if(gamepad1.dpad_right) extend(-1);
        else extend(0);

        //No fancy algorithms atm, just passing velocities.
        vals.changedParts(true, changedParts);
        vals.runValues(true, hardwareActions);

        loopNumb++;
    }

    private void setIngesters(double power){
        changedParts[4] = true;
        changedParts[5] = true;
        hardwareActions[4] = power;
        hardwareActions[5] = power;
    }

    private void openHooks(boolean open){
        if(open != HooksOpen){changedParts[6] = true; changedParts[7] = true;}
        if(open){hardwareActions[6] = LEFT_OPEN; hardwareActions[7] = RIGHT_OPEN; HooksOpen = true;}
        else {hardwareActions[6] = LEFT_CLOSE; hardwareActions[7] = RIGHT_CLOSE; HooksOpen = false;}
    }

    private void setScissors(double power){
        if(Math.abs(hardwareActions[10] - power) > 0.1){
            changedParts[10] = true;
            changedParts[11] = true;
            hardwareActions[10] = power;
            hardwareActions[11] = power;
        }
    }

    private void extend(double power){
        if(Math.abs(hardwareActions[8] - power) > 0.1) {
            changedParts[8] = true;
            hardwareActions[8] = power;
        }
    }
}
