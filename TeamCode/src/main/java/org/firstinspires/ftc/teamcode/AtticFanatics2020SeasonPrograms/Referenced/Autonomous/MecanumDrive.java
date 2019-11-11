package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;


//This Class contains SetPower, MoveEncoderTicks, and TurnDegrees, for MECANUM

public class MecanumDrive extends Configure {

    float HeadingAdjust, CurrentOrientation;

    static final double TICKS_PER_CM = 16;
    static final double TICKS_PER_DEGREE = 8.5;
    //static final double INERTIA_TICKS = 100;
    static final double TURN_OFFSET = 10;
    static final double IMU_OFFSET = 5;

    static final double LEFT_OPEN = 1;
    static final double LEFT_CLOSE = 0;
    static final double RIGHT_OPEN = 1;
    static final double RIGHT_CLOSE = 0;


    boolean Configured = false;

    public void UnhookFoundation(HardwareMap ahwMap){
        FoundationLeft.setPosition(LEFT_OPEN);
        FoundationRight.setPosition(RIGHT_OPEN);
    }
    public void HookFoundation(HardwareMap ahwMap){
        FoundationLeft.setPosition(LEFT_CLOSE);
        FoundationRight.setPosition(RIGHT_CLOSE);
    }

    public void MoveScissor (int level, HardwareMap ahwMap){

        Scissor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Scissor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int ticks = 1;
        switch (level){
            case 1: ticks = 1; break;
            case 2: break;
        }
        Scissor1.setTargetPosition(ticks);
        Scissor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Scissor1.setPower(1);
        while(Scissor1.isBusy()){

        }
        Scissor1.setPower(0);
    }

    public void ExtendGripper(double NumbCm,  HardwareMap hardmap){

        ResetMotorEncoders(hardmap);
        double Ticks = TICKS_PER_CM * NumbCm;
        ExtendGripper.setPower(1);
        ExtendGripper.setTargetPosition((int)Ticks);
        while(ExtendGripper.isBusy()){

        }
        ExtendGripper.setPower(0);
    }

    public void GrabDrop(boolean open, HardwareMap hardmap){
        if(open){
            Gripper.setPosition(.6);
        }
        else if(!open){
            Gripper.setPosition(.1);
        }
    }



    public void MoveEncoderTicks(double NumbCM, double ForwardPower, HardwareMap ahwMap) { //POSITIVE POWER IS FORWARD!!!

        ResetMotorEncoders(ahwMap);

        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_CM * NumbCM;

        Motors[1].setTargetPosition((int)Ticks);
        Motors[2].setTargetPosition((int)Ticks);
        Motors[3].setTargetPosition((int)Ticks);
        Motors[4].setTargetPosition((int)Ticks);

        RunToPosition(ahwMap);

        //HeadingAdjust = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        //for(double Counter = 0.2; Counter <= 1; Counter += 0.2)
        setPower(0, ForwardPower, 0);


        while((Motors[1].isBusy() || Motors[2].isBusy()) && (Motors[3].isBusy() || Motors[4].isBusy())) {

        }

        setPower(0,0,0);
    }

    public void MoveSetTolerance(double NumbCM, double ForwardPower, HardwareMap ahwMap) { //POSITIVE POWER IS FORWARD!!!

        ResetMotorEncoders(ahwMap);

        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_CM * NumbCM;

        Motors[1].setTargetPosition((int)Ticks);
        Motors[2].setTargetPosition((int)Ticks);
        Motors[3].setTargetPosition((int)Ticks);
        Motors[4].setTargetPosition((int)Ticks);

        setTolerance(ahwMap);

        RunToPosition(ahwMap);

        //HeadingAdjust = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        //for(double Counter = 0.2; Counter <= 1; Counter += 0.2)
        setPower(0, ForwardPower, 0);


        while((Motors[1].isBusy() || Motors[2].isBusy()) && (Motors[3].isBusy() || Motors[4].isBusy())) {

        }

        setPower(0,0,0);
    }

