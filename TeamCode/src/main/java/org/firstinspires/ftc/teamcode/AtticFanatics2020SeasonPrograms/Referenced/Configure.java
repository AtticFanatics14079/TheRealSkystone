package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


@Disabled
public class Configure {

    public BNO055IMU imu;

    public Orientation angles;

    public DcMotor[] Motors = new DcMotor[5];

    public Servo Gripper;

    public Servo RotateGripper;

    public boolean Configured = false;

    public void ResetMotorEncoders(HardwareMap ahwMap){

        Motors[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

        Motors[1] = hwMap.get(DcMotor.class, "back_left_motor");
        Motors[2] = hwMap.get(DcMotor.class, "front_left_motor");
        Motors[3] = hwMap.get(DcMotor.class, "front_right_motor");
        Motors[4] = hwMap.get(DcMotor.class, "back_right_motor");
        Gripper = hwMap.get(Servo.class, "gripper");
        RotateGripper = hwMap.get(Servo.class, "rotate_gripper");


        Motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[4].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Motors[3].setDirection(DcMotor.Direction.REVERSE);
        Motors[4].setDirection(DcMotor.Direction.REVERSE);

        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(new BNO055IMU.Parameters());

        return hwMap;
        }
}
