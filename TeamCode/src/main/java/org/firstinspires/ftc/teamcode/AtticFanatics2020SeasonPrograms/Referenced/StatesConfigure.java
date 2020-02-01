package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Utils.AxesSigns;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Utils.BNO055IMUUtil;

import java.util.List;
import java.util.Locale;


@Disabled
public class StatesConfigure {

    public BNO055IMU imu;

    public Orientation angles;

    public DcMotorImplEx[] Motors = new DcMotorImplEx[5];

    public double[] targetPosition = new double[5];

    public Servo Gripper;

    public Servo RotateGripper;

    public Servo FoundationLeft;

    public Servo FoundationRight;

    public DcMotorImplEx ExtendGripper;

    public DcMotorImplEx ScissorLeft, ScissorRight;

    public VoltageSensor voltSense;

    public DcMotor ingester;

    public boolean Configured = false;

    public static final int TOLERANCE = 10;

    public static final double LEFT_OPEN = 0.723;
    public static final double LEFT_CLOSE = 0.25;
    public static final double RIGHT_OPEN = 0.4;
    public static final double RIGHT_CLOSE = 0.883;
    public static final int EXTEND_OUT = 0;

    public static final double GRIPPER_CLOSED = 1;
    public static final double GRIPPER_OPEN = 0.2;

    public static final int[] levels = {0,600,1100,1500,1900,2300,2700, 3100, 3500, 3900};

    public enum Ingester{
        IN, OUT, STOPPEDIN, STOPPEDOUT
    }

    public Ingester ingesterStates = Ingester.IN;

    enum Robot{
        INTAKING, TRANSPORTING, LIFTING, PLACE
    }

    HardwareMap hwMap;

    List<LynxModule> allHubs;

    public double getTargetPosition(int motor){
        return targetPosition[motor];
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

    public void setTargetPosition(int Ticks){
        Motors[1].setTargetPosition(Ticks);
        Motors[2].setTargetPosition(Ticks);
        Motors[3].setTargetPosition(Ticks);
        Motors[4].setTargetPosition(Ticks);
        Motors[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors[2].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors[3].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors[4].setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

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

        System.out.println("Yate");

        //Return IMU declaration if we use it, this is to preserve a whole lotta runtime.

        Motors[1] = hwMap.get(DcMotorImplEx.class, "back_left_motor");
        Motors[2] = hwMap.get(DcMotorImplEx.class, "front_left_motor");
        Motors[3] = hwMap.get(DcMotorImplEx.class, "front_right_motor");
        Motors[4] = hwMap.get(DcMotorImplEx.class, "back_right_motor");
        FoundationLeft = hwMap.get(Servo.class, "foundation_left");
        FoundationRight = hwMap.get(Servo.class, "foundation_right");
        ScissorLeft = hwMap.get(DcMotorImplEx.class, "scissor_left");
        ScissorRight = hwMap.get(DcMotorImplEx.class, "scissor_right");
        ingester = hwMap.get(DcMotorImplEx.class, "ingester");
        Gripper = hwMap.get(Servo.class, "gripper");
        ExtendGripper = hwMap.get(DcMotorImplEx.class, "extend_gripper");
        //voltSense = hwMap.get(VoltageSensor.class, "Motor Controller 1"); //I have no idea what the voltage sensor name is so...

        Motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motors[4].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ScissorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ScissorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Motors[3].setDirection(DcMotor.Direction.REVERSE);
        Motors[4].setDirection(DcMotor.Direction.REVERSE);

        /*imu = hwMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);

         */

        //BNO055IMUUtil.remapAxes(imu, AxesOrder.YXZ, AxesSigns.NPN);

        Motors[1].setMode(DcMotorImplEx.RunMode.RUN_WITHOUT_ENCODER);
        Motors[2].setMode(DcMotorImplEx.RunMode.RUN_WITHOUT_ENCODER);
        Motors[3].setMode(DcMotorImplEx.RunMode.RUN_WITHOUT_ENCODER);
        Motors[4].setMode(DcMotorImplEx.RunMode.RUN_WITHOUT_ENCODER);
        ScissorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ScissorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ScissorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ScissorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ScissorLeft.setTargetPositionTolerance(50);
        ScissorRight.setTargetPositionTolerance(50);

        allHubs = hwMap.getAll(LynxModule.class);

        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        return hwMap;
    }

    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

    public double getHeading() {
        return imu.getAngularOrientation().firstAngle;
    }
}