    boolean isGoing(DcMotor c){
        return (Math.abs(c.getCurrentPosition() - c.getTargetPosition()) > TOLERANCE);
    }
    public void StraightWiIsGoing(double NumbCM, double ForwardPower, HardwareMap ahwMap) { //POSITIVE POWER IS FORWARD!!!

        ResetMotorEncoders(ahwMap);
        SetZeroBehavior(true, ahwMap);

        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_CM * NumbCM;

        Motors[1].setTargetPosition((int)Ticks);
        Motors[2].setTargetPosition((int)Ticks);
        Motors[3].setTargetPosition((int)Ticks);
        Motors[4].setTargetPosition((int)Ticks);

        RunToPosition(ahwMap);

        //HeadingAdjust = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        //for(double Counter = 0.2; Counter <= 1; Counter += 0.2)
        setPower(0, ForwardPower, 0);

        while((isGoing(Motors[1]) || (isGoing(Motors[2]))) && (isGoing(Motors[3]) || (isGoing(Motors[4])))) {

        }

        setPower(0,0,0);
    }


    public void MoveEncoderTicksIMU(double NumbCM, double ForwardPower, HardwareMap ahwMap) { //POSITIVE POWER IS FORWARD!!!
        // This method is the same as MoveEncoderTicks but with IMU thrown in for slight correction. May be slightly less precise (by distance) and will be slightly slower, but will be more accurate movement directionally.

        ResetMotorEncoders(ahwMap);

        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_CM * NumbCM;

        HeadingAdjust = CurrentPos.firstAngle;

        Motors[1].setTargetPosition((int)Ticks);
        Motors[2].setTargetPosition((int)Ticks);
        Motors[3].setTargetPosition((int)Ticks);
        Motors[4].setTargetPosition((int)Ticks);

        RunToPosition(ahwMap);

        //HeadingAdjust = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        //for(double Counter = 0.2; Counter <= 1; Counter += 0.2)
        setPower(0, ForwardPower, 0);

        while((Motors[1].isBusy() || Motors[2].isBusy()) && (Motors[3].isBusy() || Motors[4].isBusy())) {
            CurrentOrientation = CurrentPos.firstAngle - HeadingAdjust;
            if(Motors[1].getPower() > 0)
            {
                if(CurrentOrientation > IMU_OFFSET)
                {
                    Motors[3].setPower(ForwardPower - 0.1);
                    Motors[4].setPower(ForwardPower - 0.1);
                }
                else if(CurrentOrientation < -IMU_OFFSET)
                {
                    Motors[1].setPower(ForwardPower - 0.1);
                    Motors[2].setPower(ForwardPower - 0.1);
                }
                else setPower(0, ForwardPower, 0);
            }
            else if(Motors[1].getPower() < 0)
            {
                if(CurrentOrientation > IMU_OFFSET)
                {
                    Motors[1].setPower(ForwardPower - 0.1);
                    Motors[2].setPower(ForwardPower - 0.1);
                }
                else if(CurrentOrientation < -IMU_OFFSET)
                {
                    Motors[3].setPower(ForwardPower - 0.1);
                    Motors[4].setPower(ForwardPower - 0.1);
                }
                else setPower(0, ForwardPower, 0);
            }
        }

        setPower(0,0,0);
    }

    public void StrafeEncoderTicks(double NumbCM, double SidewaysPower, HardwareMap ahwMap) //POSITIVE POWER IS TO THE RIGHT!!!
    {

        ResetMotorEncoders(ahwMap);

        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_CM * NumbCM;

        Motors[1].setTargetPosition((int)Ticks);
        Motors[2].setTargetPosition(-(int)Ticks);
        Motors[3].setTargetPosition((int)Ticks);
        Motors[4].setTargetPosition(-(int)Ticks);

        RunToPosition(ahwMap);

        //HeadingAdjust = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        //for(double Counter = 0.2; Counter <= 1; Counter += 0.2)
        setPower(SidewaysPower, 0, 0);

        while((Motors[1].isBusy() || Motors[2].isBusy()) && (Motors[3].isBusy() || Motors[4].isBusy())) {
        }
        setPower(0,0,0);
    }

