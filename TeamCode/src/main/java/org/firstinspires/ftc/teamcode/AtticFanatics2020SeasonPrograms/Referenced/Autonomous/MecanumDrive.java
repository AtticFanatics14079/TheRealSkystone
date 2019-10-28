package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;

import util.AngleUtils;


//This Class contains SetPower, MoveEncoderTicks, and TurnDegrees, for MECANUM

public class MecanumDrive extends Configure {

    Orientation HeadingAdjust, CurrentOrientation;

    static final double TICKS_PER_CM = 30.5;
    static final double TICKS_PER_DEGREE = 23.6;
    //static final double INERTIA_TICKS = 100;
    static final double TURN_OFFSET = 10;

    boolean Configured = false;

    public void runOpMode() throws InterruptedException {
    }
    public void ExtendGripper(double NumbCm,  HardwareMap hardmap){
        if (!Configured)
        {
            Configure(hardmap);
            Configured = true;
        }
        ResetMotorEncoders(hardmap);
        double Ticks = TICKS_PER_CM * NumbCm;
        ExtendyGripper.setTargetPosition((int)Ticks);
        ExtendyGripper.setPower(1);
        while(ExtendyGripper.isBusy()){

        }
        ExtendyGripper.setPower(0);
    }
    public void setGrip (double position, HardwareMap hardmap){
        RotateGripper.setPosition(position);
    }
    public void GrabDrop(boolean open, HardwareMap hardmap){
        if(open){
            Gripper.setPosition(.6);
        }
        else if(!open){
            Gripper.setPosition(.1);
        }
    }
    public void MoveEncoderTicks(double NumbCM, double SidewaysPower, double ForwardPower, HardwareMap ahwMap) {

        if (!Configured)
        {
            Configure(ahwMap);
            Configured = true;
        }

        ResetMotorEncoders(ahwMap);


        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_CM * NumbCM;

        //HeadingAdjust = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        setPower(SidewaysPower, ForwardPower, 0f);

        while(Math.abs(Motors[1].getCurrentPosition())<Ticks && Math.abs(Motors[2].getCurrentPosition())<Ticks && Math.abs(Motors[3].getCurrentPosition())<Ticks && Math.abs(Motors[4].getCurrentPosition())<Ticks){

        }
        setPower(0,0,0);

        ResetMotorEncoders(ahwMap);

        //No IMU implementation yet

        /*while((Motors[1].getPower() != 0 || Motors[2].getPower() != 0) & (Motors[3].getPower() != 0 || Motors[4].getPower() != 0)) {
            for(int Counter = 1; Counter <= 4; ++Counter)
            {
                if(Math.abs(Motors[Counter].getCurrentPosition()) >= Math.abs(Ticks-100))
                {
                    Motors[Counter].setPower(0);
                    if ((Motors[1].getPower() == 0 || Motors[2].getPower() == 0) & (Motors[3].getPower() == 0 || Motors[4].getPower() == 0)) {
                        setPower(0, 0, 0);
                        break;
                    }
                }
            }
        }


        CurrentOrientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        if(Math.abs(CurrentOrientation.firstAngle - HeadingAdjust.firstAngle) > 2)
            TurnDegrees(CurrentOrientation.firstAngle - HeadingAdjust.firstAngle, ahwMap);

        setPower(0, 0, 0);

         */
    }

    public void TurnDegreesImu(double Degrees, HardwareMap ahwMap)
    {
        //TURNING LEFT IS POSITIVE!!!

        if (!Configured)
        {
            Configure(ahwMap);
            Configured = true;
        }
        double currentHeading = imu.getAngularOrientation().firstAngle;

        ResetMotorEncoders(ahwMap);

        //Mess with numbers if different circumference.
        double Ticks = Degrees * (19.8);

        setPower(0, 0, 0.5f);

        while((Motors[1].getPower() != 0 || Motors[3].getPower() != 0) & (Motors[2].getPower() != 0 || Motors[4].getPower() != 0))
        {
            for(int Counter = 1; Counter <= 4; ++Counter)
            {
                if(Math.abs(Motors[Counter].getCurrentPosition()) >= Math.abs(Ticks))
                    Motors[Counter].setPower(0);
            }
        }

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
        RunToPosition(ahwMap);
        double Ticks = Degrees*TICKS_PER_DEGREE;
        Motors[1].setTargetPosition(-1*(int)Ticks);
        Motors[2].setTargetPosition(-1*(int)Ticks);
        Motors[3].setTargetPosition((int)Ticks);
        Motors[4].setTargetPosition((int)Ticks);
        setPower(0,0,1);


        while((Motors[1].isBusy() || Motors[2].isBusy()|| Motors[3].isBusy() || Motors[4].isBusy())){

        }
        setPower(0,0,0);

        ResetMotorEncoders(ahwMap);
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
        Motors[2].setPower(p2);
        Motors[3].setPower(p3);
        Motors[4].setPower(p4);
    }
}
