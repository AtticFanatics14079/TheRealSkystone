package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.SampleMecanumDriveBase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.RoadRunner.DriveConstants.encoderTicksToInches;

public class ConfigurationRR extends SampleMecanumDriveBase {

    public ArrayList<DriveObject> hardware = new ArrayList<>();
    DriveObject backLeft, frontLeft, frontRight, backRight, ingester, imu, ScissorLeft, ScissorRight, FoundationLeft, FoundationRight, Gripper, ExtendGripper, Capstone;
    private List<LynxModule> allHubs;

    public ConfigurationRR(HardwareMap hwMap, ValueStorage vals){
        //Add all hardware devices here.
        //Example: hardware.put("motor1", new DriveObject(DriveObject.type.DcMotorImplEx, "left_back_motor", DriveObject.classification.Drivetrain, hwMap));
        //In this example, "left_back_motor" is whatever your configuration says.
        int i = 0;
        backLeft = new DriveObject(DriveObject.type.DcMotorImplEx, "back_left_motor", DriveObject.classification.Drivetrain, vals, i++, hwMap);
        frontLeft = new DriveObject(DriveObject.type.DcMotorImplEx, "front_left_motor", DriveObject.classification.Drivetrain, vals, i++, hwMap);
        frontRight = new DriveObject(DriveObject.type.DcMotorImplEx, "front_right_motor", DriveObject.classification.Drivetrain, vals, i++, hwMap);
        backRight = new DriveObject(DriveObject.type.DcMotorImplEx, "back_right_motor", DriveObject.classification.Drivetrain, vals, i++, hwMap);
        ingester = new DriveObject(DriveObject.type.DcMotorImplEx, "ingester", DriveObject.classification.Default, vals, i++, hwMap);
        imu = new DriveObject(DriveObject.type.IMU, "imu", DriveObject.classification.Sensor, vals, i++, hwMap);
        ScissorLeft = new DriveObject(DriveObject.type.DcMotorImplEx, "scissor_left", DriveObject.classification.toPosition, vals, i++, hwMap);
        ScissorRight = new DriveObject(DriveObject.type.DcMotorImplEx, "scissor_right", DriveObject.classification.toPosition, vals, i++, hwMap);
        FoundationLeft = new DriveObject(DriveObject.type.Servo, "foundation_left", DriveObject.classification.toPosition, vals, i++, hwMap);
        FoundationRight = new DriveObject(DriveObject.type.Servo, "foundation_right", DriveObject.classification.toPosition, vals, i++, hwMap);
        Gripper = new DriveObject(DriveObject.type.Servo, "gripper", DriveObject.classification.toPosition, vals, i++, hwMap);
        ExtendGripper = new DriveObject(DriveObject.type.DcMotorImplEx, "extend_gripper", DriveObject.classification.toPosition, vals, i++, hwMap);
        Capstone = new DriveObject(DriveObject.type.Servo, "capstone_servo", DriveObject.classification.toPosition, vals, i++, hwMap);

        hardware.add(backLeft);
        hardware.add(frontLeft);
        hardware.add(frontRight);
        hardware.add(backRight);
        hardware.add(ingester);
        hardware.add(imu);
        hardware.add(ScissorLeft);
        hardware.add(ScissorRight);
        hardware.add(FoundationLeft);
        hardware.add(FoundationRight);
        hardware.add(Gripper);
        hardware.add(ExtendGripper);
        hardware.add(Capstone);

        hardware.get(2).reverse();
        hardware.get(3).reverse();
        //Adding more later

        //Below are other configuration activities that are necessary for writing to file.
        allHubs = hwMap.getAll(LynxModule.class);

        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
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

    @Override
    public PIDCoefficients getPIDCoefficients(DcMotor.RunMode runMode) {
        Double[] pid = hardware.get(0).getPID();
        PIDCoefficients coefficients = new PIDCoefficients(pid[0], pid[1], pid[2]);
        return new PIDCoefficients(coefficients.kP, coefficients.kI, coefficients.kD);
        //Also not sure if this works
    }

    @Override
    public void setPIDCoefficients(DcMotor.RunMode runMode, PIDCoefficients coefficients) {
        hardware.get(0).setPID(coefficients.kP, coefficients.kI, coefficients.kD);
        hardware.get(1).setPID(coefficients.kP, coefficients.kI, coefficients.kD);
        hardware.get(2).setPID(coefficients.kP, coefficients.kI, coefficients.kD);
        hardware.get(3).setPID(coefficients.kP, coefficients.kI, coefficients.kD);
        //No idea if this all works
    }

    @NotNull
    @Override
    public List<Double> getWheelVelocities() {
        List<Double> v = new ArrayList<>();
        v.add(encoderTicksToInches(hardware.get(0).get()));
        v.add(encoderTicksToInches(hardware.get(1).get()));
        v.add(encoderTicksToInches(hardware.get(2).get()));
        v.add(encoderTicksToInches(hardware.get(3).get()));
        return v;
    }

    @NotNull
    @Override
    public List<Double> getWheelPositions() {
        List<Double> v = new ArrayList<>();
        v.add(encoderTicksToInches(hardware.get(0).getAllVals().second));
        v.add(encoderTicksToInches(hardware.get(1).getAllVals().second));
        v.add(encoderTicksToInches(hardware.get(2).getAllVals().second));
        v.add(encoderTicksToInches(hardware.get(3).getAllVals().second));
        return v;
    }

    @Override
    public void setMotorPowers(double v, double v1, double v2, double v3) {
        //Use setPower method of all drivetrain motors.
        hardware.get(0).setPower(v);
        hardware.get(1).setPower(v1);
        hardware.get(2).setPower(v2);
        hardware.get(3).setPower(v3);
    }

    @Override
    protected double getRawExternalHeading() {
        return imu.get();
    }
}
