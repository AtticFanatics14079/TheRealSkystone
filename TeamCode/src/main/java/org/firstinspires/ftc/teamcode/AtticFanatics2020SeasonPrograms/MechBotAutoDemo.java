package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import android.graphics.Color;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Example Autonomous Opmode
 *
 * Uses Line-following two drive around the tape at the perimeter of the lander.
 *
 * Requires mechanum bot configuration.
 *
 * Start with bot in center of lander, facing top of screen.
 *
 * Disabling for now; it was designed to work with Rover Ruckus field
 *
 */

@Autonomous(name = "mechbot auto demo", group = "Mechanum")
public class MechBotAutoDemo extends LinearOpMode {

    DcMotor m1, m2, m3, m4;
    //GyroSensor gyro;
    BNO055IMU imu;
    ColorSensor colorSensor;
    Servo backServo;

    public void runOpMode(){

        m1 = hardwareMap.dcMotor.get("back_left_motor");
        m2 = hardwareMap.dcMotor.get("front_left_motor");
        m3 = hardwareMap.dcMotor.get("front_right_motor");
        m4 = hardwareMap.dcMotor.get("back_right_motor");
        m1.setDirection(DcMotor.Direction.REVERSE);
        m2.setDirection(DcMotor.Direction.REVERSE);
        m1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(new BNO055IMU.Parameters());

        colorSensor = hardwareMap.colorSensor.get("color_sensor");
        backServo = hardwareMap.servo.get("back_servo");

        Orientation orientation;

        ElapsedTime waitTime = new ElapsedTime();
        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Seconds since init","%d. Press start when ready.", (int)waitTime.seconds());
            telemetry.update();
        }

        //Turn 45 degrees.
        setPower(0, 0, 0.5f);
        while (opModeIsActive()){
            orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            if (orientation.firstAngle >= 45) break;
        }
        setPower(0, 0, 0);

        setPower(0,0.5f,0);

        float currentHeading = 45;
        ElapsedTime et = new ElapsedTime();

        //Main loop; once per trip around the lander
        while (opModeIsActive()){

            //Turn 90 degrees.
            setPower(0,0,0.5f);
            backServo.setPosition(0.333 * (currentHeading - 45.0f)/90.0f);
            telemetry.addData("Turning 90 degrees","");
            telemetry.update();
            while (opModeIsActive()){
                orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                if (AngleUtils.normalizeDegrees(orientation.firstAngle - currentHeading) >= 90) break;
            }

            currentHeading = (float)AngleUtils.normalizeDegrees360(currentHeading + 90);


            telemetry.addData("Line Following","");
            telemetry.update();
            et.reset();



        }

        m1.setPower(0);
        m2.setPower(0);
        m3.setPower(0);
        m4.setPower(0);
    }

    void setPower(float px, float py, float pa){
        double p1 = -px + py - pa;
        double p2 = px + py + -pa;
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
        m1.setPower(p1);
        m2.setPower(p2);
        m3.setPower(p3);
        m4.setPower(p4);
    }
}
