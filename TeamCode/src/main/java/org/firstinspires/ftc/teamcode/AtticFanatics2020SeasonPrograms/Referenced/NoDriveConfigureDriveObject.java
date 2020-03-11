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

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.List;


@Disabled
public class NoDriveConfigureDriveObject {

    public BNO055IMU imu;

    public Servo Gripper;
    public Servo FoundationLeft;
    public Servo FoundationRight;

    public DcMotorImplEx ExtendGripper;
    public DcMotorImplEx ScissorLeft, ScissorRight;
    public DcMotorImplEx IngesterMotor;

    public static final double LEFT_OPEN = 0.65;
    public static final double LEFT_CLOSE = 0.19;
    public static final double RIGHT_OPEN = 0.4;
    public static final double RIGHT_CLOSE = 0.87;

    public static final double GRIPPER_CLOSED = 1;
    public static final double GRIPPER_OPEN = 0.45;

    public static final int EXTEND_OUT = 1100;

    public static final int[] levels = {0, 700, 1170, /*START OF STACKING LEVELS*/ 820, 1150, 1400, 1900, 2380, 3250, 3900, 4920, 6040, 7240, 8600, 10400, 12220};

    public enum Ingester{
        IN, OUT, STOPPEDIN, STOPPEDOUT
    }

    public Ingester ingesterStates = Ingester.IN;

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
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        FoundationLeft = hwMap.get(Servo.class, "foundation_left");
        FoundationRight = hwMap.get(Servo.class, "foundation_right");
        ScissorLeft = hwMap.get(DcMotorImplEx.class, "scissor_left");
        ScissorRight = hwMap.get(DcMotorImplEx.class, "scissor_right");
        IngesterMotor = hwMap.get(DcMotorImplEx.class, "ingester");
        Gripper = hwMap.get(Servo.class, "gripper");
        ExtendGripper = hwMap.get(DcMotorImplEx.class, "extend_gripper");

        ScissorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ScissorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ScissorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ScissorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ExtendGripper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ScissorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ScissorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ExtendGripper.setDirection(DcMotorSimple.Direction.REVERSE);

        allHubs = hwMap.getAll(LynxModule.class);

        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        return hwMap;
    }
}
