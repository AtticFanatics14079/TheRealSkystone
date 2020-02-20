package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous.RROpModes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.NoDriveConfigure;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.StatesConfigure;

public class MacroThreads__SKETCH_DO_NOT_TRUST_TO_WORK_OR_DELETE extends Thread{

    private NoDriveConfigure config;

    int level;

    public static final int[] levels = {0, 760, 1170, /*START OF STACKING LEVELS*/ 800, 1150, 1470, 1880, 2380, 3250, 3900, 4920, 6040, 7240, 8600, 10400, 12220};

    public static final int EXTEND_OUT = 1100;
    public static final int EXTEND_TO_REST = 430;

    public static final double GRIPPER_CLOSED = 1;
    public static final double GRIPPER_OPEN = 0.55;

    int targetPos;

    public enum Macros{
        PICKUP, STACK
    }

    Macros macro;

    public MacroThreads__SKETCH_DO_NOT_TRUST_TO_WORK_OR_DELETE(Macros macro, int levelInArray, NoDriveConfigure config){ //FIRST BLOCK IS LEVEL 3!!!

        this.config = config;

        this.macro = macro;

        this.level = level;
    }

    public void run(){
        switch(macro){
            case PICKUP:
                pickup();
                break;
            case STACK:
                raise();
                stack();
                break;
        }
    }

    private void pickup(){
        config.Gripper.setPosition(GRIPPER_CLOSED);
        ElapsedTime time = new ElapsedTime();
        while(time.milliseconds() < 350){}
        targetPos = levels[2];
        while(targetPos - config.ScissorRight.getCurrentPosition() > 50 && targetPos - config.ScissorLeft.getCurrentPosition() > 50) scissorP();
        config.ScissorLeft.setPower(0);
        config.ScissorRight.setPower(0);
        config.ExtendGripper.setTargetPosition(EXTEND_TO_REST);
        config.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        config.ExtendGripper.setPower(1);
        while(Math.abs(EXTEND_TO_REST - config.ExtendGripper.getCurrentPosition()) > 100){}
        targetPos = levels[1];
        while(Math.abs(config.ScissorRight.getCurrentPosition() - targetPos) > 50 && Math.abs(config.ScissorLeft.getCurrentPosition() - targetPos) > 50) scissorP();
        config.ScissorRight.setPower(0);
        config.ScissorLeft.setPower(0);
    }

    private void raise(){
        if(level == 3) { //aka stacking the first block
            targetPos = levels[2];
            while(levels[2] - config.ScissorLeft.getCurrentPosition() > 200 && levels[2] - config.ScissorRight.getCurrentPosition() > 200) scissorP();
            config.ScissorLeft.setPower(0);
            config.ScissorRight.setPower(0);
            config.ExtendGripper.setTargetPosition(EXTEND_OUT);
            config.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while(Math.abs(EXTEND_OUT - config.ExtendGripper.getCurrentPosition()) > 250) {}
            targetPos = levels[level = 3];
            while(Math.abs(config.ScissorLeft.getCurrentPosition() - targetPos) > 100 && Math.abs(config.ScissorRight.getCurrentPosition() - targetPos) > 100) scissorP();
            config.ScissorRight.setPower(0);
            config.ScissorLeft.setPower(0);
            return;
        }
        targetPos = levels[level]-100;
        int heightConfidence = 100;
        while(Math.abs(config.ScissorLeft.getCurrentPosition() - targetPos) > heightConfidence && Math.abs(config.ScissorRight.getCurrentPosition() - targetPos) > heightConfidence) scissorP();
        config.ScissorRight.setPower(0);
        config.ScissorLeft.setPower(0);
    }

    private void stack(){
        if(level == 3){
            config.Gripper.setPosition(GRIPPER_OPEN);
            ElapsedTime time = new ElapsedTime();
            while(time.milliseconds() < 500){}
            targetPos = levels[level = 2];
            while(targetPos - config.ScissorLeft.getCurrentPosition() > 100 && targetPos - config.ScissorRight.getCurrentPosition() > 100) scissorP();
        }
        else {
            config.ExtendGripper.setTargetPosition(EXTEND_OUT);
            config.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while(Math.abs(config.ExtendGripper.getCurrentPosition() - EXTEND_OUT) > 100){}
            config.Gripper.setPosition(GRIPPER_OPEN);
            ElapsedTime time = new ElapsedTime();
            while(time.milliseconds() < 500){}
        }
        config.ExtendGripper.setTargetPosition(0);
        config.ExtendGripper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(config.ExtendGripper.getCurrentPosition() > 150){}
        targetPos = levels[0];
        while(Math.abs(config.ScissorLeft.getCurrentPosition() - targetPos) > 50 && Math.abs(config.ScissorRight.getCurrentPosition() - targetPos) > 50) scissorP();
        config.ScissorRight.setPower(0);
        config.ScissorLeft.setPower(0);
    }

    private void scissorP(){
        if(targetPos > config.ScissorRight.getCurrentPosition() || targetPos > config.ScissorLeft.getCurrentPosition()) {
            config.ScissorRight.setPower((targetPos - config.ScissorRight.getCurrentPosition()) / 30.0);
            config.ScissorLeft.setPower((targetPos - config.ScissorLeft.getCurrentPosition()) / 30.0);
        }
        else{
            config.ScissorRight.setPower((targetPos - config.ScissorRight.getCurrentPosition()) / 200.0);
            config.ScissorLeft.setPower((targetPos - config.ScissorLeft.getCurrentPosition()) / 200.0);
        }
    }
}
