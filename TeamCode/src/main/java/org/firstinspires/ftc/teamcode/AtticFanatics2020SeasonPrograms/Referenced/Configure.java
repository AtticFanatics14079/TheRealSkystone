package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


@Disabled
public class Configure {

    public BNO055IMU imu;

    public Orientation CurrentPos;

    public DcMotorImplEx[] Motors = new DcMotorImplEx[5];

    public Servo Gripper;

    public Servo RotateGripper;

    public Servo FoundationLeft;

    public Servo FoundationRight;

    public DcMotor ExtendGripper;

    public DcMotor Scissor1;

    public boolean Configured = false;

    public static final int TOLERANCE = 50;

    public void setTolerance(HardwareMap ahwMap){
        Motors[1].setTargetPositionTolerance(TOLERANCE);
        Motors[2].setTargetPositionTolerance(TOLERANCE);
        Motors[3].setTargetPositionTolerance(TOLERANCE);
        Motors[4].setTargetPositionTolerance(TOLERANCE);
    }

    public void ResetMotorEncoders(HardwareMap ahwMap){

        Motors[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //ExtendGripper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //ExtendGripper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void RunToPosition(HardwareMap ahwMap){
        Motors[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors[2].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors[3].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors[4].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void SetTargetPosition(double Ticks, HardwareMap ahwMap){

        Motors[1].setTargetPosition((int)Ticks);
        Motors[2].setTargetPosition((int)Ticks);
        Motors[3].setTargetPosition((int)Ticks);
        Motors[4].setTargetPosition((int)Ticks);

    }

    public void SetZeroBehavior(boolean Brake, HardwareMap ahwMap)
    {
        if(Brake)
        {
            Motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Motors[4].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else {
            Motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Motors[4].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    public HardwareMap Configure(HardwareMap ahwMap)
    {
        HardwareMap hwMap = ahwMap;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        Motors[1] = hwMap.get(DcMotorImplEx.class, "back_left_motor");
        Motors[2] = hwMap.get(DcMotorImplEx.class, "front_left_motor");
        Motors[3] = hwMap.get(DcMotorImplEx.class, "front_right_motor");
        Motors[4] = hwMap.get(DcMotorImplEx.class, "back_right_motor");
        Gripper = hwMap.get(Servo.class, "gripper");
        RotateGripper = hwMap.get(Servo.class, "rotate_gripper");
        ExtendGripper = hwMap.get(DcMotor.class, "extend_gripper");
        FoundationLeft = hwMap.get(Servo.class, "foundation_left");
        FoundationRight = hwMap.get(Servo.class, "foundation_right");

        Motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[4].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Motors[3].setDirection(DcMotor.Direction.REVERSE);
        Motors[4].setDirection(DcMotor.Direction.REVERSE);
        //FoundationLeft.setDirection(Servo.Direction.REVERSE);

        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(new BNO055IMU.Parameters());

        CurrentPos = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        return hwMap;
        }
}
