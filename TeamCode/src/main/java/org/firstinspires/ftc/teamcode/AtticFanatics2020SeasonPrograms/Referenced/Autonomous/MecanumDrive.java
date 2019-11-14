package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
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

    public void UnhookFoundation(){
        FoundationLeft.setPosition(LEFT_OPEN);
        FoundationRight.setPosition(RIGHT_OPEN);
    }
    public void HookFoundation(){
        FoundationLeft.setPosition(LEFT_CLOSE);
        FoundationRight.setPosition(RIGHT_CLOSE);
    }

    public void MoveScissor (int level){

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

    public void ExtendGripper(double NumbCM){
        resetMotorEncoders();
        double Ticks = TICKS_PER_CM * NumbCM;
        ExtendGripper.setTargetPosition((int)Ticks);
        ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ExtendGripper.setPower(NumbCM/Math.abs(NumbCM));
        while(ExtendGripper.isBusy()){
        }
        ExtendGripper.setPower(0);
    }

    public void GrabDrop(boolean open){
        if(open){
            Gripper.setPosition(.6);
        }
        else {
            Gripper.setPosition(.1);
        }
    }



    public void MoveEncoderTicks(double NumbCM, double ForwardPower) { //POSITIVE POWER IS FORWARD!!!
        //This method is not used but is proven to work. As such, I am keeping it a a backup unless someone has a better idea.
        resetMotorEncoders();

        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_CM * NumbCM;

        Motors[1].setTargetPosition((int)Ticks);
        Motors[2].setTargetPosition((int)Ticks);
        Motors[3].setTargetPosition((int)Ticks);
        Motors[4].setTargetPosition((int)Ticks);

        runToPosition();

        //HeadingAdjust = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        //for(double Counter = 0.2; Counter <= 1; Counter += 0.2)
        setPower(0, ForwardPower, 0);


        while((Motors[1].isBusy() || Motors[2].isBusy()) && (Motors[3].isBusy() || Motors[4].isBusy())) {
        }

        setPower(0,0,0);
    }

    public void Move(double NumbCM, double ForwardPower, boolean ExtraPrecise) { //POSITIVE POWER IS FORWARD!!!

        resetMotorEncoders();

        double[] MotorMod = returnSetPower(0, ForwardPower, 0);

        double Ticks = TICKS_PER_CM * Math.abs(NumbCM) / Math.abs(ForwardPower);

        setTargetPosition(MotorMod, Ticks);

        if(ExtraPrecise) setTolerance(5);
        else setTolerance();

        drive(MotorMod);
    }

    public void Move(double NumbCM) { //Assumes forward/back movement at max speed, is the quickest overload for straight movement.

        resetMotorEncoders();

        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_CM * NumbCM;

        double NumbCMPositivity = findPositivity(NumbCM);

        double[] P = {0, NumbCMPositivity, NumbCMPositivity, NumbCMPositivity, NumbCMPositivity};

        setTargetPosition(Ticks);

        drive(P);
    }

    public void Move(double NumbCM, double SidewaysPower) //Right is a positive power, positivity/negativity of NumbCM does not matter.
    {
        resetMotorEncoders();

        double[] MotorMod = returnSetPower(SidewaysPower, 0, 0);

        double Ticks = TICKS_PER_CM * Math.abs(NumbCM) / Math.abs(SidewaysPower);

        setTargetPosition(MotorMod, Ticks);

        drive(MotorMod);
    }

    public void Move(double NumbCM, double SidewaysPower, double ForwardPower) {
        //Function works for every movement, however distance is untested for diagonal movements. NumbCM's positivity or negativity should not matter.
        //For slow movement, ONLY USE RESPECTIVE MOVEMENTS WITH POWER AS A PARAMETER

        resetMotorEncoders();

        double[] MotorMod = returnSetPower(SidewaysPower, ForwardPower, 0);

        setTargetPosition(MotorMod, TICKS_PER_CM * Math.abs(NumbCM));

        drive(MotorMod);
    }

    public void Turn(double Degrees)
    {
        resetMotorEncoders();

        double Ticks = Math.abs(Degrees)  * TICKS_PER_DEGREE;

        double DegreesPositivity = findPositivity(Degrees);

        double[] P = {0, -DegreesPositivity, -DegreesPositivity, DegreesPositivity, DegreesPositivity};

        setTargetPosition(P, Ticks);

        drive(P);
    }

    public void Turn(double Degrees, double Power) //Degrees positivity/negativity is irrelevant
    {
        resetMotorEncoders();

        double Ticks = Math.abs(Degrees) * TICKS_PER_DEGREE;

        double[] P = {0, -Power, -Power, Power, Power};

        setTargetPosition(P, Ticks/Math.abs(Power));

        drive(P);
    }

    private void drive(double[] Power)
    {
        setTolerance();

        runToPosition();

        setPower(Power);

        while ((Motors[1].isBusy() || Motors[2].isBusy())&& (Motors[3].isBusy() || Motors[4].isBusy())){
        }

        setPower(0, 0, 0);
    }

    //The following method is code from Team 16072's virtual_robot program. Small changes are only to make it fit our format, the bulk of the method was written by them.
    private void setPower(double px, double py, double pa){
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

    private void setPower(double[] p)
    {
        Motors[1].setPower(p[1]);
        Motors[2].setPower(p[2]);
        Motors[3].setPower(p[3]);
        Motors[4].setPower(p[4]);
    }

    private double[] returnSetPower(double px, double py, double pa){
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

        return p;
    }

    private double findPositivity(double Power) {
        return Power/Math.abs(Power);
    }
}