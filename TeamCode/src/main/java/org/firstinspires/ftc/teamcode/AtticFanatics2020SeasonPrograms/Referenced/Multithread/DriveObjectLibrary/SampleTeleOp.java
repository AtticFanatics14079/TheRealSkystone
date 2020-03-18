package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class SampleTeleOp extends LinearOpMode {

    public double GAS = 1, straightGas, sideGas, turnGas, startTime = 0;
    private int level = 0;
    private double[] levels = {0, 1000, 2000, 3000, 4000};
    HardwareThread hardware;

    @Override
    public void runOpMode() throws InterruptedException {
        hardware = new HardwareThread(new ValueStorage(), hardwareMap);
        hardware.config.ExtendGripper.setPID(2, 0, 0); //Gonna need to mess with this one
        waitForStart();
        ElapsedTime time = new ElapsedTime();
        double lastTime = time.milliseconds();
        hardware.start();
        hardware.startTime(time);
        while(!isStopRequested()){
            if(time.milliseconds() - lastTime >= 1) {
                lastTime = time.milliseconds();
                getInput();
            }
        }
    }

    private void getInput(){
        setPower(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        if(gamepad1.dpad_left) hardware.config.ExtendGripper.set(1000); //THIS MAY BE SKETCH BECAUSE BAD PIDs!!!
        else if(gamepad1.dpad_right) hardware.config.ExtendGripper.set(0);
        /* Next step (once we figure out the PIDs)
        if(gamepad1.dpad_up) {
            Sequence s = new Sequence(() -> hardware.config.ExtendGripper.set(0), null);
            Sequence t = new Sequence(() -> hardware.config.ExtendGripper.set(1000), s);
            Thread n = new Thread(t);
            n.run();
        }
        What this should do is extend to 1000, then extend back when it's done. */
    }

    private void setPower(double px, double py, double pa){
        double p1 = -px + py + pa;
        double p2 = px + py + pa;
        double p3 = -px + py - pa;
        double p4 = px + py - pa;
        double max = Math.max(1.0, Math.abs(p1));
        max = Math.max(max, Math.abs(p2));
        max = Math.max(max, Math.abs(p3));
        max = Math.max(max, Math.abs(p4));
        p1 /= max;
        p2 /= max;
        p3 /= max;
        p4 /= max;
        hardware.config.backLeft.setPower(p1 * 2700); //To switch to velo, random number atm
        hardware.config.frontLeft.setPower(p2 * 2700);
        hardware.config.frontRight.setPower(p3 * 27000);
        hardware.config.backRight.setPower(p4 * 2700);
    }
}
