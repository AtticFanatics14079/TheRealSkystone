package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

public class MecanumDriveStates extends StatesConfigure {

    private final double IN_TO_TICKS = 43.89;
    private final double kP = 0.0005;

    public void drive(double distance){
        Motors[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motors[4].setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double targetTicks = distance*IN_TO_TICKS;
        double averagePosition = 0;
        double m1Position = 0;
        double m2Position = 0;
        double m3Position = 0;
        double m4Position = 0;
        double power = 0;

        while(averagePosition<targetTicks){
            m1Position = Motors[1].getCurrentPosition();
            m2Position = Motors[2].getCurrentPosition();
            m3Position = Motors[3].getCurrentPosition();
            m4Position = Motors[4].getCurrentPosition();
            averagePosition = (m1Position+m2Position+m3Position+m4Position)/4;

            power = (targetTicks-averagePosition)*kP;
            Motors[1].setPower(power);
            Motors[2].setPower(power);
            Motors[3].setPower(power);
            Motors[4].setPower(power);

        }

    }


}
