package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

//This Class contains SetPower, MoveEncoderTicks, and TurnDegrees, for MECANUM

public class RRDriveStates extends StatesConfigure {

    float HeadingAdjust, CurrentOrientation;

    private static final double TICKS_PER_INCH = 45.88;
    private static final double TICKS_PER_SIDEWAYS_INCH = 49.8;
    private static final double TICKS_PER_DEGREE = 8.6;
    static final double P_CONSTANT = 0.001;
    //static final double INERTIA_TICKS = 100;
    static final double TURN_OFFSET = 10;
    static final double IMU_OFFSET = 5;

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

    /*public void ExtendGripper(boolean out, boolean wait){
        ExtendGripper.setTargetPositionTolerance(100);
        ElapsedTime time = new ElapsedTime();
        if(out){
            ExtendGripper.setTargetPosition(EXTENDED);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ExtendGripper.setPower(-1);
        }
        else{
            ExtendGripper.setTargetPosition(0);
            ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ExtendGripper.setPower(1);
        }
        if(wait) while(ExtendGripper.isBusy());
    }

    public void grabBlock(){
        Gripper.setPosition(GRIPPER_OPEN);
        setScissorLevel(0, true);
        ElapsedTime time = new ElapsedTime();
        Gripper.setPosition(GRIPPER_CLOSED);
        while(time.milliseconds() < 500); // give it time to close
        setScissorLevel(1, true);
    }

/*
    public void grabBlock(int endPosition){
        Gripper.setPosition(GRIPPER_OPEN);
        setScissorLevel(0, true);
        Gripper.setPosition(GRIPPER_CLOSED);
        ElapsedTime time = new ElapsedTime();
        while(time.milliseconds() < 500); // give it time to close
        setScissorLevel(endPosition, true);
    }

     */
    /*

    public void dropBlock(){ // SCISSOR ENDS UP AT LEVEL 1, MIGHT WANT TO OPTIMIZE
        ElapsedTime time = new ElapsedTime();
        Gripper.setPosition(GRIPPER_OPEN);
        while(time.milliseconds() < 400);
    }

public void setScissorLevel(int level, boolean Wait){
        Scissor.setTargetPositionTolerance(100);
        Scissor.setTargetPosition(levels[level]);
        Scissor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(Scissor.getTargetPosition() > Scissor.getCurrentPosition()) Scissor.setPower(1);
        else Scissor.setPower(-1);
        if(Wait) while(Scissor.isBusy());
    }

     */

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

