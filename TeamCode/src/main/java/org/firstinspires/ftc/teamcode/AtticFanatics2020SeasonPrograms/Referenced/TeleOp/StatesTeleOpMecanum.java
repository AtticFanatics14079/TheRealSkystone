package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp2Configure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

import java.util.logging.Level;

import javax.crypto.Mac;

import static java.lang.Thread.sleep;

public class StatesTeleOpMecanum extends StatesConfigure {

    public int level = 0, nextStack = 3; //0: Lowest point, 1: Resting position (under skybridge), 2: clears crossbeam.

    public double GAS, straightGas, sideGas, turnGas, startTime = 0;

    public boolean CapPressed = false, statusPressed = false, Pressed = false, grabbing = false, stacking = false, blockdropped = false, IngestPressed, Capping = false, blockPickedUp;

    public ElapsedTime time;

    public void startTime(){
        time = new ElapsedTime();
    }

    public void Move(HardwareMap ahwMap, Gamepad G1, Gamepad G2) {

        HardwareMap hwMap = ahwMap;

        clearBulkCache();

        GAS = 1;
        if (G1.right_bumper) GAS = 0.25; //Quarter speed option
        straightGas = sideGas = turnGas = GAS;
        turnGas = Math.abs(turnGas);

        if (Math.abs(G1.left_stick_y) >= 0.3 && Math.abs(G1.left_stick_x) < 0.3)
            sideGas = 0; //Enables easier direct forward movement

        else if (Math.abs(G1.left_stick_x) >= 0.3 && Math.abs(G1.left_stick_y) < 0.3)
            straightGas = 0; //Enables easier direct sideways movement

        setPower(-G1.left_stick_x * sideGas, G1.left_stick_y * straightGas, Math.pow(G1.right_stick_x, 3) * turnGas); //Normal move, no bells and whistles here

        if (G1.x && !IngestPressed) {
            switch (ingesterStates) {
                case IN:
                    ingester.setPower(0);
                    ingesterStates = Ingester.STOPPEDIN;
                    IngestPressed = true;
                    break;
                case STOPPEDIN:
                    ingester.setPower(-0.5);
                    ingesterStates = Ingester.IN;
                    IngestPressed = true;
                    break;
                case STOPPEDOUT:
                    ingester.setPower(0.5);
                    ingesterStates = Ingester.OUT;
                    IngestPressed = true;
                    break;
                case OUT:
                    ingester.setPower(0);
                    ingesterStates = Ingester.STOPPEDOUT;
                    IngestPressed = true;
                    break;
            }
        } else if (G1.y && !IngestPressed) {
            switch (ingesterStates) {
                case IN:
                    ingester.setPower(0.5);
                    ingesterStates = Ingester.OUT;
                    IngestPressed = true;
                    break;
                case OUT:
                    ingester.setPower(-0.5);
                    ingesterStates = Ingester.IN;
                    IngestPressed = true;
                    break;
            }
        } else if (!G1.y && !G1.x) IngestPressed = false;

        if(G2.a && !CapPressed) {
            if(Capping) Capping = false;
            else Capping = true;
            CapPressed = true;
        }
        else if(!G2.a){
            CapPressed = false;
        }

        //TODO: MACROS
        /*
        MANUAL CONTROLS
        START
        HERE
         */

        if (G1.dpad_down) {
            FoundationLeft.setPosition(LEFT_CLOSE);
            FoundationRight.setPosition(RIGHT_CLOSE);
        } //Set grabbers down
        else if (G1.dpad_up) {
            FoundationLeft.setPosition(LEFT_OPEN);
            FoundationRight.setPosition(RIGHT_OPEN);
        } //Set grabbers up

        if (G1.dpad_left) extend(true);
        else if (G1.dpad_right) extend(false);

        //if (G1.a || G2.a) Gripper.setPosition(GRIPPER_CLOSED);
        //else if (G1.b || G2.b) Gripper.setPosition(GRIPPER_OPEN);

        /*if (G2.dpad_up && level <= levels.length - 1 && !Pressed && !grabbing && !stacking) {
            level++;
            Pressed = true;
        } else if (G2.dpad_down && level > 0 && !Pressed && !grabbing && !stacking) {
            level--;
            Pressed = true;
        } else if (!G2.dpad_up && !G2.dpad_down && !grabbing && !stacking) Pressed = false;

         */

        if (!G2.left_bumper) {
            if(levels[level] > ScissorRight.getCurrentPosition() || levels[level] > ScissorLeft.getCurrentPosition()) {
                ScissorRight.setPower((levels[level] - ScissorRight.getCurrentPosition()) / 30.0);
                ScissorLeft.setPower((levels[level] - ScissorLeft.getCurrentPosition()) / 30.0);
            }
            else{
                ScissorRight.setPower((levels[level] - ScissorRight.getCurrentPosition()) / 200.0);
                ScissorLeft.setPower((levels[level] - ScissorLeft.getCurrentPosition()) / 200.0);
            }
        } else {
            ScissorRight.setPower(-G2.left_stick_y);
            ScissorLeft.setPower(-G2.left_stick_y);
            ExtendGripper.setPower(-G2.right_stick_y);
        }

        switch(Macro){
            case RESETTING:
                reset();
                break;
            case GRABBING:
                grab();
                break;
            case STACKING:
                stack();
                break;
            case LIFTING:
                raiseToStack();
                break;
            case GRABBED:
                if((G1.a && !Pressed) || status == Robot.STACKING || status == Robot.STATIONARY) {
                    raiseToStack();
                    Macro = Macros.LIFTING;
                    Pressed = true;
                }
                else if(G2.dpad_left) {
                    Macro = Macros.RESETTING;
                }
                break;
            case LIFTED:
                if((G1.a && !Pressed) || status == Robot.STATIONARY){
                    Macro = Macros.STACKING;
                    blockdropped = false;
                    extend(true);
                    Pressed = true;
                    stack();
                }
                else if(G2.dpad_left) {
                    Macro = Macros.RESETTING;
                }
                break;
            case NOACTION:
                if(G1.a){
                    level = 0;
                    Gripper.setPosition(GRIPPER_OPEN);
                    Macro = Macros.GRABBING;
                    Pressed = true;
                    ingesterStates = Ingester.STOPPEDIN;
                    ingester.setPower(0);
                    grab();
                }
                else if(G2.dpad_up && nextStack < levels.length - 1 && !Pressed){
                    nextStack++;
                    Pressed = true;
                }
                else if(G2.dpad_down && nextStack > 0 && !Pressed){
                    nextStack--;
                    Pressed = true;
                }
                else if(G2.right_trigger > 0.1) level = nextStack;
                else if (!G2.dpad_up && !G2.dpad_down) Pressed = false;
                break;
        }

        if(G2.x) Gripper.setPosition(GRIPPER_CLOSED);
        else if(G2.y) Gripper.setPosition(GRIPPER_OPEN);

        if(G2.back && !statusPressed) {
            statusPressed = true;
            switch (status){
                case BALANCED:
                    status = Robot.STACKING;
                    break;
                case STACKING:
                    status = Robot.STATIONARY;
                    break;
                case STATIONARY:
                    status = Robot.SHUTTLING;
                    break;
                case SHUTTLING:
                    status = Robot.BALANCED;
                    break;
            }
        }
        else if(!G2.back) statusPressed = false;
    }

