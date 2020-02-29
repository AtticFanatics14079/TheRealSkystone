package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;
import java.util.Arrays;

public class DriveObject {

    private DcMotor motor;
    private Servo servo;
    private CRServo crservo;
    private BNO055IMU imu;
    private ValueStorage vals;
    private int partNum;
    private double[] pid = {30, 0, 0}; //Default values
    private ArrayList<Thread> threads = new ArrayList<>();

    private String objectName;

    public enum type{
        DcMotorImplEx, Servo, CRServo, IMU, Null
    }

    public enum classification{
        Drivetrain, toPosition, Default, Sensor
    }

    private type thisType;
    private classification thisClass;

    public DriveObject(type typeName, String objectName, classification classifier, ValueStorage vals, int partNum, HardwareMap hwMap){

        switch(typeName){
            case DcMotorImplEx:
                motor = hwMap.get(DcMotor.class, objectName);
                break;
            case Servo:
                thisClass = classification.toPosition;
                servo = hwMap.get(Servo.class, objectName);
                break;
            case CRServo:
                crservo = hwMap.get(CRServo.class, objectName);
                break;
            case IMU:
                thisClass = classification.Sensor;
                imu = hwMap.get(BNO055IMU.class, objectName);
                //Add configuration of IMU here
                break;
        }

        this.objectName = objectName;

        this.vals = vals;

        this.partNum = partNum;

        thisType = typeName;

        thisClass = classifier;
    }

    public type getType(){
        return thisType;
    }

    public String getName(){
        return objectName;
    }

    public classification getClassification(){
        return thisClass;
    }

    public int getPartNum(){return partNum;}

    public void setType(type t){
        thisType = t;
    }

    public void setName(String n){
        objectName = n;
    }

    public void setClassification(classification c){
        thisClass = c;
    }

    public void setPartNum(int p){partNum = p;}

    public void setHardware(double Value){
        switch(thisType){
            case DcMotorImplEx:
                switch(thisClass){
                    case Drivetrain:
                        motor.setPower(Value);
                        break;
                    case toPosition:
                        motor.setPower(Value);
                        break;
                    case Default:
                        motor.setPower(Value);
                        break;
                    default:
                        System.out.println("Invalid type/classifier combination.");
                        break;
                }
                break;
            case Servo:
                servo.setPosition(Value);
                break;
            case CRServo:
                switch(thisClass){
                    case Drivetrain:
                        crservo.setPower(Value);
                        break;
                    case Default:
                        crservo.setPower(Value);
                        break;
                    default:
                        System.out.println("Invalid type/classifier combination.");
                        break;
                }
                break;
            default:
                System.out.println("Invalid type for setting.");
                break;
        }
    }

    public Thread set(double Value){
        Double[] p = new Double[partNum + 1];
        Boolean[] b = new Boolean[partNum + 1];
        for(int i = 0; i <= partNum; i++) {
            p[i] = null;
            b[i] = null;
        }
        switch(thisType){
            case DcMotorImplEx:
                switch(thisClass){
                    case Drivetrain:
                        p[partNum] = Value;
                        b[partNum] = true;
                        vals.changedParts(true, b);
                        vals.runValues(true, p);
                        //Maybe apply something to limit acceleration, but for now, just set power.
                        break;
                    case toPosition:
                        //if(pos.isAlive()) pos.stopPart(partNum); //Currently starting a new thread breaks a part
                        Thread pos = new Thread(new PositionThread((int) Value, 1, this));
                        threads.add(pos);
                        pos.start();
                        return pos;
                    case Default:
                        p[partNum] = Value;
                        b[partNum] = true;
                        vals.changedParts(true, b);
                        vals.runValues(true, p);
                        break;
                    default:
                        System.out.println("Invalid type/classifier combination.");
                        break;
                }
                break;
            case Servo:
                p[partNum] = Value;
                b[partNum] = true;
                vals.changedParts(true, b);
                vals.runValues(true, p);
                break;
            case CRServo:
                switch(thisClass){
                    case Drivetrain:
                        p[partNum] = Value;
                        b[partNum] = true;
                        vals.changedParts(true, b);
                        vals.runValues(true, p);
                        //See above for further changes.
                        break;
                    case Default:
                        p[partNum] = Value;
                        b[partNum] = true;
                        vals.changedParts(true, b);
                        vals.runValues(true, p);
                        break;
                    default:
                        System.out.println("Invalid type/classifier combination.");
                        break;
                }
                break;
            default:
                System.out.println("Invalid type for setting.");
                break;
        }
        return null;
    }

