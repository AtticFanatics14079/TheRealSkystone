package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord.Setup.DriveObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Configuration {

    public ArrayList<DriveObject> hardware = new ArrayList<>();
    private List<LynxModule> allHubs;

    public Configuration(HardwareMap hwMap){
        //Add all hardware devices here.
        //Example: hardware.put("motor1", new DriveObject(DriveObject.type.DcMotorImplEx, "left_back_motor", DriveObject.classification.Drivetrain, hwMap));
        //In this example, "left_back_motor" is whatever your configuration says.
        hardware.add(new DriveObject(DriveObject.type.DcMotorImplEx, "back_left_motor", DriveObject.classification.Drivetrain, hwMap));
        hardware.add(new DriveObject(DriveObject.type.DcMotorImplEx, "front_left_motor", DriveObject.classification.Drivetrain, hwMap));
        hardware.add(new DriveObject(DriveObject.type.DcMotorImplEx, "front_right_motor", DriveObject.classification.Drivetrain, hwMap));
        hardware.add(new DriveObject(DriveObject.type.DcMotorImplEx, "back_right_motor", DriveObject.classification.Drivetrain, hwMap));
        hardware.add(new DriveObject(DriveObject.type.DcMotorImplEx, "ingester", DriveObject.classification.Default, hwMap));
        hardware.add(new DriveObject(DriveObject.type.DcMotorImplEx, "scissor_left", DriveObject.classification.toPosition, hwMap));
        hardware.add(new DriveObject(DriveObject.type.DcMotorImplEx, "scissor_right", DriveObject.classification.toPosition, hwMap));
        hardware.add(new DriveObject(DriveObject.type.Servo, "foundation_left", DriveObject.classification.toPosition, hwMap));
        hardware.add(new DriveObject(DriveObject.type.Servo, "foundation_right", DriveObject.classification.toPosition, hwMap));
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
}