    public void TurnDegreesCurrentPos(double Degrees, HardwareMap ahwMap)
    {
        //TURNING LEFT IS POSITIVE!!!
        //double currentHeading = imu.getAngularOrientation().firstAngle;

        ResetMotorEncoders(ahwMap);

        //Mess with numbers if different circumference.
        double Ticks = Degrees * 8.8;

        setPower(0, 0, Degrees);

        while((Motors[2].getPower() != 0 || Motors[3].getPower() != 0) && (Motors[1].getPower() != 0 || Motors[4].getPower() != 0))
        {
            if(Math.abs(Motors[1].getCurrentPosition()) >= Math.abs(Ticks))
                Motors[1].setPower(0);
            if(Math.abs(Motors[4].getCurrentPosition()) >= Math.abs(Ticks))
                Motors[4].setPower(0);
            if(Math.abs(Motors[3].getCurrentPosition()) >= Math.abs(Ticks))
                Motors[3].setPower(0);
            if(Math.abs(Motors[2].getCurrentPosition()) >= Math.abs(Ticks))
                Motors[2].setPower(0);
        }

        setPower(0, 0, 0);
    }

    public void DiagonalEncoderTicks(double NumbCM, double SidewaysPower, double ForwardPower, HardwareMap ahwMap) {

        ResetMotorEncoders(ahwMap);

        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_CM * NumbCM;

        double[] PowerMod = returnSetPower(SidewaysPower, ForwardPower, 0);

        Motors[1].setTargetPosition((int)(PowerMod[1] * Ticks));
        Motors[2].setTargetPosition(-(int)(PowerMod[2] * Ticks));
        Motors[3].setTargetPosition((int)(PowerMod[3] * Ticks));
        Motors[4].setTargetPosition(-(int)(PowerMod[4] * Ticks));

        RunToPosition(ahwMap);

        //HeadingAdjust = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        //for(double Counter = 0.2; Counter <= 1; Counter += 0.2)
        setPower(SidewaysPower, 0, 0);

        while((Motors[1].isBusy() || Motors[2].isBusy()) && (Motors[3].isBusy() || Motors[4].isBusy())) {
        }
        setPower(0,0,0);
    }

    public void TurnDegreesEncoder(double Degrees, HardwareMap ahwMap)
    {

        ResetMotorEncoders(ahwMap);
        double Ticks = Degrees*TICKS_PER_DEGREE;
        Motors[1].setTargetPosition(-1*(int)Ticks);
        Motors[2].setTargetPosition(-1*(int)Ticks);
        Motors[3].setTargetPosition((int)Ticks);
        Motors[4].setTargetPosition((int)Ticks);
        RunToPosition(ahwMap);

        setPower(0,0,Degrees);

        while((Motors[1].isBusy() || Motors[3].isBusy())&& (Motors[2].isBusy() || Motors[4].isBusy())){
        }

        setPower(0,0,0);
    }


    //The following method is code from Team 16072's virtual_robot program. Small changes are only to make it fit our format, the bulk of the method was written by them.
    void setPower(double px, double py, double pa){
        double p1 = -px + py - pa;
        double p2 = px + py - pa;
        double p3 = -px + py + pa;
        double p4 = px + py + pa;
        double max = Math.max(1.0, Math.abs(p1));
        max = Math.max(max, Math.abs(p2));
        max = Math.max(max, Math.abs(p3));
        max = Math.max(max, Math.abs(p4));
        p1 /= max;
        p2 /= max;
        p3 /= max;
        p4 /= max;
        Motors[1].setPower(p1);
        Motors[4].setPower(p4);
        Motors[3].setPower(p3);
        Motors[2].setPower(p2);
    }

    double[] returnSetPower(double px, double py, double pa){
        double[] p = new double[5];
         p[1] = -px + py - pa;
         p[2] = px + py - pa;
         p[3] = -px + py + pa;
         p[4] = px + py + pa;
        double max = Math.max(1.0, Math.abs(p[1]));
        max = Math.max(max, Math.abs(p[2]));
        max = Math.max(max, Math.abs(p[3]));
        max = Math.max(max, Math.abs(p[4]));
        p[1] /= max;
        p[2] /= max;
        p[3] /= max;
        p[4] /= max;
        Motors[1].setPower(p[1]);
        Motors[4].setPower(p[2]);
        Motors[3].setPower(p[3]);
        Motors[2].setPower(p[4]);

        return p;
    }
}
