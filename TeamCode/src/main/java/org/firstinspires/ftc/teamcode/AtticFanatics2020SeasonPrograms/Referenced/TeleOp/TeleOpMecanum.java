package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;

public class TeleOpMecanum extends Configure {

    /*
    NONE OF THESE ARE BEING USED
        private final double INPUT_RAMP_DIFF = 0.003; //Increase per cycle of speed during RampSpeed
        private final double TICKS_PER_LEVEL = 100;
        private final double LEVEL_0 = 0; //Level to grab block
        private final double LEVEL_1 = 0; //Level to be above block (First layer on foundation)
        private final double LEVEL_2 = 0; //Level 2 on foundation
        private final double SCISSOR_LIMIT = 9000;
        private final double EXTEND_LIMIT_FORWARD = 50000;  //Declare equal to the limits the extender should be extended to
     */

    private int level = 0;
    private int[] levels = {0,-2700, -3100};
    //Pos0=0, Pos1=2100, Pos3100

    private HardwareMap hwMap;

    private boolean ScissorOverload = false, ExtendOverload = false, Pressed = false;

    public double RampDiff = 0.2;

    private int ScissorLevel = 500;

    public void Move(HardwareMap ahwMap, Gamepad G1, Gamepad G2)
    {
        hwMap = ahwMap;

        //GAMEPAD 1 STUFF HERE

        /*
            Used: LeftStick, RightStick, RightBumper, DpadUp, DpadDown
         */

        //if(G1.left_bumper) RampSpeed(G1.left_stick_x, G1.left_stick_y, G1.right_stick_x, 4); //RampSpeed at 1/4 speed

        /*else */if(G1.right_bumper) setPower(G1.left_stick_x/4, G1.left_stick_y/4, G1.right_stick_x/4); //Quarter speed option

        else if(Math.abs(G1.left_stick_y) >= 0.4 && Math.abs(G1.left_stick_x) < 0.4) setPower(0, G1.left_stick_y, G1.right_stick_x); //Enables easier direct forward movement

        else if(Math.abs(G1.left_stick_x) >= 0.4 && Math.abs(G1.left_stick_y) < 0.4) setPower(G1.left_stick_x, 0, G1.right_stick_x); //Enables easier direct sideways movement

        else setPower(G1.left_stick_x, G1.left_stick_y, G1.right_stick_x); //Normal move, no bells and whistles here

        if(G1.dpad_down) {FoundationLeft.setPosition(LEFT_CLOSE); FoundationRight.setPosition(RIGHT_CLOSE);} //Set grabbers down
        else if(G1.dpad_up) {FoundationLeft.setPosition(LEFT_OPEN); FoundationRight.setPosition(RIGHT_OPEN);} //Set grabbers up


        //GAMEPAD 2 STUFF STARTS HERE

        /*
            Used: A, B, LeftTrigger, RightTrigger, RightStick, DPad Left/Right/Up/Down, LeftBumper+DpadDown, LeftBumper+RightBumper
         */

        if(G2.b) Gripper.setPosition(GRIPPER_OPEN); //Move claw to not gripped position
        else if(G2.a) Gripper.setPosition(GRIPPER_CLOSED); //Move claw to gripped position

        if(Math.abs(G2.left_trigger) > 0.1) setPower(0, 0, G2.left_trigger/2.0); //Rotate one way
        else if(Math.abs(G2.right_trigger) > 0.1) setPower(0, 0, G2.right_trigger/-2.0);

        if(G2.dpad_left){
            ExtendGripper.setTargetPositionTolerance(20);
            ExtendGripper.setTargetPosition(-3180);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ExtendGripper.setPower(-1);
        }
        else if(G2.dpad_right){
            ExtendGripper.setTargetPositionTolerance(20);
            ExtendGripper.setTargetPosition(0);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ExtendGripper.setPower(1);
        }

        if(G2.dpad_up && (levels.length-1) > level && !Pressed){
            //UP ONE LEVEL
            Scissor.setTargetPositionTolerance(20);
            level++;
            Scissor.setTargetPosition(levels[level]);
            Scissor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Scissor.setPower(-1);
            Pressed = true;
        }
        else if(G2.dpad_down && G2.left_bumper && level > 0 && !Pressed){
            //PRESS LEFT BUMPER AS WELL TO GO DOWN ONE LEVEL
            Scissor.setTargetPositionTolerance(20);
            level--;
            if(level !=0) { // don't do it if doing to lowest level
                Scissor.setPower(1);
                while (Scissor.getCurrentPosition() > (levels[level] - 300)) {
                    //Overshoot the target position by 300 ticks
                }
                Scissor.setPower(0);
            }
            Scissor.setTargetPosition(levels[level]); // return to desired position
            Scissor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Scissor.setPower(1);
            Pressed = true;
        }
        else if(G2.dpad_down && level>0 && !Pressed){
            // JUST DPAD DOWN === DOWN TO FOUNDATION LEVEL (1)
            Scissor.setTargetPositionTolerance(20);
            level = 1;
            Scissor.setTargetPosition(levels[level]); // return to desired position
            Scissor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Scissor.setPower(1);
            Pressed = true;
        }
        else if(G2.left_bumper && G2.right_bumper && level == 1){
            //Subroutine: down, close, up
            //MUST BE AT LEVEL 1
            Scissor.setTargetPositionTolerance(20);
            Scissor.setPower(1);
            while(Scissor.getCurrentPosition() < (levels[0]-50)){
                //Encoder should be increasing
                //waiting to go down
            }
            Scissor.setPower(0);

            Gripper.setPosition(GRIPPER_CLOSED);

            Scissor.setTargetPosition(levels[1]);
            Scissor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Scissor.setPower(-1);
        }
        else if(!G2.dpad_down && !G2.dpad_up) Pressed = false;

        /*if(G2.left_bumper) {
            ScissorOverload = true;
            if(G2.dpad_up) Scissor.setPower(-1);
            else if(G2.dpad_down) Scissor.setPower(1);
            else Scissor.setPower(0);
        }
        else if(ScissorOverload) {Scissor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); Scissor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); ScissorOverload = false;}

        if(G2.dpad_up) Scissor.setPower(-1);
        else if(G2.dpad_down) Scissor.setPower(1);
        else Scissor.setPower(0);
        */
        if(Math.abs(G2.right_stick_x) > 0.1) RotateGripper.setPosition(RotateGripper.getPosition() + 0.01 * G2.right_stick_x);
        /*if(Scissor.getCurrentPosition() - ScissorLevel * TICKS_PER_LEVEL > 100) Scissor.setPower(1);
        else if(Scissor.getCurrentPosition() - ScissorLevel * TICKS_PER_LEVEL < 100) Scissor.setPower(-1);
        else Scissor.setPower(0);
         */

        //Add other
    }

    /*public void RampSpeed(double px, double py, double pa, double SpeedDiv)
    {
        SetZeroBehavior(false, hwMap);

        if(px == 0 && py == 0 && pa == 0)
        {
            setPower(0, 0, 0);
            RampDiff = INPUT_RAMP_DIFF;
            return;
        }
        setPower((px * RampDiff)/SpeedDiv, (py * RampDiff) / SpeedDiv, (pa * RampDiff) / SpeedDiv);
        if(RampDiff < 1)
            RampDiff += INPUT_RAMP_DIFF;
    }
     */

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
