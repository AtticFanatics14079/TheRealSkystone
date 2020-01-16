package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Comp2Configure;

public class Comp2TeleOpMecanum extends Comp2Configure {

    private int level = 0;
    private HardwareMap hwMap;

    private double GAS, straightGas, sideGas, turnGas, extenderTime, extendDirection = 0;

    private boolean ScissorOverload = false, ExtendOverload = false, Pressed = false, IngesterOut = false, StopIngester = false, IngesterPressed = false;

    ElapsedTime time;

    public void Move(HardwareMap ahwMap, Gamepad G1, Gamepad G2)
    {
        hwMap = ahwMap;

        if(time == null) time = new ElapsedTime();

        /*if(G2.a) setIngesters(0);
        else if(G2.b) setIngesters(-1);
        else setIngesters(1);
         */

        //if(G1.left_bumper) RampSpeed(G1.left_stick_x, G1.left_stick_y, G1.right_stick_x, 4); //RampSpeed at 1/4 speed

        GAS = 1;

        /*else */if(G1.right_bumper) GAS = 0.25; //Quarter speed option

        if(G2.x) GAS = -GAS; //Inverted movement

        straightGas = sideGas = turnGas = GAS;

        turnGas = Math.abs(turnGas);

        if(Math.abs(G1.left_stick_y) >= 0.3 && Math.abs(G1.left_stick_x) < 0.3) sideGas = 0; //Enables easier direct forward movement

        else if(Math.abs(G1.left_stick_x) >= 0.3 && Math.abs(G1.left_stick_y) < 0.3) straightGas = 0; //Enables easier direct sideways movement

        setPower(-G1.left_stick_x * sideGas, G1.left_stick_y * straightGas, Math.pow(G1.right_stick_x, 3) * turnGas); //Normal move, no bells and whistles here

        if(G1.dpad_down) {FoundationLeft.setPosition(LEFT_CLOSE); FoundationRight.setPosition(RIGHT_CLOSE);} //Set grabbers down
        else if(G1.dpad_up) {FoundationLeft.setPosition(LEFT_OPEN); FoundationRight.setPosition(RIGHT_OPEN);} //Set grabbers up

        if(G1.a) Gripper.setPosition(GRIPPER_CLOSED);
        else if(G1.b) Gripper.setPosition(GRIPPER_OPEN);

        //if(G2.left_trigger != 0) Gripper.setPosition(0.5);
        //else if(G2.y) Gripper.setPosition(Gripper.getPosition() + 0.01); //Gradual movement for recalibration. NOTE: The servo yeets itself if it is not at a position before moving.
        //else if(G2.x) Gripper.setPosition(Gripper.getPosition() - 0.01); //See above
        //if(G2.y) Gripper.setPosition(GRIPPER_OPEN); //Move claw to not gripped position
        //else if(G2.x) Gripper.setPosition(GRIPPER_CLOSED); //Move claw to gripped position

        if(G1.x){
            ScissorLeft.setPower(-1);
            ScissorRight.setPower(-1);
        }
        else if(G1.y){
            ScissorLeft.setPower(1);
            ScissorRight.setPower(1);
        }
        else {
            ScissorLeft.setPower(0);
            ScissorRight.setPower(0);
        }
        if(IngesterOut){
            if(!G2.right_bumper && !G2.left_bumper && IngesterPressed) IngesterPressed = false;
            else if(G2.right_bumper && !IngesterPressed) {IngesterOut = false; IngesterPressed = true;}
            else if(G2.left_bumper && !IngesterPressed){IngesterOut = false; StopIngester = true; IngesterPressed = true;}
            setIngesters(0.5);
        }
        else if(StopIngester){
            if(!G2.left_bumper && !G2.right_bumper && IngesterPressed) IngesterPressed = false;
            else if(G2.left_bumper && !IngesterPressed) {StopIngester = false; IngesterPressed = true;}
            else if(G2.right_bumper && !IngesterPressed){StopIngester = false; IngesterOut = true; IngesterPressed = true;}
            setIngesters(0);
        }
        else{
            if(!G2.left_bumper && !G2.right_bumper && IngesterPressed) IngesterPressed = false;
            else if(G2.left_bumper && !IngesterPressed) {StopIngester = true; IngesterPressed = true;}
            else if(G2.right_bumper && !IngesterPressed) {IngesterOut = true; IngesterPressed = true;}
            setIngesters(-0.5);
        }

        if(!G1.left_bumper){
            //if(G1.dpad_left) Extend(true);
            //else if(G1.dpad_right) Extend(false);
            ExtendGripper.setPower(0);
        }
        else{
            if(G1.dpad_left) ExtendGripper.setPower(1);
            else if(G1.dpad_right) ExtendGripper.setPower(-1);
            else ExtendGripper.setPower(0);
        }
        //else ExtendGripper.setPower(0);

        /*if(G2.left_trigger > 0.5 && G2.dpad_left) {
            ExtendGripper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ExtendGripper.setPower(1);
        }
        else if(G2.left_trigger > 0.5 && G2.dpad_right) {
            ExtendGripper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ExtendGripper.setPower(-1);
        }
        else if(G2.left_trigger > 0.5) ExtendGripper.setPower(0);
        else if(G2.right_trigger > 0.5){
            ExtendGripper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else if(G2.dpad_left){
            ExtendGripper.setTargetPositionTolerance(50);
            ExtendGripper.setTargetPosition(EXTENDED);
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
        else if(G2.dpad_down && level>1 && !Pressed){ // JUST DPAD DOWN === DOWN TO FOUNDATION LEVEL (2)
            level = 2;
            setScissorLevel(level);
        }
        else if(G2.a && !Pressed){ // Down, Close, Up
            grabBlock();
        }
        else if(G2.b && (level>2) && !Pressed){ // Down, Open, Up
            dropBlock();
        }
        else if(G2.left_trigger > 0.5 && G2.dpad_up) {
            Scissor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Scissor.setPower(-1);
        }
        else if(G2.left_trigger > 0.5 && G2.dpad_down) {
            Scissor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Scissor.setPower(1);
        }
        else if(G2.left_trigger > 0.5) Scissor.setPower(0);
        else if(G2.right_trigger > 0.5){
            Scissor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Scissor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else if(!G2.dpad_down && !G2.dpad_up && !G2.a && !G2.b) Pressed = false;

        //Rotation += 0.015* G2.right_stick_x;

        if(Rotation > 1){
            Rotation = 1;
        }
        else if(Rotation < 0){
            Rotation = 0;
        }
        RotateGripper.setPosition(Rotation);

         */
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
        ScissorLeft.setTargetPositionTolerance(100);
        ScissorRight.setTargetPositionTolerance(100);
        ScissorLeft.setTargetPosition(levels[level]);
        ScissorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(ScissorLeft.getTargetPosition() > ScissorLeft.getCurrentPosition()) {
            ScissorLeft.setPower(1);
            ScissorRight.setPower(1);
        }
        else {
            ScissorLeft.setPower(-1);
            ScissorRight.setPower(-1);
        }
        Pressed = true; //Can take out this line if we are using a new system, everything else should be essential.
    }

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

    /*private void grabBlock(){
        try {
            setPower(0, 0, 0);

            setScissorLevel(level = 0);
            while(Scissor.getCurrentPosition() < -3000);
            Gripper.setPosition(GRIPPER_OPEN);
            while(Scissor.isBusy());

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
