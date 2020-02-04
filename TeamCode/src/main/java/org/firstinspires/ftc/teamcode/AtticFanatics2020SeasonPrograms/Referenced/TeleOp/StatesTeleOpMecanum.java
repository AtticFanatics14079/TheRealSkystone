package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp2Configure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

import static java.lang.Thread.sleep;

public class StatesTeleOpMecanum extends StatesConfigure {
    public int level = 0, nextStack = 1; //0: Pick up Block from inside robot. 1: Above level 1

    private double GAS, straightGas, sideGas, turnGas, startTime = 0;

    private boolean Pressed = false, grabbing = false, stacking = false, blockdropped = false;

    public ElapsedTime time;

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

        if (G1.x) { //
            switch (ingesterStates) {
                case IN:
                    ingester.setPower(0);
                    ingesterStates = Ingester.STOPPEDIN;
                    break;
                case STOPPEDIN:
                    ingester.setPower(-0.5);
                    ingesterStates = Ingester.IN;
                    break;
                case STOPPEDOUT:
                    ingester.setPower(0.5);
                    ingesterStates = Ingester.OUT;
                    break;
                case OUT:
                    ingester.setPower(0);
                    ingesterStates = Ingester.STOPPEDOUT;
                    break;
            }
        } else if (G1.y) {
            switch (ingesterStates) {
                case IN:
                    ingester.setPower(0.5);
                    ingesterStates = Ingester.OUT;
                    break;
                case OUT:
                    ingester.setPower(-0.5);
                    ingesterStates = Ingester.IN;
                    break;
            }
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

        if (Math.abs(G2.left_stick_y) < 0.3) {
            ScissorRight.setPower((levels[level] - ScissorRight.getCurrentPosition()) / 100.0 + 0.05);
            ScissorLeft.setPower((levels[level] - ScissorLeft.getCurrentPosition()) / 100.0 + 0.05);
        } else if (!grabbing && !stacking) {
            ScissorRight.setPower(-G2.left_stick_y);
            ScissorLeft.setPower(-G2.left_stick_y);
        }

        switch(Macro){
            case GRABBING:
                grab();
                break;
            case STACKING:
                stack();
                break;
            case GRABBED:
                if(G1.a && !Pressed) {
                    raiseToStack();
                    Macro = Macros.LIFTED;
                    Pressed = true;
                }
                else if(G2.a) Macro = Macros.NOACTION;
                break;
            case LIFTED:
                if(G1.a && !Pressed){
                    Macro = Macros.STACKING;
                    blockdropped = false;
                    extend(true);
                    Pressed = true;
                    stack();
                }
                break;
            case NOACTION:
                if(G1.a){
                    level = 0;
                    Gripper.setPosition(GRIPPER_OPEN);
                    Macro = Macros.GRABBING;
                    Pressed = true;
                    grab();
                }
                else if(G2.dpad_up && level < levels.length - 1 && !Pressed){
                    nextStack++;
                    Pressed = true;
                }
                else if(G2.dpad_down && level > 0 && !Pressed){
                    nextStack--;
                    Pressed = true;
                }
                else if (!G2.dpad_up && !G2.dpad_down && !grabbing && !stacking) Pressed = false;
                break;
        }

        /*if (grabbing) grab();
        else if (stacking) stack();
        else if (G1.a) {
            level = 0;
            Gripper.setPosition(GRIPPER_OPEN);
            grabbing = true;
            grab();
        } else if (G1.b) {
            stacking = true;
            blockdropped = false;
            extend(true);
            stack();
        } else if (G1.right_trigger != 0) raiseToStack();
        
         */

        /*
        G2
        OVERLOADS
        END
        HERE
        */

    }

    private void grab() {
        if (Math.abs(ScissorRight.getCurrentPosition() - levels[0]) < 50 && Math.abs(ScissorLeft.getCurrentPosition() - levels[0]) < 100) {
            Gripper.setPosition(GRIPPER_CLOSED);
            if(startTime == 0) startTime = time.milliseconds();
            if(time.milliseconds() - startTime > 1200) {
                level = 1;
                Macro = Macros.GRABBED;
                startTime = 0;
                Pressed = false;
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

    private void raiseToStack(){ level = nextStack + 1; }

    private void stack(){
        if(blockdropped){
            level = nextStack + 1;
            if(Math.abs(ScissorLeft.getCurrentPosition() - levels[level]) < 50 && Math.abs(ScissorRight.getCurrentPosition() - levels[level]) < 50) {
                extend(false);
                level = 1;
                Macro = Macros.NOACTION;
                blockdropped = false;
                Pressed = false;
                nextStack++;
            }
        }
        else if(Math.abs(EXTEND_OUT - ExtendGripper.getCurrentPosition()) <150){
            level = nextStack;
            if(Math.abs(ScissorLeft.getCurrentPosition() - levels[level]) < 150 && Math.abs(ScissorRight.getCurrentPosition() - levels[level]) < 50) {
                Gripper.setPosition(GRIPPER_OPEN);
                if(startTime == 0) startTime = time.milliseconds();
                if(time.milliseconds() - startTime > 1200){
                    blockdropped = true;
                    startTime = 0;
                }
            }
        }
    }

    private void extend(boolean Out){
        if(Out) ExtendGripper.setTargetPosition(EXTEND_OUT);
        else ExtendGripper.setTargetPosition(10);
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