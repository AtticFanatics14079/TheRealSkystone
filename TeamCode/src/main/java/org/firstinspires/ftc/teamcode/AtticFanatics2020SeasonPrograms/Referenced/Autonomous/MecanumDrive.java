package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;


//This Class contains SetPower, MoveEncoderTicks, and TurnDegrees, for MECANUM

public class MecanumDrive extends Configure {

    Orientation HeadingAdjust, CurrentOrientation;

    static final double TICKS_PER_CM = 12;
    static final double TICKS_PER_DEGREE = 5;
    //static final double INERTIA_TICKS = 100;
    static final double TURN_OFFSET = 10;

    boolean Configured = false;

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
        if (!Configured)
        {
            Configure(hardmap);
            Configured = true;
        }
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

    public void MoveEncoderTicks(double NumbCM, double ForwardPower, HardwareMap ahwMap) {

        if (!Configured)
        {
            Configure(ahwMap);
            Configured = true;
        }

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

        ResetMotorEncoders(ahwMap);

        //No IMU implementation yet

        /*while((Motors[1].getPower() != 0 || Motors[2].getPower() != 0) && (Motors[3].getPower() != 0 || Motors[4].getPower() != 0)) {
            for(int Counter = 1; Counter <= 4; ++Counter)
            {
                if(Math.abs(Motors[Counter].getCurrentPosition()) >= Math.abs(Ticks))
                {
                    Motors[Counter].setPower(0);
                    if ((Motors[1].getPower() == 0 || Motors[2].getPower() == 0) & (Motors[3].getPower() == 0 || Motors[4].getPower() == 0)) {
                        setPower(0, 0, 0);
                        break;
                    }
                }
            }
        }

        */

        /*CurrentOrientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        if(Math.abs(CurrentOrientation.firstAngle - HeadingAdjust.firstAngle) > 2)
            TurnDegreesEncoder(CurrentOrientation.firstAngle - HeadingAdjust.firstAngle, ahwMap);

        setPower(0, 0, 0);
        */

    }

    public void StrafeEncoderTicks(double NumbCM, double SidewaysPower, HardwareMap ahwMap) //POSITIVE POWER IS TO THE RIGHT!!!
    {
        if (!Configured) {
            Configure(ahwMap);
            Configured = true;
        }

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

        ResetMotorEncoders(ahwMap);
    }

    public void TurnDegreesCurrentPos(double Degrees, HardwareMap ahwMap)
    {
        //TURNING LEFT IS POSITIVE!!!

        if (!Configured)
        {
            Configure(ahwMap);
            Configured = true;
        }
        //double currentHeading = imu.getAngularOrientation().firstAngle;

        ResetMotorEncoders(ahwMap);

        //Mess with numbers if different circumference.
        double Ticks = Degrees * 6;

        setPower(0, 0, 1);

        while((Motors[1].getPower() != 0 || Motors[3].getPower() != 0) && (Motors[2].getPower() != 0 || Motors[4].getPower() != 0))
        {
            for(int Counter = 1; Counter <= 4; ++Counter)
            {
                if(Math.abs(Motors[Counter].getCurrentPosition()) >= Math.abs(Ticks))
                    Motors[Counter].setPower(0);
            }
        }

        ResetMotorEncoders(ahwMap);

        setPower(0, 0, 0);
    }

    public void TurnDegreesEncoder(double Degrees, HardwareMap ahwMap)
    {
        if (!Configured)
        {
            Configure(ahwMap);
            Configured = true;
        }
        ResetMotorEncoders(ahwMap);
        double Ticks = Degrees*TICKS_PER_DEGREE;
        Motors[1].setTargetPosition(-1*(int)Ticks);
        Motors[2].setTargetPosition(-1*(int)Ticks);
        Motors[3].setTargetPosition((int)Ticks);
        Motors[4].setTargetPosition((int)Ticks);
        RunToPosition(ahwMap);


        setPower(0,0,1);


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
}
