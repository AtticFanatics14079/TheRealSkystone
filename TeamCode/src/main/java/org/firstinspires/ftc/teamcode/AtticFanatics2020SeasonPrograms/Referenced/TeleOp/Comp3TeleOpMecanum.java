package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

import java.util.ArrayList;

public class Comp3TeleOpMecanum extends StatesConfigure {

    public int level = 0, currentArrayListPosition = -1, prevTime; //0: Pick up Block from inside robot. 1: Above level 1
    private HardwareMap hwMap;
    private ArrayList<double[]> drivetrainRecords = new ArrayList<double[]>();

    private double GAS, straightGas, sideGas, turnGas, startExtend, extendDirection = 0, runbackTime, runbackStart;

    public boolean runningBack = false;
    private boolean Extending = false, Pressed = false, IngesterOut = false, StopIngester = false, IngesterPressed = false;
    ElapsedTime time, startTime;

    public void Move(HardwareMap ahwMap, Gamepad G1, Gamepad G2) {
        hwMap = ahwMap;

        if (time == null) time = new ElapsedTime();
        if (startTime == null) startTime = new ElapsedTime();

        /*if(G2.a) setIngesters(0);
        else if(G2.b) setIngesters(-1);
        else setIngesters(1);
         */

        GAS = 1;

        if (G1.right_bumper) GAS = 0.25; //Quarter speed option

        straightGas = sideGas = turnGas = GAS;

        turnGas = Math.abs(turnGas);

        if (Math.abs(G1.left_stick_y) >= 0.3 && Math.abs(G1.left_stick_x) < 0.3)
            sideGas = 0; //Enables easier direct forward movement

        else if (Math.abs(G1.left_stick_x) >= 0.3 && Math.abs(G1.left_stick_y) < 0.3)
            straightGas = 0; //Enables easier direct sideways movement

        /*if(runningBack) {
            if(Math.abs(Motors[1].getCurrentPosition() + Motors[2].getCurrentPosition() + Motors[3].getCurrentPosition() + Motors[4].getCurrentPosition())/4.0 - 50 <= 0) {
                setPower(0, 0, 0);
                resetMotorEncoders();
                runningBack = false;
            }
            else System.out.println("yate");
        }
        else if(G1.start) {
            resetMotorEncoders();
        }
        else if(G1.back){
            setTargetPosition(0);
            double temp;
            Motors[1].setPower(1 * ((temp = Motors[1].getCurrentPosition()) / Math.abs(temp)));
            Motors[2].setPower(1 * ((temp = Motors[2].getCurrentPosition()) / Math.abs(temp)));
            Motors[3].setPower(1 * ((temp = Motors[3].getCurrentPosition()) / Math.abs(temp)));
            Motors[4].setPower(1 * ((temp = Motors[4].getCurrentPosition()) / Math.abs(temp)));
            runningBack = true;
        }
        else if(G2.start){
            if(currentArrayListPosition == -1) {
                currentArrayListPosition = drivetrainRecords.size() - 2;
                runbackStart = time.milliseconds();
            }
            if(drivetrainRecords.get(currentArrayListPosition)[0] - runbackTime >  1){
                Motors[1].setPower(-drivetrainRecords.get(currentArrayListPosition)[1]);
                Motors[2].setPower(-drivetrainRecords.get(currentArrayListPosition)[2]);
                Motors[3].setPower(-drivetrainRecords.get(currentArrayListPosition)[3]);
                Motors[4].setPower(-drivetrainRecords.get(currentArrayListPosition)[4]);
                currentArrayListPosition--;
            }
            runbackTime = runbackStart - (time.milliseconds() - runbackStart);
        }
        else {
            currentArrayListPosition = -1;
            setPower(-G1.left_stick_x * sideGas, G1.left_stick_y * straightGas, Math.pow(G1.right_stick_x, 3) * turnGas); //Normal move, no bells and whistles here
        }
         */

        setPower(-G1.left_stick_x * sideGas, G1.left_stick_y * straightGas, Math.pow(G1.right_stick_x, 3) * turnGas); //Normal move, no bells and whistles here

        if (G1.dpad_down) {
            FoundationLeft.setPosition(LEFT_CLOSE);
            FoundationRight.setPosition(RIGHT_CLOSE);
        } //Set grabbers down
        else if (G1.dpad_up) {
            FoundationLeft.setPosition(LEFT_OPEN);
            FoundationRight.setPosition(RIGHT_OPEN);
        } //Set grabbers up


        //if (G1.a || G2.a) Gripper.setPosition(GRIPPER_CLOSED);
        //else if (G1.b || G2.b) Gripper.setPosition(GRIPPER_OPEN);


        if (IngesterOut) {
            if (!G1.y && !G2.left_bumper && IngesterPressed) IngesterPressed = false;
            else if (G1.y && !IngesterPressed) {
                IngesterOut = false;
                IngesterPressed = true;
            } else if (G2.left_bumper && !IngesterPressed) {
                IngesterOut = false;
                StopIngester = true;
                IngesterPressed = true;
            }
            setIngesters(0.5);
        } else if (StopIngester) {
            if (!G1.x && !G1.y && IngesterPressed) IngesterPressed = false;
            else if (G1.x && !IngesterPressed) {
                StopIngester = false;
                IngesterPressed = true;
            } else if (G1.y && !IngesterPressed) {
                StopIngester = false;
                IngesterOut = true;
                IngesterPressed = true;
            }
            setIngesters(0);
        } else {
            if (!G1.x && !G1.y && IngesterPressed) IngesterPressed = false;
            else if (G1.x && !IngesterPressed) {
                StopIngester = true;
                IngesterPressed = true;
            } else if (G1.y && !IngesterPressed) {
                IngesterOut = true;
                IngesterPressed = true;
            }
            setIngesters(-0.5);
        }

        if((int)time.milliseconds() - prevTime >= 1) {
            double[] tempVals;
            tempVals = new double[]{time.milliseconds(), Motors[1].getPower(), Motors[2].getPower(), Motors[3].getPower(), Motors[4].getPower()};
            drivetrainRecords.add(tempVals);
            System.out.println("yeet");
            prevTime = (int)time.milliseconds();
        }

        //TODO: MACROS
        /*
        G2
        OVERLOADS
        START
        HERE
         */
        //ScissorRight.setPower(-G2.left_stick_y);
        //ScissorLeft.setPower(-G2.left_stick_y);
        //ExtendGripper.setPower(-(G2.right_stick_y)/2);

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
        ScissorRight.setPower(1);
        ScissorLeft.setPower(1);
    }
    private void resetScissor(){
        ScissorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ScissorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        level = 0;
    }

    private void Extend(boolean Out){
        startTime.reset();

    }



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