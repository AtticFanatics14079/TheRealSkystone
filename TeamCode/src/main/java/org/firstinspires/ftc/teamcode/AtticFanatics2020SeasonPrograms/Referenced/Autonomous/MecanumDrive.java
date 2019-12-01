package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Configure;

//This Class contains SetPower, MoveEncoderTicks, and TurnDegrees, for MECANUM

public class MecanumDrive extends Configure {

    float HeadingAdjust, CurrentOrientation;

    private static final double TICKS_PER_INCH = 46;
    private static final double TICKS_PER_SIDEWAYS_INCH = 49.8;
    private static final double TICKS_PER_DEGREE = 8.7;
    static final double P_CONSTANT = 0.001;
    //static final double INERTIA_TICKS = 100;
    static final double TURN_OFFSET = 10;
    static final double IMU_OFFSET = 5;
    private int[] levels = {0,-2700, -3100, -4640, -6000, -7500, -9100, -11000, -12900};

    public void UnhookFoundation(){
        FoundationLeft.setPosition(LEFT_OPEN);
        FoundationRight.setPosition(RIGHT_OPEN);
        ElapsedTime time = new ElapsedTime();
        while(time.seconds() < 0.7){}
    }
    public void HookFoundation(){
        FoundationLeft.setPosition(LEFT_CLOSE);
        FoundationRight.setPosition(RIGHT_CLOSE);
        ElapsedTime time = new ElapsedTime();
        while(time.seconds() < 0.7){}
    }

    public void MoveScissor (int level){
        Scissor.setTargetPositionTolerance(80);
        Scissor.setTargetPosition(levels[level]);
        Scissor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(Scissor.getTargetPosition() > Scissor.getCurrentPosition()) Scissor.setPower(1);
        else Scissor.setPower(-1);
        while(Scissor.isBusy()){ }
        Scissor.setPower(0);
    }

    public void ExtendGripper(boolean out){
        ExtendGripper.setTargetPositionTolerance(50);
        if(out){
            ExtendGripper.setTargetPosition(-3180);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ExtendGripper.setPower(-1);
            while(ExtendGripper.isBusy()){}
            ExtendGripper.setPower(0);
        }
        else{
            ExtendGripper.setTargetPosition(0);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ExtendGripper.setPower(1);
            while(ExtendGripper.isBusy()){}
            ExtendGripper.setPower(0);
        }
    }

    public void grabBlock(){ // SCISSOR ENDS UP AT LEVEL 1
            Gripper.setPosition(GRIPPER_OPEN);
            MoveScissor(0);
            Gripper.setPosition(GRIPPER_CLOSED);
            MoveScissor(1);
    }

    public void dropBlock(){ // SCISSOR ENDS UP AT LEVEL 1, MIGHT WANT TO OPTIMIZE
            MoveScissor(0);
            Gripper.setPosition(GRIPPER_OPEN);
            MoveScissor(1);
    }

    public void MoveEncoderTicks(double NumbCM, double ForwardPower) {
        //POSITIVE POWER IS FORWARD!!!
        //This method is not used but is proven to work. As such, I am keeping it a a backup unless someone has a better idea.
        resetMotorEncoders();

        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_INCH * NumbCM;

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

        double Ticks = TICKS_PER_INCH * findPositivity(ForwardPower) * Math.abs(NumbCM);

        setTargetPosition(Ticks);

        //if(ExtraPrecise) setTolerance(2);
        //else setTolerance();

        drive(MotorMod, false);
    }

    public void Move(double NumbCM) { //Assumes forward/back movement at max speed, is the quickest overload for straight movement.

        resetMotorEncoders();

        //Mess with numbers, as different circumference.
        double Ticks = TICKS_PER_INCH * NumbCM;

        double NumbCMPositivity = findPositivity(NumbCM);

        double[] P = {0, NumbCMPositivity, NumbCMPositivity, NumbCMPositivity, NumbCMPositivity};

        setTargetPosition(Ticks);

        drive(P, false);
    }