    private void reset(){
        extend(false);
        Gripper.setPosition(GRIPPER_OPEN);
        if(Math.abs(ExtendGripper.getCurrentPosition() - 10) < 50) {
            level = 0;
            Macro = Macros.NOACTION;
        }
    }

    private void grab() {
        if(blockPickedUp){
            if(Math.abs(ScissorRight.getCurrentPosition() - levels[level]) < 10 && Math.abs(ScissorLeft.getCurrentPosition() - levels[level]) < 10) {
                //This statement was having issues when moving down, should be fixed now but may still fail.
                extendRest();
                if(Math.abs(EXTEND_TO_REST - ExtendGripper.getCurrentPosition()) < 100) {
                    Gripper.setPosition(GRIPPER_LOOSE);
                    level = 1;
                    if(startTime == 0) startTime = time.milliseconds();
                    if(time.milliseconds() - startTime > 800) {
                        Gripper.setPosition(GRIPPER_CLOSED);
                        if(time.milliseconds() - startTime > 1600) {
                            Macro = Macros.GRABBED;
                            startTime = 0;
                            Pressed = false;
                            blockPickedUp = false;
                            ingesterStates = Ingester.IN;
                            ingester.setPower(0.5);
                        }
                    }
                }
            }
        }
        else if (Math.abs(ScissorRight.getCurrentPosition() - levels[0]) < 100 && Math.abs(ScissorLeft.getCurrentPosition() - levels[0]) < 100) {
            Gripper.setPosition(GRIPPER_CLOSED);
            if(startTime == 0) startTime = time.milliseconds();
            if(time.milliseconds() - startTime > 1500) {
                startTime = 0;
                blockPickedUp = true;
                level = 2;
                if(status == Robot.STACKING || status == Robot.STATIONARY) {
                    Macro = Macros.GRABBED;
                    Pressed = false;
                    blockPickedUp = false;
                    ingesterStates = Ingester.IN;
                    ingester.setPower(0.5);
                }
            }
        }
    }

