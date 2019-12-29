package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

@Autonomous(name="test files")
public class AccessFiles_DONOTDELETE extends LinearOpMode {

    //Cole from team 9261 helped by giving this, all file references are off of his help.                                                                                                             *Cole I think I love you*

    String fileName = "test.txt";
    String data = "test";
    FileOutputStream fos;

    @Override
    public void runOpMode() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+fileName);
            if(file.exists()) {
                boolean fileDeleted = file.delete();
                if(!fileDeleted) {
                }

                boolean fileCreated = file.createNewFile();
                if(!fileCreated) {
                }
            }

            fos = new FileOutputStream(file, true);

            FileWriter fWriter;

            try {
                fWriter = new FileWriter(fos.getFD());

                fWriter.write(data);

                fWriter.flush();
                fWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                fos.getFD().sync();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}