    public void Move(double NumbCM, double SidewaysPower) //Right is a positive power, positivity/negativity of NumbCM does not matter.
    {
        resetMotorEncoders();

        double[] MotorMod = returnSetPower(SidewaysPower, 0, 0);

        double Ticks = TICKS_PER_SIDEWAYS_INCH * Math.abs(NumbCM) / Math.abs(SidewaysPower);

        setTargetPosition(MotorMod, Ticks);

        drive(MotorMod, true);
    }

    public void Move(double NumbCM, double SidewaysPower, double ForwardPower) {
        //Function works for every movement, however distance is untested for diagonal movements. NumbCM's positivity or negativity should not matter.
        //For slow movement, ONLY USE RESPECTIVE MOVEMENTS WITH POWER AS A PARAMETER

        resetMotorEncoders();

        double[] MotorMod = returnSetPower(SidewaysPower, ForwardPower, 0);

        setTargetPosition(MotorMod, TICKS_PER_INCH * Math.abs(NumbCM));

        drive(MotorMod, false);
    }

    public void Turn(double Degrees)
    {
        Turn(Degrees, findPositivity(Degrees));
    }

    public void Turn(double Degrees, double Power) //Degrees positivity/negativity is irrelevant
    {
        resetMotorEncoders();

        double Ticks = Math.abs(Degrees) * TICKS_PER_DEGREE;

        double[] P = {0, -Power, -Power, Power, Power};

        setTargetPosition(P, Ticks/Math.abs(Power));

        setPower(P);

        while(Math.abs(Motors[1].getCurrentPosition() - targetPosition[1]) > 10){}

        setPowerZero();
    }

    public void Turn(double Degrees, double Power, boolean IMU){
        if(!IMU) {
            Turn(Degrees, Power);
            return;
        }

        resetMotorEncoders();

        double[] P = {0, -Power, -Power, Power, Power};

        double originalPos = CurrentPos.secondAngle;

        setPower(P);

        while(Math.abs(CurrentPos.secondAngle - originalPos - Degrees) > 3){}

        setPowerZero();
    }

    private void drive(double[] Power, boolean strafe)
    {
        slowToTarget(Power);

        double target = Math.abs(targetPosition[1]);
        double power = Math.max(Power[1], Power[2]);
        power = Math.max(power, Power[3]);
        power = Math.max(power, Power[4]);
        power = Math.abs(power);

        ElapsedTime time = new ElapsedTime();

        while(time.seconds() < 0.1){}

        if(!strafe)
            do{
                slowToTarget(Power);
            }while ((Math.abs(Motors[1].getVelocity()) > 100 || Math.abs(Motors[2].getVelocity()) > 100 || Math.abs(Motors[3].getVelocity()) > 100 || Math.abs(Motors[4].getVelocity()) > 100) && time.seconds() < target/power/230);
        else while (Math.abs(Motors[1].getCurrentPosition() - targetPosition[1]) < 30 && time.seconds() < target/power/250){}

        setPowerZero();
    }

    /*private boolean noSlowToTarget(){
        if(Math.abs(Motors[1].getCurrentPosition() - targetPosition[1]) < 30)
            return false;
        return true;
    }
     */

    private void slowToTarget(double[] motorPower){
        Motors[1].setPower((targetPosition[1] - Motors[1].getCurrentPosition())/2000 * Math.abs(motorPower[1]));
        Motors[4].setPower((targetPosition[4] - Motors[4].getCurrentPosition())/2000 * Math.abs(motorPower[4]));
        Motors[3].setPower((targetPosition[3] - Motors[3].getCurrentPosition())/2000 * Math.abs(motorPower[3]));
        Motors[2].setPower((targetPosition[2] - Motors[2].getCurrentPosition())/2000 * Math.abs(motorPower[2]));
    }


    //The following method is code from Team 16072's virtual_robot program. Small changes are only to make it fit our format, the bulk of the method was written by them.
    public void setPower(double px, double py, double pa){
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

    private void setPowerZero(){
        Motors[1].setPower(0);
        Motors[2].setPower(0);
        Motors[3].setPower(0);
        Motors[4].setPower(0);
    }

    private double findPositivity(double Power) {
        return Power/Math.abs(Power);
    }
}