package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

public class MecanumDriveStates extends StatesConfigure {

    private final double IN_TO_TICKS = 43.89;

    public void drive(double power, double distance){
        Motors[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        setTargetPosition(537.6);

        Motors[1].setPower(power);
        Motors[2].setPower(power);
        Motors[3].setPower(power);
        Motors[4].setPower(power);

        while(Motors[1].isBusy() || Motors[2].isBusy() || Motors[3].isBusy() || Motors[4].isBusy()){}
    }

    public void setTargetPosition(double targetPosition){
        Motors[1].setTargetPosition((int)targetPosition);
        Motors[2].setTargetPosition((int)targetPosition);
        Motors[3].setTargetPosition((int)targetPosition);
        Motors[4].setTargetPosition((int)targetPosition);
        Motors[1].setMode(DcMotorImplEx.RunMode.RUN_TO_POSITION);
        Motors[2].setMode(DcMotorImplEx.RunMode.RUN_TO_POSITION);
        Motors[3].setMode(DcMotorImplEx.RunMode.RUN_TO_POSITION);
        Motors[4].setMode(DcMotorImplEx.RunMode.RUN_TO_POSITION);
    }
}
