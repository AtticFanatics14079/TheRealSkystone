package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.File;

@Disabled
@TeleOp(name = "Delete File")
public class DeleteFile extends LinearOpMode {

    ControllerInput in = new ControllerInput();

    @Override
    public void runOpMode() throws InterruptedException {
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+in.FileName);
        while(!file.delete() && !isStopRequested()){
            telemetry.addData("File " + in.FileName + " has been deleted: ", file.delete());
            telemetry.update();
        }
    }
}