        drive(MotorMod, true);
    }

    public void Turn(double Degrees)
    {
        Turn(Degrees, findPositivity(Degrees) * 0.8);
    }

    public void Turn(double Degrees, double Power) //Degrees positivity/negativity is irrelevant
    {
        resetMotorEncoders();

        double Ticks = Math.abs(Degrees) * TICKS_PER_DEGREE;

        double[] P = {0, -Power, -Power, Power, Power};

        setTargetPosition(P, Ticks/Math.abs(Power));

        setPower(P);

        while(Math.abs(targetPosition[1]) - Math.abs(Motors[1].getCurrentPosition()) > 20){}

        setPowerZero();
    }

    public void turnToDegree(double Degrees, double Power){

        resetMotorEncoders();

        double[] P = {0, -Power, -Power, Power, Power};

        double currentPos, startPos = Math.abs(getHeading() - Degrees); //target = imuAccount(originalPos + Degrees);

        setPower(P);

        while((currentPos = Math.abs(Degrees - getHeading())) - 1 >= 0){
            //System.out.println(originalPos);
            /*if(startPos - currentPos < 10){
                Motors[1].setPower(P[1] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[1]));
                Motors[2].setPower(P[2] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[2]));
                Motors[3].setPower(P[3] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[3]));
                Motors[4].setPower(P[4] * (currentPos - startPos) / 10.0 + 0.2 * findPositivity(P[4]));
            }
            */if(currentPos < 60){
                Motors[1].setPower(P[1] * Math.pow(currentPos / 60.0, 2) + 0.1 * findPositivity(P[1]));
                Motors[2].setPower(P[2] * Math.pow(currentPos / 60.0, 2) + 0.1 * findPositivity(P[2]));
                Motors[3].setPower(P[3] * Math.pow(currentPos / 60.0, 2) + 0.1 * findPositivity(P[3]));
                Motors[4].setPower(P[4] * Math.pow(currentPos / 60.0, 2) + 0.1 * findPositivity(P[4]));
            }
            else setPower(P);
        }

        setPowerZero();
    }

    private void drive(double[] Power, boolean strafe)
    {
        //setPower(Power);

        ElapsedTime time = new ElapsedTime();

        while(time.seconds() < 0.01);

        if(!strafe)
            do{
                slowToTarget(Power);
            }while (Math.abs(Motors[1].getVelocity()) > 0 || Math.abs(Motors[2].getVelocity()) > 0 || Math.abs(Motors[3].getVelocity()) > 0 || Math.abs(Motors[4].getVelocity()) > 0);
        else while (Math.abs(targetPosition[1]) - Math.abs(Motors[1].getCurrentPosition()) > 30 || Math.abs(targetPosition[2]) - Math.abs(Motors[2].getCurrentPosition()) > 30){}

        setPowerZero();
    }

    /*private boolean noSlowToTarget(){
        if(Math.abs(Motors[1].getCurrentPosition() - targetPosition[1]) < 30)
            return false;
        return true;
    }
     */

    private void slowToTarget(double[] motorPower){
        double avrPos;
        if((avrPos = (Math.pow(Motors[1].getCurrentPosition(), 3/2)+ Math.pow(Motors[2].getCurrentPosition(), 3/2) + Math.pow(Motors[3].getCurrentPosition(), 3/2) + Math.pow(Motors[4].getCurrentPosition(), 3/2))/4) < 16000) {
            Motors[1].setPower(avrPos * 0.00005 * motorPower[1] + 0.15 * motorPower[1] / Math.abs(motorPower[1]));
            Motors[2].setPower(avrPos * 0.00005 * motorPower[2] + 0.15 * motorPower[2] / Math.abs(motorPower[2]));
            Motors[3].setPower(avrPos * 0.00005 * motorPower[3] + 0.15 * motorPower[3] / Math.abs(motorPower[3]));
            Motors[4].setPower(avrPos * 0.00005 * motorPower[4] + 0.15 * motorPower[4] / Math.abs(motorPower[4]));
        }
        else if(Math.abs(Math.pow(targetPosition[1], 2) - avrPos) < 16000){
            avrPos = Math.abs(Math.pow(targetPosition[1], 2) - avrPos);
            Motors[1].setPower(avrPos * 0.00005 * motorPower[1] + 0.15 * motorPower[1] / Math.abs(motorPower[1]));
            Motors[2].setPower(avrPos * 0.00005 * motorPower[2] + 0.15 * motorPower[2] / Math.abs(motorPower[2]));
            Motors[3].setPower(avrPos * 0.00005 * motorPower[3] + 0.15 * motorPower[3] / Math.abs(motorPower[3]));
            Motors[4].setPower(avrPos * 0.00005 * motorPower[4] + 0.15 * motorPower[4] / Math.abs(motorPower[4]));
        }
        /*else {
            Motors[1].setPower((targetPosition[1] - posM1) / 1000 * Math.abs(motorPower[1]));
            Motors[4].setPower((targetPosition[4] - posM4) / 1000 * Math.abs(motorPower[4]));
            Motors[3].setPower((targetPosition[3] - posM3) / 1000 * Math.abs(motorPower[3]));
            Motors[2].setPower((targetPosition[2] - posM2) / 1000 * Math.abs(motorPower[2]));
        }
         */
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
        Motors[4].setPower(p[4]);
        Motors[3].setPower(p[3]);
        Motors[2].setPower(p[2]);
        Motors[1].setPower(p[1]);
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

    public void setPowerZero(){
        Motors[1].setPower(0);
        Motors[2].setPower(0);
        Motors[3].setPower(0);
        Motors[4].setPower(0);
    }

    private double leftOrRight(double value){
        if(value < -180) return -180 - value;
        if(value > 180) return -360 + value;
        return value;
    }

    private double findPositivity(double Power) {
        return Power/Math.abs(Power);
    }
}