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

    public double[] targetPosition = new double[5];

    public Servo Gripper;

    public Servo RotateGripper;

    public Servo FoundationLeft;

    public Servo FoundationRight;

    public DcMotor ExtendGripper;

    public DcMotor Scissor;

    public boolean Configured = false;

    public static final int TOLERANCE = 10;

    public static final double LEFT_OPEN = 0.3333;
    public static final double LEFT_CLOSE = 0.7399;
    public static final double RIGHT_OPEN = 0.7899;
    public static final double RIGHT_CLOSE = 0.3833;

    public static final double GRIPPER_CLOSED = 0.2;
    public static final double GRIPPER_OPEN = 0.6;

    public double getTargetPosition(int motor){
        return targetPosition[motor];
    }

    public void setTolerance(){
        Motors[1].setTargetPositionTolerance(TOLERANCE);
        Motors[2].setTargetPositionTolerance(TOLERANCE);
        Motors[3].setTargetPositionTolerance(TOLERANCE);
        Motors[4].setTargetPositionTolerance(TOLERANCE);
    }

    public void setTolerance(int Tolerance){
        Motors[1].setTargetPositionTolerance(Tolerance);
        Motors[2].setTargetPositionTolerance(Tolerance);
        Motors[3].setTargetPositionTolerance(Tolerance);
        Motors[4].setTargetPositionTolerance(Tolerance);
    }

    public void resetMotorEncoders(){

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

    public void runToPosition(){
        Motors[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors[2].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors[3].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors[4].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setTargetPosition(double[] PosMod, double Ticks){

        targetPosition[1] = (Ticks * PosMod[1]);
        targetPosition[2] = (Ticks * PosMod[2]);
        targetPosition[3] = (Ticks * PosMod[3]);
        targetPosition[4] = (Ticks * PosMod[4]);
    }

    public void setTargetPosition(double Ticks){

        targetPosition[1] = (Ticks);
        targetPosition[2] = (Ticks);
        targetPosition[3] = (Ticks);
        targetPosition[4] = (Ticks);
    }

    public HardwareMap Configure(HardwareMap ahwMap)
    {
        HardwareMap hwMap = ahwMap;

        /*BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        Return IMU declaration if we use it, this is to preserve a whole lotta runtime.
        */

        Motors[1] = hwMap.get(DcMotorImplEx.class, "back_left_motor");
        Motors[2] = hwMap.get(DcMotorImplEx.class, "front_left_motor");
        Motors[3] = hwMap.get(DcMotorImplEx.class, "front_right_motor");
        Motors[4] = hwMap.get(DcMotorImplEx.class, "back_right_motor");
        Gripper = hwMap.get(Servo.class, "gripper");
        RotateGripper = hwMap.get(Servo.class, "rotate_gripper");
        ExtendGripper = hwMap.get(DcMotor.class, "extend_gripper");
        FoundationLeft = hwMap.get(Servo.class, "foundation_left");
        FoundationRight = hwMap.get(Servo.class, "foundation_right");
        Scissor = hwMap.get(DcMotor.class, "scissor");

        Motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[4].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Scissor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ExtendGripper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Motors[3].setDirection(DcMotor.Direction.REVERSE);
        Motors[4].setDirection(DcMotor.Direction.REVERSE);

        //imu = hwMap.get(BNO055IMU.class, "imu");
        //imu.initialize(new BNO055IMU.Parameters());
        //See above for reason why this is commented out

        //CurrentPos = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        Scissor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Scissor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ExtendGripper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ExtendGripper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        return hwMap;
        }
}