    private void resetScissor(){
        ScissorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ScissorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ScissorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ScissorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        level = 0;
    }

    private void raiseToStack(){
        if(nextStack == 3) { //aka stacking the first block
            level = 2;
            Pressed = false;
            Macro = Macros.LIFTED;
            return;
        }
        level = nextStack + 1;
        if(Math.abs(ScissorLeft.getCurrentPosition() - levels[level]) < 100 && Math.abs(ScissorRight.getCurrentPosition() - levels[level]) < 100){
            Pressed = false;
            Macro = Macros.LIFTED;
        }
    }

    private void stack(){
        if(nextStack == 3 && !blockdropped){
            extend(true);
            if(Math.abs(EXTEND_OUT - ExtendGripper.getCurrentPosition()) < 50) {
                level = 1;
                if(Math.abs(ScissorLeft.getCurrentPosition() - levels[level]) < 50 && Math.abs(ScissorRight.getCurrentPosition() - levels[level]) < 50) {
                    Gripper.setPosition(GRIPPER_OPEN);
                    if (startTime == 0) startTime = time.milliseconds();
                    if (time.milliseconds() - startTime > 1000) {
                        level = 2;
                        blockdropped = true;
                        startTime = 0;
                    }
                }
            }
        }
        else if(blockdropped){
            if(Capping) cap();
            else {
                extend(false);
                if(Math.abs(ExtendGripper.getCurrentPosition()) < 110) {
                    level = 0;
                    Macro = Macros.NOACTION;
                    blockdropped = false;
                    Pressed = false;
                    if(nextStack < levels.length - 2) nextStack++;
                }
            }
        }
        else if(Math.abs(EXTEND_OUT - ExtendGripper.getCurrentPosition()) < 150){
            level = nextStack;
            if(Math.abs(ScissorLeft.getCurrentPosition() - levels[level]) < 50 && Math.abs(ScissorRight.getCurrentPosition() - levels[level]) < 50) {
                Gripper.setPosition(GRIPPER_OPEN);
                if(startTime == 0) startTime = time.milliseconds();
                if(time.milliseconds() - startTime > 1000){
                    blockdropped = true;
                    startTime = 0;
                }
            }
        }
    }

    private void cap(){
        extendCap();
        if(Math.abs(EXTEND_TO_CAP - ExtendGripper.getCurrentPosition()) < 150) {
            Capstone.setPosition(CAPSTONE_OPEN);
            if(startTime == 0) startTime = time.milliseconds();
            if(time.milliseconds() - startTime > 1000) {
                startTime = 0;
                Capping = false;
                extend(false);
            }
        }
    }

    private void extend(boolean Out){
        if(Out) ExtendGripper.setTargetPosition(EXTEND_OUT);
        else ExtendGripper.setTargetPosition(10);
        ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ExtendGripper.setPower(1);
    }

    private void extendCap(){
        ExtendGripper.setTargetPosition(EXTEND_TO_CAP);
        ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ExtendGripper.setPower(1);
    }

    private void extendRest(){
        ExtendGripper.setTargetPosition(EXTEND_TO_REST);
        ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ExtendGripper.setPower(1);
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