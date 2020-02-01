package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.List;


@Disabled
public class RoadRunnerAutoConfigure {

    public BNO055IMU imu;

    public Orientation CurrentPos;

    public Servo Gripper;

    public Servo RotateGripper;

    public Servo FoundationLeft;

    public Servo FoundationRight;

    public DcMotorImplEx ExtendGripper;

    public DcMotorImplEx ScissorLeft, ScissorRight;

    public VoltageSensor voltSense;

    public DcMotor IngesterMotor;

    public boolean Configured = false;

    public static final int TOLERANCE = 10;

    public static final double LEFT_OPEN = 0.723;
    public static final double LEFT_CLOSE = 0.25;
    public static final double RIGHT_OPEN = 0.4;
    public static final double RIGHT_CLOSE = 0.883;

    public static final double GRIPPER_CLOSED = 1;
    public static final double GRIPPER_OPEN = 0.2;

    public static final int[] levels = {0,600,1100,1500,1900,2300,2700, 3100, 3500, 3900};

    public enum Ingester{
        IN, OUT, STOPPEDIN, STOPPEDOUT
    }

    public Ingester ingesterStates = Ingester.IN;

    enum Scissor{
        STAGE1, STAGE2, STAGE3, STAGE4, STAGE5, STAGE6, STAGE7, STAGE8, STAGE9, STAGE10, STAGE11, STAGE12
    }

    enum Robot{
        INTAKING, TRANSPORTING, LIFTING, PLACE
    }

    HardwareMap hwMap;

    List<LynxModule> allHubs;

    public void setBulkCachingManual(){
        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    public void clearBulkCache(){
        for (LynxModule module : allHubs) {
            module.clearBulkCache();
        }
    }

    public HardwareMap Configure(HardwareMap ahwMap)
    {
        hwMap = ahwMap;

        imu = hwMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);

        //Return IMU declaration if we use it, this is to preserve a whole lotta runtime.

        FoundationLeft = hwMap.get(Servo.class, "foundation_left");
        FoundationRight = hwMap.get(Servo.class, "foundation_right");
        //ScissorLeft = hwMap.get(DcMotorImplEx.class, "scissor_left");
        //ScissorRight = hwMap.get(DcMotorImplEx.class, "scissor_right");
        IngesterMotor = hwMap.get(DcMotorImplEx.class, "ingester");
        //Gripper = hwMap.get(Servo.class, "gripper");
        //ExtendGripper = hwMap.get(DcMotorImplEx.class, "extend_gripper");
        //voltSense = hwMap.get(VoltageSensor.class, "Motor Controller 1"); //I have no idea what the voltage sensor name is so...

        //ScissorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //ScissorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(new BNO055IMU.Parameters());
        //See above for reason why this is commented out

        CurrentPos = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        */

        //ScissorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //ScissorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //ScissorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //ScissorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //ScissorLeft.setTargetPositionTolerance(50);
        //ScissorRight.setTargetPositionTolerance(50);

        allHubs = hwMap.getAll(LynxModule.class);

        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        return hwMap;
    }
}
