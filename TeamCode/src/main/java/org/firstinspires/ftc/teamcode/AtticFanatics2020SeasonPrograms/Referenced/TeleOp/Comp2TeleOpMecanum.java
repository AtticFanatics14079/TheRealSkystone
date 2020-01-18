package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp2Configure;

public class Comp2TeleOpMecanum extends Comp2Configure {

    public int level = 0; //0: Pick up Block from inside robot. 1: Above level 1
    private HardwareMap hwMap;

    private double GAS, straightGas, sideGas, turnGas, startExtend, extendDirection = 0;

    private boolean ScissorOverload = false, ExtendOverload = false, Pressed = false, IngesterOut = false, StopIngester = false, IngesterPressed = false;
    ElapsedTime time;

    public void Move(HardwareMap ahwMap, Gamepad G1, Gamepad G2) {
        hwMap = ahwMap;

        if (time == null) time = new ElapsedTime();

        /*if(G2.a) setIngesters(0);
        else if(G2.b) setIngesters(-1);
        else setIngesters(1);
         */

        GAS = 1;

        if (G1.right_bumper) GAS = 0.25; //Quarter speed option

        if (G2.x) GAS = -GAS; //Inverted movement

        straightGas = sideGas = turnGas = GAS;

        turnGas = Math.abs(turnGas);

        if (Math.abs(G1.left_stick_y) >= 0.3 && Math.abs(G1.left_stick_x) < 0.3)
            sideGas = 0; //Enables easier direct forward movement

        else if (Math.abs(G1.left_stick_x) >= 0.3 && Math.abs(G1.left_stick_y) < 0.3)
            straightGas = 0; //Enables easier direct sideways movement

        setPower(-G1.left_stick_x * sideGas, G1.left_stick_y * straightGas, Math.pow(G1.right_stick_x, 3) * turnGas); //Normal move, no bells and whistles here

        if(G1.left_bumper){
            ExtendGripper.setPower(1);
            startExtend = time.milliseconds();
        }
        if(startExtend+1000<time.milliseconds()){
            ExtendGripper.setPower(0.6);
        }
        if (G2.dpad_down) {
            FoundationLeft.setPosition(LEFT_CLOSE);
            FoundationRight.setPosition(RIGHT_CLOSE);
        } //Set grabbers down
        else if (G2.dpad_up) {
            FoundationLeft.setPosition(LEFT_OPEN);
            FoundationRight.setPosition(RIGHT_OPEN);
        } //Set grabbers up

        if (G1.a) Gripper.setPosition(GRIPPER_CLOSED);
        else if (G1.b) Gripper.setPosition(GRIPPER_OPEN);


        if (IngesterOut) {
            if (!G2.right_bumper && !G2.left_bumper && IngesterPressed) IngesterPressed = false;
            else if (G2.right_bumper && !IngesterPressed) {
                IngesterOut = false;
                IngesterPressed = true;
            } else if (G2.left_bumper && !IngesterPressed) {
                IngesterOut = false;
                StopIngester = true;
                IngesterPressed = true;
            }
            setIngesters(0.5);
        } else if (StopIngester) {
            if (!G2.left_bumper && !G2.right_bumper && IngesterPressed) IngesterPressed = false;
            else if (G2.left_bumper && !IngesterPressed) {
                StopIngester = false;
                IngesterPressed = true;
            } else if (G2.right_bumper && !IngesterPressed) {
                StopIngester = false;
                IngesterOut = true;
                IngesterPressed = true;
            }
            setIngesters(0);
        } else {
            if (!G2.left_bumper && !G2.right_bumper && IngesterPressed) IngesterPressed = false;
            else if (G2.left_bumper && !IngesterPressed) {
                StopIngester = true;
                IngesterPressed = true;
            } else if (G2.right_bumper && !IngesterPressed) {
                IngesterOut = true;
                IngesterPressed = true;
            }
            setIngesters(-0.5);
        }
        //TODO: SCISSOR LEVELS
        //TODO: MACROS

        if(G1.dpad_up && !Pressed && level<levels.length-1){ //READY TO PLACE NEXT LEVEL
            level++;
            setScissorLevel(level);
            Pressed = true;
        }
        else if(G1.dpad_down && !Pressed){ //
            setScissorLevel(0);
            level = 0;
            Pressed = true;
        }
        else if(G1.x){ // DROP BLOCK - ALREADY LINED UP
            ScissorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ScissorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ScissorRight.setTargetPosition(levels[level]-1000); // DROP OFFSET
            ScissorLeft.setTargetPosition(levels[level]-1000);
            Gripper.setPosition(GRIPPER_OPEN); // While coming down, open gripper
            try{
                Thread.sleep(500);
            }
            catch(Exception e){
                System.out.println(e);
            }
            setScissorLevel(level); // return to original position
        }
        else if(!G1.dpad_down && !G1.dpad_up){
            Pressed = false;
        }

        /*
        G2
        OVERLOADS
        START
        HERE
         */
        if(G2.x){ //While this is pressed, this will destroy Driver 1's scissor control
            ScissorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ScissorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ScissorLeft.setPower(G2.left_stick_y);
            ScissorRight.setPower(G2.left_stick_y);
            if(G2.a){ //PRESS BOTH TO STORE CURRENT POSITION AS LEVEL 0
                resetScissor();
            }
        }
        if (G2.y) { //FULL MANUAL CONTROL OF EXTENSION
            if (G2.right_trigger != 0) ExtendGripper.setPower(0.5+0.5*(G2.right_trigger));
            if (G2.left_trigger != 0) ExtendGripper.setPower(0.5*(G2.left_trigger));
            else ExtendGripper.setPower(0);
        }

        /*
        G2
        OVERLOADS
        END
        HERE
         */

    }

    private void setScissorLevel(int level){
        ScissorLeft.setTargetPosition(levels[level]);
        ScissorRight.setTargetPosition(levels[level]);
        ScissorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ScissorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    private void resetScissor(){
        ScissorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ScissorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        level = 0;
    }
    /*
    private void Extend(boolean Out){
        if(extendDirection != 0 && time.seconds() - extenderTime < 2) {
            ExtendGripper.setPower(extendDirection);
        }
        else if(extendDirection != 0){
            if(extendDirection == 1) ExtendGripper.setPower(0.2);
            else ExtendGripper.setPower(0);
            extendDirection = 0;
        }
        else {
            if(Out) extendDirection = 1;
            else extendDirection = -1;
            extenderTime = time.seconds();
        }
    }

     */

    private void setIngesters(double power){
        IngesterLeft.setPower(power);
        IngesterRight.setPower(power);
    }

    //The following method is code from Team 16072's virtual_robot program. Small changes are only to make it fit our format, the bulk of the method was written by them.
    public void setPower(double px, double py, double pa){ //Multiplied pa by -1 to suit turning requests
        double p1 = -px + py + pa;
        double p2 = px + py + pa;
        double p3 = -px + py - pa;
        double p4 = px + py - pa;
        double max = Math.max(1.0, Math.abs(p1));
        max = Math.max(max, Math.abs(p2));
        max = Math.max(max, Math.abs(p3));
        max = Math.max(max, Math.abs(p4));
        p1 /= max;
        p2 /= max;
        p3 /= max;
        p4 /= max;
        Motors[1].setPower(-p1);
        Motors[2].setPower(-p2);
        Motors[3].setPower(-p3);
        Motors[4].setPower(-p4);
    }
}