    public Double getHardware(){
        switch(thisType){
            case DcMotorImplEx:
                switch(thisClass){
                    case toPosition:
                        return (double) motor.getCurrentPosition();
                    case Drivetrain:
                        return motor.getPower();
                    case Default:
                        return motor.getPower();
                    default:
                        System.out.println("Invalid type/classifier combination.");
                        return null;
                }
            case Servo:
                return servo.getPosition();
            case CRServo:
                return crservo.getPower();
            case IMU:
                //Return reading.
                return 0.0;
            default:
                System.out.println("Invalid type, returning null.");
                return null;
        }
    }

    public Double get(){
        switch(thisType){
            case DcMotorImplEx:
                switch(thisClass){
                    case toPosition:
                        return vals.hardware(false, null)[partNum];
                    case Drivetrain:
                        return vals.hardware(false, null)[partNum];
                    case Default:
                        return vals.hardware(false, null)[partNum];
                    default:
                        System.out.println("Invalid type/classifier combination.");
                        return null;
                }
            case Servo:
                return vals.hardware(false, null)[partNum];
            case CRServo:
                return vals.hardware(false, null)[partNum];
            case IMU:
                //Return reading.
                return 0.0;
            default:
                System.out.println("Invalid type, returning null.");
                return null;
        }
    }

    public void reverse(){
        switch(thisType){
            case DcMotorImplEx:
                motor.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case CRServo:
                crservo.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case Servo:
                servo.setDirection(Servo.Direction.REVERSE);
                break;
            default:
                System.out.println("Invalid type for setting to reverse.");
        }
    }

    public Thread setTargetPosition(double targetPosition, double maxSpeed){
        if(thisClass != classification.toPosition){
            System.out.println("Invalid call.");
            return null;
        }
        switch(thisType){
            case Servo:
                Double[] p = new Double[partNum + 1];
                Boolean[] b = new Boolean[partNum + 1];
                for(int i = 0; i < partNum; i++) {
                    p[i] = null;
                    b[i] = null;
                }
                p[partNum] = targetPosition;
                b[partNum] = true;
                vals.changedParts(true, b);
                vals.runValues(true, p);
                return null;
            case DcMotorImplEx:
                //if(pos.isAlive()) pos.stopPart(partNum); //Currently starting a new thread breaks a part
                Thread pos = new Thread(new PositionThread((int) targetPosition, maxSpeed, this));
                threads.add(pos);
                pos.start();
                return pos;
        }
        return null;
    }

    public Thread groupSetTargetPosition(int[] targetPos, double[] maxSpeed, DriveObject[] drive){
        Boolean[] b = new Boolean[partNum + 1];
        for(int i = 0; i < partNum; i++) b[i] = null;
        for(DriveObject d : drive) {
            if(d.getClassification() != classification.toPosition) return null;
            b[d.getPartNum()] = true;
        }
        vals.changedParts(true, b);
        //if(pos.isAlive()) pos.stopPart(partNum); //Currently starting a new thread breaks a part
        Thread pos = new Thread(new PositionThread(targetPos, maxSpeed, drive));
        threads.add(pos);
        pos.start();
        return pos;
    }

    public Thread groupSetTargetPosition(int targetPos, double maxSpeed, DriveObject[] drive){
        for(DriveObject d : drive) if(d.getClassification() != classification.toPosition) return null;
        //if(pos.isAlive()) pos.stopPart(partNum); //Currently starting a new thread breaks a part
        Thread pos = new Thread(new PositionThread(targetPos, maxSpeed, drive));
        threads.add(pos);
        pos.start();
        return pos;
    }

    public Thread groupSetTargetPosition(int targetPos, double maxSpeed, ArrayList<DriveObject> drive){
        for(DriveObject d : drive) if(d.getClassification() != classification.toPosition) return null;
        //if(pos.isAlive()) pos.stopPart(partNum); //Currently starting a new thread breaks a part
        DriveObject[] d = new DriveObject[drive.size()];
        for(int i = 0; i < drive.size(); i++) d[i] = drive.get(i);
        Thread pos = new Thread(new PositionThread(targetPos, maxSpeed, d));
        threads.add(pos);
        pos.start();
        return pos;
    }

    public double[] getPID(){
        return pid;
    }

    public void setPID(double p, double i, double d){
        pid[0] = p;
        pid[1] = i;
        pid[2] = d;
    }

    public void endAllThreads(){
        for(Thread t : threads) {if(t != null && t.isAlive()) t.stop();}
    }

    public void setPID(double[] pid){
        this.pid = pid;
    }
}