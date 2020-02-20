package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp2Configure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

import java.util.logging.Level;

import javax.crypto.Mac;

import static java.lang.Thread.sleep;

public class StatesTeleOpMecanum extends StatesConfigure {

    public int level = 0, nextStack = 3, targetPos = 0; //0: Lowest point, 1: Resting position (under skybridge), 2: clears crossbeam.

    public double GAS, straightGas, sideGas, turnGas, startTime = 0;

    public boolean justCapped = false, manualCap = false, lock = false, overloadPressed = false, overloading = false, manual = false, CapPressed = false, statusPressed = false, Pressed = false, grabbing = false, stacking = false, blockdropped = false, IngestPressed, Capping = false, blockPickedUp;

    public ElapsedTime time;

    public void startTime(){
        time = new ElapsedTime();
    }

    public void Move(HardwareMap ahwMap, Gamepad G1, Gamepad G2) {

        clearBulkCache();

        /*
        DRIVETRAIN MOVEMENTS START HERE
        */

        GAS = 1;
        if (G1.right_bumper) GAS = 0.25; //Quarter speed option
        straightGas = sideGas = turnGas = GAS;
        turnGas = Math.abs(turnGas);

        if (Math.abs(G1.left_stick_y) >= 0.3 && Math.abs(G1.left_stick_x) < 0.3)
            sideGas = 0; //Enables easier direct forward movement

        else if (Math.abs(G1.left_stick_x) >= 0.3 && Math.abs(G1.left_stick_y) < 0.3)
            straightGas = 0; //Enables easier direct sideways movement

        setPower(G1.left_stick_x * sideGas, -G1.left_stick_y * straightGas, -Math.pow(G1.right_stick_x, 3) * turnGas); //Normal move, no bells and whistles here

        /*
        INGESTER STATES
         */

        if (G1.x && !IngestPressed) {
            switch (ingesterStates) {
                case IN:
                    ingester.setPower(0);
                    ingesterStates = Ingester.STOPPEDIN;
                    IngestPressed = true;
                    break;
                case STOPPEDIN:
                    ingester.setPower(0.65);
                    ingesterStates = Ingester.IN;
                    IngestPressed = true;
                    break;
                case STOPPEDOUT:
                    ingester.setPower(-0.3);
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
                    ingester.setPower(0.65);
                    ingesterStates = Ingester.OUT;
                    IngestPressed = true;
                    break;
                case OUT:
                    ingester.setPower(-0.3);
                    ingesterStates = Ingester.IN;
                    IngestPressed = true;
                    break;
                case STOPPEDIN:
                    ingesterStates = Ingester.STOPPEDOUT;
                    IngestPressed = true;
                case STOPPEDOUT:
                    ingesterStates = Ingester.STOPPEDIN;
                    IngestPressed = true;
            }
        } else if (!G1.y && !G1.x) IngestPressed = false;

        /*
        CAPSTONE MODE TOGGLE
         */

        if(G2.a && !CapPressed) {
            switch (stack){
                case NORMAL:
                    stack = Stacking.LOCKING;
                    CapPressed = true;
                    break;
                case LOCKING:
                    stack = Stacking.CAPPING;
                    CapPressed = true;
                    break;
                case CAPPING:
                    stack = Stacking.NORMAL;
                    CapPressed = true;
                    break;
            }
        }
        else if(!G2.a){
            CapPressed = false;
        }

        /*
        FOUNDATION HOOKS TOGGLE
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

        /*
        G2 OVERLOADS FOR SCISSOR AND EXTEND
         */

        if(G2.left_bumper && !overloadPressed){
            if(manual) {
                manual = false;
            }
            else {
                manual = true;
            }
            overloadPressed = true;
        }
        else if(!G2.left_bumper) overloadPressed = false;

        if (!manual) { // LEFT BUMPER BEGINS OVERLOADS
            if(targetPos > ScissorRight.getCurrentPosition() || targetPos > ScissorLeft.getCurrentPosition()) {
                ScissorRight.setPower((targetPos - ScissorRight.getCurrentPosition()) / 30.0);
                ScissorLeft.setPower((targetPos - ScissorLeft.getCurrentPosition()) / 30.0);
            }
            else{
                ScissorRight.setPower((targetPos - ScissorRight.getCurrentPosition()) / 200.0);
                ScissorLeft.setPower((targetPos - ScissorLeft.getCurrentPosition()) / 200.0);
            }
        } else if(manual){
            ScissorRight.setPower(-G2.left_stick_y);
            ScissorLeft.setPower(-G2.left_stick_y);
            ExtendGripper.setPower(-G2.right_stick_y);
        }

        /*
        G2 CHANGES ROBOT MODE
         */

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
                    status = Robot.BALANCED;
                    break;
            }
        }
        else if(!G2.back) statusPressed = false;

        /*
        MACRO STARTS HERE
         */

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
                if(((G1.a || G2.b) && !Pressed) || status == Robot.STACKING || status == Robot.STATIONARY) {
                    raiseToStack();
                    Macro = Macros.LIFTING;
                    Pressed = true;
                }
                else if(G2.dpad_left) {
                    Macro = Macros.RESETTING;
                }
                break;
            case LIFTED:
                if(((G1.a  || G2.b) && !Pressed) || status == Robot.STATIONARY){
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
                if(G1.a || G2.b){
                    targetPos = levels[level = 0];
                    Gripper.setPosition(GRIPPER_OPEN);
                    Macro = Macros.GRABBING;
                    Pressed = true;
                    ingesterStates = Ingester.STOPPEDIN;
                    ingester.setPower(0);
                    grab();
                }
                break;
        }

        if(G2.dpad_up && nextStack < levels.length - 1 && !Pressed){
            nextStack++;
            Pressed = true;
        }
        else if(G2.dpad_down && nextStack > 0 && !Pressed){
            nextStack--;
            Pressed = true;
        }
        else if(G2.right_trigger > 0.1) targetPos = levels[level = nextStack];
        else if (!G2.dpad_up && !G2.dpad_down) Pressed = false;

        if(G2.right_bumper || manualCap) {
            manualCap = true;
            targetPos = levels[level = 2];
            if (Math.abs(ScissorRight.getCurrentPosition() - targetPos) < 50 && Math.abs(ScissorLeft.getCurrentPosition() - targetPos) < 50) {
                cap();
            }
        }
    }

    private void reset(){
        if((level == 1 || level == 2) && ScissorRight.getCurrentPosition() < levels[2] - 200 && ScissorLeft.getCurrentPosition() < levels[2] - 200) {
            targetPos = levels[level = 2];
            return;
        }
        extend(false);
        if(Math.abs(ExtendGripper.getCurrentPosition()) < 50) {
            Gripper.setPosition(GRIPPER_OPEN);
            targetPos = levels[level = 0];
            Macro = Macros.NOACTION;
        }
    }

    private void grab() {
        if(blockPickedUp){
            if(Math.abs(ScissorRight.getCurrentPosition() - targetPos) < 50 && Math.abs(ScissorLeft.getCurrentPosition() - targetPos) < 50) {
                //This statement was having issues when moving down, should be fixed now but may still fail.
                extendRest();
                if(Math.abs(EXTEND_TO_REST - ExtendGripper.getCurrentPosition()) < 100) {
                    //Gripper.setPosition(GRIPPER_LOOSE);
                    targetPos = levels[level = 1];
                    //if(startTime == 0) startTime = time.milliseconds();
                    //if(time.milliseconds() - startTime > 600) {
                    //Gripper.setPosition(GRIPPER_CLOSED);
                    //if(time.milliseconds() - startTime > 1000) {
                    Macro = Macros.GRABBED;
                    startTime = 0;
                    Pressed = false;
                    blockPickedUp = false;
                    ingesterStates = Ingester.IN;
                    ingester.setPower(0.65);
                    //}
                    //}
                }
            }
        }
        else if (Math.abs(ScissorRight.getCurrentPosition()) < 100 && Math.abs(ScissorLeft.getCurrentPosition()) < 100) {
            Gripper.setPosition(GRIPPER_CLOSED);
            if(startTime == 0) startTime = time.milliseconds();
            if(time.milliseconds() - startTime > 350) {
                startTime = 0;
                blockPickedUp = true;
                targetPos = levels[level = 2];
                if(status == Robot.STACKING || status == Robot.STATIONARY) {
                    Macro = Macros.GRABBED;
                    Pressed = false;
                    blockPickedUp = false;
                }
            }
        }
    }

    private void raiseToStack(){
        if(nextStack == 3) { //aka stacking the first block
            targetPos = levels[level = 2];
            if(Math.abs(ScissorLeft.getCurrentPosition() - levels[2]) < 200 && Math.abs(ScissorRight.getCurrentPosition() - levels[2]) < 200) {
                extend(true);
                if(Math.abs(EXTEND_OUT - ExtendGripper.getCurrentPosition()) < 250) {
                    targetPos = levels[level = 3];
                    Pressed = false;
                    Macro = Macros.LIFTED;
                }
            }
            return;
        }
        if(status == Robot.BALANCED){
            targetPos = levels[level = nextStack]-100;
        }
        else {
            targetPos = levels[level = nextStack];
        }
        int heightConfidence = 100;
        if(status == Robot.STATIONARY && nextStack > 6) heightConfidence = 100 + level * 25; //If stationary, arc up/sideways movement.
        if(Math.abs(ScissorLeft.getCurrentPosition() - targetPos) < heightConfidence && Math.abs(ScissorRight.getCurrentPosition() - targetPos) < heightConfidence){
            Pressed = false;
            Macro = Macros.LIFTED;
        }
    }

    private void stack(){
        if(nextStack == 3 && !blockdropped){
            targetPos = levels[level = 3];
            if(Math.abs(ScissorLeft.getCurrentPosition() - targetPos) < 50 && Math.abs(ScissorRight.getCurrentPosition() - targetPos) < 50) {
                Gripper.setPosition(GRIPPER_OPEN);
                if (startTime == 0) startTime = time.milliseconds();
                if (time.milliseconds() - startTime > 500) {
                    targetPos = levels[level = 2];
                    blockdropped = true;
                    startTime = 0;
                }
            }
        }
        else if(blockdropped){
            if(stack == Stacking.CAPPING) cap();
            else {
                if(justCapped) targetPos = levels[level]; //This is untested, if there's an issue tell me
                if(Math.abs(ScissorLeft.getCurrentPosition() - targetPos) < 150 && Math.abs(ScissorRight.getCurrentPosition() - targetPos) < 150) {
                    extend(false);
                    int retractConfidence = 150;
                    if (status == Robot.STATIONARY && level > 5)
                        retractConfidence = 400 + 20 * level;
                    if (Math.abs(ExtendGripper.getCurrentPosition()) < retractConfidence) {
                        targetPos = levels[level = 0];
                        Macro = Macros.NOACTION;
                        blockdropped = false;
                        Pressed = false;
                        if (nextStack < levels.length - 1) nextStack++;
                        justCapped = true;
                    }
                }
            }
        }
        else if(Math.abs(EXTEND_OUT - ExtendGripper.getCurrentPosition()) < 30){
            if(stack == Stacking.LOCKING) {
                targetPos = levels[level = nextStack] - (80 * level);
                return;
            }
            else targetPos = levels[level = nextStack]-100;
            if(Math.abs(ScissorLeft.getCurrentPosition() - targetPos) < 20 && Math.abs(ScissorRight.getCurrentPosition() - targetPos) < 20) {
                Gripper.setPosition(GRIPPER_OPEN);
                if(startTime == 0) startTime = time.milliseconds();
                if(time.milliseconds() - startTime > 500){
                    blockdropped = true;
                    startTime = 0;
                }
            }
        }
    }

    private void cap(){
        extendCap();
        if(!manualCap) targetPos = levels[level] - (level * 100) + 150;
        if(Math.abs(EXTEND_TO_CAP - ExtendGripper.getCurrentPosition()) < 50 && Math.abs(ScissorLeft.getCurrentPosition() - targetPos) < 20 && Math.abs(ScissorRight.getCurrentPosition() - targetPos) < 20) {
            Capstone.setPosition(CAPSTONE_OPEN);
            if(startTime == 0) startTime = time.milliseconds();
            if(time.milliseconds() - startTime > 800) {
                startTime = 0;
                stack = Stacking.NORMAL;
                if(manualCap) extend(false);
                manualCap = false;
                justCapped = true;
            }
        }
    }

    private void extend(boolean Out){
        if(Out) {
            ExtendGripper.setTargetPosition(EXTEND_OUT);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //ExtendGripper.setPositionPIDFCoefficients(10);
            ExtendGripper.setPower(1);
        }
        else {
            ExtendGripper.setTargetPosition(0);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //ExtendGripper.setPositionPIDFCoefficients(15);
            ExtendGripper.setPower(1);
        }
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
        Motors[1].setPower(p1);
        Motors[2].setPower(p2);
        Motors[3].setPower(p3);
        Motors[4].setPower(p4);
    }
}