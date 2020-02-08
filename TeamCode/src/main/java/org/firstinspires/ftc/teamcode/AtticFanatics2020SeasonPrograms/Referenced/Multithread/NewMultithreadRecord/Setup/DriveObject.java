package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord.Setup;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Utils.BNO055IMUUtil;

import java.lang.reflect.Type;

public class DriveObject {

    DcMotorImplEx motor;
    Servo servo;
    CRServo crservo;
    BNO055IMU imu;

    public enum type{
        DcMotorImplEx, Servo, CRServo, IMU, Null
    }

    private String objectName;

    public enum classification{
        Drivetrain, toPosition, Default, Sensor
    }

    private type thisType;
    private classification thisClass;

    public DriveObject(type typeName, String objectName, classification classifier, HardwareMap hwMap){

        switch(typeName){
            case DcMotorImplEx:
                motor = hwMap.get(DcMotorImplEx.class, objectName);
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

    public void set(double Value){
        switch(thisType){
            case DcMotorImplEx:
                switch(thisClass){
                    case Drivetrain:
                        motor.setPower(Value);
                        break;
                    case toPosition:
                        //Add own PID or something here
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

    public Double get(){
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
}
