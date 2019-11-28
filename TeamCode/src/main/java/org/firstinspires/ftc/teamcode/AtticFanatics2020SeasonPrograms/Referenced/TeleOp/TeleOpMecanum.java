package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;

public class TeleOpMecanum extends Configure {

    private int level = 0;
    private int[] levels = {0,-2700, -3100, -4640, -6000, -7500, -9100, -11000, -12900};
    private double Rotation = 0.55;
    private HardwareMap hwMap;

    private boolean ScissorOverload = false, ExtendOverload = false, Pressed = false;

    public void Move(HardwareMap ahwMap, Gamepad G1, Gamepad G2)
    {
        hwMap = ahwMap;

        //if(G1.left_bumper) RampSpeed(G1.left_stick_x, G1.left_stick_y, G1.right_stick_x, 4); //RampSpeed at 1/4 speed

        /*else */if(G1.right_bumper) setPower(G1.left_stick_x/4, G1.left_stick_y/4, G1.right_stick_x/4); //Quarter speed option

        else if(Math.abs(G1.left_stick_y) >= 0.4 && Math.abs(G1.left_stick_x) < 0.4) setPower(0, G1.left_stick_y, G1.right_stick_x); //Enables easier direct forward movement

        else if(Math.abs(G1.left_stick_x) >= 0.4 && Math.abs(G1.left_stick_y) < 0.4) setPower(G1.left_stick_x, 0, G1.right_stick_x); //Enables easier direct sideways movement

        else setPower(G1.left_stick_x, G1.left_stick_y, G1.right_stick_x); //Normal move, no bells and whistles here

        if(G1.dpad_down) {FoundationLeft.setPosition(LEFT_CLOSE); FoundationRight.setPosition(RIGHT_CLOSE);} //Set grabbers down
        else if(G1.dpad_up) {FoundationLeft.setPosition(LEFT_OPEN); FoundationRight.setPosition(RIGHT_OPEN);} //Set grabbers up

        if(G2.y) Gripper.setPosition(GRIPPER_OPEN); //Move claw to not gripped position
        else if(G2.x) Gripper.setPosition(GRIPPER_CLOSED); //Move claw to gripped position

        if(Math.abs(G2.left_trigger) > 0.1) setPower(0, 0, G2.left_trigger/3.0); //Rotate one way
        else if(Math.abs(G2.right_trigger) > 0.1) setPower(0, 0, G2.right_trigger/-3.0);


        if(G2.dpad_left){
            ExtendGripper.setTargetPositionTolerance(50);
            ExtendGripper.setTargetPosition(-3180);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ExtendGripper.setPower(-1);
        }
        else if(G2.dpad_right){
            ExtendGripper.setTargetPositionTolerance(50);
            ExtendGripper.setTargetPosition(0);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ExtendGripper.setPower(1);
        }

        if(G2.dpad_up && (levels.length-1) > level && !Pressed){ //UP ONE LEVEL
            level++;
            setScissorLevel(level);
        }
        else if(G2.dpad_down && G2.left_bumper && level > 0 && !Pressed){ //PRESS LEFT BUMPER AS WELL TO GO DOWN ONE LEVEL
            level--;
            setScissorLevel(level);
        }
        else if(G2.dpad_down && level>0 && !Pressed){ // JUST DPAD DOWN === DOWN TO FOUNDATION LEVEL (1)
            level = 1;
            setScissorLevel(level);
        }
        else if(G2.a && (level == 1) && !Pressed){ // Down, Close, Up
            grabBlock();
        }
        else if(G2.b && (level>1) && !Pressed){ // Down, Open, Up
            dropBlock();
        }
        else if(!G2.dpad_down && !G2.dpad_up && !G2.a && !G2.b) Pressed = false;
        Rotation += 0.015* G2.right_stick_x;
        if(Rotation>1){
            Rotation = 1;
        }
        else if(Rotation <0){
            Rotation = 0;
        }
        RotateGripper.setPosition(Rotation);
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

    private void setScissorLevel(int level){
        Scissor.setTargetPositionTolerance(80);
        Scissor.setTargetPosition(levels[level]);
        Scissor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(Scissor.getTargetPosition() > Scissor.getCurrentPosition()) Scissor.setPower(1);
        else Scissor.setPower(-1);
        Pressed = true;
    }

    private void grabBlock(){
        try {
            Gripper.setPosition(GRIPPER_OPEN);

            setScissorLevel(level = 0);
            Thread.sleep(1300); // give it time to come down

            Gripper.setPosition(GRIPPER_CLOSED);
            Thread.sleep(500); // give it time to close

            setScissorLevel(level = 1);
        }
        catch (InterruptedException ignore){ }
    }

    private void dropBlock(){
        try{
            setScissorLevel(level-1);
            Thread.sleep(1000);

            Gripper.setPosition(GRIPPER_OPEN);

            Thread.sleep(200);
            setScissorLevel(level);
        }
        catch(InterruptedException ignore){ }
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
